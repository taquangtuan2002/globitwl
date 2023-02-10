package com.globits.wl.dto.report;

import java.util.Date;

import com.globits.wl.dto.AnimalDto;

public class Report16FormDto {
	private Date dateReport;
	private AnimalDto animalDto;
	
	private Long animalId;
	private String animalName;
	private String scienceName;
	
	//Tổng số cá thể nuôi
	private Long total=0L;
	private Long totalMale=0L;
	private Long totalFeMale=0L;
	private Long totalUnGen=0L;
	
	//Số con dưới 1 tuổi
	private Long totalUnder1YO=0L;
	private Long under1YOMale=0L;
	private Long under1YOFeMale=0L;
	private Long under1YOUnGen=0L;
	
	//Số con bố mẹ
	private Long totalParent=0L;
	private Long parentMale=0L;
	private Long parentFeMale=0L;
	private Long parentUnGen=0L;
	
	//Số con trên 1 tuổi
	private Long totalOver1YO=0L;
	private Long over1YOMale=0L;
	private Long over1YOFeMale=0L;
	private Long over1YOUnGen=0L;
	
	//Con hậu bị
	private Long totalGilts=0L;
	private Long giltsMale=0L;
	private Long giltsFeMale=0L;
	private Long giltsUnGen=0L;
	
	//Tổng số con xuất
	private Long totalExport=0L;
	private Long exportMale=0L;
	private Long exportFeMale=0L;
	private Long exportUnGen=0L;
	
	//Tổng số con nhập
	private Long totalImport=0L;
	private Long importMale=0L;
	private Long importFeMale=0L;
	private Long importUnGen=0L;
	
	public Date getDateReport() {
		return dateReport;
	}
	public void setDateReport(Date dateReport) {
		this.dateReport = dateReport;
	}
	public AnimalDto getAnimalDto() {
		return animalDto;
	}
	public void setAnimalDto(AnimalDto animalDto) {
		this.animalDto = animalDto;
	}
	public String getAnimalName() {
		return animalName;
	}
	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}
	public String getScienceName() {
		return scienceName;
	}
	public void setScienceName(String scienceName) {
		this.scienceName = scienceName;
	}
	public Long getAnimalId() {
		return animalId;
	}
	public void setAnimalId(Long animalId) {
		this.animalId = animalId;
	}
	public Long getTotal() {
		total = totalParent + totalGilts+totalUnder1YO+totalOver1YO + importFeMale + importMale + importUnGen -exportFeMale-exportMale-exportUnGen;
		return total;
	}
//	public void setTotal(Long total) {
//		this.total = total;
//	}
	public Long getTotalMale() {
		totalMale = parentMale + giltsMale + over1YOMale + importMale - exportMale;
		return totalMale;
	}
//	public void setTotalMale(Long totalMale) {
//		this.totalMale = totalMale;
//	}
	public Long getTotalFeMale() {
		totalFeMale = parentFeMale+giltsFeMale+over1YOFeMale + importFeMale - exportFeMale;
		return totalFeMale;
	}
//	public void setTotalFeMale(Long totalFeMale) {
//		this.totalFeMale = totalFeMale;
//	}
	public Long getTotalUnGen() {
		totalUnGen = parentUnGen+giltsUnGen+over1YOUnGen + importUnGen - exportUnGen;
		return totalUnGen;
	}
//	public void setTotalUnGen(Long totalUnGen) {
//		this.totalUnGen = totalUnGen;
//	}
	public Long getTotalUnder1YO() {
		return totalUnder1YO;
	}
	public void setTotalUnder1YO(Long totalUnder1YO) {
		this.totalUnder1YO = totalUnder1YO;
	}
	public Long getUnder1YOMale() {
		return under1YOMale;
	}
	public void setUnder1YOMale(Long under1yoMale) {
		under1YOMale = under1yoMale;
	}
	public Long getUnder1YOFeMale() {
		return under1YOFeMale;
	}
	public void setUnder1YOFeMale(Long under1yoFeMale) {
		under1YOFeMale = under1yoFeMale;
	}
	public Long getUnder1YOUnGen() {
		return under1YOUnGen;
	}
	public void setUnder1YOUnGen(Long under1yoUnGen) {
		under1YOUnGen = under1yoUnGen;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public void setTotalMale(Long totalMale) {
		this.totalMale = totalMale;
	}
	public void setTotalFeMale(Long totalFeMale) {
		this.totalFeMale = totalFeMale;
	}
	public void setTotalUnGen(Long totalUnGen) {
		this.totalUnGen = totalUnGen;
	}
	public Long getTotalParent() {
		return totalParent;
	}
	public void setTotalParent(Long totalParent) {
		this.totalParent = totalParent;
	}
	public Long getParentMale() {
		return parentMale;
	}
	public void setParentMale(Long parentMale) {
		this.parentMale = parentMale;
	}
	public Long getParentFeMale() {
		return parentFeMale;
	}
	public void setParentFeMale(Long parentFeMale) {
		this.parentFeMale = parentFeMale;
	}
	public Long getParentUnGen() {
		return parentUnGen;
	}
	public void setParentUnGen(Long parentUnGen) {
		this.parentUnGen = parentUnGen;
	}
	public Long getTotalOver1YO() {
		return totalOver1YO;
	}
	public void setTotalOver1YO(Long totalOver1YO) {
		this.totalOver1YO = totalOver1YO;
	}
	public Long getOver1YOMale() {
		return over1YOMale;
	}
	public void setOver1YOMale(Long over1yoMale) {
		over1YOMale = over1yoMale;
	}
	public Long getOver1YOFeMale() {
		return over1YOFeMale;
	}
	public void setOver1YOFeMale(Long over1yoFeMale) {
		over1YOFeMale = over1yoFeMale;
	}
	public Long getOver1YOUnGen() {
		return over1YOUnGen;
	}
	public void setOver1YOUnGen(Long over1yoUnGen) {
		over1YOUnGen = over1yoUnGen;
	}
	public Long getTotalGilts() {
		return totalGilts;
	}
	public void setTotalGilts(Long totalGilts) {
		this.totalGilts = totalGilts;
	}
	public Long getGiltsMale() {
		return giltsMale;
	}
	public void setGiltsMale(Long giltsMale) {
		this.giltsMale = giltsMale;
	}
	public Long getGiltsFeMale() {
		return giltsFeMale;
	}
	public void setGiltsFeMale(Long giltsFeMale) {
		this.giltsFeMale = giltsFeMale;
	}
	public Long getGiltsUnGen() {
		return giltsUnGen;
	}
	public void setGiltsUnGen(Long giltsUnGen) {
		this.giltsUnGen = giltsUnGen;
	}
	public Long getTotalExport() {
		return totalExport;
	}
	public void setTotalExport(Long totalExport) {
		this.totalExport = totalExport;
	}
	public Long getExportMale() {
		return exportMale;
	}
	public void setExportMale(Long exportMale) {
		this.exportMale = exportMale;
	}
	public Long getExportFeMale() {
		return exportFeMale;
	}
	public void setExportFeMale(Long exportFeMale) {
		this.exportFeMale = exportFeMale;
	}
	public Long getExportUnGen() {
		return exportUnGen;
	}
	public void setExportUnGen(Long exportUnGen) {
		this.exportUnGen = exportUnGen;
	}
	public Long getTotalImport() {
		return totalImport;
	}
	public void setTotalImport(Long totalImport) {
		this.totalImport = totalImport;
	}
	public Long getImportMale() {
		return importMale;
	}
	public void setImportMale(Long importMale) {
		this.importMale = importMale;
	}
	public Long getImportFeMale() {
		return importFeMale;
	}
	public void setImportFeMale(Long importFeMale) {
		this.importFeMale = importFeMale;
	}
	public Long getImportUnGen() {
		return importUnGen;
	}
	public void setImportUnGen(Long importUnGen) {
		this.importUnGen = importUnGen;
	}
}
