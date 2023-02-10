package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.HusbandryMethod;
import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface HusbandryMethodService extends GenericService<HusbandryMethod, Long> {

	Page<HusbandryMethodDto> getListHusbandryMethods(int pageIndex, int pageSize);

	List<HusbandryMethodDto> getAll();

	HusbandryMethodDto husbandryMethodById(Long id);

	HusbandryMethodDto saveHusbandryMethod(HusbandryMethodDto husbandryMethodDto, Long id);

	HusbandryMethodDto removeHusbandryMethod(Long id);
	
	Page<HusbandryMethodDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize);
	HusbandryMethodDto checkDuplicateCode(String code);
}
