/*******************************************************************************
 * Copyright (c) 2014, 2016 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Nyßen (itemis AG) - initial API & implementation
 *
 * Note: Parts of this class have been transferred from org.eclipse.gef.zest.examples.layout.RadialLayoutExample
 *
 *******************************************************************************/
package com.gss.rcp.examples.gef5.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef.zest.fx.ZestProperties;

import com.gss.rcp.examples.gef5.zest.AbstractZestExample;

import javafx.application.Application;

public class RadialLayoutExample extends AbstractZestExample {

	public static void main(String[] args) {
		Application.launch(args);
	}

	public RadialLayoutExample() {
		super("GEF Layouts - Radial Layout Example");
	}

	@Override
	protected Graph createGraph() {
		// create nodes
		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		Node root = n(LABEL, "Root");
		nodes.add(root);
		for (int i = 0; i < 3; i++) {
			Node n = n(LABEL, "1 - " + i);
			nodes.add(n);
			for (int j = 0; j < 3; j++) {
				// make these nodes differ via their ids (as the labels are
				// identical)
				Node n2 = n(ID, i + "-" + j, LABEL, "2 - " + j);
				nodes.add(n2);
				Edge e = e(n, n2, LABEL, "b");
				edges.add(e);
			}
			edges.add(e(root, n, LABEL, "a"));
		}
		return new Graph.Builder().nodes(nodes.toArray(new Node[] {})).edges(edges.toArray(new Edge[] {}))
				.attr(ZestProperties.LAYOUT_ALGORITHM__G, new RadialLayoutAlgorithm()).build();
	}

}
