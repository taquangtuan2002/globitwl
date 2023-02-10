package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.InjectionPlantDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface InjectionPlantService extends GenericService<InjectionPlant, Long>{

	Page<InjectionPlantDto> getPageDto(int pageIndex, int pageSize);

	List<InjectionPlantDto> getAllDto();

	InjectionPlantDto getInjectionPlantById(Long id);

	InjectionPlantDto createInjectionPlant(InjectionPlantDto dto);

	InjectionPlantDto updateInjectionPlant(Long id, InjectionPlantDto dto);

	InjectionPlantDto deleteById(Long id);


	boolean deleteByIds(List<Long> ids);

//	Page<InjectionPlantDto> searchInjectionPlantDto(SearchDto dto, int pageIndex, int pageSize);

	




}
