package com.globits.wl.dto;

import com.globits.core.dto.BaseObjectDto;

public class FarmHusbandryMethodDto extends BaseObjectDto{
	private FarmDto farm;
	private HusbandryMethodDto husbandryMethod;
	public FarmDto getFarm() {
		return farm;
	}
	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}
	public HusbandryMethodDto getHusbandryMethod() {
		return husbandryMethod;
	}
	public void setHusbandryMethod(HusbandryMethodDto husbandryMethod) {
		this.husbandryMethod = husbandryMethod;
	}
	public FarmHusbandryMethodDto() {
		super();
	}
}
