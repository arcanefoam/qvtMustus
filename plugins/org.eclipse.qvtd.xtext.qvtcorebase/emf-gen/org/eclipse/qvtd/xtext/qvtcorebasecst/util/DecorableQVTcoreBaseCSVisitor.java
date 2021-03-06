/**
 * <copyright>
 * 
 * Copyright (c) 2013 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 * 
 * </copyright>
 *
 * This code is auto-generated
 * from: model/QVTcoreBaseCST.genmodel
 * by: org.eclipse.ocl.examples.build.acceleo.GenerateVisitor
 * defined by: org.eclipse.ocl.examples.build.acceleo.generateVisitors.mtl
 * invoked by: org.eclipse.ocl.examples.build.utilities.*
 * from: org.eclipse.ocl.examples.build.*.mwe2
 *
 * Do not edit it.
 *
 * $Id$
 */
package	org.eclipse.qvtd.xtext.qvtcorebasecst.util;

import org.eclipse.jdt.annotation.NonNull;

/**
 */
public interface DecorableQVTcoreBaseCSVisitor<R> extends QVTcoreBaseCSVisitor<R>, org.eclipse.ocl.examples.xtext.essentialocl.util.DecorableEssentialOCLCSVisitor<R>
{
	void setUndecoratedVisitor(@NonNull org.eclipse.ocl.examples.xtext.base.util.BaseCSVisitor<R> visitor);
}
