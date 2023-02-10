package com.globits.wl.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.ForestProductsListDetail;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;

public class ForestProductsListDetailDto extends BaseObjectDto{

	private ForestProductsListDto forestProductsList;//Thuộc bản kê lâm sản nào
	private AnimalDto animal;//Loài nuôi
	private Double quantity;//Số lượng
	private Double amount;//Khối lượng
	private String unit;//Đơn vị tính
	private String note;//Ghi chú
	private String groupAnimalType;//Nhóm loài
	private String code;//	Số hiệu nhãn đánh dấu (nếu có)
	
	private Integer total;// tổng
	private Integer male;// đực
	private Integer female;// cái
	private Integer unGender;
	
	private Integer maleParent;// cá thể bố mẹ đực
	private Integer femaleParent;// cá thể bố mẹ cái
	private Integer unGenderParent;// cá thể bố mẹ không rõ  // thêm
	
	private Integer maleGilts;// hậu bị đực
	private Integer femaleGilts;// hậu bị cái
	private Integer unGenderGilts;// hậu bị không rõ  // thêm
	
	private Integer maleChildUnder1YearOld;// con dưới 1 tuổi đực // thêm
	private Integer femaleChildUnder1YearOld;// con dưới 1 tuổi cái // thêm
	private Integer childUnder1YearOld;// con dưới 1 tuổi
	
	private Integer maleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
	private Integer femaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
	private Integer unGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
	
	
	// số lượng các loài trả về
			
	private Integer totalBack;// tổng
			
	private Integer maleBack;// đực
				
	private Integer femaleBack;// cái
			
	private Integer unGenderBack;// không xác định
	private Integer maleParentBack;// cá thể bố mẹ đực
	private Integer femaleParentBack;// cá thể bố mẹ cái
	private Integer unGenderParentBack;// cá thể bố mẹ không rõ
	private Integer maleGiltsBack;// hậu bị đực
	private Integer femaleGiltsBack;// hậu bị cái
	private Integer unGenderGiltsBack;// hậu bị không rõ
	private Integer maleChildUnder1YearOldBack;// con dưới 1 tuổi đực
	private Integer femaleChildUnder1YearOldBack;// con dưới 1 tuổi cái
	private Integer childUnder1YearOldBack;// con dưới 1 tuổi
	private Integer maleChildOver1YearOldBack;// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
	private Integer femaleChildOver1YearOldBack;// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
	private Integer unGenderChildOver1YearOldBack;// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
	
	private ReportForm16Dto reportForm16;
	
	private ReportForm16Dto reportForm16Old;
	
	private ReportForm16Dto reportForm16Back;
	
	public ForestProductsListDetailDto() {
		super();
	}
	
	public ForestProductsListDetailDto(ForestProductsListDetail entity) {
		this.id = entity.getId();
		if(entity.getForestProductsList() != null && entity.getForestProductsList().getId() != null) {
			this.forestProductsList = new ForestProductsListDto();
			this.forestProductsList.setId(entity.getForestProductsList().getId());
		}
		if(entity.getAnimal() !=  null) {
			this.animal = new AnimalDto(entity.getAnimal());
		}
		this.quantity = entity.getQuantity();
		this.amount = entity.getAmount();
		this.unit = entity.getUnit();
		this.note = entity.getNote();
		this.groupAnimalType = entity.getGroupAnimalType();
		this.code = entity.getCode();
		
		this.total= entity.getTotal() ;
		this.male= entity.getMale() ;
		this.female= entity.getFemale() ;
		this.unGender = entity.getUnGender();
		
		this.maleParent= entity.getMaleParent() ;
		this.femaleParent= entity.getFemaleParent();
		this.unGenderParent = entity.getUnGenderParent();
		
		this.maleGilts= entity.getMaleGilts() ;
		this.femaleGilts= entity.getFemaleGilts() ;
		this.unGenderGilts = entity.getUnGenderGilts();
		
		this.maleChildUnder1YearOld = entity.getMaleChildUnder1YearOld();
		this.femaleChildUnder1YearOld = entity.getFemaleChildUnder1YearOld();
		this.childUnder1YearOld= entity.getChildUnder1YearOld() ;
		
		this.maleChildOver1YearOld= entity.getMaleChildOver1YearOld() ;
		this.femaleChildOver1YearOld= entity.getFemaleChildOver1YearOld() ;
		this.unGenderChildOver1YearOld= entity.getUnGenderChildOver1YearOld() ;
		
		this.totalBack= entity.getTotalBack() ;
		this.maleBack= entity.getMaleBack() ;
		this.femaleBack= entity.getFemaleBack() ;
		this.unGenderBack = entity.getUnGenderBack();
		
		this.maleParentBack= entity.getMaleParentBack() ;
		this.femaleParentBack= entity.getFemaleParentBack();
		this.unGenderParentBack = entity.getUnGenderParentBack();
		
		this.maleGiltsBack= entity.getMaleGiltsBack() ;
		this.femaleGiltsBack= entity.getFemaleGiltsBack() ;
		this.unGenderGiltsBack = entity.getUnGenderGiltsBack();
		
		this.maleChildUnder1YearOldBack = entity.getMaleChildUnder1YearOldBack();
		this.femaleChildUnder1YearOldBack = entity.getFemaleChildUnder1YearOldBack();
		this.childUnder1YearOldBack= entity.getChildUnder1YearOldBack() ;
		
		this.maleChildOver1YearOldBack= entity.getMaleChildOver1YearOldBack() ;
		this.femaleChildOver1YearOldBack= entity.getFemaleChildOver1YearOldBack() ;
		this.unGenderChildOver1YearOldBack= entity.getUnGenderChildOver1YearOldBack() ;
		
		if(entity.getReportForm16() != null) {
			this.reportForm16 = new ReportForm16Dto(entity.getReportForm16());
		}
		
		if(entity.getReportForm16Old() != null) {
			this.reportForm16Old = new ReportForm16Dto(entity.getReportForm16Old());
		}
		
		if(entity.getReportForm16Back()!=null) {
			this.reportForm16Back= new ReportForm16Dto(entity.getReportForm16Back());
		}
	}
	
	public Integer getTotalBack() {
		return totalBack;
	}

	public void setTotalBack(Integer totalBack) {
		this.totalBack = totalBack;
	}

	public Integer getMaleBack() {
		return maleBack;
	}

	public void setMaleBack(Integer maleBack) {
		this.maleBack = maleBack;
	}

	public Integer getFemaleBack() {
		return femaleBack;
	}

	public void setFemaleBack(Integer femaleBack) {
		this.femaleBack = femaleBack;
	}

	public Integer getUnGenderBack() {
		return unGenderBack;
	}

	public void setUnGenderBack(Integer unGenderBack) {
		this.unGenderBack = unGenderBack;
	}

	public Integer getMaleParentBack() {
		return maleParentBack;
	}

	public void setMaleParentBack(Integer maleParentBack) {
		this.maleParentBack = maleParentBack;
	}

	public Integer getFemaleParentBack() {
		return femaleParentBack;
	}

	public void setFemaleParentBack(Integer femaleParentBack) {
		this.femaleParentBack = femaleParentBack;
	}

	public Integer getUnGenderParentBack() {
		return unGenderParentBack;
	}

	public void setUnGenderParentBack(Integer unGenderParentBack) {
		this.unGenderParentBack = unGenderParentBack;
	}

	public Integer getMaleGiltsBack() {
		return maleGiltsBack;
	}

	public void setMaleGiltsBack(Integer maleGiltsBack) {
		this.maleGiltsBack = maleGiltsBack;
	}

	public Integer getFemaleGiltsBack() {
		return femaleGiltsBack;
	}

	public void setFemaleGiltsBack(Integer femaleGiltsBack) {
		this.femaleGiltsBack = femaleGiltsBack;
	}

	public Integer getUnGenderGiltsBack() {
		return unGenderGiltsBack;
	}

	public void setUnGenderGiltsBack(Integer unGenderGiltsBack) {
		this.unGenderGiltsBack = unGenderGiltsBack;
	}

	public Integer getMaleChildUnder1YearOldBack() {
		return maleChildUnder1YearOldBack;
	}

	public void setMaleChildUnder1YearOldBack(Integer maleChildUnder1YearOldBack) {
		this.maleChildUnder1YearOldBack = maleChildUnder1YearOldBack;
	}

	public Integer getFemaleChildUnder1YearOldBack() {
		return femaleChildUnder1YearOldBack;
	}

	public void setFemaleChildUnder1YearOldBack(Integer femaleChildUnder1YearOldBack) {
		this.femaleChildUnder1YearOldBack = femaleChildUnder1YearOldBack;
	}

	public Integer getChildUnder1YearOldBack() {
		return childUnder1YearOldBack;
	}

	public void setChildUnder1YearOldBack(Integer childUnder1YearOldBack) {
		this.childUnder1YearOldBack = childUnder1YearOldBack;
	}

	public Integer getMaleChildOver1YearOldBack() {
		return maleChildOver1YearOldBack;
	}

	public void setMaleChildOver1YearOldBack(Integer maleChildOver1YearOldBack) {
		this.maleChildOver1YearOldBack = maleChildOver1YearOldBack;
	}

	public Integer getFemaleChildOver1YearOldBack() {
		return femaleChildOver1YearOldBack;
	}

	public void setFemaleChildOver1YearOldBack(Integer femaleChildOver1YearOldBack) {
		this.femaleChildOver1YearOldBack = femaleChildOver1YearOldBack;
	}

	public Integer getUnGenderChildOver1YearOldBack() {
		return unGenderChildOver1YearOldBack;
	}

	public void setUnGenderChildOver1YearOldBack(Integer unGenderChildOver1YearOldBack) {
		this.unGenderChildOver1YearOldBack = unGenderChildOver1YearOldBack;
	}

	public ReportForm16Dto getReportForm16Back() {
		return reportForm16Back;
	}

	public void setReportForm16Back(ReportForm16Dto reportForm16Back) {
		this.reportForm16Back = reportForm16Back;
	}

	public ForestProductsListDto getForestProductsList() {
		return forestProductsList;
	}

	public void setForestProductsList(ForestProductsListDto forestProductsList) {
		this.forestProductsList = forestProductsList;
	}

	public AnimalDto getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGroupAnimalType() {
		return groupAnimalType;
	}

	public void setGroupAnimalType(String groupAnimalType) {
		this.groupAnimalType = groupAnimalType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Integer getChildUnder1YearOld() {
		return childUnder1YearOld;
	}

	public void setChildUnder1YearOld(Integer childUnder1YearOld) {
		this.childUnder1YearOld = childUnder1YearOld;
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

	public ReportForm16Dto getReportForm16() {
		return reportForm16;
	}

	public void setReportForm16(ReportForm16Dto reportForm16) {
		this.reportForm16 = reportForm16;
	}

	public ReportForm16Dto getReportForm16Old() {
		return reportForm16Old;
	}

	public void setReportForm16Old(ReportForm16Dto reportForm16Old) {
		this.reportForm16Old = reportForm16Old;
	}

	public Integer getUnGender() {
		return unGender;
	}

	public void setUnGender(Integer unGender) {
		this.unGender = unGender;
	}

	public Integer getUnGenderParent() {
		return unGenderParent;
	}

	public void setUnGenderParent(Integer unGenderParent) {
		this.unGenderParent = unGenderParent;
	}

	public Integer getUnGenderGilts() {
		return unGenderGilts;
	}

	public void setUnGenderGilts(Integer unGenderGilts) {
		this.unGenderGilts = unGenderGilts;
	}

	public Integer getMaleChildUnder1YearOld() {
		return maleChildUnder1YearOld;
	}

	public void setMaleChildUnder1YearOld(Integer maleChildUnder1YearOld) {
		this.maleChildUnder1YearOld = maleChildUnder1YearOld;
	}

	public Integer getFemaleChildUnder1YearOld() {
		return femaleChildUnder1YearOld;
	}

	public void setFemaleChildUnder1YearOld(Integer femaleChildUnder1YearOld) {
		this.femaleChildUnder1YearOld = femaleChildUnder1YearOld;
	}
	
}
