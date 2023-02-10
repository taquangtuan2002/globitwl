package com.globits.wl.dto;


import com.globits.security.dto.UserDto;
import com.globits.wl.domain.UserAdministrativeUnit;

public class UserAdministrativeUnitDto  {
	
	private UserDto user;
	
	private FmsAdministrativeUnitDto adminUnit;

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public FmsAdministrativeUnitDto getAdminUnit() {
		return adminUnit;
	}

	public void setAdminUnit(FmsAdministrativeUnitDto adminUnit) {
		this.adminUnit = adminUnit;
	}	
	public UserAdministrativeUnitDto() {
		super();
	}

	public UserAdministrativeUnitDto(UserAdministrativeUnit uau) {
		if (uau != null) {
			if(uau.getUser()!=null){
				this.user=new UserDto(uau.getUser());
			}
			if(uau.getAdminUnit()!=null){
				this.adminUnit=new FmsAdministrativeUnitDto(uau.getAdminUnit(),true);
			}
			
		}
	}
}
