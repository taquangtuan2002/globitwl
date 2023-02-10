package com.globits.wl.service;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ReportFormAnimalGiveBirthPeriod;
import com.globits.wl.dto.ReportFormAnimalEggPeriodDto;
import com.globits.wl.dto.ReportFormAnimalGiveBirthPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface ReportFormAnimalGiveBirthPeriodService extends GenericService<ReportFormAnimalGiveBirthPeriod, Long>{

	ReportFormAnimalGiveBirthPeriodDto saveOrUpdate(ReportFormAnimalGiveBirthPeriodDto dto);

	Page<ReportFormAnimalGiveBirthPeriodDto> getPageBySearch(AnimalReportDataSearchDto searchDto, int pageIndex,
			int pageSize);

	ReportFormAnimalGiveBirthPeriodDto getById(Long id);

	ReportFormAnimalGiveBirthPeriodDto deleteById(Long id);

	Boolean checkDuplicateYearMonthDateReport16D(SearchReportPeriodDto searchDto);

}
