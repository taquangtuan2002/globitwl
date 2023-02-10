package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsArticleService;
import com.globits.cms.service.CmsCategoryService;

@RestController
@RequestMapping("/api/news")
public class RestNewsController {

	@Autowired
	CmsArticleService cmsArticleService;

	@Autowired
	CmsCategoryService cmsCategoryService;

	@RequestMapping(value = "cmsArticle/getPageCmsArticleOrderByViewDESC/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CmsArticleDto> getPageCmsArticleOrderByViewDESC(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<CmsArticleDto> page = cmsArticleService.getPageCmsArticleOrderByViewDESC(pageIndex, pageSize);
		return page;
	}

	@RequestMapping(value = "cmsArticle/getListArticleByPage/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CmsArticleDto> getListArticleByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<CmsArticleDto> page = cmsArticleService.getPageCmsArticleDto(pageIndex, pageSize);
		return page;
	}

	@RequestMapping(value = "cmsCategory/getCategoryById/{id}", method = RequestMethod.GET)
	public CmsCategoryDto getCategoryById(@PathVariable String id) {
		return cmsCategoryService.getCmsCategoryDtoByCmsCategoryId(new Long(id));
	}

	@RequestMapping(value = "cmsArticle/seeNews/{id}", method = RequestMethod.GET)
	public void seeNews(@PathVariable String id) {
		cmsArticleService.seeNews(new Long(id));
	}

	@RequestMapping(value = "cmsArticle/getAllArticleCategory/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CmsCategoryDto> getAllArticleCategory(@PathVariable int pageIndex, @PathVariable int pageSize) {
		Page<CmsCategoryDto> page = cmsCategoryService.getPageCmsCategoryDto(pageIndex, pageSize);
		return page;
	}

	@RequestMapping(value = "cmsArticle/searchListArticleByPage", method = RequestMethod.POST)
	public Page<CmsArticleDto> searchListArticleByPage(@RequestBody SearchDto searchDto) {
		Page<CmsArticleDto> page = cmsArticleService.searchArticleServiceBySearchDto(searchDto);
		return page;
	}

	@RequestMapping(value = "cmsArticle/getNewsById/{id}", method = RequestMethod.GET)
	public CmsArticleDto getNewsById(@PathVariable String id) {
		CmsArticleDto page = cmsArticleService.getCmsArticleDtoById(new Long(id));
		return page;
	}
}
