package com.globits.wl.service;

import java.util.List;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.FarmReportPeriod;
import com.globits.wl.dto.FarmReportPeriodDto;


public interface FarmReportPeriodService  extends GenericService<FarmReportPeriod, Long>{

	Integer saveAnimalReportData(FarmReportPeriodDto dto);

	FarmReportPeriodDto getById(Long id, Integer type);

	List<FarmReportPeriodDto> searchList(FarmReportPeriodDto dto);

	Integer updateAnimalReportData(FarmReportPeriodDto dto);
	
}
