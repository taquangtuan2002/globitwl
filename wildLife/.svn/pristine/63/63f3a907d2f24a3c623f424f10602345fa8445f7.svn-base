package com.globits.wl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_export_animal")
@XmlRootElement
/*
 * Loại vật nuôi
 * VD: Gà, vịt, ngan, ngỗng,...
 */
public class ExportAnimal extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="voucher_code")
	private String voucherCode;//Số chứng từ	
	
	@ManyToOne
	@JoinColumn(name = "farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name = "animal_id")
	private Animal animal;
	
	@ManyToOne
	@JoinColumn(name="original_id")
	private Original original;//Nguồn gốc
	
	@ManyToOne
	@JoinColumn(name="product_target_id")
	private ProductTarget productTarget;//Hướng sản phẩm
	
	@Column(name="batch_code")
	private String batchCode;//Số lô
	
	@Column(name="date_import")
	private Date dateImport;//Ngày nhập
	
	@Column(name="quantity")
	private double quantity;//Số lượng: con
	
	@Column(name="amount")
	private double amount;//Khối lượng: kg,tấn, tạ,...
	
	@Column(name="description")
	private String description;//Diễn tả
	
	@Column(name="date_export")
	private Date dateExport;//Ngày xuất
	
	@Column(name="type")
	private int type;//Loại phiếu xuất
	
	@Column(name="export_reason")
	private String exportReason;//Lý do xuất
	
	@Column(name="buyer_name")
	private String buyerName;//Tên người mua
	
	@Column(name="buyer_adress")
	private String buyerAdress;//Địa chỉ người mua
	
	@Column(name="administrative_id")
	private FmsAdministrativeUnit province;

	public FmsAdministrativeUnit getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnit province) {
		this.province = province;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
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

	public Original getOriginal() {
		return original;
	}

	public void setOriginal(Original original) {
		this.original = original;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateExport() {
		return dateExport;
	}

	public void setDateExport(Date dateExport) {
		this.dateExport = dateExport;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
	
	
}
