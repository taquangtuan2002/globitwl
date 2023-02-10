package com.globits.wl.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.repository.AdministrativeUnitRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmAnimalProductTargetExist;
import com.globits.wl.domain.FarmProductTargetExist;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.domain.InjectionTime;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.InjectionPlantDto;
import com.globits.wl.dto.SeedLevelDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.ReportSeedLevelDto;
import com.globits.wl.dto.report.ReportSeedLevelProducTargetDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.FarmAnimalProductTargetExistRepository;
import com.globits.wl.repository.FarmProductTargetExistRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.repository.InjectionPlantRepository;
import com.globits.wl.repository.InjectionTimeRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.repository.SeedLevelRepository;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.ImportExportAnimalSeedLevelService;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;
import com.globits.wl.utils.WLStringUtil;
import com.globits.wl.utils.WLConstant.SeedLevel;

@Service
public class ImportExportAnimalSeedLevelServiceImpl extends GenericServiceImpl<ImportExportAnimal, Long> implements ImportExportAnimalSeedLevelService {

	@Autowired
	private InjectionPlantRepository injectionPlantRepository;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private ImportExportAnimalRepository importAnimalRepository;

	@Autowired
	private ProductTargetRepository productTargetRepository;
	
	@Autowired
	private OriginalRepository originalRepository;
	
	@Autowired
	private InjectionTimeRepository injectionTimeRepository;
	
	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	
	@Autowired
	private FarmService farmService;
	
	@Autowired
	private FarmProductTargetExistRepository productTargetExistRepository;
	
	@Autowired
	private FarmAnimalProductTargetExistRepository animalProductTargetExistRepository;
	@Autowired
	private SeedLevelRepository seedLevelRepository;
	
	
	@Override
	public List<InventoryReportDto> getQuantityReport(ReportParamDto paramDto) throws ParseException{
		if(paramDto!=null) {
			
			List<InventoryReportDto> ret = new ArrayList<InventoryReportDto>();
			List<InventoryReportDto> subRet = new ArrayList<InventoryReportDto>();
			if(paramDto.getReportType()==WLConstant.ReportType.list.getValue()) {
				if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.years.getValue()) {
					for (int i = paramDto.getFromYear(); i <= paramDto.getToYear(); i++) {
						Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
						if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
							fromDate = WLDateTimeUtil.numberToDate(1, 1, i);
						}
					    Date toDate= WLDateTimeUtil.numberToDate(31, 12, i);				    
					    paramDto.setFromDate(fromDate);
					    paramDto.setToDate(toDate);				    
					    subRet = this.getSumQuantity(paramDto);
					    if(subRet!=null) {
					    	for (ImportExportAnimalDto importExportAnimalDto : subRet) {
					    		importExportAnimalDto.setYearReport(i);
							}
					    	ret.addAll(subRet);
					    }
					}
				}
				else if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.months.getValue()) {
					for (int i = paramDto.getFromMonth(); i <= paramDto.getToMonth(); i++) {
						Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
						if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
							fromDate = WLDateTimeUtil.numberToDate(1, i, paramDto.getCurrentYear());
						}
					    Date toDate= WLDateTimeUtil.getLastDayOfMonth(i, paramDto.getCurrentYear());
					    paramDto.setFromDate(fromDate);
					    paramDto.setToDate(toDate);				    
					    subRet = this.getSumQuantity(paramDto);
					    if(subRet!=null) {
					    	for (ImportExportAnimalDto importExportAnimalDto : subRet) {
					    		importExportAnimalDto.setYearReport(paramDto.getCurrentYear());
					    		importExportAnimalDto.setMonthReport(i);
							}
					    	ret.addAll(subRet);
					    }
					}
				}
				else if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.fromToMonth.getValue()) {
					Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
					if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getFromMonth(), paramDto.getFromYear());
					}
					Date toDate= WLDateTimeUtil.numberToDate(31, paramDto.getToMonth(), paramDto.getToYear());	
					paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportAnimalDto importExportAnimalDto : subRet) {
				    		importExportAnimalDto.setYearReport(paramDto.getFromYear());
				    		importExportAnimalDto.setMonthReport(paramDto.getFromMonth());
				    		importExportAnimalDto.setToMonthReport(paramDto.getToMonth());
				    		importExportAnimalDto.setToYearReport(paramDto.getToYear());
						}
				    	ret.addAll(subRet);
				    }
					 
					
				}
			}
			else if(paramDto.getReportType()==WLConstant.ReportType.compare.getValue()) {
				if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.years.getValue()) {
					Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
					if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, 1, paramDto.getFromYear());
					}
					
				    Date toDate= WLDateTimeUtil.numberToDate(31, 12, paramDto.getFromYear());				    
				    paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportAnimalDto importExportAnimalDto : subRet) {
				    		importExportAnimalDto.setYearReport(paramDto.getFromYear());
						}
				    	ret.addAll(subRet);
				    }
				    
				    fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);			
				    if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, 1, paramDto.getToYear());
					}
				    toDate= WLDateTimeUtil.numberToDate(31, 12, paramDto.getToYear());				    
				    paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportAnimalDto importExportAnimalDto : subRet) {
				    		importExportAnimalDto.setYearReport(paramDto.getToYear());
						}
				    	ret.addAll(subRet);
				    }				    
				}
				else if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.months.getValue()) {
					Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);				
					if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getFromMonth(), paramDto.getFromYear());
					}
				    Date toDate= WLDateTimeUtil.getLastDayOfMonth(paramDto.getFromMonth(), paramDto.getFromYear());
				    paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportAnimalDto importExportAnimalDto : subRet) {
				    		importExportAnimalDto.setYearReport(paramDto.getFromYear());
				    		importExportAnimalDto.setMonthReport(paramDto.getFromMonth());
						}
				    	ret.addAll(subRet);
				    }
				    
				    fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);		
				    if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getToMonth(), paramDto.getToYear());
					}
				    toDate= WLDateTimeUtil.getLastDayOfMonth(paramDto.getToMonth(), paramDto.getToYear());
				    paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportAnimalDto importExportAnimalDto : subRet) {
				    		importExportAnimalDto.setYearReport(paramDto.getToYear());
				    		importExportAnimalDto.setMonthReport(paramDto.getToMonth());
						}
				    	ret.addAll(subRet);
				    }
				}
			}
			return ret;
		}
		return null;
	}
	
	@Override
	public List<InventoryReportDto> getSumQuantity(ReportParamDto paramDto){
		
		String sql=" SELECT ";
		if(paramDto.getType()!=null && 
				(paramDto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() || 
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			sql+= "SUM(iea.quantity),SUM(iea.amount) %s ";//Tính tổng xuất hoặc nhập đàn
		}
		else 
		{
			sql+= "SUM(iea.quantity * iea.type),SUM(iea.amount * iea.type) %s ";//Tính tồn kho
		}
		sql+= "FROM ImportExportAnimal iea WHERE 1=1 ";
		
		String whereClause="";
		if(paramDto.getFromDate()!=null) {
			whereClause+=" AND iea.dateIssue >= :fromDate ";
		}
		if(paramDto.getToDate()!=null) {
			whereClause+=" AND iea.dateIssue<= :toDate ";
		}
		if(paramDto.getBatchCode()!=null && paramDto.getBatchCode()!="") {
			whereClause+=" AND iea.batchCode = :batchCode ";
		}
		if(paramDto.getListAnimalIds()!=null && paramDto.getListAnimalIds().size( )>0) {
			whereClause+=" AND iea.animal.id in (:listAnimalIds) ";
		}
		if(paramDto.getAnimalTypeId()!=null && paramDto.getAnimalTypeId()>0) {
			whereClause+=" AND iea.animal.type.id = :animalTypeId ";
		}
		if(paramDto.getAnimalParentId()!=null && paramDto.getAnimalParentId()>0) {
			whereClause+=" AND iea.animal.parent.id = :animalParentId ";
		}
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {
			whereClause+=" AND iea.farm.id = :farmId ";
		}		
		if(paramDto.getProductTargetId()!=null && paramDto.getProductTargetId()>0) {
			whereClause+=" AND iea.productTarget.id = :productTargetId ";
		}		
		if(paramDto.getOriginalId()!=null && paramDto.getOriginalId()>0) {
			whereClause+=" AND iea.original.id = :originalId ";
		}
		if(paramDto.getWardId()!=null && paramDto.getWardId()>0) {
			whereClause+=" AND iea.farm.administrativeUnit.id = :wardId ";
		}
		if(paramDto.getDistrictId()!=null && paramDto.getDistrictId()>0) {
			whereClause+=" AND iea.farm.administrativeUnit.parent.id = :districtId ";
		}
		if(paramDto.getProvinceId()!=null && paramDto.getProvinceId()>0) {
			whereClause+=" AND iea.farm.administrativeUnit.parent.parent.id = :provinceId ";
		}
		if(paramDto.getRegionId()!=null && paramDto.getRegionId()>0) {
			whereClause+=" AND iea.farm.administrativeUnit.parent.parent.region.id = :regionId ";
		}	
		if(paramDto.getExportReason()!=null && paramDto.getExportReason().length()>0) {
			whereClause+=" AND iea.exportReason = :exportReason ";
		}
		if(paramDto.getExportType()>0) {
			whereClause+=" AND iea.exportType = :exportType ";			
		}
		if(paramDto.getType()!=null && 
				(paramDto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() || 
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			whereClause+=" AND iea.type = :type ";
		}
		if(paramDto.getIsLevelCommodity()!=null && paramDto.getIsLevelCommodity()) {
			whereClause+=" AND iea.seedLevel.id is null ";
		}
		else if(paramDto.getSeedLevelId()!=null && paramDto.getSeedLevelId()>0 && ((paramDto.getIsLevelCommodity()==null ||paramDto.getIsLevelCommodity()!=null && paramDto.getIsLevelCommodity()==false))) {
			whereClause+=" AND iea.seedLevel.id = :seedLevelId ";
		}
		
		
		String animal = " ";
		String animalParent = " ";
		String animalType = " ";
		String batchCode = " ";
		String farm = " ";
		String original = " ";
		String productTarget = " ";
		String ward = " ";
		String district = " ";
		String province = " ";
		String region = " ";
		String exportReason = " ";
		String seedLevel=" ";
		
		String orderByClause="";
		
		List<String> columns = new ArrayList<String>();
		columns.add(WLConstant.FunctionAndGroupByItem.quantity.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.amount.getValue());
		
		String groupByClause="";
		String selectClause="";
		if(paramDto.getGroupByItems()!=null && paramDto.getGroupByItems().size()>0) {
			
			for (String grItem : paramDto.getGroupByItems()) {
				if(WLConstant.FunctionAndGroupByItem.animal.getValue().equals(grItem)) {
					groupByClause+=" iea.animal.id,iea.animal.name, ";
					animal = " ,iea.animal.id,iea.animal.name ";
					selectClause+=animal;
					columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.animalParent.getValue().equals(grItem)) {
					groupByClause+=" iea.animal.parent.id,iea.animal.parent.name, ";	
					animalParent=" ,iea.animal.parent.id,iea.animal.parent.name ";	
					selectClause+=animalParent;
					columns.add(WLConstant.FunctionAndGroupByItem.animalParent.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.animalParent.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.animalType.getValue().equals(grItem)) {
					groupByClause+=" iea.animal.type.id,iea.animal.type.name, ";
					animalType=" ,iea.animal.type.id,iea.animal.type.name ";
					selectClause+=animalType;
					columns.add(WLConstant.FunctionAndGroupByItem.animalType.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.animalType.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.batchCode.getValue().equals(grItem)) {
					groupByClause+=" iea.batchCode, ";
					batchCode=" ,iea.batchCode ";
					selectClause+=batchCode;
					columns.add(WLConstant.FunctionAndGroupByItem.batchCode.getValue());
				}
				if(WLConstant.FunctionAndGroupByItem.farm.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.id,iea.farm.name, ";
					farm=" ,iea.farm.id,iea.farm.name ";
					selectClause+=farm;
					columns.add(WLConstant.FunctionAndGroupByItem.farm.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.farm.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.original.getValue().equals(grItem)) {
					groupByClause+=" iea.original.id,iea.original.name, ";
					original=" ,iea.original.id,iea.original.name ";
					selectClause+=original;
					columns.add(WLConstant.FunctionAndGroupByItem.original.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.original.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.productTarget.getValue().equals(grItem)) {
					groupByClause+=" iea.productTarget.id,iea.productTarget.name,iea.productTarget.code, ";
					productTarget=" ,iea.productTarget.id,iea.productTarget.name,iea.productTarget.code ";
					selectClause+=productTarget;
					columns.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue()+"name");
					columns.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue()+"code");
				}
				if(WLConstant.FunctionAndGroupByItem.ward.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.administrativeUnit.id,iea.farm.administrativeUnit.name, ";
					ward=" ,iea.farm.administrativeUnit.id,iea.farm.administrativeUnit.name ";
					selectClause+=ward;
					columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.district.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.administrativeUnit.parent.id,iea.farm.administrativeUnit.parent.name, ";
					district=" ,iea.farm.administrativeUnit.parent.id,iea.farm.administrativeUnit.parent.name ";
					selectClause+=district;
					columns.add(WLConstant.FunctionAndGroupByItem.district.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.district.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.province.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.administrativeUnit.parent.parent.id,iea.farm.administrativeUnit.parent.parent.name, ";
					province=" ,iea.farm.administrativeUnit.parent.parent.id,iea.farm.administrativeUnit.parent.parent.name ";
					selectClause+=province;
					columns.add(WLConstant.FunctionAndGroupByItem.province.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.province.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.region.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.administrativeUnit.parent.parent.region.id,iea.farm.administrativeUnit.parent.parent.region.name, ";
					region=" ,iea.farm.administrativeUnit.parent.parent.region.id,iea.farm.administrativeUnit.parent.parent.region.name ";
					selectClause+=region;
					columns.add(WLConstant.FunctionAndGroupByItem.region.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.region.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.seedLevel.getValue().equals(grItem)) {
					groupByClause+=" iea.seedLevel.id,iea.seedLevel.name,iea.seedLevel.level, ";
					seedLevel = " ,iea.seedLevel.id,iea.seedLevel.name,iea.seedLevel.level  ";
					selectClause+=seedLevel;
					columns.add(WLConstant.FunctionAndGroupByItem.seedLevel.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.seedLevel.getValue()+"name  ");
					columns.add(WLConstant.FunctionAndGroupByItem.seedLevel.getValue()+"level");
				}
				if(WLConstant.FunctionAndGroupByItem.exportReason.getValue().equals(grItem)) {
					groupByClause+=" iea.exportReason, ";
					exportReason=" ,iea.exportReason ";
					selectClause+=exportReason;
					columns.add(WLConstant.FunctionAndGroupByItem.exportReason.getValue());
				}
				
			}
		}
		sql = String.format(sql, selectClause);
		if(groupByClause!="") {
			groupByClause = " GROUP BY "+ groupByClause.substring(0, groupByClause.length() - 2);
		}	
		
		sql = sql + whereClause + groupByClause;
		System.out.println(sql);
		Query q = manager.createQuery(sql);
		
		if(paramDto.getFromDate()!=null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if(paramDto.getToDate()!=null) {
			q.setParameter("toDate", paramDto.getToDate());
		}
		if(paramDto.getBatchCode()!=null && paramDto.getBatchCode()!="") {
			q.setParameter("batchCode", paramDto.getBatchCode());
		}
		if(paramDto.getListAnimalIds()!=null && paramDto.getListAnimalIds().size()>0) {
			q.setParameter("listAnimalIds", paramDto.getListAnimalIds());
		}
		if(paramDto.getAnimalTypeId()!=null && paramDto.getAnimalTypeId()>0) {
			q.setParameter("animalTypeId", paramDto.getAnimalTypeId());
		}
		if(paramDto.getAnimalParentId()!=null && paramDto.getAnimalParentId()>0) {
			q.setParameter("animalParentId", paramDto.getAnimalParentId());
		}
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {		
			q.setParameter("farmId", paramDto.getFarmId());
		}		
		if(paramDto.getOriginalId()!=null && paramDto.getOriginalId()>0) {
			q.setParameter("productTargetId", paramDto.getOriginalId());
		}		
		if(paramDto.getOriginalId()!=null && paramDto.getOriginalId()>0) {
			q.setParameter("originalId", paramDto.getOriginalId());
		}
		if(paramDto.getWardId()!=null && paramDto.getWardId()>0) {
			q.setParameter("wardId", paramDto.getWardId());
		}
		if(paramDto.getDistrictId()!=null && paramDto.getDistrictId()>0) {
			q.setParameter("districtId", paramDto.getDistrictId());
		}
		if(paramDto.getProvinceId()!=null && paramDto.getProvinceId()>0) {
			q.setParameter("provinceId", paramDto.getProvinceId());
		}
		if(paramDto.getRegionId()!=null && paramDto.getRegionId()>0) {
			q.setParameter("regionId", paramDto.getRegionId());
		}
		if(paramDto.getExportReason()!=null && paramDto.getExportReason().length()>0){
			q.setParameter("exportReason", paramDto.getExportReason());
		}
		if(paramDto.getExportType()>0) {
			q.setParameter("exportType", paramDto.getExportType());	
		}		
		if(paramDto.getType()!=null && 
				(paramDto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() || 
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			q.setParameter("type", paramDto.getType());			
		}
		if(paramDto.getSeedLevelId()!=null && paramDto.getSeedLevelId()>0 && ((paramDto.getIsLevelCommodity()==null ||paramDto.getIsLevelCommodity()!=null && paramDto.getIsLevelCommodity()==false))) {
			q.setParameter("seedLevelId", paramDto.getSeedLevelId());	
		}
		
		
		List<Object[]> results = q.getResultList();
		List<InventoryReportDto> ret = new ArrayList<InventoryReportDto>();
		
		if(results!=null) {
			for (Object[] re : results) {
				InventoryReportDto io = new InventoryReportDto(re,columns);				
				ret.add(io);
			}
		}
		return ret;
	}

	@Override
	public List<ReportSeedLevelDto> getListReportSeedLevel(ReportParamDto paramDto) throws ParseException {
		List<ReportSeedLevelDto> ret=new ArrayList<ReportSeedLevelDto>();
		List<InventoryReportDto> list=new ArrayList<InventoryReportDto>();
		Hashtable<Long, ReportSeedLevelDto> hashTables = new Hashtable<Long, ReportSeedLevelDto>();
		list=this.getQuantityReport(paramDto);
		if(list!=null && list.size()>0) {
			for (InventoryReportDto inventoryReportDto : list) {
				ReportSeedLevelDto dto = hashTables.get(inventoryReportDto.getProvinceId());
				if(dto==null) {
						dto=new ReportSeedLevelDto();
						dto.setProvinceId(inventoryReportDto.getProvinceId());//id tỉnh
						dto.setProvinceName(inventoryReportDto.getProvinceName());//tên tỉnh
					FmsAdministrativeUnit au=fmsAdministrativeUnitRepository.findById(inventoryReportDto.getProvinceId());
					if(au!=null && au.getRegion()!=null) {
						dto.setProvinceName(au.getCode()+"-"+au.getName());
						dto.setRegionId(au.getRegion().getId());
						dto.setRegionName(au.getRegion().getName());
					}
					//gà
					if(inventoryReportDto.getParentlName().toLowerCase().contains("Gà".toLowerCase())) {
						if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.grandParents.getValue())){
							dto.setAmountChickenGrandparents(inventoryReportDto.getQuantity());
						}else if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.parents.getValue())){
							dto.setAmountChickenParents(inventoryReportDto.getQuantity());
						}
							
					//vịt
					}else if(inventoryReportDto.getParentlName().toLowerCase().contains("vịt".toLowerCase())) {
						if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.grandParents.getValue())){
							dto.setAmountDuckGrandparents(inventoryReportDto.getQuantity());
						}else if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.parents.getValue())){
							dto.setAmountDuckParents(inventoryReportDto.getQuantity());
						}				
					}else {//khác
						if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.grandParents.getValue())){
							dto.setAmountOtherGrandparents(inventoryReportDto.getQuantity());
						}else if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.parents.getValue())){
							dto.setAmountOtherParents(inventoryReportDto.getQuantity());
						}				
					}
					hashTables.put(inventoryReportDto.getProvinceId(), dto);
					ret.add(dto);
				}else {
					for (ReportSeedLevelDto rd : ret) {
						if(rd.getProvinceId().equals(inventoryReportDto.getProvinceId())) {
							//gà
							if(inventoryReportDto.getParentlName().toLowerCase().contains("Gà".toLowerCase())) {
								if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.grandParents.getValue())){
									if(rd.getAmountChickenGrandparents()!=null)
										rd.setAmountChickenGrandparents(rd.getAmountChickenGrandparents()+inventoryReportDto.getQuantity());
									else
										rd.setAmountChickenGrandparents(inventoryReportDto.getQuantity());
								}else if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.parents.getValue())){
									if(rd.getAmountChickenParents()!=null)
										rd.setAmountChickenParents(rd.getAmountChickenParents()+inventoryReportDto.getQuantity());
									else
										rd.setAmountChickenParents(inventoryReportDto.getQuantity());
								}
									
							//vịt
							}else if(inventoryReportDto.getParentlName().toLowerCase().contains("vịt".toLowerCase())) {
								if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.grandParents.getValue())){
									if(rd.getAmountDuckGrandparents()!=null)
										rd.setAmountDuckGrandparents(rd.getAmountDuckGrandparents()+inventoryReportDto.getQuantity());
									else {
										rd.setAmountDuckGrandparents(inventoryReportDto.getQuantity());
									}
								}else if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.parents.getValue())){
									if(rd.getAmountDuckParents()!=null)
										rd.setAmountDuckParents(rd.getAmountDuckParents()+inventoryReportDto.getQuantity());
									else {
										rd.setAmountDuckParents(inventoryReportDto.getQuantity());
									}
								}				
							}else {//khác
								if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.grandParents.getValue())){
									if(rd.getAmountOtherGrandparents()!=null)
										rd.setAmountOtherGrandparents(rd.getAmountOtherGrandparents()+inventoryReportDto.getQuantity());
									else
										rd.setAmountOtherGrandparents(inventoryReportDto.getQuantity());
								}else if(inventoryReportDto.getSeedLevelLevel()!=null && inventoryReportDto.getSeedLevelLevel().equals(WLConstant.SeedLevel.parents.getValue())){
									if(rd.getAmountOtherParents()!=null)
										rd.setAmountOtherParents(rd.getAmountOtherParents()+inventoryReportDto.getQuantity());
									else
										rd.setAmountOtherParents(inventoryReportDto.getQuantity());
								}				
							}
						}
					}
				}
			}
			
			for (ReportSeedLevelDto rd : ret) {
				double totalAmountGrandParents=0;
				double totalAmountParents=0;
				if(rd.getAmountChickenGrandparents()!=null) {
					totalAmountGrandParents=totalAmountGrandParents+rd.getAmountChickenGrandparents();
				}
				if(rd.getAmountDuckGrandparents()!=null) {
					totalAmountGrandParents=totalAmountGrandParents+rd.getAmountDuckGrandparents();
				}
				if(rd.getAmountOtherGrandparents()!=null) {
					totalAmountGrandParents=totalAmountGrandParents+rd.getAmountOtherGrandparents();
				}
				rd.setTotalAmountGrandparents(totalAmountGrandParents);
				if(rd.getAmountChickenParents()!=null) {
					totalAmountParents=totalAmountParents+rd.getAmountChickenParents();
				}
				if(rd.getAmountDuckParents()!=null) {
					totalAmountParents=totalAmountParents+rd.getAmountDuckParents();
				}
				if(rd.getAmountOtherParents()!=null) {
					totalAmountParents=totalAmountParents+rd.getAmountOtherParents();
				}
				rd.setTotalAmountParents(totalAmountParents);
			}
					
		}
		return ret;
	}

	@Override
	public List<ReportSeedLevelProducTargetDto> getListReportSeedLevelProductTarget(ReportParamDto paramDto)
			throws ParseException, UnsupportedEncodingException {
		List<ReportSeedLevelProducTargetDto> ret=new ArrayList<ReportSeedLevelProducTargetDto>();
		List<InventoryReportDto> list=new ArrayList<InventoryReportDto>();
		Hashtable<Long, ReportSeedLevelProducTargetDto> hashTables = new Hashtable<Long, ReportSeedLevelProducTargetDto>();		
		list=this.getQuantityReport(paramDto);
		if(list!=null && list.size()>0) {
			for (InventoryReportDto inventoryReportDto : list) {
				ReportSeedLevelProducTargetDto dto = hashTables.get(inventoryReportDto.getProvinceId());
				if(dto==null) {
					dto=new ReportSeedLevelProducTargetDto();
					dto.setProvinceId(inventoryReportDto.getProvinceId());//id tỉnh
					dto.setProvinceName(inventoryReportDto.getProvinceName());//tên tỉnh
					FmsAdministrativeUnit au=fmsAdministrativeUnitRepository.findById(inventoryReportDto.getProvinceId());
					if(au!=null && au.getRegion()!=null) {
						dto.setProvinceName(au.getCode()+"-"+au.getName());
						dto.setRegionId(au.getRegion().getId());
						dto.setRegionName(au.getRegion().getName());
					}
					//gà
					if(inventoryReportDto.getParentlName().toLowerCase().contains("Gà".toLowerCase())) {
						if(inventoryReportDto.getProductTargetCode()!=null && inventoryReportDto.getProductTargetCode().toLowerCase().contains(WLConstant.ProductTargetCode.MEAT.getValue().toLowerCase())){
							dto.setAmountChickenMeat(inventoryReportDto.getQuantity());								
						}else if(inventoryReportDto.getProductTargetCode()!=null && WLStringUtil.compareUTF8String(inventoryReportDto.getProductTargetCode(),WLConstant.ProductTargetCode.EGG.getValue().toLowerCase())) {
//								&& inventoryReportDto.getProductTargetCode().toLowerCase().contains(FMSConstant.ProductTargetCode.EGG.getValue().toLowerCase().toLowerCase())){
							dto.setAmountChickenEggs(inventoryReportDto.getQuantity());								
						}
							
					//vịt
					}else if(inventoryReportDto.getParentlName().toLowerCase().contains("vịt".toLowerCase())) {
						if(inventoryReportDto.getProductTargetCode()!=null && inventoryReportDto.getProductTargetCode().toLowerCase().contains(WLConstant.ProductTargetCode.MEAT.getValue().toLowerCase())){
							dto.setAmountDuckMeat(inventoryReportDto.getQuantity());
						}else if(inventoryReportDto.getProductTargetCode()!=null  && WLStringUtil.compareUTF8String(inventoryReportDto.getProductTargetCode(),WLConstant.ProductTargetCode.EGG.getValue().toLowerCase())) {
								//&& inventoryReportDto.getProductTargetCode().toLowerCase().contains(FMSConstant.ProductTargetCode.EGG.getValue().toLowerCase().toLowerCase())){
							dto.setAmountDuckEggs(inventoryReportDto.getQuantity());
						}				
					}else {//khác
						if(inventoryReportDto.getProductTargetCode()!=null && inventoryReportDto.getProductTargetCode().toLowerCase().contains(WLConstant.ProductTargetCode.MEAT.getValue().toLowerCase())){
							dto.setAmountOtherMeat(inventoryReportDto.getQuantity());
								
						}else if(inventoryReportDto.getProductTargetCode()!=null && WLStringUtil.compareUTF8String(inventoryReportDto.getProductTargetCode(),WLConstant.ProductTargetCode.EGG.getValue().toLowerCase())) { 
								//&& inventoryReportDto.getProductTargetCode().toLowerCase().contains(FMSConstant.ProductTargetCode.EGG.getValue().toLowerCase().toLowerCase())){
							dto.setAmountOtherEggs(inventoryReportDto.getQuantity());
						}				
					}
					hashTables.put(inventoryReportDto.getProvinceId(), dto);
					ret.add(dto);
				}else {
					for (ReportSeedLevelProducTargetDto rd : ret) {
						if(rd.getProvinceId().equals(inventoryReportDto.getProvinceId())) {
							//gà
							if(inventoryReportDto.getParentlName().toLowerCase().contains("Gà".toLowerCase())) {
								if(inventoryReportDto.getProductTargetCode()!=null && inventoryReportDto.getProductTargetCode().toLowerCase().contains(WLConstant.ProductTargetCode.MEAT.getValue().toLowerCase())){
									if(rd.getAmountChickenMeat()!=null)
										rd.setAmountChickenMeat(rd.getAmountChickenMeat()+inventoryReportDto.getQuantity());
									else
										rd.setAmountChickenMeat(inventoryReportDto.getQuantity());
								}else if(inventoryReportDto.getProductTargetCode()!=null  && WLStringUtil.compareUTF8String(inventoryReportDto.getProductTargetCode(),WLConstant.ProductTargetCode.EGG.getValue().toLowerCase())) {
										//&& inventoryReportDto.getProductTargetCode().toLowerCase().contains(FMSConstant.ProductTargetCode.EGG.getValue().toLowerCase().toLowerCase())){
									if(rd.getAmountChickenEggs()!=null)
										rd.setAmountChickenEggs(rd.getAmountChickenEggs()+inventoryReportDto.getQuantity());
									else
										rd.setAmountChickenEggs(inventoryReportDto.getQuantity());
								}
									
							//vịt
							}else if(inventoryReportDto.getParentlName().toLowerCase().contains("vịt".toLowerCase())) {
								if(inventoryReportDto.getProductTargetCode()!=null && inventoryReportDto.getProductTargetCode().toLowerCase().contains(WLConstant.ProductTargetCode.MEAT.getValue().toLowerCase())){
									if(rd.getAmountDuckMeat()!=null)
										rd.setAmountDuckMeat(rd.getAmountDuckMeat()+inventoryReportDto.getQuantity());
									else {
										rd.setAmountDuckMeat(inventoryReportDto.getQuantity());
									}
								}else if(inventoryReportDto.getProductTargetCode()!=null  && WLStringUtil.compareUTF8String(inventoryReportDto.getProductTargetCode(),WLConstant.ProductTargetCode.EGG.getValue().toLowerCase())) {
										//&& inventoryReportDto.getProductTargetCode().toLowerCase().contains(FMSConstant.ProductTargetCode.EGG.getValue().toLowerCase().toLowerCase())){
									if(rd.getAmountDuckEggs()!=null)
										rd.setAmountDuckEggs(rd.getAmountDuckEggs()+inventoryReportDto.getQuantity());
									else {
										rd.setAmountDuckEggs(inventoryReportDto.getQuantity());
									}
								}				
							}else {//khác
								if(inventoryReportDto.getProductTargetCode()!=null && inventoryReportDto.getProductTargetCode().toLowerCase().contains(WLConstant.ProductTargetCode.MEAT.getValue().toLowerCase())){
									if(rd.getAmountOtherMeat()!=null)
										rd.setAmountOtherMeat(rd.getAmountOtherMeat()+inventoryReportDto.getQuantity());
									else
										rd.setAmountOtherMeat(inventoryReportDto.getQuantity());
								}else if(inventoryReportDto.getProductTargetCode()!=null && WLStringUtil.compareUTF8String(inventoryReportDto.getProductTargetCode(),WLConstant.ProductTargetCode.EGG.getValue().toLowerCase())) {
//										&& inventoryReportDto.getProductTargetCode().toLowerCase().contains(FMSConstant.ProductTargetCode.EGG.getValue().toLowerCase().toLowerCase())){
									if(rd.getAmountOtherEggs()!=null)
										rd.setAmountOtherEggs(rd.getAmountOtherEggs()+inventoryReportDto.getQuantity());
									else
										rd.setAmountOtherEggs(inventoryReportDto.getQuantity());
								}				
							}
						}
					}
				}
			}
			
			for (ReportSeedLevelProducTargetDto rd : ret) {
				double totalAmountMeat=0;
				double totalAmountEggs=0;
				if(rd.getAmountChickenMeat()!=null) {
					totalAmountMeat=totalAmountMeat+rd.getAmountChickenMeat();
				}
				if(rd.getAmountDuckMeat()!=null) {
					totalAmountMeat=totalAmountMeat+rd.getAmountDuckMeat();
				}
				if(rd.getAmountOtherMeat()!=null) {
					totalAmountMeat=totalAmountMeat+rd.getAmountOtherMeat();
				}
				rd.setTotalAmountMeat(totalAmountMeat);
				if(rd.getAmountChickenEggs()!=null) {
					totalAmountEggs=totalAmountEggs+rd.getAmountChickenEggs();
				}
				if(rd.getAmountDuckEggs()!=null) {
					totalAmountEggs=totalAmountEggs+rd.getAmountDuckEggs();
				}
				if(rd.getAmountOtherEggs()!=null) {
					totalAmountEggs=totalAmountEggs+rd.getAmountOtherEggs();
				}
				rd.setTotalAmountEggs(totalAmountEggs);
			}
		}
		return ret;
	}

	

	
}
