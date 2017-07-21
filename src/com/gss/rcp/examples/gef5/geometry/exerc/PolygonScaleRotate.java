package com.gss.rcp.examples.gef5.geometry.exerc;

import org.eclipse.gef.geometry.convert.swt.Geometry2SWT;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Polygon;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;

public class PolygonScaleRotate extends AbstractScaleRotateExample implements PaintListener {

	private AbstractScaleRotateShape shape;
	private Canvas canvas;

	public PolygonScaleRotate() {
		super("sxr study!");
	}

	public static void main(String[] args) {
		new PolygonScaleRotate();
	}

	private AbstractScaleRotateShape createShape(Canvas canvas) {
		return new AbstractScaleRotateShape(canvas) {

			private Polygon polygon;

			@Override
			public void draw(GC gc) {
				polygon = createGeometry();
				gc.setLineWidth(3);
				gc.drawPolygon(Geometry2SWT.toSWTPointArray(polygon));
			}

			@Override
			public Polygon createGeometry() {
				double w = getCanvas().getClientArea().width;
				double h = getCanvas().getClientArea().height;
				double w2 = w / 2;
				double h2 = h / 2;
				double w5 = w / 5;
				double h5 = h / 5;

				Polygon me = new Polygon(new Point(w2 - w5, h2 - h5), new Point(w2 + w5, h2 - h5),
						new Point(w2 + w5, h2 + h5), new Point(w2 - w5, h2 + h5));

				me.rotateCW(getRotationAngle(), getCenter());
				me.scale(getZoomFactor(), getCenter());

				return me;
			}

			@Override
			public boolean contain(Point point) {
				if (polygon != null) {
					return polygon.contains(point);
				}
				return false;
			}

		};
	}

	@Override
	protected void createContent(Canvas canvas) {
		this.canvas = canvas;
		this.canvas.addPaintListener(this);
		shape = createShape(canvas);

	}

	@Override
	public void paintControl(PaintEvent e) {
		shape.draw(e.gc);
	}

}
