package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface AnimalTypeService extends GenericService<AnimalType, Long> {

	Page<AnimalTypeDto> getAll(int pageIndex, int pageSize);

	AnimalTypeDto getAnimalTypeById(Long id);

	AnimalTypeDto createAnimalType(AnimalTypeDto dto);

	AnimalTypeDto updateAnimalType(Long id,AnimalTypeDto dto);

	AnimalTypeDto deleteById(Long id);

	boolean deleteByIds(List<Long> ids);

	List<AnimalTypeDto> getAllDto();
	Page<AnimalTypeDto> searchAnimalTypeDto(SearchDto searchDto, int pageIndex, int pageSize);
	AnimalTypeDto checkDuplicateCode(String code);
}
