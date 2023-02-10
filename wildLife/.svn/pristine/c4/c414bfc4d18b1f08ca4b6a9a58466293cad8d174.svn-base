package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.FmsRegion;
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface FmsRegionService extends GenericService<FmsRegion, Long>{

	Page<FmsRegionDto> getListFmsRegion(int pageIndex, int pageSize);

	FmsRegionDto fmsRegionById(Long id);

	FmsRegionDto saveFmsRegion(FmsRegionDto fmsRegionDto,Long id);

	FmsRegionDto removeFmsRegion(Long id);

	boolean removeLists(List<Long> ids);

	List<FmsRegionDto> getAll();

	Page<FmsRegionDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize);
	FmsRegionDto checkDuplicateCode(String code);
}
