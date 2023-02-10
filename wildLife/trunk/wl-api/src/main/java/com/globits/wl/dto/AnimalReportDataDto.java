package com.globits.wl.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.globits.core.domain.BaseObject;
import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.IndividualAnimal;

public class AnimalReportDataDto extends BaseObjectDto {
	private AnimalDto animal;
	private FarmDto farm;
	private Integer year;// Năm của kỳ báo cáo
	private Integer quarter;// Tháng của kỳ báo cáo
	private Integer month;// Tháng của kỳ báo cáo
	private Integer day;// Tháng của kỳ báo cáo
	private Integer total;// Tổng số con
	private Integer male;// Số con con cái
	private Integer female;// Số con đực
	private Integer unGender;// Số con không xác định giới tính
	private String source;// Nguồn gốc
	private String purpose;// Mục đích nuôi
	private Date registrationDate;// ngày đăng ký
	private int type;
	private ProductTargetDto productTarget;
	private OriginalDto original;
	private List<IndividualAnimalDto> individualAnimals;
	private UUID formId;
	private Integer formType;

	private Integer reasonBornAtFarm;// Số gấu sinh ra tại trại
	private Integer reasonOther;// số gấu do nguyên nhân khác

	private FarmReportPeriodDto farmReportPeriod;
	private Long farmReportPeriodId;

	private String farmName;
	private String farmCode;
	private String wardName;
	private String wardCode;
	private String disName;
	private String disCode;
	private String proName;
	private String proCode;
	private String village;
	private String animalName;
	private String animalSciName;
	private String animalCode;

	private FmsAdministrativeUnitDto administrativeUnitDto;// xã
	private FmsAdministrativeUnitDto districtDto;// huyện
	private FmsAdministrativeUnitDto provinceDto;// tỉnh

	// Thêm 4 trường này để phân loại loài theo năm nếu có thay đổi
	private String vnlist;// DVHD nguy cap, quy, hiem (nghị định 64-2019)
	private String vnlist06;
	private String cites;// Cấp độ nguy cấp (CITES list là IB, IIB và IIIB)
	private String animalGroup;/*
								 * nhóm động vật:Nhóm 1: I cites, IB và NĐ64. Nhóm 2: II,III cites và IIB Nhóm
								 * 3: Động vật rừng thông thường hoang dã khác
								 */

	public Integer getReasonBornAtFarm() {
		return reasonBornAtFarm;
	}

	public void setReasonBornAtFarm(Integer reasonBornAtFarm) {
		this.reasonBornAtFarm = reasonBornAtFarm;
	}

	public Integer getReasonOther() {
		return reasonOther;
	}

	public void setReasonOther(Integer reasonOther) {
		this.reasonOther = reasonOther;
	}

	public List<IndividualAnimalDto> getIndividualAnimals() {
		return individualAnimals;
	}

	public void setIndividualAnimals(List<IndividualAnimalDto> individualAnimals) {
		this.individualAnimals = individualAnimals;
	}

	public ProductTargetDto getProductTarget() {
		return productTarget;
	}

	public void setProductTarget(ProductTargetDto productTarget) {
		this.productTarget = productTarget;
	}

	public OriginalDto getOriginal() {
		return original;
	}

	public void setOriginal(OriginalDto original) {
		this.original = original;
	}

	public AnimalDto getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public UUID getFormId() {
		return formId;
	}

	public void setFormId(UUID formId) {
		this.formId = formId;
	}

	public Integer getFormType() {
		return formType;
	}

	public void setFormType(Integer formType) {
		this.formType = formType;
	}

	public FarmReportPeriodDto getFarmReportPeriod() {
		return farmReportPeriod;
	}

	public void setFarmReportPeriod(FarmReportPeriodDto farmReportPeriod) {
		this.farmReportPeriod = farmReportPeriod;
	}

	public Long getFarmReportPeriodId() {
		return farmReportPeriodId;
	}

	public void setFarmReportPeriodId(Long farmReportPeriodId) {
		this.farmReportPeriodId = farmReportPeriodId;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public String getFarmCode() {
		return farmCode;
	}

	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}

	public String getWardName() {
		return wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	public String getWardCode() {
		return wardCode;
	}

	public void setWardCode(String wardCode) {
		this.wardCode = wardCode;
	}

	public String getDisName() {
		return disName;
	}

	public void setDisName(String disName) {
		this.disName = disName;
	}

	public String getDisCode() {
		return disCode;
	}

	public void setDisCode(String disCode) {
		this.disCode = disCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getAnimalName() {
		return animalName;
	}

	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

	public String getAnimalSciName() {
		return animalSciName;
	}

	public void setAnimalSciName(String animalSciName) {
		this.animalSciName = animalSciName;
	}

	public String getAnimalCode() {
		return animalCode;
	}

	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}

	public FmsAdministrativeUnitDto getAdministrativeUnitDto() {
		return administrativeUnitDto;
	}

	public void setAdministrativeUnitDto(FmsAdministrativeUnitDto administrativeUnitDto) {
		this.administrativeUnitDto = administrativeUnitDto;
	}

	public FmsAdministrativeUnitDto getDistrictDto() {
		return districtDto;
	}

	public void setDistrictDto(FmsAdministrativeUnitDto districtDto) {
		this.districtDto = districtDto;
	}

	public FmsAdministrativeUnitDto getProvinceDto() {
		return provinceDto;
	}

	public void setProvinceDto(FmsAdministrativeUnitDto provinceDto) {
		this.provinceDto = provinceDto;
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

	public AnimalReportDataDto() {
	}

	public AnimalReportDataDto(Long male, Long female, Long unGender, Animal animal, Farm farm) {
		if (male != null) {
			this.male = male.intValue();
		} else {
			this.male = 0;
		}
		if (female != null) {
			this.female = female.intValue();
		} else {
			this.female = 0;
		}
		if (unGender != null) {
			this.unGender = unGender.intValue();
		} else {
			this.unGender = 0;
		}
		this.animal = new AnimalDto(animal);
		if (farm != null && farm.getId() != null) {
			this.farm = new FarmDto();
			this.farm.setId(farm.getId());
		}
	}

	public AnimalReportDataDto(AnimalReportData entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.year = entity.getYear();// Năm của kỳ báo cáo
			this.quarter = entity.getQuarter();// Tháng của kỳ báo cáo
			this.month = entity.getMonth();// Tháng của kỳ báo cáo
			this.day = entity.getDay();// Tháng của kỳ báo cáo
			this.total = entity.getTotal();// Tổng số con
			this.male = entity.getMale();// Số con con cái
			this.female = entity.getFemale();// Số con đực
			this.unGender = entity.getUnGender();// Số con không xác định giới tính
			this.source = entity.getSource();// Mã Nguồn gốc
			this.purpose = entity.getPurpose();// Mã Mục đích nuôi
			this.registrationDate = entity.getRegistrationDate();// ngày đăng ký
			this.type = entity.getType();
			if (entity.getAnimal() != null) {
				this.animal = new AnimalDto(entity.getAnimal(), true);
				this.animalCode = entity.getAnimal().getCode();
				this.animalName = entity.getAnimal().getName();
				this.animalSciName = entity.getAnimal().getScienceName();
			}
			if (entity.getFarm() != null) {
				this.farm = new FarmDto();
				this.farm.setId(entity.getFarm().getId());
				this.farm.setCode(entity.getFarm().getCode());
				this.farm.setName(entity.getFarm().getName());
				this.farm.setOwnerName(entity.getFarm().getOwnerName());
				this.village = entity.getFarm().getVillage();
				this.farmCode = entity.getFarm().getCode();
				this.farmName = entity.getFarm().getName();
				this.farm.setBusinessRegistrationDate(entity.getFarm().getBusinessRegistrationDate());
				if (entity.getFarm().getAdministrativeUnit() != null) {
					this.wardName = entity.getFarm().getAdministrativeUnit().getName();
					this.wardCode = entity.getFarm().getAdministrativeUnit().getCode();
					if (entity.getFarm().getAdministrativeUnit().getParent() != null) {
						this.disName = entity.getFarm().getAdministrativeUnit().getParent().getName();
						this.disCode = entity.getFarm().getAdministrativeUnit().getParent().getCode();
						if (entity.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
							this.proName = entity.getFarm().getAdministrativeUnit().getParent().getParent().getName();
							this.proCode = entity.getFarm().getAdministrativeUnit().getParent().getParent().getCode();
						}
					}
				}
			}
			if (entity.getProductTarget() != null) {
				this.productTarget = new ProductTargetDto(entity.getProductTarget());
			}
			if (entity.getOriginal() != null) {
				this.original = new OriginalDto(entity.getOriginal());
			}
			this.individualAnimals = new ArrayList<IndividualAnimalDto>();
			if (entity.getIndividualAnimals() != null && entity.getIndividualAnimals().size() > 0) {
				for (IndividualAnimal individualAnimal : entity.getIndividualAnimals()) {
					if (individualAnimal != null) {
						this.individualAnimals.add(new IndividualAnimalDto(individualAnimal));
					}
				}
			}
			if (entity.getFarmReportPeriod() != null) {
				this.farmReportPeriod = new FarmReportPeriodDto(entity.getFarmReportPeriod());
			}
			this.formId = entity.getFormId();
			this.formType = entity.getFormType();
			this.reasonBornAtFarm = entity.getReasonBornAtFarm();
			this.reasonOther = entity.getReasonOther();
			if (entity.getAdministrativeUnit() != null) {
				this.administrativeUnitDto = new FmsAdministrativeUnitDto();
				this.administrativeUnitDto.setId(entity.getAdministrativeUnit().getId());
				this.administrativeUnitDto.setCode(entity.getAdministrativeUnit().getCode());
				this.administrativeUnitDto.setName(entity.getAdministrativeUnit().getName());
			}
			if (entity.getDistrict() != null) {
				this.districtDto = new FmsAdministrativeUnitDto();
				this.districtDto.setId(entity.getDistrict().getId());
				this.districtDto.setCode(entity.getDistrict().getCode());
				this.districtDto.setName(entity.getDistrict().getName());
			}
			if (entity.getProvince() != null) {
				this.provinceDto = new FmsAdministrativeUnitDto();
				this.provinceDto.setId(entity.getProvince().getId());
				this.provinceDto.setCode(entity.getProvince().getCode());
				this.provinceDto.setName(entity.getProvince().getName());
			}
			this.vnlist = entity.getVnlist();
			this.vnlist06 =  entity.getVnlist06();
			this.cites = entity.getCites();
			this.animalGroup = entity.getAnimalGroup();
		}
	}
}
