package com.globits.wl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.DoubtsOverlapDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.FarmService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/doubtsoverlap")

public class RestDoubtsOverlapController {
	@Autowired
	private FarmService farmService;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD",WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/searchByPage", method = RequestMethod.POST)
	public Page<DoubtsOverlapDto> searchByPageDoubtsOverlap(@RequestBody SearchDto dto){
		return this.farmService.searchByPageDoubtsOverlap(dto);
	}

}
