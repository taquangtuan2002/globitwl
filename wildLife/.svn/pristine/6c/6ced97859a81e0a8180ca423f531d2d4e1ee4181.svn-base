package com.globits.wl.dto.report;

import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.functiondto.KeyDto;

public class ReportOfAnimalReportDataDto {
	private KeyDto<String, String> administrativeUnit;
	private AnimalReportDataDto data;
	private int level;
	private String orderCode;
	
	public KeyDto<String, String> getAdministrativeUnit() {
		return administrativeUnit;
	}
	public void setAdministrativeUnit(KeyDto<String, String> administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}
	public AnimalReportDataDto getData() {
		return data;
	}
	public void setData(AnimalReportDataDto data) {
		this.data = data;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public ReportOfAnimalReportDataDto() {
		
	}
	
	 @Override
    public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ReportOfAnimalReportDataDto))
			return false;
		ReportOfAnimalReportDataDto key = (ReportOfAnimalReportDataDto) o;
		if (key != null && key.getAdministrativeUnit() != null) {
			return this.administrativeUnit.equals(key.getAdministrativeUnit());
		}
		return false;
    }
    
    @Override
    public int hashCode() {
        return this.administrativeUnit.hashCode();
    }
}
