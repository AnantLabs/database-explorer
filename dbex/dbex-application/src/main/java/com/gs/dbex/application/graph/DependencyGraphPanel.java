/**
 * 
 */
package com.gs.dbex.application.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.util.DrawingUtil;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.ForeignKey;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.dependency.ExportedTableRelation;
import com.gs.dbex.model.dependency.ImportedTableRelation;
import com.gs.dbex.model.dependency.TableDependency;

/**
 * @author sabuj.das
 *
 */
public class DependencyGraphPanel extends JPanel {
	
	public static final java.awt.Font DEFAULT_TEXT_FONT =
        new java.awt.Font(java.awt.Font.MONOSPACED,
            java.awt.Font.BOLD, 12);
	private static final int CURRENT_TABLE = 0, IMPORTED_TABLE = 1, EXPORTED_TABLE = 2,
		CONNECTION_ARROW_GAP = 5;
	
	private Font bitstreamFont;
	private Font tahomaFont;
	private Font verdanaFont;
	
	private TableDependency dependency;
	private boolean showCompleteTable = false;
	private Table currentTable;
	
	private int X_Zero;
	private int Y_Zero;
	private int X_Width;
	private int Y_Height;
	private int scale;
	
	private Image imagePk = null;
	private Image imageFk = null;
	
	private Map<String, TableRelationConnection> importedConnectionMap
		= new HashMap<String, TableRelationConnection>();
	private Map<String, TableRelationConnection> exportedConnectionMap
		= new HashMap<String, TableRelationConnection>();
	private Map<String, Point> currentTablePkPointMap
		= new HashMap<String, Point>();
	private Map<String, Point> currentTableFkPointMap
		= new HashMap<String, Point>();
	
	public DependencyGraphPanel(TableDependency dependency) {
		this.dependency = dependency;
		this.currentTable = dependency.getCurrentTable();
		try {
			bitstreamFont = Font.createFont(Font.TRUETYPE_FONT, 
					getClass().getResourceAsStream("/fonts/VeraMono.ttf"));
			bitstreamFont = new Font(bitstreamFont.getFontName(),
					java.awt.Font.BOLD, 11);
			
			tahomaFont = Font.createFont(Font.TRUETYPE_FONT, 
					getClass().getResourceAsStream("/fonts/TAHOMA.TTF"));
			tahomaFont = new Font(tahomaFont.getFontName(),
					java.awt.Font.BOLD, 11);
			
			verdanaFont = Font.createFont(Font.TRUETYPE_FONT, 
					getClass().getResourceAsStream("/fonts/verdana.TTF"));
			verdanaFont = new Font(verdanaFont.getFontName(),
					java.awt.Font.PLAIN, 10);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imagePk = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "PrimaryKeyColumn.gif")).getImage(); 
		imageFk = new ImageIcon(getClass()
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "ForeignKeyColumn.gif")).getImage(); 
			
			
		setBackground(new Color(255, 255, 255));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory
						.createLineBorder(new Color(0, 0, 0)), BorderFactory
						.createMatteBorder(1, 25, 1, 25, new Color(207, 218,
								231))), new LineBorder(
						new Color(153, 153, 255), 1, true)));
		setLayout(new BorderLayout());
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics = (Graphics2D) g;
		Insets i = getBorder().getBorderInsets(this);
		g.setColor(Color.BLACK);
		X_Zero = i.left;
		Y_Zero = i.top;
		X_Width = getWidth() - i.right;
		Y_Height = getHeight() - i.bottom;
		
		graphics.setFont(DEFAULT_TEXT_FONT);
		
		int tableWidth = DrawingUtil.calculateTableWidth(graphics, dependency.getCurrentTable(), showCompleteTable);
		int tableHeight = DrawingUtil.calculateTableHeight(graphics, dependency.getCurrentTable(), showCompleteTable);
		int panelWidth = 0;
		int panelHeight = 0;
		if(panelWidth < tableWidth){
			panelWidth = tableWidth * 5;
		}
		if(panelHeight < tableHeight){
			panelHeight = tableHeight * 2;
		}
		int topMargin = 25;
		setMinimumSize(new Dimension(panelWidth, panelHeight));
		Point currentTableLocation = new Point(
				( getWidth() / 2 ) - (tableWidth / 2), Y_Zero + topMargin);
		Dimension currentTableSize = new Dimension(tableWidth, tableHeight);
		drawTable(graphics, currentTableLocation, currentTableSize, 
				dependency.getCurrentTable(), CURRENT_TABLE);
		
		int importedPanelHeight = Y_Zero + topMargin * 4;
		List<ImportedTableRelation> importedRelations = dependency.getImportedRelations();
		if(null != importedRelations && !importedRelations.isEmpty()){
			int imp_X = X_Zero + topMargin ;
			int imp_Y = Y_Zero + topMargin * 4;
			Point location = new Point(imp_X, imp_Y);
			Dimension size = new Dimension();
			
			for (ImportedTableRelation r : importedRelations) {
				Table t = r.getImportedTable();
				int w = 0, h = 0;
				w = DrawingUtil.calculateTableWidth(graphics, t, showCompleteTable);
				h = DrawingUtil.calculateTableHeight(graphics, t, showCompleteTable);
				size.setSize(w, h);
				drawTable(graphics, location, size, t, IMPORTED_TABLE);
				location.setLocation(imp_X, location.y + h + 5);
				importedPanelHeight += h + 5;
			}
		}
		
		int exportedPanelHeight = Y_Zero + topMargin * 4;
		List<ExportedTableRelation> exportedRelations = dependency.getExportedRelations();
		if(null != exportedRelations && !exportedRelations.isEmpty()){
			int imp_X = X_Width - topMargin ;
			int imp_Y = Y_Zero + topMargin * 4;
			Dimension size = new Dimension();
			int count = 0;
			int x = 0, y = 0;
			for (ExportedTableRelation r : exportedRelations) {
				Table t = r.getExportedTable();
				int w = 0, h = 0;
				w = DrawingUtil.calculateTableWidth(graphics, t, showCompleteTable);
				h = DrawingUtil.calculateTableHeight(graphics, t, showCompleteTable);
				size.setSize(w, h);
				x = imp_X - w;
				if(count == 0){
					y = imp_Y;
				}
				drawTable(graphics, new Point(x,y), size, t, EXPORTED_TABLE);
				y = y + (h + 5);
				exportedPanelHeight += h + 5;
				count++;
			}
		}
		panelHeight = ((importedPanelHeight > exportedPanelHeight)
			? importedPanelHeight : exportedPanelHeight) + topMargin;
		setMinimumSize(new Dimension(panelWidth, panelHeight));
		setPreferredSize(getMinimumSize());
		
		graphics.setFont(tahomaFont);
		graphics.setColor(Color.BLUE);
		graphics.drawString("Imported Tables", 
				X_Zero + topMargin, 
				Y_Zero + topMargin + graphics.getFontMetrics().getHeight());
		graphics.drawString("Exported Tables", 
				X_Width - topMargin - 
					graphics.getFontMetrics().charsWidth("Exported Tables".toCharArray(), 0, "Exported Tables".length()), 
				Y_Zero + topMargin + graphics.getFontMetrics().getHeight());
		
		List<TableRelationConnection> relConnList = new ArrayList<TableRelationConnection>();
		relConnList.addAll(importedConnectionMap.values());
		relConnList.addAll(exportedConnectionMap.values());
		drawRelationConnections(graphics, relConnList);
	}
	
	private void drawRelationConnections(Graphics2D graphics,
			List<TableRelationConnection> relConnList) {
		
		for (TableRelationConnection rc : relConnList) {
			Point s = rc.getRelationPoint();
			RelationConnectionDirection dir = rc.getConnectionDirection();
			Point e = dir.getEndPoint();
			s = dir.getStartPoint();
			graphics.setColor(rc.getConnectionColor());
			if(s != null && e != null){
				graphics.drawLine(s.x, s.y, e.x, e.y);
			}
		}
	}
	
	private void drawExportedRelationConnections(Graphics2D graphics,
			List<TableRelationConnection> relConnList) {
		
	}

	public void drawTable(Graphics graphics, Point location, Dimension size, Table table,
			int tableType){
		// draw the border
		graphics.setColor(ApplicationConstants.TABLE_BORDER_COLOR);
		graphics.drawRect(location.x, location.y, size.width, size.height);
		graphics.setColor(ApplicationConstants.COLUMN_NAMES_BG_COLOR);
		graphics.fillRect(location.x+1, location.y+1, size.width-1, size.height-1);
		// draw the header
		graphics.setColor(ApplicationConstants.TABLE_BORDER_COLOR);
		graphics.drawRect(location.x, location.y, size.width, DrawingUtil.calculateCellHeight(graphics));
		graphics.setColor(ApplicationConstants.TABLE_HEADER_BG_COLOR);
		graphics.fillRect(location.x+1, location.y+1, size.width-1, DrawingUtil.calculateCellHeight(graphics)-1);
		graphics.setColor(ApplicationConstants.TABLE_HEADER_FG_COLOR);
		graphics.drawString(table.getModelName(), location.x+2, 
				location.y+DrawingUtil.calculateCellHeight(graphics)-4);
		// draw the left margin
		graphics.setColor(ApplicationConstants.TABLE_BORDER_COLOR);
		graphics.drawRect(location.x, location.y + DrawingUtil.calculateCellHeight(graphics),
				ApplicationConstants.TABLE_LEFT_MARGIN_WIDTH, size.height-DrawingUtil.calculateCellHeight(graphics));
		graphics.setColor(ApplicationConstants.TABLE_LEFT_MARGIN_BG_COLOR);
		graphics.fillRect(location.x+1, location.y + DrawingUtil.calculateCellHeight(graphics)+1,
				ApplicationConstants.TABLE_LEFT_MARGIN_WIDTH-1, size.height-1-DrawingUtil.calculateCellHeight(graphics));
		
		// draw columns
		int colStart_X = location.x + ApplicationConstants.TABLE_LEFT_MARGIN_WIDTH + 5;
		int colStart_Y = location.y + DrawingUtil.calculateCellHeight(graphics) + 2;
		int cellHeight = DrawingUtil.calculateCellHeight(graphics);
		List<Column> columnList = table.getColumnlist();
		for(int i = 0; i<columnList.size(); i++){
			Column c = columnList.get(i);
			if(! showCompleteTable){
				// for primary key
				if(c.getPrimaryKey()){
					graphics.setColor(ApplicationConstants.COLUMN_NAMES_FG_COLOR);
					graphics.drawString(c.getModelName(), 
							colStart_X + 2, colStart_Y + cellHeight - 4);
					if(c.getPrimaryKey()){
						Point imgLoc = new Point(
								location.x + 2,
								colStart_Y + 2
							);
						graphics.drawImage(imagePk, imgLoc.x, imgLoc.y, null);
						Point relationLineStart = null;
						switch(tableType){
							case CURRENT_TABLE:
								relationLineStart = new Point(
										imgLoc.x-2 + size.width,  
										imgLoc.y + 2);
								currentTablePkPointMap.put(table.getSchemaName() + "." + table.getModelName() + "." + c.getModelName(), relationLineStart);
								break;
							case IMPORTED_TABLE:
								relationLineStart = new Point(
										imgLoc.x-2 + size.width,  
										imgLoc.y + 2);
								List<ForeignKey> exportedKeys = table.getExportedKeys();
								if(exportedKeys != null){
									for (ForeignKey expKey : exportedKeys) {
										if(expKey.getFkTableSchem().equalsIgnoreCase(currentTable.getSchemaName())
												&& expKey.getFkTableName().equalsIgnoreCase(currentTable.getModelName())){
											Point startPoint = currentTableFkPointMap.get(expKey.getFkTableSchem() + "." 
												+ expKey.getFkTableName() + "." + expKey.getFkColumnName());
											if(startPoint != null){
												TableRelationConnection trc = new TableRelationConnection();
												trc.setConnectionColor(ApplicationConstants.EXPORTED_RELATION_LINE_COLOR);
												trc.setRelationPoint(relationLineStart);
												RelationConnectionDirection dir = new RelationConnectionDirection();
												dir.setStartPoint(startPoint);
												dir.setEndPoint(relationLineStart);
												trc.setConnectionDirection(dir);
												importedConnectionMap.put(c.getModelName(), trc);
											}
										}
									}
								}
								break;
							case EXPORTED_TABLE:
								relationLineStart = null;
								break;
						}
					}
					// for foreign key
					if(c.getForeignKey()){
						Point imgLoc = new Point(
								location.x + 2,
								colStart_Y + 2
							);
						graphics.drawImage(imageFk, imgLoc.x, imgLoc.y, null);
						Point relationLineStart = null;
						switch(tableType){
							case CURRENT_TABLE:
								relationLineStart = new Point(
										imgLoc.x-2 ,  
										imgLoc.y + 2);
								currentTableFkPointMap.put(table.getSchemaName() + "." + table.getModelName() + "." + c.getModelName(), relationLineStart);
								break;
							case EXPORTED_TABLE:
								relationLineStart = new Point(
										imgLoc.x-2 ,  
										imgLoc.y + 2);
								List<ForeignKey> importedKeys = table.getImportedKeys();
								if(importedKeys != null){
									for (ForeignKey impKey : importedKeys) {
										if(impKey.getPkTableSchem().equalsIgnoreCase(currentTable.getSchemaName())
												&& impKey.getPkTableName().equalsIgnoreCase(currentTable.getModelName())){
											for(ForeignKey expKey : currentTable.getExportedKeys()){
												if(expKey.getFkColumnName().equalsIgnoreCase(c.getModelName())){
													Point endPoint = currentTablePkPointMap.get(impKey.getPkTableSchem() + "." 
															+ impKey.getPkTableName() + "." + impKey.getPkColumnName());
													if(endPoint != null){
														TableRelationConnection trc = new TableRelationConnection();
														trc.setConnectionColor(ApplicationConstants.IMPORTED_RELATION_LINE_COLOR);
														trc.setRelationPoint(relationLineStart);
														RelationConnectionDirection dir = new RelationConnectionDirection();
														dir.setStartPoint(relationLineStart);
														dir.setEndPoint(endPoint);
														trc.setConnectionDirection(dir);
														exportedConnectionMap.put(table.getSchemaName() + "."+ table.getModelName() + "."+c.getModelName(), trc);
													}
												}
											}
											
										}
									}
								}
								break;
							case IMPORTED_TABLE:
								relationLineStart = null;
								break;
						}
					}
					// if the column is not the last column
					if(i != columnList.size()-1){
						graphics.setColor(ApplicationConstants.TABLE_BORDER_COLOR);
						graphics.drawLine(colStart_X+1, colStart_Y + cellHeight +2, location.x+size.width ,
								colStart_Y + cellHeight +2);
					}
					colStart_Y += cellHeight;
				} else if(c.getForeignKey()){
					graphics.setColor(ApplicationConstants.COLUMN_NAMES_FG_COLOR);
					graphics.drawString(c.getModelName(), 
							colStart_X + 2, colStart_Y + cellHeight - 4);
					if(c.getPrimaryKey()){
						Point imgLoc = new Point(
								location.x + 2,
								colStart_Y + 2
							);
						graphics.drawImage(imagePk, imgLoc.x, imgLoc.y, null);
						Point relationLineStart = null;
						switch(tableType){
							case CURRENT_TABLE:
								relationLineStart = new Point(
										imgLoc.x-2 + size.width,  
										imgLoc.y + 2);
								currentTablePkPointMap.put(table.getSchemaName() + "." + table.getModelName() + "." + c.getModelName(), relationLineStart);
								break;
							case IMPORTED_TABLE:
								relationLineStart = new Point(
										imgLoc.x-2 + size.width,  
										imgLoc.y + 2);
								List<ForeignKey> exportedKeys = table.getExportedKeys();
								if(exportedKeys != null){
									for (ForeignKey expKey : exportedKeys) {
										if(expKey.getFkTableSchem().equalsIgnoreCase(currentTable.getSchemaName())
												&& expKey.getFkTableName().equalsIgnoreCase(currentTable.getModelName())){
											Point startPoint = currentTableFkPointMap.get(expKey.getFkTableSchem() + "." 
												+ expKey.getFkTableName() + "." + expKey.getFkColumnName());
											if(startPoint != null){
												TableRelationConnection trc = new TableRelationConnection();
												trc.setConnectionColor(ApplicationConstants.EXPORTED_RELATION_LINE_COLOR);
												trc.setRelationPoint(relationLineStart);
												RelationConnectionDirection dir = new RelationConnectionDirection();
												dir.setStartPoint(startPoint);
												dir.setEndPoint(relationLineStart);
												trc.setConnectionDirection(dir);
												importedConnectionMap.put(c.getModelName(), trc);
											}
										
										}
									}
								}
								break;
							case EXPORTED_TABLE:
								relationLineStart = null;
								break;
						}
					}
					if(c.getForeignKey()){
						Point imgLoc = new Point(
								location.x + 2,
								colStart_Y + 2
							);
						graphics.drawImage(imageFk, imgLoc.x, imgLoc.y, null);
						Point relationLineStart = null;
						switch(tableType){
							case CURRENT_TABLE:
								relationLineStart = new Point(
										imgLoc.x-2 ,  
										imgLoc.y + 2);
								currentTableFkPointMap.put(table.getSchemaName() + "." + table.getModelName() + "." + c.getModelName(), relationLineStart);
								break;
							case EXPORTED_TABLE:
								relationLineStart = new Point(
										imgLoc.x-2 ,  
										imgLoc.y + 2);
								List<ForeignKey> importedKeys = table.getImportedKeys();
								if(importedKeys != null){
									for (ForeignKey impKey : importedKeys) {
										if(impKey.getPkTableSchem().equalsIgnoreCase(currentTable.getSchemaName())
												&& impKey.getPkTableName().equalsIgnoreCase(currentTable.getModelName())){
											for(ForeignKey expKey : currentTable.getExportedKeys()){
												if(expKey.getFkColumnName().equalsIgnoreCase(c.getModelName())){
													Point endPoint = currentTablePkPointMap.get(impKey.getPkTableSchem() + "." 
															+ impKey.getPkTableName() + "." + impKey.getPkColumnName());
													if(endPoint != null){
														TableRelationConnection trc = new TableRelationConnection();
														trc.setConnectionColor(ApplicationConstants.IMPORTED_RELATION_LINE_COLOR);
														trc.setRelationPoint(relationLineStart);
														RelationConnectionDirection dir = new RelationConnectionDirection();
														dir.setStartPoint(relationLineStart);
														dir.setEndPoint(endPoint);
														trc.setConnectionDirection(dir);
														exportedConnectionMap.put(table.getSchemaName() + "."+ table.getModelName() + "."+c.getModelName(), trc);
													}
												}
											}
										}
									}
								}
								break;
							case IMPORTED_TABLE:
								relationLineStart = null;
								break;
						}
					}
					// if the column is not the last column
					if(i != columnList.size()-1){
						graphics.setColor(ApplicationConstants.TABLE_BORDER_COLOR);
						graphics.drawLine(colStart_X+1, colStart_Y + cellHeight +2, location.x+size.width ,
								colStart_Y + cellHeight +2);
					}
					colStart_Y += cellHeight;
				}
			} 
			
		// show complete table	
			else {
				graphics.setColor(ApplicationConstants.COLUMN_NAMES_FG_COLOR);
				graphics.drawString(c.getModelName(), 
						colStart_X + 2, colStart_Y + cellHeight - 4);
				if(c.getPrimaryKey()){
					Point imgLoc = new Point(
							location.x + 2,
							colStart_Y + 2
						);
					graphics.drawImage(imagePk, imgLoc.x, imgLoc.y, null);
					Point relationLineStart = null;
					switch(tableType){
						case CURRENT_TABLE:
							relationLineStart = new Point(
									imgLoc.x-2 + size.width,  
									imgLoc.y + 2);
							currentTablePkPointMap.put(table.getSchemaName() + "." + table.getModelName() + "." + c.getModelName(), relationLineStart);
							break;
						case IMPORTED_TABLE:
							relationLineStart = new Point(
									imgLoc.x-2 + size.width,  
									imgLoc.y + 2);
							List<ForeignKey> exportedKeys = table.getExportedKeys();
							if(exportedKeys != null){
								for (ForeignKey expKey : exportedKeys) {
									if(expKey.getFkTableSchem().equalsIgnoreCase(currentTable.getSchemaName())
											&& expKey.getFkTableName().equalsIgnoreCase(currentTable.getModelName())){
										Point startPoint = currentTableFkPointMap.get(expKey.getFkTableSchem() + "." 
												+ expKey.getFkTableName() + "." + expKey.getFkColumnName());
										if(startPoint != null){
											TableRelationConnection trc = new TableRelationConnection();
											trc.setConnectionColor(ApplicationConstants.EXPORTED_RELATION_LINE_COLOR);
											trc.setRelationPoint(relationLineStart);
											RelationConnectionDirection dir = new RelationConnectionDirection();
											dir.setStartPoint(startPoint);
											dir.setEndPoint(relationLineStart);
											trc.setConnectionDirection(dir);
											importedConnectionMap.put(c.getModelName(), trc);
										}
									}
								}
							}
							break;
						case EXPORTED_TABLE:
							relationLineStart = null;
							break;
					}
				}
				if(c.getForeignKey()){
					Point imgLoc = new Point(
							location.x + 2,
							colStart_Y + 2
						);
					graphics.drawImage(imageFk, imgLoc.x, imgLoc.y, null);
					Point relationLineStart = null;
					switch(tableType){
						case CURRENT_TABLE:
							relationLineStart = new Point(
									imgLoc.x-2 ,  
									imgLoc.y + 2);
							currentTableFkPointMap.put(table.getSchemaName() + "." + table.getModelName() + "." + c.getModelName(), relationLineStart);
							break;
						case EXPORTED_TABLE:
							relationLineStart = new Point(
									imgLoc.x-2 ,  
									imgLoc.y + 2);
							List<ForeignKey> importedKeys = table.getImportedKeys();
							if(importedKeys != null){
								for (ForeignKey impKey : importedKeys) {
									if(impKey.getPkTableSchem().equalsIgnoreCase(currentTable.getSchemaName())
											&& impKey.getPkTableName().equalsIgnoreCase(currentTable.getModelName())){
										for(ForeignKey expKey : currentTable.getExportedKeys()){
											if(expKey.getFkColumnName().equalsIgnoreCase(c.getModelName())){
												Point endPoint = currentTablePkPointMap.get(impKey.getPkTableSchem() + "." 
														+ impKey.getPkTableName() + "." + impKey.getPkColumnName());
												if(endPoint != null){
													TableRelationConnection trc = new TableRelationConnection();
													trc.setConnectionColor(ApplicationConstants.IMPORTED_RELATION_LINE_COLOR);
													trc.setRelationPoint(relationLineStart);
													RelationConnectionDirection dir = new RelationConnectionDirection();
													dir.setStartPoint(relationLineStart);
													dir.setEndPoint(endPoint);
													trc.setConnectionDirection(dir);
													exportedConnectionMap.put(table.getSchemaName() + "."+ table.getModelName() + "."+c.getModelName(), trc);
												}
											}
										}
									}
								}
							}
							break;
						case IMPORTED_TABLE:
							relationLineStart = null;
							break;
					}
				}
				// if the column is not the last column
				if(i != columnList.size()-1){
					graphics.setColor(ApplicationConstants.TABLE_BORDER_COLOR);
					graphics.drawLine(colStart_X+1, colStart_Y + cellHeight +2, location.x+size.width ,
							colStart_Y + cellHeight +2);
				}
				colStart_Y += cellHeight;
			}
		}
	}

	public TableDependency getDependency() {
		return dependency;
	}

	public void setDependency(TableDependency dependency) {
		this.dependency = dependency;
	}

	public boolean isShowCompleteTable() {
		return showCompleteTable;
	}

	public void setShowCompleteTable(boolean showCompleteTable) {
		this.showCompleteTable = showCompleteTable;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
}
