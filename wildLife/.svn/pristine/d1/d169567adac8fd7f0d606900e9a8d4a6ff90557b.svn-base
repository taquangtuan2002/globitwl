package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.FarmReportPeriodDto;
import com.globits.wl.service.FarmReportPeriodService;

@RestController
@RequestMapping("/api/farmReportPeriod")
public class RestFarmReportPeriodController {
	
	@Autowired
	FarmReportPeriodService farmReportPeriodService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	private Integer saveAnimalReportData(@RequestBody FarmReportPeriodDto dto) {
		return this.farmReportPeriodService.saveAnimalReportData(dto);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value = "/getById/{id}/{type}", method = RequestMethod.GET)
	private FarmReportPeriodDto getById(@PathVariable Long id,@PathVariable Integer type) {
		return this.farmReportPeriodService.getById(id, type);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value = "/searchList", method = RequestMethod.POST)
	private List<FarmReportPeriodDto> searchList(@RequestBody FarmReportPeriodDto dto) {
		return this.farmReportPeriodService.searchList(dto);
	}
	/**create by khoadv42*/
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value = "/updateAnimalReportData", method = RequestMethod.POST)
	private Integer updateAnimalReportData(@RequestBody FarmReportPeriodDto dto) {
		return this.farmReportPeriodService.updateAnimalReportData(dto);
	}
}
