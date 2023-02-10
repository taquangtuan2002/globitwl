package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_animal_type")
@XmlRootElement
/*
 * Loại vật nuôi
 * VD: Gà, vịt, ngan, ngỗng,...
 */
public class AnimalType extends BaseObject{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7732061859227706512L;

	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "farm_id")
	private Farm farm;
	
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
	public AnimalType() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
