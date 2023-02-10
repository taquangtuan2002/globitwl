package com.globits.wl.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.FarmReportPeriod;

public class FarmReportPeriodDto extends BaseObjectDto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FarmDto farm;
	
	private Long farmId;
	
	private String title;

	private int type;

	private Integer year;//Năm của kỳ báo cáo

	private Integer quarter;//Tháng của kỳ báo cáo

	private Integer month;//Tháng của kỳ báo cáo

	private Integer day;//Tháng của kỳ báo cáo
	
	private List<AnimalReportDataFormDto> listAnimalReportDataFormDto;
	
	public FarmDto getFarm() {
		return farm;
	}
	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}
	public Long getFarmId() {
		return farmId;
	}
	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getQuarter() {
		return quarter;
	}
	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	
	public List<AnimalReportDataFormDto> getListAnimalReportDataFormDto() {
		return listAnimalReportDataFormDto;
	}
	public void setListAnimalReportDataFormDto(List<AnimalReportDataFormDto> listAnimalReportDataFormDto) {
		this.listAnimalReportDataFormDto = listAnimalReportDataFormDto;
	}
	public FarmReportPeriodDto() {
		super();		
	}
	public FarmReportPeriodDto(FarmReportPeriod entity) {
		super();		
		if(entity!=null) {
			this.id = entity.getId();
			if(entity.getFarm()!=null) {
				this.farm = new FarmDto();
				this.farm.setId(entity.getFarm().getId());
				this.farm.setName(entity.getFarm().getName());
				this.farm.setCode(entity.getFarm().getCode());
				this.farm.setYearRegistration(entity.getFarm().getYearRegistration());
			}
			this.title = entity.getTitle();
			this.year = entity.getYear();
			this.quarter = entity.getQuarter();
			this.month = entity.getMonth();
			this.day = entity.getDay();
		}
	}
	public FarmReportPeriodDto(FarmReportPeriod entity, boolean sinple) {
		super();
		if(entity!=null) {
			this.id = entity.getId();
			if(entity.getFarm()!=null) {
				this.farm = new FarmDto();
				this.farm.setId(entity.getFarm().getId());
				this.farm.setName(entity.getFarm().getName());
				this.farm.setCode(entity.getFarm().getCode());
				this.farm.setYearRegistration(entity.getFarm().getYearRegistration());
			}
			this.title = entity.getTitle();
			this.year = entity.getYear();
			this.quarter = entity.getQuarter();
			this.month = entity.getMonth();
			this.day = entity.getDay();
			this.type = entity.getType();
		}
	}
}
