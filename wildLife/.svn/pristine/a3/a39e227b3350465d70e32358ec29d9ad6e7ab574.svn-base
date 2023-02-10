package com.globits.wl.dto;

import com.globits.wl.domain.WaterSource;

public class WaterSourceDto {

	private Long id;
	private String name;
	private String code;
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

	public WaterSourceDto() {
		super();
	}

	public WaterSourceDto(WaterSource waterSource) {
		if (waterSource != null) {
			this.id = waterSource.getId();
			this.name = waterSource.getName();
			this.code = waterSource.getCode();
		}
	}

}
