package com.globits.wl.dto;

import java.util.Date;

import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.domain.InjectionTime;

public class InjectionPlantDto {
	private Long id;

	private Date injectionDate;// Ngày tiêm

	private String drug;// Loại thuốc

	private String injectionRound;// Đợt tiêm thuốc

	private ImportExportAnimalDto importAnimal;
	
	private InjectionTimeDto injectionTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInjectionDate() {
		return injectionDate;
	}

	public void setInjectionDate(Date injectionDate) {
		this.injectionDate = injectionDate;
	}

	public String getDrug() {
		return drug;
	}

	public void setDrug(String drug) {
		this.drug = drug;
	}

	public String getInjectionRound() {
		return injectionRound;
	}

	public void setInjectionRound(String injectionRound) {
		this.injectionRound = injectionRound;
	}

	public ImportExportAnimalDto getImportAnimal() {
		return importAnimal;
	}

	public void setImportAnimal(ImportExportAnimalDto importAnimal) {
		this.importAnimal = importAnimal;
	}

	public InjectionPlantDto() {
		super();
	}

	public InjectionTimeDto getInjectionTime() {
		return injectionTime;
	}

	public void setInjectionTime(InjectionTimeDto injectionTime) {
		this.injectionTime = injectionTime;
	}

	public InjectionPlantDto(InjectionPlant entity) {
		super();
		if (entity != null) {
			this.id = entity.getId();
			if (entity.getInjectionDate() != null) {
				this.injectionDate = entity.getInjectionDate();
			}
			this.drug = entity.getDrug();
			this.injectionRound = entity.getInjectionRound();
			if(entity.getImportAnimal()!=null){
				ImportExportAnimalDto dto=new ImportExportAnimalDto();
				dto.setId(entity.getImportAnimal().getId());
				dto.setBatchCode(entity.getImportAnimal().getBatchCode());
				if(entity.getImportAnimal().getFarm()!=null)
				dto.setFarm(new FarmDto(entity.getImportAnimal().getFarm(), true));
			}
			if(entity.getInjectionTime()!=null){
				injectionTime = new InjectionTimeDto();
				injectionTime.setId(entity.getInjectionTime().getId());
				injectionTime.setName(entity.getInjectionTime().getName());
				injectionTime.setType(entity.getInjectionTime().getType());
			}
		}
	}

}
