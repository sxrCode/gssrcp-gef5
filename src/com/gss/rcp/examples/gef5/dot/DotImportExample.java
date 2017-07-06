/*******************************************************************************
 * Copyright (c) 2010, 2017 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Fabian Steeg   - initial API and implementation (bug #277380)
 *     Tamas Miklossy - minor refactorings
 *
 *******************************************************************************/
package com.gss.rcp.examples.gef5.dot;

import org.eclipse.gef.dot.internal.DotImport;
import org.eclipse.gef.graph.Graph;

/**
 * @author Fabian Steeg (fsteeg)
 * @author anyssen
 */
public final class DotImportExample {

	public static void main(final String[] args) {
		/* Create Graphs based on Graphviz Dot strings */
		DotImport dotImport = new DotImport();
		Graph graph = dotImport.importDot("graph { 1--2 ; 1--3 }").get(0);
		Graph digraph = dotImport.importDot("digraph { 1->2 ; 1->3 }").get(0);

		System.out.println(graph);
		System.out.println(digraph);
	}

}
