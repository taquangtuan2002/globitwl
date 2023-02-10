package com.globits.wl.dto;

import java.util.List;

import javax.persistence.Column;

import com.globits.wl.domain.ProductTarget;

public class ProductTargetDto {
	private Long id;
	private String name;
	private String code;
	private String description;
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;
	private ProductTargetDto parent;

	public ProductTargetDto getParent() {
		return parent;
	}

	public void setParent(ProductTargetDto parent) {
		this.parent = parent;
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

	public ProductTargetDto() {
		super();
	}
	
	public ProductTargetDto(ProductTarget productTarget) {
		super();
		if(productTarget != null) {
			this.id = productTarget.getId();
			this.code = productTarget.getCode();
			this.name = productTarget.getName();
			this.description = productTarget.getDescription();
			if(productTarget.getParent() != null) {
				this.parent = new ProductTargetDto();
				this.parent.setId(productTarget.getParent().getId());
				this.parent.setCode(productTarget.getParent().getCode());
				this.parent.setName(productTarget.getParent().getName());
				this.parent.setDescription(productTarget.getParent().getDescription());
			}
		}
	}
	
}
