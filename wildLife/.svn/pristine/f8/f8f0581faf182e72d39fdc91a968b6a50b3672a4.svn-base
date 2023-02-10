package com.globits.wl.service;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.AnimalRequired;
import com.globits.wl.dto.AnimalRequiredDto;
import com.globits.wl.dto.functiondto.AnimalSearchDto;

public interface AnimalRequiredService extends GenericService<AnimalRequired, Long>{

	Page<AnimalRequiredDto> getPageBySearch(AnimalSearchDto searchDto, int pageIndex, int pageSize);

	AnimalRequiredDto getById(Long id);

	AnimalRequiredDto createAnimalRequired(AnimalRequiredDto dto);

	AnimalRequiredDto updateRequired(AnimalRequiredDto dto);

	AnimalRequiredDto animalRequiredApplyToAnimal(AnimalRequiredDto dto);

	AnimalRequiredDto changeAnimalRequiredStatus(AnimalRequiredDto dto);
	
}
