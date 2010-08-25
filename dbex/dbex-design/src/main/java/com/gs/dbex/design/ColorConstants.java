/**
 * 
 */
package com.gs.dbex.design;

import java.awt.Color;

/**
 * @author Sabuj Das
 *
 */
public interface ColorConstants {

	Color COLUMN_NAMES_FG_COLOR 		= Color.BLACK;
	Color COLUMN_NAMES_BG_COLOR 		= new Color(170,205,249);
	Color TABLE_BORDER_COLOR 			= new Color(63,72,204);
	Color SELECTED_TABLE_BORDER_COLOR 	= new Color(237,28,36);
	Color TABLE_HEADER_BG_COLOR 		= new Color(194, 210, 152);
	Color TABLE_HEADER_FG_COLOR 		= Color.BLACK;
	Color TABLE_LEFT_MARGIN_BG_COLOR 	= new Color(180,215,234);
	Color TABLE_LEFT_MARGIN_FG_COLOR 	= Color.BLACK;
	
	
	Color JOINT_CIRCLE_COLOR 	= new Color(175,62,47);
	Color TABLE_DEPENDENCY_LINE_COLOR 	= JOINT_CIRCLE_COLOR;
	
	Color SELECTED_IR_LINE_COLOR 	= new Color(34,177,76);
	Color SELECTED_IR_JOINT_CIRCLE_COLOR 	= SELECTED_IR_LINE_COLOR;
	Color SELECTED_ER_LINE_COLOR 	= new Color(0,128,255);
	Color SELECTED_ER_JOINT_CIRCLE_COLOR 	= SELECTED_ER_LINE_COLOR;
	
}
