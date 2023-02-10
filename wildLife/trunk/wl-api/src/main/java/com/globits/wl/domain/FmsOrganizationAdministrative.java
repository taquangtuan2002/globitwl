package com.globits.wl.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_fms_organization_administrative")
@XmlRootElement
public class FmsOrganizationAdministrative extends BaseObject{
	@ManyToOne
	@JoinColumn(name="organization_id")
	private AdministrativeOrganization administrativeOrganization;
	
	@ManyToOne
	@JoinColumn(name="administrativeUnit_id")
	private FmsAdministrativeUnit fmsAdministrativeUnit;

	public AdministrativeOrganization getAdministrativeOrganization() {
		return administrativeOrganization;
	}

	public void setAdministrativeOrganization(AdministrativeOrganization administrativeOrganization) {
		this.administrativeOrganization = administrativeOrganization;
	}

	public FmsAdministrativeUnit getFmsAdministrativeUnit() {
		return fmsAdministrativeUnit;
	}

	public void setFmsAdministrativeUnit(FmsAdministrativeUnit fmsAdministrativeUnit) {
		this.fmsAdministrativeUnit = fmsAdministrativeUnit;
	}
	
}
