package com.globits.wl.dto.functiondto;

import org.springframework.util.StringUtils;

public class ReportQuantityByCategoryCitesDto {
	private Integer totalNumberOfCamps;	//Số trại
	
	private Integer totalNumberOfChildren;//Tổng số con
	
	private String cites;//Cites

	private Integer year;//Năm
	
	private String animalName;// tên loài vật
	
	private Long totalAnimalByYear;// Tổng số loài theo năm

	public Integer getTotalNumberOfCamps() {
		return totalNumberOfCamps;
	}

	public void setTotalNumberOfCamps(Integer totalNumberOfCamps) {
		this.totalNumberOfCamps = totalNumberOfCamps;
	}

	public Integer getTotalNumberOfChildren() {
		return totalNumberOfChildren;
	}

	public void setTotalNumberOfChildren(Integer totalNumberOfChildren) {
		this.totalNumberOfChildren = totalNumberOfChildren;
	}

	public String getCites() {
		if(!StringUtils.hasText(this.cites)) {
			cites="Không xác định";
		}
		return cites;
	}

	public void setCites(String cites) {
		this.cites = cites;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getAnimalName() {
		return animalName;
	}

	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

	public Long getTotalAnimalByYear() {
		return totalAnimalByYear;
	}

	public void setTotalAnimalByYear(Long totalAnimalByYear) {
		this.totalAnimalByYear = totalAnimalByYear;
	}

	public ReportQuantityByCategoryCitesDto() {
		super();
	}
	
	public ReportQuantityByCategoryCitesDto(Integer totalNumberOfCamps, Integer totalNumberOfChildren, String cites,
			Integer year) {
		this.totalNumberOfCamps = totalNumberOfCamps;
		this.totalNumberOfChildren = totalNumberOfChildren;
		this.cites = cites;
		this.year = year;
	}
	
}
