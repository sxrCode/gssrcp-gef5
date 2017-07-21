package com.gss.rcp.examples.gef5.geometry.exerc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractScaleRotateExample {
	private Shell shell;

	public AbstractScaleRotateExample(String title) {
		Display display = new Display();
		shell = new Shell(display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);
		shell.setText(title);
		shell.setBounds(0, 0, 640, 480);
		shell.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		shell.open();
		createContent(shell);
		shell.redraw();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected abstract void createContent(Canvas canvas);
}
