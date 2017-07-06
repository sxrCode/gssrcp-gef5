package com.gss.rcp.parts;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.gss.rcp.examples.gef5.mvc.logo.MvcLogoExample;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;

public class PlattePart {

	@PostConstruct
	public void createContent(Composite parent) {
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		canvas.setScene(getScene());
	}
	
	private Scene getScene() {
		return new MvcLogoExample().provideScene();
	}
}
