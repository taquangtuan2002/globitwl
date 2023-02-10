package com.globits.wl.service;

import java.util.List;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.FmsAdministrativeUnitSystemMessage;
import com.globits.wl.dto.FmsAdministrativeUnitSystemMessageDto;

public interface FmsAdministrativeUnitSystemMessageService extends GenericService<FmsAdministrativeUnitSystemMessage, Long>{
	List<FmsAdministrativeUnitSystemMessageDto> saveOrUpdateList(List<FmsAdministrativeUnitSystemMessageDto> listDto);
	List<FmsAdministrativeUnitSystemMessageDto> findAll();
}
