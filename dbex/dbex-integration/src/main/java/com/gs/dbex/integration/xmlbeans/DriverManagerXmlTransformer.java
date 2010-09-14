/**
 * 
 */
package com.gs.dbex.integration.xmlbeans;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import com.gs.dbex.bod.dbcfg.ConnectionConfigurationDocument.ConnectionConfiguration;
import com.gs.dbex.bod.dbcfg.DriverManagerDocument;
import com.gs.dbex.bod.dbcfg.JarEntryDocument.JarEntry;
import com.gs.dbex.bod.dbcfg.JdbcDriverDocument.JdbcDriver;
import com.gs.dbex.bod.dbcfg.ServicesDocument.Services;
import com.gs.dbex.bod.dbcfg.impl.DriverManagerDocumentImpl;
import com.gs.dbex.model.cfg.DriverFile;
import com.gs.dbex.model.cfg.JdbcDriverConfiguration;
import com.gs.dbex.model.cfg.JdbcServices;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class DriverManagerXmlTransformer {

	private static Logger logger = Logger.getLogger(DriverManagerXmlTransformer.class);
	
	public List<JdbcDriverConfiguration> getAllJdbcDriverConfiguration(String xmlText){
		if(logger.isDebugEnabled()){
			logger.debug("getAllJdbcDriverConfiguration from XML");
		}
		List<JdbcDriverConfiguration> list = new ArrayList<JdbcDriverConfiguration>();
		
		if(!StringUtil.hasValidContent(xmlText))
			return list;
		DriverManagerDocument driverManagerDocument = null;
		XmlOptions xmloptions = new XmlOptions();
		xmloptions.setDocumentType(XmlBeans.typeForClass(DriverManagerDocumentImpl.class));
		try{
			driverManagerDocument = DriverManagerDocument.Factory.parse(xmlText, xmloptions);
			if(driverManagerDocument.getDriverManager() != null){
				JdbcDriver[] driverArray = driverManagerDocument.getDriverManager().getJdbcDriverArray();
				if(null != driverArray && driverArray.length > 0){
					for (JdbcDriver driver : driverArray) {
						if(driver != null){
							JdbcDriverConfiguration driverConfiguration = new JdbcDriverConfiguration();
							driverConfiguration.setDatabaseName(driver.getName().toString());
							driverConfiguration.setConnectionConfiguration(populateConnectionConfiguration(driver.getConnectionConfiguration()));
							driverConfiguration.setDriverFile(populateDriverFile(driver.getJarEntry()));
							driverConfiguration.setJdbcServices(populateJdbcServices(driver.getServices()));
							list.add(driverConfiguration);
						}
					}
				}
			}
		} catch (XmlException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	private JdbcServices populateJdbcServices(Services services) {
		if(services == null)
			return null;
		JdbcServices jdbcServices = new JdbcServices();
		String classNames[] = services.getClassNameArray();
		if(classNames != null && classNames.length > 0){
			for (String cn : classNames) {
				jdbcServices.getClassNameList().add(cn);
			}
		}
		return jdbcServices;
	}

	private DriverFile populateDriverFile(JarEntry jarEntry) {
		return null;
	}

	private com.gs.dbex.model.cfg.ConnectionConfiguration populateConnectionConfiguration(
			ConnectionConfiguration connCfg) {
		return null;
	}
}
