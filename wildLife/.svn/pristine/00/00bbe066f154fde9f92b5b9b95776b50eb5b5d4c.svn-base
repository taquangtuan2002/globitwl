package com.globits.wl.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;

@Entity
@Table(name="tbl_system_message")
@XmlRootElement
// bảng thông báo 
public class SystemMessage extends BaseObject{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="conten")
	private String content;
	
	@Column(name="title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User sender;
	
	@Column(name=" table_name")
	private String tableName;
	
	@Column(name="record_id")
	private long recordId;
	
	@Column(name="status")
	private int status;// 0 là chưa gửi 1 là đã gửi
	
	@Column(name="type")
	private int type;//tat ca user duoc xem hoac la cu the user nao
	
	@Column(name="roles")
	private String roles;// vai trò nào được xem thông báo

	@OneToMany(mappedBy = "systemMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<FmsAdministrativeUnitSystemMessage> fmsAdministrativeUnits = new HashSet<FmsAdministrativeUnitSystemMessage>();
	
	@OneToMany(mappedBy = "systemMessage", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<UserViewedNotification> userViewedNotifications = new HashSet<UserViewedNotification>();
	
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

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
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

	public SystemMessage() {
		super();
	}

	public Set<FmsAdministrativeUnitSystemMessage> getFmsAdministrativeUnits() {
		return fmsAdministrativeUnits;
	}

	public void setFmsAdministrativeUnits(Set<FmsAdministrativeUnitSystemMessage> fmsAdministrativeUnits) {
		this.fmsAdministrativeUnits = fmsAdministrativeUnits;
	}

	public Set<UserViewedNotification> getUserViewedNotifications() {
		return userViewedNotifications;
	}

	public void setUserViewedNotifications(Set<UserViewedNotification> userViewedNotifications) {
		this.userViewedNotifications = userViewedNotifications;
	}
	

}
