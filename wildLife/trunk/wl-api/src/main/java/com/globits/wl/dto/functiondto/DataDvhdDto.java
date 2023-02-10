package com.globits.wl.dto.functiondto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmHusbandryMethod;
import com.globits.wl.domain.HusbandryMethod;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FarmHusbandryMethodDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.utils.WLDateTimeUtil;


public class DataDvhdDto {
	
	private String wardCode;
	private String pronvinceName;
	private String districtName;
	private String wardName;
	private String farmCode;
	private String farmName;
	private String village;
	private Date dateUpdate;// ngày tháng cập nhật
	private String animalName;
	private String animalScienceName;
	private String animalCode;
	private Double quantity2015;
	private Double quantity2017;
	private Integer total;
	private Integer male;
	private Integer female;
	private Integer unGender;
	
	private Integer maleParent;// cá thể bố mẹ đực
	private Integer femaleParent;// cá thể bố mẹ cái
	private Integer unGenderParent;// cá thể bố mẹ không rõ  // thêm
	
	private Integer maleGilts;// hậu bị cái
	private Integer femaleGilts;// hậu bị cái
	private Integer unGenderGilts;// hậu bị không rõ  // thêm
	
	private Integer maleChildUnder1YearOld;// con dưới 1 tuổi đực // thêm
	private Integer femaleChildUnder1YearOld;// con dưới 1 tuổi cái // thêm
	private Integer childUnder1YearOld;// con dưới 1 tuổi childUnder1YearOld
	
	private Integer maleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực
	private Integer femaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái
	private Integer unGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính
	
	private String registerStatus;//Tình trạng đăng ký
	private String registerCode;// mã đăng ký
	private Integer registerYear;// năm đăng ký
	private String productTargetCode;// mã mục đích nuôi
	private String originalCode;// mã nguồn gốc
	private String ownerName;//Chủ cơ sở/ đại diện
	private String phoneNumber;// Số điện thoại cơ sở
	private String ownerYearOfBirth;//nam sinh
	private String latitude;//vĩ độ
	private String longitude;//longitude
	private String oldRegistrationCode;
	private String ownerCitizenCode;// Số chứng minh thư chủ cơ sở
	private String ownerPhoneNumber;//Số điện thoại chủ cơ sở
	private String errContent="";
	private Long farmId;
	private Integer year;
	private Farm farm;
	//tran huu dat them cac truong cua form16 moi start
	private Integer apartmentNumber;// so nha
	private String adressDetail; //dia chi chi tiet
	private Date startDate;// ngay bat dau
	private Date ownerCitizenDate;//ngay cap cmnd
	private String ownerCitizenLocation;// noi cap cmnd
	private String ttbvmt;//tt bao ve moi truong
	private Date ttbvmtDate;// ngay cap tt bao ve moi truong
	private String lodgingAcreage;// Diện tích chuồng trại
	private String note;//ghi chu cua form 16
	private String businessRegistrationNumber;//số đăng ký kinh doanh
	private Date dateRegistration;//Ngày cấp mã số đk theo 06
	private Date dateOtherRegistration;//Ngày cấp giấy CN đăng ký
	//tran huu dat them cac truong cua form16 moi end
	//tran huu dat them truong nhap vao co so start
	
	// nhập
	private Integer totalImport; // tổng nhập (dàng cho khai báo ở mẫu 16)
	private Integer importMaleParent;// ca the nhap vao co so bo me duc
	private Integer importFemaleParent;// ca the nhap vao co so bo me duc
	private Integer importUnGenderParent;// bố mẹ không rõ   nhập  
	
	private Integer importMaleGilts;// ca the nhap vao co so hau bi duc 
	private Integer importFemaleGilts;// ca the nhap vao co so hau bi cai
	private Integer importUnGenderGilts;//  hậu bị không rõ   nhập 
	
	private Integer importMaleChildUnder1YearOld;// con dưới 1 tuổi đực nhập
	private Integer importFemaleChildUnder1YearOld;// con dưới 1 tuổi cái nhập
	private Integer importChildUnder1YearOld;// con dưới 1 tuổi childUnder1YearOld nhap vao co so
	
	private Integer importMaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực nhap vao co so
	private Integer importFemaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái nhap vao co so
	private Integer importUnGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính nhap vao co so
	private String importReason;//lí do nhập
	//tran huu dat them truong nhap vao co so end
	//tran huu dat them truong xuat ra khoi co so start	
	// xuất
	private Integer totalExport; // tổng xuất (dàng cho khai báo ở mẫu 16)
	
	private Integer exportMaleParent;// ca the xuat ra khoi co so bo me duc
	private Integer exportFemaleParent;// ca the xuat ra khoi co so bo me duc
	private Integer exportUnGenderParent;// bố mẹ không rõ   xuất  
	
	private Integer exportMaleGilts;// ca the xuat ra khoi co so hau bi duc 
	private Integer exportFemaleGilts;// ca the xuat ra khoi co so hau bi cai
	private Integer exportUnGenderGilts;//  hậu bị không rõ   xuất  
	
	private Integer exportMaleChildUnder1YearOld;// con dưới 1 tuổi đực xuất
	private Integer exportFemaleChildUnder1YearOld;// con dưới 1 tuổi cái xuất
	private Integer exportChildUnder1YearOld;// con dưới 1 tuổi childUnder1YearOld xuat ra khoi co so
	
	private Integer exportMaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
	private Integer exportFemaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
	private Integer exportUnGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
	private String exportReason;//lí do xuất
	//tran huu dat them truong xuat ra khoi co so end
	private String husbandryMethods;
	
	
	public String getHusbandryMethods() {
		return husbandryMethods;
	}
	public void setHusbandryMethods(String husbandryMethods) {
		this.husbandryMethods = husbandryMethods;
	}
	public String getPronvinceName() {
		return pronvinceName;
	}
	public Date getDateRegistration() {
		return dateRegistration;
	}
	public void setDateRegistration(Date dateRegistration) {
		this.dateRegistration = dateRegistration;
	}
	
	public Date getDateOtherRegistration() {
		return dateOtherRegistration;
	}
	public void setDateOtherRegistration(Date dateOtherRegistration) {
		this.dateOtherRegistration = dateOtherRegistration;
	}
	public String getBusinessRegistrationNumber() {
		return businessRegistrationNumber;
	}
	public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
		this.businessRegistrationNumber = businessRegistrationNumber;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getOwnerYearOfBirth() {
		return ownerYearOfBirth;
	}
	public void setOwnerYearOfBirth(String ownerYearOfBirth) {
		this.ownerYearOfBirth = ownerYearOfBirth;
	}
	public String getLodgingAcreage() {
		return lodgingAcreage;
	}
	public void setLodgingAcreage(String lodgingAcreage) {
		this.lodgingAcreage = lodgingAcreage;
	}
	public void setPronvinceName(String pronvinceName) {
		this.pronvinceName = pronvinceName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	public String getFarmCode() {
		return farmCode;
	}
	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}
	public String getFarmName() {
		return farmName;
	}
	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public Date getDateUpdate() {
		return dateUpdate;
	}
	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	public String getAnimalName() {
		return animalName;
	}
	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}
	public String getAnimalScienceName() {
		return animalScienceName;
	}
	public void setAnimalScienceName(String animalScienceName) {
		this.animalScienceName = animalScienceName;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	public Double getQuantity2015() {
		return quantity2015;
	}
	public void setQuantity2015(Double quantity2015) {
		this.quantity2015 = quantity2015;
	}
	public Double getQuantity2017() {
		return quantity2017;
	}
	public void setQuantity2017(Double quantity2017) {
		this.quantity2017 = quantity2017;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getMale() {
		return male;
	}
	public void setMale(Integer male) {
		this.male = male;
	}
	public Integer getFemale() {
		return female;
	}
	public void setFemale(Integer female) {
		this.female = female;
	}
	public Integer getUnGender() {
		return unGender;
	}
	public void setUnGender(Integer unGender) {
		this.unGender = unGender;
	}
	public Integer getMaleParent() {
		return maleParent;
	}
	public void setMaleParent(Integer maleParent) {
		this.maleParent = maleParent;
	}
	public Integer getFemaleParent() {
		return femaleParent;
	}
	public void setFemaleParent(Integer femaleParent) {
		this.femaleParent = femaleParent;
	}
	public Integer getMaleGilts() {
		return maleGilts;
	}
	public void setMaleGilts(Integer maleGilts) {
		this.maleGilts = maleGilts;
	}
	public Integer getFemaleGilts() {
		return femaleGilts;
	}
	public void setFemaleGilts(Integer femaleGilts) {
		this.femaleGilts = femaleGilts;
	}
	public Integer getChildUnder1YearOld() {
		return childUnder1YearOld;
	}
	public void setChildUnder1YearOld(Integer childUnder1YearOld) {
		this.childUnder1YearOld = childUnder1YearOld;
	}
	public Integer getMaleChildOver1YearOld() {
		return maleChildOver1YearOld;
	}
	public void setMaleChildOver1YearOld(Integer maleChildOver1YearOld) {
		this.maleChildOver1YearOld = maleChildOver1YearOld;
	}
	public Integer getFemaleChildOver1YearOld() {
		return femaleChildOver1YearOld;
	}
	public void setFemaleChildOver1YearOld(Integer femaleChildOver1YearOld) {
		this.femaleChildOver1YearOld = femaleChildOver1YearOld;
	}
	public Integer getUnGenderChildOver1YearOld() {
		return unGenderChildOver1YearOld;
	}
	public void setUnGenderChildOver1YearOld(Integer unGenderChildOver1YearOld) {
		this.unGenderChildOver1YearOld = unGenderChildOver1YearOld;
	}
	public String getRegisterStatus() {
		return registerStatus;
	}
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	
	public String getProductTargetCode() {
		return productTargetCode;
	}
	public void setProductTargetCode(String productTargetCode) {
		this.productTargetCode = productTargetCode;
	}
	public String getOriginalCode() {
		return originalCode;
	}
	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
	}
	public Integer getRegisterYear() {
		return registerYear;
	}
	public void setRegisterYear(Integer registerYear) {
		this.registerYear = registerYear;
	}
	public String getWardCode() {
		return wardCode;
	}
	public void setWardCode(String wardCode) {
		this.wardCode = wardCode;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getOldRegistrationCode() {
		return oldRegistrationCode;
	}
	public void setOldRegistrationCode(String oldRegistrationCode) {
		this.oldRegistrationCode = oldRegistrationCode;
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
	public String getErrContent() {
		return errContent;
	}
	public void setErrContent(String errContent) {
		this.errContent = errContent;
	}
	public Long getFarmId() {
		return farmId;
	}
	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Farm getFarm() {
		return farm;
	}
	public void setFarm(Farm farm) {
		this.farm = farm;
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
	public String getAdressDetail() {
		return adressDetail;
	}
	public void setAdressDetail(String adressDetail) {
		this.adressDetail = adressDetail;
	}
	public Integer getImportMaleParent() {
		return importMaleParent;
	}
	public void setImportMaleParent(Integer importMaleParent) {
		this.importMaleParent = importMaleParent;
	}
	public Integer getImportFemaleParent() {
		return importFemaleParent;
	}
	public void setImportFemaleParent(Integer importFemaleParent) {
		this.importFemaleParent = importFemaleParent;
	}
	public Integer getImportMaleGilts() {
		return importMaleGilts;
	}
	public void setImportMaleGilts(Integer importMaleGilts) {
		this.importMaleGilts = importMaleGilts;
	}
	public Integer getImportFemaleGilts() {
		return importFemaleGilts;
	}
	public void setImportFemaleGilts(Integer importFemaleGilts) {
		this.importFemaleGilts = importFemaleGilts;
	}
	public Integer getImportChildUnder1YearOld() {
		return importChildUnder1YearOld;
	}
	public void setImportChildUnder1YearOld(Integer importChildUnder1YearOld) {
		this.importChildUnder1YearOld = importChildUnder1YearOld;
	}
	public Integer getImportMaleChildOver1YearOld() {
		return importMaleChildOver1YearOld;
	}
	public void setImportMaleChildOver1YearOld(Integer importMaleChildOver1YearOld) {
		this.importMaleChildOver1YearOld = importMaleChildOver1YearOld;
	}
	public Integer getImportFemaleChildOver1YearOld() {
		return importFemaleChildOver1YearOld;
	}
	public void setImportFemaleChildOver1YearOld(Integer importFemaleChildOver1YearOld) {
		this.importFemaleChildOver1YearOld = importFemaleChildOver1YearOld;
	}
	public Integer getImportUnGenderChildOver1YearOld() {
		return importUnGenderChildOver1YearOld;
	}
	public void setImportUnGenderChildOver1YearOld(Integer importUnGenderChildOver1YearOld) {
		this.importUnGenderChildOver1YearOld = importUnGenderChildOver1YearOld;
	}
	public String getImportReason() {
		return importReason;
	}
	public void setImportReason(String importReason) {
		this.importReason = importReason;
	}
	public Integer getExportMaleParent() {
		return exportMaleParent;
	}
	public void setExportMaleParent(Integer exportMaleParent) {
		this.exportMaleParent = exportMaleParent;
	}
	public Integer getExportFemaleParent() {
		return exportFemaleParent;
	}
	public void setExportFemaleParent(Integer exportFemaleParent) {
		this.exportFemaleParent = exportFemaleParent;
	}
	public Integer getExportMaleGilts() {
		return exportMaleGilts;
	}
	public void setExportMaleGilts(Integer exportMaleGilts) {
		this.exportMaleGilts = exportMaleGilts;
	}
	public Integer getExportFemaleGilts() {
		return exportFemaleGilts;
	}
	public void setExportFemaleGilts(Integer exportFemaleGilts) {
		this.exportFemaleGilts = exportFemaleGilts;
	}
	public Integer getExportChildUnder1YearOld() {
		return exportChildUnder1YearOld;
	}
	public void setExportChildUnder1YearOld(Integer exportChildUnder1YearOld) {
		this.exportChildUnder1YearOld = exportChildUnder1YearOld;
	}
	public Integer getExportMaleChildOver1YearOld() {
		return exportMaleChildOver1YearOld;
	}
	public void setExportMaleChildOver1YearOld(Integer exportMaleChildOver1YearOld) {
		this.exportMaleChildOver1YearOld = exportMaleChildOver1YearOld;
	}
	public Integer getExportFemaleChildOver1YearOld() {
		return exportFemaleChildOver1YearOld;
	}
	public void setExportFemaleChildOver1YearOld(Integer exportFemaleChildOver1YearOld) {
		this.exportFemaleChildOver1YearOld = exportFemaleChildOver1YearOld;
	}
	public Integer getExportUnGenderChildOver1YearOld() {
		return exportUnGenderChildOver1YearOld;
	}
	public void setExportUnGenderChildOver1YearOld(Integer exportUnGenderChildOver1YearOld) {
		this.exportUnGenderChildOver1YearOld = exportUnGenderChildOver1YearOld;
	}
	public String getExportReason() {
		return exportReason;
	}
	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}
	public Integer getUnGenderParent() {
		return unGenderParent;
	}
	public void setUnGenderParent(Integer unGenderParent) {
		this.unGenderParent = unGenderParent;
	}
	public Integer getUnGenderGilts() {
		return unGenderGilts;
	}
	public void setUnGenderGilts(Integer unGenderGilts) {
		this.unGenderGilts = unGenderGilts;
	}
	public Integer getMaleChildUnder1YearOld() {
		return maleChildUnder1YearOld;
	}
	public void setMaleChildUnder1YearOld(Integer maleChildUnder1YearOld) {
		this.maleChildUnder1YearOld = maleChildUnder1YearOld;
	}
	public Integer getFemaleChildUnder1YearOld() {
		return femaleChildUnder1YearOld;
	}
	public void setFemaleChildUnder1YearOld(Integer femaleChildUnder1YearOld) {
		this.femaleChildUnder1YearOld = femaleChildUnder1YearOld;
	}
	public Integer getTotalImport() {
		return totalImport;
	}
	public void setTotalImport(Integer totalImport) {
		this.totalImport = totalImport;
	}
	public Integer getImportUnGenderParent() {
		return importUnGenderParent;
	}
	public void setImportUnGenderParent(Integer importUnGenderParent) {
		this.importUnGenderParent = importUnGenderParent;
	}
	public Integer getImportUnGenderGilts() {
		return importUnGenderGilts;
	}
	public void setImportUnGenderGilts(Integer importUnGenderGilts) {
		this.importUnGenderGilts = importUnGenderGilts;
	}
	public Integer getImportMaleChildUnder1YearOld() {
		return importMaleChildUnder1YearOld;
	}
	public void setImportMaleChildUnder1YearOld(Integer importMaleChildUnder1YearOld) {
		this.importMaleChildUnder1YearOld = importMaleChildUnder1YearOld;
	}
	public Integer getImportFemaleChildUnder1YearOld() {
		return importFemaleChildUnder1YearOld;
	}
	public void setImportFemaleChildUnder1YearOld(Integer importFemaleChildUnder1YearOld) {
		this.importFemaleChildUnder1YearOld = importFemaleChildUnder1YearOld;
	}
	public Integer getTotalExport() {
		return totalExport;
	}
	public void setTotalExport(Integer totalExport) {
		this.totalExport = totalExport;
	}
	public Integer getExportUnGenderParent() {
		return exportUnGenderParent;
	}
	public void setExportUnGenderParent(Integer exportUnGenderParent) {
		this.exportUnGenderParent = exportUnGenderParent;
	}
	public Integer getExportUnGenderGilts() {
		return exportUnGenderGilts;
	}
	public void setExportUnGenderGilts(Integer exportUnGenderGilts) {
		this.exportUnGenderGilts = exportUnGenderGilts;
	}
	public Integer getExportMaleChildUnder1YearOld() {
		return exportMaleChildUnder1YearOld;
	}
	public void setExportMaleChildUnder1YearOld(Integer exportMaleChildUnder1YearOld) {
		this.exportMaleChildUnder1YearOld = exportMaleChildUnder1YearOld;
	}
	public Integer getExportFemaleChildUnder1YearOld() {
		return exportFemaleChildUnder1YearOld;
	}
	public void setExportFemaleChildUnder1YearOld(Integer exportFemaleChildUnder1YearOld) {
		this.exportFemaleChildUnder1YearOld = exportFemaleChildUnder1YearOld;
	}
	
	public FarmDto convertToFarmDto() {
		FarmDto farmDto = new FarmDto();
		farmDto.setName(this.farmName);
		if(this.registerYear != null && this.registerYear > 0) {
			farmDto.setYearRegistration(this.registerYear+"");
		}		
		if(this.wardCode != null && this.wardCode.trim().length() > 0) {
			FmsAdministrativeUnitDto fmsAdministrativeUnitDto = new FmsAdministrativeUnitDto();
			fmsAdministrativeUnitDto.setCode(this.wardCode);
			farmDto.setAdministrativeUnit(fmsAdministrativeUnitDto);
		}
		farmDto.setNewRegistrationCode(this.registerCode);
		farmDto.setOldRegistrationCode(oldRegistrationCode);
		farmDto.setLatitude(latitude);
		farmDto.setLongitude(longitude);
		farmDto.setOwnerName(ownerName);
		farmDto.setPhoneNumber(phoneNumber);
		farmDto.setOwnerCitizenCode(ownerCitizenCode);
		farmDto.setOwnerPhoneNumber(ownerPhoneNumber);
		farmDto.setVillage(village);
		//tinh trang dang ki 
		if(this.getRegisterStatus() != null) {
			double a = Double.valueOf(this.getRegisterStatus());
			if(a==1) {
				farmDto.setStatus(3);
			}else {
				farmDto.setStatus((int)a);
			}
		}
		
		//dientich test
		farmDto.setLodgingAcreage(Double.valueOf(lodgingAcreage));
		
		//nam sinh
		if(this.ownerYearOfBirth!=null) {
			try {
				Date dob = WLDateTimeUtil.numberToDate(1,1, Integer.parseInt(this.ownerYearOfBirth));
				farmDto.setOwnerDob(dob);
			}catch(Exception e){
				
			}
		}
		farmDto.setBusinessRegistrationNumber(businessRegistrationNumber);
		farmDto.setOwnerCitizenDate(ownerCitizenDate);
		farmDto.setOwnerCitizenLocation(ownerCitizenLocation);
		farmDto.setStartDate(startDate);
		farmDto.setApartmentNumber(apartmentNumber);
		farmDto.setAdressDetail(adressDetail);
		farmDto.setTtbvmt(ttbvmt);
		farmDto.setTtbvmtDate(ttbvmtDate);
		farmDto.setFarmProductTargets(null);
		farmDto.setFarmHusbandryMethods(null);
		return farmDto;
	}
	
	public ReportForm16Dto convertToReportForm16() {
		ReportForm16Dto reportForm16 = new ReportForm16Dto();
		reportForm16.setMaleParent(this.maleParent);
		reportForm16.setFemaleParent(this.femaleParent);
		reportForm16.setUnGenderParent(this.unGenderParent);
		
		reportForm16.setMaleGilts(this.maleGilts);
		reportForm16.setFemaleGilts(this.femaleGilts);
		reportForm16.setUnGenderGilts(this.unGenderGilts);
		
		reportForm16.setMaleChildUnder1YearOld(this.maleChildUnder1YearOld);
		reportForm16.setFemaleChildUnder1YearOld(this.femaleChildUnder1YearOld);
		reportForm16.setChildUnder1YearOld(this.childUnder1YearOld);
		
		reportForm16.setMaleChildOver1YearOld(this.maleChildOver1YearOld);
		reportForm16.setFemaleChildOver1YearOld(this.femaleChildOver1YearOld);
		reportForm16.setUnGenderChildOver1YearOld(this.unGenderChildOver1YearOld);
		
		reportForm16.setTotal(0);
		reportForm16.setMale(0);
		reportForm16.setFemale(0);
		reportForm16.setUnGender(0);
		
		if(this.maleParent != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.maleParent);
			reportForm16.setMale(reportForm16.getMale() + this.maleParent);
		}
		if(this.femaleParent != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.femaleParent);
			reportForm16.setFemale(reportForm16.getFemale() + this.femaleParent);
		}
		if(this.unGenderParent != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.unGenderParent);
			reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderParent);
		}
		
		if(this.maleGilts != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.maleGilts);
			reportForm16.setMale(reportForm16.getMale() + this.maleGilts);
		}
		if(this.femaleGilts != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.femaleGilts);
			reportForm16.setFemale(reportForm16.getFemale() + this.femaleGilts);
		}
		if(this.unGenderGilts != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.unGenderGilts);
			reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderGilts);
		}
		
		if(this.maleChildUnder1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.maleChildUnder1YearOld);
			reportForm16.setMale(reportForm16.getMale() + this.maleChildUnder1YearOld);
		}
		if(this.femaleChildUnder1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.femaleChildUnder1YearOld);
			reportForm16.setFemale(reportForm16.getFemale() + this.femaleChildUnder1YearOld);
		}
		if(this.childUnder1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.childUnder1YearOld);
			reportForm16.setUnGender(reportForm16.getUnGender() + this.childUnder1YearOld);
		} 
		
		if(this.maleChildOver1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.maleChildOver1YearOld);
			reportForm16.setMale(reportForm16.getMale() + this.maleChildOver1YearOld);
		}
		if(this.femaleChildOver1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.femaleChildOver1YearOld);
			reportForm16.setFemale(reportForm16.getFemale() + this.femaleChildOver1YearOld);
		}
		if(this.unGenderChildOver1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.unGenderChildOver1YearOld);
			reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderChildOver1YearOld);
		}
		
		//tran huu dat them cac truong nhap xuat
		reportForm16.setImportMaleParent(importMaleParent);
		reportForm16.setImportFemaleParent(importFemaleParent);
		reportForm16.setImportUnGenderParent(importUnGenderParent);
		
		reportForm16.setImportMaleGilts(importMaleGilts);
		reportForm16.setImportFemaleGilts(importFemaleGilts);
		reportForm16.setImportUnGenderGilts(importUnGenderGilts);
		
		reportForm16.setImportMaleChildUnder1YearOld(importMaleChildUnder1YearOld);
		reportForm16.setImportFemaleChildUnder1YearOld(importFemaleChildUnder1YearOld);
		reportForm16.setImportChildUnder1YearOld(importChildUnder1YearOld);
		
		reportForm16.setImportFemaleChildOver1YearOld(importFemaleChildOver1YearOld);
		reportForm16.setImportMaleChildOver1YearOld(importMaleChildOver1YearOld);
		reportForm16.setImportUnGenderChildOver1YearOld(importUnGenderChildOver1YearOld);
		reportForm16.setImportReason(importReason);
		
		Integer totalImport=0;
		if(importMaleParent != null) totalImport+=importMaleParent;
		if(importFemaleParent != null) totalImport+=importFemaleParent;
		if(importUnGenderParent != null) totalImport+=importUnGenderParent;
		
		if(importMaleGilts != null) totalImport+=importMaleGilts;
		if(importFemaleGilts != null) totalImport+=importFemaleGilts;
		if(importUnGenderGilts != null) totalImport+=importUnGenderGilts;
		
		if(importMaleChildUnder1YearOld != null) totalImport+=importMaleChildUnder1YearOld;
		if(importFemaleChildUnder1YearOld != null) totalImport+=importFemaleChildUnder1YearOld;
		if(importChildUnder1YearOld != null) totalImport+=importChildUnder1YearOld;
		
		if(importFemaleChildOver1YearOld != null) totalImport+=importFemaleChildOver1YearOld;
		if(importMaleChildOver1YearOld != null) totalImport+=importMaleChildOver1YearOld;
		if(importUnGenderChildOver1YearOld != null) totalImport+=importUnGenderChildOver1YearOld;
		
		reportForm16.setTotalImport(totalImport);
		
		reportForm16.setExportMaleParent(exportMaleParent);
		reportForm16.setExportFemaleParent(exportFemaleParent);
		reportForm16.setExportUnGenderParent(exportUnGenderParent);
		
		reportForm16.setExportMaleGilts(exportMaleGilts);
		reportForm16.setExportFemaleGilts(exportFemaleGilts);
		reportForm16.setExportUnGenderGilts(exportUnGenderGilts);
		
		reportForm16.setExportMaleChildUnder1YearOld(exportMaleChildUnder1YearOld);
		reportForm16.setExportFemaleChildUnder1YearOld(exportFemaleChildUnder1YearOld);
		reportForm16.setExportChildUnder1YearOld(exportChildUnder1YearOld);
		
		reportForm16.setExportMaleChildOver1YearOld(exportMaleChildOver1YearOld);
		reportForm16.setExportFemaleChildOver1YearOld(exportFemaleChildOver1YearOld);
		reportForm16.setExportUnGenderChildOver1YearOld(exportUnGenderChildOver1YearOld);
		reportForm16.setExportReason(exportReason);	
		
		Integer totalExport=0;
		if(exportMaleParent != null) totalExport+=exportMaleParent;
		if(exportFemaleParent != null) totalExport+=exportFemaleParent;
		if(exportUnGenderParent != null) totalExport+=exportUnGenderParent;
		
		if(exportMaleGilts != null) totalExport+=exportMaleGilts;
		if(exportFemaleGilts != null) totalExport+=exportFemaleGilts;
		if(exportUnGenderGilts != null) totalExport+=exportUnGenderGilts;
		
		if(exportMaleChildUnder1YearOld != null) totalExport+=exportMaleChildUnder1YearOld;
		if(exportFemaleChildUnder1YearOld != null) totalExport+=exportFemaleChildUnder1YearOld;
		if(exportChildUnder1YearOld != null) totalExport+=exportChildUnder1YearOld;
		
		if(exportFemaleChildOver1YearOld != null) totalExport+=exportFemaleChildOver1YearOld;
		if(exportMaleChildOver1YearOld != null) totalExport+=exportMaleChildOver1YearOld;
		if(exportUnGenderChildOver1YearOld != null) totalExport+=exportUnGenderChildOver1YearOld;
		
		reportForm16.setTotalExport(totalExport);
		
		reportForm16.setNote(note);
		//tran huu dat them cac truong nhap xuat
		
		return reportForm16;
	}
	
	public ReportForm16 convertToReportForm16Entity() {
		ReportForm16 reportForm16 = new ReportForm16();
		reportForm16.setMaleParent(this.maleParent);
		reportForm16.setFemaleParent(this.femaleParent);
		reportForm16.setUnGenderParent(this.unGenderParent);
		
		reportForm16.setMaleGilts(this.maleGilts);
		reportForm16.setFemaleGilts(this.femaleGilts);
		reportForm16.setUnGenderGilts(this.unGenderGilts);
		
		reportForm16.setMaleChildUnder1YearOld(this.maleChildUnder1YearOld);
		reportForm16.setFemaleChildUnder1YearOld(this.femaleChildUnder1YearOld);
		reportForm16.setChildUnder1YearOld(this.childUnder1YearOld);
		
		reportForm16.setMaleChildOver1YearOld(this.maleChildOver1YearOld);
		reportForm16.setFemaleChildOver1YearOld(this.femaleChildOver1YearOld);
		reportForm16.setUnGenderChildOver1YearOld(this.unGenderChildOver1YearOld);
		
		reportForm16.setTotal(0);
		reportForm16.setMale(0);
		reportForm16.setFemale(0);
		reportForm16.setUnGender(0);
		
		if(this.maleParent != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.maleParent);
			reportForm16.setMale(reportForm16.getMale() + this.maleParent);
		}
		if(this.femaleParent != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.femaleParent);
			reportForm16.setFemale(reportForm16.getFemale() + this.femaleParent);
		}
		if(this.unGenderParent != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.unGenderParent);
			reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderParent);
		}
		
		if(this.maleGilts != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.maleGilts);
			reportForm16.setMale(reportForm16.getMale() + this.maleGilts);
		}
		if(this.femaleGilts != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.femaleGilts);
			reportForm16.setFemale(reportForm16.getFemale() + this.femaleGilts);
		}
		if(this.unGenderGilts != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.unGenderGilts);
			reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderGilts);
		}
		
		if(this.maleChildUnder1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.maleChildUnder1YearOld);
			reportForm16.setMale(reportForm16.getMale() + this.maleChildUnder1YearOld);
		}
		if(this.femaleChildUnder1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.femaleChildUnder1YearOld);
			reportForm16.setFemale(reportForm16.getFemale() + this.femaleChildUnder1YearOld);
		}
		if(this.childUnder1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.childUnder1YearOld);
			reportForm16.setUnGender(reportForm16.getUnGender() + this.childUnder1YearOld);
		} 
		
		if(this.maleChildOver1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.maleChildOver1YearOld);
			reportForm16.setMale(reportForm16.getMale() + this.maleChildOver1YearOld);
		}
		if(this.femaleChildOver1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.femaleChildOver1YearOld);
			reportForm16.setFemale(reportForm16.getFemale() + this.femaleChildOver1YearOld);
		}
		if(this.unGenderChildOver1YearOld != null) {
			reportForm16.setTotal(reportForm16.getTotal() + this.unGenderChildOver1YearOld);
			reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderChildOver1YearOld);
		}
		
		//tran huu dat them cac truong nhap xuat
		reportForm16.setImportMaleParent(importMaleParent);
		reportForm16.setImportFemaleParent(importFemaleParent);
		reportForm16.setImportUnGenderParent(importUnGenderParent);
		
		reportForm16.setImportMaleGilts(importMaleGilts);
		reportForm16.setImportFemaleGilts(importFemaleGilts);
		reportForm16.setImportUnGenderGilts(importUnGenderGilts);
		
		reportForm16.setImportMaleChildUnder1YearOld(importMaleChildUnder1YearOld);
		reportForm16.setImportFemaleChildUnder1YearOld(importFemaleChildUnder1YearOld);
		reportForm16.setImportChildUnder1YearOld(importChildUnder1YearOld);
		
		reportForm16.setImportFemaleChildOver1YearOld(importFemaleChildOver1YearOld);
		reportForm16.setImportMaleChildOver1YearOld(importMaleChildOver1YearOld);
		reportForm16.setImportUnGenderChildOver1YearOld(importUnGenderChildOver1YearOld);
		reportForm16.setImportReason(importReason);
		
		Integer totalImport=0;
		if(importMaleParent != null) totalImport+=importMaleParent;
		if(importFemaleParent != null) totalImport+=importFemaleParent;
		if(importUnGenderParent != null) totalImport+=importUnGenderParent;
		
		if(importMaleGilts != null) totalImport+=importMaleGilts;
		if(importFemaleGilts != null) totalImport+=importFemaleGilts;
		if(importUnGenderGilts != null) totalImport+=importUnGenderGilts;
		
		if(importMaleChildUnder1YearOld != null) totalImport+=importMaleChildUnder1YearOld;
		if(importFemaleChildUnder1YearOld != null) totalImport+=importFemaleChildUnder1YearOld;
		if(importChildUnder1YearOld != null) totalImport+=importChildUnder1YearOld;
		
		if(importFemaleChildOver1YearOld != null) totalImport+=importFemaleChildOver1YearOld;
		if(importMaleChildOver1YearOld != null) totalImport+=importMaleChildOver1YearOld;
		if(importUnGenderChildOver1YearOld != null) totalImport+=importUnGenderChildOver1YearOld;
		
		reportForm16.setTotalImport(totalImport);
		
		reportForm16.setExportMaleParent(exportMaleParent);
		reportForm16.setExportFemaleParent(exportFemaleParent);
		reportForm16.setExportUnGenderParent(exportUnGenderParent);
		
		reportForm16.setExportMaleGilts(exportMaleGilts);
		reportForm16.setExportFemaleGilts(exportFemaleGilts);
		reportForm16.setExportUnGenderGilts(exportUnGenderGilts);
		
		reportForm16.setExportMaleChildUnder1YearOld(exportMaleChildUnder1YearOld);
		reportForm16.setExportFemaleChildUnder1YearOld(exportFemaleChildUnder1YearOld);
		reportForm16.setExportChildUnder1YearOld(exportChildUnder1YearOld);
		
		reportForm16.setExportMaleChildOver1YearOld(exportMaleChildOver1YearOld);
		reportForm16.setExportFemaleChildOver1YearOld(exportFemaleChildOver1YearOld);
		reportForm16.setExportUnGenderChildOver1YearOld(exportUnGenderChildOver1YearOld);
		reportForm16.setExportReason(exportReason);	
		
		Integer totalExport=0;
		if(exportMaleParent != null) totalExport+=exportMaleParent;
		if(exportFemaleParent != null) totalExport+=exportFemaleParent;
		if(exportUnGenderParent != null) totalExport+=exportUnGenderParent;
		
		if(exportMaleGilts != null) totalExport+=exportMaleGilts;
		if(exportFemaleGilts != null) totalExport+=exportFemaleGilts;
		if(exportUnGenderGilts != null) totalExport+=exportUnGenderGilts;
		
		if(exportMaleChildUnder1YearOld != null) totalExport+=exportMaleChildUnder1YearOld;
		if(exportFemaleChildUnder1YearOld != null) totalExport+=exportFemaleChildUnder1YearOld;
		if(exportChildUnder1YearOld != null) totalExport+=exportChildUnder1YearOld;
		
		if(exportFemaleChildOver1YearOld != null) totalExport+=exportFemaleChildOver1YearOld;
		if(exportMaleChildOver1YearOld != null) totalExport+=exportMaleChildOver1YearOld;
		if(exportUnGenderChildOver1YearOld != null) totalExport+=exportUnGenderChildOver1YearOld;
		
		reportForm16.setTotalExport(totalExport);
		
		reportForm16.setNote(note);
		//tran huu dat them cac truong nhap xuat
		
		return reportForm16;
	}
	
	public ReportForm16 updateReportForm16(ReportForm16 reportForm16) {
		if(reportForm16!=null) {
			reportForm16.setMaleParent(this.maleParent);
			reportForm16.setFemaleParent(this.femaleParent);
			reportForm16.setUnGenderParent(this.unGenderParent);
			
			reportForm16.setMaleGilts(this.maleGilts);
			reportForm16.setFemaleGilts(this.femaleGilts);
			reportForm16.setUnGenderGilts(this.unGenderGilts);
			
			reportForm16.setMaleChildUnder1YearOld(this.maleChildUnder1YearOld);
			reportForm16.setFemaleChildUnder1YearOld(this.femaleChildUnder1YearOld);
			reportForm16.setChildUnder1YearOld(this.childUnder1YearOld);
			
			reportForm16.setMaleChildOver1YearOld(this.maleChildOver1YearOld);
			reportForm16.setFemaleChildOver1YearOld(this.femaleChildOver1YearOld);
			reportForm16.setUnGenderChildOver1YearOld(this.unGenderChildOver1YearOld);
			
			reportForm16.setTotal(0);
			reportForm16.setMale(0);
			reportForm16.setFemale(0);
			reportForm16.setUnGender(0);
			
			if(this.maleParent != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.maleParent);
				reportForm16.setMale(reportForm16.getMale() + this.maleParent);
			}
			if(this.femaleParent != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.femaleParent);
				reportForm16.setFemale(reportForm16.getFemale() + this.femaleParent);
			}
			if(this.unGenderParent != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.unGenderParent);
				reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderParent);
			}
			
			if(this.maleGilts != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.maleGilts);
				reportForm16.setMale(reportForm16.getMale() + this.maleGilts);
			}
			if(this.femaleGilts != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.femaleGilts);
				reportForm16.setFemale(reportForm16.getFemale() + this.femaleGilts);
			}
			if(this.unGenderGilts != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.unGenderGilts);
				reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderGilts);
			}
			
			if(this.maleChildUnder1YearOld != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.maleChildUnder1YearOld);
				reportForm16.setMale(reportForm16.getMale() + this.maleChildUnder1YearOld);
			}
			if(this.femaleChildUnder1YearOld != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.femaleChildUnder1YearOld);
				reportForm16.setFemale(reportForm16.getFemale() + this.femaleChildUnder1YearOld);
			}
			if(this.childUnder1YearOld != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.childUnder1YearOld);
				reportForm16.setUnGender(reportForm16.getUnGender() + this.childUnder1YearOld);
			} 
			
			if(this.maleChildOver1YearOld != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.maleChildOver1YearOld);
				reportForm16.setMale(reportForm16.getMale() + this.maleChildOver1YearOld);
			}
			if(this.femaleChildOver1YearOld != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.femaleChildOver1YearOld);
				reportForm16.setFemale(reportForm16.getFemale() + this.femaleChildOver1YearOld);
			}
			if(this.unGenderChildOver1YearOld != null) {
				reportForm16.setTotal(reportForm16.getTotal() + this.unGenderChildOver1YearOld);
				reportForm16.setUnGender(reportForm16.getUnGender() + this.unGenderChildOver1YearOld);
			}
			
			//tran huu dat them cac truong nhap xuat
			reportForm16.setImportMaleParent(importMaleParent);
			reportForm16.setImportFemaleParent(importFemaleParent);
			reportForm16.setImportUnGenderParent(importUnGenderParent);
			
			reportForm16.setImportMaleGilts(importMaleGilts);
			reportForm16.setImportFemaleGilts(importFemaleGilts);
			reportForm16.setImportUnGenderGilts(importUnGenderGilts);
			
			reportForm16.setImportMaleChildUnder1YearOld(importMaleChildUnder1YearOld);
			reportForm16.setImportFemaleChildUnder1YearOld(importFemaleChildUnder1YearOld);
			reportForm16.setImportChildUnder1YearOld(importChildUnder1YearOld);
			
			reportForm16.setImportFemaleChildOver1YearOld(importFemaleChildOver1YearOld);
			reportForm16.setImportMaleChildOver1YearOld(importMaleChildOver1YearOld);
			reportForm16.setImportUnGenderChildOver1YearOld(importUnGenderChildOver1YearOld);
			reportForm16.setImportReason(importReason);
			
			Integer totalImport=0;
			if(importMaleParent != null) totalImport+=importMaleParent;
			if(importFemaleParent != null) totalImport+=importFemaleParent;
			if(importUnGenderParent != null) totalImport+=importUnGenderParent;
			
			if(importMaleGilts != null) totalImport+=importMaleGilts;
			if(importFemaleGilts != null) totalImport+=importFemaleGilts;
			if(importUnGenderGilts != null) totalImport+=importUnGenderGilts;
			
			if(importMaleChildUnder1YearOld != null) totalImport+=importMaleChildUnder1YearOld;
			if(importFemaleChildUnder1YearOld != null) totalImport+=importFemaleChildUnder1YearOld;
			if(importChildUnder1YearOld != null) totalImport+=importChildUnder1YearOld;
			
			if(importFemaleChildOver1YearOld != null) totalImport+=importFemaleChildOver1YearOld;
			if(importMaleChildOver1YearOld != null) totalImport+=importMaleChildOver1YearOld;
			if(importUnGenderChildOver1YearOld != null) totalImport+=importUnGenderChildOver1YearOld;
			
			reportForm16.setTotalImport(totalImport);
			
			reportForm16.setExportMaleParent(exportMaleParent);
			reportForm16.setExportFemaleParent(exportFemaleParent);
			reportForm16.setExportUnGenderParent(exportUnGenderParent);
			
			reportForm16.setExportMaleGilts(exportMaleGilts);
			reportForm16.setExportFemaleGilts(exportFemaleGilts);
			reportForm16.setExportUnGenderGilts(exportUnGenderGilts);
			
			reportForm16.setExportMaleChildUnder1YearOld(exportMaleChildUnder1YearOld);
			reportForm16.setExportFemaleChildUnder1YearOld(exportFemaleChildUnder1YearOld);
			reportForm16.setExportChildUnder1YearOld(exportChildUnder1YearOld);
			
			reportForm16.setExportMaleChildOver1YearOld(exportMaleChildOver1YearOld);
			reportForm16.setExportFemaleChildOver1YearOld(exportFemaleChildOver1YearOld);
			reportForm16.setExportUnGenderChildOver1YearOld(exportUnGenderChildOver1YearOld);
			reportForm16.setExportReason(exportReason);	
			
			Integer totalExport=0;
			if(exportMaleParent != null) totalExport+=exportMaleParent;
			if(exportFemaleParent != null) totalExport+=exportFemaleParent;
			if(exportUnGenderParent != null) totalExport+=exportUnGenderParent;
			
			if(exportMaleGilts != null) totalExport+=exportMaleGilts;
			if(exportFemaleGilts != null) totalExport+=exportFemaleGilts;
			if(exportUnGenderGilts != null) totalExport+=exportUnGenderGilts;
			
			if(exportMaleChildUnder1YearOld != null) totalExport+=exportMaleChildUnder1YearOld;
			if(exportFemaleChildUnder1YearOld != null) totalExport+=exportFemaleChildUnder1YearOld;
			if(exportChildUnder1YearOld != null) totalExport+=exportChildUnder1YearOld;
			
			if(exportFemaleChildOver1YearOld != null) totalExport+=exportFemaleChildOver1YearOld;
			if(exportMaleChildOver1YearOld != null) totalExport+=exportMaleChildOver1YearOld;
			if(exportUnGenderChildOver1YearOld != null) totalExport+=exportUnGenderChildOver1YearOld;
			
			reportForm16.setTotalExport(totalExport);
			
			reportForm16.setNote(note);
			//tran huu dat them cac truong nhap xuat
		}
		return reportForm16;
	}
	
}
