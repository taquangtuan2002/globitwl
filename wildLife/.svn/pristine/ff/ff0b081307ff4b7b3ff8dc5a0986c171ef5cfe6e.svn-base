package com.globits.wl.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

/*
 * Lần tiêm
 */
@Entity
@Table(name = "tbl_injection_time")
@XmlRootElement
public class InjectionTime extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4062679915807465788L;
	private String code;
	private String name;
	/*
	 * Kiểu lần tiêm:
	 * 0: tiêm vắc xin phòng bệnh
	 * 1: tiêm kháng sinh chữa bệnh
	 * 2: khác
	 * FMSConstant.InjectionType
	 */
	private int type;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
