package com.globits.wl.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.ReportFormAnimalGiveBirth;
import com.globits.wl.domain.ReportFormAnimalGiveBirthPeriod;

public class ReportFormAnimalGiveBirthPeriodDto extends BaseObjectDto {
	private FarmDto farm;
	private Integer year;
	private Integer month;
	private Integer date;
	private Set<ReportFormAnimalGiveBirthDto>  reportFormAnimalGiveBirths = new HashSet<ReportFormAnimalGiveBirthDto>();

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

	public Set<ReportFormAnimalGiveBirthDto> getReportFormAnimalGiveBirths() {
		return reportFormAnimalGiveBirths;
	}

	public void setReportFormAnimalGiveBirths(Set<ReportFormAnimalGiveBirthDto> reportFormAnimalGiveBirths) {
		this.reportFormAnimalGiveBirths = reportFormAnimalGiveBirths;
	}

	public ReportFormAnimalGiveBirthPeriodDto() {
		super();
	}
	
	public ReportFormAnimalGiveBirthPeriodDto(ReportFormAnimalGiveBirthPeriod entity) {
		super();
		this.id = entity.getId();
		this.year = entity.getYear();
		this.month = entity.getMonth();
		this.date = entity.getDate();
		if(entity.getFarm() != null) {
			this.farm = new FarmDto(entity.getFarm());
		}
		if(entity.getReportFormAnimalGiveBirths() != null && entity.getReportFormAnimalGiveBirths().size() > 0) {
			for(ReportFormAnimalGiveBirth reportFormAnimalGiveBirth :entity.getReportFormAnimalGiveBirths()) {
				if(reportFormAnimalGiveBirth != null) {
					this.reportFormAnimalGiveBirths.add(new ReportFormAnimalGiveBirthDto(reportFormAnimalGiveBirth));
				}
			}
		}
		
		
	}
	
}
