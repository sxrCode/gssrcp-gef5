package com.gss.rcp.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Edge.Builder;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef.layout.algorithms.TreeLayoutAlgorithm;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.zest.fx.ZestFxModule;
import org.eclipse.gef.zest.fx.ZestProperties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import javafx.application.Platform;
import javafx.scene.Scene;

public class TreeLayoutScene {

	private static int id = 0;
	protected static final String ID = ZestProperties.CSS_ID__NE;
	protected static final String LABEL = ZestProperties.LABEL__NE;
	protected static final String CSS_CLASS = ZestProperties.CSS_CLASS__NE;
	protected static final String LAYOUT_IRRELEVANT = ZestProperties.LAYOUT_IRRELEVANT__NE;

	protected static String genId() {
		return Integer.toString(id++);
	}

	protected static Edge e(org.eclipse.gef.graph.Node n, org.eclipse.gef.graph.Node m, Object... attr) {
		String label = (String) n.attributesProperty().get(LABEL) + (String) m.attributesProperty().get(LABEL);
		Builder builder = new Edge.Builder(n, m).attr(LABEL, label).attr(ID, genId());
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildEdge();
	}

	protected static Edge e(Graph graph, org.eclipse.gef.graph.Node n, org.eclipse.gef.graph.Node m, Object... attr) {
		Edge edge = e(n, m, attr);
		graph.getEdges().add(edge);
		return edge;
	}

	protected static org.eclipse.gef.graph.Node n(Object... attr) {
		org.eclipse.gef.graph.Node.Builder builder = new org.eclipse.gef.graph.Node.Builder();
		String id = genId();
		builder.attr(ID, id).attr(LABEL, id);
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildNode();
	}

	protected static org.eclipse.gef.graph.Node n(Graph graph, Object... attr) {
		Node node = n(attr);
		graph.getNodes().add(node);
		return node;
	}

	protected IDomain domain;
	protected IViewer viewer;
	protected Graph graph;

	public TreeLayoutScene() {
		// configure application
		Injector injector = Guice.createInjector(createModule());
		domain = injector.getInstance(IDomain.class);
		viewer = domain.getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));
	}

	protected Module createModule() {
		return new ZestFxModule();

	}

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
				Edge e = e(n, n2);
				edges.add(e);
			}
			edges.add(e(root, n));
		}
		return new Graph.Builder().nodes(nodes.toArray(new Node[] {})).edges(edges.toArray(new Edge[] {}))
				.attr(ZestProperties.LAYOUT_ALGORITHM__G, new TreeLayoutAlgorithm()).build();

	}

	public Scene getScene() {
		Scene scene = new Scene(viewer.getCanvas());
		domain.activate();
		Platform.runLater(() -> {
			graph = createGraph();
			viewer.getContents().setAll(Collections.singletonList(graph));
		});
		return scene;
	}

}
