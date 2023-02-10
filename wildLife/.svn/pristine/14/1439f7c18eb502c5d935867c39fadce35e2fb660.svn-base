
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

@Entity
@Table(name = "tbl_technical_staff")
@XmlRootElement
public class TechnicalStaff extends BaseObject{

	private static final long serialVersionUID = -3797504023854437947L;

	@Column(name = "name")
	private String name;
	
	@Column(name = "birth_date")
	private Date birthdate;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name="position")
	private String position;
	
	@Column(name="code")
	private String code;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "province_id")
	private FmsAdministrativeUnit province;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	private FmsAdministrativeUnit district;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commune_id")
	private FmsAdministrativeUnit commune;

	@ManyToOne
	@JoinColumn(name="administrativeUnit_id")
	private FmsAdministrativeUnit fmsAdministrativeUnit;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public FmsAdministrativeUnit getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnit province) {
		this.province = province;
	}

	public FmsAdministrativeUnit getDistrict() {
		return district;
	}

	public void setDistrict(FmsAdministrativeUnit district) {
		this.district = district;
	}

	public FmsAdministrativeUnit getCommune() {
		return commune;
	}

	public void setCommune(FmsAdministrativeUnit commune) {
		this.commune = commune;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public FmsAdministrativeUnit getFmsAdministrativeUnit() {
		return fmsAdministrativeUnit;
	}

	public void setFmsAdministrativeUnit(FmsAdministrativeUnit fmsAdministrativeUnit) {
		this.fmsAdministrativeUnit = fmsAdministrativeUnit;
	}
	
	
	
	
}