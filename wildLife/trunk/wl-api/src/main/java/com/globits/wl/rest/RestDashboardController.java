package com.globits.wl.rest;

import com.globits.wl.dto.functiondto.*;
import com.globits.wl.response.DashboardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.globits.wl.dto.DashboardVolatilityDto;
import com.globits.wl.service.DashboardService;



@RestController
@RequestMapping("/api/dashboard")
public class RestDashboardController {
	@Autowired
	DashboardService dashboardService;

	@RequestMapping(value = "/getCites", method = RequestMethod.POST)
	public DashboardCitesDto getCites(@RequestBody SearchDto searchDto) {
		return dashboardService.Cites(searchDto);
	}

	@RequestMapping(value = "/getFarm", method = RequestMethod.POST)
	public DashboardFarmDto getFarm(@RequestBody SearchDto searchDto) {
		return dashboardService.getFarm(searchDto);
	}

	@RequestMapping(value = "/getVolatility", method = RequestMethod.POST)
	public DashboardVolatilityDto getVolatility(@RequestBody SearchReportForm16Dto searchDto) {
		return dashboardService.Volatility(searchDto);
	}

	@RequestMapping(value = "getFarmProvidedCodeInYearNumber", method = RequestMethod.POST)
	public Long getFarmProvidedCodeInYearNumber(@RequestBody SearchDto searchDto) {
		return dashboardService.farmProvidedCodeInYearNumber(searchDto);
	}
	
	@RequestMapping(value="/getNumberGovermentDecree", method = RequestMethod.POST)
	public DashboardGovermentDecreeDto getNumberGovermentDecree(@RequestBody SearchReportForm16Dto searchDto) {
		return dashboardService.getNumberGovermentDecree(searchDto);
	}
	@RequestMapping(value="/getCitesVN", method = RequestMethod.POST)
	public DashBoardCitesVnDto getCitesVn(@RequestBody SearchReportForm16Dto searchDto) {
		return dashboardService.CitesVn(searchDto);
	}

	@RequestMapping(value="/getTotalCites", method = RequestMethod.POST)
	public DashboardTotalDtoCites getCites(@RequestBody DashboardTotalDtoCites dto) {
		return dashboardService.totalDtoCites(dto);
	}

	@RequestMapping(value="/getTotalGroverment", method = RequestMethod.POST)
	public DashboardTotalGrovermentDecreeDto getGroverment(@RequestBody DashboardTotalGrovermentDecreeDto dto) {
		return dashboardService.totalGroverment(dto);
	}

	@RequestMapping(value="/getDataDashboard", method = RequestMethod.POST)
	public DashboardResponse getData(@RequestBody SearchDto search) {
		return dashboardService.getDataDashboard(search);
	}


}
