package com.globits.wl.rest;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.domain.CmsArticleType;
import com.globits.cms.dto.CmsArticleTypeDto;
import com.globits.cms.service.CmsArticleTypeService;

@RestController
@RequestMapping("/api/cms/articletype")
public class RestFmsCmsArticleTypeController {
	@Autowired
	private CmsArticleTypeService cmsArticleTypeService;
	
	@RequestMapping(value = "/{ArticleTypeId}", method = RequestMethod.GET)
	public CmsArticleTypeDto getArticleTypeById(@PathVariable("ArticleTypeId") String articleTypeId) {
		CmsArticleType at=cmsArticleTypeService.findById(new Long(articleTypeId));
		return new CmsArticleTypeDto(at);
	}
	@RequestMapping(value = "page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CmsArticleTypeDto> getArticleType(@PathVariable int pageIndex, @PathVariable int pageSize){
		return cmsArticleTypeService.getListArticleType(pageIndex, pageSize);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@Secured({"ROLE_ADMIN","ROLE_DLP"})
	public CmsArticleTypeDto addArticleType(@RequestBody CmsArticleTypeDto dto) {
		return cmsArticleTypeService.addArticleType(dto);
	}
	
	@RequestMapping(value = "/removeList", method = RequestMethod.DELETE)
	@Secured({"ROLE_ADMIN","ROLE_DLP"})
	public boolean removeArticleType(@RequestBody List<CmsArticleTypeDto> dtos) {
		boolean at =cmsArticleTypeService.removeList(dtos);
		return at;
	}
	@RequestMapping(value = "/checkCode/{code}",method = RequestMethod.GET)
	public CmsArticleTypeDto checkDuplicateCode(@PathVariable("code") String code) {
		return cmsArticleTypeService.checkDuplicateCode(code);
	}
}
