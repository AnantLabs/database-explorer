/** ***************************************************************************
 *		Oracle GUI	
 *	
 *	File	: WindowUtil.java
 *	Type	: com.gs.dbex.application.util.WindowUtil.java
 *	Date	: Jul 29, 2009	8:15:51 PM
 *
 *	Author	: Sabuj Das | sabuj.das@gmail.com
 *
 *	
 *****************************************************************************/
package com.gs.dbex.application.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * @author Sabuj Das | sabuj.das@gmail.com
 *
 */
public class WindowUtil {

	/**
	 * This method changes the location of the component to move it to
	 * the center of the screen.
	 * @param component
	 */
	public static void bringToCenter(final Component component){
		Dimension originalSize = component.getSize();
		Dimension scrResolution = Toolkit.getDefaultToolkit().getScreenSize();
		Point location = new Point(
				(int)((scrResolution.getWidth() / 2) - (originalSize.getWidth() / 2)),
				(int)((scrResolution.getHeight() / 2) - (originalSize.getHeight() / 2))
			);
		component.setLocation(location);
	}
	
	/**
	 * Changes the location of the child component according to the parent component
	 * to make the child component center to the parent component.
	 * <br><i>Note: </i>The component can be a Frame or Dialog.
	 * @param child
	 * @param parent
	 */
	public static void bringCenterTo(final Component child, final Component parent){
		Dimension childOriginalSize = child.getSize();
		Dimension parentSize = parent.getSize();
		Point parentLocation = parent.getLocation();
		Point location = new Point(
				parentLocation.x + (parentSize.width/2 - childOriginalSize.width/2),
				parentLocation.y + (parentSize.height/2 - childOriginalSize.height/2)
			);
		child.setLocation(location);
	}
	
	
	
}
