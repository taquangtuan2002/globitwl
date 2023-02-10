package com.globits.wl.dto.report;

import com.globits.wl.dto.ImportExportAnimalDto;

public class ReportSeedLevelProducTargetDto extends ImportExportAnimalDto {	
	
	private Long provinceId;//tỉnh
	private String provinceName;
	
	private Long regionId;//vùng
	private String regionName;
	
	private Double amountChickenMeat;//Số lượng gà thit
	private Double amountChickenEggs;//Số lượng gà trứng
	
	private Double amountDuckMeat;//Số lượng vịt thịt
	private Double amountDuckEggs;//Số lượng vit trứng
	
	private Double amountOtherMeat;//Số lượng khác thịt
	private Double amountOtherEggs;//Số lượng khác trứng
	
	private Double totalAmountMeat;//tổng số con thịt
	private Double totalAmountEggs;//Tổng số con trứng
	
	
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
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
	public Double getAmountChickenMeat() {
		return amountChickenMeat;
	}
	public void setAmountChickenMeat(Double amountChickenMeat) {
		this.amountChickenMeat = amountChickenMeat;
	}
	public Double getAmountChickenEggs() {
		return amountChickenEggs;
	}
	public void setAmountChickenEggs(Double amountChickenEggs) {
		this.amountChickenEggs = amountChickenEggs;
	}
	public Double getAmountDuckMeat() {
		return amountDuckMeat;
	}
	public void setAmountDuckMeat(Double amountDuckMeat) {
		this.amountDuckMeat = amountDuckMeat;
	}
	public Double getAmountDuckEggs() {
		return amountDuckEggs;
	}
	public void setAmountDuckEggs(Double amountDuckEggs) {
		this.amountDuckEggs = amountDuckEggs;
	}
	public Double getAmountOtherMeat() {
		return amountOtherMeat;
	}
	public void setAmountOtherMeat(Double amountOtherMeat) {
		this.amountOtherMeat = amountOtherMeat;
	}
	public Double getAmountOtherEggs() {
		return amountOtherEggs;
	}
	public void setAmountOtherEggs(Double amountOtherEggs) {
		this.amountOtherEggs = amountOtherEggs;
	}
	public Double getTotalAmountMeat() {
		return totalAmountMeat;
	}
	public void setTotalAmountMeat(Double totalAmountMeat) {
		this.totalAmountMeat = totalAmountMeat;
	}
	public Double getTotalAmountEggs() {
		return totalAmountEggs;
	}
	public void setTotalAmountEggs(Double totalAmountEggs) {
		this.totalAmountEggs = totalAmountEggs;
	}	
}
