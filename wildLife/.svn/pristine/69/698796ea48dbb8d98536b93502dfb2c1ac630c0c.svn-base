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

import com.globits.wl.dto.ForestProductsListDetailDto;
import com.globits.wl.dto.AnimalCertificateDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.service.AnimalCertificateService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/animalCertificate")
public class RestAnimalCertificateCotroller {
	
	@Autowired
	private AnimalCertificateService  animalCertificateService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<AnimalCertificateDto> getPageDto(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.animalCertificateService.getPageDto(pageIndex, pageSize);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	private List<AnimalCertificateDto> getAll() {
		return this.animalCertificateService.getAllDto();
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private AnimalCertificateDto getAnimalCertificateById(@PathVariable("id") Long id) {
		return this.animalCertificateService.getAnimalCertificateById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private AnimalCertificateDto deleteById(@PathVariable("id") Long id) {
		AnimalCertificateDto dto=new AnimalCertificateDto();
		try {
			dto=this.animalCertificateService.deleteById(id);			 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
			// TODO: handle exception
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private AnimalCertificateDto deleteByIds(@RequestBody List<Long> ids) {
		AnimalCertificateDto dto=new AnimalCertificateDto();
		try {
			this.animalCertificateService.deleteByIds(ids);			 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
			// TODO: handle exception
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private AnimalCertificateDto createAnimalCertificate(@RequestBody AnimalCertificateDto dto) {
		Long id = null;
		return this.animalCertificateService.createOrUpdateAnimalCertificate(id,dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private AnimalCertificateDto updateAnimalCertificateDto(@PathVariable("id") Long id,@RequestBody AnimalCertificateDto dto) {
		return this.animalCertificateService.createOrUpdateAnimalCertificate(id,dto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<AnimalCertificateDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<AnimalCertificateDto> results = this.animalCertificateService.searchAnimalCertificateDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<AnimalCertificateDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public AnimalCertificateDto checkDuplicateCode(@PathVariable("code") String code) {
		return animalCertificateService.checkDuplicateCode(code);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getDetailsByStatusAC", method = RequestMethod.POST)
	public List<ForestProductsListDetailDto> getDetailsByStatusAC(@RequestBody AnimalReportDataSearchDto searchDto) {
		return animalCertificateService.findAllByAC(searchDto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public Page<AnimalCertificateDto> searchByPage(@RequestBody SearchReportPeriodDto searchDto) {
		Page<AnimalCertificateDto> page = this.animalCertificateService.searchByPage(searchDto);
		return page;
	}

}
