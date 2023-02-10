package com.globits.wl.dto.functiondto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import com.globits.core.dto.AdministrativeUnitDto;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmAnimal;
import com.globits.wl.domain.FarmAnimalType;
import com.globits.wl.domain.FarmFileAttachment;
import com.globits.wl.domain.FarmHusbandryType;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.FarmStore;

public class ExportEggSearchDto {
	private Long id;
	private String nameOrCode;
	private Long farmId; //nông trại
	private Date fromDate; //từ ngày
	private Date toDate; //đến ngày
	private Long province;
	private Long district;
	private Long ward;
	
	private int fromMonth;
	private int toMonth;
	private int currentYear;
	private Integer type;
	private int eggType;
	private String batchCode;
	private Long animalId;
	private Long ownershipId;
	
	public Long getAnimalId() {
		return animalId;
	}

	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public int getEggType() {
		return eggType;
	}

	public void setEggType(int eggType) {
		this.eggType = eggType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(int fromMonth) {
		this.fromMonth = fromMonth;
	}

	public int getToMonth() {
		return toMonth;
	}

	public void setToMonth(int toMonth) {
		this.toMonth = toMonth;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOrCode() {
		return nameOrCode;
	}

	public void setNameOrCode(String nameOrCode) {
		this.nameOrCode = nameOrCode;
	}

	public Long getFarmId() {
		return farmId;
	}

	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}

	public Long getWard() {
		return ward;
	}

	public void setWard(Long ward) {
		this.ward = ward;
	}

	public Long getOwnershipId() {
		return ownershipId;
	}

	public void setOwnershipId(Long ownershipId) {
		this.ownershipId = ownershipId;
	}	
	
}
