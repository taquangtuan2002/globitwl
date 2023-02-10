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

import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.SearchDto;
import com.globits.wl.dto.UserAttachmentsDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.service.UserAttachmentsService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/user_attachments")
public class RestUserAttachmentsController {
	
	@Autowired
	private UserAttachmentsService userAttachmentsService;


	@RequestMapping(value = "searchByPage", method = RequestMethod.POST)
	public Page<UserAttachmentsDto> searchByPage(@RequestBody SearchDto searchDto) {
		Page<UserAttachmentsDto> page = userAttachmentsService.searchByPage(searchDto);
		return page;
	}
	
	@RequestMapping(value = "searchByPageUniqueUser", method = RequestMethod.POST)
	public Page<UserAttachmentsDto> searchByPageUniqueUser(@RequestBody SearchReportPeriodDto searchDto) {
		Page<UserAttachmentsDto> page = userAttachmentsService.searchByPageUniqueUser(searchDto);
		return page;
	}


	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public List<UserAttachmentsDto> saveList(@RequestBody List<UserAttachmentsDto> listUserAttachments) {
		return userAttachmentsService.saveList(listUserAttachments);
	}	

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getByUserId/{userId}", method = RequestMethod.GET)
	public List<UserAttachmentsDto> getByUserId(@PathVariable Long userId) {
		return userAttachmentsService.getByUserId(userId);
	}
	
}
