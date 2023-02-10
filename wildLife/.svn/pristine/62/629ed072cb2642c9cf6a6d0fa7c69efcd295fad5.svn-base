package com.globits.wl.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import com.globits.core.dto.FileDescriptionDto;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmAnimal;
import com.globits.wl.domain.FarmAnimalProductTargetExist;
import com.globits.wl.domain.FarmAnimalType;
import com.globits.wl.domain.FarmCertificate;
import com.globits.wl.domain.FarmFileAttachment;
import com.globits.wl.domain.FarmHusbandryMethod;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.FarmStore;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.IndividualAnimal;
import com.globits.wl.utils.WLConstant;

public class FarmDto {
	private Long id;
	private String name;
	private String code;
	private String created_by;
	private LocalDateTime create_date;

	private String businessRegistrationNumber;// số đăng ký kinh doanh
	private Date businessRegistrationDate;// ngày đăng ký kinh doanh
	private Integer status;// Trạng thái hoạt động (1:đang hoạt động, -1: ngưng hoạt động)
	private String longitude;// Kinh độ
	private String latitude;// Vĩ độ

	private String gMapX;// Google map X
	private String gMapY;// Google map Y

	private FmsAdministrativeUnitDto administrativeUnit;// Đơn vị hành chính (xã)
	private Long provinceId;
	private String provinceName;
	private String provinceCode;
	private Long districtId;
	private String districtName;
	private String districtCode;

	private Long wardId;
	private String wardCode;
	private String wardsName;

	private String adressDetail;// Địa chỉ chi tiết
	private String village;// thôn/ấp/xóm
	private String phoneNumber;// số điện thoại
	private String fax;// Fax
	private Integer apartmentNumber; // số nhà
	private Date startDate;// ngày bắt đầu nuôi

	private String mediaLink;// media Link
	private String description;// ô tả giới tiệu cơ sở chăn nuôi để quảng bá

	/*
	 * Người đại diện
	 */
	private String ownerName;// Tên chủ cơ sở
	private String ownerCitizenCode;// Số chứng minh thư chủ cơ sở
	private String ownerPhoneNumber;// Di động
	private String ownerEmail;// Email chủ cơ sở
	private String ownerAdress;// Địa chỉ chủ cơ sở
	private Date ownerDob;
	private Integer ownerAge;
	private String ownerGender;
	private String ownerPositionName; // Chức vụ
	private Date ownerCitizenDate;//ngay cap cmnd
	private String ownerCitizenLocation;// noi cap cmnd
	private FmsAdministrativeUnitDto ownerAdministrativeUnit;// Đơn vị hành chính của chủ cơ sở
	private String ownerVillage;
	private Long ownerProvinceId;
	private String ownerProvinceName;
	private String ownerProvinceCode;
	private Long ownerDistrictId;
	private String ownerDistrictName;
	private String ownerDistrictCode;

	private Long ownerWardsId;
	private String ownerWardsCode;
	private String ownerWardsName;

	
	
	/*
	 * Nhân viên thú ý (Veterinary Staff : VetStaff)
	 */
	private String vetStaffName;// Tên nhân viên thú y
	private String vetStaffCitizenCode;// Số chứng minh thư nhân viên thú y
	private String vetStaffPhoneNumber;// Điện thoại nhân viên thú y
	private String vetStaffEmail;// Email nhân viên thú y
	private String vetStaffAdress;// Địa chỉ nhân viên thú y

	/*
	 * Thông tin năng lực trang trại
	 */
	private String newRegistrationCode;// Mã đăng ký mới
	private String oldRegistrationCode;// Mã đăng ký cũ
	private Date issuingCodeDate;// Ngày cấp mã số
	private Date foundingDate;// Ngày thành lập
	private String reasonNotYetRegister;// Lý do chưa đăng ký trang trại
	private ProductTargetDto productForTrading;// Đối với hình thức thương mại là : 1-Nuôi sinh sản; 2-Nuôi sinh trưởng;
												// 3-Nuôi sinh sản, Nuôi sinh trưởng

	private Double totalAcreage;// Tổng Diện tích

	private Double lodgingAcreage;// Diện tích chuồng trại

	private Integer maxNumberOfAnimal;// Số lượng vật nuôi tối đa
	private List<ProductTargetDto> farmProductTargets = new ArrayList<ProductTargetDto>();// Loại hình sản xuất
	private HusbandryMethodDto husbandryMethod;// Phương thức chăn nuôi
	private WaterSourceDto waterResources;// Nguồn nước
	private Boolean isOutSourcing;// Nhận nuôi gia công
	private Integer numberOfLabor;// Số lượng nhân công
	private Integer distanceToResidentialArea;// Khoảng cách đến khu dân cư

	private List<FarmAnimalProductTargetExistDto> farmAnimalProductTargetExists = new ArrayList<FarmAnimalProductTargetExistDto>();// Danh
																																	// mục
																																	// vật
																																	// +
																																	// hướng
																																	// sản
																																	// phẩm

	private Set<AnimalDto> farmAnimals = new HashSet<AnimalDto>();// Danh mục vật nuôi của trang trại
	private List<AnimalDto> animals = new ArrayList<AnimalDto>();// Danh mục vật nuôi cha
	private Set<AnimalTypeDto> farmAnimalTypes = new HashSet<AnimalTypeDto>();// Danh mục loại vật nuôi của trang trại

	private Set<FarmFileAttachmentDto> attachments = new HashSet<FarmFileAttachmentDto>();
	private Set<CertificateDto> FarmCertificates = new HashSet<CertificateDto>();
	private Set<AnimalReportDataDto> animalReportDatas = new HashSet<AnimalReportDataDto>();
	private List<AnimalReportDataDto> animalReportDataSummarys = new ArrayList<AnimalReportDataDto>();
	/*
	 * Địa chỉ các cơ sở bán hàng
	 */
	private Set<FarmStoreDto> stores = new HashSet<FarmStoreDto>();

	private FarmSizeDto farmSize;// Quy mô trang trại
	private boolean autoGenericCode;// tự động gen mã

	private boolean isImport;// Trường hợp import từ excel
	private Double balanceNumber;// số tồn hiện tại đang nuôi
	private OwnershipDto ownership;
	private Integer salanganeHouseType;// Loại nhà chim yến: 0-Nhà xây mới, 1-Nhà cơ nới
	private Double salanganeNestExploitQuantity;// số lượng tổ yến khai thác

	// Lat theo tọa độ GCS
	private Double gcsLat;
	// Long theo tọa độ GCS
	private Double gcsLong;
	// Zone theo tọa độ GCS
	private String gcsZone;
	// Độ chính xác theo tọa độ GCS
	private Double gcsAccuracy;
	// Cao độ
	private Double gcsElevation;
	// ngày phỏng vấn
	private Date interviewDate;
	// ngày phỏng vấn
	private String oldCode;

	private String mapCode;

	// thời gian bắt đầu phỏng vấn
	private String interviewStartTime;
	// thời gian kết thúc phỏng vấn
	private String interviewFinTime;
	// Người phỏng vấn
	private String interviewer;
	// Người được phỏng vấn
	private String inName;
	// Tuổi người phỏng
	private Integer inAge;
	// Giới tính
	private String inGen;
	// Số điện thoại
	private String inTel;
	private String caName;
	private Integer caAge;
	private String caGen;
	private String type;
	private String yearRegistration;
	private Boolean isDuplicate;

	// trường tạm thời (convert để trả về client)
	private List<AnimalReportDataFormDto> informationHerdHaveFormIdAndformType = new ArrayList<AnimalReportDataFormDto>();// Thông
																															// tin
																															// đàn
																															// có
																															// form
																															// id
																															// và
																															// form
																															// type
	private List<AnimalReportDataFormDto> listBearDataForm = new ArrayList<AnimalReportDataFormDto>();
	
	private List<AnimalReportDataFormDto> listNormalAnimalDataForm = new ArrayList<AnimalReportDataFormDto>();
	
	private List<AnimalReportDataDto> informationHerd = new ArrayList<AnimalReportDataDto>();// Thông tin đàn không có
																								// form id và form type

	private Integer methodFeed;// Hình thức nuôi
	private Date stopDate;//ngày ngưng hoạt động
	private Integer statusFarm;// Trạng thái hoạt động (0:đang hoạt động, 1: ngưng hoạt động) 
	
	private Set<FarmHusbandryMethodDto> farmHusbandryMethods;
	
	private String ttbvmt;//tt bao ve moi truong
	private Date ttbvmtDate;// ngay cap tt bao ve moi truong
	private Date dateRegistration;//ngày cấp mã số đăng ký theo 06
	private Date dateOtherRegistration;//ngày cấp số giấy CN đăng ký
	private String xVN2000;
	private String yVN2000;
	private Integer ownerDobYear; // năm sinh

	public String getxVN2000() {return xVN2000;}

	public void setxVN2000(String xVN2000) {this.xVN2000 = xVN2000;}

	public String getyVN2000() {return yVN2000;}

	public void setyVN2000(String yVN2000) {this.yVN2000 = yVN2000;}

	public Long getOwnerProvinceId() {
		return ownerProvinceId;
	}

	public void setOwnerProvinceId(Long ownerProvinceId) {
		this.ownerProvinceId = ownerProvinceId;
	}

	public String getOwnerProvinceName() {
		return ownerProvinceName;
	}

	public void setOwnerProvinceName(String ownerProvinceName) {
		this.ownerProvinceName = ownerProvinceName;
	}

	public String getOwnerDistrictName() {
		return ownerDistrictName;
	}

	public void setOwnerDistrictName(String ownerDistrictName) {
		this.ownerDistrictName = ownerDistrictName;
	}

	public Long getOwnerWardsId() {
		return ownerWardsId;
	}

	public void setOwnerWardsId(Long ownerWardsId) {
		this.ownerWardsId = ownerWardsId;
	}

	public String getOwnerWardsCode() {
		return ownerWardsCode;
	}

	public void setOwnerWardsCode(String ownerWardsCode) {
		this.ownerWardsCode = ownerWardsCode;
	}

	

	public String getOwnerWardsName() {
		return ownerWardsName;
	}

	public void setOwnerWardsName(String ownerWardsName) {
		this.ownerWardsName = ownerWardsName;
	}

	public String getOwnerVillage() {
		return ownerVillage;
	}

	public void setOwnerVillage(String ownerVillage) {
		this.ownerVillage = ownerVillage;
	}

	public String getOwnerProvinceCode() {
		return ownerProvinceCode;
	}

	public void setOwnerProvinceCode(String ownerProvinceCode) {
		this.ownerProvinceCode = ownerProvinceCode;
	}

	

	public String getOwnerDistrictCode() {
		return ownerDistrictCode;
	}

	public void setOwnerDistrictCode(String ownerDistrictCode) {
		this.ownerDistrictCode = ownerDistrictCode;
	}

	public Long getOwnerDistrictId() {
		return ownerDistrictId;
	}

	public void setOwnerDistrictId(Long ownerDistrictId) {
		this.ownerDistrictId = ownerDistrictId;
	}

	public Date getDateRegistration() {
		return dateRegistration;
	}

	public void setDateRegistration(Date dateRegistration) {
		this.dateRegistration = dateRegistration;
	}

	public Set<FarmHusbandryMethodDto> getFarmHusbandryMethods() {
		return farmHusbandryMethods;
	}

	public void setFarmHusbandryMethods(Set<FarmHusbandryMethodDto> farmHusbandryMethods) {
		this.farmHusbandryMethods = farmHusbandryMethods;
	}

	public List<AnimalReportDataFormDto> getInformationHerdHaveFormIdAndformType() {
		return informationHerdHaveFormIdAndformType;
	}

	public List<AnimalReportDataFormDto> getListNormalAnimalDataForm() {
		return listNormalAnimalDataForm;
	}

	public void setListNormalAnimalDataForm(List<AnimalReportDataFormDto> listNormalAnimalDataForm) {
		this.listNormalAnimalDataForm = listNormalAnimalDataForm;
	}

	public void setInformationHerdHaveFormIdAndformType(
			List<AnimalReportDataFormDto> informationHerdHaveFormIdAndformType) {
		this.informationHerdHaveFormIdAndformType = informationHerdHaveFormIdAndformType;
	}

	public List<AnimalReportDataFormDto> getListBearDataForm() {
		return listBearDataForm;
	}

	public void setListBearDataForm(List<AnimalReportDataFormDto> listBearDataForm) {
		this.listBearDataForm = listBearDataForm;
	}

	public List<AnimalReportDataDto> getInformationHerd() {
		return informationHerd;
	}

	public void setInformationHerd(List<AnimalReportDataDto> informationHerd) {
		this.informationHerd = informationHerd;
	}

	public List<FarmAnimalProductTargetExistDto> getFarmAnimalProductTargetExists() {
		return farmAnimalProductTargetExists;
	}

	public void setFarmAnimalProductTargetExists(List<FarmAnimalProductTargetExistDto> farmAnimalProductTargetExists) {
		this.farmAnimalProductTargetExists = farmAnimalProductTargetExists;
	}

	public Double getSalanganeNestExploitQuantity() {
		return salanganeNestExploitQuantity;
	}

	public void setSalanganeNestExploitQuantity(Double salanganeNestExploitQuantity) {
		this.salanganeNestExploitQuantity = salanganeNestExploitQuantity;
	}

	public Integer getSalanganeHouseType() {
		return salanganeHouseType;
	}

	public void setSalanganeHouseType(Integer salanganeHouseType) {
		this.salanganeHouseType = salanganeHouseType;
	}

	public Set<AnimalReportDataDto> getAnimalReportDatas() {
		return animalReportDatas;
	}

	public void setAnimalReportDatas(Set<AnimalReportDataDto> animalReportDatas) {
		this.animalReportDatas = animalReportDatas;
	}

	public OwnershipDto getOwnership() {
		return ownership;
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

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}

	public ProductTargetDto getProductForTrading() {
		return productForTrading;
	}

	public void setProductForTrading(ProductTargetDto productForTrading) {
		this.productForTrading = productForTrading;
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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public Date getOwnerDob() {
		return ownerDob;
	}

	public void setOwnerDob(Date ownerDob) {
		this.ownerDob = ownerDob;
	}

	public Integer getOwnerAge() {
		return ownerAge;
	}

	public void setOwnerAge(Integer ownerAge) {
		this.ownerAge = ownerAge;
	}

	public String getOwnerGender() {
		return ownerGender;
	}

	public void setOwnerGender(String ownerGender) {
		this.ownerGender = ownerGender;
	}
	
	public String getOwnerPositionName() {
		return ownerPositionName;
	}

	public void setOwnerPositionName(String ownerPositionName) {
		this.ownerPositionName = ownerPositionName;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getgMapX() {
		return gMapX;
	}

	public void setgMapX(String gMapX) {
		this.gMapX = gMapX;
	}

	public String getgMapY() {
		return gMapY;
	}

	public void setgMapY(String gMapY) {
		this.gMapY = gMapY;
	}

	public String getAdressDetail() {
		return adressDetail;
	}

	public void setAdressDetail(String adressDetail) {
		this.adressDetail = adressDetail;
	}

	public String getMediaLink() {
		return mediaLink;
	}

	public void setMediaLink(String mediaLink) {
		this.mediaLink = mediaLink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getReasonNotYetRegister() {
		return reasonNotYetRegister;
	}

	public void setReasonNotYetRegister(String reasonNotYetRegister) {
		this.reasonNotYetRegister = reasonNotYetRegister;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerCitizenCode() {
		return ownerCitizenCode;
	}

	public void setOwnerCitizenCode(String ownerCitizenCode) {
		this.ownerCitizenCode = ownerCitizenCode;
	}

	public String getOwnerPhoneNumber() {
		return ownerPhoneNumber;
	}

	public void setOwnerPhoneNumber(String ownerPhoneNumber) {
		this.ownerPhoneNumber = ownerPhoneNumber;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getVetStaffName() {
		return vetStaffName;
	}

	public void setVetStaffName(String vetStaffName) {
		this.vetStaffName = vetStaffName;
	}

	public String getVetStaffCitizenCode() {
		return vetStaffCitizenCode;
	}

	public void setVetStaffCitizenCode(String vetStaffCitizenCode) {
		this.vetStaffCitizenCode = vetStaffCitizenCode;
	}

	public String getVetStaffPhoneNumber() {
		return vetStaffPhoneNumber;
	}

	public void setVetStaffPhoneNumber(String vetStaffPhoneNumber) {
		this.vetStaffPhoneNumber = vetStaffPhoneNumber;
	}

	public String getVetStaffEmail() {
		return vetStaffEmail;
	}

	public void setVetStaffEmail(String vetStaffEmail) {
		this.vetStaffEmail = vetStaffEmail;
	}

	public String getVetStaffAdress() {
		return vetStaffAdress;
	}

	public void setVetStaffAdress(String vetStaffAdress) {
		this.vetStaffAdress = vetStaffAdress;
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

	public FmsAdministrativeUnitDto getAdministrativeUnit() {
		return administrativeUnit;
	}

	public void setAdministrativeUnit(FmsAdministrativeUnitDto administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
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

	public List<ProductTargetDto> getFarmProductTargets() {
		return farmProductTargets;
	}

	public void setFarmProductTargets(List<ProductTargetDto> farmProductTargets) {
		this.farmProductTargets = farmProductTargets;
	}

	public HusbandryMethodDto getHusbandryMethod() {
		return husbandryMethod;
	}

	public void setHusbandryMethod(HusbandryMethodDto husbandryMethod) {
		this.husbandryMethod = husbandryMethod;
	}

	public WaterSourceDto getWaterResources() {
		return waterResources;
	}

	public void setWaterResources(WaterSourceDto waterResources) {
		this.waterResources = waterResources;
	}

	public Boolean getIsOutSourcing() {
		return isOutSourcing;
	}

	public void setIsOutSourcing(Boolean isOutSourcing) {
		this.isOutSourcing = isOutSourcing;
	}

	public Integer getNumberOfLabor() {
		return numberOfLabor;
	}

	public void setNumberOfLabor(Integer numberOfLabor) {
		this.numberOfLabor = numberOfLabor;
	}

	public Integer getDistanceToResidentialArea() {
		return distanceToResidentialArea;
	}

	public void setDistanceToResidentialArea(Integer distanceToResidentialArea) {
		this.distanceToResidentialArea = distanceToResidentialArea;
	}

	public Set<FarmFileAttachmentDto> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<FarmFileAttachmentDto> attachments) {
		this.attachments = attachments;
	}

	public Set<FarmStoreDto> getStores() {
		return stores;
	}

	public void setStores(Set<FarmStoreDto> stores) {
		this.stores = stores;
	}

	public Set<AnimalDto> getFarmAnimals() {
		return farmAnimals;
	}

	public void setFarmAnimals(Set<AnimalDto> farmAnimals) {
		this.farmAnimals = farmAnimals;
	}

	public List<AnimalDto> getAnimals() {
		return animals;
	}

	public void setAnimals(List<AnimalDto> animals) {
		this.animals = animals;
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

	public Set<CertificateDto> getFarmCertificates() {
		return FarmCertificates;
	}

	public void setFarmCertificates(Set<CertificateDto> farmCertificates) {
		FarmCertificates = farmCertificates;
	}

	public Set<AnimalTypeDto> getFarmAnimalTypes() {
		return farmAnimalTypes;
	}

	public void setFarmAnimalTypes(Set<AnimalTypeDto> farmAnimalTypes) {
		this.farmAnimalTypes = farmAnimalTypes;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public FarmSizeDto getFarmSize() {
		return farmSize;
	}

	public void setFarmSize(FarmSizeDto farmSize) {
		this.farmSize = farmSize;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOwnerAdress() {
		return ownerAdress;
	}

	public void setOwnerAdress(String ownerAdress) {
		this.ownerAdress = ownerAdress;
	}

	public boolean isAutoGenericCode() {
		return autoGenericCode;
	}

	public void setAutoGenericCode(boolean autoGenericCode) {
		this.autoGenericCode = autoGenericCode;
	}

	public boolean isImport() {
		return isImport;
	}

	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	public Double getBalanceNumber() {
		return balanceNumber;
	}

	public void setBalanceNumber(Double balanceNumber) {
		this.balanceNumber = balanceNumber;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public Long getWardId() {
		return wardId;
	}

	public void setWardId(Long wardId) {
		this.wardId = wardId;
	}

	public String getWardCode() {
		return wardCode;
	}

	public void setWardCode(String wardCode) {
		this.wardCode = wardCode;
	}

	public String getWardsName() {
		return wardsName;
	}

	public void setWardsName(String wardsName) {
		this.wardsName = wardsName;
	}

	public String getNewRegistrationCode() {
		return newRegistrationCode;
	}

	public void setNewRegistrationCode(String newRegistrationCode) {
		this.newRegistrationCode = newRegistrationCode;
	}

	public String getOldRegistrationCode() {
		return oldRegistrationCode;
	}

	public void setOldRegistrationCode(String oldRegistrationCode) {
		this.oldRegistrationCode = oldRegistrationCode;
	}

	public Date getIssuingCodeDate() {
		return issuingCodeDate;
	}

	public void setIssuingCodeDate(Date issuingCodeDate) {
		this.issuingCodeDate = issuingCodeDate;
	}

	public Date getFoundingDate() {
		return foundingDate;
	}

	public void setFoundingDate(Date foundingDate) {
		this.foundingDate = foundingDate;
	}

	public Double getGcsLat() {
		return gcsLat;
	}

	public void setGcsLat(Double gcsLat) {
		this.gcsLat = gcsLat;
	}

	public Double getGcsLong() {
		return gcsLong;
	}

	public void setGcsLong(Double gcsLong) {
		this.gcsLong = gcsLong;
	}

	public String getGcsZone() {
		return gcsZone;
	}

	public void setGcsZone(String gcsZone) {
		this.gcsZone = gcsZone;
	}

	public Double getGcsAccuracy() {
		return gcsAccuracy;
	}

	public void setGcsAccuracy(Double gcsAccuracy) {
		this.gcsAccuracy = gcsAccuracy;
	}

	public Double getGcsElevation() {
		return gcsElevation;
	}

	public void setGcsElevation(Double gcsElevation) {
		this.gcsElevation = gcsElevation;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	public String getMapCode() {
		return mapCode;
	}

	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}

	public String getInterviewStartTime() {
		return interviewStartTime;
	}

	public void setInterviewStartTime(String interviewStartTime) {
		this.interviewStartTime = interviewStartTime;
	}

	public String getInterviewFinTime() {
		return interviewFinTime;
	}

	public void setInterviewFinTime(String interviewFinTime) {
		this.interviewFinTime = interviewFinTime;
	}

	public String getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(String interviewer) {
		this.interviewer = interviewer;
	}

	public String getInName() {
		return inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	public Integer getInAge() {
		return inAge;
	}

	public void setInAge(Integer inAge) {
		this.inAge = inAge;
	}

	public String getInGen() {
		return inGen;
	}

	public void setInGen(String inGen) {
		this.inGen = inGen;
	}

	public String getInTel() {
		return inTel;
	}

	public void setInTel(String inTel) {
		this.inTel = inTel;
	}

	public String getCaName() {
		return caName;
	}

	public void setCaName(String caName) {
		this.caName = caName;
	}

	public Integer getCaAge() {
		return caAge;
	}

	public void setCaAge(Integer caAge) {
		this.caAge = caAge;
	}

	public String getCaGen() {
		return caGen;
	}

	public void setCaGen(String caGen) {
		this.caGen = caGen;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYearRegistration() {
		return yearRegistration;
	}

	public void setYearRegistration(String yearRegistration) {
		this.yearRegistration = yearRegistration;
	}

	public Boolean getIsDuplicate() {
		return isDuplicate;
	}

	public void setIsDuplicate(Boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}
	
	public List<AnimalReportDataDto> getAnimalReportDataSummarys() {
		return animalReportDataSummarys;
	}

	public void setAnimalReportDataSummarys(List<AnimalReportDataDto> animalReportDataSummarys) {
		this.animalReportDataSummarys = animalReportDataSummarys;
	}

	public Integer getMethodFeed() {
		return methodFeed;
	}

	public void setMethodFeed(Integer methodFeed) {
		this.methodFeed = methodFeed;
	}	

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}

	public Integer getStatusFarm() {
		return statusFarm;
	}

	public void setStatusFarm(Integer statusFarm) {
		this.statusFarm = statusFarm;
	}
	

	public Integer getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(Integer apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getOwnerCitizenDate() {
		return ownerCitizenDate;
	}

	public void setOwnerCitizenDate(Date ownerCitizenDate) {
		this.ownerCitizenDate = ownerCitizenDate;
	}

	public String getOwnerCitizenLocation() {
		return ownerCitizenLocation;
	}

	public void setOwnerCitizenLocation(String ownerCitizenLocation) {
		this.ownerCitizenLocation = ownerCitizenLocation;
	}

	public String getTtbvmt() {
		return ttbvmt;
	}

	public void setTtbvmt(String ttbvmt) {
		this.ttbvmt = ttbvmt;
	}

	public Date getTtbvmtDate() {
		return ttbvmtDate;
	}

	public void setTtbvmtDate(Date ttbvmtDate) {
		this.ttbvmtDate = ttbvmtDate;
	}

	public FmsAdministrativeUnitDto getOwnerAdministrativeUnit() {
		return ownerAdministrativeUnit;
	}

	public void setOwnerAdministrativeUnit(FmsAdministrativeUnitDto ownerAdministrativeUnit) {
		this.ownerAdministrativeUnit = ownerAdministrativeUnit;
	}

	public Integer getOwnerDobYear() {
		return ownerDobYear;
	}

	public void setOwnerDobYear(Integer ownerDobYear) {
		this.ownerDobYear = ownerDobYear;
	}

	public Date getDateOtherRegistration() {
		return dateOtherRegistration;
	}

	public void setDateOtherRegistration(Date dateOtherRegistration) {
		this.dateOtherRegistration = dateOtherRegistration;
	}

	public FarmDto() {
	}

	public FarmDto(Farm farm, boolean simple) {
		this.id = farm.getId();
		this.name = farm.getName();
		this.code = farm.getCode();
		this.oldCode = farm.getOldCode();
		this.longitude = farm.getLongitude();
		this.latitude = farm.getLatitude();
		this.gMapX = farm.getgMapX();
		this.gMapY = farm.getgMapY();
		this.adressDetail = farm.getAdressDetail();
		this.mediaLink = farm.getMediaLink();
		this.description = farm.getDescription();
		this.ownerName = farm.getOwnerName();
		this.ownerPhoneNumber = farm.getOwnerPhoneNumber();
		this.ownerDob = farm.getOwnerDob();
		if(farm.getOwnerDob() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(farm.getOwnerDob());
			this.ownerDobYear = calendar.get(Calendar.YEAR);
		}
		this.ownerPositionName = farm.getOwnerPositionName();
		this.ownerCitizenCode = farm.getOwnerCitizenCode();
		this.ownerEmail = farm.getOwnerEmail();
		this.vetStaffName = farm.getVetStaffName();
		this.vetStaffCitizenCode = farm.getVetStaffCitizenCode();
		this.vetStaffPhoneNumber = farm.getVetStaffPhoneNumber();
		this.vetStaffEmail = farm.getVetStaffEmail();
		this.vetStaffAdress = farm.getVetStaffAdress();
		this.totalAcreage = farm.getTotalAcreage();
		this.lodgingAcreage = farm.getLodgingAcreage();
		this.maxNumberOfAnimal = farm.getMaxNumberOfAnimal();
		this.isOutSourcing = farm.getIsOutSourcing();
		this.numberOfLabor = farm.getNumberOfLabor();
		this.balanceNumber = farm.getBalanceNumber();
		this.created_by = farm.getCreatedBy();
		this.create_date = farm.getCreateDate();
		this.salanganeHouseType = farm.getSalanganeHouseType();
		this.salanganeNestExploitQuantity = farm.getSalanganeNestExploitQuantity();
		this.reasonNotYetRegister = farm.getReasonNotYetRegister();
		this.newRegistrationCode = farm.getNewRegistrationCode();
		this.oldRegistrationCode = farm.getOldRegistrationCode();
		this.issuingCodeDate = farm.getIssuingCodeDate();
		this.foundingDate = farm.getFoundingDate();
		this.interviewStartTime = farm.getInterviewStartTime();
		this.interviewFinTime = farm.getInterviewFinTime();
		this.interviewer = farm.getInterviewer();
		this.inName = farm.getInName();
		this.inAge = farm.getInAge();
		this.inGen = farm.getInGen();
		this.inTel = farm.getInTel();
		this.caName = farm.getCaName();
		this.caAge = farm.getCaAge();
		this.caGen = farm.getCaGen();
		this.type = farm.getType();
		this.yearRegistration = farm.getYearRegistration();
		this.isDuplicate = farm.getIsDuplicate();
		this.methodFeed = farm.getMethodFeed();
		this.stopDate=farm.getStopDate();
		this.statusFarm=farm.getStatusFarm();
		this.status = farm.getStatus();
		this.village = farm.getVillage();
		this.mapCode = farm.getMapCode();
		//tran huu dat them thuoc tinh trong file excel moi
		this.ttbvmt= farm.getTtbvmt();//ttbvmt
		this.ttbvmtDate= farm.getTtbvmtDate();//ngay cap ttbvmt
		this.ownerCitizenDate= farm.getOwnerCitizenDate();//ngay cap cmnd
		this.ownerCitizenLocation=farm.getOwnerCitizenLocation();//noi cap cmnd
		this.apartmentNumber= farm.getApartmentNumber();// so nha
		this.startDate=farm.getStartDate();//ngay bat dau nuoi
		this.dateRegistration=farm.getDateRegistration();//Ngày cấp mã đăng ký theo 06
		this.dateOtherRegistration=farm.getDateOtherRegistration();//Ngày cấp mã số CN đăng ký
		this.phoneNumber=farm.getPhoneNumber();
		this.businessRegistrationNumber=farm.getBusinessRegistrationNumber();
		this.xVN2000 = farm.getxVN2000();
		this.yVN2000 = farm.getyVN2000();
		//tran huu dat them thuoc tinh trong file excel moi
		if (farm.getAdministrativeUnit() != null) {
			this.administrativeUnit = new FmsAdministrativeUnitDto(farm.getAdministrativeUnit(), true);
			// this.administrativeUnit = new
			// FmsAdministrativeUnitDto(farm.getAdministrativeUnit());
			this.wardsName = farm.getAdministrativeUnit().getName();
			this.wardCode = farm.getAdministrativeUnit().getCode();
			this.wardId = farm.getAdministrativeUnit().getId();

			if (farm.getAdministrativeUnit().getParent() != null) {
				this.districtName = farm.getAdministrativeUnit().getParent().getName();
				this.districtCode = farm.getAdministrativeUnit().getParent().getCode();
				this.districtId = farm.getAdministrativeUnit().getParent().getId();
			}

			if (farm.getAdministrativeUnit().getParent().getParent() != null) {
				this.provinceName = farm.getAdministrativeUnit().getParent().getParent().getName();
				this.provinceCode = farm.getAdministrativeUnit().getParent().getParent().getCode();
				this.provinceId = farm.getAdministrativeUnit().getParent().getParent().getId();
			}

		}
		this.ownerVillage=farm.getOwnerVillage();
		if (farm.getOwnerAdministrativeUnit() != null) {
			// this.administrativeUnit = new
			// FmsAdministrativeUnitDto(farm.getAdministrativeUnit());
			this.ownerWardsName = farm.getOwnerAdministrativeUnit().getName();
			this.ownerWardsCode = farm.getOwnerAdministrativeUnit().getCode();
			this.ownerWardsId = farm.getOwnerAdministrativeUnit().getId();

			if (farm.getOwnerAdministrativeUnit().getParent() != null) {
				this.ownerDistrictName = farm.getOwnerAdministrativeUnit().getParent().getName();
				this.ownerDistrictCode = farm.getOwnerAdministrativeUnit().getParent().getCode();
				this.ownerDistrictId = farm.getOwnerAdministrativeUnit().getParent().getId();
			}

			if (farm.getOwnerAdministrativeUnit().getParent().getParent() != null) {
				this.ownerProvinceName = farm.getOwnerAdministrativeUnit().getParent().getParent().getName();
				this.ownerProvinceCode = farm.getOwnerAdministrativeUnit().getParent().getParent().getCode();
				this.ownerProvinceId = farm.getOwnerAdministrativeUnit().getParent().getParent().getId();
			}

		}
		if (farm.getOwnerAdministrativeUnit() != null) {
			this.ownerAdministrativeUnit = new FmsAdministrativeUnitDto(farm.getOwnerAdministrativeUnit(), true);
			this.ownerWardsName = farm.getOwnerAdministrativeUnit().getName();
			this.ownerWardsCode = farm.getOwnerAdministrativeUnit().getCode();
			this.ownerWardsId = farm.getOwnerAdministrativeUnit().getId();
			if (farm.getOwnerAdministrativeUnit().getParent() != null) {
				this.ownerDistrictName = farm.getOwnerAdministrativeUnit().getParent().getName();
				this.ownerDistrictCode = farm.getOwnerAdministrativeUnit().getParent().getCode();
				this.ownerDistrictId = farm.getOwnerAdministrativeUnit().getParent().getId();
				if (farm.getOwnerAdministrativeUnit().getParent().getParent() != null) {
					this.ownerProvinceName = farm.getOwnerAdministrativeUnit().getParent().getParent().getName();
					this.ownerProvinceCode = farm.getOwnerAdministrativeUnit().getParent().getParent().getCode();
					this.ownerProvinceId = farm.getOwnerAdministrativeUnit().getParent().getParent().getId();
				}
			}
		}
		this.farmHusbandryMethods = new HashSet<FarmHusbandryMethodDto>();
		if(farm.getFarmHusbandryMethods() != null && farm.getFarmHusbandryMethods().size() > 0) {
			for(FarmHusbandryMethod farmHusbandryMethod :farm.getFarmHusbandryMethods()) {
				if(farmHusbandryMethod.getHusbandryMethod() != null) {
					FarmHusbandryMethodDto farmHusbandryMethodDto = new FarmHusbandryMethodDto();
					farmHusbandryMethodDto.setHusbandryMethod(new HusbandryMethodDto());
					farmHusbandryMethodDto.getHusbandryMethod().setId(farmHusbandryMethod.getHusbandryMethod().getId());
					farmHusbandryMethodDto.getHusbandryMethod().setName(farmHusbandryMethod.getHusbandryMethod().getName());
					farmHusbandryMethodDto.getHusbandryMethod().setCode(farmHusbandryMethod.getHusbandryMethod().getCode());
					farmHusbandryMethod.setId(farmHusbandryMethod.getId());
					this.farmHusbandryMethods.add(farmHusbandryMethodDto);
				}
			}
		}
		if (farm.getFarmProductTargets() != null) {
			this.farmProductTargets = new ArrayList<ProductTargetDto>();
			for (FarmProductTarget fht : farm.getFarmProductTargets()) {
				this.farmProductTargets.add(new ProductTargetDto(fht.getProductTarget()));
			}
		}
		this.mapCode=farm.getMapCode();
	}

	public FarmDto(Farm farm) {
		this.id = farm.getId();
		this.name = farm.getName();
		this.code = farm.getCode();
		this.oldCode = farm.getOldCode();
		this.longitude = farm.getLongitude();
		this.latitude = farm.getLatitude();
		this.gMapX = farm.getgMapX();
		this.gMapY = farm.getgMapY();
		this.adressDetail = farm.getAdressDetail();
		this.mediaLink = farm.getMediaLink();
		this.description = farm.getDescription();
		this.ownerName = farm.getOwnerName();
		this.ownerPhoneNumber = farm.getOwnerPhoneNumber();
		this.ownerCitizenCode = farm.getOwnerCitizenCode();
		this.ownerEmail = farm.getOwnerEmail();
		this.ownerDob = farm.getOwnerDob();
		if(farm.getOwnerDob() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(farm.getOwnerDob());
			this.ownerDobYear = calendar.get(Calendar.YEAR);
		}
		this.ownerPositionName = farm.getOwnerPositionName();
		this.vetStaffName = farm.getVetStaffName();
		this.vetStaffCitizenCode = farm.getVetStaffCitizenCode();
		this.vetStaffPhoneNumber = farm.getVetStaffPhoneNumber();
		this.vetStaffEmail = farm.getVetStaffEmail();
		this.vetStaffAdress = farm.getVetStaffAdress();
		this.totalAcreage = farm.getTotalAcreage();
		this.lodgingAcreage = farm.getLodgingAcreage();
		this.maxNumberOfAnimal = farm.getMaxNumberOfAnimal();
		this.isOutSourcing = farm.getIsOutSourcing();
		this.numberOfLabor = farm.getNumberOfLabor();
		this.balanceNumber = farm.getBalanceNumber();
		this.created_by = farm.getCreatedBy();
		this.create_date = farm.getCreateDate();
		this.salanganeHouseType = farm.getSalanganeHouseType();
		this.reasonNotYetRegister = farm.getReasonNotYetRegister();
		this.newRegistrationCode = farm.getNewRegistrationCode();
		this.oldRegistrationCode = farm.getOldRegistrationCode();
		this.issuingCodeDate = farm.getIssuingCodeDate();
		this.foundingDate = farm.getFoundingDate();
		this.salanganeNestExploitQuantity = farm.getSalanganeNestExploitQuantity();
		this.isDuplicate = farm.getIsDuplicate();
		this.methodFeed = farm.getMethodFeed();
		this.stopDate=farm.getStopDate();
		this.statusFarm=farm.getStatusFarm();
		this.mapCode=farm.getMapCode();
		this.xVN2000 = farm.getxVN2000();
		this.yVN2000 = farm.getyVN2000();
		//tran huu dat them thuoc tinh trong file excel moi
				this.ttbvmt= farm.getTtbvmt();//ttbvmt
				this.ttbvmtDate= farm.getTtbvmtDate();//ngay cap ttbvmt
				this.ownerCitizenDate= farm.getOwnerCitizenDate();//ngay cap cmnd
				this.ownerCitizenLocation=farm.getOwnerCitizenLocation();//noi cap cmnd
				this.apartmentNumber= farm.getApartmentNumber();// so nha
				this.startDate=farm.getStartDate();//ngay bat dau nuoi
				this.dateRegistration= farm.getDateRegistration();//Ngày cấp mã số đk theo 06
				this.phoneNumber=farm.getPhoneNumber();
				this.businessRegistrationNumber=farm.getBusinessRegistrationNumber();
				//tran huu dat them thuoc tinh trong file excel moi
				this.dateOtherRegistration=farm.getDateOtherRegistration();//Ngày cấp mã số giấy CN đăng ký
		if (farm.getFarmProductTargets() != null) {
			this.farmProductTargets = new ArrayList<ProductTargetDto>();
			for (FarmProductTarget fht : farm.getFarmProductTargets()) {
				this.farmProductTargets.add(new ProductTargetDto(fht.getProductTarget()));
			}
		}

		if (farm.getHusbandryMethod() != null) {
			this.husbandryMethod = new HusbandryMethodDto(farm.getHusbandryMethod());
		}

		if (farm.getWaterResources() != null) {
			this.waterResources = new WaterSourceDto(farm.getWaterResources());
		}

		this.distanceToResidentialArea = farm.getDistanceToResidentialArea();

		// this.attachments.clear();
		if (farm.getAttachments() != null && farm.getAttachments().size() > 0) {
			for (FarmFileAttachment ffa : farm.getAttachments()) {
				if (ffa != null) {
					FarmFileAttachmentDto ffaDto = new FarmFileAttachmentDto();
					if (ffa.getFile() != null) {
						ffaDto.setFile(new FileDescriptionDto(ffa.getFile()));
					}
					ffaDto.setId(ffa.getId());
					this.attachments.add(ffaDto);
				}
			}
		}
		this.stores.clear();
		if (farm.getStores() != null && farm.getStores().size() > 0) {
			for (FarmStore fr : farm.getStores()) {
				if (fr != null) {
					FarmStoreDto store = new FarmStoreDto();
					store.setId(fr.getId());
					store.setAdress(fr.getAdress());
					store.setCode(fr.getCode());
					store.setName(fr.getName());
					store.setPhoneNumber(fr.getPhoneNumber());
					if (fr.getAdministrativeUnit() != null) {
						store.setAdministrativeUnitDto(new FmsAdministrativeUnitDto(fr.getAdministrativeUnit()));
					}
					this.stores.add(store);
				}
			}
		}
		this.ownerVillage=farm.getOwnerVillage();
		if (farm.getOwnerAdministrativeUnit() != null) {
			// this.administrativeUnit = new
			// FmsAdministrativeUnitDto(farm.getAdministrativeUnit());
			this.ownerWardsName = farm.getOwnerAdministrativeUnit().getName();
			this.ownerWardsCode = farm.getOwnerAdministrativeUnit().getCode();
			this.ownerWardsId = farm.getOwnerAdministrativeUnit().getId();

			if (farm.getOwnerAdministrativeUnit().getParent() != null) {
				this.ownerDistrictName = farm.getOwnerAdministrativeUnit().getParent().getName();
				this.ownerDistrictCode = farm.getOwnerAdministrativeUnit().getParent().getCode();
				this.ownerDistrictId = farm.getOwnerAdministrativeUnit().getParent().getId();
			}

			if (farm.getOwnerAdministrativeUnit().getParent().getParent() != null) {
				this.ownerProvinceName = farm.getOwnerAdministrativeUnit().getParent().getParent().getName();
				this.ownerProvinceCode = farm.getOwnerAdministrativeUnit().getParent().getParent().getCode();
				this.ownerProvinceId = farm.getOwnerAdministrativeUnit().getParent().getParent().getId();
			}

		}

		if (farm.getAdministrativeUnit() != null) {
			this.administrativeUnit = new FmsAdministrativeUnitDto(farm.getAdministrativeUnit(), true);
			this.wardsName = farm.getAdministrativeUnit().getName();
			if (farm.getAdministrativeUnit().getParent() != null) {
				this.districtId = farm.getAdministrativeUnit().getParent().getId();
				this.districtName = farm.getAdministrativeUnit().getParent().getName();
				this.districtCode = farm.getAdministrativeUnit().getParent().getCode();
				if (farm.getAdministrativeUnit().getParent().getParent() != null) {
					this.provinceId = farm.getAdministrativeUnit().getParent().getParent().getId();
					this.provinceName = farm.getAdministrativeUnit().getParent().getParent().getName();
					this.provinceCode = farm.getAdministrativeUnit().getParent().getParent().getCode();
				}
			}
		}
		
		if (farm.getOwnerAdministrativeUnit() != null) {
			this.ownerAdministrativeUnit = new FmsAdministrativeUnitDto(farm.getOwnerAdministrativeUnit(), true);
			this.ownerWardsName = farm.getOwnerAdministrativeUnit().getName();
			this.ownerWardsCode = farm.getOwnerAdministrativeUnit().getCode();
			this.ownerWardsId = farm.getOwnerAdministrativeUnit().getId();
			if (farm.getOwnerAdministrativeUnit().getParent() != null) {
				this.ownerDistrictName = farm.getOwnerAdministrativeUnit().getParent().getName();
				this.ownerDistrictCode = farm.getOwnerAdministrativeUnit().getParent().getCode();
				this.ownerDistrictId = farm.getOwnerAdministrativeUnit().getParent().getId();
				if (farm.getOwnerAdministrativeUnit().getParent().getParent() != null) {
					this.ownerProvinceName = farm.getOwnerAdministrativeUnit().getParent().getParent().getName();
					this.ownerProvinceCode = farm.getOwnerAdministrativeUnit().getParent().getParent().getCode();
					this.ownerProvinceId = farm.getOwnerAdministrativeUnit().getParent().getParent().getId();
				}
			}
		}
		if (farm.getFarmAnimals() != null && farm.getFarmAnimals().size() > 0) {
			for (FarmAnimal item : farm.getFarmAnimals()) {
				this.farmAnimals.add(new AnimalDto(item.getAnimal()));

			}
		}
		this.businessRegistrationDate = farm.getBusinessRegistrationDate();
		this.businessRegistrationNumber = farm.getBusinessRegistrationNumber();
		if (farm.getStatus() != null) {
			this.status = farm.getStatus();
		}

		if (farm.getFarmAnimalTypes() != null && farm.getFarmAnimalTypes().size() > 0) {
			for (FarmAnimalType item : farm.getFarmAnimalTypes()) {
				this.farmAnimalTypes.add(new AnimalTypeDto(item.getAnimalType()));

			}
		}
		this.farmHusbandryMethods = new HashSet<FarmHusbandryMethodDto>();
		if(farm.getFarmHusbandryMethods() != null && farm.getFarmHusbandryMethods().size() > 0) {
			for(FarmHusbandryMethod farmHusbandryMethod :farm.getFarmHusbandryMethods()) {
				if(farmHusbandryMethod.getHusbandryMethod() != null) {
					FarmHusbandryMethodDto farmHusbandryMethodDto = new FarmHusbandryMethodDto();
					farmHusbandryMethodDto.setHusbandryMethod(new HusbandryMethodDto());
					farmHusbandryMethodDto.getHusbandryMethod().setId(farmHusbandryMethod.getHusbandryMethod().getId());
					farmHusbandryMethodDto.getHusbandryMethod().setCode(farmHusbandryMethod.getHusbandryMethod().getCode());
					farmHusbandryMethod.setId(farmHusbandryMethod.getId());
					this.farmHusbandryMethods.add(farmHusbandryMethodDto);
				}
			}
		}
		if (farm.getFarmCertificates() != null && farm.getFarmCertificates().size() > 0) {
			for (FarmCertificate item : farm.getFarmCertificates()) {
				this.FarmCertificates.add(new CertificateDto(item.getCertificate()));

			}
		}
		if (farm.getAnimalReportDatas() != null && farm.getAnimalReportDatas().size() > 0) {
			this.animalReportDatas = new HashSet<AnimalReportDataDto>();
			for (AnimalReportData animalReportData : farm.getAnimalReportDatas()) {
//				if (animalReportData.getFormId() == null) {
					this.animalReportDatas.add(new AnimalReportDataDto(animalReportData));
//				}
			}
//			this.informationHerdHaveFormIdAndformType = new ArrayList<AnimalReportDataFormDto>();
//			this.informationHerd = new ArrayList<AnimalReportDataDto>();
//
//			Hashtable<UUID, AnimalReportDataFormDto> hashMap = new Hashtable<UUID, AnimalReportDataFormDto>();
//			Hashtable<UUID, AnimalReportDataFormDto> hashMapBear = new Hashtable<UUID, AnimalReportDataFormDto>();
//			Hashtable<UUID, AnimalReportDataFormDto> hashMapNormal = new Hashtable<UUID, AnimalReportDataFormDto>();
//			for (AnimalReportData animalReportData : farm.getAnimalReportDatas()) {
//				this.animalReportDatas.add(new AnimalReportDataDto(animalReportData));
//
//				if (animalReportData.getFormId() != null && animalReportData.getFormType() != null && animalReportData
//						.getFormType().equals(WLConstant.AnimalReportDataFormType.firstType.getValue())) {
//					AnimalReportDataFormDto item = hashMap.get(animalReportData.getFormId());
//					if (item == null) {
//						item = new AnimalReportDataFormDto();
//						item.setAnimal(new AnimalDto(animalReportData.getAnimal(), false));
//						item.setSource(animalReportData.getSource());
//						item.setPurpose(animalReportData.getPurpose());
//						item.setYear(animalReportData.getYear());
//					}
//
//					if (animalReportData.getType() == WLConstant.AnimalReportDataType.animalParent.getValue()) {// Đàn bố mẹ
//						item.setMaleParent(animalReportData.getMale());
//						item.setFemaleParent(animalReportData.getFemale());
//						item.setUnGenderParent(animalReportData.getUnGender());
//						
//					} else if (animalReportData.getType() == WLConstant.AnimalReportDataType.childOverOneOld.getValue()) {// Con non trên 1 tuổi
//						item.setMaleChild(animalReportData.getMale());
//						item.setFemaleChild(animalReportData.getFemale());
//						item.setUnGenderChild(animalReportData.getUnGender());
//
//					} else if (animalReportData.getType() == WLConstant.AnimalReportDataType.childUnderOneOld.getValue()) {// Con non dưới 1 tuổi
//						item.setMaleChildUnder1YO(animalReportData.getMale());
//						item.setFemaleChildUnder1YO(animalReportData.getFemale());
//						item.setUnGenderChildUnder1YO(animalReportData.getUnGender());
//					}
//
//					hashMap.put(animalReportData.getFormId(), item);
//				}
//				else if(animalReportData.getFormId() != null 
//						&& animalReportData.getFormType() != null 
//						&& animalReportData.getFormType().equals(WLConstant.AnimalReportDataFormType.secondType.getValue())) {
//					
//					AnimalReportDataFormDto item = hashMapBear.get(animalReportData.getFormId());
//					if (item == null) {
//						item = new AnimalReportDataFormDto();
//						item.setAnimal(new AnimalDto(animalReportData.getAnimal(), false));
//						item.setSource(animalReportData.getSource());
//						item.setPurpose(animalReportData.getPurpose());
//						item.setYear(animalReportData.getYear());
//					}
//
//					if (animalReportData.getType() == WLConstant.AnimalReportDataType.hasChip.getValue()) {// Đàn bố mẹ
//						item.setMaleBearHasChip(animalReportData.getMale());
//						item.setFemaleBearHasChip(animalReportData.getFemale());
//						item.setTotalBearHasChip(animalReportData.getTotal());
//						String chipCodes="";
//						if(animalReportData.getIndividualAnimals()!=null && animalReportData.getIndividualAnimals().size()>0) {
//							for (IndividualAnimal individualAnimal : animalReportData.getIndividualAnimals()) {
//								chipCodes+=individualAnimal.getCode()+";";
//							}
//						}
//						item.setChipCodes(chipCodes);
//						
//					} else if (animalReportData.getType() == WLConstant.AnimalReportDataType.noChip.getValue()) {// Con non trên 1 tuổi
//						item.setMaleBearNoChip(animalReportData.getMale());
//						item.setFemaleBearNoChip(animalReportData.getFemale());
//						item.setTotalBearNoChip(animalReportData.getTotal());
//						item.setReasonBornAtFarm(animalReportData.getReasonBornAtFarm());
//						item.setReasonOther(animalReportData.getReasonOther());
//					}
//					hashMapBear.put(animalReportData.getFormId(), item);
//					
//				}
//				else if(animalReportData.getFormId() != null 
//						&& animalReportData.getFormType() != null 
//						&& animalReportData.getFormType().equals(WLConstant.AnimalReportDataFormType.thirdType.getValue())) {
//					
//					AnimalReportDataFormDto item = hashMapNormal.get(animalReportData.getFormId());
//					if (item == null) {
//						item = new AnimalReportDataFormDto();
//						item.setAnimal(new AnimalDto(animalReportData.getAnimal(), false));
//						item.setSource(animalReportData.getSource());
//						item.setPurpose(animalReportData.getPurpose());
//						item.setYear(animalReportData.getYear());
//						item.setTotalNormal(animalReportData.getTotal());;
//					}
//					hashMapNormal.put(animalReportData.getFormId(), item);
//					
//				}
//				else {
//					informationHerd.add(new AnimalReportDataDto(animalReportData));
//				}
//			}
			
//			this.informationHerdHaveFormIdAndformType = new ArrayList<>(hashMap.values());
//			this.listBearDataForm = new ArrayList<>(hashMapBear.values());
//			this.listNormalAnimalDataForm = new ArrayList<>(hashMapNormal.values());
		}

		if (farm.getFarmSize() != null) {
			this.farmSize = new FarmSizeDto(farm.getFarmSize());
		}
		this.village = farm.getVillage();
		this.ownerAdress = farm.getOwnerAdress();
		this.phoneNumber = farm.getPhoneNumber();
		this.fax = farm.getFax();
		if (farm.getOwnership() != null) {
			this.ownership = new OwnershipDto(farm.getOwnership());
		}
		this.interviewStartTime = farm.getInterviewStartTime();
		this.interviewFinTime = farm.getInterviewFinTime();
		this.interviewer = farm.getInterviewer();
		this.inName = farm.getInName();
		this.inAge = farm.getInAge();
		this.inGen = farm.getInGen();
		this.inTel = farm.getInTel();
		this.caName = farm.getCaName();
		this.caAge = farm.getCaAge();
		this.caGen = farm.getCaGen();
		this.type = farm.getType();
		this.yearRegistration = farm.getYearRegistration();
	}

	public FarmDto(Farm farm, int isForm16CAndForm16D) {
		this.code = farm.getCode();
		this.name = farm.getName();
	}

	public boolean containsAnimal(Long id) {
		for (AnimalDto o : this.animals) {
			if (o != null && o.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

}
