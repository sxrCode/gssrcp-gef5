package com.gss.rcp.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class BrowerPart {

	private Text textInput;
	private Browser browser;

	@Inject
	private EPartService partService;

	@PostConstruct
	private void createContent(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		this.textInput = new Text(parent, SWT.BORDER);
		this.textInput.setMessage("search");
		this.textInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button = new Button(parent, SWT.PUSH);
		button.setText("search");
		System.out.println("create browser!");
		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		browser.setUrl("http://www.sina.com.cn/");

		if (partService != null) {
			MPart mPart = partService.createPart("gssrcp-gef5.part.sample");
			System.out.println("new part: " + partService.isPartVisible(mPart));
		}
	}
}
