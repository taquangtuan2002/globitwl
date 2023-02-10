package com.globits.wl.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_report_form_animal_egg_period")
@XmlRootElement
public class ReportFormAnimalEggPeriod extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@Column(name="year")
	private Integer year;
	
	@Column(name="month")
	private Integer month;
	
	@Column(name="date")
	private Integer date;
	
	@OneToMany(mappedBy = "reportFormAnimalEggPeriod", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ReportFormAnimalEgg>  reportFormAnimalEggs = new HashSet<ReportFormAnimalEgg>();

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Set<ReportFormAnimalEgg> getReportFormAnimalEggs() {
		return reportFormAnimalEggs;
	}

	public void setReportFormAnimalEggs(Set<ReportFormAnimalEgg> reportFormAnimalEggs) {
		this.reportFormAnimalEggs = reportFormAnimalEggs;
	}
	
}
