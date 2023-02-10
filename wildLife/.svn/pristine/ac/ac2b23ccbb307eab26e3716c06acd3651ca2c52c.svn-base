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

import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.SeedLevelDto;
import com.globits.wl.dto.WaterSourceDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.WaterSourceSearchDto;
import com.globits.wl.service.SeedLevelService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/seed_level")
public class RestSeedLevelController {

	@Autowired
	private SeedLevelService seedLevelService;
	
	//get seed level by page
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<SeedLevelDto> getWaterSources(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return this.seedLevelService.getListSeedLevels(pageIndex, pageSize);
	}
	
	//get all seed level
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	public List<SeedLevelDto> getAll() {
		return this.seedLevelService.getAll();
	}
	
	//find seed level by id
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{seedLevelId}", method = RequestMethod.GET)
	public SeedLevelDto waterSourceById(@PathVariable("seedLevelId") Long id) {
		return this.seedLevelService.seedLevelById(id);

	}
	// create
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public SeedLevelDto saveWaterSource(@RequestBody SeedLevelDto seedLevelDto) {
		return this.seedLevelService.saveSeedLevel(seedLevelDto, null);
	}

	// update
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public SeedLevelDto updateSeedLevel(@PathVariable("id") Long id, @RequestBody SeedLevelDto seedLevelDto) {
		return this.seedLevelService.saveSeedLevel(seedLevelDto, id);
	}
	//delete
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public SeedLevelDto removeSeedLevel(@PathVariable Long id) {
		SeedLevelDto dto=new SeedLevelDto();
		try {
			dto=this.seedLevelService.removeSeedLevel(id);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	//find by text
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)
	public ResponseEntity <Page<SeedLevelDto>> getSeedLevelBySearchDto(@RequestBody SearchDto searchDto, @PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<SeedLevelDto> results = this.seedLevelService.searchSeedLevelBySearchDto(searchDto, pageIndex, pageSize);
		return new ResponseEntity<Page<SeedLevelDto>>(results, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value = "/checkCode",method = RequestMethod.POST)
	public SeedLevelDto checkDuplicateCode(@RequestBody String code) {
		return seedLevelService.checkDuplicateCode(code);
	}
}
