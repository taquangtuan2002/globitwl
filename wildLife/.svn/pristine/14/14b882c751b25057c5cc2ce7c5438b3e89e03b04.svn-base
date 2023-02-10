package com.globits.wl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_farm_report_period")
public class FarmReportPeriod extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@Column(name="title")
	private String title;
	@Column(name="type")
	private int type;
	@Column(name="year")
	private Integer year;//Năm của kỳ báo cáo
	@Column(name="quarter")
	private Integer quarter;//Tháng của kỳ báo cáo
	@Column(name="month")
	private Integer month;//Tháng của kỳ báo cáo
	@Column(name="day")
	private Integer day;//Tháng của kỳ báo cáo
	public Farm getFarm() {
		return farm;
	}
	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getQuarter() {
		return quarter;
	}
	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	
	public FarmReportPeriod() {
		super();
	}
}
