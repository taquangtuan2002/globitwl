package com.globits.wl.dto;

import java.util.List;

import com.globits.wl.domain.Farm;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;

public class FarmToSyncMap {
	private Farm farm;
	private List<FarmAnimalTotalDto> listAnimalReportTotal;
	public Farm getFarm() {
		return farm;
	}
	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	public List<FarmAnimalTotalDto> getListAnimalReportTotal() {
		return listAnimalReportTotal;
	}
	public void setListAnimalReportTotal(List<FarmAnimalTotalDto> listAnimalReportTotal) {
		this.listAnimalReportTotal = listAnimalReportTotal;
	}
	
	
}
