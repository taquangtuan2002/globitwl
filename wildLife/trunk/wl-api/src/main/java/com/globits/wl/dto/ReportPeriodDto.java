package com.globits.wl.dto;

import java.util.HashSet;
import java.util.Set;

import com.globits.cms.dto.BaseObjectDto;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;

public class ReportPeriodDto extends BaseObjectDto {

	private FarmDto farm;
	
	private Integer year;
	
	private Integer month;
	
	private Integer date;

	private Integer type;// Kiểu form A: 1; B: 2

	private Set<ReportForm16Dto> reportItems = new HashSet<ReportForm16Dto>();

	private FmsAdministrativeUnitDto administrativeUnitDto;
	private FmsAdministrativeUnitDto districtDto;//huyện
	private FmsAdministrativeUnitDto provinceDto;// tỉnh
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Set<ReportForm16Dto> getReportItems() {
		return reportItems;
	}

	public void setReportItems(Set<ReportForm16Dto> reportItems) {
		this.reportItems = reportItems;
	}

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public FmsAdministrativeUnitDto getAdministrativeUnitDto() {
		return administrativeUnitDto;
	}

	public void setAdministrativeUnitDto(FmsAdministrativeUnitDto administrativeUnitDto) {
		this.administrativeUnitDto = administrativeUnitDto;
	}

	public FmsAdministrativeUnitDto getDistrictDto() {
		return districtDto;
	}

	public void setDistrictDto(FmsAdministrativeUnitDto districtDto) {
		this.districtDto = districtDto;
	}

	public FmsAdministrativeUnitDto getProvinceDto() {
		return provinceDto;
	}

	public void setProvinceDto(FmsAdministrativeUnitDto provinceDto) {
		this.provinceDto = provinceDto;
	}

	public ReportPeriodDto() {
		super();
	}

	public ReportPeriodDto(ReportPeriod entity) {
		super();
		if (entity != null) {
			this.setId(entity.getId());
			this.type = entity.getType();
			this.date = entity.getDate();
			this.month = entity.getMonth();
			this.year =entity.getYear();
			if (entity.getFarm() != null) {
				this.farm = new FarmDto(entity.getFarm(), false);
			}
			
			if (entity.getReportItems() != null && entity.getReportItems().size() > 0) {
				this.reportItems = new HashSet<ReportForm16Dto>();
				for (ReportForm16 reportForm16 : entity.getReportItems()) {
					this.reportItems.add(new ReportForm16Dto(reportForm16));
				}
			}
			if(entity.getAdministrativeUnit()!=null) {
				this.administrativeUnitDto = new FmsAdministrativeUnitDto();
				this.administrativeUnitDto.setId(entity.getAdministrativeUnit().getId());
				this.administrativeUnitDto.setCode(entity.getAdministrativeUnit().getCode());
				this.administrativeUnitDto.setName(entity.getAdministrativeUnit().getName());
			}
			if(entity.getDistrict()!=null) {
				this.districtDto = new FmsAdministrativeUnitDto();
				this.districtDto.setId(entity.getDistrict().getId());
				this.districtDto.setCode(entity.getDistrict().getCode());
				this.districtDto.setName(entity.getDistrict().getName());
			}
			if(entity.getProvince()!=null) {
				this.provinceDto = new FmsAdministrativeUnitDto();
				this.provinceDto.setId(entity.getProvince().getId());
				this.provinceDto.setCode(entity.getProvince().getCode());
				this.provinceDto.setName(entity.getProvince().getName());
			}
		}
	}

}
