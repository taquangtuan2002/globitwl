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

import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.OriginalService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/original")
public class RestOriginalController {
	@Autowired
	private OriginalService originalService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/pagination/getall/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<OriginalDto> getOriginalDtos(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.originalService.getAllWidthPagination(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getalldtos", method = RequestMethod.GET)
	public List<OriginalDto> getOriginalDtos() {
		return this.originalService.getAll();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OriginalDto getPageOriginalDtoById(@PathVariable("id") Long id) {
		return this.originalService.getOriginalById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public OriginalDto saveOriginal(@RequestBody OriginalDto dto) {
		return this.originalService.saveOriginal(dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public OriginalDto deleteOriginById(@PathVariable Long id) {
		OriginalDto dto=new OriginalDto();
		try {
			this.originalService.deleteOriginById(id);	 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/ids", method = RequestMethod.DELETE)
	public OriginalDto deleteOriginByIds(@RequestBody Set<Long> ids) {
		OriginalDto dto=new OriginalDto();
		try {
			this.originalService.deleteOriginByIds(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<OriginalDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<OriginalDto> results = this.originalService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<OriginalDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public OriginalDto checkDuplicateCode(@PathVariable("code") String code) {
		return originalService.checkDuplicateCode(code);
	}
}
