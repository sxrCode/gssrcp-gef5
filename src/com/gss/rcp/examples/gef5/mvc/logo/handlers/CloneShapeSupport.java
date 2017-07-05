/*******************************************************************************
 * Copyright (c) 2015, 2017 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API and implementation
 *
 *******************************************************************************/
package com.gss.rcp.examples.gef5.mvc.logo.handlers;

import org.eclipse.gef.geometry.planar.IShape;

import com.gss.rcp.examples.gef5.mvc.logo.model.GeometricShape;
import com.gss.rcp.examples.gef5.mvc.logo.parts.GeometricShapePart;

import javafx.scene.effect.Effect;
import javafx.scene.paint.Paint;

// only applicable for GeometricShapePart
public class CloneShapeSupport extends AbstractCloneContentSupport {

	@Override
	public Object cloneContent() {
		GeometricShape originalShape = getAdaptable().getContent();
		GeometricShape shape = new GeometricShape((IShape) originalShape.getGeometry().getCopy(),
				originalShape.getTransform().getCopy(), copyPaint(originalShape.getFill()),
				copyEffect(originalShape.getEffect()));
		shape.setStroke(copyPaint(originalShape.getStroke()));
		shape.setStrokeWidth(originalShape.getStrokeWidth());
		return shape;
	}

	private Effect copyEffect(Effect effect) {
		// FIXME [JDK-internal]: Do not use deprecated method to copy Effect.
		return effect.impl_copy();
	}

	private Paint copyPaint(Paint paint) {
		// TODO: Verify this is sufficient.
		return Paint.valueOf(paint.toString());
	}

	@Override
	public GeometricShapePart getAdaptable() {
		return (GeometricShapePart) super.getAdaptable();
	}
}
