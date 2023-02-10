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
import com.globits.wl.dto.WaterSourceDto;
import com.globits.wl.dto.functiondto.WaterSourceSearchDto;
import com.globits.wl.service.WaterSourceService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/watersource")
public class RestWaterSourceController {

	@Autowired
	private WaterSourceService waterSourceService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<WaterSourceDto> getWaterSources(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return this.waterSourceService.getListWaterSources(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<WaterSourceDto> getAll() {
		return this.waterSourceService.getAll();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{waterSourceId}", method = RequestMethod.GET)
	public WaterSourceDto waterSourceById(@PathVariable("waterSourceId") Long id) {
		return this.waterSourceService.waterSourceById(id);

	}

	// create
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public WaterSourceDto saveWaterSource(@RequestBody WaterSourceDto waterSourceDto) {
		return this.waterSourceService.saveWaterSource(waterSourceDto, null);
	}

	// update
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public WaterSourceDto updateRegion(@PathVariable("id") Long id, @RequestBody WaterSourceDto waterSourceDto) {
		return this.waterSourceService.saveWaterSource(waterSourceDto, id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public WaterSourceDto removeWaterSource(@PathVariable Long id) {
		WaterSourceDto dto=new WaterSourceDto();
		try {
			dto=this.waterSourceService.removeWaterSource(id);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<WaterSourceDto>> getListSimpleByNameCode(@RequestBody WaterSourceSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<WaterSourceDto> results = this.waterSourceService.searchWaterSourceDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<WaterSourceDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public WaterSourceDto checkDuplicateCode(@PathVariable("code") String code) {
		return waterSourceService.checkDuplicateCode(code);
	}
}
