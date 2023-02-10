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

import com.globits.wl.dto.UnitDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.UnitService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/unit")
public class RestUnitController {
	@Autowired
	private UnitService unitService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/pagination/getall/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<UnitDto> getUnitDtos(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.unitService.getAllWidthPagination(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getalldtos", method = RequestMethod.GET)
	public List<UnitDto> getUnitDtos() {
		return this.unitService.getAll();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UnitDto getUnitDtoById(@PathVariable("id") Long id) {
		return this.unitService.getUnitById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public UnitDto saveUnit(@RequestBody UnitDto dto) {
		return this.unitService.saveUnit(dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public UnitDto deleteUnitById(@PathVariable Long id) {
		UnitDto dto=new UnitDto();
		try {
			boolean isDel=this.unitService.deleteUnitById(id);	 
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
	public UnitDto deleteUnitByIds(@RequestBody Set<Long> ids) {
		UnitDto dto=new UnitDto();
		try {
			this.unitService.deleteUnitByIds(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<UnitDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<UnitDto> results = this.unitService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<UnitDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public UnitDto checkDuplicateCode(@PathVariable("code") String code) {
		return unitService.checkDuplicateCode(code);
	}
}
