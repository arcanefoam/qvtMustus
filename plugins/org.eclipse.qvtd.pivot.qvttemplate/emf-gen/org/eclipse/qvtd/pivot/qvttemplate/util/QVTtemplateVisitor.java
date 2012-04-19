/**
 * <copyright>
 *
 * Copyright (c) 2010,2011 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 *
 * This code is auto-generated
 * from: /org.eclipse.qvtd.pivot.qvttemplate/model/QVTtemplate.ecore
 * by: org.eclipse.ocl.examples.build.acceleo.GenerateVisitor
 * defined by: org.eclipse.ocl.examples.build.acceleo.generateVisitors.mtl
 * invoked by: org.eclipse.ocl.examples.build.utilities.*
 * from: org.eclipse.ocl.examples.build.*.mwe2
 *
 * Do not edit it.
 *
 * $Id$
 */
package	org.eclipse.qvtd.pivot.qvttemplate.util;

/**
 */
public interface QVTtemplateVisitor<R> extends org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor<R>
{
	R visitCollectionTemplateExp(org.eclipse.qvtd.pivot.qvttemplate.CollectionTemplateExp object);
	R visitObjectTemplateExp(org.eclipse.qvtd.pivot.qvttemplate.ObjectTemplateExp object);
	R visitPropertyTemplateItem(org.eclipse.qvtd.pivot.qvttemplate.PropertyTemplateItem object);
	R visitTemplateExp(org.eclipse.qvtd.pivot.qvttemplate.TemplateExp object);
}
