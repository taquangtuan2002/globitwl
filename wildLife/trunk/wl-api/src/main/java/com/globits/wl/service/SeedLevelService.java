package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.SeedLevelDto;
import com.globits.wl.dto.WaterSourceDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.WaterSourceSearchDto;

public interface SeedLevelService {

	Page<SeedLevelDto> getListSeedLevels(int pageIndex, int pageSize);

	List<SeedLevelDto> getAll();

	SeedLevelDto seedLevelById(Long id);

	SeedLevelDto saveSeedLevel(SeedLevelDto seedLevelDto, Long id);

	SeedLevelDto removeSeedLevel(Long id);
	
	Page<SeedLevelDto> searchSeedLevelBySearchDto(SearchDto searchDto, int pageIndex, int pageSize);

	SeedLevelDto checkDuplicateCode(String code);
	
//	SeedLevelDto checkDuplicateCode(String code);
}
