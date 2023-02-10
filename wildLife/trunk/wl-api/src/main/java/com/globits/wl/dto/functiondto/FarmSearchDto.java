package com.globits.wl.dto.functiondto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.globits.wl.dto.OwnershipDto;

public class FarmSearchDto {
	private Long id;
	private String name;
	private String code;
	private String nameOrCode;
	private int year;
	private Long region;
	private Long province;
	private Long district;
	private Long ward;
	private Long animalId;//vật nuôi
	private Long animalParentId;//Loại vật nuôi (cha)
	private Long husbandryMethodId;//phương thức chăn nuôi
	private Long waterResourceId;//nguồn nước
	private Long animalTypeId;//loại vật nuôi
	private Long fileId;//chứng chỉ
	private Long productTargetId;//loại hình sx
	private Double balanceNumber;//số tồn hiện tại đang nuôi
	private Double fromBalanceNumber;// 
	private Double toBalanceNumber;
	private String vetStaffName;// Tên nhân viên thú y
	private String ownerName;// Tên chủ cơ sở
	private Double totalAcreage;// Tổng Diện tích
	private Double lodgingAcreage;// Diện tích chuồng trại
	private Integer maxNumberOfAnimal;// Số lượng vật nuôi tối đa
	private String adressDetail;// Địa chỉ chi tiết
	private String phoneNumber;// số điện thoại
	private String businessRegistrationNumber;//số đăng ký kinh doanh
	private Date businessRegistrationDate;//ngày đăng ký kinh doanh
	private Integer status;// Trạng thái hoạt động (1:đang hoạt động, -1: ngưng hoạt động)
	private Integer statusFarm;// Trạng thái hoạt động (0:đang hoạt động, 1: ngưng hoạt động)
	private Integer distanceToResidentialArea;// Khoảng cách đến khu dân cư
	private String longitude;// Kinh độ
	private String latitude;// Vĩ độ
	private String fax;// Fax
	private String ownerCitizenCode;// Số chứng minh thư chủ cơ sở
	private String vetStaffCitizenCode;// Số chứng minh thư nhân viên thú y
	private Boolean isOutSourcing;// Nhận nuôi gia công
	private Double quantityProductExist;//lượng tồn theo hướng sản phẩm
	private Long productTargetExistId;//hướng sản phẩm theo tồn hướng sản phẩm
	private Double quantityAnimalProductExist;//lượng tồn theo hướng sản phẩm +vật nuôi
	private Long animalProductTargetId;//hướng sản phẩm theo tồn hướng sản phẩm +vật nuôi
	private Long animalExistId;//vật nuôi theo tồn hướng sản phẩm +vật nuôi
	private Long certificateId;//chứng chỉ
	private String createdBy;
	private String modifiedBy;
	private Date createDateFrom;
	private Date createDateTo;
	private Date modifiedDateFrom;
	private Date modifiedDateTo;
	private OwnershipDto ownership;//loại hình thức sở hữu
	private Boolean viewAnimalProductTargetExist;//view thêm tồn vật nuôi + hướng sản phẩm
	private Boolean showListFarmSelect;
	private String yearRegistration;
	private List<Long> listAnimalIds;
	private String animalClass;
	private String ordo;
	private String family;
	private Boolean checkQuantityLessThanZero;
	
	private Boolean checkSort;

	private Boolean isConvert = true;	//check có kết xuất dữ liệu hay không (mặc định true)
	
	public Long getAnimalParentId() {
		return animalParentId;
	}

	public void setAnimalParentId(Long animalParentId) {
		this.animalParentId = animalParentId;
	}

	public OwnershipDto getOwnership() {
		return ownership;
	}

	public Integer getStatusFarm() {
		return statusFarm;
	}

	public void setStatusFarm(Integer statusFarm) {
		this.statusFarm = statusFarm;
	}

	public void setOwnership(OwnershipDto ownership) {
		this.ownership = ownership;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNameOrCode() {
		return nameOrCode;
	}

	public void setNameOrCode(String nameOrCode) {
		this.nameOrCode = nameOrCode;
	}	
	public Long getRegion() {
		return region;
	}

	public void setRegion(Long region) {
		this.region = region;
	}

	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}

	public Long getWard() {
		return ward;
	}

	public void setWard(Long ward) {
		this.ward = ward;
	}
	
	public String getAdressDetail() {
		return adressDetail;
	}

	public void setAdressDetail(String adressDetail) {
		this.adressDetail = adressDetail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBusinessRegistrationNumber() {
		return businessRegistrationNumber;
	}

	public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
		this.businessRegistrationNumber = businessRegistrationNumber;
	}

	public Date getBusinessRegistrationDate() {
		return businessRegistrationDate;
	}

	public void setBusinessRegistrationDate(Date businessRegistrationDate) {
		this.businessRegistrationDate = businessRegistrationDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAnimalId() {
		return animalId;
	}

	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}

	public Long getHusbandryMethodId() {
		return husbandryMethodId;
	}

	public void setHusbandryMethodId(Long husbandryMethodId) {
		this.husbandryMethodId = husbandryMethodId;
	}

	public Long getWaterResourceId() {
		return waterResourceId;
	}

	public void setWaterResourceId(Long waterResourceId) {
		this.waterResourceId = waterResourceId;
	}

	public Long getAnimalTypeId() {
		return animalTypeId;
	}

	public void setAnimalTypeId(Long animalTypeId) {
		this.animalTypeId = animalTypeId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getProductTargetId() {
		return productTargetId;
	}

	public void setProductTargetId(Long productTargetId) {
		this.productTargetId = productTargetId;
	}

	public Double getBalanceNumber() {
		return balanceNumber;
	}

	public void setBalanceNumber(Double balanceNumber) {
		this.balanceNumber = balanceNumber;
	}
	

	public Double getFromBalanceNumber() {
		return fromBalanceNumber;
	}

	public void setFromBalanceNumber(Double fromBalanceNumber) {
		this.fromBalanceNumber = fromBalanceNumber;
	}

	public Double getToBalanceNumber() {
		return toBalanceNumber;
	}

	public void setToBalanceNumber(Double toBalanceNumber) {
		this.toBalanceNumber = toBalanceNumber;
	}

	public String getVetStaffName() {
		return vetStaffName;
	}

	public void setVetStaffName(String vetStaffName) {
		this.vetStaffName = vetStaffName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Double getTotalAcreage() {
		return totalAcreage;
	}

	public void setTotalAcreage(Double totalAcreage) {
		this.totalAcreage = totalAcreage;
	}

	public Double getLodgingAcreage() {
		return lodgingAcreage;
	}

	public void setLodgingAcreage(Double lodgingAcreage) {
		this.lodgingAcreage = lodgingAcreage;
	}

	public Integer getMaxNumberOfAnimal() {
		return maxNumberOfAnimal;
	}

	public void setMaxNumberOfAnimal(Integer maxNumberOfAnimal) {
		this.maxNumberOfAnimal = maxNumberOfAnimal;
	}

	public Integer getDistanceToResidentialArea() {
		return distanceToResidentialArea;
	}

	public void setDistanceToResidentialArea(Integer distanceToResidentialArea) {
		this.distanceToResidentialArea = distanceToResidentialArea;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOwnerCitizenCode() {
		return ownerCitizenCode;
	}

	public void setOwnerCitizenCode(String ownerCitizenCode) {
		this.ownerCitizenCode = ownerCitizenCode;
	}

	public String getVetStaffCitizenCode() {
		return vetStaffCitizenCode;
	}

	public void setVetStaffCitizenCode(String vetStaffCitizenCode) {
		this.vetStaffCitizenCode = vetStaffCitizenCode;
	}

	public Boolean getIsOutSourcing() {
		return isOutSourcing;
	}

	public void setIsOutSourcing(Boolean isOutSourcing) {
		this.isOutSourcing = isOutSourcing;
	}

	public Double getQuantityProductExist() {
		return quantityProductExist;
	}

	public void setQuantityProductExist(Double quantityProductExist) {
		this.quantityProductExist = quantityProductExist;
	}

	public Double getQuantityAnimalProductExist() {
		return quantityAnimalProductExist;
	}

	public void setQuantityAnimalProductExist(Double quantityAnimalProductExist) {
		this.quantityAnimalProductExist = quantityAnimalProductExist;
	}

	public Long getProductTargetExistId() {
		return productTargetExistId;
	}

	public void setProductTargetExistId(Long productTargetExistId) {
		this.productTargetExistId = productTargetExistId;
	}

	public Long getAnimalProductTargetId() {
		return animalProductTargetId;
	}

	public void setAnimalProductTargetId(Long animalProductTargetId) {
		this.animalProductTargetId = animalProductTargetId;
	}

	public Long getAnimalExistId() {
		return animalExistId;
	}

	public void setAnimalExistId(Long animalExistId) {
		this.animalExistId = animalExistId;
	}

	public Long getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDateFrom() {
		return modifiedDateFrom;
	}

	public void setModifiedDateFrom(Date modifiedDateFrom) {
		this.modifiedDateFrom = modifiedDateFrom;
	}

	public Date getModifiedDateTo() {
		return modifiedDateTo;
	}

	public void setModifiedDateTo(Date modifiedDateTo) {
		this.modifiedDateTo = modifiedDateTo;
	}

	public Boolean getViewAnimalProductTargetExist() {
		return viewAnimalProductTargetExist;
	}

	public void setViewAnimalProductTargetExist(Boolean viewAnimalProductTargetExist) {
		this.viewAnimalProductTargetExist = viewAnimalProductTargetExist;
	}

	public Boolean getShowListFarmSelect() {
		return showListFarmSelect;
	}

	public void setShowListFarmSelect(Boolean showListFarmSelect) {
		this.showListFarmSelect = showListFarmSelect;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getYearRegistration() {
		return yearRegistration;
	}

	public void setYearRegistration(String yearRegistration) {
		this.yearRegistration = yearRegistration;
	}

	public List<Long> getListAnimalIds() {
		return listAnimalIds;
	}

	public void setListAnimalIds(List<Long> listAnimalIds) {
		this.listAnimalIds = listAnimalIds;
	}

	public String getAnimalClass() {
		return animalClass;
	}

	public void setAnimalClass(String animalClass) {
		this.animalClass = animalClass;
	}

	public String getOrdo() {
		return ordo;
	}

	public void setOrdo(String ordo) {
		this.ordo = ordo;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public Boolean getCheckQuantityLessThanZero() {
		return checkQuantityLessThanZero;
	}

	public void setCheckQuantityLessThanZero(Boolean checkQuantityLessThanZero) {
		this.checkQuantityLessThanZero = checkQuantityLessThanZero;
	}

	public Boolean getIsConvert() {
		return isConvert;
	}

	public void setIsConvert(Boolean isConvert) {
		this.isConvert = isConvert;
	}

	public Boolean getCheckSort() {
		return checkSort;
	}

	public void setCheckSort(Boolean checkSort) {
		this.checkSort = checkSort;
	}
	
	
}
