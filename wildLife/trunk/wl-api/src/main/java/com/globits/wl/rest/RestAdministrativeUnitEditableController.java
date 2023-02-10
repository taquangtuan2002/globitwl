package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.AdministrativeUnitEditableDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.service.AdministrativeUnitEditableService;
import com.globits.wl.utils.WLConstant;

import java.util.List;

@RestController
@RequestMapping("api/AdministrativeUnitEditable")
public class RestAdministrativeUnitEditableController {
	
	@Autowired
	private AdministrativeUnitEditableService service;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public Page<AdministrativeUnitEditableDto> getSearchByPage(@RequestBody AnimalReportDataSearchDto searchDto) {
		return service.findPage(searchDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public AdministrativeUnitEditableDto saveOrUpdate(@RequestBody AdministrativeUnitEditableDto dto) {
		return service.saveOrUpdate(dto);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getAdministrativeUnitEditableById/{id}", method = RequestMethod.GET)
	public AdministrativeUnitEditableDto getAdministrativeUnitEditableById(@PathVariable Long id) {
		return service.getAdministrativeUnitEditableById(id);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getAdministrativeUnitEditableByAdminUnit/{id}", method = RequestMethod.GET)
	public List<AdministrativeUnitEditableDto> getAdministrativeUnitEditableByAdminUnit(@PathVariable Long id) {
		return service.getAdministrativeUnitEditableByAdminUnit(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public boolean deleteById(@PathVariable Long id) {
		try {
			this.service.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
