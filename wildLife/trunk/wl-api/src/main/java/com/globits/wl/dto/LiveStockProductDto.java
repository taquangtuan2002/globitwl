package com.globits.wl.dto;

import com.globits.core.dto.FileDescriptionDto;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.LiveStockProduct;

public class LiveStockProductDto {
	private Long id;
	private AnimalDto animal;
	private String name;
	private String code;
	private UnitDto unitQuantity;//đơn vị tính theo  số lượng
	private UnitDto unitAmount;//đơn vị tính theo  khối lượng
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public AnimalDto getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
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

	public UnitDto getUnitQuantity() {
		return unitQuantity;
	}

	public void setUnitQuantity(UnitDto unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	public UnitDto getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(UnitDto unitAmount) {
		this.unitAmount = unitAmount;
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

	public LiveStockProductDto() {
		super();
	}
	public LiveStockProductDto(LiveStockProduct lsp) {
		super();
		if (lsp != null) {
			this.id = lsp.getId();
			this.code = lsp.getCode();
			this.name = lsp.getName();
			if(lsp.getAnimal()!=null) {
				this.animal=new AnimalDto();
				this.animal.setId(lsp.getAnimal().getId());
				this.animal.setName(lsp.getAnimal().getName());
				this.animal.setCode(lsp.getAnimal().getCode());
			}
			if(lsp.getUnitAmount()!=null) {
				this.unitAmount=new UnitDto();
				this.unitAmount.setId(lsp.getUnitAmount().getId());
				this.unitAmount.setCode(lsp.getUnitAmount().getCode());
				this.unitAmount.setName(lsp.getUnitAmount().getName());
			}
			if(lsp.getUnitQuantity()!=null) {
				this.unitQuantity=new UnitDto();
				this.unitQuantity.setId(lsp.getUnitQuantity().getId());
				this.unitQuantity.setCode(lsp.getUnitQuantity().getCode());
				this.unitQuantity.setName(lsp.getUnitQuantity().getName());
			}
		}
	}
}
