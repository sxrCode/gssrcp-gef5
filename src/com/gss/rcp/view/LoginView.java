package com.gss.rcp.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.gss.rcp.util.ImageHelper;

public class LoginView {

	private Shell loginShell;
	private Composite loginPanel;
	private Text fTextUsername;
	private Text fTextPassword;
	private Composite buttonLogin;
	private Composite buttonCancel;

	private boolean fAuthenticated = false;

	private int screenHeight;
	private int screenWeith;

	private Rectangle loginPanelBounds;

	public LoginView(final Shell shell) {
		this.loginShell = shell;
		screenHeight = shell.getDisplay().getBounds().height;
		screenWeith = shell.getDisplay().getBounds().width;
	}

	public void open() {
		createUI();
		addButtonEvent();
		loginShell.layout(true);
		System.out.println("open login view!");
		doEventLoop();
	}

	private void doEventLoop() {
		// loginShell.pack();
		loginShell.open();
		System.out.println("display size: " + loginShell.getDisplay().getBounds().toString());
		System.out.println("panel bounds: " + loginPanelBounds.toString());
		while (fAuthenticated == false) {
			if (loginShell.getDisplay().readAndDispatch() == false) {
				loginShell.getDisplay().sleep();
			}
		}
		loginShell.dispose();
	}

	private void addButtonEvent() {
		if (buttonLogin != null) {
			buttonLogin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					fAuthenticated = true;
				}
			});
		}

		if (buttonCancel != null) {
			buttonCancel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					System.out.println("say no!");
					loginShell.getDisplay().close();
					System.exit(0);
				}
			});
		}

		loginShell.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!fAuthenticated) {
					loginShell.getDisplay().close();
					System.exit(0);
				}

			}
		});
	}

	private void createUI() {
		setLoginShell();
		// Create the login panel
		createUILoginPanel();
	}

	private void setLoginShell() {
		FillLayout layout = new FillLayout();
		loginShell.setLayout(layout);
		loginShell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		loginShell.setBounds((int) (screenWeith * 0.4), (int) (screenHeight * 0.3), (int) (screenWeith * 0.2),
				(int) (screenHeight * 0.4));
	}

	private void createUILoginPanel() {
		loginPanel = new Composite(loginShell, SWT.NONE);
		GridLayout loginPanelLayout = new GridLayout(1, false);
		loginPanel.setLayout(loginPanelLayout);
		loginPanelLayout.horizontalSpacing = 0;
		loginPanelLayout.verticalSpacing = 0;
		loginPanelLayout.marginLeft = 0;
		loginPanelLayout.marginRight = 0;
		loginPanelLayout.marginWidth = 0;
		loginPanel.setBounds(loginShell.getBounds());
		loginPanelBounds = loginPanel.getBounds();
		loginPanel.setBackground(new Color(loginShell.getDisplay(), 235, 242, 249));

		createUICompositeBanner();
		createUILoginContent();
		createUIButton();
	}

	private void createUICompositeBanner() {
		Composite banner = new Composite(loginPanel, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		banner.setSize(loginPanelBounds.width, (int) (loginPanelBounds.height * 0.5));
		System.out.println("loginPanelBounds: " + loginPanelBounds.toString());
		data.minimumHeight = (int) (loginPanelBounds.height * 0.5);
		banner.setLayoutData(data);
		System.out.println("banner: " + banner.getBounds().toString());

		GridLayout gridLayout = new GridLayout(2, true);
		banner.setLayout(gridLayout);
		banner.setBackgroundImage(ImageHelper.getImage(ImageHelper.BANNER));
	}

	private void createUILoginContent() {
		Composite content = new Composite(loginPanel, SWT.NONE);
		content.setBackground(new Color(loginShell.getDisplay(), 235, 242, 249));
		content.setSize(loginPanelBounds.width, (int) (loginPanelBounds.height * 0.3));
		GridData contentLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		contentLayoutData.verticalIndent = 0;
		contentLayoutData.horizontalIndent = 0;
		content.setLayoutData(contentLayoutData);

		GridLayout gridLayout = new GridLayout(4, false);
		content.setLayout(gridLayout);
		System.out.println("content: " + content.getBounds().toString());

		createUIBlank(content, 0.1);
		createUILabelUserName(content, 0.3);
		createUITextUserName(content, 0.4);
		createUIBlank(content, 0.1);

		createUIBlank(content, 0.1);
		createUILabelPassword(content, 0.3);
		createUITextPassword(content, 0.4);
		createUIBlank(content, 0.1);

	}

	private void createUIBlank(Composite parent, double widthPre) {
		Composite blank = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		blank.setLayoutData(data);
		blank.setBackground(new Color(loginShell.getDisplay(), 10, 100, 130));
	}

	private void createUILabelUserName(Composite parent, double widthPre) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = (int) (parent.getBounds().width * widthPre);

		Label label = new Label(composite, SWT.CENTER);
		label.setAlignment(SWT.CENTER);
		label.setText("User name:");
		RowData rowData = new RowData((int) (data.minimumWidth * 0.8), 30);
		label.setLayoutData(rowData);
		label.setBackground(new Color(loginShell.getDisplay(), 220, 220, 220));

		// Configure layout data
		composite.setLayoutData(data);
		composite.setBackground(new Color(loginShell.getDisplay(), 106, 90, 205));
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.center = true;
		layout.wrap = false;
		composite.setLayout(layout);
	}

	private void createUITextUserName(Composite parent, double widthPre) {
		// Create the text widget
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = (int) (parent.getBounds().width * widthPre);
		composite.setLayoutData(data);
		fTextUsername = new Text(composite, SWT.BORDER | SWT.LEAD);
		RowData rowData = new RowData();
		rowData.width = (int) (data.minimumWidth * 0.8);
		fTextUsername.setLayoutData(rowData);
		fTextUsername.setTextLimit(10);
		composite.setLayout(new RowLayout());
		composite.setBackground(new Color(loginShell.getDisplay(), 255, 255, 240));
	}

	private void createUILabelPassword(Composite parent, double widthPre) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = (int) (parent.getBounds().width * widthPre);

		Label label = new Label(composite, SWT.CENTER);
		label.setAlignment(SWT.CENTER);
		label.setText("Password:"); //$NON-NLS-1$
		RowData rowData = new RowData((int) (data.minimumWidth * 0.8), 30);
		label.setLayoutData(rowData);
		label.setBackground(new Color(loginShell.getDisplay(), 220, 220, 220));

		// Configure layout data
		composite.setLayoutData(data);
		composite.setBackground(new Color(loginShell.getDisplay(), 106, 90, 205));
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.center = true;
		layout.wrap = false;
		composite.setLayout(layout);
	}

	private void createUITextPassword(Composite parent, double widthPre) {
		// Create the text widget
		Composite composite = new Composite(parent, SWT.NONE);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = (int) (parent.getBounds().width * widthPre);
		composite.setLayoutData(data);
		fTextPassword = new Text(composite, SWT.BORDER | SWT.LEAD);
		RowData rowData = new RowData();
		rowData.width = (int) (data.minimumWidth * 0.8);
		fTextPassword.setLayoutData(rowData);
		fTextPassword.setTextLimit(10);
		composite.setLayout(new RowLayout());
		composite.setBackground(new Color(loginShell.getDisplay(), 235, 242, 249));
	}

	private void createUIButton() {
		Composite buttonContent = new Composite(loginPanel, SWT.NONE);
		buttonContent.setBackground(new Color(loginShell.getDisplay(), 235, 242, 249));
		buttonContent.setSize(loginPanelBounds.width, (int) (loginPanelBounds.height * 0.2));
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.minimumHeight = (int) (loginPanelBounds.height * 0.2);
		buttonContent.setLayoutData(gridData);

		GridLayout gridLayout = new GridLayout(2, true);
		buttonContent.setLayout(gridLayout);
		createUIButtonOK(buttonContent);
		createUIButtonCancel(buttonContent);
		System.out.println("buttonContent: " + buttonContent.getBounds().toString());
	}

	private void createUIButtonOK(Composite parent) {
		buttonLogin = buildButton(parent);
		Image image = ImageHelper.getImage(ImageHelper.LOGIN_IMG);
		ImageData imageData = image.getImageData().scaledTo(buttonLogin.getBounds().width,
				buttonLogin.getBounds().height);
		buttonLogin.setBackgroundImage(new Image(loginShell.getDisplay(), imageData));
	}

	private void createUIButtonCancel(Composite parent) {
		buttonCancel = buildButton(parent);
		Image image = ImageHelper.getImage(ImageHelper.CANCEL_IMG);
		ImageData imageData = image.getImageData().scaledTo(buttonCancel.getBounds().width,
				buttonCancel.getBounds().height);
		buttonCancel.setBackgroundImage(new Image(loginShell.getDisplay(), imageData));
	}

	private Composite buildButton(Composite parent) {
		Composite button = new Composite(parent, SWT.NONE);
		GridData data = new GridData(GridData.CENTER, GridData.CENTER, true, true);
		data.widthHint = (int) (parent.getBounds().width * 0.326);
		data.heightHint = (int) (parent.getBounds().height * 0.55);
		button.setLayoutData(data);
		button.setSize((int) (parent.getBounds().width * 0.326), (int) (parent.getBounds().height * 0.55));
		return button;
	}
}
