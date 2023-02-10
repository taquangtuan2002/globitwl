package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.HusbandryType;
import com.globits.wl.dto.HusbandryTypeDto;

public interface HusbandryTypeService extends GenericService<HusbandryType, Long>{

	Page<HusbandryTypeDto> getListHusbandryTypes(int pageIndex, int pageSize);

	List<HusbandryTypeDto> getAll();

	HusbandryTypeDto husbandryTypeById(Long id);

	HusbandryTypeDto saveHusbandryType(HusbandryTypeDto husbandryTypeDto, Long id);

	HusbandryTypeDto removeHusbandryType(Long id);

}
