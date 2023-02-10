package com.globits.wl.dto.functiondto;

import com.globits.wl.domain.Farm;
import com.globits.wl.dto.FarmDto;

public class FarmDuplicateDoubtsDto {
	private FarmDto farmA;
	private FarmDto farmB;
	
	public FarmDto getFarmA() {
		return farmA;
	}
	public void setFarmA(FarmDto farmA) {
		this.farmA = farmA;
	}
	public FarmDto getFarmB() {
		return farmB;
	}
	public void setFarmB(FarmDto farmB) {
		this.farmB = farmB;
	}
	public FarmDuplicateDoubtsDto() {
		super();
	}
	public FarmDuplicateDoubtsDto(FarmDto farmADto, FarmDto farmBDto) {
		super();
		this.farmA = farmADto;
		this.farmB = farmBDto;
	}
	public FarmDuplicateDoubtsDto(Farm farmADto, Farm farmBDto) {
		super();
		this.farmA = new FarmDto(farmADto);
		this.farmB = new FarmDto(farmBDto);
	}
	
}
