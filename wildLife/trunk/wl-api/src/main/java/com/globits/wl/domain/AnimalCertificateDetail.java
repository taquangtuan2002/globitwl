package com.globits.wl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_animal_certificate_detail")
@XmlRootElement
public class AnimalCertificateDetail extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="animal_certificate_id")
	private AnimalCertificate animalCertificate;
	
	
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;//Loài nuôi
	
	@Column(name="quantity")
	private Double quantity;//Số lượng
	
	@Column(name="amount")
	private Double amount;//Trọng lượng
	
	@Column(name="unit")
	private String unit;//Đơn vị tính
	
	@Column(name="note")
	private String note;//Ghi chú
	
	@Column(name="mark_specification")
	private String markSpecification; // quy cách đánh dấu
	
	@JoinColumn(name="original")
	private String original; // nguồn gốc
	
	// số lượng các loài
	@Column(name="total")
	private Integer total;// tổng
		
	@Column(name="male")
	private Integer male;// đực
	
	@Column(name="female")
	private Integer female;// cái
	
	@Column(name="un_gender")
	private Integer unGender;// không xác định
	@Column(name="male_parent")
	private Integer maleParent;// cá thể bố mẹ đực
	@Column(name="female_parent")
	private Integer femaleParent;// cá thể bố mẹ cái
	@Column(name="unGender_parent")
	private Integer unGenderParent;// cá thể bố mẹ không rõ
		
	@Column(name="male_gilts")
	private Integer maleGilts;// hậu bị đực
	@Column(name="female_gilts")
	private Integer femaleGilts;// hậu bị cái
	@Column(name="unGender_gilts")
	private Integer unGenderGilts;// hậu bị không rõ
	
	@Column(name="male_child_under_1year_old")
	private Integer maleChildUnder1YearOld;// con dưới 1 tuổi đực
	@Column(name="female_child_under_1year_old")
	private Integer femaleChildUnder1YearOld;// con dưới 1 tuổi cái
	@Column(name="child_under_1_year_old")
	private Integer childUnder1YearOld;// con dưới 1 tuổi
		
	@Column(name="male_child_over_1_year_old")
	private Integer maleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực 
	@Column(name="female_chid_over_1_year_old")
	private Integer femaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái 
	@Column(name="ungender_child_over_1_year_old")
	private Integer unGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính
	
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

	public Integer getUnGender() {
		return unGender;
	}

	public void setUnGender(Integer unGender) {
		this.unGender = unGender;
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

	public AnimalCertificate getAnimalCertificate() {
		return animalCertificate;
	}

	public void setAnimalCertificate(AnimalCertificate animalCertificate) {
		this.animalCertificate = animalCertificate;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
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
	
}
