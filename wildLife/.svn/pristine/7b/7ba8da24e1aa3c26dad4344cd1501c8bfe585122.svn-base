package com.globits.wl.dto.report;

import java.util.Date;
import java.util.List;

import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.NumberUtils;

public class InventoryReportDto extends ImportExportAnimalDto {	
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
	private String scienceName;
	
	private Long originalId;
	private String originalName;
	
	private Long parentId;
	private String parentlName;
	
	private Long productTargetId;
	private String productTargetName;
	private String productTargetCode;
	
	private Long seedLevelId;
	private String seedLevelName;
	private Integer seedLevelLevel;
	private Integer eggType;
	private String eggTypeName;
	
	private Long ownershipId;//hình thức sở hữu id
	private String ownershipName;//tên hình thức sở hữu
	private double percent;//phần trăm :%
	
	private String reportCode;//Sử dụng khi làm báo cáo, nếu cần nhóm 1 số loại vật nuôi lại làm 1 nhóm
	private String reportName;//Sử dụng khi làm báo cáo, nếu cần nhóm 1 số loại vật nuôi lại làm 1 nhóm
	private Long importExportAnimalIdEgg;
	private String importExportAnimalCodeEgg;

	private Integer liveStockMethod;
	private String liveStockMethodName;

	private Integer weekIndex;		//tuần hiện tại
	private String weekSortName;		//tên hiển thị ngắn
	private String weekFullName;		//tên hiển thị đầy đủ	
	
	public Integer getWeekIndex() {
		return weekIndex;
	}
	public void setWeekIndex(Integer weekIndex) {
		this.weekIndex = weekIndex;
	}
	public String getWeekSortName() {
		return weekSortName;
	}
	public void setWeekSortName(String weekSortName) {
		this.weekSortName = weekSortName;
	}
	public String getWeekFullName() {
		return weekFullName;
	}
	public void setWeekFullName(String weekFullName) {
		this.weekFullName = weekFullName;
	}
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
	public String getScienceName() {
		return scienceName;
	}
	public void setScienceName(String scienceName) {
		this.scienceName = scienceName;
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
	
	public Long getSeedLevelId() {
		return seedLevelId;
	}
	public void setSeedLevelId(Long seedLevelId) {
		this.seedLevelId = seedLevelId;
	}
	public String getSeedLevelName() {
		return seedLevelName;
	}
	public void setSeedLevelName(String seedLevelName) {
		this.seedLevelName = seedLevelName;
	}
	
	public Integer getSeedLevelLevel() {
		return seedLevelLevel;
	}
	public void setSeedLevelLevel(Integer seedLevelLevel) {
		this.seedLevelLevel = seedLevelLevel;
	}
	public String getProductTargetCode() {
		return productTargetCode;
	}
	public void setProductTargetCode(String productTargetCode) {
		this.productTargetCode = productTargetCode;
	}
	public Integer getEggType() {
		return eggType;
	}
	public void setEggType(Integer eggType) {
		this.eggType = eggType;
	}
	public String getEggTypeName() {
		return eggTypeName;
	}
	public void setEggTypeName(String eggTypeName) {
		this.eggTypeName = eggTypeName;
	}	
	public Long getOwnershipId() {
		return ownershipId;
	}
	public void setOwnershipId(Long ownershipId) {
		this.ownershipId = ownershipId;
	}
	public String getOwnershipName() {
		return ownershipName;
	}
	public void setOwnershipName(String ownershipName) {
		this.ownershipName = ownershipName;
	}	
	
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public double getPercent() {
//		return percent;
		return NumberUtils.roundM(percent,2);
	}
	public void setPercent(double percent) {
		this.percent = percent;
	}
	
	public Long getImportExportAnimalIdEgg() {
		return importExportAnimalIdEgg;
	}
	public void setImportExportAnimalIdEgg(Long importExportAnimalIdEgg) {
		this.importExportAnimalIdEgg = importExportAnimalIdEgg;
	}
	public String getImportExportAnimalCodeEgg() {
		return importExportAnimalCodeEgg;
	}
	public void setImportExportAnimalCodeEgg(String importExportAnimalCodeEgg) {
		this.importExportAnimalCodeEgg = importExportAnimalCodeEgg;
	}	
		
	public Integer getLiveStockMethod() {
		return liveStockMethod;
	}
	public void setLiveStockMethod(Integer liveStockMethod) {
		this.liveStockMethod = liveStockMethod;
	}
	public String getLiveStockMethodName() {
		return liveStockMethodName;
	}
	public void setLiveStockMethodName(String liveStockMethodName) {
		this.liveStockMethodName = liveStockMethodName;
	}
	public InventoryReportDto() {
		
	}
	public InventoryReportDto(Object[] results,List<String> columns) {
		super( results, columns);
		if(results!=null && results.length>0 && columns!=null && columns.size()>0) {
			for (int i = 0; i < columns.size(); i++) {
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.eggType.getValue())) {
					this.eggType = (Integer)results[i];
					if(this.eggType==WLConstant.EggType.commodity.getValue()) {
						this.eggTypeName="Thương phẩm";
					}
					else {
						this.eggTypeName="Trứng giống";
					}
				}
			}
		}
		if(this.getFarm()!=null) {
			this.farmId = this.getFarm().getId();
			this.farmName = this.getFarmName();
			this.setFarm(null);
		}
		if(this.getAnimal()!=null) {
			this.animalId = this.getAnimal().getId();
			this.animalName = this.getAnimal().getName();
			this.scienceName = this.getAnimal().getScienceName();
			
			this.reportCode=this.getAnimal().getReportCode();
			this.reportName=this.getAnimal().getReportName();
			this.setLiveStockMethod(this.getAnimal().getLiveStockMethod());
			if(this.liveStockMethod==WLConstant.AnimalLiveStockMethod.organic.getValue()) {
				this.liveStockMethodName="Phi công nghiệp";
			}
			else {
				this.liveStockMethodName="Nuôi công nghiệp";
			}
			this.setAnimal(null);
		}
		if(this.getDistrict()!=null) {
			this.districtId = this.getDistrict().getId();
			this.districtName = this.getDistrict().getName();
			this.setDistrict(null);
		}
		if(this.getOriginal()!=null) {
			this.originalId = this.getOriginal().getId();
			this.originalName = this.getOriginal().getName();
			this.setOriginal(null);
		}
		if(this.getParent()!=null) {
			this.parentId = this.getParent().getId();
			this.parentlName = this.getParent().getName();
			this.setParent(null);
		}
		if(this.getProductTarget()!=null) {
			this.productTargetId = this.getProductTarget().getId();
			this.productTargetName = this.getProductTarget().getName();
			this.productTargetCode=this.getProductTarget().getCode();
			this.setProductTarget(null);
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
		if(this.getSeedLevel()!=null) {
			this.seedLevelId=this.getSeedLevel().getId();
			this.seedLevelName=this.getSeedLevel().getName();
			this.seedLevelLevel=this.getSeedLevel().getLevel();
			this.setSeedLevel(null);
		}
		if(this.getOwnership()!=null) {
			this.ownershipId=this.getOwnership().getId();
			this.ownershipName=this.getOwnership().getName();
			this.setOwnership(null);
		}
		if(this.getImportExportAnimal()!=null) {
			this.importExportAnimalIdEgg=this.getImportExportAnimal().getId();
			this.importExportAnimalCodeEgg=this.getImportExportAnimal().getBatchCode();
		}
		
	}
}
