package com.gss.rcp.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class ImageHelper {

	private static ImageRegistry registry = null;

	public static String BANNER = "/icons/banner.png";
	public static String ASC_IMG = "/icons/asc_img_12.png";
	public static String LOGIN_IMG = "/icons/login_img.png";
	public static String CANCEL_IMG = "/icons/cancel_img.png";

	public static Image getImage(String imageName) {
		if (registry == null) {
			registry = new ImageRegistry();
		}
		Image result = registry.get(imageName);
		if (result == null) {
			registry.put(imageName, ImageDescriptor.createFromFile(ImageHelper.class, imageName));
			result = registry.get(imageName);
		}
		return result;
	}

	public static void dispose() {
		if (registry != null) {
			registry.dispose();
		}
	}
}
