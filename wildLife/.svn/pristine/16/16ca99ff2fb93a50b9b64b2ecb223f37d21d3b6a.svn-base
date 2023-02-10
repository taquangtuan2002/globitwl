package com.globits.wl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsCategoryService;

@RestController
@RequestMapping("/api/cms/category")
public class RestFmsCmsCategoryController {
	
	@Autowired
	private CmsCategoryService cmsCategoryService;
	
	@RequestMapping(value="/page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CmsCategoryDto> getPageCmsCategoryDto(@PathVariable("pageIndex")int pageIndex, @PathVariable("pageSize")int pageSize){
		return cmsCategoryService.getPageCmsCategoryDto(pageIndex, pageSize);
	}
	
	@RequestMapping(value="/{categoryId}",method = RequestMethod.GET)
	public CmsCategoryDto getCmsCategoryDtoByCmsCategoryId(@PathVariable("categoryId")Long categoryId){
		return cmsCategoryService.getCmsCategoryDtoByCmsCategoryId(categoryId);
	}
	
	@RequestMapping(value="/create",method = RequestMethod.POST)
	@Secured({"ROLE_ADMIN","ROLE_DLP"})
	public CmsCategoryDto createCmsCategory(@RequestBody CmsCategoryDto dto){
		return cmsCategoryService.saveCmsCategory(dto, null);
	}
	
	@RequestMapping(value = "/update/{categoryId}", method = RequestMethod.PUT)
	@Secured({"ROLE_ADMIN","ROLE_DLP"})
	public CmsCategoryDto updateCmsCategory(@RequestBody CmsCategoryDto dto, @PathVariable("categoryId")Long categoryId){
		return cmsCategoryService.saveCmsCategory(dto, categoryId);
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	@Secured({"ROLE_ADMIN","ROLE_DLP"})
	public Boolean deleteCmsCategory(@PathVariable("id")Long id) {
		return cmsCategoryService.deleteCategoryById(id);
	}
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public Page<CmsCategoryDto> searchCmsCategory(@RequestBody SearchDto search) {
		return cmsCategoryService.getCmsCategoryBySearch(search);
	}
	
}
