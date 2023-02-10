package com.globits.wl.dto;

import com.globits.wl.domain.Animal;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmAnimalProductTargetExist;
import com.globits.wl.domain.ProductTarget;

public class FarmAnimalProductTargetExistDto {
	private Long id;
	private Farm farm;
	private ProductTargetDto productTarget;
	private AnimalDto animal;
	private double quantity;// Số lượng tồn: con

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public ProductTargetDto getProductTarget() {
		return productTarget;
	}

	public void setProductTarget(ProductTargetDto productTarget) {
		this.productTarget = productTarget;
	}

	public AnimalDto getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalDto animal) {
		this.animal = animal;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public FarmAnimalProductTargetExistDto() {

	}
	public FarmAnimalProductTargetExistDto(FarmAnimalProductTargetExist entity) {
		this.id=entity.getId();
		this.quantity=entity.getQuantity();
		if(entity.getAnimal()!=null) {
			this.animal = new AnimalDto();
			this.animal.setId(entity.getAnimal().getId());
			this.animal.setCode(entity.getAnimal().getCode());
			this.animal.setName(entity.getAnimal().getName());
			this.animal.setDescription(entity.getAnimal().getDescription());
			if(entity.getAnimal().getParent()!=null) {
				this.animal.setParent(new AnimalDto());
				this.animal.getParent().setId(entity.getAnimal().getParent().getId());
				this.animal.getParent().setName(entity.getAnimal().getParent().getName());
				this.animal.getParent().setCode(entity.getAnimal().getParent().getCode());
			}
		}
		if(entity.getProductTarget()!=null) {
			this.productTarget = new ProductTargetDto(entity.getProductTarget());
		}
	}

}
