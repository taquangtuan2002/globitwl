package com.globits.wl.dto;

import java.util.Date;

import org.joda.time.LocalDateTime;

import com.globits.wl.domain.ExportAnimal;
import com.globits.wl.domain.FmsAdministrativeUnit;

public class ExportAnimalDto {

	private Long id;
	private String voucherCode;
	private String batchCode;
	private Date dateImport;
	private double quantity;
	private double amount;
	private String description;
	private Date dateExport;
	private int type;
	private String exportReason;
	private String buyerName;
	private String buyerAdress;
	private String created_by;
	private LocalDateTime create_date;
	
	private FmsAdministrativeUnitDto province;
	public FmsAdministrativeUnitDto getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnitDto province) {
		this.province = province;
	}

	private FarmDto farm;
	private AnimalDto animal;
	private OriginalDto original;//Nguồn gố	
	private ProductTargetDto productTarget;//Hướng sản phẩm
	
	
	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Long getId() {
		return id;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public void setId(Long id) {
		this.id = id;
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

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

	public AnimalDto getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}

	public OriginalDto getOriginal() {
		return original;
	}

	public void setOriginal(OriginalDto original) {
		this.original = original;
	}

	public ProductTargetDto getProductTarget() {
		return productTarget;
	}

	public void setProductTarget(ProductTargetDto productTarget) {
		this.productTarget = productTarget;
	}

	public ExportAnimalDto() {
		super();
	}

	public ExportAnimalDto(ExportAnimal exportAnimal) {

		this.id = exportAnimal.getId();
		this.voucherCode = exportAnimal.getVoucherCode();
		this.batchCode = exportAnimal.getBatchCode();
		this.dateImport = exportAnimal.getDateImport();
		this.quantity = exportAnimal.getQuantity();
		this.amount = exportAnimal.getAmount();
		this.description = exportAnimal.getDescription();
		this.dateExport = exportAnimal.getDateExport();
		this.type = exportAnimal.getType();
		this.exportReason = exportAnimal.getExportReason();
		this.buyerName = exportAnimal.getBuyerName();
		this.buyerAdress = exportAnimal.getBuyerAdress();
		this.created_by = exportAnimal.getCreatedBy();
		this.create_date= exportAnimal.getCreateDate();

		if (exportAnimal.getAnimal() != null) {
			this.animal = new AnimalDto();
			this.animal.setId(exportAnimal.getAnimal().getId());
			this.animal.setCode(exportAnimal.getAnimal().getCode());
			this.animal.setName(exportAnimal.getAnimal().getName());
			this.animal.setDescription(exportAnimal.getAnimal().getDescription());
			if(exportAnimal.getAnimal().getParent()!=null) {
				this.animal.setParent(new AnimalDto());
				this.animal.getParent().setId(exportAnimal.getAnimal().getParent().getId());
				this.animal.getParent().setName(exportAnimal.getAnimal().getParent().getName());
				this.animal.getParent().setCode(exportAnimal.getAnimal().getParent().getCode());
			}
		}
		if (exportAnimal.getFarm() != null) {
			this.farm = new FarmDto();
			this.farm.setId(exportAnimal.getFarm().getId());
			this.farm.setCode(exportAnimal.getFarm().getCode());
			this.farm.setName(exportAnimal.getFarm().getName());
			this.farm.setDescription(exportAnimal.getFarm().getDescription());
			this.farm.setOwnerName(exportAnimal.getFarm().getOwnerName());
			this.farm.setAdressDetail(exportAnimal.getFarm().getAdressDetail());
		}
		if(exportAnimal.getProductTarget()!=null) {
			this.productTarget = new ProductTargetDto(exportAnimal.getProductTarget());
		}
		if(exportAnimal.getOriginal()!=null) {
			this.original = new OriginalDto(exportAnimal.getOriginal());
		}
		if(exportAnimal.getProvince() != null) {
			this.province = new FmsAdministrativeUnitDto(exportAnimal.getProvince());
		}
	}

}
