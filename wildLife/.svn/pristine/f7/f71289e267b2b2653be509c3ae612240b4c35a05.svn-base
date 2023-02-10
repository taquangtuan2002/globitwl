package com.globits.wl.dto;

import com.globits.wl.domain.FmsRegion;

public class FmsRegionDto {

	private Long id;
	private String name;
	private String code;
	private String description;
	private double acreage;
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

	public FmsRegionDto() {
		super();
	}

	public FmsRegionDto(FmsRegion fmsRegion) {
		super();
		if (fmsRegion != null) {
			this.id =fmsRegion.getId();
			this.name = fmsRegion.getName();
			this.code = fmsRegion.getCode();
			this.description = fmsRegion.getDescription();
			this.acreage = fmsRegion.getAcreage();
		}
	}

}
