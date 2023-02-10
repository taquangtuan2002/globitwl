package com.globits.wl.rest;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.ForestProductsListDetailDto;
import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.service.ForestProductsListService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("api/forestproductslist")
public class RestForestProductsListController {
	@Autowired
	private ForestProductsListService forestProductsListService;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.POST)
	public ForestProductsListDto saveOrUpdate(@RequestBody ForestProductsListDto dto) {
		return forestProductsListService.saveOrUpdate(dto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ForestProductsListDto getById(@PathVariable Long id) {
		return forestProductsListService.getById(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/searchByPage/{pageIndex}/{pageSize}", method = RequestMethod.POST)
	public Page<ForestProductsListDto> getSearchByPage(@RequestBody AnimalReportDataSearchDto searchDto, @PathVariable("pageIndex")int pageIndex, @PathVariable("pageSize") int pageSize) {
		return forestProductsListService.getSearchByPage(searchDto, pageIndex, pageSize);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getall", method = RequestMethod.POST)
	public List<ForestProductsListDto> getAllBySearch(@RequestBody AnimalReportDataSearchDto searchDto) {
		return forestProductsListService.getAllBySearch(searchDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public Boolean deleteById(@PathVariable("id") Long id) {
		return forestProductsListService.deleteById(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="deleteLinkedByExportAnimalId/{id}", method = RequestMethod.DELETE)
	public Boolean deleteLinkedByExportAnimalId(@PathVariable("id") Long id) {
		// Set null trường của forestProductList
		return forestProductsListService.deleteLinkedByExportAnimalId(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/sendEmail", method = RequestMethod.POST)
	public ForestProductsListDto sendEmail(@RequestBody ForestProductsListDto dto) {
		return forestProductsListService.sendEmail(dto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getEmailAddressToSend", method = RequestMethod.POST)
	public Set<String> getEmailAddressToSend(@RequestBody ForestProductsListDto dto) {
		return forestProductsListService.getEmailAddressToSend(dto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getDetailsByStatusFP", method = RequestMethod.POST)
	public List<ForestProductsListDetailDto> getDetailsByStatusFP(@RequestBody AnimalReportDataSearchDto searchDto) {
		return forestProductsListService.findAllByFPL(searchDto);
	}
	
}
