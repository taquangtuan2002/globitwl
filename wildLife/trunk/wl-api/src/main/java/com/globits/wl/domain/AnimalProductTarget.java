package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_animal_product_target")
public class AnimalProductTarget extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;
	
	@ManyToOne
	@JoinColumn(name="product_target_id")
	private ProductTarget productTarget;

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
		
	public ProductTarget getProductTarget() {
		return productTarget;
	}

	public void setProductTarget(ProductTarget productTarget) {
		this.productTarget = productTarget;
	}

	public AnimalProductTarget() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
