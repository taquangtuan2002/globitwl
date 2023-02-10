package com.globits.wl.dto.functiondto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import com.globits.core.dto.AdministrativeUnitDto;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmAnimal;
import com.globits.wl.domain.FarmAnimalType;
import com.globits.wl.domain.FarmFileAttachment;
import com.globits.wl.domain.FarmHusbandryType;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.FarmStore;
import com.globits.wl.dto.ExportEggDto;
import com.globits.wl.dto.ImportAnimalFeedDto;
import com.globits.wl.dto.ImportDrugDto;
import com.globits.wl.utils.NumberUtils;

public class ReportManagerDto {
	private String name;
	private String code;
	private Double quantity;
	private int count;
	private double totalInventory;
	private double totalImport;
	private double totalExport;
	
	private int countImport;
	private int countExport;
	private int countInvenroty;
	
	private List<ImportDrugDto> drugs;
	private List<ImportAnimalFeedDto> feeds;
	private List<ExportEggDto> eggs;
	
	public int getCountImport() {
		return countImport;
	}

	public void setCountImport(int countImport) {
		this.countImport = countImport;
	}

	public int getCountExport() {
		return countExport;
	}

	public void setCountExport(int countExport) {
		this.countExport = countExport;
	}

	public int getCountInvenroty() {
		return countInvenroty;
	}

	public void setCountInvenroty(int countInvenroty) {
		this.countInvenroty = countInvenroty;
	}

	public double getTotalInventory() {
		return NumberUtils.round(totalInventory,2);
//		return totalInventory;
	}

	public void setTotalInventory(double totalInventory) {
		this.totalInventory = totalInventory;
	}

	public double getTotalImport() {
		return NumberUtils.round(totalImport,2);
//		return totalImport;
	}

	public void setTotalImport(double totalImport) {
		this.totalImport = totalImport;
	}

	public double getTotalExport() {
		return NumberUtils.round(totalExport,2);
//		return totalExport;
	}

	public void setTotalExport(double totalExport) {
		this.totalExport = totalExport;
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

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ImportDrugDto> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<ImportDrugDto> drugs) {
		this.drugs = drugs;
	}

	public List<ImportAnimalFeedDto> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<ImportAnimalFeedDto> feeds) {
		this.feeds = feeds;
	}

	public List<ExportEggDto> getEggs() {
		return eggs;
	}

	public void setEggs(List<ExportEggDto> eggs) {
		this.eggs = eggs;
	}	

}
