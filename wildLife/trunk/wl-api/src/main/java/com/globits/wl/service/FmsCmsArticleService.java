package com.globits.wl.service;

import org.springframework.data.domain.Page;

import com.globits.cms.domain.CmsArticle;
import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;
import com.globits.core.utils.SecurityUtils;
//import com.globits.education.Const;

public interface FmsCmsArticleService  extends GenericService<CmsArticle, Long>{

	Page<CmsArticleDto> getPageCmsArticleOrderByViewDESC(int pageIndex, int pageSize);
//	SecurityUtils.isUserInRole(modifiedUser, Const.ROLE_ADMIN);

	void seeNews(Long id);

	Page<CmsArticleDto> searchArticleServiceBySearchDto(SearchDto search);

	Boolean deleteCmsArticleById(Long id);

	CmsArticleDto saveCmsArticle(CmsArticleDto dto, Long id);

	CmsArticleDto getCmsArticleDtoById(Long id);

	Page<CmsArticleDto> getPageCmsArticleDto(int pageIndex, int pageSize);
}
