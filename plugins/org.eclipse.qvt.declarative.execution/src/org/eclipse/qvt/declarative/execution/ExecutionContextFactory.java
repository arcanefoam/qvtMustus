/**
 * <copyright>
 * 
 * Copyright (c) 2007, 2008 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * </copyright>
 * 
 * Contributors:
 *     Quentin Glineur - initial API and implementation
 *
 * $Id: ExecutionContextFactory.java,v 1.2 2008/10/09 17:21:02 qglineur Exp $
 */
package org.eclipse.qvt.declarative.execution;

import java.util.List;

import org.eclipse.qvt.declarative.execution.ExecutionContext.ExecutionMode;
import org.eclipse.qvt.declarative.execution.internal.ExecutionContextFactoryImpl;

public interface ExecutionContextFactory {
	
	public ExecutionContextFactory INSTANCE = ExecutionContextFactoryImpl.getInstance();
	
	public ExecutionContext createExecutionContext(List<LabelledModel> sourceModels,
			LabelledModel directionModel, ExecutionMode mode);

}
