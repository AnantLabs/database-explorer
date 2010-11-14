package com.gs.dbex.historyMgr.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.gs.dbex.historyMgr.dao.ConnectionPropertiesDao;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Sabuj Das
 *
 */
public class ConnectionPropertiesDaoImpl implements ConnectionPropertiesDao {

	private HibernateTemplate hibernateTemplate;
	
	public ConnectionPropertiesDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public ConnectionProperties saveConnectionProperties(
			ConnectionProperties connectionProperties) {
		if(null == connectionProperties){
			return null;
		}
		if(null == connectionProperties.getConnectionPropId()){
			getHibernateTemplate().save(connectionProperties);
			connectionProperties.getDatabaseConfiguration().setConnectionPropId(connectionProperties.getConnectionPropId());
			getHibernateTemplate().save(connectionProperties.getDatabaseConfiguration());
		} else {
			getHibernateTemplate().saveOrUpdate(connectionProperties);
			getHibernateTemplate().saveOrUpdate(connectionProperties.getDatabaseConfiguration());
		}
		return connectionProperties;
	}

	@Override
	public List<ConnectionProperties> saveAllConnectionProperties(
			List<ConnectionProperties> connectionPropertiesList) {
		if(null == connectionPropertiesList){
			return null;
		}
		getHibernateTemplate().saveOrUpdateAll(connectionPropertiesList);
		return connectionPropertiesList;
	}

}
