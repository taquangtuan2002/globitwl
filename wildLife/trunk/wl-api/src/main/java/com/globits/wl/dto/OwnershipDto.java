package com.globits.wl.dto;

import com.globits.wl.domain.Ownership;

public class OwnershipDto {
	private Long id;
	private String name;
	private String code;
	private String description;
	private boolean duplicate;
	private String dupCode;
	private String dupName;

	public boolean isDuplicate() {
		return duplicate;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	public String getDupCode() {
		return dupCode;
	}

	public void setDupCode(String dupCode) {
		this.dupCode = dupCode;
	}

	public String getDupName() {
		return dupName;
	}

	public void setDupName(String dupName) {
		this.dupName = dupName;
	}

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

	public OwnershipDto() {
		super();
	}

	public OwnershipDto(Ownership entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.code = entity.getCode();
		this.description = entity.getDescription();
	}

}
