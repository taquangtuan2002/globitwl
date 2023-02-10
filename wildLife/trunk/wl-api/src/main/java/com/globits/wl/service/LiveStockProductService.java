package com.globits.wl.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.LiveStockProductDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface LiveStockProductService {

	Page<LiveStockProductDto> getAllWidthPagination(int pageIndex, int pageSize);

	LiveStockProductDto getById(Long id);

	LiveStockProductDto save(LiveStockProductDto dto);

	boolean deleteById(Long id);

	boolean deleteByIds(Set<Long> ids);

	List<LiveStockProductDto> getAll();
	
	Page<LiveStockProductDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize);
	LiveStockProductDto checkDuplicateCode(String code);
}
