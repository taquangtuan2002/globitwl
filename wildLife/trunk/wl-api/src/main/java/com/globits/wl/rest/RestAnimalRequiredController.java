package com.globits.wl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.AnimalRequiredDto;
import com.globits.wl.dto.functiondto.AnimalSearchDto;
import com.globits.wl.service.AnimalRequiredService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("api/animalrequired")
public class RestAnimalRequiredController {
	@Autowired
	private AnimalRequiredService service;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getPage/{pageIndex}/{pageSize}",method=RequestMethod.POST)
	public Page<AnimalRequiredDto> getPageBySearch(@RequestBody AnimalSearchDto searchDto,@PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize){
		return this.service.getPageBySearch(searchDto, pageIndex, pageSize);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public AnimalRequiredDto getById(@PathVariable("id")Long id){
		return this.service.getById(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method=RequestMethod.POST)
	public AnimalRequiredDto creatOrUpdate(@RequestBody AnimalRequiredDto dto){
		return this.service.createAnimalRequired(dto);
	}
	/** api change status với quyền admin */
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/updateRequired",method=RequestMethod.PUT)
	public AnimalRequiredDto updateRequired(@RequestBody AnimalRequiredDto dto){
		return this.service.updateRequired(dto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/animalRequiredApplyToAnimal",method=RequestMethod.POST)
	public AnimalRequiredDto animalRequiredApplyToAnimal(@RequestBody AnimalRequiredDto dto){
		return this.service.animalRequiredApplyToAnimal(dto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/changeAnimalRequiredStatus",method=RequestMethod.POST)
	public AnimalRequiredDto changeAnimalRequiredStatus(@RequestBody AnimalRequiredDto dto){
		return this.service.changeAnimalRequiredStatus(dto);
	}
	
}
