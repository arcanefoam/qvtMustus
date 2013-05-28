/**
 *
 * $Id$
 */
package test.middle.HSV2HLS.validation;

import org.eclipse.emf.common.util.EList;

import test.hls.HLSTree.HLSNode;

import test.hsv.HSVTree.HSVNode;

import test.middle.HSV2HLS.HSVNode2HLSNode;

import test.middle.RGB;

/**
 * A sample validator interface for {@link test.middle.HSV2HLS.HSVNode2HLSNode}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface HSVNode2HLSNodeValidator {
	boolean validate();

	boolean validateParent(HSVNode2HLSNode value);
	boolean validateName(String value);

	boolean validateChildren(EList<HSVNode2HLSNode> value);
	boolean validateHsv(HSVNode value);
	boolean validateHls(HLSNode value);
	boolean validateRgb(RGB value);
}