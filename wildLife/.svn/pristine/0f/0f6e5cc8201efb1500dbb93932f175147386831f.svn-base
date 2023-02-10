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

import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.HusbandryMethodService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/husbandrymethod")
public class RestHusbandryMethodController {

	@Autowired 
	private HusbandryMethodService husbandryMethodService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<HusbandryMethodDto> getHusbandryMethod(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return this.husbandryMethodService.getListHusbandryMethods(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<HusbandryMethodDto> getAll() {
		return this.husbandryMethodService.getAll();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{husbandryMethodId}", method = RequestMethod.GET)
	public HusbandryMethodDto husbandryMethodById(@PathVariable("husbandryMethodId") Long id) {
		return this.husbandryMethodService.husbandryMethodById(id);
	}

	// create
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public HusbandryMethodDto saveHusbandryMethod(@RequestBody HusbandryMethodDto husbandryMethodDto) {
		return this.husbandryMethodService.saveHusbandryMethod(husbandryMethodDto, null);
	}

	// update
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public HusbandryMethodDto updateHusbandryMethod(@PathVariable("id") Long id,
			@RequestBody HusbandryMethodDto husbandryMethodDto) {
		return this.husbandryMethodService.saveHusbandryMethod(husbandryMethodDto, id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public HusbandryMethodDto removeHusbandryMethod(@PathVariable Long id) {
		HusbandryMethodDto dto=new HusbandryMethodDto();
		try {
			dto=this.husbandryMethodService.removeHusbandryMethod(id);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<HusbandryMethodDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<HusbandryMethodDto> results = this.husbandryMethodService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<HusbandryMethodDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public HusbandryMethodDto checkDuplicateCode(@PathVariable("code") String code) {
		return husbandryMethodService.checkDuplicateCode(code);
	}
}
