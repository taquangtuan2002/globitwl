package com.globits.wl.dto;

import java.util.Date;

import com.globits.core.dto.BaseObjectDto;
import com.globits.security.dto.UserDto;
import com.globits.wl.domain.UserViewedNotification;

public class UserViewedNotificationDto extends BaseObjectDto{
	
	private Date viewedDate;
	
	private boolean viewed;
	
	private Date sendDate;
	
	private UserDto user;
	
	private SystemMessageDto systemMessage;

	public Date getViewedDate() {
		return viewedDate;
	}

	public void setViewedDate(Date viewedDate) {
		this.viewedDate = viewedDate;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public SystemMessageDto getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(SystemMessageDto systemMessage) {
		this.systemMessage = systemMessage;
	}

	public UserViewedNotificationDto() {
		super();
	}

	public UserViewedNotificationDto(UserViewedNotification entity) {
		super();
		this.id= entity.getId();
		this.viewedDate=entity.getViewedDate();
		this.viewed=entity.isViewed();
		this.sendDate=entity.getSendDate();
		if(entity.getUser()!=null && entity.getUser().getId()!=null) {
			this.user=new UserDto();
			this.user.setId(entity.getUser().getId());
			this.user.setUsername(entity.getUser().getUsername());
			this.user.setEmail(entity.getUser().getEmail());
		}
		if(entity.getSystemMessage()!=null && entity.getSystemMessage().getId()!=null) {
			this.systemMessage= new SystemMessageDto();
			this.systemMessage.setId(entity.getSystemMessage().getId());
			this.systemMessage.setContent(entity.getSystemMessage().getContent());
			this.systemMessage.setRecordId(entity.getSystemMessage().getRecordId());
			this.systemMessage.setRoles(entity.getSystemMessage().getRoles());
			this.systemMessage.setTitle(entity.getSystemMessage().getTitle());
			this.systemMessage.setType(entity.getSystemMessage().getType());
			
			this.systemMessage.setSender(new UserDto());
			this.systemMessage.getSender().setId(entity.getSystemMessage().getSender().getId());
			this.systemMessage.getSender().setUsername(entity.getSystemMessage().getSender().getUsername());
			this.systemMessage.getSender().setEmail(entity.getSystemMessage().getSender().getEmail());
			
			this.systemMessage.setStatus(entity.getSystemMessage().getStatus());
			this.systemMessage.setTableName(entity.getSystemMessage().getTableName());
		}
	}
	
	

}
