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
 * $Id: EnumCSAttribution.java,v 1.3 2011/04/25 09:50:02 ewillink Exp $
 */
package org.eclipse.qvtd.pivot.qvtrelation.attributes;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.pivot.Class;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.scoping.AbstractAttribution;
import org.eclipse.ocl.examples.pivot.scoping.EnvironmentView;
import org.eclipse.ocl.examples.pivot.scoping.ScopeView;
import org.eclipse.qvtd.pivot.qvtbase.utilities.QVTbaseUtils;
import org.eclipse.qvtd.pivot.qvtrelation.Key;
import org.eclipse.qvtd.pivot.qvtrelation.RelationalTransformation;

public class KeyAttribution extends AbstractAttribution
{
	public static final KeyAttribution INSTANCE = new KeyAttribution();

	@Override
	public ScopeView computeLookup(EObject target, EnvironmentView environmentView, ScopeView scopeView) {
		Key targetElement = (Key)target;
		RelationalTransformation transformation = targetElement.getTransformation();
		Set<org.eclipse.ocl.examples.pivot.Package> allPackages = QVTbaseUtils.getAllUsedPackages(transformation);
		for (org.eclipse.ocl.examples.pivot.Package usedPackage : allPackages) {
			environmentView.addNamedElement(usedPackage);
		}
		if (!environmentView.hasFinalResult()) {
			MetaModelManager metaModelManager = environmentView.getMetaModelManager();
			for (org.eclipse.ocl.examples.pivot.Package usedPackage : allPackages) {
				environmentView.addNamedElements(metaModelManager.getLocalClasses(usedPackage));
			}
		}
		org.eclipse.ocl.examples.pivot.Class identifies = targetElement.getIdentifies();
		if (identifies != null) {
			MetaModelManager metaModelManager = environmentView.getMetaModelManager();
			environmentView.addNamedElements(metaModelManager.getLocalProperties(identifies, Boolean.FALSE));
		}
		return scopeView.getParent();
	}
}
