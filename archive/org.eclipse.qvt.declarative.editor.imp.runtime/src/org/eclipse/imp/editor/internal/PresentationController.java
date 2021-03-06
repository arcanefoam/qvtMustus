/*******************************************************************************
* Copyright (c) 2007 IBM Corporation.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Robert Fuhrer (rfuhrer@watson.ibm.com) - initial API and implementation
*******************************************************************************/

package org.eclipse.imp.editor.internal;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.imp.core.ErrorHandler;
import org.eclipse.imp.editor.LanguageServiceManager;
import org.eclipse.imp.parser.IModelListener;
import org.eclipse.imp.parser.IParseController;
import org.eclipse.imp.parser.ISourcePositionLocator;
import org.eclipse.imp.preferences.PreferenceCache;
import org.eclipse.imp.runtime.RuntimePlugin;
import org.eclipse.imp.services.ITokenColorer;
import org.eclipse.imp.utils.ConsoleUtil;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Display;

/**
 * A class that does the real work of repairing the text presentation for an associated ISourceViewer.
 * Calls to damage(IRegion) simply accumulate damaged regions into a work queue, which is processed
 * at the subsequent call to update(IParseController, IProgressMonitor).
 * @author Claffra
 * @author rfuhrer@watson.ibm.com
 */
public class PresentationController implements IModelListener {
    public static final String CONSOLE_NAME= "Source Tokens";

    private final ISourceViewer fSourceViewer;

    private final ITokenColorer fColorer;

    private final IParseController fParseCtlr;

    private final Stack<IRegion> fWorkItems= new Stack<IRegion>();

    public PresentationController(ISourceViewer sourceViewer, LanguageServiceManager langServiceMgr) {
        fSourceViewer= sourceViewer;
        this.fParseCtlr= langServiceMgr.getParseController();
        fColorer= langServiceMgr.getTokenColorer();
    }

    public AnalysisRequired getAnalysisRequired() {
        return AnalysisRequired.LEXICAL_ANALYSIS;
    }

    private void dumpToken(Object token, ISourcePositionLocator locator, PrintStream ps) {
        if (locator != null) {
            try {
                final IDocument document= fSourceViewer.getDocument();
                final int startOffset= locator.getStartOffset(token);
//              ps.print( " (" + prs.getKind(i) + ")");
                ps.print(" \t" + startOffset);
                ps.print(" \t" + locator.getLength(token));
                int line = document.getLineOfOffset(startOffset);
                ps.print(" \t" + line);
                ps.print(" \t" + (startOffset - document.getLineOffset(line)));
            } catch (BadLocationException e) {
                RuntimePlugin.getInstance().logException("Error computing position of token", e);
            }
        }
        ps.print(" \t" + token);
        ps.println();
    }

    private void dumpTokens(Iterator<Object> tokenIter, PrintStream ps) {
        ISourcePositionLocator locator = fParseCtlr.getSourcePositionLocator();

        if (locator != null) {
            ps.println(" Offset \tLen \tLine \tCol \tText");
        } else {
            ps.println(" Text");
        }
        for(; tokenIter.hasNext(); ) {
            dumpToken(tokenIter.next(), locator, ps);
        }
    }

    /**
     * Push the damaged area onto the work queue for repair when we get scheduled to process the queue.
     * @param region the damaged area
     */
    public void damage(IRegion region) {
        if (fColorer == null)
            return;

        IRegion bigRegion= fColorer.calculateDamageExtent(region, fParseCtlr);

        if (bigRegion != null) {
            fWorkItems.push(bigRegion);
        }
    }

    public void update(IParseController controller, IProgressMonitor monitor) {
        if (!monitor.isCanceled()) {
//            if (fWorkItems.size() == 0) {
//                ConsoleUtil.findConsoleStream(PresentationController.CONSOLE_NAME).println("PresentationController.update() called, but no damage in the work queue?");
//            }
            synchronized (fWorkItems) {
                for(int n= fWorkItems.size() - 1; !monitor.isCanceled() && n >= 0; n--) {
                    Region damage= (Region) fWorkItems.get(n);
                    changeTextPresentationForRegion(controller, monitor, damage);
                }
                if (!monitor.isCanceled())
                    fWorkItems.removeAllElements();
            }
        }
    }

    private void changeTextPresentationForRegion(IParseController parseController, IProgressMonitor monitor, IRegion damage) {
        if (parseController == null) {
            return;
        }
        if (PreferenceCache.dumpTokens) { // RuntimePlugin.getPreferencesService().getBooleanPreference(PreferenceConstants.P_DUMP_TOKENS)
            PrintStream ps= ConsoleUtil.findConsoleStream(PresentationController.CONSOLE_NAME);

            dumpTokens(parseController.getTokenIterator(damage), ps);
        }

        final TextPresentation presentation= new TextPresentation();
        ISourcePositionLocator locator= parseController.getSourcePositionLocator();

        aggregateTextPresentation(parseController, monitor, damage, presentation, locator);
        if (!monitor.isCanceled() && !presentation.isEmpty()) {
            submitTextPresentation(presentation);
        }
    }

    private void aggregateTextPresentation(IParseController parseController, IProgressMonitor monitor, IRegion damage, TextPresentation presentation,
            ISourcePositionLocator locator) {
        int prevOffset= -1;
        int prevEnd= -1;
        for(Iterator<Object> iter= parseController.getTokenIterator(damage); iter.hasNext() && !monitor.isCanceled(); ) {
            Object token= iter.next();
            int offset= locator.getStartOffset(token);
            int end= locator.getEndOffset(token);

            if (offset <= prevEnd && end >= prevOffset) {
                continue;
            }
             else {
	            TextAttribute attribute= fColorer.getColoring(parseController, token);
	            if (attribute != null) {
	            	changeTokenPresentation(parseController, presentation, offset, attribute, end);
	            }
            }
            prevOffset= offset;
            prevEnd= end;
        }
    }

    private void changeTokenPresentation(IParseController controller, TextPresentation presentation, int startOffset, TextAttribute attribute, int endOffset) {
    	StyleRange styleRange= new StyleRange(startOffset, endOffset - startOffset + 1,
                attribute == null ? null : attribute.getForeground(),
                attribute == null ? null : attribute.getBackground(),
                attribute == null ? SWT.NORMAL : attribute.getStyle());
        
       // SMS 21 Jun 2007:  negative (possibly 0) length style ranges seem to cause problems;
        // but if you have one it should lead to an IllegalArgumentException in changeTextPresentation(..)
        if (styleRange.length <= 0 || styleRange.start + styleRange.length > this.fSourceViewer.getDocument().getLength()) {
        } else {
            presentation.addStyleRange(styleRange);
        }
    }

    private void submitTextPresentation(final TextPresentation uncheckedPresentation) {
    	int charCount = 0;
    	if (fSourceViewer != null) {		
    		IDocument document = fSourceViewer.getDocument();
			if (document != null)			// EDW 27 Sep 2008 May be null after file deleted
				charCount = document.getLength();
    	}
    	final TextPresentation presentation;
    	if (!checkPresentation(uncheckedPresentation, charCount))
			presentation = correctPresentation(uncheckedPresentation, charCount);
    	else
    		presentation = uncheckedPresentation;
        Display.getDefault().asyncExec(new Runnable() {
            public void run() {
            	
            	// SMS 16 Sep 2008
            	int charCount = 0;
            	if (fSourceViewer != null) {		
            		IDocument document = fSourceViewer.getDocument();
					if (document != null)			// EDW 27 Sep 2008 May be null after file deleted
						charCount = document.getLength();
            	}
            	
            	// Attempt to head off exception due to final range extending beyond
            	// last character in text
            	int lastStart = presentation.getLastStyleRange().start;
            	int lastLength = presentation.getLastStyleRange().length;
            	int end = lastStart + lastLength;
        		TextPresentation newPresentation = null;
            	if (end >= charCount) {
            		newPresentation = new TextPresentation();
            		Iterator presIt = presentation.getAllStyleRangeIterator();
            		while (presIt.hasNext()) {
            			StyleRange nextRange = (StyleRange) presIt.next();
            			if (nextRange.start + nextRange.length <= charCount)
            				newPresentation.addStyleRange(nextRange);
            		}
            	} else {
            		newPresentation = presentation;
            	}
            	
        	    // SMS 21 Jun 2007 added try-catch block
        	    // Note:  It doesn't work to just eat the exception here; if there is a problematic token
        	    // then an exception is likely to arise downstream in the computation anyway
        	    try {
        	    	if (fSourceViewer != null)
        	    		fSourceViewer.changeTextPresentation(newPresentation, true);
        	    } catch (IllegalArgumentException e) {
        	        // Possible causes (not necessarily exclusive:
        	    	// - negative length in a style range
        	    	// - overlapping ranges
        	    	// - range extends beyond last character in file
        	    	Iterator<StyleRange> ranges = presentation.getAllStyleRangeIterator();
        	    	List<StyleRange> rangesList = new ArrayList<StyleRange>();
        	    	while(ranges.hasNext())
        	    		rangesList.add((StyleRange)ranges.next());
        	    	String explanation = null;
        	    	if (rangesList.size() > 0) {
        	    		StyleRange firstRange = rangesList.get(0);
        	    		if (firstRange.length < 0)
        	    			explanation = "Style range with start = " + firstRange.start + " has negative length = " + firstRange.length;
        	    		if (explanation == null) {
        	       	    	for (int i = 1; i < rangesList.size(); i++) {
        	       	    		int currStart = rangesList.get(i).start;
        	       	    		int currLength = rangesList.get(i).length;
        	       	    		if (currLength <0) {
        	       	    			explanation = "Style range with start = " + currStart + " has negative length = " + currLength;
        	       	    			break;
        	       	    		}
        	       	    		
        	       	    		int prevStart = rangesList.get(i-1).start;
        	       	    		int prevLength = rangesList.get(i-1).length;
                	    		if (prevStart + prevLength - 1 >= currStart) {
                	    			explanation = "Style range with start = " + prevStart + " and length = " + prevLength + 
                	    				"overlaps style range with start = " + currStart;
                	    			break;
                	    		}
                	    	}
        	    		}
        	    		if (explanation == null) {
        	            	int finalStart = presentation.getLastStyleRange().start;
        	            	int finalLength = presentation.getLastStyleRange().length;
        	            	int finalEnd = finalStart + finalLength;
        	            	if (finalEnd >= charCount) {
            	    			explanation = "Final style range with start = " + finalStart + " and length = " + finalLength + 
            	    				"extends beyond last character (character count = " + charCount + ")";
        	            	}
        	    		}
        	    		if (explanation == null)
        	    			explanation = "Cause not identified";        	    		
        	    	}
 
        	        ErrorHandler.logError("PresentationController.submitTextPresentation:  IllegalArgumentException:  " + explanation, e);
        	        throw e;
        	    }
            }
        });
    }

	private boolean checkPresentation(TextPresentation presentation, int charCount) {		// EDW
		Iterator presIt = presentation.getAllStyleRangeIterator();
		int end = -1;
		while (presIt.hasNext()) {
			StyleRange nextRange = (StyleRange) presIt.next();
        	int start = nextRange.start;
           	int length = nextRange.length;
            if (start < end) {
        		System.out.println("Unexpected style start " + start + ":" + length + ":" + (start + length) + "/" + charCount);
        		return false;
        	}
        	if (length < 1) {
        		System.out.println("Unexpected style length " + start + ":" + length + ":" + (start + length) + "/" + charCount);
        		return false;
        	}
        	end = start + length;      	
        	if (end > charCount) {
        		System.out.println("Unexpected style end " + start + ":" + length + ":" + (start + length) + "/" + charCount);
        		return false;
        	}
		}
		return true;
	}

	private TextPresentation correctPresentation(TextPresentation presentation, int charCount) {		// EDW
		List<StyleRange> ranges = new ArrayList<StyleRange>();
		Iterator presIt = presentation.getAllStyleRangeIterator();
		for (int styleNumber = 0; presIt.hasNext(); styleNumber++) {
			StyleRange range = (StyleRange) presIt.next();
        	int start = range.start;
        	int length = range.length;
    		if (length <= 0)
        		System.out.println("Ignored -ve length style " + styleNumber + ": " + range.start + ":" + range.length + ":" + (range.start + range.length) + "/" + charCount);
    		else if (start >= charCount)
        		System.out.println("Ignored past-end style " + styleNumber + ": " + range.start + ":" + range.length + ":" + (range.start + range.length) + "/" + charCount);
    		else {
        		int end = start + length;
        		if (end > charCount) {
            		System.out.println("Truncated over-end style " + styleNumber + ": " + range.start + ":" + range.length + ":" + (range.start + range.length) + "/" + charCount);
            		range.length = charCount - start;
        		}
        		ranges.add(range);
        	}
		}
		Collections.sort(ranges, new Comparator<StyleRange>()
		{
			public int compare(StyleRange o1, StyleRange o2) {
				return o1.start - o2.start;
			}
		});
		IDocument document = fSourceViewer.getDocument();
		TextPresentation newPresentation = new TextPresentation();
		int iMax = ranges.size();
		for (int i = 0; i < iMax; i++) {
			StyleRange thisRange = ranges.get(i);
			if ((i+1) < iMax) {
				StyleRange nextRange = ranges.get(i+1);
        		int end = thisRange.start + thisRange.length;
				if (end > nextRange.start) {
            		try {
						System.out.println("Truncated over-lap style " + i + " " +
								thisRange.start + ":" + thisRange.length + ":" + (thisRange.start + thisRange.length) + ":'" + document.get(thisRange.start, thisRange.length) + "' " +
								nextRange.start + ":" + nextRange.length + ":" + (nextRange.start + nextRange.length) + ":'" + document.get(nextRange.start, nextRange.length) + "'");
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
					thisRange.length = nextRange.start - thisRange.start;
				}
			}
			newPresentation.addStyleRange(thisRange);
		}
		return newPresentation;
	}
}
