package com.globits.wl.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.globits.core.domain.BaseObject;
/*
 * Loại hình sản xuất của trang trại
 * VD: hướng thịt, hướng trứng, hướng sữa
 */
@Entity
@Table(name = "tbl_farm_product_target")
public class FarmProductTarget  extends BaseObject{
	@ManyToOne
	@JoinColumn(name="farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name="product_target_id")
	private ProductTarget productTarget;

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
	
	public FarmProductTarget() {
		this.setUuidKey(UUID.randomUUID());
	}
}
