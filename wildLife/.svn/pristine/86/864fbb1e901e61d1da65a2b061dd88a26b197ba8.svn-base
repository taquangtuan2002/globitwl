package com.globits.wl.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.ProductTarget;

public class FarmProductTargetDto {
	private Long id;
	private FarmDto farm;	
	private ProductTargetDto productTarget;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FarmDto getFarm() {
		return farm;
	}
	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}
	public ProductTargetDto getProductTarget() {
		return productTarget;
	}
	public void setProductTarget(ProductTargetDto productTarget) {
		this.productTarget = productTarget;
	}
	public FarmProductTargetDto() {
		
	}
	public FarmProductTargetDto(FarmProductTarget entity) {
		if(entity!=null) {
			if(entity.getFarm()!=null) {
				this.farm = new FarmDto();
				this.farm.setId(entity.getFarm().getId());
			}
			if(entity.getProductTarget()!=null) {
				this.productTarget = new ProductTargetDto();
				this.productTarget.setId(entity.getProductTarget().getId());
				this.productTarget.setCode(entity.getProductTarget().getCode());
				this.productTarget.setName(entity.getProductTarget().getName());
			}
		}
	}
}
