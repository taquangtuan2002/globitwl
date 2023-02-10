package com.globits.wl.dto;

public class DashboardVolatilityDto {

	 private Long totalListOfForestProductsByYear;
	 private Integer increaseVolatilityOfTheBase;//biến động tăng của cơ sở
	 private Integer increasedVariationOfTheIndividual;//biến động tăng của cá thể

	 private Integer decreasedVolatilityOfTheBase;//biến động giảm của cơ sở
	 private Integer decreasedIndividualVariability;//biến động giảm của cá thể
	 private Integer changesInTotalHouseholds;//tổng hô có biến động
	 private Long exportListOfForestProducts; //xuất bảng kê lâm sản
	 private Long importListOfForestProducts; //nhập bảng kê lâm sản
	 private Long totalEstablishmentStopsFarming; //Số cơ sở ngừng gây nuôi
	 private Long registrationFacilityInTheYear;//sớ cơ sở đăng ký nuôi mới trong năm
	 private Long theNumberHouseholdsRemainsTheSame; //số hộ giữ nguyên trong năm
	 
	 public DashboardVolatilityDto() {
		 
	 }

	public Long getTotalListOfForestProductsByYear() {
		return totalListOfForestProductsByYear;
	}

	public void setTotalListOfForestProductsByYear(Long totalListOfForestProductsByYear) {
		this.totalListOfForestProductsByYear = totalListOfForestProductsByYear;
	}

	public Integer getIncreaseVolatilityOfTheBase() {
		return increaseVolatilityOfTheBase;
	}

	public void setIncreaseVolatilityOfTheBase(Integer increaseVolatilityOfTheBase) {
		this.increaseVolatilityOfTheBase = increaseVolatilityOfTheBase;
	}

	public Integer getIncreasedVariationOfTheIndividual() {
		return increasedVariationOfTheIndividual;
	}

	public void setIncreasedVariationOfTheIndividual(Integer increasedVariationOfTheIndividual) {
		this.increasedVariationOfTheIndividual = increasedVariationOfTheIndividual;
	}

	public Integer getDecreasedVolatilityOfTheBase() {
		return decreasedVolatilityOfTheBase;
	}

	public void setDecreasedVolatilityOfTheBase(Integer decreasedVolatilityOfTheBase) {
		this.decreasedVolatilityOfTheBase = decreasedVolatilityOfTheBase;
	}

	public Integer getDecreasedIndividualVariability() {
		return decreasedIndividualVariability;
	}

	public void setDecreasedIndividualVariability(Integer decreasedIndividualVariability) {
		this.decreasedIndividualVariability = decreasedIndividualVariability;
	}

	public Integer getChangesInTotalHouseholds() {
		return changesInTotalHouseholds;
	}

	public void setChangesInTotalHouseholds(Integer changesInTotalHouseholds) {
		this.changesInTotalHouseholds = changesInTotalHouseholds;
	}

	public Long getExportListOfForestProducts() {
		return exportListOfForestProducts;
	}

	public void setExportListOfForestProducts(Long exportListOfForestProducts) {
		this.exportListOfForestProducts = exportListOfForestProducts;
	}

	public Long getImportListOfForestProducts() {
		return importListOfForestProducts;
	}

	public void setImportListOfForestProducts(Long importListOfForestProducts) {
		this.importListOfForestProducts = importListOfForestProducts;
	}

	public Long getTotalEstablishmentStopsFarming() {
		return totalEstablishmentStopsFarming;
	}

	public void setTotalEstablishmentStopsFarming(Long totalEstablishmentStopsFarming) {
		this.totalEstablishmentStopsFarming = totalEstablishmentStopsFarming;
	}

	public Long getRegistrationFacilityInTheYear() {
		return registrationFacilityInTheYear;
	}

	public void setRegistrationFacilityInTheYear(Long registrationFacilityInTheYear) {
		this.registrationFacilityInTheYear = registrationFacilityInTheYear;
	}

	public Long getTheNumberHouseholdsRemainsTheSame() {
		return theNumberHouseholdsRemainsTheSame;
	}

	public void setTheNumberHouseholdsRemainsTheSame(Long theNumberHouseholdsRemainsTheSame) {
		this.theNumberHouseholdsRemainsTheSame = theNumberHouseholdsRemainsTheSame;
	}
	
	 
}
