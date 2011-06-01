package com.gs.dbex.integration.impl.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.core.SchemaGrabber;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.integration.DependencyIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.ForeignKey;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.dependency.ExportedTableRelation;
import com.gs.dbex.model.dependency.ImportedTableRelation;
import com.gs.dbex.model.dependency.TableDependency;
import com.gs.utils.jdbc.JdbcUtil;

public class OracleDependencyIntegrationImpl implements DependencyIntegration {

	private static final Logger logger = Logger.getLogger(OracleDependencyIntegrationImpl.class);
	private SchemaGrabber dbGrabber;
	
	public OracleDependencyIntegrationImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	public SchemaGrabber getDbGrabber() {
		return dbGrabber;
	}

	public void setDbGrabber(SchemaGrabber dbGrabber) {
		this.dbGrabber = dbGrabber;
	}



	@Override
	public TableDependency generateTableDependency(ConnectionProperties connectionProperties, String tableName) throws DbexException {
		TableDependency dependency = new TableDependency();
		
		
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		
		Table currentTable = getCurrentTable(connectionProperties, tableName);
		
		
		if(currentTable == null){
			return null;
		}
		dependency.setCurrentTable(currentTable);
		
		List<ForeignKey> importedKeys = currentTable.getImportedKeys();
		List<ForeignKey> exportedKeys = currentTable.getExportedKeys();
		List<ImportedTableRelation> importedRelations = new ArrayList<ImportedTableRelation>();
		List<ExportedTableRelation> exportedRelations = new ArrayList<ExportedTableRelation>();
		
		if(importedKeys != null){
			for (ForeignKey impKey : importedKeys) {
				ImportedTableRelation r = new ImportedTableRelation();
				r.setRelationTitle("Imported Relation");
				r.setRelationType("IMPORTED");
				r.setImportedKey(impKey);
				Table importedTable = getCurrentTable(connectionProperties, impKey.getFkTableName());
					/*dbGrabber.grabTable(connection, 
						impKey.getPkTableSchem(), 
						impKey.getPkTableName(), ReadDepthEnum.DEEP);*/
				r.setImportedTable(importedTable);
				r.setForeignColumnName(impKey.getPkColumnName());
				importedRelations.add(r);
			}
		}
		
		if(exportedKeys != null){
			for (ForeignKey expKey : exportedKeys) {
				ExportedTableRelation r = new ExportedTableRelation();
				r.setRelationTitle("Exported Relation");
				r.setRelationType("EXPORTED");
				r.setExportedKey(expKey);
				Table importedTable = getCurrentTable(connectionProperties, expKey.getFkTableName()); 
					
					/*dbGrabber.grabTable(connection, 
						expKey.getFkTableSchem(), 
						expKey.getFkTableName(), ReadDepthEnum.DEEP);*/
				r.setExportedTable(importedTable);
				r.setForeignColumnName(expKey.getFkColumnName());
				exportedRelations.add(r);
			}
		}
		
		dependency.setImportedRelations(importedRelations);
		dependency.setExportedRelations(exportedRelations);
		
		return dependency;
	}


	private Table getCurrentTable(ConnectionProperties connectionProperties,
			String tableName) throws DbexException {
		Table currentTable = null;
		Connection connection = null;
		try {
			if(dbGrabber != null){
				currentTable = dbGrabber.grabTable(connectionProperties, connectionProperties.getDatabaseConfiguration().getSchemaName(), tableName, ReadDepthEnum.DEEP);
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		return currentTable;
	}
	
}
