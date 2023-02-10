package com.globits.wl.dto;

import com.globits.wl.domain.FarmSize;

public class FarmSizeDto {
	private Long id;
	private String name;
	private String code;
	private int minQuantity;
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public FarmSizeDto() {
		super();
	}
	
	public FarmSizeDto(FarmSize entity) {
		if(entity!=null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();
			this.minQuantity = entity.getMinQuantity();
		}		
	}
}
