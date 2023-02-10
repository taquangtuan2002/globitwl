package com.globits.wl.dto;

import java.util.HashSet;
import java.util.Set;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.ReportFormAnimalEgg;
import com.globits.wl.domain.ReportFormAnimalEggPeriod;

public class ReportFormAnimalEggPeriodDto extends BaseObjectDto{
	private FarmDto farm;
	
	private Integer year;
	
	private Integer month;
	
	private Integer date;
	
	private Set<ReportFormAnimalEggDto>  reportFormAnimalEggs = new HashSet<ReportFormAnimalEggDto>();

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

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

	public Set<ReportFormAnimalEggDto> getReportFormAnimalEggs() {
		return reportFormAnimalEggs;
	}

	public void setReportFormAnimalEggs(Set<ReportFormAnimalEggDto> reportFormAnimalEggs) {
		this.reportFormAnimalEggs = reportFormAnimalEggs;
	}
	/**
	 * Trả về true thấy phần tử trong mảng báo cáo có tổng âm 
	 * */
	public Boolean getValidRequired() {
		Boolean flag = false;
		if(this.reportFormAnimalEggs != null && this.reportFormAnimalEggs.size() > 0) {
			for(ReportFormAnimalEggDto reportFormAnimalEggDto :this.reportFormAnimalEggs) {
				if( (reportFormAnimalEggDto.getQuantityChildIncrement() != null ? reportFormAnimalEggDto.getQuantityChildIncrement() : 0) - (reportFormAnimalEggDto.getQuantityChildSeparateCaptivity() != null ? reportFormAnimalEggDto.getQuantityChildSeparateCaptivity() : 0) < 0 ) {
					flag = true;
				}
				if( (reportFormAnimalEggDto.getQuantityChildHatch() != null ? reportFormAnimalEggDto.getQuantityChildHatch() : 0) - (reportFormAnimalEggDto.getQuantityChildDie() != null ? reportFormAnimalEggDto.getQuantityChildDie() : 0) < 0 ) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public ReportFormAnimalEggPeriodDto() {
		super();
	}
	
	public ReportFormAnimalEggPeriodDto(ReportFormAnimalEggPeriod entity) {
		super();
		this.id = entity.getId();
		this.year = entity.getYear();
		this.month = entity.getMonth();
		this.date = entity.getDate();
		this.farm = new FarmDto(entity.getFarm());
		if(entity.getReportFormAnimalEggs() != null) {
			for(ReportFormAnimalEgg reportFormAnimalEgg : entity.getReportFormAnimalEggs()) {
				if(reportFormAnimalEgg != null) {
					this.reportFormAnimalEggs.add(new ReportFormAnimalEggDto(reportFormAnimalEgg));
				}
			}
		}
		
	}
	
}
