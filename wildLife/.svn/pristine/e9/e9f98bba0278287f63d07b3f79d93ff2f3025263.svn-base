package com.globits.wl.dto;

import javax.persistence.Column;

import com.globits.wl.domain.SeedLevel;

public class SeedLevelDto {

	private Long id;
	private String name;
	private String code;
	private int level;
	private boolean duplicate;
	private String dupName;
	private String dupCode;

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

	public Boolean getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(Boolean duplicate) {
		this.duplicate = duplicate;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public SeedLevelDto() {
		super();
	}

	public SeedLevelDto(SeedLevel seedLevel) {
		if (seedLevel != null) {
			this.id = seedLevel.getId();
			this.name = seedLevel.getName();
			this.code = seedLevel.getCode();
			this.level = seedLevel.getLevel();
		}
	}

}
