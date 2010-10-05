package com.gs.dbex.model.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.gs.dbex.model.vo.cfg.ConnectionPropertiesVO;

/**
 * @author Sabuj Das
 *
 */
public class UserVO implements Serializable {

	private Long userId;
	private String userName;
	private String fullName;
	private String emailAddress;
	private String password;
	private Set<ConnectionPropertiesVO> connectionPropertiesVOs;
	
	public UserVO() {
		connectionPropertiesVOs = new HashSet<ConnectionPropertiesVO>();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<ConnectionPropertiesVO> getConnectionPropertiesVOs() {
		return connectionPropertiesVOs;
	}

	public void setConnectionPropertiesVOs(
			Set<ConnectionPropertiesVO> connectionPropertiesVOs) {
		this.connectionPropertiesVOs = connectionPropertiesVOs;
	}
	
	
}
