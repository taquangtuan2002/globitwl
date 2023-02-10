package com.globits.wl.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_animal_required_product_target")
@XmlRootElement
public class AnimalRequiredProductTarget extends BaseObject{
	@ManyToOne
	@JoinColumn(name="animal_id")
	private AnimalRequired animalRequired;
	
	@ManyToOne
	@JoinColumn(name="product_target_id")
	private ProductTarget productTarget;

	public AnimalRequired getAnimalRequired() {
		return animalRequired;
	}

	public void setAnimalRequired(AnimalRequired animalRequired) {
		this.animalRequired = animalRequired;
	}

	public ProductTarget getProductTarget() {
		return productTarget;
	}

	public void setProductTarget(ProductTarget productTarget) {
		this.productTarget = productTarget;
	}
	
	
}
