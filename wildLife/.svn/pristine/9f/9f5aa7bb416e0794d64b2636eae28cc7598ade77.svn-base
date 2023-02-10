package com.globits.wl.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;
@Entity
@Table(name = "tbl_user_administrative_unit")
@XmlRootElement
public class UserAdministrativeUnit extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "admin_unit_id")
	private FmsAdministrativeUnit adminUnit;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public FmsAdministrativeUnit getAdminUnit() {
		return adminUnit;
	}
	public void setAdminUnit(FmsAdministrativeUnit adminUnit) {
		this.adminUnit = adminUnit;
	}
	
}
