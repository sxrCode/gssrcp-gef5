package com.gss.rcp.parts;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gss.rcp.view.LoginView;

public class SamplePart {

	private Text txtInput;
	private TableViewer tableViewer;
	private Button button;

	private Logger logger = null;

	@Inject
	private EPartService ePartService;

	@Inject
	private EModelService eModelService;

	@Inject
	private ESelectionService eSelectionService;

	@Inject
	private MDirtyable dirty;

	@Inject
	private MApplication mApplication;

	@PostConstruct
	public void createComposite(Composite parent) {
		logger = LoggerFactory.getLogger(SamplePart.class);
		parent.setLayout(new GridLayout(1, false));

		txtInput = new Text(parent, SWT.BORDER);
		txtInput.setMessage("Not found logger!");
		if (logger != null) {
			txtInput.setMessage("Enter text to mark part as dirty");
		}

		txtInput.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dirty.setDirty(true);
			}
		});
		txtInput.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		button = new Button(parent, SWT.PUSH);
		button.setText("test");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dirty.setDirty(false);
				MPart mPart = (MPart) eModelService.find("gssrcp-gef5.com.gss.rcp.parts.BrowerPart", mApplication);
				// MPart mPart =
				// ePartService.createPart("gssrcp-gef5.com.gss.rcp.parts.BrowerPart");
				// ContextInjectionFactory.make();
				if (mPart != null) {
					System.out.println("’“µΩ¡À£∫ " + mPart.getElementId());
					if (mPart.getContext() != null) {
						System.out.println("mPart Context isn't null!");
					}
					mApplication.getContext().set("browser",
							"http://wiki.eclipse.org/Eclipse4/RCP/Modeled_UI/Model_Elements#Overview");
					if (mPart.getObject() == null) {
						System.out.println("Object is null");
					}
					if (!mPart.isVisible()) {
						mPart.setVisible(true);
					}
					mPart.setContributionURI("bundleclass://gssrcp-gef5/com.gss.rcp.parts.BrowerPart");
					ePartService.activate(mPart);
					if (mPart.getObject() instanceof BrowerPart) {
						System.out.println("Object is instanceof BrowerPart");
					}
				}

			}
		});

		tableViewer = new TableViewer(parent);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(createInitialDataModel());
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

		if (eModelService != null && eSelectionService != null && ePartService != null) {
			logger.info("services are ready!");
		}
	}

	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	@Persist
	public void save() {
		dirty.setDirty(false);
	}

	@Inject
	public void test5(Shell sysShell, Display display) {
		System.out.println("loginView open!");
		Shell shell = new Shell(display, SWT.BORDER | SWT.APPLICATION_MODAL);
		LoginView loginView = new LoginView(shell);
		loginView.open();
		sysShell.setMaximized(true);
	}

	private List<String> createInitialDataModel() {
		return Arrays.asList("Sample item 1", "Sample item 2", "Sample item 3", "Sample item 4", "Sample item 5");
	}
}