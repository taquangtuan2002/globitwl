package com.globits.wl.service;

import java.util.List;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface ReportForm16GetParentService extends GenericService<ReportForm16, Long> {

	List<ReportForm16Dto> getReport16aBySearchDtoRecently(SearchReportPeriodDto searchDto);
}
