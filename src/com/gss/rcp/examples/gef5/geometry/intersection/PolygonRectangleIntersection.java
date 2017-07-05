/*******************************************************************************
 * Copyright (c) 2011, 2016 itemis AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 *     
 *******************************************************************************/
package com.gss.rcp.examples.gef5.geometry.intersection;

import org.eclipse.gef.geometry.convert.swt.Geometry2SWT;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Polygon;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class PolygonRectangleIntersection extends AbstractPolygonIntersectionExample {

	public static void main(String[] args) {
		new PolygonRectangleIntersection();
	}

	public PolygonRectangleIntersection() {
		super("Polygon/Rectangle Intersection");
	}

	@Override
	protected Point[] computeIntersections(IGeometry g1, IGeometry g2) {
		return ((Polygon) g1).getOutline().getIntersections(((Rectangle) g2).getOutline());
	}

	@Override
	protected AbstractControllableShape createControllableShape2(Canvas canvas) {
		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				addControlPoint(new Point(100, 150));
				addControlPoint(new Point(550, 300));
			}

			@Override
			public Rectangle createGeometry() {
				Point[] points = getControlPoints();
				return new Rectangle(points[0], points[1]);
			}

			@Override
			public void drawShape(GC gc) {
				Rectangle rect = createGeometry();
				gc.drawRectangle(Geometry2SWT.toSWTRectangle(rect));
			}
		};
	}
}
