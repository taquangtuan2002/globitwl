package com.globits.wl.dto;

import java.util.Date;
import java.util.List;

import com.globits.wl.domain.ImportDrug;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.utils.WLConstant;

public class ImportDrugDto {

	private Long id;
	private String code;
	private Date dateImport;//Ngày nhập
	private FarmDto farm;
	private UnitDto unit;//đơn vị tính
	private DrugDto drug;//thuốc
	private double quantity;//số lượng
	private double price;//giá
	private String supplier;//Nhà cung cấp
	private String preservation;//Cách bảo quản
	private ImportDrugDto importDrug;
	private Integer type;
	private double remainQuantity;
	
	private Date dateOfManufacture;// Ngày sản xuất
	
	private Date expiryDate;// Hạn sử dụng;
	
	private String producer;// Nhà sản xuất;
	
	private String symbolCode;// Ký hiệu-mã hiệu;
	
	private String tradenames;// Tên thương mại;
	
	private String batchCodeManufacture;// Số lô theo nhà sản xuất;
	
	private ImportExportAnimalDto importExportAnimal;// Mã lô tiêm thuốc
	
	public ImportExportAnimalDto getImportExportAnimal() {
		return importExportAnimal;
	}

	public void setImportExportAnimal(ImportExportAnimalDto importExportAnimal) {
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

	public ImportDrugDto getImportDrug() {
		return importDrug;
	}

	public void setImportDrug(ImportDrugDto importDrug) {
		this.importDrug = importDrug;
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

	public Date getDateImport() {
		return dateImport;
	}

	public void setDateImport(Date dateImport) {
		this.dateImport = dateImport;
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

	public UnitDto getUnit() {
		return unit;
	}

	public void setUnit(UnitDto unit) {
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
	
	public DrugDto getDrug() {
		return drug;
	}

	public void setDrug(DrugDto drug) {
		this.drug = drug;
	}

	public String getPreservation() {
		return preservation;
	}

	public void setPreservation(String preservation) {
		this.preservation = preservation;
	}

	public ImportDrugDto() {
		super();
	}

	public ImportDrugDto(ImportDrug importDrug) {
		this.id = importDrug.getId();
		this.code=importDrug.getCode();
		this.dateImport=importDrug.getDateImport();
		if(importDrug.getDrug()!=null)
		this.drug=new DrugDto(importDrug.getDrug());
		this.preservation=importDrug.getPreservation();
		this.price=importDrug.getPrice();
		this.quantity=importDrug.getQuantity();
		this.supplier=importDrug.getSupplier();
		if(importDrug.getUnit()!= null) {
			this.unit=new UnitDto(importDrug.getUnit());
		}
		this.dateOfManufacture = importDrug.getDateOfManufacture();
		this.expiryDate = importDrug.getExpiryDate();
		this.producer = importDrug.getProducer();
		this.symbolCode = importDrug.getSymbolCode();
		this.tradenames = importDrug.getTradenames();
		this.batchCodeManufacture = importDrug.getBatchCodeManufacture();

		if (importDrug.getFarm() != null) {
			this.farm = new FarmDto();
			this.farm.setId(importDrug.getFarm().getId());
			this.farm.setCode(importDrug.getFarm().getCode());
			this.farm.setName(importDrug.getFarm().getName());
			this.farm.setDescription(importDrug.getFarm().getDescription());
			this.farm.setOwnerName(importDrug.getFarm().getOwnerName());
			this.farm.setAdressDetail(importDrug.getFarm().getAdressDetail());
		}
		if(importDrug.getImportExportAnimal() != null) {
			this.importExportAnimal = new ImportExportAnimalDto(importDrug.getImportExportAnimal());
		}
		this.type = importDrug.getType();
		if(importDrug.getUnit()!= null) {
			this.unit = new UnitDto(importDrug.getUnit());
		}
		this.remainQuantity = importDrug.getRemainQuantity();
		if(importDrug.getImportDrug()!= null) {
			this.importDrug = new ImportDrugDto();
			this.importDrug.setId(importDrug.getImportDrug().getId());
			this.importDrug.setDateImport(importDrug.getImportDrug().getDateImport());
			this.importDrug.setQuantity(importDrug.getImportDrug().getQuantity());
			this.importDrug.setRemainQuantity(importDrug.getImportDrug().getRemainQuantity());
			this.importDrug.setCode(importDrug.getImportDrug().getCode());
			this.importDrug.setSupplier(importDrug.getImportDrug().getSupplier());
			this.importDrug.setPreservation(importDrug.getImportDrug().getPreservation());
			this.importDrug.setPrice(importDrug.getImportDrug().getPrice());
			if(importDrug.getImportDrug().getUnit()!= null) {
				this.importDrug.setUnit(new UnitDto(importDrug.getImportDrug().getUnit()));
			}
			if(importDrug.getImportDrug().getDrug()!= null) {
				this.importDrug.setDrug(new DrugDto(importDrug.getImportDrug().getDrug()));
			}
			if(this.code == null) {
				this.code = importDrug.getImportDrug().getCode();
			}
			this.importDrug.setExpiryDate(importDrug.getImportDrug().getExpiryDate());
		}
		
		
	}

	public ImportDrugDto(Object[] results,List<String> columns) {
		if(results!=null && results.length>0 && columns!=null && columns.size()>0) {
			for (int i = 0; i < columns.size(); i++) {
				
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.quantity.getValue())) {
					this.quantity = (Double)results[i];
				}
				
//				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(FMSConstant.GroupByItem.district.getValue()+"id")) {
//					if(this.district==null) this.district = new FmsAdministrativeUnitDto();
//					this.district.setId((Long)results[i]);
//				}
//				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(FMSConstant.GroupByItem.district.getValue()+"name")) {
//					if(this.district==null) this.district = new FmsAdministrativeUnitDto();
//					this.district.setName(String.valueOf(results[i]));
//				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.farm.getValue()+"id")) {
					if(this.farm==null) this.farm = new FarmDto();
					this.farm.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.farm.getValue()+"name")) {
					if(this.farm==null) this.farm = new FarmDto();
					this.farm.setName(String.valueOf(results[i]));
				}
//				
//				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(FMSConstant.GroupByItem.province.getValue()+"id")) {					
//					if(this.province==null) this.province = new FmsAdministrativeUnitDto();
//					this.province.setId((Long)results[i]);
//				}
//				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(FMSConstant.GroupByItem.province.getValue()+"name")) {					
//					if(this.province==null) this.province = new FmsAdministrativeUnitDto();
//					this.province.setName(String.valueOf(results[i]));
//				}
//				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(FMSConstant.GroupByItem.region.getValue()+"id")) {
//					if(this.region==null) this.region = new FmsRegionDto();
//					this.region.setId((Long)results[i]);
//				}
//				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(FMSConstant.GroupByItem.region.getValue()+"name")) {
//					if(this.region==null) this.region = new FmsRegionDto();
//					this.region.setName(String.valueOf(results[i]));
//				}
//				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(FMSConstant.GroupByItem.ward.getValue()+"id")) {					
//					if(this.ward==null) this.ward = new FmsAdministrativeUnitDto();
//					this.ward.setId((Long)results[i]);
//				}
//				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(FMSConstant.GroupByItem.ward.getValue()+"name")) {					
//					if(this.ward==null) this.ward = new FmsAdministrativeUnitDto();
//					this.ward.setName(String.valueOf(results[i]));
//				}
			}
		}
	}
}
