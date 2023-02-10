package com.globits.wl.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_husbandry_type")
@XmlRootElement
/*
 * Hình thức chăn nuôi
 */
public class HusbandryType extends BaseObject {
	@OneToMany(mappedBy = "husbandryType", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<FarmHusbandryType> farmHusbandryType = new HashSet<FarmHusbandryType>();
	private String name;
	private String code;

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

	public Set<FarmHusbandryType> getFarmHusbandryType() {
		return farmHusbandryType;
	}

	public void setFarmHusbandryType(Set<FarmHusbandryType> farmHusbandryType) {
		this.farmHusbandryType = farmHusbandryType;
	}

}
