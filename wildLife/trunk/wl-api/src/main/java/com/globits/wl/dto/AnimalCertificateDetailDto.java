package com.globits.wl.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.Certificate;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.AnimalCertificateDetail;

public class AnimalCertificateDetailDto extends BaseObjectDto{
	
	private AnimalCertificateDto animalCertificate;
	
	private AnimalDto animal;//Loài nuôi
	
	private Double quantity;//Số lượng
	
	private Double amount;//Trọng lượng
	
	private String unit;//Đơn vị tính
	
	private String note;//Ghi chú
	
	private String markSpecification; // quy cách đánh dấu
	
	private String original; // nguồn gốc 

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
	
	private ReportForm16 reportForm16Old;
	
	
	public ReportForm16 getReportForm16Old() {
		return reportForm16Old;
	}
	public void setReportForm16Old(ReportForm16 reportForm16Old) {
		this.reportForm16Old = reportForm16Old;
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public AnimalCertificateDto getAnimalCertificate() {
		return animalCertificate;
	}
	public void setAnimalCertificate(AnimalCertificateDto animalCertificate) {
		this.animalCertificate = animalCertificate;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getMarkSpecification() {
		return markSpecification;
	}
	public void setMarkSpecification(String markSpecification) {
		this.markSpecification = markSpecification;
	}
	public String getOriginal() {
		return original;
	}
	public void setOriginal(String original) {
		this.original = original;
	}
	public AnimalCertificateDetailDto() {
		super();
	}
	public AnimalCertificateDetailDto(AnimalCertificateDetail entity) {
		this.id = entity.getId();
		if(entity.getAnimalCertificate()!=null){
			this.animalCertificate= new AnimalCertificateDto();
			this.animalCertificate.setId(entity.getAnimalCertificate().getId());
		}
		if(entity.getAnimal()!=null){
			this.animal= new AnimalDto(entity.getAnimal());
		}
		if(entity.getQuantity() !=null) {
			this.quantity = entity.getQuantity();
		}
		if(entity.getAmount() !=null) {
			this.amount = entity.getAmount();
		}
		if(entity.getNote() !=null) {
			this.note = entity.getNote();
		}
		if(entity.getMarkSpecification() !=null) {
			this.markSpecification = entity.getMarkSpecification();
		}
		if(entity.getOriginal() !=null) {
			this.original = entity.getOriginal();
		}
		if(entity.getTotal() !=null) {
			this.total = entity.getTotal();
		}
		if(entity.getMale() !=null) {
			this.male = entity.getMale();
		}
		if(entity.getFemale() !=null) {
			this.female = entity.getFemale();
		}
		if(entity.getUnGender() !=null) {
			this.unGender = entity.getUnGender();
		}
		if(entity.getUnit() !=null) {
			this.unit = entity.getUnit();
		}
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
	}
}
