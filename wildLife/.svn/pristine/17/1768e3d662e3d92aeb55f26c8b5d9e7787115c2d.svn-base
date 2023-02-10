package com.globits.wl.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

/*
 * Kỳ báo cáo
 */
@Entity
@Table(name = "tbl_report_period")
@XmlRootElement
public class ReportPeriod extends BaseObject {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "farm_id")
	private Farm farm;
	
	private Integer year;
	
	private Integer month;
	
	private Integer date;

	private Integer type;// Kiểu form A: 1; B: 2

	@OneToMany(mappedBy = "reportPeriod", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<ReportForm16> reportItems = new HashSet<ReportForm16>();

	@ManyToOne
	@JoinColumn(name="administrative_unit_id")
	private FmsAdministrativeUnit administrativeUnit;

	@ManyToOne
	@JoinColumn(name="district_id")
	private FmsAdministrativeUnit district;
	
	@ManyToOne
	@JoinColumn(name="province_id")
	private FmsAdministrativeUnit province;
	
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Set<ReportForm16> getReportItems() {
		return reportItems;
	}

	public void setReportItems(Set<ReportForm16> reportItems) {
		this.reportItems = reportItems;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public FmsAdministrativeUnit getAdministrativeUnit() {
		return administrativeUnit;
	}

	public void setAdministrativeUnit(FmsAdministrativeUnit administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}

	public FmsAdministrativeUnit getDistrict() {
		return district;
	}

	public void setDistrict(FmsAdministrativeUnit district) {
		this.district = district;
	}

	public FmsAdministrativeUnit getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnit province) {
		this.province = province;
	}
	
}
