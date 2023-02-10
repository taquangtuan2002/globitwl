package com.globits.wl.dto.functiondto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.globits.cms.dto.BaseObjectDto;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.dto.RoleDto;
import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.dto.FmsAdministrativeUnitDto;

public class UserExportDto extends BaseObjectDto{
	private String displayName;
	private String username;
	private String email;
	private Set<RoleDto> roles;
	private Date dob;
	private FmsAdministrativeUnitDto province;
	private FmsAdministrativeUnitDto district;
	private FmsAdministrativeUnitDto ward;
	private String gender;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public FmsAdministrativeUnitDto getWard() {
		return ward;
	}
	public void setWard(FmsAdministrativeUnitDto ward) {
		this.ward = ward;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<RoleDto> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public UserExportDto() {
		super();
	}
	public UserExportDto(User user, UserAdministrativeUnit userAdministrativeUnit) {
		super();
		if(user.getPerson() != null) {
			this.displayName = user.getPerson().getDisplayName();
			this.dob = user.getPerson().getBirthDate();
			this.username = user.getUsername();
			this.email = user.getEmail();
			this.roles = new HashSet<RoleDto>();
			this.gender = user.getPerson().getGender();
			if(user.getRoles() != null) {
				for(Role role: user.getRoles()) {
					if(role!= null) {
						roles.add(new RoleDto(role));
					}
				}
			}
		}
		if(userAdministrativeUnit != null && userAdministrativeUnit.getAdminUnit() != null) {
			if(userAdministrativeUnit.getAdminUnit().getParent() == null) {
				this.province = new FmsAdministrativeUnitDto();
				this.province.setId(userAdministrativeUnit.getAdminUnit().getId());
				this.province.setCode(userAdministrativeUnit.getAdminUnit().getCode());
				this.province.setName(userAdministrativeUnit.getAdminUnit().getName());
			}else if(userAdministrativeUnit.getAdminUnit().getParent() != null) {
				if(userAdministrativeUnit.getAdminUnit().getParent().getParent() == null) {
					this.province = new FmsAdministrativeUnitDto();
					this.province.setId(userAdministrativeUnit.getAdminUnit().getParent().getId());
					this.province.setCode(userAdministrativeUnit.getAdminUnit().getParent().getCode());
					this.province.setName(userAdministrativeUnit.getAdminUnit().getParent().getName());
					
					this.district = new FmsAdministrativeUnitDto();
					this.district.setId(userAdministrativeUnit.getAdminUnit().getId());
					this.district.setCode(userAdministrativeUnit.getAdminUnit().getCode());
					this.district.setName(userAdministrativeUnit.getAdminUnit().getName());
				}else if(userAdministrativeUnit.getAdminUnit().getParent().getParent() != null) {
					this.province = new FmsAdministrativeUnitDto();
					this.province.setId(userAdministrativeUnit.getAdminUnit().getParent().getParent().getId());
					this.province.setCode(userAdministrativeUnit.getAdminUnit().getParent().getParent().getCode());
					this.province.setName(userAdministrativeUnit.getAdminUnit().getParent().getParent().getName());
					
					this.district = new FmsAdministrativeUnitDto();
					this.district.setId(userAdministrativeUnit.getAdminUnit().getParent().getId());
					this.district.setCode(userAdministrativeUnit.getAdminUnit().getParent().getCode());
					this.district.setName(userAdministrativeUnit.getAdminUnit().getParent().getName());
					
					this.ward = new FmsAdministrativeUnitDto();
					this.ward.setId(userAdministrativeUnit.getAdminUnit().getId());
					this.ward.setCode(userAdministrativeUnit.getAdminUnit().getCode());
					this.ward.setName(userAdministrativeUnit.getAdminUnit().getName());
				}
			}
		}

	}
	
}
