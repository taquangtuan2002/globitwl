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
@Table(name = "tbl_seed_level")
@XmlRootElement
/*
 * Danh mục cấp giống
 * VD: cấp thường phẩm, cấp ông bà, cấp cụ kỵ...
 */
public class SeedLevel extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7821871376450485086L;

	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;
	
	@Column(name="level")
	private int level;
	
	
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
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public SeedLevel() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
