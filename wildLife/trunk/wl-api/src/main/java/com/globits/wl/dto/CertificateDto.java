package com.globits.wl.dto;

import com.globits.wl.domain.Certificate;

public class CertificateDto {
	private Long id;
	private String code;
	private String name;
	private String description;
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public CertificateDto() {
		super();
		
	}
	public CertificateDto(Certificate entity) {
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
		if(entity.getDescription()!=null) {
			this.description=entity.getDescription();
		}	
	}
	
}
