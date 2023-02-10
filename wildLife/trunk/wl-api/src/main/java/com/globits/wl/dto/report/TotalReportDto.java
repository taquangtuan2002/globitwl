package com.globits.wl.dto.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.FmsRegionDto;
import com.mysql.fabric.xmlrpc.base.Array;

public class TotalReportDto {
	private String title;
	private Date fromDate;
	private Date toDate;
	private List<TotalReportSubDto> listSub = new ArrayList<TotalReportSubDto>();
	private int totalQuantity;
	private double totalAmount;
	private int totalEggs;
	private List<FmsAdministrativeUnitDto> listAdministrativeUnit = new ArrayList<FmsAdministrativeUnitDto>();
	private List<FmsRegionDto> listRegion = new ArrayList<FmsRegionDto>();
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<TotalReportSubDto> getListSub() {
		return listSub;
	}

	public void setListSub(List<TotalReportSubDto> listSub) {
		this.listSub = listSub;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getTotalEggs() {
		return totalEggs;
	}

	public void setTotalEggs(int totalEggs) {
		this.totalEggs = totalEggs;
	}

	public List<FmsAdministrativeUnitDto> getListAdministrativeUnit() {
		return listAdministrativeUnit;
	}

	public void setListAdministrativeUnit(List<FmsAdministrativeUnitDto> listAdministrativeUnit) {
		this.listAdministrativeUnit = listAdministrativeUnit;
	}

	public List<FmsRegionDto> getListRegion() {
		return listRegion;
	}

	public void setListRegion(List<FmsRegionDto> listRegion) {
		this.listRegion = listRegion;
	}	
}
