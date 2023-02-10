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

import com.globits.wl.dto.OwnershipDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.OwnershipService;
import com.globits.wl.utils.WLConstant;

@RestController("")
@RequestMapping("api/ownership")
public class RestOwnershipController {
	@Autowired
	private OwnershipService ownershipService;
	
	//get seed level by page
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/search/{pageIndex}/{pageSize}", method = RequestMethod.POST)
	public Page<OwnershipDto> getOwnerShips(@RequestBody SearchDto searchDto,@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize) {
		return this.ownershipService.getSearchOwnerShip(searchDto, pageIndex, pageSize);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<OwnershipDto> getAll() {
		return this.ownershipService.getAll();
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OwnershipDto getById(@PathVariable("id") Long id) {
		return this.ownershipService.getOwnershipDtoById(id);

	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(method = RequestMethod.POST)
	public OwnershipDto create(@RequestBody OwnershipDto OwnershipDto) {
		return this.ownershipService.save(OwnershipDto, null);
	}

	// update
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public OwnershipDto update(@PathVariable("id") Long id, @RequestBody OwnershipDto OwnershipDto) {
		return this.ownershipService.save(OwnershipDto, id);
	}
	//delete
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public OwnershipDto removeById(@PathVariable Long id) {
		OwnershipDto dto=new OwnershipDto();
		try {
			dto=this.ownershipService.removeById(id);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value = "/checkCode",method = RequestMethod.POST)
	public OwnershipDto checkDuplicateCode(@RequestBody String code) {
		return ownershipService.checkDuplicateCode(code);
	}
}
