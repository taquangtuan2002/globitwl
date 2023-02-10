package com.globits.wl.dto.functiondto;
import java.util.List;

public class ReportAccordingToTheRedBookDto {
	private Integer totalNumberOfCamps;	//Số trại
	
	private Integer totalNumberOfChildren;//Tổng số con
	
	private String vnList06;//VnList-06
	//List vnList06
	private List<String> vnList06s;
	
	private Integer year;//Năm
	
	private String animalName;// tên loài vật
	
	private Integer totalAnimalByYear;// Tổng số loài theo năm
	
	public String getAnimalName() {
		return animalName;
	}
	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}
	
	public Integer getTotalAnimalByYear() {
		return totalAnimalByYear;
	}
	public void setTotalAnimalByYear(Integer totalAnimalByYear) {
		this.totalAnimalByYear = totalAnimalByYear;
	}
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
	public String getVnList06() {
		if(vnList06==null) {
			vnList06="Không xác định";
		}
		return vnList06;
	}
	public void setVnList06(String vnList06) {
		this.vnList06 = vnList06;
	}
	
	//ListVN06
	public List<String> getVnList06s() {

		return vnList06s;
	}
	public void setVnList06s(List<String> vnList06s) {
		this.vnList06s = vnList06s;
	}
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public ReportAccordingToTheRedBookDto() {
		super();
	}
		
	public ReportAccordingToTheRedBookDto(Integer total,String animalName, Integer year) {
		super();
		this.totalAnimalByYear = total;
		this.animalName = animalName;
		this.year = year;
	}
	
	public ReportAccordingToTheRedBookDto(Integer totalNumberOfCamps, Integer totalNumberOfChildren, String vnList06, List<String> vnList06s,
			Integer year) {
		super();
		this.totalNumberOfCamps = totalNumberOfCamps;
		this.totalNumberOfChildren = totalNumberOfChildren;
		this.vnList06 = vnList06;
		this.vnList06s = vnList06s;
		this.year = year;
	}

}
