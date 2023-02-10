package com.globits.wl.rest;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.core.service.FileDescriptionService;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.InjectionPlantDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.InjectionPlantService;

@RestController
@RequestMapping("/api/injectionplant")
public class RestInjectionPlantCotroller {
	@Autowired 
	private FileDescriptionService fileService;
	@Autowired
	private InjectionPlantService injectionPlantService;
	
	@RequestMapping(value="page/{pageIndex}/{pageSize}",method=RequestMethod.GET)
	public Page<InjectionPlantDto> getPageDto(@PathVariable("pageIndex") int pageIndex,@PathVariable("pageSize")int pageSize){
		return this.injectionPlantService.getPageDto(pageIndex,pageSize);
	}
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	private List<InjectionPlantDto> getAll() {
		return this.injectionPlantService.getAllDto();
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private InjectionPlantDto getInjectionPlantById(@PathVariable("id") Long id) {
		return this.injectionPlantService.getInjectionPlantById(id);
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private InjectionPlantDto createInjectionPlant(@RequestBody InjectionPlantDto dto) {
		return this.injectionPlantService.createInjectionPlant(dto);
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private InjectionPlantDto updateInjectionPlant(@PathVariable("id") Long id,@RequestBody InjectionPlantDto dto) {
		return this.injectionPlantService.updateInjectionPlant(id,dto);
	}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private InjectionPlantDto deleteById(@PathVariable("id") Long id) {
		return this.injectionPlantService.deleteById(id);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private boolean deleteByIds(@RequestBody List<Long> ids) {
		return this.injectionPlantService.deleteByIds(ids);
	}
	
//	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
//	public ResponseEntity<Page<InjectionPlantDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
//		
//		Page<InjectionPlantDto> results = this.injectionPlantService.searchInjectionPlantDto(dto, pageIndex, pageSize);
//		return new ResponseEntity<Page<InjectionPlantDto>>(results, HttpStatus.OK);
//	}
	
}
