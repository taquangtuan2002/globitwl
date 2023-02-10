package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_certificate")
@XmlRootElement
/*
 * Chứng chỉ
 * VD: 
 */
public class Certificate   extends BaseObject{
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
	public Certificate() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
