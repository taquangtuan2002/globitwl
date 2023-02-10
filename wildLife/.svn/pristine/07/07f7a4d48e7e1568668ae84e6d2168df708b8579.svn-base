package com.globits.wl.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.Link;
import com.globits.wl.domain.Unit;

public class LinkDto extends BaseObjectDto {
	private String name;
	private String code;
	private String description;
	private String hyperLink;
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;
	
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

	public String getHyperLink() {
		return hyperLink;
	}

	public void setHyperLink(String hyperLink) {
		this.hyperLink = hyperLink;
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

	public LinkDto() {
		super();
	}

	public LinkDto(Link link) {
		super();
		if (link != null) {
			this.setId(link.getId());
			this.code = link.getCode();
			this.description = link.getDescription();
			this.name = link.getName();
			this.hyperLink =  link.getHyperLink();
		}
	}

}
