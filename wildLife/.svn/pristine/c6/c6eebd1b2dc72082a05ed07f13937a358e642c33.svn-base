package com.globits.wl.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_farm_husbandry_method")
public class FarmHusbandryMethod extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name="husbandry_method_id")
	private HusbandryMethod husbandryMethod;

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public HusbandryMethod getHusbandryMethod() {
		return husbandryMethod;
	}

	public void setHusbandryMethod(HusbandryMethod husbandryMethod) {
		this.husbandryMethod = husbandryMethod;
	}
	
}
