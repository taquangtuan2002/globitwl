package com.globits.wl.dto;

import com.globits.wl.domain.InjectionTime;

public class InjectionTimeDto {
	private Long id;
	private String code;
	private String name;
	private int type;
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public boolean isDuplicate() {
		return isDuplicate;
	}
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
	public String getDupName() {
		return dupName;
	}
	public void setDupName(String dupName) {
		this.dupName = dupName;
	}
	public String getDupCode() {
		return dupCode;
	}
	public void setDupCode(String dupCode) {
		this.dupCode = dupCode;
	}
	public InjectionTimeDto() {
		super();
		
	}
	public InjectionTimeDto(InjectionTime entity) {
		super();
		if(entity!=null) {
			this.id=entity.getId();
		}
		if(entity.getName()!=null) {
			this.name=entity.getName();
		}
		if(entity.getCode()!=null) {
			this.code=entity.getCode();
		}
		if(entity.getType()!=0) {
			this.type=entity.getType();
		}
		
		
	}
	
}
