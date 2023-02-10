package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.HusbandryTypeDto;
import com.globits.wl.service.HusbandryTypeService;

@RestController
@RequestMapping("/api/husbandrytype")
public class RestHusbandryTypeController {

	@Autowired
	private HusbandryTypeService husbandryTypeService;

	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<HusbandryTypeDto> getHusbandryType(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return this.husbandryTypeService.getListHusbandryTypes(pageIndex, pageSize);
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<HusbandryTypeDto> getAll() {
		return this.husbandryTypeService.getAll();
	}

	@RequestMapping(value = "/{husbandryTypeId}", method = RequestMethod.GET)
	public HusbandryTypeDto husbandryTypeById(@PathVariable("husbandryTypeId") Long id) {
		return this.husbandryTypeService.husbandryTypeById(id);
	}

	// create
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public HusbandryTypeDto saveHusbandryType(@RequestBody HusbandryTypeDto husbandryTypeDto) {
		return this.husbandryTypeService.saveHusbandryType(husbandryTypeDto, null);
	}

	// update
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public HusbandryTypeDto updateHusbandryType(@PathVariable("id") Long id,
			@RequestBody HusbandryTypeDto husbandryTypeDto) {
		return this.husbandryTypeService.saveHusbandryType(husbandryTypeDto, id);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public HusbandryTypeDto removeHusbandryType(@PathVariable Long id) {
		 return this.husbandryTypeService.removeHusbandryType(id);
	}
}
