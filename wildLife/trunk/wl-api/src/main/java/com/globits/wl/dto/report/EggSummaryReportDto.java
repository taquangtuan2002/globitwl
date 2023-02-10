package com.globits.wl.dto.report;

import java.util.List;

public class EggSummaryReportDto extends InventoryReportDto {
	
	private double totalEggCount;//tổng con đẻ trứng
	private List<ObjectReportDto> reportCoes;
	private List<ObjectReportDto> eggTypes;
	private List<String> eggTypeNames;
	private List<EggReportDto> eggs;//danh sách trứng
	
	public double getTotalEggCount() {
		return totalEggCount;
	}
	public void setTotalEggCount(double totalEggCount) {
		this.totalEggCount = totalEggCount;
	}
	
	public List<ObjectReportDto> getReportCoes() {
		return reportCoes;
	}
	public void setReportCoes(List<ObjectReportDto> reportCoes) {
		this.reportCoes = reportCoes;
	}
	public List<ObjectReportDto> getEggTypes() {
		return eggTypes;
	}
	public void setEggTypes(List<ObjectReportDto> eggTypes) {
		this.eggTypes = eggTypes;
	}
	public List<EggReportDto> getEggs() {
		return eggs;
	}
	public void setEggs(List<EggReportDto> eggs) {
		this.eggs = eggs;
	}
	public List<String> getEggTypeNames() {
		return eggTypeNames;
	}
	public void setEggTypeNames(List<String> eggTypeNames) {
		this.eggTypeNames = eggTypeNames;
	}		
}
