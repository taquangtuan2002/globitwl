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

import com.globits.wl.dto.LiveStockProductDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.LiveStockProductService;
import com.globits.wl.service.OriginalService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/liveStockProduct")
public class RestLiveStockProductController {
	@Autowired
	private LiveStockProductService liveStockProductService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/pagination/getall/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<LiveStockProductDto> getLiveStockProductDtos(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.liveStockProductService.getAllWidthPagination(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getalldtos", method = RequestMethod.GET)
	public List<LiveStockProductDto> getLiveStockProductDtos() {
		return this.liveStockProductService.getAll();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public LiveStockProductDto getById(@PathVariable("id") Long id) {
		return this.liveStockProductService.getById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public LiveStockProductDto save(@RequestBody LiveStockProductDto dto) {
		return this.liveStockProductService.save(dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public LiveStockProductDto deleteById(@PathVariable Long id) {
		LiveStockProductDto dto=new LiveStockProductDto();
		try {
			this.liveStockProductService.deleteById(id);	 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/ids", method = RequestMethod.DELETE)
	public LiveStockProductDto deleteByIds(@RequestBody Set<Long> ids) {
		LiveStockProductDto dto=new LiveStockProductDto();
		try {
			this.liveStockProductService.deleteByIds(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<LiveStockProductDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<LiveStockProductDto> results = this.liveStockProductService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<LiveStockProductDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public LiveStockProductDto checkDuplicateCode(@PathVariable("code") String code) {
		return liveStockProductService.checkDuplicateCode(code);
	}
}
