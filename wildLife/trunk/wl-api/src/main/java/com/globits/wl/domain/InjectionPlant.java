package com.globits.wl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.wl.dto.ImportExportAnimalDto;
@Entity
@Table(name = "tbl_injection_plant")
@XmlRootElement
/*
 * Kế hoạch tiêm chủng vật nuôi
 */
public class InjectionPlant extends BaseObject{
	
	@Column(name="injection_date")
	private Date injectionDate;//Ngày tiêm
	
	@Column(name="drug")
	private String drug;//Loại thuốc
	
	@Column(name="injection_round")
	private String injectionRound;//Đợt tiêm thuốc
	
	@ManyToOne
	@JoinColumn(name = "import_animal_id")
	private ImportExportAnimal importAnimal;
	
	@ManyToOne
	@JoinColumn(name = "injection_time_id")
	private InjectionTime injectionTime;//Đợt tiêm

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

	public ImportExportAnimal getImportAnimal() {
		return importAnimal;
	}

	public void setImportAnimal(ImportExportAnimal importAnimal) {
		this.importAnimal = importAnimal;
	}

	public InjectionTime getInjectionTime() {
		return injectionTime;
	}

	public void setInjectionTime(InjectionTime injectionTime) {
		this.injectionTime = injectionTime;
	}
	
	
	
}
