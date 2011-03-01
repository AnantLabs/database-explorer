/**
 * 
 */
package com.gs.dbex.design.util;

import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class DesignUtil{

	
	public static int calculateTableWidth(Graphics graphics, Table table, boolean isComplete){
		int charWidth = graphics.getFontMetrics().charWidth('H');
		int maxLength = table.getModelName().length();
		for (Column column : table.getColumnlist()) {
			if(!isComplete){
				if(column.getPrimaryKey() || column.getForeignKey()){
					String colName = column.getModelName();
					int length = colName.length();
					if(length > maxLength){
						maxLength = length;
					}
				}
			}else{
				String colName = column.getModelName();
				int length = colName.length();
				if(length > maxLength){
					maxLength = length;
				}
			}
		}
		return 30 + (charWidth * maxLength);
	}
	
	public static int calculateTableHeight(Graphics graphics, Table table, boolean isComplete){
		int charHeight = calculateCellHeight(graphics) + 2;
		int lineCount = 1; 
		for (Column c : table.getColumnlist()) {
			if(!isComplete){
				if(c.getPrimaryKey() || c.getForeignKey()){
					lineCount ++;
				}
			}else{
				lineCount ++;
			}
		}
		return charHeight * lineCount;
	}
	
	public static int calculateCellHeight(Graphics graphics){
		return graphics.getFontMetrics().getHeight() + 5;
	}
	
	public static int calculateTextWidth(Graphics graphics, String text){
		int width = 0;
		int charWidth = graphics.getFontMetrics().charWidth('H');
		if(StringUtil.hasValidContent(text)){
			width = charWidth * (text.length() + 1);
		}
		return width;
	}
	
	public static void updateTableColumnWidth(JTable jTable){
		int colCount = jTable.getColumnModel().getColumnCount();
		Graphics g = jTable.getGraphics();
		
		if(colCount > 0){
			for (int i = 0; i < colCount; i++) {
				TableColumn col = jTable.getColumnModel().getColumn(i);
				if(col != null){
					if(null != col.getHeaderValue()){
						String header = col.getHeaderValue().toString();
						int width = 128;
						if(g != null){
							width = DesignUtil.calculateTextWidth(g, header);
						}
						col.setPreferredWidth(width);
					}
					
				}
			}
		}
		jTable.updateUI();
	}
}
