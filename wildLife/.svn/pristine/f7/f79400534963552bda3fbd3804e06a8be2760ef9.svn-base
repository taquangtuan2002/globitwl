package com.globits.wl.dto;

import java.util.Date;

import com.globits.wl.domain.IndividualAnimal;
/**Cá thể động vật hoang dã*/
public class IndividualAnimalDto {
	private Long id;
	private AnimalDto animal;
	/**
	 * Phiếu nhập đàn của cá thể này
	 */
	private ImportExportAnimalDto importAnimal;
	/**
	 * Phiếu xuất đàn của cá thể này
	 */
	private ImportExportAnimalDto exportAnimal;
	private String name;
	private String code;
	/**
	 * Trạng thái:
	 * 0: bình thường
	 */
	private int status;
	private Integer gender;
	/**
	 * Ngày sinh
	 */
	private Date birthDate;
	/**
	 * Ngày tuổi
	 */
	private Integer dayOld;
	
	private String additionalInformation;
	
	private OriginalDto original;
	private String individualAnimalStatus;
	
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AnimalDto getAnimal() {
		return animal;
	}
	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}
	public ImportExportAnimalDto getImportAnimal() {
		return importAnimal;
	}
	public void setImportAnimal(ImportExportAnimalDto importAnimal) {
		this.importAnimal = importAnimal;
	}
	public ImportExportAnimalDto getExportAnimal() {
		return exportAnimal;
	}
	public void setExportAnimal(ImportExportAnimalDto exportAnimal) {
		this.exportAnimal = exportAnimal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public OriginalDto getOriginal() {
		return original;
	}
	public void setOriginal(OriginalDto original) {
		this.original = original;
	}
	public String getIndividualAnimalStatus() {
		return individualAnimalStatus;
	}
	public void setIndividualAnimalStatus(String individualAnimalStatus) {
		this.individualAnimalStatus = individualAnimalStatus;
	}
	public IndividualAnimalDto() {
		super();
	}
	
	public IndividualAnimalDto(IndividualAnimal entity) {
		super();
		this.id = entity.getId();
		if(entity.getAnimal() != null) {
			this.animal = new AnimalDto();
			this.animal.setId(entity.getAnimal().getId());
		}
		this.name = entity.getName();
		this.code = entity.getCode();
		if(entity.getImportAnimal() != null) {
			this.importAnimal = new ImportExportAnimalDto();
			this.importAnimal.setId(entity.getImportAnimal().getId());
		}
		if(entity.getExportAnimal() != null) {
			this.exportAnimal = new ImportExportAnimalDto();
			this.exportAnimal.setId(entity.getExportAnimal().getId());
		}
		this.status = entity.getStatus();
		this.gender = entity.getGender();
		this.birthDate = entity.getBirthDate();
		this.dayOld = entity.getDayOld();
		this.individualAnimalStatus = entity.getIndividualAnimalStatus();
		this.additionalInformation = entity.getAdditionalInformation();
		if(entity.getOriginal() != null) {
			this.original = new OriginalDto(entity.getOriginal());
		}
	}
	
	
}
