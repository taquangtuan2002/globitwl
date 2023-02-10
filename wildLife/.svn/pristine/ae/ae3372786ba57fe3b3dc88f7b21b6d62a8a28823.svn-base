package com.globits.wl.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.UnitDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface UnitService {

	Page<UnitDto> getAllWidthPagination(int pageIndex, int pageSize);

	UnitDto getUnitById(Long id);

	UnitDto saveUnit(UnitDto dto);

	boolean deleteUnitById(Long id);

	boolean deleteUnitByIds(Set<Long> ids);

	List<UnitDto> getAll();
	
	Page<UnitDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize);
	UnitDto checkDuplicateCode(String code);
}
