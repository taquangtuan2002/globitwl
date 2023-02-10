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

import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.CertificateDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.CertificateService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/certificate")
public class RestCertificateCotroller {
	
	@Autowired
	private CertificateService  certificateService;

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CertificateDto> getPageDto(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return this.certificateService.getPageDto(pageIndex, pageSize);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	private List<CertificateDto> getAll() {
		return this.certificateService.getAllDto();
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private CertificateDto getCertificateById(@PathVariable("id") Long id) {
		return this.certificateService.getCertificateById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private CertificateDto deleteById(@PathVariable("id") Long id) {
		CertificateDto dto=new CertificateDto();
		try {
			dto=this.certificateService.deleteById(id);			 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
			// TODO: handle exception
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private CertificateDto deleteByIds(@RequestBody List<Long> ids) {
		CertificateDto dto=new CertificateDto();
		try {
			this.certificateService.deleteByIds(ids);			 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
			// TODO: handle exception
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private CertificateDto createCertificate(@RequestBody CertificateDto dto) {
		return this.certificateService.createCertificate(dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private CertificateDto updateCertificateDto(@PathVariable("id") Long id,@RequestBody CertificateDto dto) {
		return this.certificateService.updateCertificate(id,dto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<CertificateDto>> getListSimpleByNameCode(@RequestBody SearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<CertificateDto> results = this.certificateService.searchCertificateDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<CertificateDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public CertificateDto checkDuplicateCode(@PathVariable("code") String code) {
		return certificateService.checkDuplicateCode(code);
	}

}
