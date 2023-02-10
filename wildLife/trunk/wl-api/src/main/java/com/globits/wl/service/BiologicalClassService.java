package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import com.globits.wl.dto.BiologicalClassDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface BiologicalClassService {
	Page<BiologicalClassDto> searchByPage(SearchDto dto);
	BiologicalClassDto saveOrUpdate(BiologicalClassDto dto);
	BiologicalClassDto getById(Long id);
	Boolean deleteById(Long id);
	
	List<BiologicalClassDto> saveOrUpdateListImport(List<BiologicalClassDto> list);
}
