package com.globits.wl.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.security.dto.UserDto;
import com.globits.wl.domain.FmsAdministrativeUnitSystemMessage;

public class FmsAdministrativeUnitSystemMessageDto extends BaseObjectDto{
	
	
	private SystemMessageDto systemMessage;
	
	private FmsAdministrativeUnitDto administrativeUnit;

	public SystemMessageDto getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(SystemMessageDto systemMessage) {
		this.systemMessage = systemMessage;
	}

	public FmsAdministrativeUnitDto getAdministrativeUnit() {
		return administrativeUnit;
	}

	public void setAdministrativeUnit(FmsAdministrativeUnitDto administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}

	public FmsAdministrativeUnitSystemMessageDto() {
		super();
	}

	public FmsAdministrativeUnitSystemMessageDto(FmsAdministrativeUnitSystemMessage entity) {
		super();
		if(entity.getSystemMessage()!=null && entity.getSystemMessage().getId()!=null) {
			this.systemMessage= new SystemMessageDto();
			this.systemMessage.setId(entity.getSystemMessage().getId());
			this.systemMessage.setContent(entity.getSystemMessage().getContent());
			this.systemMessage.setRecordId(entity.getSystemMessage().getRecordId());
			this.systemMessage.setRoles(entity.getSystemMessage().getRoles());
			this.systemMessage.setTitle(entity.getSystemMessage().getTitle());
			this.systemMessage.setType(entity.getSystemMessage().getType());
			this.systemMessage.setSender(new UserDto(entity.getSystemMessage().getSender()));
			this.systemMessage.setStatus(entity.getSystemMessage().getStatus());
			
		}
		if(entity.getAdministrativeUnit()!=null && entity.getAdministrativeUnit().getId()!=null) {
			this.administrativeUnit= new FmsAdministrativeUnitDto();
			this.administrativeUnit.setId(entity.getAdministrativeUnit().getId());
		}
	}
	

}
