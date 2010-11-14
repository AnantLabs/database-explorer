/**
 * 
 */
package com.gs.dbex.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.Check;

import com.gs.dbex.model.cfg.ConnectionProperties;


/**
 * @author sabuj.das
 *
 */
@Entity
@Table(
		name="DBEX_USER", 
		schema="DBEX_CONFIGURATION", 
		uniqueConstraints = {@UniqueConstraint(columnNames={"USER_NAME", "EMAIL_ID"})}
	)
public class User implements Serializable, Comparable<User> {

	private Long userId;
	private String userName;
	private String fullName;
	private String emailAddress;
	private String password;
	private Set<ConnectionProperties> connectionProperties;
	private Integer versionNumber;
	
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name="USER_NAME", nullable=false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="FULL_NAME", nullable=false)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name="EMAIL_ID", nullable=false)
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@OneToMany(targetEntity=ConnectionProperties.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="USER_ID")
    @OrderBy(value="displayOrder")
	public Set<ConnectionProperties> getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(
			Set<ConnectionProperties> connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	@Version
	@Column(name="VERSION_NUMBER")
	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Override
	public int compareTo(User o) {
		return this.userId.compareTo(o.userId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result
				+ ((fullName == null) ? 0 : fullName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		return true;
	}
	
	
}
