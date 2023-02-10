package com.globits.wl.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.globits.wl.domain.Original;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface OriginalService {

	Page<OriginalDto> getAllWidthPagination(int pageIndex, int pageSize);

	OriginalDto getOriginalById(Long id);

	OriginalDto saveOriginal(OriginalDto dto);

	boolean deleteOriginById(Long id);

	boolean deleteOriginByIds(Set<Long> ids);

	List<OriginalDto> getAll();
	
	Page<OriginalDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize);
	OriginalDto checkDuplicateCode(String code);
	
	
	Original getOrinalFindById(Long id);
}
