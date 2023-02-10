package com.globits.wl.dto;

import java.util.Date;

import com.globits.core.dto.BaseObjectDto;
import com.globits.wl.domain.ReportFormAnimalGiveBirth;

public class ReportFormAnimalGiveBirthDto extends BaseObjectDto {
	private FarmDto farm;

	private AnimalDto animal;

	private Date dateReport;

	private Integer parentMale;// Số con bố mẹ: đực

	private Integer parentFemale;// Số bố mẹ : cái

	private Integer quantityChildHatch;// Số con non nở

	private Integer quantityChildDie;// Số con non chết

	private Integer quantityChildLive;// Số con non còn sống

	private Integer quantityChildIncrement;// Số con non cộng dồn theo thời gian

	private Integer quantityChildSeparateCaptivity;// Số con rời khỏi khu nuôi nhốt

	private Integer remainQuantity;// Số con còn lại

	private String note;// ghi chú
	
	private Boolean hasEditQuantityChildIncrement = false;

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

	public Date getDateReport() {
		return dateReport;
	} 

	public void setDateReport(Date dateReport) {
		this.dateReport = dateReport;
	}

	public Integer getParentMale() {
		return parentMale;
	}

	public void setParentMale(Integer parentMale) {
		this.parentMale = parentMale;
	}

	public Integer getParentFemale() {
		return parentFemale;
	}

	public void setParentFemale(Integer parentFemale) {
		this.parentFemale = parentFemale;
	}

	public Integer getQuantityChildHatch() {
		return quantityChildHatch;
	}

	public void setQuantityChildHatch(Integer quantityChildHatch) {
		this.quantityChildHatch = quantityChildHatch;
	}

	public Integer getQuantityChildDie() {
		return quantityChildDie;
	}

	public void setQuantityChildDie(Integer quantityChildDie) {
		this.quantityChildDie = quantityChildDie;
	}

	public Integer getQuantityChildLive() {
		return quantityChildLive;
	}

	public void setQuantityChildLive(Integer quantityChildLive) {
		this.quantityChildLive = quantityChildLive;
	}

	public Integer getQuantityChildIncrement() {
		return quantityChildIncrement;
	}

	public void setQuantityChildIncrement(Integer quantityChildIncrement) {
		this.quantityChildIncrement = quantityChildIncrement;
	}

	public Integer getQuantityChildSeparateCaptivity() {
		return quantityChildSeparateCaptivity;
	}

	public void setQuantityChildSeparateCaptivity(Integer quantityChildSeparateCaptivity) {
		this.quantityChildSeparateCaptivity = quantityChildSeparateCaptivity;
	}

	public Integer getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(Integer remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getHasEditQuantityChildIncrement() {
		return hasEditQuantityChildIncrement;
	}

	public void setHasEditQuantityChildIncrement(Boolean hasEditQuantityChildIncrement) {
		this.hasEditQuantityChildIncrement = hasEditQuantityChildIncrement;
	}

	public ReportFormAnimalGiveBirthDto() {
		super();
	}

	public ReportFormAnimalGiveBirthDto(ReportFormAnimalGiveBirth entity) {
		super();
		this.id = entity.getId();
		if(entity.getFarm() != null) {
			this.farm = new FarmDto(entity.getFarm(), 1);
		}
		if(entity.getAnimal() != null) {
			this.animal = new AnimalDto(entity.getAnimal());
		}
		this.dateReport = entity.getDateReport();
		this.parentFemale = entity.getParentFemale();
		this.parentMale = entity.getParentMale();
		this.quantityChildHatch = entity.getQuantityChildHatch();
		this.quantityChildDie = entity.getQuantityChildDie();
		this.quantityChildLive = entity.getQuantityChildLive();
		this.quantityChildIncrement = entity.getQuantityChildIncrement();
		this.quantityChildSeparateCaptivity = entity.getQuantityChildSeparateCaptivity();
		this.remainQuantity = entity.getRemainQuantity();
		this.note = entity.getNote();
	}
}
