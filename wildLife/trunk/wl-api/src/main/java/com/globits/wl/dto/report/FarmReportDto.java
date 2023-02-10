package com.globits.wl.dto.report;

import java.util.List;

import com.globits.wl.utils.WLConstant;

public class FarmReportDto {
	private double totalAcreage;
	private double livestockCapacityByTotal;
	private double lodgingAcreage;
	private double livestockCapacityByLodging;
	private Long maxNumberOfAnimal;
	private double balanceNumber;
	private Long numberOfFarm;
	private Integer status;
	private Long wardId;
	private String wardName;
	private Long districtId;
	private String districtName;
	private Long provinceId;
	private String provinceName;
	private Long regionId;
	private String regionName;
	
	public double getTotalAcreage() {
		return totalAcreage;
	}
	public void setTotalAcreage(double totalAcreage) {
		this.totalAcreage = totalAcreage;
	}
	public double getLodgingAcreage() {
		return lodgingAcreage;
	}
	public void setLodgingAcreage(double lodgingAcreage) {
		this.lodgingAcreage = lodgingAcreage;
	}
	public Long getMaxNumberOfAnimal() {
		return maxNumberOfAnimal;
	}
	public void setMaxNumberOfAnimal(Long maxNumberOfAnimal) {
		this.maxNumberOfAnimal = maxNumberOfAnimal;
	}
	public double getBalanceNumber() {
		return balanceNumber;
	}
	public void setBalanceNumber(double balanceNumber) {
		this.balanceNumber = balanceNumber;
	}
	public Long getNumberOfFarm() {
		return numberOfFarm;
	}
	public void setNumberOfFarm(Long numberOfFarm) {
		this.numberOfFarm = numberOfFarm;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getWardId() {
		return wardId;
	}
	public void setWardId(Long wardId) {
		this.wardId = wardId;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
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
	public double getLivestockCapacityByTotal() {
		return livestockCapacityByTotal;
	}
	public void setLivestockCapacityByTotal(double livestockCapacityByTotal) {
		this.livestockCapacityByTotal = livestockCapacityByTotal;
	}
	public double getLivestockCapacityByLodging() {
		return livestockCapacityByLodging;
	}
	public void setLivestockCapacityByLodging(double livestockCapacityByLodging) {
		this.livestockCapacityByLodging = livestockCapacityByLodging;
	}
	public FarmReportDto() {
		
	}
	public FarmReportDto(Object[] results,List<String> columns) {
		if(results!=null && results.length>0 && columns!=null && columns.size()>0) {
			for (int i = 0; i < columns.size(); i++) {
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.totalAcreage.getValue())) {
					this.totalAcreage = (double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.lodgingAcreage.getValue())) {
					this.lodgingAcreage = (double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.maxNumberOfAnimal.getValue())) {
					this.maxNumberOfAnimal = (Long)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.balanceNumber.getValue())) {
					this.balanceNumber = (double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.countFarm.getValue())) {
					this.numberOfFarm = (Long)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.status.getValue())) {
					this.status = (Integer)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.region.getValue()+"id")) {
					this.regionId = (Long)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.region.getValue()+"name")) {
					this.regionName = (String)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.province.getValue()+"id")) {
					this.provinceId = (Long)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.province.getValue()+"name")) {
					this.provinceName = (String)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.district.getValue()+"id")) {
					this.districtId = (Long)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.district.getValue()+"name")) {
					this.districtName = (String)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.ward.getValue()+"id")) {
					this.wardId = (Long)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.ward.getValue()+"name")) {
					this.wardName = (String)results[i];
				}
			}
		}		
	}
}
