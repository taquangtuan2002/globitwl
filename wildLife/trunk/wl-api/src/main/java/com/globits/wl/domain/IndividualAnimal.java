package com.globits.wl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

/**
 * Cá thể động vật hoang dã
 * DANGNH: 01/06/2020
 */
@Entity
@Table(name = "tbl_individual_animal")
@XmlRootElement
public class IndividualAnimal extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7648530670552941070L;
	/**
	 * Loại động vật gì
	 */
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;
	/**
	 * Phiếu nhập đàn của cá thể này
	 */
	@ManyToOne
	@JoinColumn(name="import_animal_id")
	private ImportExportAnimal importAnimal;
	/**
	 * Phiếu xuất đàn của cá thể này
	 */
	@ManyToOne
	@JoinColumn(name="export_animal_id")
	private ImportExportAnimal exportAnimal;
	
	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;
	
	/**
	 * Trạng thái:
	 * 0: bình thường
	 */
	@Column(name="status")
	private int status;
	
	/**
	 * Giới tính
	 */
	@Column(name="gender")
	private Integer gender;
	
	/**
	 * Ngày sinh
	 */
	@Column(name="birth_date")
	private Date birthDate;
	
	/**
	 * Ngày tuổi
	 */
	@Column(name="day_old")
	private Integer dayOld;
	
	@ManyToOne
	@JoinColumn(name="animal_report_data_id")
	private AnimalReportData animalReportData;
	@Column(name="additional_information")
	private String additionalInformation;
	@ManyToOne
	@JoinColumn(name="original_id")
	private Original original;
	@Column(name="individualAnimalStatus")
	private String individualAnimalStatus;
	
	public AnimalReportData getAnimalReportData() {
		return animalReportData;
	}

	public void setAnimalReportData(AnimalReportData animalReportData) {
		this.animalReportData = animalReportData;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public ImportExportAnimal getImportAnimal() {
		return importAnimal;
	}

	public void setImportAnimal(ImportExportAnimal importAnimal) {
		this.importAnimal = importAnimal;
	}

	public ImportExportAnimal getExportAnimal() {
		return exportAnimal;
	}

	public void setExportAnimal(ImportExportAnimal exportAnimal) {
		this.exportAnimal = exportAnimal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getDayOld() {
		return dayOld;
	}

	public void setDayOld(Integer dayOld) {
		this.dayOld = dayOld;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public Original getOriginal() {
		return original;
	}

	public void setOriginal(Original original) {
		this.original = original;
	}

	public String getIndividualAnimalStatus() {
		return individualAnimalStatus;
	}

	public void setIndividualAnimalStatus(String individualAnimalStatus) {
		this.individualAnimalStatus = individualAnimalStatus;
	}
	
}
