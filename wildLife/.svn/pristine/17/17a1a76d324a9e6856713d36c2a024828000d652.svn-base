package com.globits.wl.rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.service.ReportForm16GetParentService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/reportForm16GetParent")
public class RestReportForm16GetParent {
	
	@Autowired
	ReportForm16GetParentService reportPeriodService;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "getReport16BySearchDto", method = RequestMethod.POST)
	public List<ReportForm16Dto> getReport16aBySearchDto(@RequestBody SearchReportPeriodDto searchDto) {
		List<ReportForm16Dto> page = reportPeriodService.getReport16aBySearchDtoRecently(searchDto);
		return page;
	}
}
