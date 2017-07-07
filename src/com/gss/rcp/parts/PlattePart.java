package com.gss.rcp.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.gef.fx.swt.canvas.FXCanvasEx;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.log.LogService;

import com.gss.rcp.examples.gef5.mvc.logo.MvcLogoExample;

import javafx.scene.Scene;

public class PlattePart {

	@Inject
	private LogService logger;

	@PostConstruct
	public void createContent(Composite parent) {
		logger.log(LogService.LOG_ERROR, "PlattePart createContent!");
		FXCanvasEx canvas = new FXCanvasEx(parent, SWT.NONE);
		canvas.setScene(getScene());
	}

	private Scene getScene() {
		return new MvcLogoExample().provideScene();
	}
}
