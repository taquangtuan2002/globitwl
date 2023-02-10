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
@Table(name = "tbl_import_animal_feed")
@XmlRootElement
/*
 * Nhập thức ăn gia súc (nhập cám)
 */
public class ImportAnimalFeed  extends BaseObject{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="code")
	private String code;

	@Column(name="date_of_manufacture")
	private Date dateOfManufacture;//ngày sản xuất

	@Column(name="expiry_date")
	private Date expiryDate;//Hạn sử dụng

	@Column(name = "production_facilities")
	private String productionFacilities;	// Nhà sản xuất - cơ sở sản xuất

	@Column(name = "symbol_code")
	private String symbolCode;	//Ký hiệu - mã hiệu

	@Column(name = "lot_number_by_manufacturer")
	private String lotNumberByManufacturer;	//Số lô theo nhà sản xuất
	
	@Column(name="date_issue")
	private Date dateIssue;//ngày nhập- ngày xuất
	
	//  1: nhập 
	// -1: xuất
	@Column(name="type")
	private int type;	//Loại phiếu

	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;

	@ManyToOne
	@JoinColumn(name="import_animal_feed_id")
	private ImportAnimalFeed importAnimalFeed; //phiếu nhập

	@ManyToOne
	@JoinColumn(name="unit_id")
	private Unit unit;	//đơn vị tính
	
	@ManyToOne
	@JoinColumn(name="bran_id")
	private Bran bran;//thức ăn gia súc (danh sách cám)
	
	@Column(name="quantity")
	private double quantity;//số lượng

	@Column(name="remain_quantity")
	private double remainQuantity;//Số lượng còn lại
	
	@Column(name="amount")
	private double amount;//khối lượng
	
	@Column(name="price")
	private double price;//giá
	
	@JoinColumn(name="supplier")
	private String supplier;//Người bán
	
	@JoinColumn(name="description")
	private String description;//diễn giải

	@Column(name = "buyer_name")
	private String buyerName;// Khách hàng
	
	@Column(name = "buyer_adress")
	private String buyerAdress;// Địa chỉ khách hang
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Date getDateIssue() {
		return dateIssue;
	}
	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
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
	public Bran getBran() {
		return bran;
	}
	public void setBran(Bran bran) {
		this.bran = bran;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ImportAnimalFeed getImportAnimalFeed() {
		return importAnimalFeed;
	}
	public void setImportAnimalFeed(ImportAnimalFeed importAnimalFeed) {
		this.importAnimalFeed = importAnimalFeed;
	}
	public double getRemainQuantity() {
		return remainQuantity;
	}
	public void setRemainQuantity(double remainQuantity) {
		this.remainQuantity = remainQuantity;
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
	public String getProductionFacilities() {
		return productionFacilities;
	}
	public void setProductionFacilities(String productionFacilities) {
		this.productionFacilities = productionFacilities;
	}
	public String getSymbolCode() {
		return symbolCode;
	}
	public void setSymbolCode(String symbolCode) {
		this.symbolCode = symbolCode;
	}
	public String getLotNumberByManufacturer() {
		return lotNumberByManufacturer;
	}
	public void setLotNumberByManufacturer(String lotNumberByManufacturer) {
		this.lotNumberByManufacturer = lotNumberByManufacturer;
	}

}
