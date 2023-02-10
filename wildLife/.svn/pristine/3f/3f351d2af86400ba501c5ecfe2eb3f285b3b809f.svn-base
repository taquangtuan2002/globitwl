package com.globits.wl.dto.functiondto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.globits.wl.domain.Animal;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;

public class FarmAnimalTotalDto {
	private String farmCode;
	private String animalCode;
	
	private FarmDto farm;
	private AnimalDto animal;
	private FmsAdministrativeUnitDto administrativeUnit;
	private String administrativeUnitName;
	private Long totalFarm;
	
	private Integer total;
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer type;

	public FmsAdministrativeUnitDto getAdministrativeUnit() {
		return administrativeUnit;
	}
	public void setAdministrativeUnit(FmsAdministrativeUnitDto administrativeUnit) {
		this.administrativeUnit = administrativeUnit;
	}
	public AnimalDto getAnimal() {
		return animal;
	}
	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}
	public String getAdministrativeUnitName() {
		return administrativeUnitName;
	}
	public void setAdministrativeUnitName(String administrativeUnitName) {
		this.administrativeUnitName = administrativeUnitName;
	}
	public Long getTotalFarm() {
		return totalFarm;
	}
	public void setTotalFarm(Long totalFarm) {
		this.totalFarm = totalFarm;
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
	public String getFarmCode() {
		return farmCode;
	}
	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public FarmDto getFarm() {
		return farm;
	}
	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}
	public FarmAnimalTotalDto() {
		super();
	}
	public FarmAnimalTotalDto(String farmCode, String animalCode, Integer total, Integer year) {
		this.farmCode = farmCode;
		this.animalCode = animalCode;
		this.total = total;
		this.year = year;
	}
	public FarmAnimalTotalDto(String farmCode, String animalCode, Integer total, Integer year, Integer month, Integer day) {
		this.farmCode = farmCode;
		this.animalCode = animalCode;
		this.total = total;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	public FarmAnimalTotalDto(Farm farm, Animal animal, Integer total, Integer year, Integer month, Integer day) {
		this.farmCode = farm.getCode();
		this.animalCode = animal.getCode();
		this.total = total;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	public FarmAnimalTotalDto(Farm farm, Animal animal, Integer total, Integer year, Integer month, Integer day, Integer type) {
		this.farmCode = farm.getCode();
		this.animalCode = animal.getCode();
		this.total = total;
		this.year = year;
		this.month = month;
		this.day = day;
		this.type = type;
	}
	
	public FarmAnimalTotalDto(Farm farm, Animal animal, Long total, Integer year) {
		if(farm != null) {
			this.farm = new FarmDto();
			this.farm.setId(farm.getId());
			this.farm.setName(farm.getName());
			this.farm.setAdressDetail(farm.getAdressDetail());
			this.farm.setIssuingCodeDate(farm.getIssuingCodeDate());
			this.farm.setNewRegistrationCode(farm.getNewRegistrationCode());
			this.farm.setLongitude(farm.getLongitude());
			this.farm.setLatitude(farm.getLatitude());
			this.farmCode = farm.getCode();
		}
		if(animal != null) {
			this.animal = new AnimalDto(animal);
			this.animalCode = this.animal.getCode();
		}
		if(total != null) {
			this.total = total.intValue();
		}
		this.year = year;
	}
	
	public FarmAnimalTotalDto(FmsAdministrativeUnit fmsAdministrativeUnit,Animal animal, Integer year, Long total, Long totalFarm) {
		this.animal = new AnimalDto(animal);
		this.year = year;
		this.total = total.intValue();
		this.totalFarm = totalFarm;
		if(fmsAdministrativeUnit!=null) {
			this.administrativeUnitName = fmsAdministrativeUnit.getName();
		}		
		this.administrativeUnit = new FmsAdministrativeUnitDto(fmsAdministrativeUnit);
	}
	public FarmAnimalTotalDto(String fmsAdministrativeUnitName,Animal animal, Integer year, Long total, Long totalFarm) {
		this.animal = new AnimalDto(animal);
		this.year = year;
		this.total = total.intValue();
		this.totalFarm = totalFarm;
		this.administrativeUnitName = fmsAdministrativeUnitName;
	}
	public FarmAnimalTotalDto(FmsAdministrativeUnit fmsAdministrativeUnit,Animal animal, Integer year, Integer total, Long totalFarm) {
		this.animal = new AnimalDto(animal);
		this.year = year;
		this.total = total;
		this.totalFarm = totalFarm;
		this.administrativeUnit = new FmsAdministrativeUnitDto(fmsAdministrativeUnit);
	}
	
	public FarmAnimalTotalDto(Animal animal, Integer year, Long total, Long totalFarm) {
		this.animal = new AnimalDto(animal);
		this.year = year;
		this.total = total.intValue();
		this.totalFarm = totalFarm;
	}
	public FarmAnimalTotalDto(Animal animal, Integer year, Integer total, Long totalFarm) {
		this.animal = new AnimalDto(animal);
		this.year = year;
		this.total = total;
		this.totalFarm = totalFarm;
	}
	
	public Map<String, Object> toMAP(){
		Map<String, Object> map = new HashMap<String, Object>();
		if(this.farmCode != null) {
			map.put("farmCode", this.farmCode);
		}
		else if(this.farm!=null) {
			map.put("farmCode", this.farm.getCode());
		}
		if(this.animalCode != null) {
			map.put("animalCode", this.animalCode);
		}
		else if(this.animal!=null) {
			map.put("animalCode", this.animal.getCode());
		}
		if(this.total != null) {
			map.put("total", this.total);
		}
		if(this.year != null) {
			map.put("year", this.year);
		}
		return map;
	}
	
}
