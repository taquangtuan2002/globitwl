package com.globits.wl.dto;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDateTime;

import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.FmsRegion;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.IndividualAnimal;
import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.utils.NumberUtils;
import com.globits.wl.utils.WLConstant;

public class ImportExportAnimalDto {
	private Long id;
	
	/*
	 * 1: Nhập
	 * -1: Xuất
	 */
	private int type;
	
	private FarmDto farm;
	
	private FmsAdministrativeUnitDto ward;
	private FmsAdministrativeUnitDto district;
	private FmsAdministrativeUnitDto province;
	private FmsRegionDto region;
	
	private AnimalDto animal;
	private Long animalId;
	private String animalName;
	
	private AnimalDto parent;//Loại gia cầm
	private AnimalTypeDto animalType;//Loại vật nuôi
	
	private OriginalDto original;//Nguồn gốc
	
	private ProductTargetDto productTarget;//Hướng sản phẩm
	private SeedLevelDto seedLevel;	//Cấp giống
	
	private String batchCode;//Số lô
	
	private Date dateImport;//Ngày nhập
	
	private Date dateExport;//Ngày xuất
	
	private Date dateIssue;
	
	private double quantity;//Số lượng: con
	
	private double amount;//Khối lượng: kg,tấn, tạ,...
	
	private int dayOld;//Ngày tuổi
	
	private double lifeCycle;//Số ngày dự kiến nuôi
	
	private String description;//Diễn tả
	private String voucherCode;//Số hóa đơn (trường hợp xuất)
	
	private Set<InjectionPlantDto> injectionPlants = new HashSet<InjectionPlantDto>();
	
	private String supplierName;
	
	private String supplierAdress;
	
	private int exportType;//Loại phiếu xuất
	
	
	private String exportReason;//Lý do xuất
	
	
	private String buyerName;//Tên người mua
	
	
	private String buyerAdress;//Địa chỉ người mua
	
	private int monthReport;
	private int yearReport;
	private String monthInYear;
	private int toMonthReport;
	private int toYearReport;
	private String created_by;//người tạo
	private LocalDateTime create_date;

	
	private double remainQuantity;//Số lượng còn lại: con
	private String provinceName;
	private ImportExportAnimalDto importAnimal;//Phiếu nhập Id
	private FmsAdministrativeUnitDto provincial;
	private Long countFarm;
	private Boolean isExChange;//đánh dấu chuyển loại
	private ImportExportAnimalDto exportAnimal;//Phiếu xuất Id
	private ProductTargetDto productTargetChange;//Hướng sản phẩm khi điều chuyển loại
	private OwnershipDto ownership;//Hình thức sở hữu
	private ImportExportAnimalDto importExportAnimal;//id đàn của nhập trứng
	private Long numberOfDayRasied;//số ngày đã nuôi
	private double farmingTime;//thời gian nuôi (ngày)
	private double weightGain;//tăng trọng (kg)
	private double loss;//hao hụt(% )
	private int eggYield;//năng suất trứng quả/năm
	private double quantityEgg;// Số lượng trứng thu đối với lô có hướng trứng
	private Integer gender;// Giới tính
	private String chipCode;// mã số chip
	private Boolean isBornAtFarm;// Là true -> sinh ra tại trại -> false -> nguyên nhân khác
	private List<IndividualAnimalDto> individualAnimals;
	private boolean dupCodeIndividualAnimals;
	private Set<String> individualAnimalDupCode = new HashSet<String>();
	
	private Integer male;// tổng con đực
	private Integer female;// tổng con cái
	private Integer unGender;// số con không xác định giới tính
	private Integer animalReportDataType;// Loại con dưới 1 tuổi, trên 1 tuổi...
	
	private Integer remainMale;// Số lượng con đực còn lại
	private Integer remainFemale;// Số lượng con cái còn lại
	private Integer remainUnGender;// Số lượng không xác định còn lại
	
	private ForestProductsListDto forestProductsList;

	public boolean isDupCodeIndividualAnimals() {
		return dupCodeIndividualAnimals;
	}

	public void setDupCodeIndividualAnimals(boolean dupCodeIndividualAnimals) {
		this.dupCodeIndividualAnimals = dupCodeIndividualAnimals;
	}

	public Set<String> getIndividualAnimalDupCode() {
		return individualAnimalDupCode;
	}

	public void setIndividualAnimalDupCode(Set<String> individualAnimalDupCode) {
		this.individualAnimalDupCode = individualAnimalDupCode;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public List<IndividualAnimalDto> getIndividualAnimals() {
		return individualAnimals;
	}

	public void setIndividualAnimals(List<IndividualAnimalDto> individualAnimals) {
		this.individualAnimals = individualAnimals;
	}

	public String getChipCode() {
		return chipCode;
	}

	public void setChipCode(String chipCode) {
		this.chipCode = chipCode;
	}

	public Boolean getIsBornAtFarm() {
		return isBornAtFarm;
	}

	public void setIsBornAtFarm(Boolean isBornAtFarm) {
		this.isBornAtFarm = isBornAtFarm;
	}

	public double getQuantityEgg() {
		return quantityEgg;
	}

	public void setQuantityEgg(double quantityEgg) {
		this.quantityEgg = quantityEgg;
	}

	public SeedLevelDto getSeedLevel() {
		return seedLevel;
	}

	public void setSeedLevel(SeedLevelDto seedLevel) {
		this.seedLevel = seedLevel;
	}

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

	
	public FmsAdministrativeUnitDto getProvincial() {
		return provincial;
	}

	public void setProvincial(FmsAdministrativeUnitDto provincial) {
		this.provincial = provincial;
	}

	public Long getCountFarm() {
		return countFarm;
	}

	public void setCountFarm(Long countFarm) {
		this.countFarm = countFarm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

	public Long getAnimalId() {
		if(animal!=null) {
			this.animalId = animal.getId();
		}
		return animalId;
	}

	public String getAnimalName() {
		if(animal!=null) {
			this.animalName = animal.getName();
		}
		return animalName;
	}

	public FmsAdministrativeUnitDto getWard() {
		return ward;
	}

	public void setWard(FmsAdministrativeUnitDto ward) {
		this.ward = ward;
	}

	public FmsAdministrativeUnitDto getDistrict() {
		return district;
	}

	public void setDistrict(FmsAdministrativeUnitDto district) {
		this.district = district;
	}

	public FmsAdministrativeUnitDto getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnitDto province) {
		this.province = province;
	}

	public FmsRegionDto getRegion() {
		return region;
	}

	public void setRegion(FmsRegionDto region) {
		this.region = region;
	}

	public AnimalDto getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}

	public AnimalDto getParent() {
		return parent;
	}

	public void setParent(AnimalDto parent) {
		this.parent = parent;
	}

	public AnimalTypeDto getAnimalType() {
		return animalType;
	}

	public void setAnimalType(AnimalTypeDto animalType) {
		this.animalType = animalType;
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

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Set<InjectionPlantDto> getInjectionPlants() {
		return injectionPlants;
	}

	public void setInjectionPlants(Set<InjectionPlantDto> injectionPlants) {
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

	public double getRemainQuantity() {
		//return remainQuantity;
		return NumberUtils.round(remainQuantity,2);
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getMonthReport() {
		return monthReport;
	}

	public void setMonthReport(int monthReport) {
		this.monthReport = monthReport;
	}

	public int getYearReport() {
		return yearReport;
	}

	public void setYearReport(int yearReport) {
		this.yearReport = yearReport;
	}	

	public Boolean getIsExChange() {
		return isExChange;
	}

	public void setIsExChange(Boolean isExChange) {
		this.isExChange = isExChange;
	}

	public ImportExportAnimalDto getExportAnimal() {
		return exportAnimal;
	}

	public void setExportAnimal(ImportExportAnimalDto exportAnimal) {
		this.exportAnimal = exportAnimal;
	}	

	public ProductTargetDto getProductTargetChange() {
		return productTargetChange;
	}

	public void setProductTargetChange(ProductTargetDto productTargetChange) {
		this.productTargetChange = productTargetChange;
	}
	

	public Long getNumberOfDayRasied() {
		return numberOfDayRasied;
	}

	public void setNumberOfDayRasied(Long numberOfDayRasied) {
		this.numberOfDayRasied = numberOfDayRasied;
	}

	public String getMonthInYear() {
		monthInYear=monthReport+"/"+yearReport ;
		if(toMonthReport>0 && toYearReport>0){
			if(monthReport==toMonthReport && yearReport==toYearReport){
				monthInYear=monthReport+"/"+yearReport ;
			}else{
				monthInYear= monthInYear+ " - "+toMonthReport+"/"+toYearReport ;
			}
		
		}
		return monthInYear;
	}

	public void setRemainQuantity(double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public ImportExportAnimalDto getImportAnimal() {
		return importAnimal;
	}

	public void setImportAnimal(ImportExportAnimalDto importAnimal) {
		this.importAnimal = importAnimal;
	}

	public int getToMonthReport() {
		return toMonthReport;
	}

	public void setToMonthReport(int toMonthReport) {
		this.toMonthReport = toMonthReport;
	}

	public int getToYearReport() {
		return toYearReport;
	}

	public void setToYearReport(int toYearReport) {
		this.toYearReport = toYearReport;
	}

	public OwnershipDto getOwnership() {
		return ownership;
	}

	public void setOwnership(OwnershipDto ownership) {
		this.ownership = ownership;
	}		

	public ImportExportAnimalDto getImportExportAnimal() {
		return importExportAnimal;
	}

	public void setImportExportAnimal(ImportExportAnimalDto importExportAnimal) {
		this.importExportAnimal = importExportAnimal;
	}	

	public double getFarmingTime() {
		return farmingTime;
	}

	public void setFarmingTime(double farmingTime) {
		this.farmingTime = farmingTime;
	}

	public double getWeightGain() {
		return NumberUtils.round(weightGain,2);
//		return weightGain;
	}

	public void setWeightGain(double weightGain) {
		this.weightGain = weightGain;
	}

	public double getLoss() {
		return NumberUtils.round(loss,2);
//		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}

	public int getEggYield() {
		return eggYield;
	}

	public void setEggYield(int eggYield) {
		this.eggYield = eggYield;
	}

	public Integer getMale() {
		if(male == null) {
			return 0;
		}
		return male;
	}

	public void setMale(Integer male) {
		this.male = male;
	}

	public Integer getFemale() {
		if(female == null) {
			return 0;
		}
		return female;
	}

	public void setFemale(Integer female) {
		this.female = female;
	}

	public Integer getUnGender() {
		if(unGender == null) {
			return 0;
		}
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

	public ForestProductsListDto getForestProductsList() {
		return forestProductsList;
	}

	public void setForestProductsList(ForestProductsListDto forestProductsList) {
		this.forestProductsList = forestProductsList;
	}

	public ImportExportAnimalDto() {
		super();
	}
	
	public ImportExportAnimalDto(ImportExportAnimal entity) {
		super();
		
		if(entity != null) { 
			this.id = entity.getId();
			this.type  = entity.getType();
			if(entity.getFarm()!= null) {
				this.farm = new FarmDto(entity.getFarm(),true);
				if(entity.getFarm().getAdministrativeUnit()!=null && entity.getFarm().getAdministrativeUnit().getParent()!=null && entity.getFarm().getAdministrativeUnit().getParent().getParent()!=null) {
					this.provinceName = entity.getFarm().getAdministrativeUnit().getParent().getParent().getName();
					if (entity.getFarm().getAdministrativeUnit().getParent().getParent().getRegion() != null) {
						this.region = new FmsRegionDto(entity.getFarm().getAdministrativeUnit().getParent().getParent().getRegion());
					}
				}
			}
			if(entity.getAnimal()!= null) {
				this.animal = new AnimalDto(entity.getAnimal(),true);
			}
			if(entity.getProductTarget()!=null) {
				this.productTarget = new ProductTargetDto(entity.getProductTarget());
			}
			if (entity.getSeedLevel() != null) {
				this.seedLevel = new SeedLevelDto(entity.getSeedLevel());
			}
			if(entity.getOriginal()!=null) {
				this.original = new OriginalDto(entity.getOriginal());
			}
			if(entity.getProvince()!= null) {
				this.provincial = new FmsAdministrativeUnitDto(entity.getProvince());
			}
			this.batchCode = entity.getBatchCode();
			if(entity.getDateImport()!= null) {
				this.dateImport = entity.getDateImport();
			}
			this.quantity = entity.getQuantity();
			this.amount = entity.getAmount();
			this.dayOld = entity.getDayOld();
			this.lifeCycle = entity.getLifeCycle();
			this.description = entity.getDescription();
			this.female = entity.getFemale();
			this.male = entity.getMale();
			this.unGender = entity.getUnGender();
			this.remainMale = entity.getRemainMale();
			this.remainFemale = entity.getRemainFemale();
			this.remainUnGender = entity.getRemainUnGender();
			this.animalReportDataType = entity.getAnimalReportDataType();
			if(entity.getInjectionPlants() != null && entity.getInjectionPlants().size() > 0) {
				for(InjectionPlant injectionPlant :entity.getInjectionPlants()) {
					if(injectionPlant!= null) {
						InjectionPlantDto injectionPlantDto= new InjectionPlantDto(injectionPlant);
						this.injectionPlants.add(injectionPlantDto);
					}
				}
			}
			this.supplierName = entity.getSupplierName();
			this.supplierAdress = entity.getSupplierAdress();
			
			this.dateExport = entity.getDateExport();
			this.voucherCode = entity.getVoucherCode();
			this.exportType = entity.getExportType();
			this.exportReason = entity.getExportReason();
			this.buyerName = entity.getBuyerName();
			this.buyerAdress = entity.getBuyerAdress();
			this.dateIssue = entity.getDateIssue();
			this.remainQuantity=entity.getRemainQuantity();
			this.created_by=entity.getCreatedBy();
			this.create_date=entity.getCreateDate();
			this.gender = entity.getGender();
			this.chipCode = entity.getChipCode();
			this.isBornAtFarm = entity.getIsBornAtFarm();
			if(entity.getImportAnimal()!=null){
				ImportExportAnimalDto dto=new ImportExportAnimalDto();
				dto.setId(entity.getImportAnimal().getId());
				dto.setBatchCode(entity.getImportAnimal().getBatchCode());
				dto.setDateImport(entity.getImportAnimal().getDateImport());
				dto.setQuantity(entity.getImportAnimal().getQuantity());
				dto.setRemainQuantity(entity.getImportAnimal().getRemainQuantity());
				dto.setAmount(entity.getImportAnimal().getAmount());
				dto.setRemainMale(entity.getImportAnimal().getRemainMale());
				dto.setRemainFemale(entity.getImportAnimal().getRemainFemale());
				dto.setRemainUnGender(entity.getImportAnimal().getRemainUnGender());
				dto.setAnimalReportDataType(entity.getImportAnimal().getAnimalReportDataType());
				this.importAnimal=dto;
			}
			if(entity.getIsExChange()!=null) {
				this.isExChange=entity.getIsExChange();
			}
			if(entity.getExportAnimal()!=null){
				ImportExportAnimalDto dto=new ImportExportAnimalDto();
				dto.setId(entity.getExportAnimal().getId());
				dto.setBatchCode(entity.getExportAnimal().getBatchCode());
				dto.setDateImport(entity.getExportAnimal().getDateExport());
				dto.setQuantity(entity.getExportAnimal().getQuantity());
				//dto.setRemainQuantity(entity.getExportAnimal().getRemainQuantity());
				dto.setAmount(entity.getExportAnimal().getAmount());
				this.exportAnimal=dto;
			}
			if(entity.getQuantityEgg()!= null) {
				this.quantityEgg = entity.getQuantityEgg();
			}
			if(entity.getIndividualAnimals() != null) {
				this.individualAnimals = new ArrayList<IndividualAnimalDto>();
				for(IndividualAnimal individualAnimal :entity.getIndividualAnimals()) {
					if(individualAnimal != null) {
						this.individualAnimals.add(new IndividualAnimalDto(individualAnimal));
					}
				}
			}
			if(entity.getForestProductsList() != null) {
				this.forestProductsList =new ForestProductsListDto(entity.getForestProductsList());
				this.forestProductsList.setId(entity.getForestProductsList().getId());
			}
		}
	}
	
	public ImportExportAnimalDto(Double quantity,Double amount) {
		if(quantity!=null)
			this.quantity = quantity;
		if(amount!=null)
			this.amount = amount;
	}
	
	public ImportExportAnimalDto(String created_by,LocalDateTime create_date, Double quantity,Double amount,Animal animal,Animal animalParent,AnimalType animalType,
			String batchCode,Farm farm,Original original,ProductTarget productTarget,FmsAdministrativeUnit ward,
			FmsAdministrativeUnit district,FmsAdministrativeUnit province,FmsRegion region) {
		if(created_by!=null) {
			this.created_by= created_by;
		}
		if(create_date!=null) {
			this.create_date=create_date;
		}
		if(quantity!=null)
			this.quantity = quantity;
		if(amount!=null)
			this.amount = amount;
		if(animal!=null) {
			this.animal = new AnimalDto(animal);
		}
		if(batchCode!=null) {
			this.batchCode = batchCode;
		}
		if(animalParent!=null) {
			this.parent = new AnimalDto(animalParent);			
		}
		if(animalType!=null) {
			this.animalType = new AnimalTypeDto(animalType);			
		}
		if(farm!=null) {
			this.farm = new FarmDto(farm);
			if(farm.getAdministrativeUnit()!=null && farm.getAdministrativeUnit().getParent()!=null && farm.getAdministrativeUnit().getParent().getParent()!=null) {
				this.provinceName = farm.getAdministrativeUnit().getParent().getParent().getName();
			}
		}		
		if(original!=null) {
			this.original = new OriginalDto(original);		
		}
		if(productTarget!=null) {
			this.productTarget = new ProductTargetDto(productTarget);
		}
		if(ward!=null) {
			this.ward = new FmsAdministrativeUnitDto(ward);
		}
		if(district!=null) {
			this.district =  new FmsAdministrativeUnitDto(district);
		}
		if(province!=null) {
			this.province = new FmsAdministrativeUnitDto(province);
		}
		if(region!=null) {
			this.region = new FmsRegionDto(region);
		}
	}
	
	public ImportExportAnimalDto(Object[] results,List<String> columns) {
		if(results!=null && results.length>0 && columns!=null && columns.size()>0) {
			for (int i = 0; i < columns.size(); i++) {
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.amount.getValue())) {
					this.amount = (Double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.quantity.getValue())) {
					this.quantity = (Double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.sumMale.getValue())) {
					this.male = Math.toIntExact((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.sumFeMale.getValue())) {
					this.female = Math.toIntExact((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.sumUnGen.getValue())) {
					this.unGender = Math.toIntExact((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.dayOld.getValue())) {
					this.numberOfDayRasied = (Long)results[i];
				}				
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animal.getValue()+"id")) {
					if(this.animal==null) this.animal = new AnimalDto();
					this.animal.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animal.getValue()+"name")) {
					if(this.animal==null) this.animal = new AnimalDto();
					this.animal.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animal.getValue()+"scienceName")) {
					if(this.animal==null) this.animal = new AnimalDto();
					this.animal.setScienceName(String.valueOf(results[i]));
				}
				
				
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animal.getValue()+"farmingTime")) {
					this.farmingTime = (Double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animal.getValue()+"weightGain")) {
					this.weightGain = (Double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animal.getValue()+"loss")) {
					this.loss = (Double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animal.getValue()+"eggYield")) {
					this.eggYield = (Integer)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animalParent.getValue()+"id")) {
					if(this.parent==null) this.parent = new AnimalDto();
					this.parent.setId((Long)results[i]);							
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animalParent.getValue()+"name")) {
					if(this.parent==null) this.parent = new AnimalDto();
					this.parent.setName(String.valueOf(results[i]));							
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animalType.getValue()+"id")) {
					if(this.animalType==null) this.animalType= new AnimalTypeDto();
					this.animalType.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animalType.getValue()+"name")) {
					if(this.animalType==null) this.animalType= new AnimalTypeDto();
					this.animalType.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.batchCode.getValue())) {
					this.batchCode = String.valueOf(results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.district.getValue()+"id")) {
					if(this.district==null) this.district = new FmsAdministrativeUnitDto();
					this.district.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.district.getValue()+"name")) {
					if(this.district==null) this.district = new FmsAdministrativeUnitDto();
					this.district.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.farm.getValue()+"id")) {
					if(this.farm==null) this.farm = new FarmDto();
					this.farm.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.farm.getValue()+"name")) {
					if(this.farm==null) this.farm = new FarmDto();
					this.farm.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.original.getValue()+"id")) {
					if(this.original==null) this.original = new OriginalDto();
					this.original.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.original.getValue()+"name")) {
					if(this.original==null) this.original = new OriginalDto();
					this.original.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.productTarget.getValue()+"id")) {
					if(this.productTarget==null) this.productTarget = new ProductTargetDto();
					this.productTarget.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.productTarget.getValue()+"name")) {
					if(this.productTarget==null) this.productTarget = new ProductTargetDto();
					this.productTarget.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.productTarget.getValue()+"code")) {
					if(this.productTarget==null) this.productTarget = new ProductTargetDto();
					this.productTarget.setCode(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.seedLevel.getValue()+"name")) {
					if(this.seedLevel==null) this.seedLevel = new SeedLevelDto();
					this.seedLevel.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.province.getValue()+"id")) {					
					if(this.province==null) this.province = new FmsAdministrativeUnitDto();
					this.province.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.province.getValue()+"name")) {					
					if(this.province==null) this.province = new FmsAdministrativeUnitDto();
					this.province.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.region.getValue()+"id")) {
					if(this.region==null) this.region = new FmsRegionDto();
					this.region.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.region.getValue()+"name")) {
					if(this.region==null) this.region = new FmsRegionDto();
					this.region.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.ward.getValue()+"id")) {					
					if(this.ward==null) this.ward = new FmsAdministrativeUnitDto();
					this.ward.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.ward.getValue()+"name")) {					
					if(this.ward==null) this.ward = new FmsAdministrativeUnitDto();
					this.ward.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.countFarm.getValue())) {				
					this.countFarm = (Long)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.seedLevel.getValue()+"id")) {					
					if(this.seedLevel==null) this.seedLevel = new SeedLevelDto();
					this.seedLevel.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.seedLevel.getValue()+"name")) {					
					if(this.seedLevel==null) this.seedLevel = new SeedLevelDto();
					this.seedLevel.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.seedLevel.getValue()+"level")) {					
					if(this.seedLevel==null) this.seedLevel = new SeedLevelDto();
					this.seedLevel.setLevel((Integer)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.ownership.getValue()+"id")) {					
					if(this.ownership==null) this.ownership = new OwnershipDto();
					this.ownership.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.ownership.getValue()+"name")) {					
					if(this.ownership==null) this.ownership = new OwnershipDto();
					this.ownership.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.report.getValue()+"code")) {
					if(this.animal==null) this.animal = new AnimalDto();
					this.animal.setReportCode(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.report.getValue()+"name")) {
					if(this.animal==null) this.animal = new AnimalDto();
					this.animal.setReportName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.importEgg.getValue()+"id")) {					
					if(this.importExportAnimal==null) this.importExportAnimal = new ImportExportAnimalDto();
					this.importExportAnimal.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.importEgg.getValue()+"code")) {					
					if(this.importExportAnimal==null) this.importExportAnimal = new ImportExportAnimalDto();
					this.importExportAnimal.setBatchCode(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.liveStockMethod.getValue())) {					
					if(this.animal==null) this.animal = new AnimalDto();
					this.animal.setLiveStockMethod((Integer)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.animalReportDataType.getValue())) {					
					this.animalReportDataType = (Integer)results[i];
				}				
			}
		}
	}
}
