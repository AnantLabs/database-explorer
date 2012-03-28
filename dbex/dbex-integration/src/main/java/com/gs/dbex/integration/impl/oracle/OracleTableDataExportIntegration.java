/* ******************************************************************************
 * 	
 * 	Name	: OracleTableDataExportIntegration.java
 * 	Type	: com.gs.dbex.integration.impl.oracle.OracleTableDataExportIntegration
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

package com.gs.dbex.integration.impl.oracle;

import java.io.File;
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

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.impl.AbstractTableDataExportIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.io.IOUtil;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class OracleTableDataExportIntegration extends
		AbstractTableDataExportIntegration {

	private static final String SQL_DATE_FORMAT = "'DD-MON-RR HH.MI.SS.FF AM'";
	private static final String SQL_DATE_FUNCTION = "to_timestamp";
	
	
	public OracleTableDataExportIntegration() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public boolean exportToInsertStatement(String schemaName, String tableName,
			ConnectionProperties connectionProperties, String exportQuery,
			File outputFile) throws DbexException {
		Connection connection = null;
		ResultSet resultSet = null;
		List<StringBuffer> insertStmtList = new ArrayList<StringBuffer>();
		StringBuffer resultBuffer = new StringBuffer("INSERT INTO ")
			.append(schemaName).append(".").append(tableName).append(" ( ");
		try{
			connection = connectionProperties.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(exportQuery,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = ps.executeQuery();
			
			if(resultSet == null){
				return true;
			}
			
			ResultSetMetaData metaData = resultSet.getMetaData();
			
			int columnCount = metaData.getColumnCount();
			for(int i = 1; i<= columnCount; i++){
				resultBuffer.append(metaData.getColumnName(i));
				if(i != columnCount){
					resultBuffer.append(", ");
				}
			}
			resultBuffer.append(" ) VALUES ( "); 
			resultSet.first();
			while(resultSet.next()){
				StringBuffer rowResultBuffer = new StringBuffer(resultBuffer.toString());
				for(int i = 1; i<= columnCount; i++){
					int columnType = metaData.getColumnType(i);
					Object o = resultSet.getObject(i);
					String value = "null";
					if(o != null)
						value = o.toString();
					rowResultBuffer.append(getInsertStringFromRawObject(o, columnType));
					if(i != columnCount){
						rowResultBuffer.append(", ");
					}else{
						rowResultBuffer.append(" );");
					}
				}
				insertStmtList.add(rowResultBuffer);
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
		
		if(insertStmtList.size() > 0){
			StringBuffer b = new StringBuffer();
			for (StringBuffer sb : insertStmtList) {
				b.append(sb).append("\n");
			}
			IOUtil.writeAsText(outputFile, b.toString());
			
		}
		return false;
	}

	public String getInsertStringFromRawObject(Object rawObject, int columnType) throws SQLException {
		String value = "";
		if(rawObject == null){
			return "NULL";
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
				value = SQL_DATE_FUNCTION + "('" +
					dateFormat.format(utilDate) + "', " + SQL_DATE_FORMAT + ")";
				break;
			case Types.CLOB:
				Clob aClob = (Clob) rawObject;
				value = "'" + aClob.getSubString(1, (int) aClob.length()) + "'";
				break;
			case Types.BLOB:
				Blob aBlob = (Blob) rawObject;
				byte[] allBytesInBlob = aBlob.getBytes(1, (int) aBlob.length());
				value = "'" + StringUtil.convertToString(allBytesInBlob) + "'";
				break;
			default:
				value = "'" + rawObject.toString() + "'";
				break;
		}
		
		return value;
	}
	
}
