package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.Drug;
import com.globits.wl.dto.DrugDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface DrugService extends GenericService<Drug, Long> {

	Page<DrugDto> getPageDto(int pageIndex, int pageSize);

	List<DrugDto> getAllDto();

	DrugDto getDrugById(Long id);

	DrugDto deleteById(Long id);

	boolean deleteByIds(List<Long> ids);

	DrugDto createDrug(DrugDto dto);

	DrugDto updateDrug(Long id, DrugDto dto);

	Page<DrugDto> searchDrugDto(SearchDto dto, int pageIndex, int pageSize);

	DrugDto checkDuplicateCode(String code);

}
