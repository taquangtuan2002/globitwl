package com.globits.wl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_report_animal_give_birth")
@XmlRootElement
public class ReportFormAnimalGiveBirth extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	@ManyToOne
	@JoinColumn(name="report_animal_give_birth_period_id")
	private ReportFormAnimalGiveBirthPeriod reportFormAnimalGiveBirthPeriod;
	
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;
	
	@Column(name="date_report")
	private Date dateReport;
	
	@Column(name="parent_male")
	private Integer parentMale;// Số con bố mẹ: đực
	
	@Column(name="parent_female")
	private Integer parentFemale;// Số bố mẹ : cái
	
	@Column(name="quantity_child_hatch")
	private Integer quantityChildHatch;// Số con non nở
	
	@Column(name="quantityChildDie")
	private Integer quantityChildDie;// Số con non chết
	
	@Column(name="quantity_child_live")
	private Integer quantityChildLive;// Số con non còn sống
	
	@Column(name="quantity_child_increment")
	private Integer quantityChildIncrement;// Số con non cộng dồn theo thời gian
	
	@Column(name="quantity_child_separate_captivity")
	private Integer quantityChildSeparateCaptivity;//Số con rời khỏi khu nuôi nhốt
	
	@Column(name="remain_quantity")
	private Integer remainQuantity;// Số con còn lại
	
	@Column(name="note")
	private String note;// ghi chú
//	@Column(name="")
//	private String xacNhanCoQuanKiemLam;// Xác nhận của cơ quan kiểm lâm/ thủy sản

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
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

	public ReportFormAnimalGiveBirthPeriod getReportFormAnimalGiveBirthPeriod() {
		return reportFormAnimalGiveBirthPeriod;
	}

	public void setReportFormAnimalGiveBirthPeriod(ReportFormAnimalGiveBirthPeriod reportFormAnimalGiveBirthPeriod) {
		this.reportFormAnimalGiveBirthPeriod = reportFormAnimalGiveBirthPeriod;
	}
	
}
