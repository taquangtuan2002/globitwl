package com.globits.wl.dto.functiondto;

import java.util.List;

import com.globits.wl.dto.Report18Dto;

public class Report18CityDto extends Report18Dto {
	private String name;
	private String code;
	private String nameOrCode;
	private Long id;

	private List<Report18DistrictDto> listDistrict;

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

	public List<Report18DistrictDto> getListDistrict() {
		return listDistrict;
	}

	public void setListDistrict(List<Report18DistrictDto> listDistrict) {
		this.listDistrict = listDistrict;
	}

}
