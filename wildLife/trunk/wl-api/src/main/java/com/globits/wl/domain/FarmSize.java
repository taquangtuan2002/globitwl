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
@Table(name = "tbl_farm_size")
@XmlRootElement
/*
 * Quy mô trang trại
 * VD: lớn, nhỏ,...
 */
public class FarmSize extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7821871376450485086L;

	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="min_quantity")
	private int minQuantity;
	
	
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
	
	public int getMinQuantity() {
		return minQuantity;
	}
	public void setMinQuantity(int minQuantity) {
		this.minQuantity = minQuantity;
	}
	
	public FarmSize() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
