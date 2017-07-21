package com.gss.rcp.examples.gef5.geometry.exerc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractContainmentExample {

	private Shell shell;

	public AbstractContainmentExample() {
		Display display = Display.getDefault();
		this.shell = new Shell(display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public Shell getShell() {
		return this.shell;
	}

	abstract public class AbstractControllableShape {

	}
}
