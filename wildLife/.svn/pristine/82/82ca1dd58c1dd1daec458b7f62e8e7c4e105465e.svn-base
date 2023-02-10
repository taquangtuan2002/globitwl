package com.globits.wl.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ForestProductsList;
import com.globits.wl.dto.ForestProductsListDetailDto;
import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;

public interface ForestProductsListService extends GenericService<ForestProductsList, Long>{

	ForestProductsListDto saveOrUpdate(ForestProductsListDto dto);

	ForestProductsListDto getById(Long id);

	Page<ForestProductsListDto> getSearchByPage(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize);

	List<ForestProductsListDto> getAllBySearch(AnimalReportDataSearchDto searchDto);

	Boolean deleteById(Long id);

	Boolean deleteLinkedByExportAnimalId(Long id);
	
	ForestProductsListDto sendEmail(ForestProductsListDto dto);
	
	Set<String> getEmailAddressToSend(ForestProductsListDto dto);

	List<ForestProductsListDetailDto> findAllByFPL(AnimalReportDataSearchDto searchDto);
}
