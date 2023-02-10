package com.globits.wl.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.globits.wl.domain.Bran;
import com.globits.wl.domain.ExportAnimal;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.ImportAnimalFeed;
import com.globits.wl.domain.Unit;
import com.globits.wl.utils.NumberUtils;

public class ImportAnimalFeedDto {

	private Long id;
	private String code;
	private Date dateIssue;//ngày nhập- ngày xuất
	// =0 thức ăn
	// ==1 nguyên liệu thức ăn
	private int type; // Loại phiếu
	private FarmDto farm;
	private BranDto bran;// thức ăn gia súc (danh sách cám)
	private double quantity;// số lượng
	private double remainQuantity;//Số lượng còn lại
	private double amount;// khối lượng
	private double price;// giá
	private String supplier;// Người bán
	private String description;// diễn giải

	private ImportAnimalFeedDto importAnimalFeed; // phiếu nhập
	private UnitDto unit; // đơn vị tính
	private String buyerName;// Khách hàng
	private String buyerAdress;// Địa chỉ khách hang
	private Date dateOfManufacture;//ngày sản xuất
	private Date expiryDate;//Hạn sử dụng
	private String productionFacilities;	// Nhà sản xuất - cơ sở sản xuất
	private String symbolCode;	//Ký hiệu - mã hiệu
	private String lotNumberByManufacturer;	//Số lô theo nhà sản xuất

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

	public double getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
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

	public BranDto getBran() {
		return bran;
	}

	public void setBran(BranDto bran) {
		this.bran = bran;
	}

	public double getQuantity() {
		//return quantity;
		return NumberUtils.round(quantity,2);
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		//return amount;
		return NumberUtils.round(amount,2);
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

	public ImportAnimalFeedDto getImportAnimalFeed() {
		return importAnimalFeed;
	}

	public void setImportAnimalFeed(ImportAnimalFeedDto importAnimalFeed) {
		this.importAnimalFeed = importAnimalFeed;
	}

	public UnitDto getUnit() {
		return unit;
	}

	public void setUnit(UnitDto unit) {
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

	public ImportAnimalFeedDto() {
		super();
	}

	public ImportAnimalFeedDto(ImportAnimalFeed importAnimalFeed) {
		this.id = importAnimalFeed.getId();
		this.code = importAnimalFeed.getCode();
		this.dateIssue = importAnimalFeed.getDateIssue();
		this.amount = importAnimalFeed.getAmount();
		if (importAnimalFeed.getBran() != null)
			this.bran = new BranDto(importAnimalFeed.getBran());
		this.description = importAnimalFeed.getDescription();
		this.price = importAnimalFeed.getPrice();
		this.quantity = importAnimalFeed.getQuantity();
		this.supplier = importAnimalFeed.getSupplier();
		this.buyerAdress = importAnimalFeed.getBuyerAdress();
		this.buyerName = importAnimalFeed.getBuyerName();
		this.type = importAnimalFeed.getType();
		this.remainQuantity = importAnimalFeed.getRemainQuantity();
		this.expiryDate = importAnimalFeed.getExpiryDate();
		this.productionFacilities = importAnimalFeed.getProductionFacilities();
		this.symbolCode = importAnimalFeed.getSymbolCode();
		this.lotNumberByManufacturer = importAnimalFeed.getLotNumberByManufacturer();
		this.dateOfManufacture = importAnimalFeed.getDateOfManufacture();
		
		if (importAnimalFeed.getUnit() != null) {
			this.unit = new UnitDto(importAnimalFeed.getUnit());
		}
		if (importAnimalFeed.getImportAnimalFeed() != null) {
			this.importAnimalFeed = new ImportAnimalFeedDto(importAnimalFeed.getImportAnimalFeed(), true);
		}

		if (importAnimalFeed.getFarm() != null) {
			this.farm = new FarmDto();
			this.farm.setId(importAnimalFeed.getFarm().getId());
			this.farm.setCode(importAnimalFeed.getFarm().getCode());
			this.farm.setName(importAnimalFeed.getFarm().getName());
			this.farm.setDescription(importAnimalFeed.getFarm().getDescription());
			this.farm.setOwnerName(importAnimalFeed.getFarm().getOwnerName());
			this.farm.setAdressDetail(importAnimalFeed.getFarm().getAdressDetail());
		}
	}

	public ImportAnimalFeedDto(ImportAnimalFeed importAnimalFeed, boolean simple) {
		if (importAnimalFeed != null) {
			this.id = importAnimalFeed.getId();
			this.code = importAnimalFeed.getCode();
			this.dateIssue = importAnimalFeed.getDateIssue();
			this.amount = importAnimalFeed.getAmount();
			if (importAnimalFeed.getBran() != null)
				this.bran = new BranDto(importAnimalFeed.getBran());
			this.description = importAnimalFeed.getDescription();
			this.price = importAnimalFeed.getPrice();
			this.quantity = importAnimalFeed.getQuantity();
			this.supplier = importAnimalFeed.getSupplier();
			this.buyerAdress = importAnimalFeed.getBuyerAdress();
			this.buyerName = importAnimalFeed.getBuyerName();
			this.type = importAnimalFeed.getType();
			this.remainQuantity = importAnimalFeed.getRemainQuantity();
			this.expiryDate = importAnimalFeed.getExpiryDate();
			this.productionFacilities = importAnimalFeed.getProductionFacilities();
			this.symbolCode = importAnimalFeed.getSymbolCode();
			this.lotNumberByManufacturer = importAnimalFeed.getLotNumberByManufacturer();
			this.dateOfManufacture = importAnimalFeed.getDateOfManufacture();
			
			if (importAnimalFeed.getUnit() != null) {
				this.unit = new UnitDto(importAnimalFeed.getUnit());
			}

			if (importAnimalFeed.getFarm() != null) {
				this.farm = new FarmDto();
				this.farm.setId(importAnimalFeed.getFarm().getId());
				this.farm.setCode(importAnimalFeed.getFarm().getCode());
				this.farm.setName(importAnimalFeed.getFarm().getName());
				this.farm.setDescription(importAnimalFeed.getFarm().getDescription());
				this.farm.setOwnerName(importAnimalFeed.getFarm().getOwnerName());
				this.farm.setAdressDetail(importAnimalFeed.getFarm().getAdressDetail());
			}
		}
	}

}
