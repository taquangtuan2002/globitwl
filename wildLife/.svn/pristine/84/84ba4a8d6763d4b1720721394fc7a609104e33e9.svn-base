package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.WaterSourceDto;
import com.globits.wl.dto.functiondto.WaterSourceSearchDto;

public interface WaterSourceService {

	Page<WaterSourceDto> getListWaterSources(int pageIndex, int pageSize);

	List<WaterSourceDto> getAll();

	WaterSourceDto waterSourceById(Long id);

	WaterSourceDto saveWaterSource(WaterSourceDto waterSourceDto, Long id);

	WaterSourceDto removeWaterSource(Long id);
	Page<WaterSourceDto> searchWaterSourceDto(WaterSourceSearchDto searchDto, int pageIndex, int pageSize);
	WaterSourceDto checkDuplicateCode(String code);
}
