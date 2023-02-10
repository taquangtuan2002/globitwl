package com.globits.wl.rest;

import java.text.ParseException;
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

import com.globits.wl.dto.ImportDrugDto;
import com.globits.wl.dto.functiondto.ImportDrugSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.service.ImportDrugService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/importdrug")
public class RestImportDrugController {
	@Autowired
	private ImportDrugService importDrugService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<ImportDrugDto> getImportDrugs(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return this.importDrugService.getListImportDrug(pageIndex, pageSize);
	}

	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ImportDrugDto getImportDrugById(@PathVariable("id") Long id) {
		return this.importDrugService.getImportDrugById(id);
	}

	// create
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ImportDrugDto saveImportDrug(@RequestBody ImportDrugDto exportAnimalDto) {
		return this.importDrugService.saveImportDrug(exportAnimalDto, null);
	}

	// update
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ImportDrugDto updateImportDrug(@PathVariable("id") Long id,
			@RequestBody ImportDrugDto exportAnimalDto) {
		return this.importDrugService.saveImportDrug(exportAnimalDto, id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ImportDrugDto removeImportDrug(@PathVariable Long id) {
		ImportDrugDto dto=new ImportDrugDto();
		try {
			dto=this.importDrugService.removeImportDrug(id); 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
		
	}
	//seachDto
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<ImportDrugDto>> getListSimpleByNameCode(@RequestBody ImportDrugSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<ImportDrugDto> results = (Page<ImportDrugDto>) this.importDrugService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<ImportDrugDto>>(results, HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/inventoryDrug",method=RequestMethod.POST)
	public ReportManagerDto getInventory(@RequestBody ImportDrugSearchDto paramDto) {
		return this.importDrugService.getSumQuantity(paramDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/validDelete/{importId}",method=RequestMethod.GET)
	public boolean validDelete(@PathVariable("importId") Long importId) {
		return this.importDrugService.validDelete(importId);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/reportInventory",method=RequestMethod.POST)
	public ReportManagerDto reportInventory(@RequestBody ImportDrugSearchDto paramDto) {
		return this.importDrugService.reportInventory(paramDto);
	}
	
}
