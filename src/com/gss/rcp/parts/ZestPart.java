package com.gss.rcp.parts;

import javax.annotation.PostConstruct;

import org.eclipse.gef.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef.zest.fx.jface.IGraphContentProvider;
import org.eclipse.gef.zest.fx.jface.ZestContentViewer;
import org.eclipse.gef.zest.fx.jface.ZestFxJFaceModule;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public class ZestPart {
	
	private ZestContentViewer viewer = null;

	
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
	
	class MyLabelProvider extends LabelProvider implements IColorProvider {
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
		public Color getForeground(Object element) {
			return Display.getCurrent()
					.getSystemColor(((String) element).charAt(0) == 'F' ? SWT.COLOR_BLACK : SWT.COLOR_RED);
		}

		@Override
		public Color getBackground(Object element) {
			return Display.getCurrent()
					.getSystemColor(((String) element).charAt(0) == 'S' ? SWT.COLOR_YELLOW : SWT.COLOR_WHITE);
		}
	}
}
