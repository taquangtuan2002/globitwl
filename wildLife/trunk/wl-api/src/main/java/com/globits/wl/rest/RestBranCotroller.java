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
import com.globits.wl.dto.BranDto;
import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.BranService;

@RestController
@RequestMapping("/api/bran")
public class RestBranCotroller {
	@Autowired
	private FileDescriptionService fileService;
	@Autowired
	private BranService BranService;

	@RequestMapping(value = "page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<BranDto> getPageDto(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.BranService.getPageDto(pageIndex, pageSize);
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	private List<BranDto> getAll() {
		return this.BranService.getAllDto();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private BranDto getBranById(@PathVariable("id") Long id) {
		return this.BranService.getBranById(id);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private BranDto deleteById(@PathVariable("id") Long id) {
		BranDto dto=new BranDto();
		try {
			dto=this.BranService.deleteById(id);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private BranDto deleteByIds(@RequestBody List<Long> ids) {
		BranDto dto=new BranDto();
		try {
			this.BranService.deleteByIds(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private BranDto createBran(@RequestBody BranDto dto) {
		return this.BranService.createBran(dto);
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private BranDto updateBran(@PathVariable("id") Long id,@RequestBody BranDto dto) {
		return this.BranService.updateBran(id,dto);
	}
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<BranDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<BranDto> results = this.BranService.searchBranDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<BranDto>>(results, HttpStatus.OK);
	}
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public BranDto checkDuplicateCode(@PathVariable("code") String code) {
		return BranService.checkDuplicateCode(code);
	}

}
