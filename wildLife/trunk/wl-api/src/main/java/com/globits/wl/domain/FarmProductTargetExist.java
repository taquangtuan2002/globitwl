package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
/*
 * Lượng tồn theo hướng sản phầm và cơ sở chăn nuôi
 * 
 */
@Entity
@Table(name = "tbl_farm_product_target_exist")
public class FarmProductTargetExist  extends BaseObject{
	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name="product_target_id")
	private ProductTarget productTarget;
	
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
	
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public FarmProductTargetExist() {
		this.setUuidKey(UUID.randomUUID());
	}
}
