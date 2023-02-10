package com.globits.wl.dto;

import java.util.HashSet;
import java.util.Set;

import com.globits.core.dto.BaseObjectDto;
import com.globits.security.dto.UserDto;
import com.globits.wl.domain.FmsAdministrativeUnitSystemMessage;
import com.globits.wl.domain.SystemMessage;
import com.globits.wl.domain.UserViewedNotification;

public class SystemMessageDto extends BaseObjectDto{

	private String content;

	private String title;

	private UserDto sender;
	
	private String tableName;
	
	private long recordId;

	private int status;// 0 là chưa gửi 1 là đã gửi

	private int type;//tat ca user duoc xem hoac la cu the user nao
	
	private String roles;// vai trò nào được xem thông báo
	
	//them dto cho 2 thuoc tinh set
	private Set<FmsAdministrativeUnitSystemMessageDto> fmsAdministrativeUnits;
	
	private Set<UserViewedNotificationDto> userViewedNotifications;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserDto getSender() {
		return sender;
	}

	public void setSender(UserDto sender) {
		this.sender = sender;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public SystemMessageDto() {
		super();
	}

	public Set<FmsAdministrativeUnitSystemMessageDto> getFmsAdministrativeUnits() {
		return fmsAdministrativeUnits;
	}

	public void setFmsAdministrativeUnits(Set<FmsAdministrativeUnitSystemMessageDto> fmsAdministrativeUnits) {
		this.fmsAdministrativeUnits = fmsAdministrativeUnits;
	}

	public Set<UserViewedNotificationDto> getUserViewedNotifications() {
		return userViewedNotifications;
	}

	public void setUserViewedNotifications(Set<UserViewedNotificationDto> userViewedNotifications) {
		this.userViewedNotifications = userViewedNotifications;
	}

	public SystemMessageDto(SystemMessage entity ) {
		super();
		this.id= entity.getId();
		this.type=entity.getType();
		this.title=entity.getTitle();
		this.tableName= entity.getTableName();
		this.status= entity.getStatus();
		this.roles= entity.getRoles();
		this.recordId= entity.getRecordId();
		this.content= entity.getContent();
		if(entity.getSender()!=null) {
			this.sender=new UserDto( entity.getSender());
		}
		if(entity.getFmsAdministrativeUnits()!=null && entity.getFmsAdministrativeUnits().size()>0) {
			this.fmsAdministrativeUnits= new HashSet<FmsAdministrativeUnitSystemMessageDto>();
			for(FmsAdministrativeUnitSystemMessage fms: entity.getFmsAdministrativeUnits() ) {
				FmsAdministrativeUnitSystemMessageDto dto= new FmsAdministrativeUnitSystemMessageDto(fms);
				this.fmsAdministrativeUnits.add(dto);
			}
		}
		if(entity.getUserViewedNotifications()!=null && entity.getUserViewedNotifications().size()>0) {
			this.userViewedNotifications= new HashSet<>();
			for(UserViewedNotification userview: entity.getUserViewedNotifications()) {
				UserViewedNotificationDto dto= new UserViewedNotificationDto(userview);
				this.userViewedNotifications.add(dto);
			}
		}
		
	}
	
	

}
