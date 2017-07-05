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
import org.eclipse.gef.geometry.planar.CubicCurve;
import org.eclipse.gef.geometry.planar.Ellipse;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class EllipseCubicCurveIntersection extends AbstractEllipseIntersectionExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new EllipseCubicCurveIntersection();
	}

	public EllipseCubicCurveIntersection() {
		super("Ellipse/CubicCurve Intersection");
	}

	@Override
	protected Point[] computeIntersections(IGeometry g1, IGeometry g2) {
		return ((Ellipse) g1).getIntersections((CubicCurve) g2);
	}

	@Override
	protected AbstractControllableShape createControllableShape2(Canvas canvas) {
		return new AbstractControllableShape(canvas) {
			@Override
			public void createControlPoints() {
				addControlPoint(new Point(100, 150));
				addControlPoint(new Point(400, 200));
				addControlPoint(new Point(300, 400));
				addControlPoint(new Point(550, 300));
			}

			@Override
			public CubicCurve createGeometry() {
				return new CubicCurve(getControlPoints());
			}

			@Override
			public void drawShape(GC gc) {
				CubicCurve c = createGeometry();
				gc.drawPath(new org.eclipse.swt.graphics.Path(Display.getCurrent(),
						Geometry2SWT.toSWTPathData(c.toPath())));
			}
		};
	}
}
