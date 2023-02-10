package com.globits.wl.dto;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDateTime;

import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.FmsRegion;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.ImportExportLiveStockProduct;
import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.domain.SeedLevel;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.NumberUtils;

public class ImportExportLiveStockProductDto {
	private Long id;
	
	/*
	 * 1: Nhập
	 * -1: Xuất
	 */
	private int type;
	
	private FarmDto farm;
	
	private FmsAdministrativeUnitDto ward;
	private FmsAdministrativeUnitDto district;
	private FmsAdministrativeUnitDto province;
	private FmsRegionDto region;
	private LiveStockProductDto liveStockProductDto;

	private String batchCode;//Số lô

	private Date dateIssue;//ngày nhập- ngày xuất
	
	private double quantity;//Số lượng: con
	
	private double amount;//Khối lượng: kg,tấn, tạ,...
	
	private String description;//Diễn tả
	private String voucherCode;//Số hóa đơn (trường hợp xuất)
	
	private int exportType;//Loại phiếu xuất
		
	private String exportReason;//Lý do xuất
	
	private String buyerName;//Tên người mua
	
	private String buyerAdress;//Địa chỉ người mua
	
	private int monthReport;
	private int yearReport;
	private String monthInYear;
	private int toMonthReport;
	private int toYearReport;
	private String created_by;//người tạo
	private LocalDateTime create_date;

	private double remainQuantity;//Số lượng còn lại: con
	private double remainAmount;//Khối lượng còn lại: con
	private String provinceName;
	private ImportExportLiveStockProductDto importLiveStockPorduct;//Phiếu nhập Id
	private FmsAdministrativeUnitDto provincial;
	private Long countFarm;

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	
	public FmsAdministrativeUnitDto getProvincial() {
		return provincial;
	}

	public void setProvincial(FmsAdministrativeUnitDto provincial) {
		this.provincial = provincial;
	}

	public Long getCountFarm() {
		return countFarm;
	}

	public void setCountFarm(Long countFarm) {
		this.countFarm = countFarm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public FarmDto getFarm() {
		return farm;
	}

	public void setFarm(FarmDto farm) {
		this.farm = farm;
	}

	public FmsAdministrativeUnitDto getWard() {
		return ward;
	}

	public void setWard(FmsAdministrativeUnitDto ward) {
		this.ward = ward;
	}

	public FmsAdministrativeUnitDto getDistrict() {
		return district;
	}

	public void setDistrict(FmsAdministrativeUnitDto district) {
		this.district = district;
	}

	public FmsAdministrativeUnitDto getProvince() {
		return province;
	}

	public void setProvince(FmsAdministrativeUnitDto province) {
		this.province = province;
	}

	public FmsRegionDto getRegion() {
		return region;
	}

	public void setRegion(FmsRegionDto region) {
		this.region = region;
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
		//return quantity;
		return NumberUtils.round(quantity,2);
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		//return amount;
		return NumberUtils.round(amount,2);
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

	public double getRemainQuantity() {
		//return remainQuantity;
		return NumberUtils.round(remainQuantity,2);
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public int getMonthReport() {
		return monthReport;
	}

	public void setMonthReport(int monthReport) {
		this.monthReport = monthReport;
	}

	public int getYearReport() {
		return yearReport;
	}

	public void setYearReport(int yearReport) {
		this.yearReport = yearReport;
	}	
	

	public ImportExportLiveStockProductDto getImportLiveStockPorduct() {
		return importLiveStockPorduct;
	}

	public void setImportLiveStockPorduct(ImportExportLiveStockProductDto importLiveStockPorduct) {
		this.importLiveStockPorduct = importLiveStockPorduct;
	}

	public LiveStockProductDto getLiveStockProductDto() {
		return liveStockProductDto;
	}

	public void setLiveStockProductDto(LiveStockProductDto liveStockProductDto) {
		this.liveStockProductDto = liveStockProductDto;
	}

	public String getMonthInYear() {
		monthInYear=monthReport+"/"+yearReport ;
		if(toMonthReport>0 && toYearReport>0){
			if(monthReport==toMonthReport && yearReport==toYearReport){
				monthInYear=monthReport+"/"+yearReport ;
			}else{
				monthInYear= monthInYear+ " - "+toMonthReport+"/"+toYearReport ;
			}
		
		}
		return monthInYear;
	}

	public void setRemainQuantity(double remainQuantity) {
		this.remainQuantity = remainQuantity;
	}

	public double getRemainAmount() {
		return NumberUtils.round(remainAmount,2);
	}

	public void setRemainAmount(double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public int getToMonthReport() {
		return toMonthReport;
	}

	public void setToMonthReport(int toMonthReport) {
		this.toMonthReport = toMonthReport;
	}

	public int getToYearReport() {
		return toYearReport;
	}

	public void setToYearReport(int toYearReport) {
		this.toYearReport = toYearReport;
	}

	public ImportExportLiveStockProductDto() {
		super();
	}
	
	public ImportExportLiveStockProductDto(ImportExportLiveStockProduct entity) {
		super();
		
		if(entity != null) { 
			this.id = entity.getId();
			this.type  = entity.getType();
			if(entity.getFarm()!= null) {
				this.farm = new FarmDto(entity.getFarm(),true);
				if(entity.getFarm().getAdministrativeUnit()!=null && entity.getFarm().getAdministrativeUnit().getParent()!=null && entity.getFarm().getAdministrativeUnit().getParent().getParent()!=null) {
					this.provinceName = entity.getFarm().getAdministrativeUnit().getParent().getParent().getName();
				}
			}

			
			if(entity.getProvince()!= null) {
				this.provincial = new FmsAdministrativeUnitDto(entity.getProvince());
			}
			this.batchCode = entity.getBatchCode();
		
			this.quantity = entity.getQuantity();
			this.amount = entity.getAmount();
			
			this.description = entity.getDescription();
		
			this.voucherCode = entity.getVoucherCode();
			this.exportType = entity.getExportType();
			this.exportReason = entity.getExportReason();
			this.buyerName = entity.getBuyerName();
			this.buyerAdress = entity.getBuyerAdress();
			this.dateIssue = entity.getDateIssue();
			this.remainQuantity=entity.getRemainQuantity();
			this.remainAmount=entity.getRemainAmount();
			this.created_by=entity.getCreatedBy();
			this.create_date=entity.getCreateDate();
			if (entity.getLiveStockProduct() != null) {
				this.liveStockProductDto = new LiveStockProductDto(entity.getLiveStockProduct());
			}
			if(entity.getImportLiveStockPorduct()!=null){
				ImportExportLiveStockProductDto dto=new ImportExportLiveStockProductDto();
				dto.setId(entity.getImportLiveStockPorduct().getId());
				dto.setDateIssue(entity.getImportLiveStockPorduct().getDateIssue());
				dto.setBatchCode(entity.getImportLiveStockPorduct().getBatchCode());			
				dto.setQuantity(entity.getImportLiveStockPorduct().getQuantity());
				dto.setAmount(entity.getImportLiveStockPorduct().getAmount());
				dto.setRemainQuantity(entity.getImportLiveStockPorduct().getRemainQuantity());
				dto.setRemainAmount(entity.getImportLiveStockPorduct().getRemainAmount());
				this.importLiveStockPorduct=dto;
			}
			
		}
	}
	
	public ImportExportLiveStockProductDto(Object[] results,List<String> columns) {
		if(results!=null && results.length>0 && columns!=null && columns.size()>0) {
			for (int i = 0; i < columns.size(); i++) {
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.amount.getValue())) {
					this.amount = (Double)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.quantity.getValue())) {
					this.quantity = (Double)results[i];
				}			
				
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.batchCode.getValue())) {
					this.batchCode = String.valueOf(results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.district.getValue()+"id")) {
					if(this.district==null) this.district = new FmsAdministrativeUnitDto();
					this.district.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.district.getValue()+"name")) {
					if(this.district==null) this.district = new FmsAdministrativeUnitDto();
					this.district.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.farm.getValue()+"id")) {
					if(this.farm==null) this.farm = new FarmDto();
					this.farm.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.farm.getValue()+"name")) {
					if(this.farm==null) this.farm = new FarmDto();
					this.farm.setName(String.valueOf(results[i]));
				}
				
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.province.getValue()+"id")) {					
					if(this.province==null) this.province = new FmsAdministrativeUnitDto();
					this.province.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.province.getValue()+"name")) {					
					if(this.province==null) this.province = new FmsAdministrativeUnitDto();
					this.province.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.region.getValue()+"id")) {
					if(this.region==null) this.region = new FmsRegionDto();
					this.region.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.region.getValue()+"name")) {
					if(this.region==null) this.region = new FmsRegionDto();
					this.region.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.ward.getValue()+"id")) {					
					if(this.ward==null) this.ward = new FmsAdministrativeUnitDto();
					this.ward.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.ward.getValue()+"name")) {					
					if(this.ward==null) this.ward = new FmsAdministrativeUnitDto();
					this.ward.setName(String.valueOf(results[i]));
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.countFarm.getValue())) {				
					this.countFarm = (Long)results[i];
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.liveStockProduct.getValue()+"id")) {					
					if(this.liveStockProductDto==null) this.liveStockProductDto = new LiveStockProductDto();
					this.liveStockProductDto.setId((Long)results[i]);
				}
				if(results[i]!=null && columns.get(i)!=null && columns.get(i).equals(WLConstant.FunctionAndGroupByItem.liveStockProduct.getValue()+"name")) {					
					if(this.liveStockProductDto==null) this.liveStockProductDto = new LiveStockProductDto();
					this.liveStockProductDto.setName(String.valueOf(results[i]));
				}
			}
		}
	}


}
