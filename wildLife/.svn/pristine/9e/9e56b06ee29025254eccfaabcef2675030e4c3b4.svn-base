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

import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.functiondto.DensityRegionDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.AnimalTypeService;
import com.globits.wl.service.impl.AnimalTypeServiceIpml;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/animaltype")
public class RestAnimalTypeController {
	@Autowired
	AnimalTypeService animalTypeService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	private Page<AnimalTypeDto> getAll(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.animalTypeService.getAll(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getalldtos", method = RequestMethod.GET)
	private List<AnimalTypeDto> getAll() {
		return this.animalTypeService.getAllDto();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private AnimalTypeDto getAnimalTypeById(@PathVariable("id") Long id) {
		return this.animalTypeService.getAnimalTypeById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private AnimalTypeDto createAnimalType(@RequestBody AnimalTypeDto dto) {
		return this.animalTypeService.createAnimalType(dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private AnimalTypeDto updateAnimalType(@PathVariable("id") Long id,@RequestBody AnimalTypeDto dto) {
		return this.animalTypeService.updateAnimalType(id,dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private AnimalTypeDto deleteById(@PathVariable("id") Long id) {
		AnimalTypeDto dto=null;
		try {
			 dto=animalTypeService.deleteById(id);
			return dto;
		} catch (Exception e) {
			dto=new AnimalTypeDto();
			dto.setCode("-1");
			return dto;
			// TODO: handle exception
		}
		
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private boolean deleteByIds(@RequestBody List<Long> ids) {
		return this.animalTypeService.deleteByIds(ids);
	}
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<AnimalTypeDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<AnimalTypeDto> results = this.animalTypeService.searchAnimalTypeDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<AnimalTypeDto>>(results, HttpStatus.OK);
	}
	//@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public AnimalTypeDto checkDuplicateCode(@PathVariable("code") String code) {
		return animalTypeService.checkDuplicateCode(code);
	}

}
