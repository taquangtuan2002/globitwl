package com.globits.wl.dto.functiondto;

import java.util.List;



public class DensityRegionDto {	
	private Long regionId;//id vùng
	private String regionName; //tên vùng
	private double densityRegion;//mật độ vùng (sum quantity province / sum area province);
	private double numberProvince;//số tỉnh vủa 1 vùng
	private List<FarmAdministrativeUnitDto> farmAdministrativeUnits;//các tỉnh thuộc vùng
	
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public double getDensityRegion() {
		return densityRegion;
	}
	public void setDensityRegion(double densityRegion) {
		this.densityRegion = densityRegion;
	}
	public List<FarmAdministrativeUnitDto> getFarmAdministrativeUnits() {
		return farmAdministrativeUnits;
	}
	public void setFarmAdministrativeUnits(
			List<FarmAdministrativeUnitDto> farmAdministrativeUnits) {
		this.farmAdministrativeUnits = farmAdministrativeUnits;
	}
	public double getNumberProvince() {
		return numberProvince;
	}
	public void setNumberProvince(double numberProvince) {
		this.numberProvince = numberProvince;
	}		
	
}
