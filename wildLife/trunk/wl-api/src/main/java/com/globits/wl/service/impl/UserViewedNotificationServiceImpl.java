package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;
import com.globits.wl.domain.SystemMessage;
import com.globits.wl.domain.UserViewedNotification;
import com.globits.wl.dto.UserViewedNotificationDto;
import com.globits.wl.repository.SystemMessageRepository;
import com.globits.wl.repository.UserViewedNotificationRepository;
import com.globits.wl.service.UserViewedNotificationService;


@Service
public class UserViewedNotificationServiceImpl extends GenericServiceImpl<UserViewedNotification, Long> implements UserViewedNotificationService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private SystemMessageRepository systemMessageRepository;
	@Autowired
	private UserViewedNotificationRepository userViewedNotificationRepository;
	
	@Override
	public List<UserViewedNotificationDto> findByCurrentUser() {
		// TODO Auto-generated method stub
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser= null;
		List<UserViewedNotificationDto> listDto= new ArrayList<>();
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
			Set<Role> roles= currentUser.getRoles();
			boolean tmp= false;
			for(Role role:roles) {
				if(role.getName().equals("ROLE_SDAH")) {
					tmp=true;
					break;
				}
			}
			List<UserViewedNotification> listEntity= userViewedNotificationRepository.findByUser(currentUser.getId());
			if(tmp=true) {
				if(listEntity!=null && listEntity.size()>0) {
					for(UserViewedNotification entity: listEntity) {
						listDto.add(new UserViewedNotificationDto(entity));
					}
				}
			}			
		}
		return listDto;
	}

	@Override
	public UserViewedNotificationDto saveOrUpdate(UserViewedNotificationDto dto) {
		if(dto!=null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			UserViewedNotification entity= null;
			if(dto.getId()!=null) {
				entity= userViewedNotificationRepository.findOne(dto.getId());
			}
			if(entity==null) {
				entity= new UserViewedNotification();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			}else {
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);
			}
			SystemMessage systemMessage= null;
			if(dto.getSystemMessage()!=null && dto.getSystemMessage().getId()!=null) {
				systemMessage= systemMessageRepository.findOne(dto.getSystemMessage().getId());
				entity.setSystemMessage(systemMessage);
			}
			User user= null;
			if(dto.getUser()!=null && dto.getUser().getId()!=null) {
				user= userRepository.findOne(dto.getUser().getId());
				entity.setUser(user);
			}
			entity.setSendDate(dto.getSendDate());
			entity.setViewed(dto.isViewed());
			entity.setViewedDate(dto.getViewedDate());
			entity= userViewedNotificationRepository.save(entity);
			return new UserViewedNotificationDto(entity);
		}
		return dto;
	}

}
