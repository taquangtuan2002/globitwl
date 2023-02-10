package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_link")
@XmlRootElement
/*
 *Link người dùng tạo để view ra 
 */
public class Link  extends BaseObject{
	private static final long serialVersionUID = 1L;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="hyper_link")
	private String hyperLink;
	@Column(name="description")
	private String description;
	
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
	public String getHyperLink() {
		return hyperLink;
	}
	public void setHyperLink(String hyperLink) {
		this.hyperLink = hyperLink;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Link() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
