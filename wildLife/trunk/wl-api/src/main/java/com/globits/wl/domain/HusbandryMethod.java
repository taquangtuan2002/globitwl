package com.globits.wl.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_husbandry_method")
@XmlRootElement
/*
 * Phương thức chăn nuôi
 * vd: chăn thả, nuôi nhốt
 */
public class HusbandryMethod extends BaseObject{
	private String code;
	private String name;
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
}
