package com.gs.dbex.historyMgr.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.gs.dbex.historyMgr.dao.DbexUserDao;
import com.gs.dbex.model.User;
import com.gs.utils.text.StringUtil;

public class DbexUserDaoImpl implements DbexUserDao {

	private HibernateTemplate hibernateTemplate;
	
	public DbexUserDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public User saveUser(User user){
		getHibernateTemplate().saveOrUpdate(user);
		return user;
	}

	@Override
	public User getUser(String userName) {
		if(!StringUtil.hasValidContent(userName))
			return null;
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class)
			.add(Restrictions.eq("userName", userName));
		List userList = getHibernateTemplate().findByCriteria(criteria);
		if(null != userList && userList.size() > 0){
			return (User) userList.get(0);
		}
		return null;
	}
	
}
