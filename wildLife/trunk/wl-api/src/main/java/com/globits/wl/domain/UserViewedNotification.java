package com.globits.wl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;

@Entity
@Table(name="tbl_user_view_notification")
@XmlRootElement
public class UserViewedNotification extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="viewed_date")
	private Date viewedDate;
	
	@Column(name="viewed")
	private boolean viewed;
	
	@Column(name="send_date")
	private Date sendDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="system_message_id")
	private SystemMessage systemMessage;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SystemMessage getSystemMessage() {
		return systemMessage;
	}

	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
	}

	public UserViewedNotification() {
		super();
	}

	
	
}
