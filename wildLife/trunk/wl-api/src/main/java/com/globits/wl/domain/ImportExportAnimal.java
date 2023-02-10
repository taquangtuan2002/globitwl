package com.globits.wl.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_import_export_animal")
@XmlRootElement
/*
 * Nhập vật nuôi
 */
public class ImportExportAnimal  extends BaseObject{
	
	/*
	 * Kiểu phiếu: 1 = tăng đàn;-1=giảm đàn
	 */
	@Column(name="type")
	private int type;
	
	@ManyToOne
	@JoinColumn(name = "farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name = "animal_id")
	private Animal animal;
	
	@ManyToOne
	@JoinColumn(name="original_id")
	private Original original;//Nguồn gốc , Bỏ đi không dùng// bỏ comment code: ngày 8/7/2020 by khoadv42
	
	@ManyToOne
	@JoinColumn(name="product_target_id")
	private ProductTarget productTarget;//Hướng sản phẩm
	
	@ManyToOne
	@JoinColumn(name="seed_level_id")
	private SeedLevel seedLevel;	//Cấp giống
	
	@Column(name="batch_code")
	private String batchCode;//Số lô
	
	@Column(name="date_import")
	private Date dateImport;//Ngày nhập
	
	@Column(name="date_export")
	private Date dateExport;//Ngày xuất
	
	@Column(name="date_issue")
	private Date dateIssue;//Ngày nhập hoặc xuất (gộp chung để tính tồn kho)
	
	@Column(name="quantity")
	private double quantity;//Số lượng: con
	
	@Column(name="amount")
	private double amount;//Khối lượng: kg,tấn, tạ,...
	
	@Column(name="day_old")
	private int dayOld;//Ngày tuổi
	
	@Column(name="life_cycle")
	private double lifeCycle;//Số ngày dự kiến nuôi
	
	@Column(name="description")
	private String description;//Diễn tả
	
	@Column(name="voucher_code")
	private String voucherCode;//Số chứng từ
	
	@OneToMany(mappedBy = "importAnimal", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<InjectionPlant> injectionPlants = new HashSet<InjectionPlant>();
	
	@Column(name="supplier_name")
	private String supplierName;//Tên nhà cung cấp
	
	@Column(name="supplier_adress")
	private String supplierAdress;//Địa chỉ nhà cung cấp
	
	@Column(name="export_type")
	private int exportType;//Loại phiếu xuất
	
	@Column(name="export_reason")
	private String exportReason;//Lý do xuất
	
	@Column(name="buyer_name")
	private String buyerName;//Tên người mua
	
	@Column(name="buyer_adress")
	private String buyerAdress;//Địa chỉ người mua
	
	@ManyToOne
	@JoinColumn(name="fms_administrative_unit_id")
	private FmsAdministrativeUnit province;
	
	@ManyToOne
	@JoinColumn(name="import_export_animal_id")
	private ImportExportAnimal importAnimal;//Phiếu nhập Id
	
	@Column(name="remain_quantity")
	private double remainQuantity;//Số lượng còn lại: con
	@Column(name="is_exChange")
	private Boolean isExChange;//đánh dấu chuyển loại
	
	@ManyToOne
	@JoinColumn(name="export_animal_id")
	private ImportExportAnimal exportAnimal;//Phiếu xuất Id
	
	@Column(name="quantity_egg")
	private Double quantityEgg;// Số lượng trứng thu đối với lô có hướng trứng
	
	@Column(name = "gender")// giới tính con vật 1-đực, 2-cái, 3 không rõ
	private Integer gender;
	
	@Column(name = "chip_code")
	private String chipCode;// Mã số chip
	
	@Column(name="is_born_at_farm")
	private Boolean isBornAtFarm;// Là true -> sinh ra tại trại -> false -> nguyên nhân khác
	
	@OneToMany(mappedBy = "importAnimal", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<IndividualAnimal> individualAnimals;
	
	@Column(name="male")
	private Integer male;// tổng con đực
	@Column(name="female")
	private Integer female;// tổng con cái
	@Column(name="un_gender")
	private Integer unGender;// số con không xác định giới tính
	@Column(name="animal_report_data_type")
	private Integer animalReportDataType;// Loại con dưới 1 tuổi, trên 1 tuổi, con hậu bị, con bố mẹ
	
	@Column(name="remain_male")
	private Integer remainMale;// Số lượng con đực còn lại
	@Column(name="remain_female")
	private Integer remainFemale;// Số lượng con cái còn lại
	@Column(name="remain_un_gender")
	private Integer remainUnGender;// Số lượng không xác định còn lại
	
	@ManyToOne
	@JoinColumn(name="forest_products_list_id")
	private ForestProductsList forestProductsList;//Thuộc bản kê lâm sản nào
	public Set<IndividualAnimal> getIndividualAnimals() {
		return individualAnimals;
	}

	public void setIndividualAnimals(Set<IndividualAnimal> individualAnimals) {
		this.individualAnimals = individualAnimals;
	}

	public String getChipCode() {
		return chipCode;
	}

	public void setChipCode(String chipCode) {
		this.chipCode = chipCode;
	}

	public Integer getRemainMale() {
		return remainMale;
	}

	public void setRemainMale(Integer remainMale) {
		this.remainMale = remainMale;
	}

	public Integer getRemainFemale() {
		return remainFemale;
	}

	public void setRemainFemale(Integer remainFemale) {
		this.remainFemale = remainFemale;
	}

	public Integer getRemainUnGender() {
		return remainUnGender;
	}

	public void setRemainUnGender(Integer remainUnGender) {
		this.remainUnGender = remainUnGender;
	}

	public Boolean getIsBornAtFarm() {
		return isBornAtFarm;
	}

	public void setIsBornAtFarm(Boolean isBornAtFarm) {
		this.isBornAtFarm = isBornAtFarm;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Double getQuantityEgg() {
		return quantityEgg;
	}

	public void setQuantityEgg(Double quantityEgg) {
		this.quantityEgg = quantityEgg;
	}

	public FmsAdministrativeUnit getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnit province) {
		this.province = province;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

//	public Original getOriginal() {
//		return original;
//	}
//
//	public void setOriginal(Original original) {
//		this.original = original;
//	}

	public ProductTarget getProductTarget() {
		return productTarget;
	}

	public void setProductTarget(ProductTarget productTarget) {
		this.productTarget = productTarget;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public Date getDateImport() {
		return dateImport;
	}

	public void setDateImport(Date dateImport) {
		this.dateImport = dateImport;
	}

	public Date getDateExport() {
		return dateExport;
	}

	public void setDateExport(Date dateExport) {
		this.dateExport = dateExport;
	}

	public Date getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getDayOld() {
		return dayOld;
	}

	public void setDayOld(int dayOld) {
		this.dayOld = dayOld;
	}

	public double getLifeCycle() {
		return lifeCycle;
	}

	public void setLifeCycle(double lifeCycle) {
		this.lifeCycle = lifeCycle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Original getOriginal() {
		return original;
	}

	public void setOriginal(Original original) {
		this.original = original;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Set<InjectionPlant> getInjectionPlants() {
		return injectionPlants;
	}

	public void setInjectionPlants(Set<InjectionPlant> injectionPlants) {
		this.injectionPlants = injectionPlants;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierAdress() {
		return supplierAdress;
	}

	public void setSupplierAdress(String supplierAdress) {
		this.supplierAdress = supplierAdress;
	}
	
	public int getExportType() {
		return exportType;
	}

	public void setExportType(int exportType) {
		this.exportType = exportType;
	}

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerAdress() {
		return buyerAdress;
	}

	public void setBuyerAdress(String buyerAdress) {
		this.buyerAdress = buyerAdress;
	}	

	public ImportExportAnimal getImportAnimal() {
		return importAnimal;
	}

	public void setImportAnimal(ImportExportAnimal importAnimal) {
		this.importAnimal = importAnimal;
	}

	public double getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public ImportExportAnimal() {
		this.setUuidKey(UUID.randomUUID());
	}

	public SeedLevel getSeedLevel() {
		return seedLevel;
	}

	public void setSeedLevel(SeedLevel seedLevel) {
		this.seedLevel = seedLevel;
	}	

	public Boolean getIsExChange() {
		return isExChange;
	}

	public void setIsExChange(Boolean isExChange) {
		this.isExChange = isExChange;
	}

	public ImportExportAnimal getExportAnimal() {
		return exportAnimal;
	}

	public void setExportAnimal(ImportExportAnimal exportAnimal) {
		this.exportAnimal = exportAnimal;
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

	public Integer getAnimalReportDataType() {
		return animalReportDataType;
	}

	public void setAnimalReportDataType(Integer animalReportDataType) {
		this.animalReportDataType = animalReportDataType;
	}

	public ForestProductsList getForestProductsList() {
		return forestProductsList;
	}

	public void setForestProductsList(ForestProductsList forestProductsList) {
		this.forestProductsList = forestProductsList;
	}
	
}
