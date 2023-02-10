package com.globits.wl.dto;



import java.util.Date;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.TechnicalStaff;

public class TechnicalStaffDto extends BaseObjectDto {
	
	private Long id;
	private String name;
	private Date birthdate;
	private String gender;
	private String phoneNumber;
	private String email;
	private String position;
	private String code;
	
	private boolean isDuplicate;
	private String dupCode;
	
	private FmsAdministrativeUnitDto province;
	private FmsAdministrativeUnitDto district;
	private FmsAdministrativeUnitDto commune;
	private FmsAdministrativeUnitDto fmsAdministrativeUnit;
	
	public TechnicalStaffDto() {
		super();
	}
	
	public TechnicalStaffDto(TechnicalStaff entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.birthdate = entity.getBirthdate();
		this.gender = entity.getGender();
		this.phoneNumber = entity.getPhoneNumber();
		this.email = entity.getEmail();
		this.position = entity.getPosition();
		this.code = entity.getCode();
		if(entity.getProvince()!=null) {
			this.province = new FmsAdministrativeUnitDto(entity.getProvince());
			
		}
		if(entity.getDistrict()!=null) {
			this.district = new FmsAdministrativeUnitDto(entity.getDistrict());
		}
		if(entity.getCommune()!=null) {
			this.commune = new FmsAdministrativeUnitDto(entity.getCommune());
		}
		if(entity.getFmsAdministrativeUnit()!=null) {
			this.fmsAdministrativeUnit = new FmsAdministrativeUnitDto(entity.getFmsAdministrativeUnit());
		}
//		if(entity.getCommune()!=null) {
//			this.commune = new FmsAdministrativeUnitDto();
//			this.commune.setId(entity.getCommune().getId());
//			this.commune.setName(entity.getCommune().getName());
//		}
//		if(entity.getDistrict()!=null) {
//			this.district = new FmsAdministrativeUnitDto();
//			this.district.setId(entity.getDistrict().getId());
//			this.district.setName(entity.getDistrict().getName());
//	}
//		if(entity.getProvince()!=null) {
//			this.province = new FmsAdministrativeUnitDto();
//			this.province.setId(entity.getProvince().getId());
//			this.province.setName(entity.getProvince().getName());
//			
//		}

		
	}
	
	public TechnicalStaffDto(TechnicalStaff entity, boolean sinple) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.birthdate = entity.getBirthdate();
		this.gender = entity.getGender();
		this.phoneNumber = entity.getPhoneNumber();
		this.email = entity.getEmail();
		this.position = entity.getPosition();
		this.code = entity.getCode();
		if(entity.getProvince()!=null) {
			this.province = new FmsAdministrativeUnitDto(entity.getProvince());
			
		}
		if(entity.getDistrict()!=null) {
			this.district = new FmsAdministrativeUnitDto(entity.getDistrict());
		}
		if(entity.getCommune()!=null) {
			this.commune = new FmsAdministrativeUnitDto(entity.getCommune());
		}
		if(entity.getFmsAdministrativeUnit()!=null) {
			this.fmsAdministrativeUnit = new FmsAdministrativeUnitDto(entity.getFmsAdministrativeUnit());
		}
//		if(entity.getCommune()!=null) {
//		this.commune = new FmsAdministrativeUnitDto();
//		this.commune.setId(entity.getCommune().getId());
//		this.commune.setName(entity.getCommune().getName());
//	}
//	if(entity.getDistrict()!=null) {
//	this.district = new FmsAdministrativeUnitDto();
//	this.district.setId(entity.getDistrict().getId());
//	this.district.setName(entity.getDistrict().getName());
//}
//	if(entity.getProvince()!=null) {
//		this.province = new FmsAdministrativeUnitDto();
//		this.province.setId(entity.getProvince().getId());
//		this.province.setName(entity.getProvince().getName());
//		
//	}
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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

	public FmsAdministrativeUnitDto getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnitDto province) {
		this.province = province;
	}

	public FmsAdministrativeUnitDto getDistrict() {
		return district;
	}

	public void setDistrict(FmsAdministrativeUnitDto district) {
		this.district = district;
	}

	public FmsAdministrativeUnitDto getCommune() {
		return commune;
	}

	public void setCommune(FmsAdministrativeUnitDto commune) {
		this.commune = commune;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDuplicate() {
		return isDuplicate;
	}

	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public String getDupCode() {
		return dupCode;
	}

	public void setDupCode(String dupCode) {
		this.dupCode = dupCode;
	}

	public FmsAdministrativeUnitDto getFmsAdministrativeUnit() {
		return fmsAdministrativeUnit;
	}

	public void setFmsAdministrativeUnit(FmsAdministrativeUnitDto fmsAdministrativeUnit) {
		this.fmsAdministrativeUnit = fmsAdministrativeUnit;
	}
	
}