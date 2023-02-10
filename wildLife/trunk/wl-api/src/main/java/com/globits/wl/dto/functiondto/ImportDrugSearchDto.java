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

public class ImportDrugSearchDto {
	private Long id;
	private String nameOrCode;
	private Long farmId; //nông trại
	private Date fromDate; //từ ngày
	private Date toDate; //đến ngày
	private Long drugId;//cám
	private Long province;
	private Long district;
	private Long ward;
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Long getDrugId() {
		return drugId;
	}

	public void setDrugId(Long drugId) {
		this.drugId = drugId;
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

}
