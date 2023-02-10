package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.repository.ReportPeriodRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.ReportForm16GetParentService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/reportPeriod")
public class RestReportPeriodController {

	@Autowired
	ReportPeriodService reportPeriodService;

	@Autowired
	AnimalReportDataService animalReportDataService;

	@Autowired
	ReportPeriodRepository reportPeriodRepository;
	
	@Autowired
	AnimalReportDataRepository animalReportDataRepository;
	@Autowired
	ReportForm16GetParentService reportForm16GetParentService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "searchByPage", method = RequestMethod.POST)
	public Page<ReportPeriodDto> searchByPage(@RequestBody SearchReportPeriodDto searchDto) {
		Page<ReportPeriodDto> page = reportPeriodService.searchByPage(searchDto);
		return page;
	}

	 
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "getReport16aBySearchDto", method = RequestMethod.POST)
	public 	 Page<ReportPeriodDto> getReport16aBySearchDto(@RequestBody SearchReportPeriodDto searchDto) {
		Page<ReportPeriodDto> page = reportPeriodService.getReport16aBySearchDto(searchDto);
		return page;
	}
	 
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.POST)
	public ReportPeriodDto saveOrUpdate(@RequestBody ReportPeriodDto dto) {
		return reportPeriodService.saveOrUpdate(dto);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ReportPeriodDto getById(@PathVariable("id")Long id){
		return reportPeriodService.getById(id);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ReportPeriodDto deleteReportPeriod(@PathVariable("id") Long id){
		ReportPeriodDto dtos=new ReportPeriodDto();
		try {
			ReportPeriod reportPeriod = reportPeriodRepository.findOne(id);
			if (reportPeriod != null && reportPeriod.getId() != null && reportPeriod.getFarm() != null) {
				reportPeriodService.updateAnimalReportDataBeforeDeleteReportPeriod(reportPeriod);
				reportPeriodService.delete(reportPeriod.getId());
				animalReportDataService.updateOneFarmtoMap(true, reportPeriod.getFarm().getCode(), reportPeriod.getYear());
				return dtos;
			}
			return null;
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
		
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/checkDuplicateYearMonthDate",method=RequestMethod.POST)
	public Boolean checkDuplicateYearMonthDate(@RequestBody SearchReportPeriodDto searchDto){
		return reportPeriodService.checkDuplicateYearMonthDate(searchDto);
		
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/updateDataFromReportPeriodToAnimalReportDataForAll",method=RequestMethod.POST)
	public int updateDataFromReportPeriodToAnimalReportDataForAll(@RequestBody SearchReportPeriodDto searchDto) {
		return reportPeriodService.updateDataFromReportPeriodToAnimalReportDataForAll(searchDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "getLastRecordReportPeriodByFarmAndAnimal", method = RequestMethod.POST)
	public ReportPeriodDto getLastRecordReportPeriodByFarmAndAnimal(@RequestBody SearchReportPeriodDto searchDto) {
		ReportPeriodDto lastRecord = reportPeriodService.getLastRecordReportPeriodByFarmAndAnimal(searchDto);
		return lastRecord;
	}
}
