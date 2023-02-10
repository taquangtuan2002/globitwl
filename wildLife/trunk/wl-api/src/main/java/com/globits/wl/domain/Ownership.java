package com.globits.wl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_ownership")
@XmlRootElement
public class Ownership extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
