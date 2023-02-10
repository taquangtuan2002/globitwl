package com.globits.wl.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_export_egg")
@XmlRootElement
/*
 * Xuất trứng
 */
public class ExportEgg extends BaseObject {
	@Column(name = "code")
	private String code;// mã phiếu
	@Column(name = "date_export")
	private Date dateExport;// Ngày xuất
	@ManyToOne
	@JoinColumn(name = "farm_id")
	private Farm farm;
	
	@Column(name = "quantity")
	private double quantity;// số lượng xuất (quả)
	@Column(name = "remain_quantity")
	private double remainQuantity;// số trứng còn lại
	@Column(name = "buyer_name")
	private String buyerName;// Khách hàng
	
	@Column(name = "buyer_adress")
	private String buyerAdress;// Địa chỉ khách hang

	@Column(name = "type")
	private Integer type;// xuất trứng type = -1 hoặc null; nhập trứng type = 1

	@Column(name = "batch_code")
	private String batchCode;// Số lô
	
	@ManyToOne
	@JoinColumn(name = "import_egg_id")
	private ExportEgg importEgg;// phiếu nhập trứng
	
	@ManyToOne
	@JoinColumn(name = "export_egg_id")
	private ExportEgg exportEgg;// phiếu xuất trứng
	
	@Column(name = "egg_type")
	private int eggType;// trứng thương phẩm hay chứng giống
	
	@Column(name="reason_for_export")
	private Integer reasonForExport;	//Lý do xuất:  (1) bán - (2) loại thải - (3) tiêu thụ nội bộ

	@ManyToOne
	@JoinColumn(name="import_export_animal_id")
	private ImportExportAnimal importExportAnimal;// đàn gia cầm
	
	public ImportExportAnimal getImportExportAnimal() {
		return importExportAnimal;
	}

	public void setImportExportAnimal(ImportExportAnimal importExportAnimal) {
		this.importExportAnimal = importExportAnimal;
	}

	public double getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public int getEggType() {
		return eggType;
	}

	public void setEggType(int eggType) {
		this.eggType = eggType;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public ExportEgg getImportEgg() {
		return importEgg;
	}

	public void setImportEgg(ExportEgg importEgg) {
		this.importEgg = importEgg;
	}

	public ExportEgg getExportEgg() {
		return exportEgg;
	}

	public void setExportEgg(ExportEgg exportEgg) {
		this.exportEgg = exportEgg;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Farm getFarm() {
		return farm;
	}

	public void setFarm(Farm farm) {
		this.farm = farm;
	}

	public Date getDateExport() {
		return dateExport;
	}

	public void setDateExport(Date dateExport) {
		this.dateExport = dateExport;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
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

	public Integer getReasonForExport() {
		return reasonForExport;
	}

	public void setReasonForExport(Integer reasonForExport) {
		this.reasonForExport = reasonForExport;
	}
	
}
