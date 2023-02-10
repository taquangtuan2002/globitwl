package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.domain.TechnicalStaff;
import com.globits.wl.dto.ExportAnimalDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.TechnicalStaffDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.service.TechnicalStaffService;


@RestController
@RequestMapping("api/technicaldtaff")
public class RestTechnicalStaffController {
	
	@Autowired
	private TechnicalStaffService service;
	
//	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getall", method = RequestMethod.GET)
	public List<TechnicalStaffDto> getList(){
		return service.getAll();
	}
	
	@RequestMapping(value="/create",method = RequestMethod.POST)
	public TechnicalStaffDto create(@RequestBody TechnicalStaffDto dto) {
		return service.saveStaffFromProvince(dto, null);
	}
	
	@RequestMapping(value="/update/{id}",method = RequestMethod.PUT)
	public TechnicalStaffDto undate(@PathVariable("id") Long id, @RequestBody TechnicalStaffDto dto) {
		return service.saveStaffFromProvince(dto, id);
	}

//	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
//	public TechnicalStaff save(@RequestBody TechnicalStaff technicalStaff, @PathVariable("id") Long id) {
//		return service.saveOne(technicalStaff);
//	}
	
	@RequestMapping(value="/getStaffFromProvince", method = RequestMethod.GET)
	public List<TechnicalStaffDto> getStaffFromProvince(@RequestBody FmsAdministrativeUnitDto dto){
		if(dto.getId()!=null) {
			Long id = dto.getId();
		return service.getStaffFromProvince(id);}
		return null;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public TechnicalStaffDto delete(@PathVariable("id") Long id) {
		TechnicalStaffDto dto = new TechnicalStaffDto();
		try {
			dto = service.deleteTechnicalStaff(id);
			return dto;
		}
		catch (Exception e) {
			dto.setCode("-1");
			return dto;
		}
	}
	
	@RequestMapping(value="/delete/ids", method = RequestMethod.DELETE)
	public TechnicalStaffDto deleteList(@RequestBody List<Long> ids){
		TechnicalStaffDto dto=new TechnicalStaffDto();
		try {
			service.deleteTechnicalStaffs(ids);		 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}

	
	@RequestMapping(value = "page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<TechnicalStaffDto> getListTechnicalStaff(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize) {
		return service.getPageTechnicalStaff(pageIndex, pageSize);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private TechnicalStaffDto getTechnicalStaffById(@PathVariable("id") String id) {
		TechnicalStaffDto dto= service.getTechnicalStaffById(new Long(id));
		return dto;
	}
	
//	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
//	private List<TechnicalStaffDto> getTechnicalStaffByName(@PathVariable("name") String name) {
//		List<TechnicalStaffDto> list= service.getTechnicalStaffByName(name);
//		return list;
//	}
	
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public Page<TechnicalStaffDto> getListSimpleByNameCode(@RequestBody SearchReportPeriodDto searchDto, @PathVariable("pageIndex")int pageIndex, @PathVariable("pageSize") int pageSize) {
		
		return service.searchDto(searchDto, pageIndex, pageSize);
	}
	
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public TechnicalStaffDto checkDuplicateCode(@PathVariable("code") String code) {
		return service.checkDuplicateCode(code);
	}
}