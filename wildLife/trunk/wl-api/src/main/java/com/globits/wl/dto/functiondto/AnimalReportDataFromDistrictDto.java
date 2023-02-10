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
import com.globits.wl.dto.AnimalReportDataFormDto;

public class AnimalReportDataFromDistrictDto extends AnimalReportDataFormDto {
	private String name;
	private String code;
	private String nameOrCode;
	private Long id;
	private Long parentDistrictId;
	private List<AnimalReportDataFromWardDto> listWard;

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

	public String getNameOrCode() {
		return nameOrCode;
	}

	public void setNameOrCode(String nameOrCode) {
		this.nameOrCode = nameOrCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AnimalReportDataFromWardDto> getListWard() {
		return listWard;
	}

	public void setListWard(List<AnimalReportDataFromWardDto> listWard) {
		this.listWard = listWard;
	}
	public Long getParentDistrictId() {
		return parentDistrictId;
	}

	public void setParentDistrictId(Long parentDistrictId) {
		this.parentDistrictId = parentDistrictId;
	}
}
