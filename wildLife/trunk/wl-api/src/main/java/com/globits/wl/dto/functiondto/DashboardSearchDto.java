package com.globits.wl.dto.functiondto;

import java.util.List;

import com.globits.wl.dto.report.InventoryReportDto;

public class DashboardSearchDto {
	private Long farmId;
	private String typeAnimalStr;
	private double totalAmountMeat;
	private int totalAmountEgg;
	
	private int fromMonthMeat;
	private int toMonthMeat;
	private int yearMeat;
	
	private int fromMonthEgg;
	private int toMonthEgg;
	private int yearEgg;
	
	private List<InventoryReportDto> listQuantityMeats;
	private List<InventoryReportDto> listExportEggs;

	public List<InventoryReportDto> getListQuantityMeats() {
		return listQuantityMeats;
	}

	public void setListQuantityMeats(List<InventoryReportDto> listQuantityMeats) {
		this.listQuantityMeats = listQuantityMeats;
	}

	public List<InventoryReportDto> getListExportEggs() {
		return listExportEggs;
	}

	public void setListExportEggs(List<InventoryReportDto> listExportEggs) {
		this.listExportEggs = listExportEggs;
	}

	public int getFromMonthMeat() {
		return fromMonthMeat;
	}

	public void setFromMonthMeat(int fromMonthMeat) {
		this.fromMonthMeat = fromMonthMeat;
	}

	public int getToMonthMeat() {
		return toMonthMeat;
	}

	public void setToMonthMeat(int toMonthMeat) {
		this.toMonthMeat = toMonthMeat;
	}

	public int getYearMeat() {
		return yearMeat;
	}

	public void setYearMeat(int yearMeat) {
		this.yearMeat = yearMeat;
	}

	public int getFromMonthEgg() {
		return fromMonthEgg;
	}

	public void setFromMonthEgg(int fromMonthEgg) {
		this.fromMonthEgg = fromMonthEgg;
	}

	public int getToMonthEgg() {
		return toMonthEgg;
	}

	public void setToMonthEgg(int toMonthEgg) {
		this.toMonthEgg = toMonthEgg;
	}

	public int getYearEgg() {
		return yearEgg;
	}

	public void setYearEgg(int yearEgg) {
		this.yearEgg = yearEgg;
	}

	public double getTotalAmountMeat() {
		return totalAmountMeat;
	}

	public void setTotalAmountMeat(double totalAmountMeat) {
		this.totalAmountMeat = totalAmountMeat;
	}

	public int getTotalAmountEgg() {
		return totalAmountEgg;
	}

	public void setTotalAmountEgg(int totalAmountEgg) {
		this.totalAmountEgg = totalAmountEgg;
	}

	public String getTypeAnimalStr() {
		return typeAnimalStr;
	}

	public void setTypeAnimalStr(String typeAnimalStr) {
		this.typeAnimalStr = typeAnimalStr;
	}

	public Long getFarmId() {
		return farmId;
	}

	public void setFarmId(Long farmId) {
		this.farmId = farmId;
	}
	
}
