package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.UserViewedNotificationDto;
import com.globits.wl.service.UserViewedNotificationService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("api/userviewednotification")
public class RestUserViewedNotificationController {
	@Autowired
	private UserViewedNotificationService userViewedNotificationService;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.POST)
	public UserViewedNotificationDto saveOrUpdate(@RequestBody UserViewedNotificationDto dto) {
		return userViewedNotificationService.saveOrUpdate(dto);
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(method = RequestMethod.GET)
	public List<UserViewedNotificationDto> getMessageByCurrentUser() {
		return userViewedNotificationService.findByCurrentUser();
	}
}
