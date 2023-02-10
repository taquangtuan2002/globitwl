package com.globits.wl.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_import_export_live_stock_product")
@XmlRootElement
/*
 * Nhập - xuất sản phẩm chăn nuôi gia cầm
 */
public class ImportExportLiveStockProduct  extends BaseObject{
	
	/*
	 * Kiểu phiếu: 1 = tăng đàn;-1=giảm đàn
	 */
	@Column(name="type")
	private int type;
	
	@ManyToOne
	@JoinColumn(name = "farm_id")
	private Farm farm;
	
	@ManyToOne
	@JoinColumn(name = "live_stock_product_id")
	private LiveStockProduct liveStockProduct;
	
	@Column(name="batch_code")
	private String batchCode;//Số lô
	
	@Column(name="date_issue")
	private Date dateIssue;//Ngày nhập hoặc xuất (gộp chung để tính tồn kho)
	
	@Column(name="quantity")
	private double quantity;//Số lượng: con
	
	@Column(name="amount")
	private double amount;//Khối lượng: kg,tấn, tạ,...
	
	@Column(name="description")
	private String description;//Diễn tả
	
	@Column(name="voucher_code")
	private String voucherCode;//Số chứng từ		
	
	@Column(name="export_type")
	private int exportType;//Loại phiếu xuất
	
	@Column(name="export_reason")
	private String exportReason;//Lý do xuất
	
	@Column(name="buyer_name")
	private String buyerName;//Tên người mua
	
	@Column(name="buyer_adress")
	private String buyerAdress;//Địa chỉ người mua
	
	@ManyToOne
	@JoinColumn(name="fms_administrative_unit_id")
	private FmsAdministrativeUnit province;//tỉnh tiêu thụ
	
	@ManyToOne
	@JoinColumn(name="import_live_stock_product_id")
	private ImportExportLiveStockProduct importLiveStockPorduct;//Phiếu nhập Id
	
	@Column(name="remain_quantity")
	private double remainQuantity;//Số lượng còn lại: con
	
	@Column(name="remain_amount")
	private double remainAmount;//Khối lượng còn lại: con
	
	
	public FmsAdministrativeUnit getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnit province) {
		this.province = province;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}
	
	public LiveStockProduct getLiveStockProduct() {
		return liveStockProduct;
	}

	public void setLiveStockProduct(LiveStockProduct liveStockProduct) {
		this.liveStockProduct = liveStockProduct;
	}


	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}


	public Date getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(Date dateIssue) {
		this.dateIssue = dateIssue;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	
	public int getExportType() {
		return exportType;
	}

	public void setExportType(int exportType) {
		this.exportType = exportType;
	}

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerAdress() {
		return buyerAdress;
	}

	public void setBuyerAdress(String buyerAdress) {
		this.buyerAdress = buyerAdress;
	}		

	public ImportExportLiveStockProduct getImportLiveStockPorduct() {
		return importLiveStockPorduct;
	}

	public void setImportLiveStockPorduct(ImportExportLiveStockProduct importLiveStockPorduct) {
		this.importLiveStockPorduct = importLiveStockPorduct;
	}

	public double getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}	

	public double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public ImportExportLiveStockProduct() {
		this.setUuidKey(UUID.randomUUID());
	}
	
}
