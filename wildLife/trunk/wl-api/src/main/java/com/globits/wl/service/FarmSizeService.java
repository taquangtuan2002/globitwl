package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.dto.BuildingDto;
import com.globits.core.service.GenericService;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.FarmSize;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FarmSizeDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface FarmSizeService extends GenericService<FarmSize, Long> {

	Page<FarmSizeDto> getAll(int pageIndex, int pageSize);

	FarmSizeDto getFarmSizeById(Long id);

	FarmSizeDto createFarmSize(FarmSizeDto dto);

	FarmSizeDto updateFarmSize(Long id,FarmSizeDto dto);

	FarmSizeDto deleteById(Long id);

	boolean deleteByIds(List<Long> ids);

	List<FarmSizeDto> getAllDto();
	Page<FarmSizeDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize);
	
	FarmSizeDto checkDuplicateCode(String code);
	
	FarmSizeDto getByQuantity(int quantity);
}
