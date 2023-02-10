package com.globits.wl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_administrative_unit_editable")
@XmlRootElement
public class AdministrativeUnitEditable extends BaseObject{

	private static final long serialVersionUID = -3797504023854437947L;

	@ManyToOne
	@JoinColumn(name = "admin_unit_id")
	private FmsAdministrativeUnit adminUnit;
	
	@Column(name = "role_string")
	private String roles;//các role cách nhau bằng dấu ,
	
	@Column(name = "editable",nullable = true)
	private Boolean editable;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "quater")
	private Integer quater;

	public FmsAdministrativeUnit getAdminUnit() {
		return adminUnit;
	}

	public void setAdminUnit(FmsAdministrativeUnit adminUnit) {
		this.adminUnit = adminUnit;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getQuater() {
		return quater;
	}

	public void setQuater(Integer quater) {
		this.quater = quater;
	}
	
	
}
