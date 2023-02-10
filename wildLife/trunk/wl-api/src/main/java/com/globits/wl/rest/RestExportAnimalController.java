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

import com.globits.wl.dto.ExportAnimalDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.ExportAnimalService;
import com.globits.wl.service.FarmService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/exportanimal")
public class RestExportAnimalController {
	@Autowired
	private ExportAnimalService exportAnimalService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<ExportAnimalDto> getExportAnimals(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return this.exportAnimalService.getListExportAnimal(pageIndex, pageSize);
	}

	// get All list ko phan trang
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<ExportAnimalDto> getAll() {
		return this.exportAnimalService.getAll();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ExportAnimalDto getExportAnimalById(@PathVariable("id") Long id) {
		return this.exportAnimalService.getExportAnimalById(id);
	}

	// create
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ExportAnimalDto saveExportAnimal(@RequestBody ExportAnimalDto exportAnimalDto) {
		return this.exportAnimalService.saveExportAnimal(exportAnimalDto, null);
	}

	// update
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ExportAnimalDto updateExportAnimal(@PathVariable("id") Long id,
			@RequestBody ExportAnimalDto exportAnimalDto) {
		return this.exportAnimalService.saveExportAnimal(exportAnimalDto, id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ExportAnimalDto removeExportAnimal(@PathVariable Long id) {
		return this.exportAnimalService.removeExportAnimal(id);
	}
	//seachDto
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<ImportExportAnimalDto>> getListSimpleByNameCode(@RequestBody ImportExportAnimalSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<ImportExportAnimalDto> results = (Page<ImportExportAnimalDto>) this.exportAnimalService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<ImportExportAnimalDto>>(results, HttpStatus.OK);
	}
}
