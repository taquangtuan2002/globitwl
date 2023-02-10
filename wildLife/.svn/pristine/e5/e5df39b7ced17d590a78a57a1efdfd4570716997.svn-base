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

import com.globits.wl.dto.ExportAnimalDto;
import com.globits.wl.dto.ImportAnimalFeedDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ImportAnimalFeedSearchDto;
import com.globits.wl.dto.functiondto.ImportDrugSearchDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.ExportAnimalService;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.ImportAnimalFeedService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/importanimalfeed")
public class RestImportAnimalFeedController {
	@Autowired
	private ImportAnimalFeedService exportAnimalService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<ImportAnimalFeedDto> getImportAnimalFeeds(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return this.exportAnimalService.getListImportAnimalFeed(pageIndex, pageSize);
	}

	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ImportAnimalFeedDto getImportAnimalFeedById(@PathVariable("id") Long id) {
		return this.exportAnimalService.getImportAnimalFeedById(id);
	}

	// create
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ImportAnimalFeedDto saveImportAnimalFeed(@RequestBody ImportAnimalFeedDto exportAnimalDto) {
		return this.exportAnimalService.saveImportAnimalFeed(exportAnimalDto, null);
	}

	// update
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ImportAnimalFeedDto updateImportAnimalFeed(@PathVariable("id") Long id,
			@RequestBody ImportAnimalFeedDto exportAnimalDto) {
		return this.exportAnimalService.saveImportAnimalFeed(exportAnimalDto, id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ImportAnimalFeedDto removeImportAnimalFeed(@PathVariable Long id) {
		ImportAnimalFeedDto dto=new ImportAnimalFeedDto();
		try {
			dto=this.exportAnimalService.removeImportAnimalFeed(id); 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
		
	}
	//seachDto
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<ImportAnimalFeedDto>> getListSimpleByNameCode(@RequestBody ImportAnimalFeedSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<ImportAnimalFeedDto> results = (Page<ImportAnimalFeedDto>) this.exportAnimalService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<ImportAnimalFeedDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/inventoryAnimalFeed",method=RequestMethod.POST)
	public ReportManagerDto getInventory(@RequestBody ImportAnimalFeedSearchDto paramDto) {
		return this.exportAnimalService.getSumQuantity(paramDto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/validDateIssueDelete/{id}",method=RequestMethod.GET)
	public boolean validDateIssueDelete(@PathVariable("id") Long id) {
		return this.exportAnimalService.validDateIssue(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/reportInventory",method=RequestMethod.POST)
	public ReportManagerDto reportInventory(@RequestBody ImportAnimalFeedSearchDto paramDto) {
		return this.exportAnimalService.reportInventory(paramDto);
	}
}
