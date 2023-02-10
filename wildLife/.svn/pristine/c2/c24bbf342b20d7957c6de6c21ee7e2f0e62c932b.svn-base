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

import com.globits.core.service.FileDescriptionService;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.InjectionTimeDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.InjectionTimeService;

@RestController
@RequestMapping("/api/injectiontime")
public class RestInjectionTimeCotroller {
	@Autowired
	private FileDescriptionService fileService;
	@Autowired
	private InjectionTimeService injectionTimeService;

	@RequestMapping(value = "page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<InjectionTimeDto> getPageDto(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.injectionTimeService.getPageDto(pageIndex, pageSize);
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	private List<InjectionTimeDto> getAll() {
		return this.injectionTimeService.getAllDto();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private InjectionTimeDto getInjectionTimeById(@PathVariable("id") Long id) {
		return this.injectionTimeService.getInjectionTimeById(id);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private InjectionTimeDto deleteById(@PathVariable("id") Long id) {
		InjectionTimeDto dto=new InjectionTimeDto();
		try {
			dto=this.injectionTimeService.deleteById(id);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private InjectionTimeDto deleteByIds(@RequestBody List<Long> ids) {
		InjectionTimeDto dto=new InjectionTimeDto();
		try {
			this.injectionTimeService.deleteByIds(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private InjectionTimeDto createInjectionTime(@RequestBody InjectionTimeDto dto) {
		return this.injectionTimeService.createInjectionTime(dto);
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private InjectionTimeDto updateInjectionTime(@PathVariable("id") Long id,@RequestBody InjectionTimeDto dto) {
		return this.injectionTimeService.updateInjectionTime(id,dto);
	}
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<InjectionTimeDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<InjectionTimeDto> results = this.injectionTimeService.searchInjectionTimeDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<InjectionTimeDto>>(results, HttpStatus.OK);
	}
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public InjectionTimeDto checkDuplicateCode(@PathVariable("code") String code) {
		return injectionTimeService.checkDuplicateCode(code);
	}

}
