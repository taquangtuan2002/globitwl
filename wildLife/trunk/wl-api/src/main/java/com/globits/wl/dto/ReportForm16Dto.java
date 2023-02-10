package com.globits.wl.dto;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.globits.cms.dto.BaseObjectDto;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ReportForm16;

public class ReportForm16Dto extends BaseObjectDto {

	private Integer type;// Kiểu form A: 1; B: 2

	private Date dateReport;

	private FarmDto farm;

	private ImportExportAnimalDto importExportAnimal;

	private AnimalDto animal;

	private ReportPeriodDto reportPeriod;

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
	private Integer childUnder1YearOld;// con dưới 1 tuổi không rõ giới tính // thêm

	private Integer maleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực

	private Integer femaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái

	private Integer unGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính
	
	// nhập
	private Integer totalImport; // tổng nhập (dàng cho khai báo ở mẫu 16)
	
	private Integer importMaleParent;// bố mẹ đực   nhập  
	private Integer importFemaleParent;// bố mẹ cái   nhập 
	private Integer importUnGenderParent;// bố mẹ không rõ   nhập  
		
	private Integer importMaleGilts;// hậu bị đực   nhập  
	private Integer importFemaleGilts;//  hậu bị cái   nhập  
	private Integer importUnGenderGilts;//  hậu bị không rõ   nhập  
		
	private Integer importMaleChildUnder1YearOld;// con dưới 1 tuổi đực nhập
	private Integer importFemaleChildUnder1YearOld;// con dưới 1 tuổi cái nhập
	private Integer importChildUnder1YearOld;// con dưới 1 tuổi không rõ giới tính nhập
		
	private Integer importMaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực nhap vao co so
	private Integer importFemaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái nhap vao co so
	private Integer importUnGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính nhap vao co so
	
	private String importReason;//lí do nhập
		
	// xuất
	private Integer totalExport; // tổng xuất (dàng cho khai báo ở mẫu 16)
	
	private Integer exportMaleParent;// bố mẹ đực   xuất 
	private Integer exportFemaleParent;// bố mẹ cái   xuất 
	private Integer exportUnGenderParent;// bố mẹ không rõ   xuất  
		
	private Integer exportMaleGilts;// hậu bị đực   xuất  
	private Integer exportFemaleGilts;// hậu bị cái   xuất  
	private Integer exportUnGenderGilts;//  hậu bị không rõ   xuất  
		
	private Integer exportMaleChildUnder1YearOld;// con dưới 1 tuổi đực xuất
	private Integer exportFemaleChildUnder1YearOld;// con dưới 1 tuổi cái xuất
	private Integer exportChildUnder1YearOld;// con dưới 1 tuổi không rõ xuất
	
	private Integer exportMaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
	private Integer exportFemaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
	private Integer exportUnGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
	
	private String exportReason;//lí do xuất
	
			
	private Integer maleImport;// số nhập cơ sở đực

	private Integer femaleImport;// số nhập cơ sở cái

	private Integer unGenderImport;// số nhập cơ sở không xác định giới tính

	private Integer maleExport;// số xuất cơ sở đực

	private Integer femaleExport;// số xuất cơ sở cái

	private Integer unGenderExport;// số xuất cơ sở không xác định giới tính

	private String note;// ghi chú

	private String confirmForestProtection;

	private FmsAdministrativeUnitDto administrativeUnitDto;
	private FmsAdministrativeUnitDto districtDto;// huyện
	private FmsAdministrativeUnitDto provinceDto;// tỉnh
	private Boolean isLastRecord;// là bản ghi cuối cùng
	
	// Thêm 4 trường này để phân loại loài theo năm nếu có thay đổi
	private String vnlist;// DVHD nguy cap, quy, hiem (nghị định 64-2019)
	private String vnlist06;
	private String cites;// Cấp độ nguy cấp (CITES list là IB, IIB và IIIB)
	private String animalGroup;/*
								 * nhóm động vật:Nhóm 1: I cites, IB và NĐ64. Nhóm 2: II,III cites và IIB Nhóm
								 * 3: Động vật rừng thông thường hoang dã khác
								 */

	//tran huu dat them biến có thể sửa
	private boolean editable;
	//tran huu dat thêm biến có thể sửa
	
	
	private OriginalDto original;
	//Các biến cho export rút gọn, đầy đủ
	private BigInteger periodId;
	private BigInteger farmId;
	private BigInteger originalId;
	private BigInteger provId;
	private BigInteger disId;
	private BigInteger wardId;
	private BigInteger animalId;
	private BigInteger form16Id;
	
	public ReportForm16Dto() {
		super();
	}

	public ReportForm16Dto(ReportForm16 entity) {
		super();
		if (entity != null) {
			this.setId(entity.getId());
			this.type = entity.getType();
			this.dateReport = entity.getDateReport();
			if (entity.getFarm() != null) {
				this.farm = new FarmDto(entity.getFarm(), false);
			}
			if (entity.getAnimal() != null) {
				this.animal = new AnimalDto(entity.getAnimal(), false);
			}
			this.total = entity.getTotal();
			this.male = entity.getMale();
			this.female = entity.getFemale();
			this.unGender = entity.getUnGender();
			
			this.maleParent = entity.getMaleParent();
			this.femaleParent = entity.getFemaleParent();
			this.unGenderParent = entity.getUnGenderParent();
			
			this.maleGilts = entity.getMaleGilts();
			this.femaleGilts = entity.getFemaleGilts();
			this.unGenderGilts = entity.getUnGenderGilts();

			this.maleChildUnder1YearOld = entity.getMaleChildUnder1YearOld();
			this.femaleChildUnder1YearOld = entity.getFemaleChildUnder1YearOld();
			this.childUnder1YearOld = entity.getChildUnder1YearOld();
			
			this.maleChildOver1YearOld = entity.getMaleChildOver1YearOld();
			this.femaleChildOver1YearOld = entity.getFemaleChildOver1YearOld();
			this.unGenderChildOver1YearOld = entity.getUnGenderChildOver1YearOld();
			
			//tran huu dat entity to dto start
			this.totalImport = entity.getTotalImport();
			
			this.importMaleParent= entity.getImportMaleParent();
			this.importFemaleParent= entity.getImportFemaleParent();
			this.importUnGenderParent = entity.getImportUnGenderParent();
			
			this.importMaleGilts= entity.getImportMaleGilts();// ca the nhap vao co so hau bi duc 
			this.importFemaleGilts= entity.getImportFemaleGilts();// ca the nhap vao co so hau bi cai
			this.importUnGenderGilts = entity.getImportUnGenderGilts();
			
			this.importMaleChildUnder1YearOld = entity.getImportMaleChildUnder1YearOld();
			this.importFemaleChildUnder1YearOld = entity.getImportFemaleChildUnder1YearOld();
			this.importChildUnder1YearOld= entity.getImportChildUnder1YearOld();// con dưới 1 tuổi childUnder1YearOld nhap vao co so
			
			this.importMaleChildOver1YearOld= entity.getImportMaleChildOver1YearOld();// con trên 1 tuổi 1 tuổi đực nhap vao co so
			this.importFemaleChildOver1YearOld = entity.getImportFemaleChildOver1YearOld();// con trên 1 tuổi 1 tuổi cái nhap vao co so
			this.importUnGenderChildOver1YearOld= entity.getImportUnGenderChildOver1YearOld();// con trên 1 tuổi 1 tuổi không rõ giới tính nhap vao co so
			
			this.importReason= entity.getImportReason();
			
			// xuất
			this.totalExport = entity.getTotalExport();
			
			this.exportMaleParent= entity.getExportMaleParent();// ca the xuat ra khoi co so bo me duc
			this.exportFemaleParent= entity.getExportFemaleParent();// ca the xuat ra khoi co so bo me duc
			this.exportUnGenderParent = entity.getExportUnGenderParent();
			
			this.exportMaleGilts= entity.getExportMaleGilts();// ca the xuat ra khoi co so hau bi duc 
			this.exportFemaleGilts= entity.getExportFemaleGilts();// ca the xuat ra khoi co so hau bi cai]
			this.exportUnGenderGilts = entity.getExportUnGenderGilts();
			
			this.exportMaleChildUnder1YearOld = entity.getExportMaleChildUnder1YearOld();
			this.exportFemaleChildUnder1YearOld = entity.getExportFemaleChildUnder1YearOld();
			this.exportChildUnder1YearOld= entity.getExportChildUnder1YearOld();// con dưới 1 tuổi childUnder1YearOld xuat ra khoi co so
			
			this.exportMaleChildOver1YearOld= entity.getExportMaleChildOver1YearOld();// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
			this.exportFemaleChildOver1YearOld= entity.getExportFemaleChildOver1YearOld();// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
			this.exportUnGenderChildOver1YearOld= entity.getExportUnGenderChildOver1YearOld();// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
			this.exportReason= entity.getExportReason();
			//tran huu dat entity to dto end
			
			this.maleImport = entity.getMaleImport();
			this.femaleImport = entity.getFemaleImport();
			this.unGenderImport = entity.getUnGenderImport();
			this.maleExport = entity.getMaleExport();
			this.femaleExport = entity.getFemaleExport();
			this.unGenderExport = entity.getUnGenderExport();
			this.note = entity.getNote();
			this.confirmForestProtection = entity.getConfirmForestProtection();
			if (entity.getIsLastRecord() != null) {
				this.isLastRecord = entity.getIsLastRecord();
			} else {
				this.isLastRecord = false;
			}
			if (entity.getAdministrativeUnit() != null) {
				this.administrativeUnitDto = new FmsAdministrativeUnitDto();
				this.administrativeUnitDto.setId(entity.getAdministrativeUnit().getId());
				this.administrativeUnitDto.setCode(entity.getAdministrativeUnit().getCode());
				this.administrativeUnitDto.setName(entity.getAdministrativeUnit().getName());
			}
			if (entity.getDistrict() != null) {
				this.districtDto = new FmsAdministrativeUnitDto();
				this.districtDto.setId(entity.getDistrict().getId());
				this.districtDto.setCode(entity.getDistrict().getCode());
				this.districtDto.setName(entity.getDistrict().getName());
			}
			if (entity.getProvince() != null) {
				this.provinceDto = new FmsAdministrativeUnitDto();
				this.provinceDto.setId(entity.getProvince().getId());
				this.provinceDto.setCode(entity.getProvince().getCode());
				this.provinceDto.setName(entity.getProvince().getName());
			}
			this.vnlist = entity.getVnlist();
			this.vnlist06 =  entity.getVnlist06();
			this.cites = entity.getCites();
			this.animalGroup = entity.getAnimalGroup();
			this.totalExport = entity.getTotalExport();
			this.totalImport = entity.getTotalImport();
			
			if (entity.getOriginal() != null) {
				this.original = new OriginalDto();
				this.original.setId(entity.getOriginal().getId());
				this.original.setName(entity.getOriginal().getName());
				this.original.setCode(entity.getOriginal().getCode());
			}
		}
	}
	
	public Integer getTotalImport() {
		return totalImport;
	}

	public void setTotalImport(Integer totalImport) {
		this.totalImport = totalImport;
	}

	public Integer getTotalExport() {
		return totalExport;
	}

	public void setTotalExport(Integer totalExport) {
		this.totalExport = totalExport;
	}

	public String getConfirmForestProtection() {
		return confirmForestProtection;
	}

	public void setConfirmForestProtection(String confirmForestProtection) {
		this.confirmForestProtection = confirmForestProtection;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getDateReport() {
		return dateReport;
	}

	public void setDateReport(Date dateReport) {
		this.dateReport = dateReport;
	}

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

	public ImportExportAnimalDto getImportExportAnimal() {
		return importExportAnimal;
	}

	public void setImportExportAnimal(ImportExportAnimalDto importExportAnimal) {
		this.importExportAnimal = importExportAnimal;
	}

	public AnimalDto getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}

	public ReportPeriodDto getReportPeriod() {
		return reportPeriod;
	}

	public void setReportPeriod(ReportPeriodDto reportPeriod) {
		this.reportPeriod = reportPeriod;
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

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
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

	public Integer getMaleImport() {
		return maleImport;
	}

	public void setMaleImport(Integer maleImport) {
		this.maleImport = maleImport;
	}

	public Integer getFemaleImport() {
		return femaleImport;
	}

	public void setFemaleImport(Integer femaleImport) {
		this.femaleImport = femaleImport;
	}

	public Integer getUnGenderImport() {
		return unGenderImport;
	}

	public void setUnGenderImport(Integer unGenderImport) {
		this.unGenderImport = unGenderImport;
	}

	public Integer getMaleExport() {
		return maleExport;
	}

	public void setMaleExport(Integer maleExport) {
		this.maleExport = maleExport;
	}

	public Integer getFemaleExport() {
		return femaleExport;
	}

	public void setFemaleExport(Integer femaleExport) {
		this.femaleExport = femaleExport;
	}

	public Integer getUnGenderExport() {
		return unGenderExport;
	}

	public void setUnGenderExport(Integer unGenderExport) {
		this.unGenderExport = unGenderExport;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public FmsAdministrativeUnitDto getAdministrativeUnitDto() {
		return administrativeUnitDto;
	}

	public void setAdministrativeUnitDto(FmsAdministrativeUnitDto administrativeUnitDto) {
		this.administrativeUnitDto = administrativeUnitDto;
	}

	public FmsAdministrativeUnitDto getDistrictDto() {
		return districtDto;
	}

	public void setDistrictDto(FmsAdministrativeUnitDto districtDto) {
		this.districtDto = districtDto;
	}

	public FmsAdministrativeUnitDto getProvinceDto() {
		return provinceDto;
	}

	public void setProvinceDto(FmsAdministrativeUnitDto provinceDto) {
		this.provinceDto = provinceDto;
	}

	public Boolean getIsLastRecord() {
		return isLastRecord;
	}

	public void setIsLastRecord(Boolean isLastRecord) {
		this.isLastRecord = isLastRecord;
	}

	public String getVnlist() {
		return vnlist;
	}

	public void setVnlist(String vnlist) {
		this.vnlist = vnlist;
	}

	public String getVnlist06() {
		return vnlist06;
	}

	public void setVnlist06(String vnlist06) {
		this.vnlist06 = vnlist06;
	}

	public String getCites() {
		return cites;
	}

	public void setCites(String cites) {
		this.cites = cites;
	}

	public String getAnimalGroup() {
		return animalGroup;
	}

	public void setAnimalGroup(String animalGroup) {
		this.animalGroup = animalGroup;
	}
	

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public OriginalDto getOriginal() {
		return original;
	}

	public void setOriginal(OriginalDto original) {
		this.original = original;
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

	public BigInteger getPeriodId() {
		return periodId;
	}

	public void setPeriodId(BigInteger periodId) {
		this.periodId = periodId;
	}

	public BigInteger getFarmId() {
		return farmId;
	}

	public void setFarmId(BigInteger farmId) {
		this.farmId = farmId;
	}

	public BigInteger getOriginalId() {
		return originalId;
	}

	public void setOriginalId(BigInteger originalId) {
		this.originalId = originalId;
	}

	public BigInteger getProvId() {
		return provId;
	}

	public void setProvId(BigInteger provId) {
		this.provId = provId;
	}

	public BigInteger getDisId() {
		return disId;
	}

	public void setDisId(BigInteger disId) {
		this.disId = disId;
	}

	public BigInteger getWardId() {
		return wardId;
	}

	public void setWardId(BigInteger wardId) {
		this.wardId = wardId;
	}

	public BigInteger getAnimalId() {
		return animalId;
	}

	public void setAnimalId(BigInteger animalId) {
		this.animalId = animalId;
	}

	public BigInteger getForm16Id() {
		return form16Id;
	}

	public void setForm16Id(BigInteger form16Id) {
		this.form16Id = form16Id;
	}

}
