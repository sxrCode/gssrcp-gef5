package com.gss.rcp.parts;

import java.util.Collections;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.zest.fx.jface.IGraphAttributesProvider;
import org.eclipse.gef.zest.fx.jface.IGraphContentProvider;
import org.eclipse.gef.zest.fx.jface.ZestContentViewer;
import org.eclipse.gef.zest.fx.jface.ZestFxJFaceModule;
import org.eclipse.gef.zest.fx.parts.NodePart;
import org.eclipse.gef.zest.fx.parts.ZestFxContentPartFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.multibindings.MapBinder;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;


public class ZestPart {
	
	private ZestContentViewer viewer = null;
	private static final String ATTR_CUSTOM = "custom";
	
	@PostConstruct
	public void createContent(Composite parent) {
		System.out.println("ZestPart createContent!");
		
		Button button = new Button(parent, SWT.PUSH);
		button.setText("Reload");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				viewer.setInput(null);
				viewer.setInput(new Object());
			}
		});
		viewer = new ZestContentViewer(new ZestFxJFaceModule());
		viewer.createControl(parent, SWT.NONE);
		viewer.setContentProvider(new MyContentProvider());
		viewer.setLabelProvider(new MyLabelProvider());
		viewer.setLayoutAlgorithm(new SpringLayoutAlgorithm());
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				System.out.println("Selection changed: " + (event.getSelection()));
			}
		});
		viewer.setInput(new Object());
	}
	
	class MyContentProvider implements IGraphContentProvider {
		private Object input;

		private  String first() {
			return "First";
		}

		private  String second() {
			return "Second";
		}

		private  String third() {
			return "Third";
		}

		@Override
		public Object[] getNodes() {
			if (input == null) {
				return new Object[] {};
			}
			return new Object[] { first(), second(), third() };
		}

		public Object[] getAdjacentNodes(Object entity) {
			if (entity.equals(first())) {
				return new Object[] { second() };
			}
			if (entity.equals(second())) {
				return new Object[] { third() };
			}
			if (entity.equals(third())) {
				return new Object[] { first() };
			}
			return null;
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			input = newInput;
		}

		@Override
		public Object[] getNestedGraphNodes(Object node) {
			return null;
		}

		@Override
		public boolean hasNestedGraph(Object node) {
			return false;
		}
	}
	
	class MyLabelProvider extends LabelProvider implements IGraphAttributesProvider {
		public Image getImage(Object element) {
			return Display.getCurrent().getSystemImage(SWT.ICON_WARNING);
		}

		public String getText(Object element) {
			if (element instanceof String) {
				return element.toString();
			}
			return null;
		}

		@Override
		public Map<String, Object> getEdgeAttributes(Object sourceNode, Object targetNode) {
			return null;
		}

		@Override
		public Map<String, Object> getNodeAttributes(Object node) {
			if (node.toString().startsWith("T")) {
				return Collections.singletonMap(ATTR_CUSTOM, null);
			}
			return null;
		}

		@Override
		public Map<String, Object> getGraphAttributes() {
			return null;
		}

		@Override
		public Map<String, Object> getNestedGraphAttributes(Object nestingNode) {
			return null;
		}
	}
	
	class CustomContentPartFactory extends ZestFxContentPartFactory {
		@Inject
		private Injector injector;

		@Override
		public IContentPart<? extends Node> createContentPart(Object content, Map<Object, Object> contextMap) {
			if (content instanceof org.eclipse.gef.graph.Node) {
				// create custom node if we find the custom attribute
				org.eclipse.gef.graph.Node n = (org.eclipse.gef.graph.Node) content;
				if (n.attributesProperty().containsKey(ATTR_CUSTOM)) {
					CustomNodeContentPart part = new CustomNodeContentPart();
					if (part != null) {
						injector.injectMembers(part);
					}
					return part;
				}
			}
			return super.createContentPart(content, contextMap);
		}
	}
	
	class CustomModule extends ZestFxJFaceModule {
		protected void bindContentPartFactoryAsContentViewerAdapter(MapBinder<AdapterKey<?>, Object> adapterMapBinder) {
			adapterMapBinder.addBinding(AdapterKey.defaultRole()).to(CustomContentPartFactory.class);
		}
	}
	
	class CustomNodeContentPart extends NodePart {
		private VBox vbox;
		private Text labelText;

		@Override
		protected Group doCreateVisual() {
			ImageView ian = new ImageView(
					new javafx.scene.image.Image(getClass().getResource("ibull.jpg").toExternalForm()));
			Polyline body = new Polyline(0, 0, 0, 60, 25, 90, 0, 60, -25, 90, 0, 60, 0, 25, 25, 0, 0, 25, -25, 0);
			body.setTranslateX(ian.getLayoutBounds().getWidth() / 2 - body.getLayoutBounds().getWidth() / 2 - 5);
			body.setTranslateY(-15);
			labelText = new Text();
			vbox = new VBox();
			vbox.getChildren().addAll(ian, body, labelText);
			return new Group(vbox);
		}

		@Override
		protected Text getLabelText() {
			return labelText;
		}
	}
}
