package com.globits.wl.dto.functiondto;

import com.globits.wl.domain.Animal;
import com.globits.wl.domain.Farm;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.utils.WLConstant;

public class ReportImportExportTimeDto {
	private AnimalDto animal;// loài vật nuôi
	private FarmDto farm;
	private Long countFarmImport; // số hộ nhập
	private Long countFarmExport; // số hộ xuất
	
	private Double totalImport;// Số cá thể nhập

	private Long totalTimeImport; // Số lần nhập

	private Double totalExport;// Số cá thể xuất
	private Long totalTimeExport;// Số lần xuất
	private Double remainQuantity; // Số còn lại
	

	public ReportImportExportTimeDto() {
		super();
	}
	
	public ReportImportExportTimeDto(Animal animal, Long countFarm, Long total, Long time, Long type) {
		if(animal != null) {
			this.animal = new AnimalDto();
			this.animal.setId(animal.getId());
			this.animal.setName(animal.getName());
			this.animal.setScienceName(animal.getScienceName());
		}
		if(type != null && type.intValue() == WLConstant.ImportExportAnimalType.importAnimal.getValue().intValue()) {
			this.countFarmImport = countFarm;
			this.totalImport = total.doubleValue();
			this.totalTimeImport = time;
		}else if(type != null && type.intValue() == WLConstant.ImportExportAnimalType.exportAnimal.getValue().intValue()) {
			this.countFarmExport = countFarm;
			this.totalExport = total.doubleValue();
			this.totalTimeExport = time;
		}
	}
	
	public ReportImportExportTimeDto(Farm farm, Animal animal,Long countFarm, Double total, Long time, Integer type) {
		if(farm != null) {
			this.farm = new FarmDto();
			this.farm.setId(farm.getId());
			this.farm.setName(farm.getName());
		}
		if(animal != null) {
			this.animal = new AnimalDto();
			this.animal.setId(animal.getId());
			this.animal.setName(animal.getName());
			this.animal.setScienceName(animal.getScienceName());
		}
		if(type != null && type.intValue() == WLConstant.ImportExportAnimalType.importAnimal.getValue().intValue()) {
			this.countFarmImport = countFarm;
			this.totalImport = total;
			this.totalTimeImport = time;
		}else if(type != null && type.intValue() == WLConstant.ImportExportAnimalType.exportAnimal.getValue().intValue()) {
			this.countFarmExport = countFarm;
			this.totalExport = total;
			this.totalTimeExport = time;
		}
	}
	
	public ReportImportExportTimeDto(Animal animal, Long maleParent, Long femaleParent, Long maleGilts, Long femaleGilts, Long childUnder1YearOld, Long maleChildOver1YearOld, Long femaleChildOver1YearOld, Long unGenderChildOver1YearOld, Long time, Long type) {
		if(animal != null) {
			this.animal = new AnimalDto();
			this.animal.setId(animal.getId());
			this.animal.setName(animal.getName());
			this.animal.setScienceName(animal.getScienceName());
		}
		if(type != null && type.intValue() == WLConstant.ImportExportAnimalType.importAnimal.getValue().intValue()) {
			this.totalImport = 0D;
			if(maleParent != null) {
				this.totalImport += maleParent.doubleValue();
			}
			if(femaleParent != null) {
				this.totalImport += femaleParent.doubleValue();
			}
			if(maleGilts != null) {
				this.totalImport += maleGilts.doubleValue();
			}
			if(femaleGilts != null) {
				this.totalImport += femaleGilts.doubleValue();
			}
			if(childUnder1YearOld != null) {
				this.totalImport += childUnder1YearOld.doubleValue();
			}
			if(maleChildOver1YearOld != null) {
				this.totalImport += maleChildOver1YearOld.doubleValue();
			}
			if(femaleChildOver1YearOld != null) {
				this.totalImport += femaleChildOver1YearOld.doubleValue();
			}
			if(unGenderChildOver1YearOld != null) {
				this.totalImport += unGenderChildOver1YearOld.doubleValue();
			}
			this.totalTimeImport = time;
		}else if(type != null && type.intValue() == WLConstant.ImportExportAnimalType.exportAnimal.getValue().intValue()) {
			this.totalExport = 0D;
			if(maleParent != null) {
				this.totalExport += maleParent.doubleValue();
			}
			if(femaleParent != null) {
				this.totalExport += femaleParent.doubleValue();
			}
			if(maleGilts != null) {
				this.totalExport += maleGilts.doubleValue();
			}
			if(femaleGilts != null) {
				this.totalExport += femaleGilts.doubleValue();
			}
			if(childUnder1YearOld != null) {
				this.totalExport += childUnder1YearOld.doubleValue();
			}
			if(maleChildOver1YearOld != null) {
				this.totalExport += maleChildOver1YearOld.doubleValue();
			}
			if(femaleChildOver1YearOld != null) {
				this.totalExport += femaleChildOver1YearOld.doubleValue();
			}
			if(unGenderChildOver1YearOld != null) {
				this.totalExport += unGenderChildOver1YearOld.doubleValue();
			}
			this.totalTimeExport = time;
		}
	}

	public AnimalDto getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

	public Double getTotalImport() {
		return totalImport;
	}

	public void setTotalImport(Double totalImport) {
		this.totalImport = totalImport;
	}

	public Long getTotalTimeImport() {
		return totalTimeImport;
	}

	public void setTotalTimeImport(Long totalTimeImport) {
		this.totalTimeImport = totalTimeImport;
	}

	public Double getTotalExport() {
		return totalExport;
	}

	public void setTotalExport(Double totalExport) {
		this.totalExport = totalExport;
	}

	public Long getTotalTimeExport() {
		return totalTimeExport;
	}

	public void setTotalTimeExport(Long totalTimeExport) {
		this.totalTimeExport = totalTimeExport;
	}
	
	public Double getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(Double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public Long getCountFarmImport() {
		return countFarmImport;
	}

	public void setCountFarmImport(Long countFarmImport) {
		this.countFarmImport = countFarmImport;
	}

	public Long getCountFarmExport() {
		return countFarmExport;
	}

	public void setCountFarmExport(Long countFarmExport) {
		this.countFarmExport = countFarmExport;
	}

}
