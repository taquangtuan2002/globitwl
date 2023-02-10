package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.Bran;
import com.globits.wl.dto.BranDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface BranService extends GenericService<Bran, Long> {

	Page<BranDto> getPageDto(int pageIndex, int pageSize);

	List<BranDto> getAllDto();

	BranDto getBranById(Long id);

	BranDto deleteById(Long id);

	boolean deleteByIds(List<Long> ids);

	BranDto createBran(BranDto dto);

	BranDto updateBran(Long id, BranDto dto);

	Page<BranDto> searchBranDto(SearchDto dto, int pageIndex, int pageSize);

	BranDto checkDuplicateCode(String code);

}
