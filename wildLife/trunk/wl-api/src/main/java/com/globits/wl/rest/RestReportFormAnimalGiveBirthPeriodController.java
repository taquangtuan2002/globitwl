package com.globits.wl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.ReportFormAnimalEggPeriodDto;
import com.globits.wl.dto.ReportFormAnimalGiveBirthPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.service.ReportFormAnimalEggPeriodService;
import com.globits.wl.service.ReportFormAnimalGiveBirthPeriodService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("api/reportformanimalgivebirthperiod")
public class RestReportFormAnimalGiveBirthPeriodController {
	@Autowired
	private ReportFormAnimalGiveBirthPeriodService service;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ReportFormAnimalGiveBirthPeriodDto> saveOrUpdate(@RequestBody ReportFormAnimalGiveBirthPeriodDto dto){
		ReportFormAnimalGiveBirthPeriodDto result = service.saveOrUpdate(dto);
		return new ResponseEntity<ReportFormAnimalGiveBirthPeriodDto>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getpage/{pageIndex}/{pageSize}", method = RequestMethod.POST)
	public ResponseEntity<Page<ReportFormAnimalGiveBirthPeriodDto>> getPage(@RequestBody AnimalReportDataSearchDto searchDto,@PathVariable("pageIndex") int pageIndex,@PathVariable("pageSize") int pageSize){
		Page<ReportFormAnimalGiveBirthPeriodDto> result = service.getPageBySearch(searchDto, pageIndex, pageSize);
		return new ResponseEntity<Page<ReportFormAnimalGiveBirthPeriodDto>>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ResponseEntity<ReportFormAnimalGiveBirthPeriodDto> getById(@PathVariable("id") Long id){
		ReportFormAnimalGiveBirthPeriodDto result = service.getById(id);
		return new ResponseEntity<ReportFormAnimalGiveBirthPeriodDto>(result, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<ReportFormAnimalGiveBirthPeriodDto> deleteById(@PathVariable("id") Long id){
		ReportFormAnimalGiveBirthPeriodDto result = service.deleteById(id);
		return new ResponseEntity<ReportFormAnimalGiveBirthPeriodDto>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/checkDuplicateYearMonthDateReport16D",method = RequestMethod.POST)
	public ResponseEntity<Boolean> checkDuplicateYearMonthDateReport16D(@RequestBody SearchReportPeriodDto searchDto){
		Boolean result = service.checkDuplicateYearMonthDateReport16D(searchDto);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
}
