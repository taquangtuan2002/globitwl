package com.globits.wl.rest;

import java.util.Date;
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
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.ReportFormAnimalEggSearchDto;
import com.globits.wl.service.ReportFormAnimalEggService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("api/reportformanimalegg")
public class RestReportFormAnimalEggController {
	@Autowired
	ReportFormAnimalEggService reportFormAnimalEggService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ReportFormAnimalEggDto> saveOrUpdate(@RequestBody ReportFormAnimalEggDto dto){
		ReportFormAnimalEggDto result = reportFormAnimalEggService.save(dto);
		return new ResponseEntity<ReportFormAnimalEggDto>(result, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="saveList" ,method = RequestMethod.POST)
	public ResponseEntity<List<ReportFormAnimalEggDto>> saveList(@RequestBody List<ReportFormAnimalEggDto> dto){
		List<ReportFormAnimalEggDto> result = reportFormAnimalEggService.saveList(dto);
		return new ResponseEntity<List<ReportFormAnimalEggDto>>(result, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.POST)
	public ResponseEntity<List<ReportFormAnimalEggDto>> getAll(@RequestBody AnimalReportDataSearchDto searchDto){
		List<ReportFormAnimalEggDto> result = reportFormAnimalEggService.getAll(searchDto);
		return new ResponseEntity<List<ReportFormAnimalEggDto>>(result, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ReportFormAnimalEggDto> getById(@PathVariable("id") Long id){
		ReportFormAnimalEggDto result = reportFormAnimalEggService.getById(id);
		return new ResponseEntity<ReportFormAnimalEggDto>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getpage/{pageIndex}/{pageSize}", method = RequestMethod.POST)
	public ResponseEntity<Page<ReportFormAnimalEggDto>> getPage(@RequestBody AnimalReportDataSearchDto searchDto, @PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize){
		Page<ReportFormAnimalEggDto> result = reportFormAnimalEggService.getPage(searchDto, pageIndex, pageSize);
		return new ResponseEntity<Page<ReportFormAnimalEggDto>>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id")Long id){
		Boolean result = reportFormAnimalEggService.deleteById(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/quantityChildIncrement", method = RequestMethod.POST)
	public ResponseEntity<Integer> quantityChildIncrement(@RequestBody ReportFormAnimalEggSearchDto dto){
		Integer result = reportFormAnimalEggService.quantityChildIncrement(dto);
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "searchByPage", method = RequestMethod.POST)
	public Page<ReportFormAnimalEggDto> searchByPage(@RequestBody ReportFormAnimalEggSearchDto searchDto) {
		Page<ReportFormAnimalEggDto> page = reportFormAnimalEggService.searchByPage(searchDto);
		return page;
	}
	
}
