package com.gss.rcp.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
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
	@Optional
	@Named("browser")
	private String url;

	@PostConstruct
	private void createContent(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		this.textInput = new Text(parent, SWT.BORDER);
		this.textInput.setMessage("search");
		this.textInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button = new Button(parent, SWT.PUSH);
		button.setText("search");
		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		if (url != null) {
			browser.setUrl(url);
		} else {
			browser.setUrl("http://www.sina.com.cn/");
		}

	}
}
