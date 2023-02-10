package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_farm_animal")
public class FarmAnimal extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	public FarmAnimal() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
