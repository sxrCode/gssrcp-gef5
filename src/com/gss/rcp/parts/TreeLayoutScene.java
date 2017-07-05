package com.gss.rcp.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.text.html.parser.ContentModel;

import org.eclipse.gef4.common.adapt.AdapterKey;
import org.eclipse.gef4.graph.Edge;
import org.eclipse.gef4.graph.Graph;
import org.eclipse.gef4.layout.algorithms.TreeLayoutAlgorithm;
import org.eclipse.gef4.mvc.fx.domain.FXDomain;
import org.eclipse.gef4.mvc.fx.viewer.FXViewer;
import org.eclipse.gef4.zest.fx.ZestFxModule;
import org.eclipse.gef4.zest.fx.ZestProperties;
import org.eclipse.osgi.container.Module;

import com.google.inject.Guice;
import com.google.inject.Injector;

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

	protected static Edge makeEdge(Node n, Node m, Object... attr) {
		String label = (String) n.attributesProperty().get(LABEL) + (String) m.attributesProperty().get(LABEL);
		Builder builder = new Edge.Builder(n, m).attr(LABEL, label).attr(ID, genId());
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildEdge();
	}

	protected static Edge e(Graph graph, Node n, Node m, Object... attr) {
		Edge edge = makeEdge(n, m, attr);
		edge.setGraph(graph);
		graph.getEdges().add(edge);
		return edge;
	}

	protected static Node makeNode(Object... attr) {
		org.eclipse.gef4.graph.Node.Builder builder = new org.eclipse.gef4.graph.Node.Builder();
		String id = genId();
		builder.attr(ID, id).attr(LABEL, id);
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildNode();
	}

	protected static Node makeNode(Graph graph, Object... attr) {
		Node node = makeNode(attr);
		node.setGraph(graph);
		graph.getNodes().add(node);
		return node;
	}

	protected FXDomain domain;
	protected FXViewer viewer;
	protected Graph graph;

	public TreeLayoutScene() {
		Injector injector = Guice.createInjector(createModule());
		domain = injector.getInstance(FXDomain.class);
		viewer = domain.getAdapter(AdapterKey.get(FXViewer.class, FXDomain.CONTENT_VIEWER_ROLE));
	}

	protected Module createModule() {
		return new ZestFxModule();
	}

	protected Graph createGraph() {
		// create nodes
		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		Node root = makeNode(LABEL, "Root");
		nodes.add(root);
		for (int i = 0; i < 3; i++) {
			Node n = makeNode(LABEL, "1 - " + i);
			nodes.add(n);
			for (int j = 0; j < 3; j++) {
				// make these nodes differ via their ids (as the labels are
				// identical)
				Node n2 = makeNode(ID, i + "-" + j, LABEL, "2 - " + j);
				nodes.add(n2);
				Edge e = makeEdge(n, n2);
				edges.add(e);
			}
			edges.add(makeEdge(root, n));
		}
		return new Graph.Builder().nodes(nodes.toArray(new Node[] {})).edges(edges.toArray(new Edge[] {}))
				.attr(ZestProperties.LAYOUT_ALGORITHM__G, new TreeLayoutAlgorithm()).build();

	}

	public Scene getScene() {
		Scene scene = new Scene(viewer.getCanvas());
		// Job job = new Job("time") {
		//
		// @Override
		// protected IStatus run(IProgressMonitor monitor) {
		// graph = createGraph();
		// viewer.getAdapter(ContentModel.class).getContents().setAll(Collections.singletonList(graph));
		// return Status.OK_STATUS;
		// }
		//
		// };
		//
		// job.schedule();

		Platform.runLater(() -> {
			graph = createGraph();
			viewer.getAdapter(ContentModel.class).getContents().setAll(Collections.singletonList(graph));
		});
		domain.activate();
		return scene;
	}

}
