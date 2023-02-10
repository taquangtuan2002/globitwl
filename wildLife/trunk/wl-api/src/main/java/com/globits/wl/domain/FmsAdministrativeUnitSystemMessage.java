package com.globits.wl.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name="tbl_fms_administrative_unit_system_message")
@XmlRootElement
public class FmsAdministrativeUnitSystemMessage extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="system_message_id")
	private SystemMessage systemMessage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "administrative_unit_id")
	private FmsAdministrativeUnit administrativeUnit;

	public SystemMessage getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
	}

	public FmsAdministrativeUnit getAdministrativeUnit() {
		return administrativeUnit;
	}

	public void setAdministrativeUnit(FmsAdministrativeUnit administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}

	public FmsAdministrativeUnitSystemMessage() {
		super();
	}
	

}
