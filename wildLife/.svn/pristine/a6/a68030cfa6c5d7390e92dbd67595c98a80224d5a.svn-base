package com.globits.wl.dto;

import com.globits.wl.domain.FarmHusbandryType;

public class FarmHusbandryTypeDto {
	private Long id;
	private FarmDto farm;

	private HusbandryTypeDto husbandryType;

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

	public HusbandryTypeDto getHusbandryType() {
		return husbandryType;
	}

	public void setHusbandryType(HusbandryTypeDto husbandryType) {
		this.husbandryType = husbandryType;
	}

	public FarmHusbandryTypeDto() {
		super();
	}

	public FarmHusbandryTypeDto(FarmHusbandryType entity) {
		super();
		if (entity != null) {
			if (entity.getHusbandryType() != null) {
				this.husbandryType = new HusbandryTypeDto(entity.getHusbandryType());
			}
			if(entity.getFarm()!=null) {
				this.farm = new FarmDto();
				this.farm.setId(entity.getFarm().getId());
			}
			this.id = entity.getId();
		}
	}

}
