package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_farm_store")
public class FarmStore extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6399656331026018688L;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="adress")
	private String adress;
	@Column(name="phone_number")
	private String phoneNumber;
	@ManyToOne
	@JoinColumn(name="administrative_unit_id")
	private FmsAdministrativeUnit administrativeUnit;
	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
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
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public FmsAdministrativeUnit getAdministrativeUnit() {
		return administrativeUnit;
	}
	public void setAdministrativeUnit(FmsAdministrativeUnit administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}
	public Farm getFarm() {
		return farm;
	}
	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	public FarmStore() {
		this.setUuidKey(UUID.randomUUID());
	}
}
