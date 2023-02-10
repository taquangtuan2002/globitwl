package com.globits.wl.dto.report;

import com.globits.wl.utils.NumberUtils;
/*
 * Dự báo năng suất sản lượng thịt
 */
public class ProductivityForecastReportDto extends InventoryReportDto   {

	private int quantityChildren;//Số con thịt hơi xuất chuồng
	private double quantityMeat;//sản lượng thịt
	private double quantityEgg;//năng suất trứng

	public double getQuantityMeat() {
		return NumberUtils.round(quantityMeat,2);
//		return quantityMeat;
	}
	public void setQuantityMeat(double quantityMeat) {
		this.quantityMeat = quantityMeat;
	}
	public double getQuantityEgg() {
		return NumberUtils.round(quantityEgg,0);
//		return quantityEgg;
	}
	public void setQuantityEgg(double quantityEgg) {
		this.quantityEgg = quantityEgg;
	}
	public int getQuantityChildren() {
		return quantityChildren;
	}
	public void setQuantityChildren(int quantityChildren) {
		this.quantityChildren = quantityChildren;
	}
	
}
