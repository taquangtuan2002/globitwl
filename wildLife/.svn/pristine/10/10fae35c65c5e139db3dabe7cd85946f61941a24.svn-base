package com.globits.wl.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_farm_husbandry_type")
public class FarmHusbandryType extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "farm_id", nullable = true)
	private Farm farm;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "husbandry_type_id", nullable = true)
	private HusbandryType husbandryType;

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public HusbandryType getHusbandryType() {
		return husbandryType;
	}

	public void setHusbandryType(HusbandryType husbandryType) {
		this.husbandryType = husbandryType;
	}

}
