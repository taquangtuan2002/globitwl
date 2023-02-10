package com.globits.wl.dto;

import java.util.Date;

import com.globits.wl.domain.ExportEgg;

public class ExportEggDto {
	private Long id;

	private String code;// mã phiếu
	private String msg;
	private Date dateExport;//ngày xuất
	private Date dateImport;
	private Double quantity;//số lượng xuất (quả)
	private String buyerName;//Khách hàng
	private String buyerAdress;//Địa chỉ khách hang
	private FarmDto farm;
	private ExportEggDto importEgg;
	private ExportEggDto exportEgg;
	private int eggType;
	private Double remainQuantity;
	private String batchCode;
	private ImportExportAnimalDto importExportAnimal;
	private Integer reasonForExport;	//Lý do xuất:  (1) bán - (2) loại thải - (3) tiêu thụ nội bộ
	
	public Integer getReasonForExport() {
		return reasonForExport;
	}

	public void setReasonForExport(Integer reasonForExport) {
		this.reasonForExport = reasonForExport;
	}

	public ImportExportAnimalDto getImportExportAnimal() {
		return importExportAnimal;
	}

	public void setImportExportAnimal(ImportExportAnimalDto importExportAnimal) {
		this.importExportAnimal = importExportAnimal;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public Double getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(Double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public int getEggType() {
		return eggType;
	}

	public void setEggType(int eggType) {
		this.eggType = eggType;
	}

	public ExportEggDto getImportEgg() {
		return importEgg;
	}

	public void setImportEgg(ExportEggDto importEgg) {
		this.importEgg = importEgg;
	}

	public ExportEggDto getExportEgg() {
		return exportEgg;
	}

	public void setExportEgg(ExportEggDto exportEgg) {
		this.exportEgg = exportEgg;
	}
	private Integer type;

	public Date getDateImport() {
		return dateImport;
	}

	public void setDateImport(Date dateImport) {
		this.dateImport = dateImport;
	}

	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
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


	public Date getDateExport() {
		return dateExport;
	}


	public void setDateExport(Date dateExport) {
		this.dateExport = dateExport;
	}


	public Double getQuantity() {
		return quantity;
	}


	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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

	public ExportEggDto() {
		super();
		}
	public ExportEggDto(ExportEgg entity) {
		super();
		if (entity != null) {
			this.id = entity.getId();
			if (entity.getDateExport() != null) {
				this.dateExport = entity.getDateExport();
			}
			if(entity.getExportEgg() != null) {
				this.exportEgg = new ExportEggDto();
				this.exportEgg.setId(entity.getExportEgg().getId());
			}
			if(entity.getImportEgg() != null) {
				this.importEgg = new ExportEggDto();
				this.importEgg.setId(entity.getImportEgg().getId());
				this.importEgg.setQuantity(entity.getImportEgg().getQuantity());
				this.importEgg.setRemainQuantity(entity.getImportEgg().getRemainQuantity());
				this.importEgg.setBatchCode(entity.getImportEgg().getBatchCode());
				if(entity.getImportEgg().getDateExport() != null) {
					this.importEgg.setDateExport(entity.getImportEgg().getDateExport());
				}
			}
			this.remainQuantity = entity.getRemainQuantity();
			this.eggType = entity.getEggType();
			this.code=entity.getCode();
			this.quantity = entity.getQuantity();
			this.buyerName = entity.getBuyerName();
			this.buyerAdress=entity.getBuyerAdress();
			this.batchCode = entity.getBatchCode();
			this.reasonForExport = entity.getReasonForExport();
			if(entity.getFarm()!=null){
				farm = new FarmDto();
				farm.setId(entity.getFarm().getId());
				farm.setName(entity.getFarm().getName());
				farm.setCode(entity.getFarm().getCode());
			}
			this.type = entity.getType();
			this.importExportAnimal = new ImportExportAnimalDto(entity.getImportExportAnimal());
		}
	}

}
