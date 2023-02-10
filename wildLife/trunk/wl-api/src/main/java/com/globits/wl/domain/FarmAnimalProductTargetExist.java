package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
/*
 * Lượng tồn theo hướng sản phầm và cơ sở chăn nuôi và vật nuôi
 * 
 */
@Entity
@Table(name = "tbl_farm_animal_product_target_exist")
public class FarmAnimalProductTargetExist  extends BaseObject{
	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name="product_target_id")
	private ProductTarget productTarget;
	
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;
	
	@Column(name="quantity")
	private double quantity;//Số lượng tồn: con

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public ProductTarget getProductTarget() {
		return productTarget;
	}

	public void setProductTarget(ProductTarget productTarget) {
		this.productTarget = productTarget;
	}	
	
	
	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public FarmAnimalProductTargetExist() {
		this.setUuidKey(UUID.randomUUID());
	}
}
