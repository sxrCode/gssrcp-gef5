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
package com.gss.rcp.examples.gef5.geometry.demos;

import org.eclipse.gef.geometry.convert.swt.Geometry2SWT;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.geometry.planar.Region;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import com.gss.rcp.examples.gef5.geometry.AbstractExample;
import com.gss.rcp.examples.gef5.geometry.ControllableShape;

public class RegionClippingExample extends AbstractExample {
	public static void main(String[] args) {
		new RegionClippingExample("Region Clipping Example");
	}

	public RegionClippingExample(String title) {
		super(title);
	}

	@Override
	protected ControllableShape[] getControllableShapes() {
		return new ControllableShape[] { new ControllableShape() {
			{
				addControlPoints(new Point(100, 100), new Point(200, 200));
				addControlPoints(new Point(150, 150), new Point(250, 250));
			}

			@Override
			public Region getShape() {
				Point[] cp = getPoints();
				Region region = new Region(new Rectangle(cp[0], cp[1]), new Rectangle(cp[2], cp[3]));
				return region;
			}

			@Override
			public void onDraw(GC gc) {
				Region region = getShape();

				gc.setClipping(Geometry2SWT.toSWTRegion(region));

				gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				for (int y = 0; y < 800; y += 20) {
					gc.drawString(
							"abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz",
							20, y);
				}

				gc.setClipping((org.eclipse.swt.graphics.Region) null);

				gc.setAlpha(128);
				gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
				for (Rectangle r : region.getShapes()) {
					gc.fillRectangle(Geometry2SWT.toSWTRectangle(r));
				}
				gc.setAlpha(255);
			}
		} };
	}

}
