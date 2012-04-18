/**
 * <copyright>
 *
 * Copyright (c) 2012 E.D.Willink and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     E.D.Willink - initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.qvtd.xtext.qvtcore.scoping;

import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.examples.pivot.scoping.Attribution;
import org.eclipse.qvtd.xtext.qvtcore.attributes.DomainCSAttribution;
import org.eclipse.qvtd.xtext.qvtcore.attributes.MappingCSAttribution;
import org.eclipse.qvtd.xtext.qvtcore.attributes.PivotCSAttribution;
import org.eclipse.qvtd.xtext.qvtcore.attributes.TopLevelCSAttribution;
import org.eclipse.qvtd.xtext.qvtcore.attributes.TransformationCSAttribution;
import org.eclipse.qvtd.xtext.qvtcorecst.QVTcoreCSTPackage;

public class QVTcoreScoping
{	
	public static void init() {
		Map<EClassifier, Attribution> registry = Attribution.REGISTRY;
		registry.put(QVTcoreCSTPackage.Literals.DOMAIN_CS, DomainCSAttribution.INSTANCE);
		registry.put(QVTcoreCSTPackage.Literals.MAPPING_CS, MappingCSAttribution.INSTANCE);
		registry.put(QVTcoreCSTPackage.Literals.PATTERN_CS, PivotCSAttribution.INSTANCE);
		registry.put(QVTcoreCSTPackage.Literals.TOP_LEVEL_CS, TopLevelCSAttribution.INSTANCE);
		registry.put(QVTcoreCSTPackage.Literals.TRANSFORMATION_CS, TransformationCSAttribution.INSTANCE);
	}
}