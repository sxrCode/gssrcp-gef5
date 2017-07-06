package com.gss.rcp.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.gss.rcp.examples.gef5.mvc.logo.MvcLogoExample;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class PlattePart {

	@PostConstruct
	public void createContent(Composite parent) {
		System.out.println("FxtestPart createContent!");
		FXCanvas fxCanvasEx = new FXCanvas(parent, SWT.NONE);
		fxCanvasEx.setScene(createScene());
	}

	private Scene createScene() {
		return new MvcLogoExample().provideScene();
	}
}
