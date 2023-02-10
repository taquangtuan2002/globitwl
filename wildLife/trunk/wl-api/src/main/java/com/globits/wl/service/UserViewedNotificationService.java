package com.globits.wl.service;

import java.util.List;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.UserViewedNotification;
import com.globits.wl.dto.UserViewedNotificationDto;

public interface UserViewedNotificationService extends GenericService<UserViewedNotification, Long>{
	
	List<UserViewedNotificationDto> findByCurrentUser();
	UserViewedNotificationDto saveOrUpdate(UserViewedNotificationDto dto);
}
