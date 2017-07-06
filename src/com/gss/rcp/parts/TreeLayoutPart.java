package com.gss.rcp.parts;

import javax.annotation.PostConstruct;

import org.eclipse.gef.fx.swt.canvas.FXCanvasEx;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public class TreeLayoutPart {

	@PostConstruct
	public void createContent(Composite parent) {
		FXCanvasEx fxCanvasEx = new FXCanvasEx(parent, SWT.NONE);
		fxCanvasEx.setScene(new TreeLayoutScene().getScene());
	}
}
