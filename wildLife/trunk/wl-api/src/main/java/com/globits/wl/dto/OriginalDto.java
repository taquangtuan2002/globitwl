package com.globits.wl.dto;

import com.globits.wl.domain.Original;

public class OriginalDto {
	private Long id;
	private String name;
	private String code;
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

	public OriginalDto() {
		super();
	}

	public OriginalDto(Original original) {
		super();
		if (original != null) {
			this.id = original.getId();
			this.code = original.getCode();
			this.description = original.getDescription();
			this.name = original.getName();
		}
	}

}
