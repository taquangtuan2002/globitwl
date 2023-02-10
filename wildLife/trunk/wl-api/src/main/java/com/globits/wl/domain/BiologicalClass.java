package com.globits.wl.domain;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_animal_class")
@XmlRootElement

public class BiologicalClass extends BaseObject{
	/**
	 * bảng lớp-bộ-họ
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name; // tên
	
	@Column(name="sci")
	private String sci; // tên latin
	
	@Column(name="type")
	private Integer type; // WLConstant.BiologicalClass 1: lớp, 2: bộ, 3: họ
	 
	@Column(name="code")
	private String code; // mã để tạo mã động vật
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private BiologicalClass parent;

	@JsonIgnore
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private Set<BiologicalClass> subBiologicals; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSci() {
		return sci;
	}

	public void setSci(String sci) {
		this.sci = sci;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BiologicalClass getParent() {
		return parent;
	}

	public void setParent(BiologicalClass parent) {
		this.parent = parent;
	}

	public Set<BiologicalClass> getSubBiologicals() {
		return subBiologicals;
	}

	public void setSubBiologicals(Set<BiologicalClass> subBiologicals) {
		this.subBiologicals = subBiologicals;
	}
}
