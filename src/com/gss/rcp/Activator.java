package com.gss.rcp;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class Activator implements BundleActivator {

	public static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Activator start!");
		configLogback(getContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	private void configLogback(BundleContext bundleContext) throws JoranException, IOException {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		JoranConfigurator joranConfigurator = new JoranConfigurator();
		joranConfigurator.setContext(loggerContext);
		URL bundleUrl = FileLocator.find(Activator.context.getBundle(), new Path("config/logback.xml"), null);
		if (bundleUrl != null) {
			System.out.println(bundleUrl.getFile() + "  " + bundleUrl.getPath());
		} else {
			System.out.println("logback.xml not found!");
			return;
		}
		loggerContext.reset();
		loggerContext.putProperty("LOG_DIR", Platform.getLocation().toOSString() + "/log/" + getLogname());
		joranConfigurator.doConfigure(bundleUrl.openStream());
	}

	private String getLogname() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		return "gssrcp_" + date + ".log";
	}

}
