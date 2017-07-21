package com.gss.rcp.examples.gef5.geometry.exerc;

import org.eclipse.gef.geometry.euclidean.Angle;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public abstract class AbstractScaleRotateShape {
	private Canvas canvas;
	private Angle rotationAngle = Angle.fromDeg(0);
	private double zoomFactor = 1;

	public AbstractScaleRotateShape(Canvas parent) {
		this.canvas = parent;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public Angle getRotationAngle() {
		return rotationAngle;
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public Point getCenter() {
		return new Point(canvas.getClientArea().width / 2, canvas.getClientArea().height / 2);
	}

	public abstract IGeometry createGeometry();

	public abstract void draw(GC gc);

	public abstract boolean contain(Point point);
}
