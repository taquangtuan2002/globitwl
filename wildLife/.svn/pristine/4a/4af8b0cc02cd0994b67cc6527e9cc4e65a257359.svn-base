package com.globits.wl.service;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ReportFormAnimalEggPeriod;
import com.globits.wl.dto.ReportFormAnimalEggPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface ReportFormAnimalEggPeriodService extends GenericService<ReportFormAnimalEggPeriod, Long>{

	ReportFormAnimalEggPeriodDto saveOrUpdate(ReportFormAnimalEggPeriodDto dto);

	Page<ReportFormAnimalEggPeriodDto> getPageBySearch(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize);

	ReportFormAnimalEggPeriodDto getById(Long id);

	ReportFormAnimalEggPeriodDto deleteById(Long id);

	Boolean checkDuplicateYearMonthDateReport16C(SearchReportPeriodDto searchDto);

}
