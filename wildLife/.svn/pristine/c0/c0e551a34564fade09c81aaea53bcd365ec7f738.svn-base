package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_live_stock_product")
public class LiveStockProduct extends BaseObject{

	/**
	 * Danh mục sản phẩm chăn nuôi gia cầm 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	
	@ManyToOne
	@JoinColumn(name="animal_id")
	private Animal animal;
	
	@ManyToOne
	@JoinColumn(name="unit_quantity_id")
	private Unit unitQuantity;//đơn vị tính theo  số lượng
	
	@ManyToOne
	@JoinColumn(name="unit_amount_id")
	private Unit unitAmount;//đơn vị tính theo  khối lượng

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Unit getUnitQuantity() {
		return unitQuantity;
	}

	public void setUnitQuantity(Unit unitQuantity) {
		this.unitQuantity = unitQuantity;
	}

	public Unit getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(Unit unitAmount) {
		this.unitAmount = unitAmount;
	}

	public LiveStockProduct() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
