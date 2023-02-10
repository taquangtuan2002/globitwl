package com.globits.wl.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_import_animal_feeddrug")
@XmlRootElement
/*
 * Nhập thuốc/vacxin
 */
public class ImportDrug  extends BaseObject{	
	@Column(name="code")
	private String code;
	@Column(name="date_import")
	private Date dateImport;//Ngày nhập

	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name="unit_id")
	private Unit unit;//đơn vị tính (g,kg,liều)
	
	@ManyToOne
	@JoinColumn(name="drug_id")
	private Drug drug;//tên thuốc
	
	@Column(name="quantity")
	private double quantity;//số lượng
	
	@Column(name="remain_quantity")
	private double remainQuantity;//số lượng còn lại

	@Column(name="price")
	private double price;//giá
	
	@Column(name="supplier")
	private String supplier;//Nhà cung cấp
	
	@Column(name="preservation")
	private String preservation;//cách bảo quản
	
	@Column(name="type")
	private Integer type;
	
	@ManyToOne
	@JoinColumn(name="import_drug_id")
	private ImportDrug importDrug;// phiếu nhập thuốc
	
	@Column(name="date_of_manufacture")
	private Date dateOfManufacture;// Ngày sản xuất
	
	@Column(name="expiry_date")
	private Date expiryDate;// Hạn sử dụng;
	
	@Column(name="producer")
	private String producer;// Nhà sản xuất;
	
	@Column(name="symbol_code")
	private String symbolCode;// Ký hiệu-mã hiệu;
	
	@Column(name="tradenames")
	private String tradenames;// Tên thương mại;
	
	@Column(name="batch_code_manufacture")
	private String batchCodeManufacture;// Số lô theo nhà sản xuất;
	
	@ManyToOne
	@JoinColumn(name="import_export_animal_id")
	private ImportExportAnimal importExportAnimal;// Mã lô tiêm thuốc
	
	public ImportExportAnimal getImportExportAnimal() {
		return importExportAnimal;
	}
	public void setImportExportAnimal(ImportExportAnimal importExportAnimal) {
		this.importExportAnimal = importExportAnimal;
	}
	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}
	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getSymbolCode() {
		return symbolCode;
	}
	public void setSymbolCode(String symbolCode) {
		this.symbolCode = symbolCode;
	}
	public String getTradenames() {
		return tradenames;
	}
	public void setTradenames(String tradenames) {
		this.tradenames = tradenames;
	}
	public String getBatchCodeManufacture() {
		return batchCodeManufacture;
	}
	public void setBatchCodeManufacture(String batchCodeManufacture) {
		this.batchCodeManufacture = batchCodeManufacture;
	}
	public double getRemainQuantity() {
		return remainQuantity;
	}
	public void setRemainQuantity(double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}
	public ImportDrug getImportDrug() {
		return importDrug;
	}
	public void setImportDrug(ImportDrug importDrug) {
		this.importDrug = importDrug;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDateImport() {
		return dateImport;
	}
	public void setDateImport(Date dateImport) {
		this.dateImport = dateImport;
	}
	
	public Farm getFarm() {
		return farm;
	}
	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Drug getDrug() {
		return drug;
	}
	public void setDrug(Drug drug) {
		this.drug = drug;
	}
	public String getPreservation() {
		return preservation;
	}
	public void setPreservation(String preservation) {
		this.preservation = preservation;
	}
	

}
