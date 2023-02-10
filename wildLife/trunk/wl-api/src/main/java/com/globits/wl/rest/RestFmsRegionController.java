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

import com.globits.wl.dto.CertificateDto;
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.FmsRegionService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/fmsregion")
public class RestFmsRegionController {
	
	@Autowired
	private FmsRegionService fmsRegionService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<FmsRegionDto> getFmsRegions(@PathVariable int pageIndex, @PathVariable int pageSize){
		return this.fmsRegionService.getListFmsRegion(pageIndex, pageSize);
	} 
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<FmsRegionDto> getAll(){
		return this.fmsRegionService.getAll();
	} 
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{fmsRegionId}", method = RequestMethod.GET)
	public FmsRegionDto fmsRegionById(@PathVariable("fmsRegionId") Long id) {
		 return this.fmsRegionService.fmsRegionById(id);
		  
	}
	
	//create
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public FmsRegionDto saveFmsRegion(@RequestBody FmsRegionDto fmsRegionDto) {
		return this.fmsRegionService.saveFmsRegion(fmsRegionDto,null);
	}
	//update
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public FmsRegionDto updateRegion(@PathVariable("id") Long id ,@RequestBody FmsRegionDto fmsRegionDto) {
		return this.fmsRegionService.saveFmsRegion(fmsRegionDto,id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public FmsRegionDto removeFmsRegion(@PathVariable Long id) {
		FmsRegionDto dto=new FmsRegionDto();
		try {
			dto=this.fmsRegionService.removeFmsRegion(id);			 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
			// TODO: handle exception
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/deleteLists", method = RequestMethod.DELETE)
	public FmsRegionDto removeLists(@RequestBody List<Long> ids) {
		FmsRegionDto dto=new FmsRegionDto();
		try {
			 this.fmsRegionService.removeLists(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
			// TODO: handle exception
		}
		 
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<FmsRegionDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<FmsRegionDto> results = this.fmsRegionService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<FmsRegionDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public FmsRegionDto checkDuplicateCode(@PathVariable("code") String code) {
		return fmsRegionService.checkDuplicateCode(code);
	}
}

