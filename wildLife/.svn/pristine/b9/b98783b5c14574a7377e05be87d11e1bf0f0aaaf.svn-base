package com.globits.wl.dto;

import java.util.ArrayList;
import java.util.List;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.AdministrativeUnitEditable;

public class AdministrativeUnitEditableDto extends BaseObjectDto {

	private FmsAdministrativeUnitDto adminUnit;

	private String roles;// các role cách nhau bằng dấu ,

	private Boolean editable;

	private Integer year;

	private Integer quater;
	
	List<FmsAdministrativeUnitDto> listSelectedAdminUnit = new ArrayList<FmsAdministrativeUnitDto>();

	public AdministrativeUnitEditableDto() {
		super();
	}

	public AdministrativeUnitEditableDto(AdministrativeUnitEditable entity) {
		super();
		this.id = entity.getId();
		if (entity.getAdminUnit() != null) {
			this.adminUnit = new FmsAdministrativeUnitDto(entity.getAdminUnit());
		}
		this.roles = entity.getRoles();
		this.editable = entity.getEditable();
		this.year = entity.getYear();
		this.quater = entity.getQuater();
	}

	public List<FmsAdministrativeUnitDto> getListSelectedAdminUnit() {
		return listSelectedAdminUnit;
	}

	public void setListSelectedAdminUnit(List<FmsAdministrativeUnitDto> listSelectedAdminUnit) {
		this.listSelectedAdminUnit = listSelectedAdminUnit;
	}

	public FmsAdministrativeUnitDto getAdminUnit() {
		return adminUnit;
	}

	public void setAdminUnit(FmsAdministrativeUnitDto adminUnit) {
		this.adminUnit = adminUnit;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getQuater() {
		return quater;
	}

	public void setQuater(Integer quater) {
		this.quater = quater;
	}

}
