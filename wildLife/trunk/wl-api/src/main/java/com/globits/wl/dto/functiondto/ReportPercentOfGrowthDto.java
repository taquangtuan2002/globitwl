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

public class ReportPercentOfGrowthDto {
	private int year;
	private double totalQuantity;
	private Long parentAnimalId;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public Long getParentAnimalId() {
		return parentAnimalId;
	}
	public void setParentAnimalId(Long parentAnimalId) {
		this.parentAnimalId = parentAnimalId;
	}	
	
}
