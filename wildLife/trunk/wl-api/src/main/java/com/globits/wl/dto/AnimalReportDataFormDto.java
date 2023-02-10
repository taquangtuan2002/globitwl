package com.globits.wl.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

public class AnimalReportDataFormDto {
	private AnimalDto animal;	
	private FarmDto farm;
	
	private Integer year;//Năm của kỳ báo cáo
	private Integer quarter;//Tháng của kỳ báo cáo
	private Integer month;//Tháng của kỳ báo cáo
	private Integer day;//Tháng của kỳ báo cáo
	
	private String source;//Nguồn gốc
	private String purpose;//Mục đích nuôi
	private Date registrationDate;//ngày đăng ký
	private int type;
	private ProductTargetDto productTarget;
	private OriginalDto original;
	
	private Integer totalNormal=0;
	
	private Integer totalParent=0;
	private Integer maleParent=0;//Số con con cái bố mẹ
	private Integer femaleParent=0;//Số con đực bố mẹ
	private Integer unGenderParent=0;//Số con không xác định giới tính bố mẹ
	
	private Integer totalChild=0;
	private Integer maleChild=0;//Số con con cái Con trên 1 tuổi
	private Integer femaleChild=0;//Số con đực Con trên 1 tuổi
	private Integer unGenderChild=0;//Số con không xác định giới tính Con trên 1 tuổi
	
	private Integer totalChildUnder1YO=0;
	private Integer maleChildUnder1YO=0;//Số con con cái Con dưới 1 tuổi
	private Integer femaleChildUnder1YO=0;//Số con đực Con dưới 1 tuổi
	private Integer unGenderChildUnder1YO=0;//Số con không xác định giới tính Con dưới 1 tuổi
	
	private Integer totalBearHasChip=0;// Tổng số gấu có chip
	private Integer maleBearHasChip=0;// Tổng số gấu đực có chip
	private Integer femaleBearHasChip=0;// số gấu cái có chíp
	private String chipCodes;// danh sách mã chip
	
	private Integer totalBearNoChip=0;// tổng gấu không chip
	private Integer maleBearNoChip=0;// số gấu đực không chip
	private Integer femaleBearNoChip=0;// số gấu cái không chip
	
	private Integer reasonBornAtFarm=0;// Số gấu sinh ra tại trại
	private Integer reasonOther=0;// số gấu do nguyên nhân khác
	
	private Integer totalGilts=0;
	private Integer maleGilts=0;
	private Integer femaleGilts=0;
	
	private Integer totalChildOver1YO=0;
	private Integer maleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực
	private Integer femaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái
	private Integer unGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính
	
	private List<String> listChipCode;
	
	private Long farmReportPeriodId;
	private String productTargets;
	private String note;
	private FmsAdministrativeUnitDto administrativeUnitDto;//xã
	private FmsAdministrativeUnitDto districtDto;//huyện
	private FmsAdministrativeUnitDto provinceDto;// tỉnh
	
	private String administrativeUnitName; //tên xã
	private String districtName; //tên huyện
	private String provinceName; //tên tỉnh
	private String adressDetail; //thôn ấp, xóm, số nhà
	private String farmCode; //mã số cơ sở 
	private String farmName; //Họ tên chủ nuôi
	private String animeName; //Tên loài
	private String animeSci; //Tên loài khoa học
	private String animeCode; //Mã loài
	
	private Integer total; //Mã loài
	private String foundingDate; // ngày thành lập
	
	public AnimalReportDataFormDto(Object[] entity) {
		if(entity != null) {
			int i = 7;
			
			if(entity[i] != null)
				this.provinceName = entity[i].toString();
			
			if(entity[++i] != null)
				this.districtName = entity[i].toString();
			
			if(entity[++i] != null)
				this.administrativeUnitName = entity[i].toString();
			
			if(entity[++i] != null)
				this.adressDetail = entity[i].toString();
			
			if(entity[++i] != null)
				this.farmCode = entity[i].toString();
			
			if(entity[++i] != null)
				this.farmName = entity[i].toString();
			
			if(entity[++i] != null)
				this.animeName = entity[i].toString();
			
			if(entity[++i] != null)
				this.animeSci = entity[i].toString();
			
			if(entity[++i] != null)
				this.animeCode = entity[i].toString();
			
			if(entity[++i] != null)
				this.total = (Integer) entity[i];
			
			this.totalParent = 0;
			if(entity[++i] != null) {
				this.maleParent = (Integer) entity[i];
				this.totalParent += this.maleParent;
			}
			if(entity[++i] != null) {
				this.femaleParent = (Integer) entity[i];
				this.totalParent += this.femaleParent;
			}
			
			this.totalGilts = 0;
			if(entity[++i] != null) {
				this.maleGilts = (Integer) entity[i];
				this.totalGilts += this.maleGilts;
			}
			if(entity[++i] != null) {
				this.femaleGilts = (Integer) entity[i];
				this.totalGilts += this.femaleGilts;
			}
			
			if(entity[++i] != null) {
				this.totalChildUnder1YO = (Integer) entity[i];
			}
			
			this.totalChildOver1YO = 0;
			if(entity[++i] != null) {
				this.maleChildOver1YearOld = (Integer) entity[i];
				this.totalChildOver1YO += this.maleChildOver1YearOld;
			}
			if(entity[++i] != null) {
				this.femaleChildOver1YearOld = (Integer) entity[i];
				this.totalChildOver1YO += this.femaleChildOver1YearOld;
			}
			if(entity[++i] != null) {
				this.unGenderChildOver1YearOld = (Integer) entity[i];
				this.totalChildOver1YO += this.unGenderChildOver1YearOld;
			}
			
			if(entity[++i] != null)
				this.foundingDate = entity[i].toString();
			this.note = "";
		}
	}
	
	public Integer getTotalChildOver1YO() {
		return totalChildOver1YO;
	}

	public void setTotalChildOver1YO(Integer totalChildOver1YO) {
		this.totalChildOver1YO = totalChildOver1YO;
	}

	public Integer getMaleChildOver1YearOld() {
		return maleChildOver1YearOld;
	}

	public void setMaleChildOver1YearOld(Integer maleChildOver1YearOld) {
		this.maleChildOver1YearOld = maleChildOver1YearOld;
	}

	public Integer getFemaleChildOver1YearOld() {
		return femaleChildOver1YearOld;
	}

	public void setFemaleChildOver1YearOld(Integer femaleChildOver1YearOld) {
		this.femaleChildOver1YearOld = femaleChildOver1YearOld;
	}

	public Integer getUnGenderChildOver1YearOld() {
		return unGenderChildOver1YearOld;
	}

	public void setUnGenderChildOver1YearOld(Integer unGenderChildOver1YearOld) {
		this.unGenderChildOver1YearOld = unGenderChildOver1YearOld;
	}

	public String getAdministrativeUnitName() {
		return administrativeUnitName;
	}

	public void setAdministrativeUnitName(String administrativeUnitName) {
		this.administrativeUnitName = administrativeUnitName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAdressDetail() {
		return adressDetail;
	}

	public void setAdressDetail(String adressDetail) {
		this.adressDetail = adressDetail;
	}

	public String getFarmCode() {
		return farmCode;
	}

	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	public String getAnimeName() {
		return animeName;
	}

	public void setAnimeName(String animeName) {
		this.animeName = animeName;
	}

	public String getAnimeSci() {
		return animeSci;
	}

	public void setAnimeSci(String animeSci) {
		this.animeSci = animeSci;
	}

	public String getAnimeCode() {
		return animeCode;
	}

	public void setAnimeCode(String animeCode) {
		this.animeCode = animeCode;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getFoundingDate() {
		return foundingDate;
	}

	public void setFoundingDate(String foundingDate) {
		this.foundingDate = foundingDate;
	}

	public String getProductTargets() {
		return productTargets;
	}
	public void setProductTargets(String productTargets) {
		this.productTargets = productTargets;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
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
	public Integer getTotalBearHasChip() {
		totalBearHasChip = 0;
		if(maleBearHasChip != null) {
			totalBearHasChip += maleBearHasChip;
		}
		if(femaleBearHasChip != null) {
			totalBearHasChip += femaleBearHasChip;
		}
		return totalBearHasChip;
	}
	public void setTotalBearHasChip(Integer totalBearHasChip) {
		this.totalBearHasChip = totalBearHasChip;
	}
	public Integer getMaleBearHasChip() {
		return maleBearHasChip;
	}
	public void setMaleBearHasChip(Integer maleBearHasChip) {
		this.maleBearHasChip = maleBearHasChip;
	}
	public Integer getFemaleBearHasChip() {
		return femaleBearHasChip;
	}
	public void setFemaleBearHasChip(Integer femaleBearHasChip) {
		this.femaleBearHasChip = femaleBearHasChip;
	}
	public Integer getTotalBearNoChip() {
		totalBearNoChip = 0;
		if(maleBearNoChip != null) {
			totalBearNoChip += maleBearNoChip;
		}
		if(femaleBearNoChip != null) {
			totalBearNoChip += femaleBearNoChip;
		}
		return totalBearNoChip;
	}
	public void setTotalBearNoChip(Integer totalBearNoChip) {
		this.totalBearNoChip = totalBearNoChip;
	}
	public Integer getMaleBearNoChip() {
		return maleBearNoChip;
	}
	public void setMaleBearNoChip(Integer maleBearNoChip) {
		this.maleBearNoChip = maleBearNoChip;
	}
	public Integer getFemaleBearNoChip() {
		return femaleBearNoChip;
	}
	public void setFemaleBearNoChip(Integer femaleBearNoChip) {
		this.femaleBearNoChip = femaleBearNoChip;
	}
	public String getChipCodes() {
		return chipCodes;
	}
	public void setChipCodes(String chipCodes) {
		this.chipCodes = chipCodes;
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
	public Integer getTotalNormal() {
		return totalNormal;
	}
	public void setTotalNormal(Integer totalNormal) {
		this.totalNormal = totalNormal;
	}
	public Integer getTotalParent() {
//		totalParent=0;
//		if(maleParent!=null)
//			totalParent+=maleParent;
//		if(femaleParent!=null)
//			totalParent+=femaleParent;
//		if(unGenderParent!=null) {
//			totalParent+=unGenderParent;
//		}
		return totalParent;
	}
	public void setTotalParent(Integer totalParent) {
		this.totalParent = totalParent;
	}
	public void setTotalChild(Integer totalChild) {
		this.totalChild = totalChild;
	}
	public Integer getMaleParent() {
		return maleParent;
	}
	public void setMaleParent(Integer maleParent) {
		this.maleParent = maleParent;
	}
	public Integer getFemaleParent() {
		return femaleParent;
	}
	public void setFemaleParent(Integer femaleParent) {
		this.femaleParent = femaleParent;
	}
	public Integer getUnGenderParent() {
		return unGenderParent;
	}
	public void setUnGenderParent(Integer unGenderParent) {
		this.unGenderParent = unGenderParent;
	}
	public Integer getTotalChild() {
//		totalChild=0;
//		if(maleChild!=null)
//			totalChild+=maleChild;
//		if(femaleChild!=null)
//			totalChild+=femaleChild;
//		if(unGenderChild!=null) {
//			totalChild+=unGenderChild;
//		}
		return totalChild;
	}
	public Integer getMaleChild() {
		return maleChild;
	}
	public void setMaleChild(Integer maleChild) {
		this.maleChild = maleChild;
	}
	public Integer getFemaleChild() {
		return femaleChild;
	}
	public void setFemaleChild(Integer femaleChild) {
		this.femaleChild = femaleChild;
	}
	public Integer getUnGenderChild() {
		return unGenderChild;
	}
	public void setUnGenderChild(Integer unGenderChild) {
		this.unGenderChild = unGenderChild;
	}
	public Integer getTotalChildUnder1YO() {
//		totalChildUnder1YO=0;
//		if(maleChildUnder1YO!=null)
//			totalChildUnder1YO+=maleChildUnder1YO;
//		if(femaleChildUnder1YO!=null)
//			totalChildUnder1YO+=femaleChildUnder1YO;
//		if(unGenderChildUnder1YO!=null) {
//			totalChildUnder1YO+=unGenderChildUnder1YO;
//		}
		return totalChildUnder1YO;
	}
	public void setTotalChildUnder1YO(Integer totalChildUnder1YO) {
		this.totalChildUnder1YO = totalChildUnder1YO;
	}
	public Integer getMaleChildUnder1YO() {
		return maleChildUnder1YO;
	}
	public void setMaleChildUnder1YO(Integer maleChildUnder1YO) {
		this.maleChildUnder1YO = maleChildUnder1YO;
	}
	public Integer getFemaleChildUnder1YO() {
		return femaleChildUnder1YO;
	}
	public void setFemaleChildUnder1YO(Integer femaleChildUnder1YO) {
		this.femaleChildUnder1YO = femaleChildUnder1YO;
	}
	public Integer getUnGenderChildUnder1YO() {
		return unGenderChildUnder1YO;
	}
	public void setUnGenderChildUnder1YO(Integer unGenderChildUnder1YO) {
		this.unGenderChildUnder1YO = unGenderChildUnder1YO;
	}
	public List<String> getListChipCode() {
		return listChipCode;
	}
	public void setListChipCode(List<String> listChipCode) {
		this.listChipCode = listChipCode;
	}
	public Long getFarmReportPeriodId() {
		return farmReportPeriodId;
	}
	public void setFarmReportPeriodId(Long farmReportPeriodId) {
		this.farmReportPeriodId = farmReportPeriodId;
	}
	public Integer getTotalGilts() {
//		totalGilts = 0;
//		if(maleGilts!=null) {
//			totalGilts+=maleGilts;
//		}
//		if(femaleGilts!=null) {
//			totalGilts+=femaleGilts;
//		}
		return totalGilts;
	}
	public void setTotalGilts(Integer totalGilts) {
		this.totalGilts = totalGilts;
	}
	public Integer getMaleGilts() {
		return maleGilts;
	}
	public void setMaleGilts(Integer maleGilts) {
		this.maleGilts = maleGilts;
	}
	public Integer getFemaleGilts() {
		return femaleGilts;
	}
	public void setFemaleGilts(Integer femaleGilts) {
		this.femaleGilts = femaleGilts;
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
	public AnimalReportDataFormDto() {
		this.totalNormal=0;
		
		this.totalParent=0;
		this.maleParent=0;//Số con con cái bố mẹ
		this.femaleParent=0;//Số con đực bố mẹ
		this.unGenderParent=0;//Số con không xác định giới tính bố mẹ
		
		this.totalChild=0;
		this.maleChild=0;//Số con con cái Con trên 1 tuổi
		this.femaleChild=0;//Số con đực Con trên 1 tuổi
		this.unGenderChild=0;//Số con không xác định giới tính Con trên 1 tuổi
		
		this.totalChildUnder1YO=0;
		this.maleChildUnder1YO=0;//Số con con cái Con dưới 1 tuổi
		this.femaleChildUnder1YO=0;//Số con đực Con dưới 1 tuổi
		this.unGenderChildUnder1YO=0;//Số con không xác định giới tính Con dưới 1 tuổi
		
		this.totalBearHasChip=0;// Tổng số gấu có chip
		this.maleBearHasChip=0;// Tổng số gấu đực có chip
		this.femaleBearHasChip=0;// số gấu cái có chíp
		
		this.totalBearNoChip=0;// tổng gấu không chip
		this.maleBearNoChip=0;// số gấu đực không chip
		this.femaleBearNoChip=0;// số gấu cái không chip
		
		this.reasonBornAtFarm=0;// Số gấu sinh ra tại trại
		this.reasonOther=0;// số gấu do nguyên nhân khác
		
		this.totalGilts=0;
		this.maleGilts=0;
		this.femaleGilts=0;
	}
}
