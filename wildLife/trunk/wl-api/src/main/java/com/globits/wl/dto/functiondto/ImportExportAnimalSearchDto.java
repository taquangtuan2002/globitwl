package com.globits.wl.dto.functiondto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import com.globits.core.dto.AdministrativeUnitDto;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmAnimal;
import com.globits.wl.domain.FarmAnimalType;
import com.globits.wl.domain.FarmFileAttachment;
import com.globits.wl.domain.FarmHusbandryType;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.FarmStore;

public class ImportExportAnimalSearchDto {
	private Long id;
	private String nameOrCode;
	private int type;// xuất đàn=-1, nhập đàn=1
	private Long farmId; //nông trại
	private Long cityId; //tỉnh
	private Long districtId; //thành phố
	private Long wardsId; //phường xã
	private List<Long> districtIds; // list huyện/thành phố
	private Date fromDate; //từ ngày
	private Date toDate; //đến ngày
	private Integer remainQuantityUp;
	private Integer remainQuantityDown;
	private String create_by;
	private LocalDateTime create_date;
	private String productTargetCode;
	private Long auId; // id địa chỉ
	
	private Boolean isImportExportAnimalSeed;
	
	private Long animalId;
	
	public String getProductTargetCode() {
		return productTargetCode;
	}

	public void setProductTargetCode(String productTargetCode) {
		this.productTargetCode = productTargetCode;
	}

	public Boolean getIsImportExportAnimalSeed() {
		return isImportExportAnimalSeed;
	}

	public void setIsImportExportAnimalSeed(Boolean isImportExportAnimalSeed) {
		this.isImportExportAnimalSeed = isImportExportAnimalSeed;
	}

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameOrCode() {
		return nameOrCode;
	}

	public void setNameOrCode(String nameOrCode) {
		this.nameOrCode = nameOrCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getFarmId() {
		return farmId;
	}

	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getWardsId() {
		return wardsId;
	}

	public void setWardsId(Long wardsId) {
		this.wardsId = wardsId;
	}

	public List<Long> getDistrictIds() {
		return districtIds;
	}

	public void setDistrictIds(List<Long> districtIds) {
		this.districtIds = districtIds;
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

	public Integer getRemainQuantityUp() {
		return remainQuantityUp;
	}

	public void setRemainQuantityUp(Integer remainQuantityUp) {
		this.remainQuantityUp = remainQuantityUp;
	}

	public Integer getRemainQuantityDown() {
		return remainQuantityDown;
	}

	public void setRemainQuantityDown(Integer remainQuantityDown) {
		this.remainQuantityDown = remainQuantityDown;
	}

	public Long getAnimalId() {
		return animalId;
	}

	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}

	public Long getAuId() {
		return auId;
	}

	public void setAuId(Long auId) {
		this.auId = auId;
	}	
	
}
