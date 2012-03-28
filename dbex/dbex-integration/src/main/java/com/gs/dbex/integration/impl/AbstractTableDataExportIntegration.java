/* ******************************************************************************
 * 	
 * 	Name	: AbstractTableDataExportIntegration.java
 * 	Type	: com.gs.dbex.integration.impl.AbstractTableDataExportIntegration
 * 
 * 	Created	: Mar 18, 2012
 * 	
 * 	Author	: Sabuj Das [ mailto::sabuj.das@gmail.com ]
 * 
 * -----------------------------------------------------------------------------*
 * 																				*
 * Copyright © Sabuj Das 2010 All Rights Reserved. 								*
 * <br/>No part of this document may be reproduced without written 				*
 * consent from the author.														*
 * 																				*
 ****************************************************************************** */

package com.gs.dbex.integration.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.TableDataExportIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.vo.XmlColumnVo;
import com.gs.dbex.model.vo.XmlResultsVo;
import com.gs.dbex.model.vo.XmlRowVo;
import com.gs.utils.io.IOUtil;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public abstract class AbstractTableDataExportIntegration implements
		TableDataExportIntegration {

	public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yy HH.mm.ss.SSS aaa";
	
	/**
	 * 
	 */
	public AbstractTableDataExportIntegration() {
		
	}
	
	public abstract String getInsertStringFromRawObject(Object rawObject, int columnType) throws SQLException;
	
	public String getStringFromRawObject(Object rawObject, int columnType) throws SQLException {
		String value = "";
		if(rawObject == null){
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		switch(columnType){
			case Types.SMALLINT:
			case Types.INTEGER:
			case Types.DECIMAL:
			case Types.BIGINT:
			case Types.REAL:
			case Types.DOUBLE:
			case Types.NUMERIC:
				value = rawObject.toString();
				break;
			
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
				java.sql.Timestamp sqlTimestamp = (java.sql.Timestamp) rawObject;
				java.util.Date utilDate = new java.util.Date();
				utilDate.setTime(sqlTimestamp.getTime());
				value = dateFormat.format(utilDate);
				break;
			case Types.CLOB:
				Clob aClob = (Clob) rawObject;
				value = aClob.getSubString(1, (int) aClob.length());
				break;
			case Types.BLOB:
				Blob aBlob = (Blob) rawObject;
				byte[] allBytesInBlob = aBlob.getBytes(1, (int) aBlob.length());
				value = StringUtil.convertToString(allBytesInBlob);
				break;
			default:
				value = rawObject.toString();
				break;
		}
		
		return value;
	}

	
	
	@Override
	public boolean exportToCSV(String schemaName, String tableName,
			ConnectionProperties connectionProperties, String exportQuery,
			File outputFile) throws DbexException {
		Connection connection = null;
		ResultSet resultSet = null;
		
		List<StringBuffer> rowDataList = new ArrayList<StringBuffer>();
		try{
			connection = connectionProperties.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(exportQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = ps.executeQuery();
			
			if(resultSet == null){
				return false;
			}
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			StringBuffer rowData = new StringBuffer("");
			for (int i = 1; i <= columnCount; i++) {
				rowData.append(resultSetMetaData.getColumnName(i));
				if(i != columnCount){
					rowData.append(",");
				}
			}
			rowData.append("\r\n");
			rowDataList.add(rowData);
			
			while(resultSet.next()){
				rowData = new StringBuffer("");
				for(int i = 1; i<= columnCount; i++){
					int columnType = resultSetMetaData.getColumnType(i);
					Object object = resultSet.getObject(i);
					String value = getStringFromRawObject(object, columnType);
					rowData.append(StringUtil.getCsvString(value));
					if(i != columnCount){
						rowData.append(',');
					}
				}
				rowData.append("\r\n");
				rowDataList.add(rowData);
			}
			
		} catch(SQLException e){
			e.printStackTrace();
			throw new DbexException(e);
		} catch(Exception e){
			e.printStackTrace();
			throw new DbexException(e);
		} finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(rowDataList.size() > 0){
			StringBuffer b = new StringBuffer();
			for (StringBuffer sb : rowDataList) {
				b.append(sb);
			}
			IOUtil.writeAsText(outputFile, b.toString());
			
		}
		return true;
	}
	
	
	
	@Override
	public boolean exportToTEXT(String schemaName, String tableName,
			ConnectionProperties connectionProperties, String exportQuery,
			File outputFile, char separator) throws DbexException {
		Connection connection = null;
		ResultSet resultSet = null;
		
		List<StringBuffer> rowDataList = new ArrayList<StringBuffer>();
		try{
			connection = connectionProperties.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(exportQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = ps.executeQuery();
			
			if(resultSet == null){
				return false;
			}
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			StringBuffer rowData = new StringBuffer("");
			for (int i = 1; i <= columnCount; i++) {
				rowData.append(resultSetMetaData.getColumnName(i));
				if(i != columnCount){
					rowData.append("\t");
				}
			}
			rowData.append("\r\n");
			rowDataList.add(rowData);
			
			while(resultSet.next()){
				rowData = new StringBuffer("");
				for(int i = 1; i<= columnCount; i++){
					int columnType = resultSetMetaData.getColumnType(i);
					Object object = resultSet.getObject(i);
					rowData.append(getStringFromRawObject(object, columnType));
					if(i != columnCount){
						rowData.append('\t');
					}
				}
				rowData.append("\r\n");
				rowDataList.add(rowData);
			}
			
		} catch(SQLException e){
			e.printStackTrace();
			throw new DbexException(e);
		} catch(Exception e){
			e.printStackTrace();
			throw new DbexException(e);
		} finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if(rowDataList.size() > 0){
			StringBuffer b = new StringBuffer();
			for (StringBuffer sb : rowDataList) {
				b.append(sb);
			}
			IOUtil.writeAsText(outputFile, b.toString());
			
		}
		return true;
	}

	
	@Override
	public boolean exportToHTML(String schemaName, String tableName,
			ConnectionProperties connectionProperties, String exportQuery,
			File outputFile) throws DbexException {
		Connection connection = null;
		ResultSet resultSet = null;
		StringBuffer rowData = new StringBuffer("<html>\n<head>\n<META http-equiv=\"Content-Type\" content=\"text/html; charset=Cp1252\">\n</head>\n<body><table border=\"1\">\n");
		try{
			connection = connectionProperties.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(exportQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = ps.executeQuery();
			
			if(resultSet == null){
				return false;
			}
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				rowData.append("\t<TH>").append(resultSetMetaData.getColumnName(i)).append("</TH>\n");
			}
			while(resultSet.next()){
				rowData.append("<TR>\n");
				for (int i = 1; i <= columnCount; i++) {
					int columnType = resultSetMetaData.getColumnType(i);
					Object object = resultSet.getObject(i);
					rowData.append("\t<TD>")
						.append(getStringFromRawObject(object, columnType))
						.append("</TD>\n");
				}
				rowData.append("</TR>\n");
			}
			rowData.append("</table></body></html>");
		} catch(SQLException e){
			e.printStackTrace();
			throw new DbexException(e);
		} catch(Exception e){
			e.printStackTrace();
			throw new DbexException(e);
		} finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		IOUtil.writeAsText(outputFile, rowData.toString());
		return true;
	}
	
	@Override
	public boolean exportToXML(String schemaName, String tableName,
			ConnectionProperties connectionProperties, String exportQuery,
			File outputFile) throws DbexException {
		Connection connection = null;
		ResultSet resultSet = null;
		XmlResultsVo resultsVo = new XmlResultsVo();
		try{
			connection = connectionProperties.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(exportQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = ps.executeQuery();
			
			if(resultSet == null){
				return false;
			}
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			while(resultSet.next()){
				XmlRowVo rowVo = new XmlRowVo();
				for (int i = 1; i <= columnCount; i++) {
					int columnType = resultSetMetaData.getColumnType(i);
					String columnName = resultSetMetaData.getColumnName(i);
					Object object = resultSet.getObject(i);
					String value = getStringFromRawObject(object, columnType);
					XmlColumnVo columnVo = new XmlColumnVo();
					columnVo.setColumnName(columnName);
					columnVo.setColumnValue(value);
					rowVo.getXmlColumnList().add(columnVo);
				}
				resultsVo.getXmlRowList().add(rowVo);
			}
		} catch(SQLException e){
			e.printStackTrace();
			throw new DbexException(e);
		} catch(Exception e){
			e.printStackTrace();
			throw new DbexException(e);
		} finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		Mapping mapping = new Mapping();
		
		try {
			mapping.loadMapping(getClass().getResource("/castor/castor-resultset-mapping.xml"));
			Marshaller marshaller = new Marshaller(new OutputStreamWriter(new FileOutputStream(outputFile)));
			marshaller.setMapping(mapping);
			marshaller.marshal(resultsVo);
		} catch (IOException e) {
			e.printStackTrace();
			throw new DbexException(e);
		} catch (MappingException e) {
			e.printStackTrace();
			throw new DbexException(e);
		} catch (MarshalException e) {
			e.printStackTrace();
			throw new DbexException(e);
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new DbexException(e);
		}
		return false;
	}

	
	@Override
	public boolean exportToExcel(String schemaName, String tableName,
			ConnectionProperties connectionProperties, String exportQuery,
			File outputFile) throws DbexException {
		Connection connection = null;
		ResultSet resultSet = null;
		
		HSSFWorkbook wb = new HSSFWorkbook();
	    
		
		List<StringBuffer> rowDataList = new ArrayList<StringBuffer>();
		try{
			connection = connectionProperties.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(exportQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = ps.executeQuery();
			
			if(resultSet == null){
				return false;
			}
			
			HSSFSheet sheet = wb.createSheet("Table Data");
			
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			short rowCount = 0;
			HSSFRow titleRow = sheet.createRow(rowCount);
			rowCount++;
			titleRow.setHeightInPoints((short)35);
			
			for (int i = 1, cellCount=0; i <= columnCount; i++, cellCount++) {
				HSSFCell cell = titleRow.createCell((short)cellCount, HSSFCell.CELL_TYPE_STRING);
				HSSFRichTextString hssfRichTextString = new HSSFRichTextString(resultSetMetaData.getColumnName(i));
				cell.setCellValue(hssfRichTextString);
			}
			
			while(resultSet.next()){
				HSSFRow dataRow = sheet.createRow(rowCount);
				rowCount++;
				titleRow.setHeight((short)25);
				for(int i = 1; i<= columnCount; i++){
					int columnType = resultSetMetaData.getColumnType(i);
					HSSFCell cell = dataRow.createCell((short)(i-1), getPoiCellType(columnType));
					Object object = resultSet.getObject(i);
					HSSFRichTextString hssfRichTextString 
						= new HSSFRichTextString(getStringFromRawObject(object, columnType));
					cell.setCellValue(hssfRichTextString);
				}
			}
			
		} catch(SQLException e){
			e.printStackTrace();
			throw new DbexException(e);
		} catch(Exception e){
			e.printStackTrace();
			throw new DbexException(e);
		} finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		FileOutputStream fileOut = null;
		try{
			fileOut = new FileOutputStream(outputFile);
			wb.write(fileOut);
		} catch (Exception e) {
			throw new DbexException(e);
		}
	    finally{
	    	IOUtil.close(fileOut);
	    }
		
		return true;
	}

	private int getPoiCellType(int columnType){
		int type = HSSFCell.CELL_TYPE_STRING;
		
		switch(columnType){
			case Types.SMALLINT:
			case Types.INTEGER:
			case Types.DECIMAL:
			case Types.BIGINT:
			case Types.REAL:
			case Types.DOUBLE:
			case Types.NUMERIC:
				type = HSSFCell.CELL_TYPE_NUMERIC;
				break;
			
			case Types.DATE:
			case Types.TIME:
			case Types.TIMESTAMP:
			case Types.CLOB:
			case Types.BLOB:
				type = HSSFCell.CELL_TYPE_STRING;
				break;
			default:
				type = HSSFCell.CELL_TYPE_STRING;
				break;
		}
	
		return type;
	}
}
