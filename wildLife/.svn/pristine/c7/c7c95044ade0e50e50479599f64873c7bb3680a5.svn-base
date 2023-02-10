package com.globits.wl.rest;

import java.util.List;

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

import com.globits.wl.dto.ReportFormAnimalEggDto;
import com.globits.wl.dto.ReportFormAnimalGiveBirthDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.ReportFormAnimalEggSearchDto;
import com.globits.wl.service.ReportFormAnimalGiveBirthService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/reportanimalgivebirth")
public class RestReportFormAnimalGiveBirthController {
	@Autowired
	private ReportFormAnimalGiveBirthService reportAnimalGiveBirthService;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ReportFormAnimalGiveBirthDto> saveOrUpdate(@RequestBody ReportFormAnimalGiveBirthDto dto){
		ReportFormAnimalGiveBirthDto result = reportAnimalGiveBirthService.save(dto);
		return new ResponseEntity<ReportFormAnimalGiveBirthDto>(result, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="saveList", method = RequestMethod.POST)
	public ResponseEntity<List<ReportFormAnimalGiveBirthDto>> saveList(@RequestBody List<ReportFormAnimalGiveBirthDto> dto){
		List<ReportFormAnimalGiveBirthDto> result = reportAnimalGiveBirthService.saveList(dto);
		return new ResponseEntity<List<ReportFormAnimalGiveBirthDto>>(result, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.POST)
	public ResponseEntity<List<ReportFormAnimalGiveBirthDto>> getAll(@RequestBody AnimalReportDataSearchDto searchDto){
		List<ReportFormAnimalGiveBirthDto> result = reportAnimalGiveBirthService.getAll(searchDto);
		return new ResponseEntity<List<ReportFormAnimalGiveBirthDto>>(result, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ReportFormAnimalGiveBirthDto> getById(@PathVariable("id") Long id){
		ReportFormAnimalGiveBirthDto result = reportAnimalGiveBirthService.getReportFormAnimalGiveBirthDtoById(id);
		return new ResponseEntity<ReportFormAnimalGiveBirthDto>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getpage/{pageIndex}/{pageSize}", method = RequestMethod.POST)
	public ResponseEntity<Page<ReportFormAnimalGiveBirthDto>> getPage(@RequestBody AnimalReportDataSearchDto searchDto, @PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize){
		Page<ReportFormAnimalGiveBirthDto> result = reportAnimalGiveBirthService.getPage(searchDto, pageIndex, pageSize);
		return new ResponseEntity<Page<ReportFormAnimalGiveBirthDto>>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id")Long id){
		Boolean result = reportAnimalGiveBirthService.deleteById(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getNumberOfChildrenInTheCommunityOverTime", method = RequestMethod.POST)
	public ResponseEntity<ReportFormAnimalGiveBirthDto> getNumberOfChildrenInTheCommunityOverTime(@RequestBody AnimalReportDataSearchDto searchDto){
		ReportFormAnimalGiveBirthDto result = reportAnimalGiveBirthService.getNumberOfChildrenInTheCommunityOverTime(searchDto);
		return new ResponseEntity<ReportFormAnimalGiveBirthDto>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/giveBirthQuantityChildIncrement", method = RequestMethod.POST)
	public ResponseEntity<Integer> getGiveBirthQuantityChildIncrement(@RequestBody ReportFormAnimalEggSearchDto searchDto){
		Integer result = reportAnimalGiveBirthService.getGiveBirthQuantityChildIncrement(searchDto);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "searchByPage", method = RequestMethod.POST)
	public Page<ReportFormAnimalGiveBirthDto> searchByPage(@RequestBody ReportFormAnimalEggSearchDto searchDto) {
		Page<ReportFormAnimalGiveBirthDto> page = reportAnimalGiveBirthService.searchByPage(searchDto);
		return page;
	}
	
}
