package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_fms_region")
@XmlRootElement
/*
 * Vùng sinh thái
 * Khái niệm Vùng sinh thái trong chương trình quản lý chăn nuôi
 */
public class FmsRegion  extends BaseObject{
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@Column(name="acreage")
	private double acreage;//Diện tích
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
	public double getAcreage() {
		return acreage;
	}
	public void setAcreage(double acreage) {
		this.acreage = acreage;
	}	
	public FmsRegion() {
		this.setUuidKey(UUID.randomUUID());
	}
}
