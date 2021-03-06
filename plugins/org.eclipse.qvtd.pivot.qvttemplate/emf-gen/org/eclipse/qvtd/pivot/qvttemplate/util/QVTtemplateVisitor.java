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
 * from: model/QVTtemplate.genmodel
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

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

/**
 */
public interface QVTtemplateVisitor<R> extends org.eclipse.qvtd.pivot.qvtbase.util.QVTbaseVisitor<R>
{
	@Nullable R visitCollectionTemplateExp(@NonNull org.eclipse.qvtd.pivot.qvttemplate.CollectionTemplateExp object);
	@Nullable R visitObjectTemplateExp(@NonNull org.eclipse.qvtd.pivot.qvttemplate.ObjectTemplateExp object);
	@Nullable R visitPropertyTemplateItem(@NonNull org.eclipse.qvtd.pivot.qvttemplate.PropertyTemplateItem object);
	@Nullable R visitTemplateExp(@NonNull org.eclipse.qvtd.pivot.qvttemplate.TemplateExp object);
}
