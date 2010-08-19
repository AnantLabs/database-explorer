/**
 * 
 */
package com.gs.dbex;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.dbex.application.frame.DatabaseExplorerFrame;
import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.service.DatabaseConnectionService;
import com.gs.dbex.service.DatabaseMetadataService;
import com.gs.dbex.service.DbexServiceBeanFactory;

/**
 * @author sabuj.das
 * 
 */
public class ApplicationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					new String[] { "/context/dbex-service-context.xml",
							"/context/dbex-integration-context.xml",
							"/context/dbex-historyMgr-context.xml"});
			
			/*ConnectionProperties connectionProperties = new ConnectionProperties();
			connectionProperties.setConnectionName("TestOracle");
			//connectionProperties.setConnectionUrl("jdbc:oracle:thin:hr/hr@localhost:1521/XE");
			connectionProperties.setDatabaseType(DatabaseTypeEnum.ORACLE.getCode());
			connectionProperties.setUserName("hr");
			connectionProperties.setPassword("hr");
			connectionProperties.setHostName("localhost");
			connectionProperties.setPortNumber(1521);
			connectionProperties.setSidServiceName("XE");
			
			connectionProperties.setConnectionName("TestMysql");
			connectionProperties.setDatabaseType(DatabaseTypeEnum.MYSQL.getCode());
			connectionProperties.setUserName("root");
			connectionProperties.setPassword("ROOT");
			connectionProperties.setHostName("localhost");
			connectionProperties.setPortNumber(3306);
			connectionProperties.setDriverClassName("com.mysql.jdbc.Driver"); 
			
			connectionProperties.setConnectionName("OtherDB");
			connectionProperties.setDatabaseType(DatabaseTypeEnum.OTHER.getCode());
			connectionProperties.setUserName("root");
			connectionProperties.setPassword("root");
			connectionProperties.setConnectionUrl("");
			connectionProperties.setDriverClassName("com.mysql.jdbc.Driver");
			
			DatabaseConnectionService connectionService = DbexServiceBeanFactory
					.getBeanFactory().getDatabaseConnectionService();
			DatabaseMetadataService metadataService = DbexServiceBeanFactory
					.getBeanFactory().getDatabaseMetadataService();
			
			if (connectionService != null) {
				System.out.println("service done");
				boolean b = connectionService.connectToDatabase(connectionProperties);
				if(b){
					System.out.println("Connection successful");
					if(metadataService != null){
						Database database = metadataService.getDatabaseDetails(connectionProperties, "hr");
						System.out.println(database.getModelName());
					}
					
				} else{
					System.out.println("Connection Failed");
				}
				connectionService.closeConnection(connectionProperties);
			}*/
			DatabaseExplorerFrame frame = new DatabaseExplorerFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			if (e instanceof DbexException) {
				DbexException de = (DbexException) e;
				if (de.getExceptionCode() != null) {
					System.out.println("CODE: " + de.getExceptionCode());
				} else {
					System.out.println("Message: " + de.getExceptionMessage());
				}
			} else {
				e.printStackTrace();
			}
		}
	}

}
