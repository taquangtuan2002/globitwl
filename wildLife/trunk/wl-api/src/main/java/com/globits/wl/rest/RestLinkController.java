package com.globits.wl.rest;

import java.util.List;
import java.util.Set;

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

import com.globits.wl.dto.LinkDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.LinkService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/link")
public class RestLinkController {
	@Autowired
	private LinkService linkService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/pagination/getall/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<LinkDto> getLinkDtos(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.linkService.getAllWidthPagination(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getalldtos", method = RequestMethod.GET)
	public List<LinkDto> getLinkDtos() {
		return this.linkService.getAll();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public LinkDto getLinkDtoById(@PathVariable("id") Long id) {
		return this.linkService.getLinkById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public LinkDto saveLink(@RequestBody LinkDto dto) {
		return this.linkService.saveLink(dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public LinkDto deleteLinkById(@PathVariable Long id) {
		LinkDto dto=new LinkDto();
		try {
			boolean isDel=this.linkService.deleteLinkById(id);	 
			if(isDel==false) {
				dto.setCode("-1");
			}
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/ids", method = RequestMethod.DELETE)
	public LinkDto deleteLinkByIds(@RequestBody Set<Long> ids) {
		LinkDto dto=new LinkDto();
		try {
			this.linkService.deleteLinkByIds(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<LinkDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<LinkDto> results = this.linkService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<LinkDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public LinkDto checkDuplicateCode(@PathVariable("code") String code) {
		return linkService.checkDuplicateCode(code);
	}
}
