package com.globits.wl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_report_form16")
@XmlRootElement
public class ReportForm16 extends BaseObject{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="report_period_id")
	private ReportPeriod reportPeriod;

	@Column(name="date_report")
	private Date dateReport;
	
	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name="import_export_animal_id")
	private ImportExportAnimal importExportAnimal;
	
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;
	
	@Column(name="total")
	private Integer total;
	
	@Column(name="male")
	private Integer male;
	
	@Column(name="female")
	private Integer female;
	
	@Column(name="un_gender")
	private Integer unGender;
	
	@Column(name="male_parent")
	private Integer maleParent;// cá thể bố mẹ đực
	@Column(name="female_parent")
	private Integer femaleParent;// cá thể bố mẹ cái
	@Column(name="unGender_parent")
	private Integer unGenderParent;// cá thể bố mẹ không rõ
	
	@Column(name="male_gilts")
	private Integer maleGilts;// hậu bị cái
	@Column(name="female_gilts")
	private Integer femaleGilts;// hậu bị cái
	@Column(name="unGender_gilts")
	private Integer unGenderGilts;// hậu bị không rõ
	
	@Column(name="male_child_under_1year_old")
	private Integer maleChildUnder1YearOld;// con dưới 1 tuổi đực
	@Column(name="female_child_under_1year_old")
	private Integer femaleChildUnder1YearOld;// con dưới 1 tuổi cái
	@Column(name="child_under_1year_old")
	private Integer childUnder1YearOld;// con dưới 1 tuổi không rõ giới tính
	
	@Column(name="male_child_over_1year_old")
	private Integer maleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực
	@Column(name="female_child_over_1year_old")
	private Integer femaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái
	@Column(name="un_gender_child_over_1year_old")
	private Integer unGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính
	
	// nhập
	@Column(name="total_import")
	private Integer totalImport; // tổng nhập (dàng cho khai báo ở mẫu 16)
	
	@Column(name="import_male_parent")
	private Integer importMaleParent;// bố mẹ đực   nhập  
	@Column(name="import_female_parent")
	private Integer importFemaleParent;// bố mẹ cái   nhập 
	@Column(name="import_unGender_parent")
	private Integer importUnGenderParent;// bố mẹ không rõ   nhập 
	
	@Column(name="import_male_gilts")
	private Integer importMaleGilts;// hậu bị đực   nhập  
	@Column(name="import_female_gilts")
	private Integer importFemaleGilts;// hậu bị cái   nhập  
	@Column(name="import_unGender_gilts")
	private Integer importUnGenderGilts;// hậu bị không rõ   nhập  
	
	@Column(name="import_male_child_under_1year_old")
	private Integer importMaleChildUnder1YearOld;// con dưới 1 tuổi đực nhập
	@Column(name="import_female_child_under_1year_old")
	private Integer importFemaleChildUnder1YearOld;// con dưới 1 tuổi cái nhập
	@Column(name="import_child_under_1_year_old")
	private Integer importChildUnder1YearOld;// con dưới 1 tuổi không rõ giới tính nhập
	
	@Column(name="import_male_child_over_1_year_old")
	private Integer importMaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực nhap vao co so
	@Column(name="import_female_child_over_year_old")
	private Integer importFemaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái nhap vao co so
	@Column(name="import_ungender_child_over_1_year_old")
	private Integer importUnGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính nhap vao co so
	
	@Column(name="import_reason")
	private String importReason;//lí do nhập
	
	// Xuất
	@Column(name="total_export")
	private Integer totalExport; // tổng xuất (dàng cho khai báo ở mẫu 16)
	
	@Column(name="export_male_parent")
	private Integer exportMaleParent;// bố mẹ đực   xuất 
	@Column(name="export_female_parent")
	private Integer exportFemaleParent;// bố mẹ cái   xuất 
	@Column(name="export_unGender_parent")
	private Integer exportUnGenderParent;// bố mẹ không rõ   xuất 
	
	@Column(name="export_male_gilts")
	private Integer exportMaleGilts;// hậu bị đực   xuất  
	@Column(name="export_female_gilts")
	private Integer exportFemaleGilts;// hậu bị cái   xuất  
	@Column(name="export_unGender_gilts")
	private Integer exportUnGenderGilts;// hậu bị không rõ   xuất  
	
	@Column(name="export_male_child_under_1year_old")
	private Integer exportMaleChildUnder1YearOld;// con dưới 1 tuổi đực xuất
	@Column(name="export_female_child_under_1year_old")
	private Integer exportFemaleChildUnder1YearOld;// con dưới 1 tuổi cái xuất
	@Column(name="export_child_under_1_year_old")
	private Integer exportChildUnder1YearOld;// con dưới 1 tuổi không rõ xuất
		
	@Column(name="export_male_child_over_1_year_old")
	private Integer exportMaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
	@Column(name="export_female_chid_over_1_year_old")
	private Integer exportFemaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
	@Column(name="export_ungender_child_over_1_year_old")
	private Integer exportUnGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
	
	@Column(name="export_reason")
	private String exportReason;//lí do xuất
	//tran huu dat them truong xuat ra khoi co so end
	
	@Column(name="male_import")
	private Integer maleImport;// số nhập cơ sở đực
	
	@Column(name="female_import")
	private Integer femaleImport;// số nhập cơ sở cái
	
	@Column(name="un_gender_import")
	private Integer unGenderImport;// số nhập cơ sở không xác định giới tính
	
	@Column(name="male_export")
	private Integer maleExport;// số xuất cơ sở đực
	
	@Column(name="female_export")
	private Integer femaleExport;// số xuất cơ sở cái
	
	@Column(name="un_gender_export")
	private Integer unGenderExport;// số xuất cơ sở không xác định giới tính
	
	@Column(name = "note")
	private String note;// ghi chú
	@Column(name = "type")
	private Integer type;// Kiểu form A: 1; B: 2
	@Column(name="confirm_forest_protection")
	private String confirmForestProtection;
	
	@ManyToOne
	@JoinColumn(name="administrative_unit_id")
	private FmsAdministrativeUnit administrativeUnit;
	
	@ManyToOne
	@JoinColumn(name="district_id")
	private FmsAdministrativeUnit district;
	
	@ManyToOne
	@JoinColumn(name="province_id")
	private FmsAdministrativeUnit province;
	
	@Column(name="is_last_record")
	private Boolean isLastRecord;//là bản ghi cuối cùng
	
	//Thêm 4 trường này để phân loại loài theo năm nếu có thay đổi
	@Column(name = "vnlist")
	private String vnlist;// DVHD nguy cap, quy, hiem (nghị định 64-2019)

	@Column(name = "vnlist06")
	private String vnlist06;
	
	@Column(name = "cites") // Cấp độ nguy cấp (CITES list là IB, IIB và IIIB)
	private String cites;
	
	@Column(name="animal_group")
	private String animalGroup;/*nhóm động vật:Nhóm 1: I cites, IB và NĐ64.
											   Nhóm 2: II,III cites và IIB
											   Nhóm 3: Động vật rừng thông thường hoang dã khác*/
	
	@ManyToOne
	@JoinColumn(name="original_id")
	private Original original;

	public ReportPeriod getReportPeriod() {
		return reportPeriod;
	}

	public void setReportPeriod(ReportPeriod reportPeriod) {
		this.reportPeriod = reportPeriod;
	}

	public Date getDateReport() {
		return dateReport;
	}

	public void setDateReport(Date dateReport) {
		this.dateReport = dateReport;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public ImportExportAnimal getImportExportAnimal() {
		return importExportAnimal;
	}

	public void setImportExportAnimal(ImportExportAnimal importExportAnimal) {
		this.importExportAnimal = importExportAnimal;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
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

	public Integer getUnGenderParent() {
		return unGenderParent;
	}

	public void setUnGenderParent(Integer unGenderParent) {
		this.unGenderParent = unGenderParent;
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

	public Integer getTotalImport() {
		return totalImport;
	}

	public void setTotalImport(Integer totalImport) {
		this.totalImport = totalImport;
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

	public Integer getImportUnGenderParent() {
		return importUnGenderParent;
	}

	public void setImportUnGenderParent(Integer importUnGenderParent) {
		this.importUnGenderParent = importUnGenderParent;
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

	public Integer getTotalExport() {
		return totalExport;
	}

	public void setTotalExport(Integer totalExport) {
		this.totalExport = totalExport;
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

	public Integer getExportUnGenderParent() {
		return exportUnGenderParent;
	}

	public void setExportUnGenderParent(Integer exportUnGenderParent) {
		this.exportUnGenderParent = exportUnGenderParent;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getConfirmForestProtection() {
		return confirmForestProtection;
	}

	public void setConfirmForestProtection(String confirmForestProtection) {
		this.confirmForestProtection = confirmForestProtection;
	}

	public FmsAdministrativeUnit getAdministrativeUnit() {
		return administrativeUnit;
	}

	public void setAdministrativeUnit(FmsAdministrativeUnit administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}

	public FmsAdministrativeUnit getDistrict() {
		return district;
	}

	public void setDistrict(FmsAdministrativeUnit district) {
		this.district = district;
	}

	public FmsAdministrativeUnit getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnit province) {
		this.province = province;
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

	public Original getOriginal() {
		return original;
	}

	public void setOriginal(Original original) {
		this.original = original;
	}
	
}
