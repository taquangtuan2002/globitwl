package com.globits.wl.dto.report;

import java.util.List;

import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.ImportExportLiveStockProductDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.utils.WLConstant;

public class LiveStockProductReportDto extends ImportExportLiveStockProductDto {	
	private Long farmId;	
	private String farmName;
	
	private Long wardId;
	private String wardName;
	
	private Long districtId;
	private String districtName;
	
	private Long provinceId;
	private String provinceName;
	
	private Long regionId;
	private String regionName;
	
	private Long animalId;
	private String animalName;
	
	private Long originalId;
	private String originalName;
	
	private Long parentId;
	private String parentlName;
	
	private Long productTargetId;
	private String productTargetName;
	private String productTargetCode;
	
	private Long liveStockProductId;
	private String liveStockProductName;
	
	public Long getFarmId() {
		return farmId;
	}
	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}
	public String getFarmName() {
		return farmName;
	}
	public void setFarmName(String farmName) {
		this.farmName = farmName;
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
	public Long getAnimalId() {
		return animalId;
	}
	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}
	public String getAnimalName() {
		return animalName;
	}
	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}
	public Long getOriginalId() {
		return originalId;
	}
	public void setOriginalId(Long originalId) {
		this.originalId = originalId;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getParentlName() {
		return parentlName;
	}
	public void setParentlName(String parentlName) {
		this.parentlName = parentlName;
	}
	public Long getProductTargetId() {
		return productTargetId;
	}
	public void setProductTargetId(Long productTargetId) {
		this.productTargetId = productTargetId;
	}
	public String getProductTargetName() {
		return productTargetName;
	}
	public void setProductTargetName(String productTargetName) {
		this.productTargetName = productTargetName;
	}
	
	public String getProductTargetCode() {
		return productTargetCode;
	}
	public void setProductTargetCode(String productTargetCode) {
		this.productTargetCode = productTargetCode;
	}
	
	public Long getLiveStockProductId() {
		return liveStockProductId;
	}
	public void setLiveStockProductId(Long liveStockProductId) {
		this.liveStockProductId = liveStockProductId;
	}
	public String getLiveStockProductName() {
		return liveStockProductName;
	}
	public void setLiveStockProductName(String liveStockProductName) {
		this.liveStockProductName = liveStockProductName;
	}
	public LiveStockProductReportDto(Object[] results,List<String> columns) {
		super( results, columns);
		
		if(this.getFarm()!=null) {
			this.farmId = this.getFarm().getId();
			this.farmName = this.getFarmName();
			this.setFarm(null);
		}
		
		if(this.getDistrict()!=null) {
			this.districtId = this.getDistrict().getId();
			this.districtName = this.getDistrict().getName();
			this.setDistrict(null);
		}
		
		if(this.getProvince()!=null) {
			this.provinceId = this.getProvince().getId();
			this.provinceName = this.getProvince().getName();
			this.setProvince(null);
		}
		if(this.getRegion()!=null) {
			this.regionId = this.getRegion().getId();
			this.regionName = this.getRegion().getName();
			this.setRegion(null);
		}
		if(this.getWard()!=null) {
			this.wardId = this.getWard().getId();
			this.wardName = this.getWard().getName();
			this.setWard(null);
		}
		if(this.getLiveStockProductDto()!=null) {
			this.liveStockProductId=this.getLiveStockProductDto().getId();
			this.liveStockProductName=this.getLiveStockProductDto().getName();
			this.setLiveStockProductDto(null);
		}
	}
}
