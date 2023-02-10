package com.globits.wl.service;

import java.util.List;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.SystemMessage;
import com.globits.wl.dto.AnimalRequiredDto;
import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.SystemMessageDto;

public interface SystemMessageService extends GenericService<SystemMessage, Long>{
	SystemMessageDto saveOrUpdate(SystemMessageDto dto);
	List<SystemMessageDto> findAll();
	SystemMessageDto findById(long id);
	SystemMessageDto saveMessageForestProduct(ForestProductsListDto forestProductDto);
	Object getSystemMessageOfTableName(long systemMessageId);
	SystemMessageDto saveMessageAnimalRequired(AnimalRequiredDto animalRequiredDto);
}
