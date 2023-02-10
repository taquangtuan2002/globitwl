package com.globits.wl.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.globits.core.domain.FileDescription;
import com.globits.core.dto.BaseObjectDto;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.security.dto.UserDto;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.ForestProductsList;
import com.globits.wl.domain.ForestProductsListDetail;
import com.globits.wl.domain.ImportExportAnimal;

public class ForestProductsListDto extends BaseObjectDto{
	/*TỔ CHỨC, CÁ NHÂN LẬP BẢNG KÊ LÂM SẢN*/
	private FarmDto farm;
	private String ownerConfirm;
	private String code;
	/**/
	
	private Date dateIssue;//Ngày khai báo
	private String original;//Nguồn gốc lâm sản
	private String invoiceCode;//Số hóa đơn kèm theo (nếu có);
	private Date invoiceDate;//Ngày hóa đơn kèm theo (nếu có);
	
	private String vehicle;//Phương tiện vận chuyển (nếu có)
	private String vehicleRegistrationPlate;//biển số/số hiệu phương tiện
	
	private Integer transportDuration;//Thời gian vận chuyển (ngày)
	private Date transportStart;//vận chuyển từ ngày
	private Date transportEnd;//vận chuyển đến ngày
	private String transportFrom;//Vận chuyển từ
	private String transportTo;//Vận chuyển đến
	
	
	private FileDescriptionDto file;
	
	// Tỉnh, Xã, Thôn
	private FmsAdministrativeUnitDto provinceFrom;
	private FmsAdministrativeUnitDto provinceTo;
	private FmsAdministrativeUnitDto communeFrom;
	private FmsAdministrativeUnitDto communeTo;
	private String villageFrom;
	private String villageTo;
	private String buyerPhoneNumber;
	
	private FmsAdministrativeUnitDto administrativeUnitFrom;
	private FmsAdministrativeUnitDto administrativeUnitTo;
	private AdministrativeOrganizationDto administrativeOrganization;
	private Long provinceIdFrom;
	private String provinceNameFrom;
	private String provinceCodeFrom;
	private Long provinceIdTo;
	private String provinceNameTo;
	private String provinceCodeTo;
	
	/*XÁC NHẬN CỦA CƠ QUAN KIỂM LÂM SỞ TẠI*/
	private UserDto confirmByUser;//Cán bộ xác nhận (maping với user)
	
	private String userConfirmName;//Cán bộ xác nhận (dạng String)
	/**/
	
	/*Chi tiết bản kê lâm sản*/
	private Set<ForestProductsListDetailDto> details;
	/**/
	/*Các lần xuất đàn có bản kê lâm sản*/
	private Set<ImportExportAnimalDto> exports;
	
	// Xuất excel
	private ForestProductsListDetailDto detail;
	
	private String buyerName;
	private String buyerDetailAddress;
	
	//trạng thái đã bị hủy của bản kê
	private Integer canceled;
	//Thông tin khi xác nhận BKLS
	private Date statusConfirmDate;
	private String statusConfirmName;
	private String noteConfirm;
	private String addressConfirm; //noi xac nhan
	private String signatureName; // họ và tên người ký xác nhận
	

	private List<String> listEmail;
	
	// đơn vị cấp bản kê
	private String organizationName;
	private String provinceName;
	private String organizationCreatedFplName;
	private String addressCreatedFpl; 
	
	// Xác nhận thay đổi thông tin
	private Boolean isCheckFarm;
	
	
	public String getSignatureName() {
		return signatureName;
	}

	public void setSignatureName(String signatureName) {
		this.signatureName = signatureName;
	}

	public String getAddressConfirm() {
		return addressConfirm;
	}

	public void setAddressConfirm(String addressConfirm) {
		this.addressConfirm = addressConfirm;
	}

	public String getAddressCreatedFpl() {
		return addressCreatedFpl;
	}

	public void setAddressCreatedFpl(String addressCreatedFpl) {
		this.addressCreatedFpl = addressCreatedFpl;
	}

	public String getOrganizationCreatedFplName() {
		return organizationCreatedFplName;
	}

	public void setOrganizationCreatedFplName(String organizationCreatedFplName) {
		this.organizationCreatedFplName = organizationCreatedFplName;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Date getStatusConfirmDate() {
		return statusConfirmDate;
	}

	public void setStatusConfirmDate(Date statusConfirmDate) {
		this.statusConfirmDate = statusConfirmDate;
	}

	public String getStatusConfirmName() {
		return statusConfirmName;
	}

	public void setStatusConfirmName(String statusConfirmName) {
		this.statusConfirmName = statusConfirmName;
	}
	
	public String getNoteConfirm() {
		return noteConfirm;
	}

	public void setNoteConfirm(String noteConfirm) {
		this.noteConfirm = noteConfirm;
	}

	public Integer getCanceled() {
		return canceled;
	}

	public void setCanceled(Integer canceled) {
		this.canceled = canceled;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerDetailAddress() {
		return buyerDetailAddress;
	}

	public void setBuyerDetailAddress(String buyerDetailAddress) {
		this.buyerDetailAddress = buyerDetailAddress;
	}

	public Long getProvinceIdFrom() {
		return provinceIdFrom;
	}

	public void setProvinceIdFrom(Long provinceIdFrom) {
		this.provinceIdFrom = provinceIdFrom;
	}

	public String getProvinceNameFrom() {
		return provinceNameFrom;
	}

	public void setProvinceNameFrom(String provinceNameFrom) {
		this.provinceNameFrom = provinceNameFrom;
	}

	public String getProvinceCodeFrom() {
		return provinceCodeFrom;
	}

	public void setProvinceCodeFrom(String provinceCodeFrom) {
		this.provinceCodeFrom = provinceCodeFrom;
	}

	public Long getProvinceIdTo() {
		return provinceIdTo;
	}

	public void setProvinceIdTo(Long provinceIdTo) {
		this.provinceIdTo = provinceIdTo;
	}

	public String getProvinceNameTo() {
		return provinceNameTo;
	}

	public void setProvinceNameTo(String provinceNameTo) {
		this.provinceNameTo = provinceNameTo;
	}

	public String getProvinceCodeTo() {
		return provinceCodeTo;
	}

	public void setProvinceCodeTo(String provinceCodeTo) {
		this.provinceCodeTo = provinceCodeTo;
	}

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

	public String getOwnerConfirm() {
		return ownerConfirm;
	}

	public void setOwnerConfirm(String ownerConfirm) {
		this.ownerConfirm = ownerConfirm;
	}

	public Date getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}

	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getVehicleRegistrationPlate() {
		return vehicleRegistrationPlate;
	}

	public void setVehicleRegistrationPlate(String vehicleRegistrationPlate) {
		this.vehicleRegistrationPlate = vehicleRegistrationPlate;
	}

	public Integer getTransportDuration() {
		return transportDuration;
	}

	public void setTransportDuration(Integer transportDuration) {
		this.transportDuration = transportDuration;
	}

	public Date getTransportStart() {
		return transportStart;
	}

	public void setTransportStart(Date transportStart) {
		this.transportStart = transportStart;
	}

	public Date getTransportEnd() {
		return transportEnd;
	}

	public void setTransportEnd(Date transportEnd) {
		this.transportEnd = transportEnd;
	}

	public String getTransportFrom() {
		return transportFrom;
	}

	public void setTransportFrom(String transportFrom) {
		this.transportFrom = transportFrom;
	}

	public String getTransportTo() {
		return transportTo;
	}

	public void setTransportTo(String transportTo) {
		this.transportTo = transportTo;
	}

	public UserDto getConfirmByUser() {
		return confirmByUser;
	}

	public void setConfirmByUser(UserDto confirmByUser) {
		this.confirmByUser = confirmByUser;
	}

	public String getUserConfirmName() {
		return userConfirmName;
	}

	public void setUserConfirmName(String userConfirmName) {
		this.userConfirmName = userConfirmName;
	}

	public Set<ForestProductsListDetailDto> getDetails() {
		return details;
	}

	public void setDetails(Set<ForestProductsListDetailDto> details) {
		this.details = details;
	}

	public Set<ImportExportAnimalDto> getExports() {
		return exports;
	}

	public void setExports(Set<ImportExportAnimalDto> exports) {
		this.exports = exports;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public FileDescriptionDto getFile() {
		return file;
	}

	public void setFile(FileDescriptionDto file) {
		this.file = file;
	}

	public FmsAdministrativeUnitDto getAdministrativeUnitFrom() {
		return administrativeUnitFrom;
	}

	public void setAdministrativeUnitFrom(FmsAdministrativeUnitDto administrativeUnitFrom) {
		this.administrativeUnitFrom = administrativeUnitFrom;
	}

	public FmsAdministrativeUnitDto getAdministrativeUnitTo() {
		return administrativeUnitTo;
	}

	public void setAdministrativeUnitTo(FmsAdministrativeUnitDto administrativeUnitTo) {
		this.administrativeUnitTo = administrativeUnitTo;
	}

	public ForestProductsListDetailDto getDetail() {
		return detail;
	}

	public void setDetail(ForestProductsListDetailDto detail) {
		this.detail = detail;
	}

	public List<String> getListEmail() {
		return listEmail;
	}

	public void setListEmail(List<String> listEmail) {
		this.listEmail = listEmail;
	}

	public AdministrativeOrganizationDto getAdministrativeOrganization() {
		return administrativeOrganization;
	}

	public void setAdministrativeOrganization(AdministrativeOrganizationDto administrativeOrganization) {
		this.administrativeOrganization = administrativeOrganization;
	}

	public ForestProductsListDto() {
		super();
	}
	
	public ForestProductsListDto(ForestProductsList entity) {
		super();
		this.id = entity.getId();
		if(entity.getFarm() != null) {
			this.farm = new FarmDto(entity.getFarm());
		}
		if(entity.getFile()!=null) {
			this.file= new FileDescriptionDto(entity.getFile());
		}
		
		this.code = entity.getCode();
		this.ownerConfirm = entity.getOwnerConfirm();
		this.dateIssue = entity.getDateIssue();
		this.original = entity.getOriginal();
		this.invoiceCode = entity.getInvoiceCode();
		this.invoiceDate = entity.getInvoiceDate();
		
		this.vehicle = entity.getVehicle();
		this.vehicleRegistrationPlate = entity.getVehicleRegistrationPlate();
		this.transportDuration = entity.getTransportDuration();
		this.transportStart = entity.getTransportStart();
		this.transportEnd = entity.getTransportEnd();
		
		this.transportFrom = entity.getTransportFrom();
		this.transportTo = entity.getTransportTo();
		// Tỉnh, Xã, Thôn 
		if(entity.getProvinceFrom() != null) {
			this.provinceFrom = new FmsAdministrativeUnitDto(entity.getProvinceFrom());
		}
		if(entity.getProvinceTo() != null) {
			this.provinceTo = new FmsAdministrativeUnitDto(entity.getProvinceTo());
		}
		if(entity.getCommuneFrom() != null) {
			this.communeFrom = new FmsAdministrativeUnitDto(entity.getCommuneFrom());
		}
		if(entity.getCommuneTo() != null) {
			this.communeTo = new FmsAdministrativeUnitDto(entity.getCommuneTo());
		}
		this.villageFrom = entity.getVillageFrom();
		this.villageTo = entity.getVillageTo();
		this.buyerPhoneNumber = entity.getBuyerPhonenumber();

		
		if(entity.getAdministrativeUnitFrom()!=null) {
			this.administrativeUnitFrom= new FmsAdministrativeUnitDto(entity.getAdministrativeUnitFrom());
		}
		if(entity.getAdministrativeUnitTo()!=null) {
			this.administrativeUnitTo= new FmsAdministrativeUnitDto(entity.getAdministrativeUnitTo());
		}
		if (entity.getAdministrativeOrganization() != null){
			this.administrativeOrganization = new AdministrativeOrganizationDto(entity.getAdministrativeOrganization());
		}

		if(entity.getConfirmByUser() != null) {
			this.confirmByUser = new UserDto(entity.getConfirmByUser());
		}
		this.userConfirmName = entity.getUserConfirmName();
		this.buyerName=entity.getBuyerName();
		this.buyerDetailAddress=entity.getBuyerDetailAddress();
		this.statusConfirmName=entity.getStatusConfirmName();
		this.statusConfirmDate=entity.getStatusConfirmDate();
		this.canceled=entity.getCanceled();
		this.noteConfirm =entity.getNoteConfirm();
		if(entity.getOrganizationName() !=null) {
			this.organizationName = entity.getOrganizationName();
		}
		if(entity.getProvinceName() != null) {
			this.provinceName = entity.getProvinceName();
		}
		if(entity.getOrganizationCreatedFplName() != null) {
			this.organizationCreatedFplName = entity.getOrganizationCreatedFplName();
		}
		if(entity.getAddressConfirm() != null) {
			this.addressConfirm = entity.getAddressConfirm();
		}
		if(entity.getAddressCreatedFpl() != null) {
			this.addressCreatedFpl = entity.getAddressCreatedFpl();
		}
		if(entity.getSignatureName() != null) {
			this.signatureName = entity.getSignatureName();
		}
		
		if(entity.getDetails() != null && entity.getDetails().size() > 0) {
			this.details = new HashSet<ForestProductsListDetailDto>();
			for(ForestProductsListDetail forestProductsList : entity.getDetails()) {
				if(forestProductsList != null) {
					this.details.add(new ForestProductsListDetailDto(forestProductsList));
				}
			}
		}
	}
	
	public ForestProductsListDto(ForestProductsListDto another) {
		this.id = another.getId();
		if(another.getFarm() != null) {
			this.farm = another.getFarm();
		}
		if(another.getFile()!=null) {
			this.file= another.getFile();
		}
		
		this.code = another.getCode();
		this.ownerConfirm = another.getOwnerConfirm();
		this.dateIssue = another.getDateIssue();
		this.original = another.getOriginal();
		this.invoiceCode = another.getInvoiceCode();
		this.invoiceDate = another.getInvoiceDate();
		
		this.vehicle = another.getVehicle();
		this.vehicleRegistrationPlate = another.getVehicleRegistrationPlate();
		this.transportDuration = another.getTransportDuration();
		this.transportStart = another.getTransportStart();
		this.transportEnd = another.getTransportEnd();
		
		this.transportFrom = another.getTransportFrom();
		this.transportTo = another.getTransportTo();
		if(another.getAdministrativeUnitFrom()!=null) {
			this.administrativeUnitFrom= another.getAdministrativeUnitFrom();
		}
		if(another.getAdministrativeUnitTo()!=null) {
			this.administrativeUnitTo= another.getAdministrativeUnitTo();
		}
		
		if(another.getConfirmByUser() != null) {
			this.confirmByUser = another.getConfirmByUser();
		}
		if(another.getOrganizationCreatedFplName() != null) {
			this.organizationCreatedFplName = another.getOrganizationCreatedFplName();
		}
		if(another.getAddressConfirm() != null) {
			this.addressConfirm = another.getAddressConfirm();
		}
		if(another.getAddressCreatedFpl() != null) {
			this.addressCreatedFpl = another.getAddressCreatedFpl();
		}
		if(another.getSignatureName() != null) {
			this.signatureName = another.getSignatureName();
		}
		this.userConfirmName = another.getUserConfirmName();
		this.buyerName= another.getBuyerName();
		this.buyerDetailAddress=another.getBuyerDetailAddress();
		this.statusConfirmName=another.getStatusConfirmName();
		this.statusConfirmDate=another.getStatusConfirmDate();
		this.canceled = another.getCanceled();
		this.noteConfirm = another.getNoteConfirm();
		if(another.getOrganizationName() !=null) {
			this.organizationName = another.getOrganizationName();
		}
		if(another.getProvinceName() != null) {
			this.provinceName = another.getProvinceName();
		}
		
	}

	public FmsAdministrativeUnitDto getProvinceFrom() {
		return provinceFrom;
	}

	public void setProvinceFrom(FmsAdministrativeUnitDto provinceFrom) {
		this.provinceFrom = provinceFrom;
	}

	public FmsAdministrativeUnitDto getProvinceTo() {
		return provinceTo;
	}

	public void setProvinceTo(FmsAdministrativeUnitDto provinceTo) {
		this.provinceTo = provinceTo;
	}

	public FmsAdministrativeUnitDto getCommuneFrom() {
		return communeFrom;
	}

	public void setCommuneFrom(FmsAdministrativeUnitDto communeFrom) {
		this.communeFrom = communeFrom;
	}

	public FmsAdministrativeUnitDto getCommuneTo() {
		return communeTo;
	}

	public void setCommuneTo(FmsAdministrativeUnitDto communeTo) {
		this.communeTo = communeTo;
	}

	public String getVillageFrom() {
		return villageFrom;
	}

	public void setVillageFrom(String villageFrom) {
		this.villageFrom = villageFrom;
	}

	public String getVillageTo() {
		return villageTo;
	}

	public void setVillageTo(String villageTo) {
		this.villageTo = villageTo;
	}

	public String getBuyerPhoneNumber() {
		return buyerPhoneNumber;
	}

	public void setBuyerPhoneNumber(String buyerPhoneNumber) {
		this.buyerPhoneNumber = buyerPhoneNumber;
	}

	public Boolean getIsCheckFarm() {
		return isCheckFarm;
	}

	public void setIsCheckFarm(Boolean isCheckFarm) {
		this.isCheckFarm = isCheckFarm;
	}
}
