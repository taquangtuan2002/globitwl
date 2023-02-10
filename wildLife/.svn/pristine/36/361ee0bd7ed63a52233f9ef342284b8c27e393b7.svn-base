package com.globits.wl.rest;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.BiologicalClassDto;
import com.globits.wl.dto.DrugDto;
import com.globits.wl.dto.UnitDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.BiologicalClassService;
import com.globits.wl.service.UnitService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/biologicalClass")
public class RestBiologicalClassController {
	@Autowired
	private BiologicalClassService service;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public Page<BiologicalClassDto> searchByPage(@RequestBody SearchDto dto) {
		return service.searchByPage(dto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "", method = RequestMethod.POST)
	public BiologicalClassDto save(@RequestBody BiologicalClassDto dto) {
		BiologicalClassDto result = service.saveOrUpdate(dto);
		return result;
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private BiologicalClassDto getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	private Boolean delete(@PathVariable("id") Long id) {
		return service.deleteById(id);
	}
	
}
