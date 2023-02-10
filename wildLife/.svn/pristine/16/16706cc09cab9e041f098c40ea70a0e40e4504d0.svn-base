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

import com.globits.core.service.FileDescriptionService;
import com.globits.wl.dto.DrugDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.DrugService;

@RestController
@RequestMapping("/api/drug")
public class RestDrugCotroller {
	@Autowired
	private DrugService drugService;

	@RequestMapping(value = "page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<DrugDto> getPageDto(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.drugService.getPageDto(pageIndex, pageSize);
	}

	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	private List<DrugDto> getAll() {
		return this.drugService.getAllDto();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private DrugDto getBranById(@PathVariable("id") Long id) {
		return this.drugService.getDrugById(id);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private DrugDto deleteById(@PathVariable("id") Long id) {
		DrugDto dto=new DrugDto();
		try {
			dto=this.drugService.deleteById(id);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private DrugDto deleteByIds(@RequestBody List<Long> ids) {
		DrugDto dto=new DrugDto();
		try {
			this.drugService.deleteByIds(ids);
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private DrugDto createBran(@RequestBody DrugDto dto) {
		return this.drugService.createDrug(dto);
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private DrugDto updateBran(@PathVariable("id") Long id,@RequestBody DrugDto dto) {
		return this.drugService.updateDrug(id,dto);
	}
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<DrugDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<DrugDto> results = this.drugService.searchDrugDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<DrugDto>>(results, HttpStatus.OK);
	}
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public DrugDto checkDuplicateCode(@PathVariable("code") String code) {
		return drugService.checkDuplicateCode(code);
	}

}
