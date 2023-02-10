package com.globits.wl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_one_time_token")
@XmlRootElement
public class OneTimeToken extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="username")
	
	private String username;				//username

	@Column(name="token")
	private String token;					//key sinh ra khi người dùng yêu cầu lấy lại mật khẩu

	@Column(name="last_update_date")
	private Date lastUpdateDate;			//Thời gian cập nhật cuối cùng (cập nhật khi người dùng yêu cầu lấy lại mật khẩu)

	@Column(name="expried_time")
	private Date expriedTime;				//Thời gian hết hạn (cập nhật khi người dùng yêu cầu lấy lại mật khẩu ) Công thức: time now + thời gian hiệu lực của token

	@Column(name="last_reset_date")
	private Date lastResetDate;				//Thời gian reset cuối cùng (cập nhật khi người dùng đổi lại mật khẩu thành công)

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getLastResetDate() {
		return lastResetDate;
	}

	public void setLastResetDate(Date lastResetDate) {
		this.lastResetDate = lastResetDate;
	}

	public Date getExpriedTime() {
		return expriedTime;
	}

	public void setExpriedTime(Date expriedTime) {
		this.expriedTime = expriedTime;
	}
	
	
}

