/*******************************************************************************
 * Copyright (c) 2015, 2016 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API & implementation
 *
 *******************************************************************************/
package com.gss.rcp.examples.gef5.layout;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.zest.fx.ZestProperties;

import com.gss.rcp.examples.gef5.zest.AbstractZestExample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class FullyMeshedLayoutExample extends AbstractZestExample {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private Graph graph = new Graph();

	public FullyMeshedLayoutExample() {
		super("GEF Layout - Fully Meshed Example");
	}

	@Override
	protected Graph createGraph() {
		ZestProperties.setLayoutAlgorithm(graph, new RadialLayoutAlgorithm());
		return graph;
	}

	@Override
	protected Scene createScene(IViewer viewer) {
		Scene scene = super.createScene(viewer);
		Group overlay = ((InfiniteCanvas) ((IViewer) viewer).getCanvas()).getOverlayGroup();
		Button addNodeButton = new Button("add node");
		overlay.getChildren().add(addNodeButton);
		addNodeButton.setOnAction(new EventHandler<ActionEvent>() {
			private int id = 0;
			private List<org.eclipse.gef.graph.Node> nodes = new ArrayList<>();

			@Override
			public void handle(ActionEvent event) {
				// add node
				Node newNode = n(graph, LABEL, Integer.toString(id++));
				// connect with all other nodes
				for (org.eclipse.gef.graph.Node n : nodes) {
					e(graph, n, newNode);
				}
				nodes.add(newNode);
			}
		});
		return scene;
	}

}
