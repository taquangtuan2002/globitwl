package com.globits.wl.dto.functiondto;

import java.util.Date;
import java.util.List;

public class AnimalReportDataSearchDto {
	private Long id;
	private Long provinceId;
	private Long districtId;
	private Long communeId;
	private String provinceName;
	private String districtName;
	private String communeName;
	private Integer year;
	private Integer quarter;
	private Integer month;
	private Integer day;
	private Long farmId;
	private Long animalId;
	private List<String>  listCites;
	
	private List<Long> animalIds;
	
	private Date fromDate;
	private Date toDate;
	private String nameOrCode;
	
	private String animalClass;
	//List animal class
	private List<String> listAnimalClass;
	private String animalOrdo;
	//List ordo class
	private List<String> listAnimalOrdo;
	private String animalFamily;
	//List family
	private List<String> listAnimalFamily;
	private String animalCites;
	//list Animal Cites
	private List<String> listAnimalCites;
	
	private String animalVnlist;
	//list animal vnList
	private List<String> listAnimalVnlist;
	private String animalVnlist06;
	//list animal vnlist06
	private List<String> listAnimalVnlist06;
	private String animalGroup;
	//list animal group
	private List<String> listAnimalGroup;
	private Date dateReport;
	private Integer pageIndex;
	private Integer pageSize;
	private Long skipFPLD;
	private Boolean editable;
	private String text;
	private Boolean animalVnlist06ByForm;

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getEditable() {
		return editable;
	}
	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
	public Long getSkipFPLD() {
		return skipFPLD;
	}
	public void setSkipFPLD(Long skipFPLD) {
		this.skipFPLD = skipFPLD;
	}
	public String getAnimalGroup() {
		return animalGroup;
	}
	public void setAnimalGroup(String animalGroup) {
		this.animalGroup = animalGroup;
	}
	public String getAnimalVnlist() {
		return animalVnlist;
	}
	public void setAnimalVnlist(String animalVnlist) {
		this.animalVnlist = animalVnlist;
	}
	public String getAnimalVnlist06() {
		return animalVnlist06;
	}
	public void setAnimalVnlist06(String animalVnlist06) {
		this.animalVnlist06 = animalVnlist06;
	}
	public String getAnimalCites() {
		return animalCites;
	}
	public void setAnimalCites(String animalCites) {
		this.animalCites = animalCites;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Long> getAnimalIds() {
		return animalIds;
	}
	public void setAnimalIds(List<Long> animalIds) {
		this.animalIds = animalIds;
	}
	public List<String> getListCites() {
		return listCites;
	}
	public void setListCites(List<String> listCites) {
		this.listCites = listCites;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public Long getCommuneId() {
		return communeId;
	}
	public void setCommuneId(Long communeId) {
		this.communeId = communeId;
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
	public Long getFarmId() {
		return farmId;
	}
	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}
	public Long getAnimalId() {
		return animalId;
	}
	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getNameOrCode() {
		return nameOrCode;
	}
	public void setNameOrCode(String nameOrCode) {
		this.nameOrCode = nameOrCode;
	}
	public Date getDateReport() {
		return dateReport;
	}
	public void setDateReport(Date dateReport) {
		this.dateReport = dateReport;
	}
	public String getAnimalClass() {
		return animalClass;
	}
	public void setAnimalClass(String animalClass) {
		this.animalClass = animalClass;
	}
	public String getAnimalOrdo() {
		return animalOrdo;
	}
	public void setAnimalOrdo(String animalOrdo) {
		this.animalOrdo = animalOrdo;
	}
	public String getAnimalFamily() {
		return animalFamily;
	}
	public void setAnimalFamily(String animalFamily) {
		this.animalFamily = animalFamily;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getCommuneName() {
		return communeName;
	}
	public void setCommuneName(String communeName) {
		this.communeName = communeName;
	}

	public Boolean getAnimalVnlist06ByForm() {
		return animalVnlist06ByForm;
	}


	public void setAnimalVnlist06ByForm(Boolean animalVnlist06ByForm) {
		this.animalVnlist06ByForm = animalVnlist06ByForm;
	}
	public List<String> getListAnimalClass() {
		return listAnimalClass;
	}
	public void setListAnimalClass(List<String> listAnimalClass) {
		this.listAnimalClass = listAnimalClass;
	}
	public List<String> getListAnimalOrdo() {
		return listAnimalOrdo;
	}
	public void setListAnimalOrdo(List<String> listAnimalOrdo) {
		this.listAnimalOrdo = listAnimalOrdo;
	}
	public List<String> getListAnimalFamily() {
		return listAnimalFamily;
	}
	public void setListAnimalFamily(List<String> listAnimalFamily) {
		this.listAnimalFamily = listAnimalFamily;
	}
	public List<String> getListAnimalCites() {
		return listAnimalCites;
	}
	public void setListAnimalCites(List<String> listAnimalCites) {
		this.listAnimalCites = listAnimalCites;
	}
	public List<String> getListAnimalVnlist() {
		return listAnimalVnlist;
	}
	public void setListAnimalVnlist(List<String> listAnimalVnlist) {
		this.listAnimalVnlist = listAnimalVnlist;
	}
	public List<String> getListAnimalVnlist06() {
		return listAnimalVnlist06;
	}
	public void setListAnimalVnlist06(List<String> listAnimalVnlist06) {
		this.listAnimalVnlist06 = listAnimalVnlist06;
	}
	public List<String> getListAnimalGroup() {
		return listAnimalGroup;
	}
	public void setListAnimalGroup(List<String> listAnimalGroup) {
		this.listAnimalGroup = listAnimalGroup;
	}
	
}
