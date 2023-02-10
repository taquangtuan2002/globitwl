package com.globits.wl.dto.functiondto;

import java.util.List;



public class UserUserAdministrativeUnitDto {
	private String nameCode;
	private Long userId;
	private Long administrativeUnitId;
	private List<Long> listAdministrativeUnitId;
	private Long roleId;
	private List<Long> listRoleId;
	
	public List<Long> getListRoleId() {
		return listRoleId;
	}
	public void setListRoleId(List<Long> listRoleId) {
		this.listRoleId = listRoleId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getNameCode() {
		return nameCode;
	}
	public void setNameCode(String nameCode) {
		this.nameCode = nameCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAdministrativeUnitId() {
		return administrativeUnitId;
	}
	public void setAdministrativeUnitId(Long administrativeUnitId) {
		this.administrativeUnitId = administrativeUnitId;
	}
	public List<Long> getListAdministrativeUnitId() {
		return listAdministrativeUnitId;
	}
	public void setListAdministrativeUnitId(List<Long> listAdministrativeUnitId) {
		this.listAdministrativeUnitId = listAdministrativeUnitId;
	}
			
}
