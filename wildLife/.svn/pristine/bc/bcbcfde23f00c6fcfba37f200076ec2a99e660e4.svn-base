package com.globits.wl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.wl.dto.ReportForm16Dto;

@Entity
@Table(name = "tbl_forest_products_list_detail")
@XmlRootElement
public class ForestProductsListDetail extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="forest_products_list_id")
	private ForestProductsList forestProductsList;//Thuộc bản kê lâm sản nào
	
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;//Loài nuôi
	
	@Column(name="quantity")
	private Double quantity;//Số lượng
	@Column(name="amount")
	private Double amount;//Khối lượng
	@Column(name="unit")
	private String unit;//Đơn vị tính
	@Column(name="note")
	private String note;//Ghi chú
	
	@Column(name="group_animal_type")
	private String groupAnimalType;//Nhóm loài
	@Column(name="code")
	private String code;//	Số hiệu nhãn đánh dấu (nếu có)
	
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
	private Integer maleChildOver1YearOld;// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
	@Column(name="female_chid_over_1_year_old")
	private Integer femaleChildOver1YearOld;// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
	@Column(name="ungender_child_over_1_year_old")
	private Integer unGenderChildOver1YearOld;// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
	
	// số lượng các loài trả về
		@Column(name="total_back")
		private Integer totalBack;// tổng
		
		@Column(name="male_back")
		private Integer maleBack;// đực
			
		@Column(name="female_back")
		private Integer femaleBack;// cái
		
		@Column(name="un_gender_back")
		private Integer unGenderBack;// không xác định
		@Column(name="male_parent_back")
		private Integer maleParentBack;// cá thể bố mẹ đực
		@Column(name="female_parent_back")
		private Integer femaleParentBack;// cá thể bố mẹ cái
		@Column(name="unGender_parent_back")
		private Integer unGenderParentBack;// cá thể bố mẹ không rõ
			
		@Column(name="male_gilts_back")
		private Integer maleGiltsBack;// hậu bị đực
		@Column(name="female_gilts_back")
		private Integer femaleGiltsBack;// hậu bị cái
		@Column(name="unGender_gilts_back")
		private Integer unGenderGiltsBack;// hậu bị không rõ
		
		@Column(name="male_child_under_1year_old_back")
		private Integer maleChildUnder1YearOldBack;// con dưới 1 tuổi đực
		@Column(name="female_child_under_1year_old_back")
		private Integer femaleChildUnder1YearOldBack;// con dưới 1 tuổi cái
		@Column(name="child_under_1_year_old_back")
		private Integer childUnder1YearOldBack;// con dưới 1 tuổi
			
		@Column(name="male_child_over_1_year_old_back")
		private Integer maleChildOver1YearOldBack;// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
		@Column(name="female_chid_over_1_year_old_back")
		private Integer femaleChildOver1YearOldBack;// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
		@Column(name="ungender_child_over_1_year_old_back")
		private Integer unGenderChildOver1YearOldBack;// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
	
	// mẫu 16 tương ứng
	@OneToOne
	@JoinColumn(name="report_form_16_id")
	private ReportForm16 reportForm16;
	
	@OneToOne
	@JoinColumn(name="report_form_16_back_id")
	private ReportForm16 reportForm16Back;
	
	@OneToOne
	@JoinColumn(name="report_form_16_old_id")
	private ReportForm16 reportForm16Old;
	
	
	
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
	public ReportForm16 getReportForm16Back() {
		return reportForm16Back;
	}
	public void setReportForm16Back(ReportForm16 reportForm16Back) {
		this.reportForm16Back = reportForm16Back;
	}
	public ForestProductsList getForestProductsList() {
		return forestProductsList;
	}
	public void setForestProductsList(ForestProductsList forestProductsList) {
		this.forestProductsList = forestProductsList;
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
	public ReportForm16 getReportForm16() {
		return reportForm16;
	}
	public void setReportForm16(ReportForm16 reportForm16) {
		this.reportForm16 = reportForm16;
	}
	public ReportForm16 getReportForm16Old() {
		return reportForm16Old;
	}
	public void setReportForm16Old(ReportForm16 reportForm16Old) {
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
