package com.globits.wl.domain;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_product_target")
@XmlRootElement
/*
 * Hướng sản phẩm. 
 * VD:hướng thịt, hướng trứng, hướng sữa
 */
public class ProductTarget extends BaseObject{
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private ProductTarget parent;
	
	public ProductTarget getParent() {
		return parent;
	}
	public void setParent(ProductTarget parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ProductTarget() {
		this.setUuidKey(UUID.randomUUID());
	}
}
