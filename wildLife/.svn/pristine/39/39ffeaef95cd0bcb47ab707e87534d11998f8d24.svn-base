
package com.globits.wl.dto;

import com.globits.wl.domain.FmsOrganizationAdministrative;

public class FmsOrganizationAdministrativeDto {
	private AdministrativeOrganizationDto administrativeOrganization;
	private FmsAdministrativeUnitDto fmsAdministrativeUnit;

	public FmsOrganizationAdministrativeDto() {
		
	}
	
	public FmsOrganizationAdministrativeDto(FmsOrganizationAdministrative entity) {
		if(entity.getAdministrativeOrganization() != null) {
			this.administrativeOrganization = new AdministrativeOrganizationDto(entity.getAdministrativeOrganization(), true);
		}
		if(entity.getFmsAdministrativeUnit() != null) {
			this.fmsAdministrativeUnit = new FmsAdministrativeUnitDto(entity.getFmsAdministrativeUnit(), true);
		}
	}

	public AdministrativeOrganizationDto getAdministrativeOrganization() {
		return administrativeOrganization;
	}

	public void setAdministrativeOrganization(AdministrativeOrganizationDto administrativeOrganization) {
		this.administrativeOrganization = administrativeOrganization;
	}

	public FmsAdministrativeUnitDto getFmsAdministrativeUnit() {
		return fmsAdministrativeUnit;
	}

	public void setFmsAdministrativeUnit(FmsAdministrativeUnitDto fmsAdministrativeUnit) {
		this.fmsAdministrativeUnit = fmsAdministrativeUnit;
	}
	
	
}

