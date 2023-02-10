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

import com.globits.wl.dto.FarmSizeDto;
import com.globits.wl.dto.InjectionTimeDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.FarmSizeService;

@RestController
@RequestMapping("/api/farmsize")
public class RestFarmSizeController {
	@Autowired
	FarmSizeService farmSizeService;
	//@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value = "/getall/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	private Page<FarmSizeDto> getAll(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.farmSizeService.getAll(pageIndex, pageSize);
	}
	//@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value = "/getalldtos", method = RequestMethod.GET)
	private List<FarmSizeDto> getAll() {
		return this.farmSizeService.getAllDto();
	}
	//@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private FarmSizeDto getFarmSizeById(@PathVariable("id") Long id) {
		return this.farmSizeService.getFarmSizeById(id);
	}
	//@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/getByQuantity/{quantity}", method = RequestMethod.GET)
	private FarmSizeDto getFarmSizeById(@PathVariable("quantity") int quantity) {
		return this.farmSizeService.getByQuantity(quantity);
	}
	//@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private FarmSizeDto createFarmSize(@RequestBody FarmSizeDto dto) {
		return this.farmSizeService.createFarmSize(dto);
	}
	//@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private FarmSizeDto updateFarmSize(@PathVariable("id") Long id,@RequestBody FarmSizeDto dto) {
		return this.farmSizeService.updateFarmSize(id,dto);
	}
	//@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private FarmSizeDto deleteById(@PathVariable("id") Long id) {
		FarmSizeDto dto=new FarmSizeDto();
		try {
			dto=this.farmSizeService.deleteById(id);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	//@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private FarmSizeDto deleteByIds(@RequestBody List<Long> ids) {
		FarmSizeDto dto=new FarmSizeDto();
		try {
			this.farmSizeService.deleteByIds(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	//@Secured("ROLE_ADMIN")
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<FarmSizeDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<FarmSizeDto> results = this.farmSizeService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<FarmSizeDto>>(results, HttpStatus.OK);
	}
	//@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public FarmSizeDto checkDuplicateCode(@PathVariable("code") String code) {
		return farmSizeService.checkDuplicateCode(code);
	}

}
