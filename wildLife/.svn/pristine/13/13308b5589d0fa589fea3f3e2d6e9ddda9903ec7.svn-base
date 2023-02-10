package com.globits.wl.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.naming.spi.DirStateFactory.Result;
import javax.persistence.Query;

import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.Constants;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.security.service.RoleService;
import com.globits.wl.WLUtil;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmAnimalProductTargetExist;
import com.globits.wl.domain.FarmProductTargetExist;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.IndividualAnimal;
import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.domain.InjectionTime;
import com.globits.wl.domain.LiveStockProduct;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.domain.SeedLevel;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.IndividualAnimalDto;
import com.globits.wl.dto.InjectionPlantDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.AnimalRaisingSearchDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.FarmAnimal2017Dto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.functiondto.ReportPercentOfGrowthDto;
import com.globits.wl.dto.report.AnimalRaisingReportDto;
import com.globits.wl.dto.report.AnimalRaisingSymbolNameDto;
import com.globits.wl.dto.report.FarmSummaryReportDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.LiveStockProductReportDto;
import com.globits.wl.dto.report.Report16FormDto;
import com.globits.wl.dto.report.TotalReportDto;
import com.globits.wl.dto.report.TotalReportSubDetailDto;
import com.globits.wl.dto.report.TotalReportSubDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.AnimalTypeRepository;
import com.globits.wl.repository.FarmAnimalProductTargetExistRepository;
import com.globits.wl.repository.FarmProductTargetExistRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.repository.IndividualAnimalRepository;
import com.globits.wl.repository.InjectionPlantRepository;
import com.globits.wl.repository.InjectionTimeRepository;
import com.globits.wl.repository.LiveStockProductRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.repository.SeedLevelRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.ExportEggService;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.service.ImportExportLivelStockProductService;
import com.globits.wl.service.ReportForm16Service;
import com.globits.wl.utils.FarmMapServiceUtil;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ImportExportAnimalServiceImpl extends GenericServiceImpl<ImportExportAnimal, Long> implements ImportExportAnimalService {

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
	@Autowired
	private AnimalService animalService;
	
	@Autowired
	private ExportEggService exportEggService;
	@Autowired
	ImportExportLivelStockProductService importExportLivelStockProductService;
	@Autowired
	LiveStockProductRepository livelStockProductRepository;
	@Autowired
	RoleService roleService;
	@Autowired
	private IndividualAnimalRepository individualAnimalRepository;
	@Autowired
	private AnimalTypeRepository animalTypeRepository;
	
	@Autowired
	private AnimalReportDataService animalReportDataService;
	
	@Autowired
	ReportForm16Service reportForm16Service;
	
	@Override
	public Page<ImportExportAnimalDto> getByPage(int pageIndex, int pageSize,int type) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.importAnimalRepository.getByPage(pageable,type);
	}

	@Override
	public Set<ImportExportAnimalDto> getAll(int type) {
		return this.importAnimalRepository.getAll(type);
	}
	
	@Override
	public ImportExportAnimalDto getById(Long id) {
		ImportExportAnimalDto ret=new ImportExportAnimalDto();
		ret=this.importAnimalRepository.getById(id);
		//trường hợp phiếu xuất chuyển loại thì get hướng sản phẩm chuyển loại khi tạo phiếu nhập chuyển loại mới
		if(ret!=null && ret.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() && ret.getIsExChange()!=null && ret.getIsExChange()) {
			List<ImportExportAnimal> list=this.importAnimalRepository.getImportChangeByExportExChange(ret.getId(), ret.getIsExChange());
			if(list!=null && list.size()>0) {
				if(list.get(0).getProductTarget()!=null) {
					ProductTargetDto ptDto=new ProductTargetDto();
					ptDto.setId(list.get(0).getProductTarget().getId());
					ptDto.setCode(list.get(0).getProductTarget().getCode());
					ptDto.setName(list.get(0).getProductTarget().getName());
					ret.setProductTargetChange(ptDto);
				}
			}
		}
		return ret;
	}

	@Override
	public ObjectDto deleteById(Long id) {
		ObjectDto dto=new ObjectDto();
		if (id != null) {
			ImportExportAnimal iea=this.importAnimalRepository.findById(id);	
			if(iea!=null){
				if(iea.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()){
					if(iea.getImportAnimal()!=null && iea.getImportAnimal().getId()!=null){
						ImportExportAnimal im=this.importAnimalRepository.findById(iea.getImportAnimal().getId());
						if(im!=null && iea.getQuantity()>0){
							double remain=im.getRemainQuantity() + iea.getQuantity();
							im.setRemainQuantity(remain);
							this.importAnimalRepository.save(im);
						}
					}
				}else if(iea.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {					
					if(iea!=null && iea.getId()!=null && iea.getFarm()!=null &&  iea.getFarm().getId()!=null && iea.getBatchCode()!=null){
						List<ImportExportAnimal> list=this.importAnimalRepository.findBy(WLConstant.ImportExportAnimalType.exportAnimal.getValue(), iea.getFarm().getId(), iea.getBatchCode());
						if(list!=null && list.size()>0) {
							dto.setCode("-2");
							dto.setName("Không thể xóa bản ghi nhập đàn vì còn dữ liệu xuất đàn!");
							return dto;
						}
					}
				}
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				User modifiedUser = (User) authentication.getPrincipal();
				Date currentDate = new Date();
				long diffInMillies = Math.abs(currentDate.getTime() - iea.getCreateDate().toDate().getTime());
			    long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    //Nếu quá khoảng thời gian quy định, chỉ tài khoản admin mới được quyền xóa dữ liệu
				if(diff>=WLConstant.TimeEditData && !SecurityUtils.isUserInRole(modifiedUser, WLConstant.ROLE_ADMIN) ) {
					dto.setCode("-2");
					dto.setName("Quá khoảng thời gian qui định, chỉ tài khoản admin mới có quyền xóa dữ liệu!");
					return dto;
				}
				this.importAnimalRepository.delete(id);	
				//cập nhật lại số tồn hiện tại của cơ sở chăn nuôi khi xóa
				if(iea.getFarm()!=null && iea.getFarm().getId()!=null){
					farmService.countBalanceNumberByFarm(iea.getFarm().getId());
				}
				//cập nhật số tồn theo hướng sản phẩm
				updateQuantityFarmProductTarget(iea);
				//cập nhật tồn theo hướng sản phẩm + vật nuôi
				updateQuantityFarmAnimalProductTargetExist(iea);
				return dto;
			}
			
		}
		return dto;
	}

	@Override
	public ImportExportAnimalDto save(Long id, ImportExportAnimalDto dto) {
		if (dto != null) {
			//check list individual animal code duplicate
//			if(dto.getIndividualAnimals()!= null && dto.getIndividualAnimals().size() > 0) {
//				int len = dto.getIndividualAnimals().size();
//				for(int index1 = 0; index1 < len-1; index1++ ) {
//					for(int index2 = index1+1; index2 < len; index2++ ) {
//						IndividualAnimalDto individualAnimalDto1 = dto.getIndividualAnimals().get(index1);
//						IndividualAnimalDto individualAnimalDto2 = dto.getIndividualAnimals().get(index2);
//						if(individualAnimalDto1 != null && individualAnimalDto2 != null && individualAnimalDto1.getCode().equals(individualAnimalDto2.getCode())){
//							dto.setDupCodeIndividualAnimals(true);
//							dto.getIndividualAnimalDupCode().add(individualAnimalDto1.getCode());
//							continue;
//						}
//					}
//				}
//				if(dto.isDupCodeIndividualAnimals()) {
//					return dto;
//				}
//			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			boolean isNew=false;
			double remainQuantity=0;//số lượng còn lại
			ImportExportAnimal old=null;
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			double firstQuantity = 0d;
			double firstMale = 0d;
			double firstFemale = 0d;
			double firstUngender = 0d;
			ImportExportAnimal importExportAnimal = null;
			ImportExportAnimal importRecord = null;//Phiếu nhập, trong trường hợp xuất đàn
			//Check các điều kiện, nếu không đạt thì không lưu lại
			if(dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue() 
					&&( dto.getFarm()==null 
					|| dto.getDateImport()==null 
					|| dto.getAnimal()==null 
//					|| dto.getProductTarget()==null 
					|| Double.valueOf(dto.getQuantity())==null  
					|| dto.getQuantity()<=0
//					|| Double.valueOf(dto.getDayOld())==null 
//					|| dto.getDayOld()<=0
//					|| Double.valueOf(dto.getLifeCycle())==null 
//					|| dto.getLifeCycle()<=0
					)) {
				return null;
			}
			else if(dto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(dto.getFarm()==null 
						|| dto.getDateExport()==null
						|| Double.valueOf(dto.getQuantity())==null  
						|| dto.getQuantity()<=0) {
					return null;
				}
				if(dto.getImportAnimal()!=null
						&& dto.getImportAnimal().getId()!=null
						&& dto.getImportAnimal().getId()>0L) {
					importRecord = importAnimalRepository.findOne(dto.getImportAnimal().getId());
				}
//				if(importRecord==null) {//Nếu không thấy phiếu nhập (trong trường hợp xuất đàn) thì trả về null
//					return null;
//				}
//				else {
//					
//				}
				if(importRecord != null) {
					remainQuantity = this.checkRemainQuantity(importRecord.getId());
				}
			}

			if(id!=null) {
				importExportAnimal = this.importAnimalRepository.findOne(id);
				old=this.importAnimalRepository.findOne(id);;
			}
			else if (dto.getId() != null) {
				importExportAnimal = this.importAnimalRepository.findOne(dto.getId());
				old=this.importAnimalRepository.findOne(dto.getId());
			}
			if(importExportAnimal!= null && importExportAnimal.getQuantity() > 0) {
				firstQuantity = importExportAnimal.getQuantity();
				firstMale = importExportAnimal.getMale();
				firstFemale = importExportAnimal.getFemale();
				firstUngender = importExportAnimal.getUnGender();
			}
			
			ReportParamDto reportParamDto = new ReportParamDto();
			reportParamDto.setAnimalReportDataType(dto.getAnimalReportDataType());
			reportParamDto.setAnimalId(dto.getAnimal().getId());
			reportParamDto.setFarmId(dto.getFarm().getId());
			InventoryReportDto inventoryReportDto = this.getInventoryReportRemainQuantity(reportParamDto);
			if(inventoryReportDto != null && dto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(dto.getMale() > inventoryReportDto.getMale() + firstMale ) {
					return dto;
				}
				if(dto.getFemale() > inventoryReportDto.getFemale() + firstFemale ) {
					return dto;
				}
				if(dto.getUnGender() > inventoryReportDto.getUnGender() + firstUngender ) {
					return dto;
				}
			}
			
			if(importExportAnimal != null) {
				long diffInMillies = Math.abs(currentDate.toDate().getTime() - importExportAnimal.getCreateDate().toDate().getTime());
			    long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    //Nếu quá khoảng thời gian quy định, chỉ tài khoản admin mới được quyền sửa dữ liệu
				if(diff>=WLConstant.TimeEditData && !SecurityUtils.isUserInRole(modifiedUser, WLConstant.ROLE_ADMIN) ) {
					return null;
				}
			}				
			else if (importExportAnimal == null) {
				importExportAnimal = new ImportExportAnimal();
				isNew=true;
				importExportAnimal.setCreateDate(currentDate);
				importExportAnimal.setCreatedBy(currentUserName);
				
				importExportAnimal.setModifiedBy(currentUserName);
				importExportAnimal.setModifyDate(currentDate);
			}
			importExportAnimal.setGender(dto.getGender());
			importExportAnimal.setType(dto.getType());
//			if(dto.getAnimal()!= null && dto.getAnimal().getParent() != null && dto.getAnimal().getParent().getCode().equals(FMSConstant.BEAR_CODE)  && dto.getChipCode() != null) {
//				List<ImportExportAnimal> listAnimalWithChipCode = importAnimalRepository.getListAnimalWithChipCodeAndType(dto.getChipCode(), FMSConstant.ImportExportAnimalType.importAnimal.getValue());
//				if(listAnimalWithChipCode != null && listAnimalWithChipCode.size() > 0) {
//					dto.setChipCode(FMSConstant.DUPLICATE_CHIP_CODE);
//					return dto;
//				}
//			}
			importExportAnimal.setChipCode(dto.getChipCode());
			importExportAnimal.setIsBornAtFarm(dto.getIsBornAtFarm());
			//Nếu số lượng nhập đàn nhiều hơn số lượng còn lại thì trả về null, đã tổng quát cả trường hợp sửa và thêm mới
//			if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()
//					&& dto.getQuantity()>remainQuantity+importExportAnimal.getQuantity()) {
//				return null;
//			}
			
			if (dto.getFarm() != null && dto.getFarm().getId() != null) {
				Farm farm = this.farmRepository.findOne(dto.getFarm().getId());							
				importExportAnimal.setFarm(farm);
			}
			Animal animal = null;
			if (dto.getAnimal() != null && dto.getAnimal().getId()!=null) {
				animal = this.animalRepository.findOne(dto.getAnimal().getId());							
				importExportAnimal.setAnimal(animal);
			}
			
			if(dto.getProductTarget()!=null && dto.getProductTarget().getId()!=null) {
				ProductTarget pt = productTargetRepository.findOne(dto.getProductTarget().getId());
				importExportAnimal.setProductTarget(pt);				
			}
			else {
				importExportAnimal.setProductTarget(null);	
			}
			
			if (dto.getSeedLevel() != null) {
				SeedLevel seedLevel = seedLevelRepository.findOne(dto.getSeedLevel().getId());
				importExportAnimal.setSeedLevel(seedLevel);
			} else {
				importExportAnimal.setSeedLevel(null);
			}
			
			importExportAnimal.setMale(dto.getMale());
			importExportAnimal.setFemale(dto.getFemale());
			importExportAnimal.setUnGender(dto.getUnGender());
			if(dto.getAnimalReportDataType() != null) {
				importExportAnimal.setAnimalReportDataType(dto.getAnimalReportDataType());
			}
			
			if(dto.getOriginal()!=null && dto.getOriginal().getId()!=null) {
				Original pt = originalRepository.findOne(dto.getOriginal().getId());
				importExportAnimal.setOriginal(pt);
			}
			else {
				importExportAnimal.setOriginal(null);
			}
			
			//tự động gen mã lô khi nhập đàn (YYMMDD-STT)
//			if(isNew && dto.getType()==1 && dto.getDateImport()!=null){
//				String code=autoGenBathCode(dto.getDateImport());
//				importAnimal.setBatchCode(code);
//			}else if(dto.getType()!=1){
//				importAnimal.setBatchCode(dto.getBatchCode());
//			}			
			// Khoadv42 sửa ngày 08/04/2020
			if(isNew == false && dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				remainQuantity = this.checkRemainQuantity(importExportAnimal.getId());
				if(dto.getQuantity() < (firstQuantity - remainQuantity)) {
					dto.setBatchCode("-5");// Số lượng nhập nhỏ hơn số lượng đã xuất;
					return dto;
				}
				if(dto.getDateImport()!= null && checkValidDateImport(dto.getDateImport(),importExportAnimal.getId())) {
					dto.setBatchCode("-7");// không thể sửa vì ngày lớn hơn ngày export từ dữ liệu đã dùng
					return dto;
				}
			}
			if(isNew && dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				String code=autoGenBathCode(dto.getDateImport());
				importExportAnimal.setBatchCode(code);
			}else if (dto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(importRecord == null) {
					String code=autoGenBathCode(dto.getDateExport());
					importExportAnimal.setBatchCode(code);
				}else importExportAnimal.setBatchCode(importRecord.getBatchCode());
				//cập nhập trường điều chuyển loại khi xuất đàn loại phiếu xuất là điều chuyển loại
				if(dto.getExportType()>0 && dto.getExportType()==WLConstant.ExportAnimalType.exChangeType.getValue()) {
					importExportAnimal.setIsExChange(true);
				}else {
					importExportAnimal.setIsExChange(false);
				}
			}
			if (dto.getDateImport() != null) {
				importExportAnimal.setDateImport(dto.getDateImport());
				importExportAnimal.setDateIssue(importExportAnimal.getDateImport());
			}			
			if(animal != null && animal.getParent() != null && animal.getParent().getCode().equals(WLConstant.BEAR_CODE)) {
				importExportAnimal.setQuantity(1);
			}else
			importExportAnimal.setQuantity(dto.getQuantity());
			importExportAnimal.setAmount(dto.getAmount());
			importExportAnimal.setDayOld(dto.getDayOld());
			importExportAnimal.setLifeCycle(dto.getLifeCycle());
			importExportAnimal.setDescription(dto.getDescription());
			importExportAnimal.setSupplierAdress(dto.getSupplierAdress());
			importExportAnimal.setSupplierName(dto.getSupplierName());
			
			if (dto.getDateExport() != null) {
				importExportAnimal.setDateExport(dto.getDateExport());
				importExportAnimal.setDateIssue(importExportAnimal.getDateExport());
			}	
			if(dto.getProvincial() != null && dto.getProvincial().getId() != null) {
				FmsAdministrativeUnit province = this.fmsAdministrativeUnitRepository.findOne(dto.getProvincial().getId());
				importExportAnimal.setProvince(province);
			}
			importExportAnimal.setVoucherCode(dto.getVoucherCode());
			importExportAnimal.setExportType(dto.getExportType());
			if(dto.getIsBornAtFarm() == null || dto.getIsBornAtFarm() == false) {
				importExportAnimal.setExportReason(dto.getExportReason());
			}else importExportAnimal.setExportReason(null);
			importExportAnimal.setBuyerName(dto.getBuyerName());
			importExportAnimal.setBuyerAdress(dto.getBuyerAdress());
			
			Set<InjectionPlant> injectionPlants = new HashSet<InjectionPlant>();
			if (dto.getInjectionPlants() != null && dto.getInjectionPlants().size() > 0) {
				for (InjectionPlantDto injectionPlantDto : dto.getInjectionPlants()) {
					if (injectionPlantDto != null) {
						InjectionPlant ip = null;
						if (injectionPlantDto.getId() != null) {
							ip = this.injectionPlantRepository.findOne(injectionPlantDto.getId());
						}
						if (ip == null) {
							ip = new InjectionPlant();
							ip.setCreateDate(currentDate);
							ip.setCreatedBy(currentUserName);
						}
						ip.setDrug(injectionPlantDto.getDrug());
						if (injectionPlantDto.getInjectionDate() != null) {
							ip.setInjectionDate(injectionPlantDto.getInjectionDate());
						}
						ip.setInjectionRound(injectionPlantDto.getInjectionRound());
						ip.setImportAnimal(importExportAnimal);
						//Thêm lần tiêm
						if(injectionPlantDto.getInjectionTime()!=null && injectionPlantDto.getInjectionTime().getId()!=null) {
							InjectionTime ijt = injectionTimeRepository.findOne(injectionPlantDto.getInjectionTime().getId());
							ip.setInjectionTime(ijt);
						}
						injectionPlants.add(ip);
					}
				}				
			}			
			if (importExportAnimal.getInjectionPlants() == null) {
				importExportAnimal.setInjectionPlants(injectionPlants);
			} else {
				importExportAnimal.getInjectionPlants().clear();
				importExportAnimal.getInjectionPlants().addAll(injectionPlants);
			}
			
			// Khoadv42 day: 01/06/2020
			Set<IndividualAnimal> individualAnimals = new HashSet<IndividualAnimal>();
			if(dto.getIndividualAnimals() != null && dto.getIndividualAnimals().size() > 0) {
				for(IndividualAnimalDto individualAnimalDto: dto.getIndividualAnimals()) {
					IndividualAnimal individualAnimal = null;
					if(individualAnimalDto != null && individualAnimalDto.getId() != null) {
						individualAnimal = individualAnimalRepository.findOne(individualAnimalDto.getId());;
					}
					if(individualAnimal == null) {
						individualAnimal = new IndividualAnimal();
						individualAnimal.setCreatedBy(currentUserName);
						individualAnimal.setCreateDate(currentDate);
					}
					individualAnimal.setName(individualAnimalDto.getName());
					List<IndividualAnimal> listIndividual= individualAnimalRepository.findListByCode(individualAnimalDto.getCode());
					if(individualAnimal.getId() == null) {
						// TH Thêm mới check code trùng
						if(listIndividual != null && listIndividual.size() > 0) {
							dto.setDupCodeIndividualAnimals(true);
							dto.getIndividualAnimalDupCode().add(individualAnimalDto.getCode());
							continue;
						}
					}else {
						// TH Update check code trùng
						if(listIndividual != null && listIndividual.size() > 0 && listIndividual.get(0) != null && individualAnimalDto.getId() != listIndividual.get(0).getId()) {
							dto.setDupCodeIndividualAnimals(true);
							dto.getIndividualAnimalDupCode().add(individualAnimalDto.getCode());
							continue;
						}
					}
					individualAnimal.setCode(individualAnimalDto.getCode());
					individualAnimal.setBirthDate(individualAnimalDto.getBirthDate());
					individualAnimal.setAnimal(animal);
					individualAnimal.setStatus(individualAnimalDto.getStatus());
					individualAnimal.setGender(individualAnimalDto.getGender());
					individualAnimal.setDayOld(individualAnimalDto.getDayOld());
					individualAnimal.setAdditionalInformation(individualAnimalDto.getAdditionalInformation());
					individualAnimal.setIndividualAnimalStatus(individualAnimalDto.getIndividualAnimalStatus());
					Original original = null;
					if(individualAnimalDto.getOriginal() != null && individualAnimalDto.getOriginal().getId() != null) {
						original = originalRepository.findOne(individualAnimalDto.getOriginal().getId());
					}
					individualAnimal.setOriginal(original);
					if(dto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
						individualAnimal.setImportAnimal(importExportAnimal);
					}else if(dto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
						individualAnimal.setExportAnimal(importExportAnimal);
					}
					individualAnimals.add(individualAnimal);
				}
			}
			if(dto.isDupCodeIndividualAnimals() == true) {
				return dto;
			}
			if(importExportAnimal.getIndividualAnimals() == null) {
				importExportAnimal.setIndividualAnimals(individualAnimals);
			}else {
				importExportAnimal.getIndividualAnimals().clear();
				importExportAnimal.getIndividualAnimals().addAll(individualAnimals);
			}
			
			
			
			importExportAnimal.setImportAnimal(importRecord);					
			if(importRecord != null) {
				importExportAnimal.setSeedLevel(importRecord.getSeedLevel());
				importExportAnimal.setGender(importRecord.getGender());
//				importExportAnimal.setAnimalReportDataType(importRecord.getAnimalReportDataType());
			}
			
			importExportAnimal = this.importAnimalRepository.save(importExportAnimal);
			
			if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				remainQuantity = this.checkRemainQuantity(importExportAnimal.getId());
				importExportAnimal.setRemainQuantity(remainQuantity);
				importExportAnimal = this.importAnimalRepository.save(importExportAnimal);
			}
			else if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(importExportAnimal.getImportAnimal()!=null) {
					remainQuantity = this.checkRemainQuantity(importExportAnimal.getImportAnimal().getId());
					importExportAnimal.getImportAnimal().setRemainQuantity(remainQuantity);
					this.importAnimalRepository.save(importExportAnimal.getImportAnimal());
				}
				/*Nếu là phiếu xuất điều chuyển loại  thì tạo thêm 1 phiếu nhập
				 * Phiếu nhập mới sẽ giống phiếu nhập ban đầu của phiếu xuất , ngày nhập là ngày của phiếu xuất, đánh dấu chuyển loại
				 * 
				 */
				if(importExportAnimal.getIsExChange()!=null && importExportAnimal.getIsExChange()) {
					//gọi hàm tạo phiếu nhập ở đây
					createUpdateImportExportAnimalExChange(importExportAnimal,dto.getProductTargetChange());
				}
			}
			
			dto.setId(importExportAnimal.getId());
			if(isNew==false && importExportAnimal!=null && importExportAnimal.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				//khi sửa nhập đàn có khả năng thay đổi cơ sở chăn nuôi và vật nuôi => cần thay đổi xuất đàn (nếu có) theo nhập đàn
				updateExportAnimalWhenUpdateImport(importExportAnimal);				
			}
//			//cập nhật số lượng còn lại vào phiếu nhập đàn khi tạo phiếu xuất đàn
			if(dto.getType()==-1 && dto.getImportAnimal()!=null && dto.getImportAnimal().getId()!=null){
				ImportExportAnimal importAnimalOld=this.importAnimalRepository.findOne(dto.getImportAnimal().getId());
				if(importAnimalOld!=null){
					importAnimalOld.setRemainQuantity(remainQuantity);
					this.importAnimalRepository.save(importAnimalOld);
				}
			}
			//cập nhật tổng tồn của cơ sở chăn nuôi
//			if(importExportAnimal!=null && importExportAnimal.getFarm()!=null && importExportAnimal.getFarm().getId()!=null){
//				farmService.countBalanceNumberByFarm(importExportAnimal.getFarm().getId());
//			}
			//cập nhật lượng tồn theo hướng sản phẩm và cập nhật lượng tồn theo hướng sản phẩm + vật nuôi		
//			if(importExportAnimal!=null) {
//				if(importExportAnimal.getFarm()!=null && importExportAnimal.getFarm().getId()!=null) {
//					farmService.deleteFarmAnimalProductTargetExistByFarmId(importExportAnimal.getFarm().getId());
//					farmService.deleteFarmProductTargetExistByFarmId(importExportAnimal.getFarm().getId());
//					updateAllFarmAnimalProductTargetExistByFarm(importExportAnimal.getFarm().getId());
//					updateAllFarmProductTargetExistByFarm(importExportAnimal.getFarm().getId());
//				}				
////				updateQuantityFarmProductTarget(importAnimalTemp);
////				updateQuantityFarmAnimalProductTargetExist(importAnimalTemp);
//			}
			AnimalReportDataSearchDto searchDto = new AnimalReportDataSearchDto();
			if(dto.getFarm() != null) {
				searchDto.setFarmId(dto.getFarm().getId());
			}
			if(dto.getAnimal() != null) {
				searchDto.setAnimalId(dto.getAnimal().getId());
			}
			
			//comment ngày 23/11/2020 - không kết xuất về bảng animal_report_data
			/*SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
			String[] dates = formatDate.format(importExportAnimal.getDateIssue()).split("/");
			searchDto.setYear(Integer.parseInt(dates[2]));
			searchDto.setMonth(Integer.parseInt(dates[1]));
			searchDto.setDay(Integer.parseInt(dates[0]));
			animalReportDataService.convertFromImportExportAnimal2ReportAnimalData(searchDto);
			
			// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
			animalReportDataService.updateAnimalReportDataByFarmAnimalYear(importExportAnimal.getFarm().getId(), Arrays.asList(importExportAnimal.getAnimal().getId()), Integer.parseInt(dates[2]));
			
//			reportForm16Service.createByImportExportAnimal(importExportAnimal);
//			List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByFarmIdAndAnimalId(importExportAnimal.getFarm().getId(), importExportAnimal.getAnimal().getId());
			List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(importExportAnimal.getFarm().getId(), null);
			if(listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
				for (FarmAnimalTotalDto farmAnimalTotalDto : listAnimalReportTotal) {
					if(farmAnimalTotalDto != null) {
						try {
							FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
						} catch (Exception e) {
							// TODO: handle exception
							continue;
						}
					}
				}
			}*/
			return dto;
		}
		return null;
	}
	
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
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue())
		)
		{
			sql+= "SUM(iea.quantity),SUM(iea.amount),COUNT(distinct iea.farm.id),SUM(iea.male),SUM(iea.female),SUM(iea.unGender) %s ";//Tính tổng xuất hoặc nhập đàn
		}
		else 
		{
			sql+= "SUM(iea.quantity * iea.type),SUM(iea.amount),COUNT(distinct iea.farm.id),SUM(iea.male * iea.type),SUM(iea.female * iea.type),SUM(iea.unGender * iea.type) %s ";//Tính tồn kho
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
		if(paramDto.getListAnimalIds()!=null && paramDto.getListAnimalIds().size()>0) {
			whereClause+=" AND iea.animal.id in (:listAnimalIds) ";
		}
		if(paramDto.getAnimalTypeId()!=null && paramDto.getAnimalTypeId()>0) {
			whereClause+=" AND iea.animal.type.id = :animalTypeId ";
		}
		if(paramDto.getAnimalParentId()!=null && paramDto.getAnimalParentId()>0) {
			whereClause+=" AND iea.animal.parent.id = :animalParentId ";
		}
		if(paramDto.getLiveStockMethod()!=null) {
			whereClause+=" AND iea.animal.liveStockMethod = :liveStockMethod ";
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
		if(paramDto.getOwnershipId()!=null && paramDto.getOwnershipId()>0L) {
			whereClause+=" AND iea.farm.ownership.id = :ownershipId ";
		}
		if(paramDto.getAnimalReportDataType() != null) {
			whereClause += " and iea.animalReportDataType = :animalReportDataType ";
		}
		
		
		String animal = " ";
		String animalParent = " ";
		String animalType = " ";
		String batchCode = " ";
		String liveStockMethod = " ";		
		String farm = " ";
		String original = " ";
		String productTarget = " ";
		String ward = " ";
		String district = " ";
		String province = " ";
		String region = " ";
		String exportReason = " ";
		String ownership = " ";
		
		String orderByClause="";
		
		List<String> columns = new ArrayList<String>();
		columns.add(WLConstant.FunctionAndGroupByItem.quantity.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.amount.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.countFarm.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.sumMale.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.sumFeMale.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.sumUnGen.getValue());
		String groupByClause="";
		String selectClause="";
		if(paramDto.getGroupByItems()!=null && paramDto.getGroupByItems().size()>0) {
			
			for (String grItem : paramDto.getGroupByItems()) {
				if(WLConstant.FunctionAndGroupByItem.animal.getValue().equals(grItem)) {
					groupByClause+=" iea.animal.id,iea.animal.name,iea.animal.scienceName, ";
					animal = " ,iea.animal.id,iea.animal.name,iea.animal.scienceName ";
					selectClause+=animal;
					columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue()+"name");
					columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue()+"scienceName");
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
				if(WLConstant.FunctionAndGroupByItem.liveStockMethod.getValue().equals(grItem)) {
					groupByClause+=" iea.animal.liveStockMethod, ";
					liveStockMethod=" ,iea.animal.liveStockMethod ";
					selectClause+=liveStockMethod;
					columns.add(WLConstant.FunctionAndGroupByItem.liveStockMethod.getValue());
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
				if(WLConstant.FunctionAndGroupByItem.exportReason.getValue().equals(grItem)) {
					groupByClause+=" iea.exportReason, ";
					exportReason=" ,iea.exportReason ";
					selectClause+=exportReason;
					columns.add(WLConstant.FunctionAndGroupByItem.exportReason.getValue());
				}
				if(WLConstant.FunctionAndGroupByItem.ownership.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.ownership.id,iea.farm.ownership.name, ";
					region=" ,iea.farm.ownership.id,iea.farm.ownership.name ";
					selectClause+=region;
					columns.add(WLConstant.FunctionAndGroupByItem.ownership.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.ownership.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.animalReportDataType.getValue().equals(grItem)) {
					groupByClause+=" iea.animalReportDataType, ";
					region=" ,iea.animalReportDataType ";
					selectClause+=region;
					columns.add(WLConstant.FunctionAndGroupByItem.animalReportDataType.getValue());
				}
				
			}
		}
		sql = String.format(sql, selectClause);
		if(groupByClause!="") {
			groupByClause = " GROUP BY "+ groupByClause.substring(0, groupByClause.length() - 2);
		}	
		
		sql = sql + whereClause + groupByClause;
//		System.out.println(sql);
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
		if(paramDto.getListAnimalIds()!=null && paramDto.getListAnimalIds().size() >0) {
			q.setParameter("listAnimalIds", paramDto.getListAnimalIds());
		}
		if(paramDto.getAnimalTypeId()!=null && paramDto.getAnimalTypeId()>0) {
			q.setParameter("animalTypeId", paramDto.getAnimalTypeId());
		}
		if(paramDto.getAnimalParentId()!=null && paramDto.getAnimalParentId()>0) {
			q.setParameter("animalParentId", paramDto.getAnimalParentId());
		}
		if(paramDto.getLiveStockMethod()!=null) {
			q.setParameter("liveStockMethod", paramDto.getLiveStockMethod());
		}
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {		
			q.setParameter("farmId", paramDto.getFarmId());
		}		
		if(paramDto.getProductTargetId()!=null && paramDto.getProductTargetId()>0) {
			q.setParameter("productTargetId", paramDto.getProductTargetId());
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
		if(paramDto.getOwnershipId()!=null && paramDto.getOwnershipId()>0L) {
			q.setParameter("ownershipId", paramDto.getOwnershipId());	
		}
		if(paramDto.getAnimalReportDataType() != null) {
			q.setParameter("animalReportDataType", paramDto.getAnimalReportDataType());
		}
		
		List<Object[]> results = q.getResultList();
		List<InventoryReportDto> ret = new ArrayList<InventoryReportDto>();
		
		if(results!=null) {
			for (Object[] re : results) {
				if(paramDto.getIsSumTotal()!=null && paramDto.getIsSumTotal()) {
					if(re!=null && re[0]!=null) {
						InventoryReportDto io = new InventoryReportDto(re,columns);				
						ret.add(io);
					}
					
				}else {
					InventoryReportDto io = new InventoryReportDto(re,columns);				
					ret.add(io);
				}
				
			}
		}
		return ret;
	}

	@Override
	public Page<ImportExportAnimalDto> searchDto(
			ImportExportAnimalSearchDto searchDto, int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		String sql = " select new com.globits.wl.dto.ImportExportAnimalDto(fa) from ImportExportAnimal fa  where (1=1)";
		String sqlCount = "select count(fa.id) from ImportExportAnimal fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.batchCode like :namecode or fa.farm.code like :namecode or fa.farm.name like :namecode or fa.animal.name like :namecode )";
		}
		if(searchDto.getFarmId()!=null){
			whereClause += " and (fa.farm.id= :farmId)";
		}
		if(searchDto.getCreate_by()!=null){
			whereClause += " and (fa.created_by= :created_by)";
		}
		if(searchDto.getCreate_date()!=null){
			whereClause += " and (fa.create_date= :create_date)";
		}
		if(searchDto.getWardsId()!=null) {//đây là query theo xã
			whereClause += " and (fa.farm.administrativeUnit.id= :wardsId)";
		}
		if(searchDto.getDistrictId()!=null) {//đây là query theo huyện
			whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(searchDto.getCityId()!=null) {
			whereClause += " and fa.farm.administrativeUnit.parent.parent.id  = :provinceId";
		}
//		if(searchDto.getCityId()!=null && searchDto.getDistrictIds()!=null && searchDto.getDistrictIds().size()>0) {//đây là query theo tỉnh lấy list id của huyện
//			whereClause += " and (fa.farm.administrativeUnit.parent.id in :districtIds)";
//		}
		if(searchDto.getRemainQuantityDown() != null) {
			whereClause += " and fa.remainQuantity  > :remainQuantityDown";
		}
		if(searchDto.getProductTargetCode() != null) {
			whereClause += " and fa.productTarget.code = :productTargetCode";
		}
		if(searchDto.getType()==1){//nhập
			if(searchDto.getFromDate()!=null){
				whereClause += " and (fa.dateImport >= :fromDate)";
			}
			if(searchDto.getToDate()!=null){
				whereClause += " and (fa.dateImport <= :toDate)";
			}
			if(searchDto.getIsImportExportAnimalSeed()!=null) {
				if (searchDto.getIsImportExportAnimalSeed()) {		//Nếu = true thì tìm kiếm theo import export con giống
					whereClause += " AND (fa.seedLevel IS NOT NULL ) ";
				}
				else {
					whereClause += " AND (fa.seedLevel IS NULL ) ";
				}
			}
		}else if(searchDto.getType()==-1){//xuất
			if(searchDto.getFromDate()!=null){
				whereClause += " and (fa.dateExport >= :fromDate)";
			}
			if(searchDto.getToDate()!=null){
				whereClause += " and (fa.dateExport <= :toDate)";
			}
		}
		
		if(searchDto.getAnimalId() != null) {
			whereClause += " and (fa.animal.id = :animalId)";
		}
		
		whereClause += " and (fa.type= :type)";
		
				
		sql +=whereClause;
		if(searchDto.getType()==1) {
			sql +=" order by fa.dateImport desc, fa.batchCode desc ";
		}else if(searchDto.getType()==-1) {
			sql +=" order by fa.dateExport desc, fa.batchCode desc ";
		}
		
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,ImportExportAnimalDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}	
		if(searchDto.getFarmId()!=null){
			q.setParameter("farmId", searchDto.getFarmId());
			qCount.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getCreate_by()!=null){
			q.setParameter("created_by", searchDto.getCreate_by());
			qCount.setParameter("created_by", searchDto.getCreate_by());
		}
		if(searchDto.getCreate_date()!=null){
			q.setParameter("create_date", searchDto.getCreate_date());
			qCount.setParameter("create_date", searchDto.getCreate_date());
		}
		if(searchDto.getWardsId()!=null) {//đây là query theo xã
			q.setParameter("wardsId", searchDto.getWardsId());
			qCount.setParameter("wardsId", searchDto.getWardsId());			
		}
		if(searchDto.getDistrictId()!=null) {//đây là query theo huyện
			q.setParameter("districtId", searchDto.getDistrictId());
			qCount.setParameter("districtId", searchDto.getDistrictId());
		}
		if(searchDto.getCityId()!=null ) {//đây là query theo tỉnh 
			q.setParameter("provinceId", searchDto.getCityId());
			qCount.setParameter("provinceId", searchDto.getCityId());
		}
		if(searchDto.getRemainQuantityDown() != null) {
			double remainQuantityDow = Double.valueOf(searchDto.getRemainQuantityDown().intValue()+"");
			q.setParameter("remainQuantityDown", remainQuantityDow);
			qCount.setParameter("remainQuantityDown", remainQuantityDow);
		}
		if(searchDto.getProductTargetCode() != null) {
			q.setParameter("productTargetCode", searchDto.getProductTargetCode());
			qCount.setParameter("productTargetCode", searchDto.getProductTargetCode());
		}
		if(searchDto.getFromDate()!=null) {
			q.setParameter("fromDate", searchDto.getFromDate());
			qCount.setParameter("fromDate", searchDto.getFromDate());
		}
		if(searchDto.getToDate()!=null) {
			q.setParameter("toDate", searchDto.getToDate());
			qCount.setParameter("toDate", searchDto.getToDate());
		}
		if(searchDto.getAnimalId() != null) {
			q.setParameter("animalId", searchDto.getAnimalId());
			qCount.setParameter("animalId", searchDto.getAnimalId());
		}
		
		q.setParameter("type", searchDto.getType());
		qCount.setParameter("type", searchDto.getType());
		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<ImportExportAnimalDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}
	@Override
	public String autoGenBathCode(Date importDate){
		String bathCode="";
		String y="";
		String m="";
		String d="";
		Integer max=0;
		String code="";
		Date end=null;
		if(importDate!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(importDate);
			int year = cal.get(Calendar.YEAR);
			 y=String.valueOf(year);
			y=y.substring(2, 4);
			int month = cal.get(Calendar.MONTH) +1;
			if(month<10){
				m="0"+String.valueOf(month);
			}else{
				m=String.valueOf(month);
			}
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if(day<10){
				d="0"+String.valueOf(day);
			}else{
				d=String.valueOf(day);
			}
			bathCode=y+m+d;
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			importDate=cal.getTime();
			end=WLDateTimeUtil.getEndOfDay(importDate);
			try {
				max=this.importAnimalRepository.count(1, importDate,end);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			if(max!=null && max>0){
				max=max+1;
			}else{
				max=1;
			}
			code=bathCode;
			bathCode=bathCode +"-"+max;
			
			//phòng trường hợp đã tồn tại bathCode
			List<ImportExportAnimal> list=importAnimalRepository.findByTypeAndBatchCode(WLConstant.ImportExportAnimalType.importAnimal.getValue(), bathCode);
			if(list!=null && list.size()>0) {
				max=max+1;
				bathCode=code +"-"+max;
			}
		}
		return bathCode;
	}
	public Double checkRemainQuantity(Long importAnimalId) {
		ImportExportAnimal importAnimal=importAnimalRepository.findOne(importAnimalId);
		if(importAnimal!=null) {
			List<ImportExportAnimal> listExport = importAnimalRepository.findExportByImport(importAnimalId);
			if(listExport!=null && listExport.size()>0) {
				double ret=importAnimal.getQuantity();
				for (ImportExportAnimal importExportAnimal : listExport) {
					ret-=importExportAnimal.getQuantity();
				}
				return ret;
			}
			else {
				return importAnimal.getQuantity();
			}
		}
		return null;
	}
	
//	public double quantityRemain(boolean isNew,double quantity, Long importAnimalId, ImportExportAnimal exportAnimal, String batchCode){
//		double remainQuantity=0;
//		ImportExportAnimal importAnimal=importAnimalRepository.findOne(importAnimalId);
//		if(importAnimal!=null && quantity>0){
//			if(isNew){//trường hợp xuất đàn thêm mới; số lượng còn lại= số lượng còn lại của phiểu nhập - số lượng phiểu xuất
//				remainQuantity=importAnimal.getRemainQuantity()- quantity;
//				return remainQuantity;
//			}else{ // trường hợp cập nhật lại phiểu xuất; xử lý phức tạp cần xem lại
//				// trường hợp đúng phiếu nhập cũ số lượng còn lại= (số lượng còn lại của phiếu nhập + số lượng phiếu xuất cũ)- số lượng phiếu xuất khi sửa nếu trùng phiếu nhập
//				// trường hợp khác phiếu nhập thì phải cập nhật số lượng còn lại vào  phiếu nhập cũ và phiếu nhập mới khi xuất
//				if(exportAnimal!=null){
//					//ImportExportAnimal exportAnimal=importAnimalRepository.findById(exportAnimalId);
//					//trường hợp cập nhật chọn lại đúng phiếu nhập cũ
//					if(exportAnimal!=null && exportAnimal.getBatchCode().equals(batchCode)){//số lượng còn lại 
//						remainQuantity=(importAnimal.getRemainQuantity()+ exportAnimal.getQuantity())- quantity;
//						return remainQuantity;
//					}
//					//trường hợp cập nhật chọn phiếu nhập khác
//					else{					
//						// cập nhật số lượng còn lại cho phiếu nhập mới
//						remainQuantity=importAnimal.getRemainQuantity()- quantity;
//						// hoàn lại số lượng còn lại cho phiếu nhập cũ
//						if(remainQuantity>=0 && exportAnimal!=null && exportAnimal.getImportAnimal()!=null && exportAnimal.getImportAnimal().getId()!=null){
//							ImportExportAnimal importAnimalOld=importAnimalRepository.findOne(exportAnimal.getImportAnimal().getId());
//							double remainOle=importAnimalOld.getRemainQuantity();
//							remainOle=remainOle+exportAnimal.getQuantity();
//							importAnimalOld.setRemainQuantity(remainOle);
//							this.importAnimalRepository.save(importAnimalOld);
//						}
//						return remainQuantity;
//					}
//					
//				}
//				
//			}
//			
//		}
//		
//		return remainQuantity;
//	}
	/*
	 * Dùng cho import từ Excel
	 */
	public ImportExportAnimalDto saveImport(Long id, ImportExportAnimalDto dto) {
		if (dto != null &&dto.getBatchCode()!=null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			boolean isNew=false;
			double remainQuantity=0;//số lượng còn lại
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			ImportExportAnimal importAnimal = null;
			Farm farm =null;
			if (dto.getFarm() != null && dto.getFarm().getId() != null) {
				 farm = this.farmRepository.findOne(dto.getFarm().getId());							
				//importAnimal.setFarm(farm);
			}else if(dto.getFarm()!=null && dto.getFarm().getCode()!=null){
				List<Farm> farms=this.farmRepository.findByCode(dto.getFarm().getCode());
				if(farms!=null && farms.size()>0){
					if(farms.size()==1){
						farm=farms.get(0);
						//importAnimal.setFarm(farm);
					}else{
						for (Farm item : farms) {
							if(dto.getFarm().getName()!=null && item.getName().equals(dto.getFarm().getName())){
								farm=item;
								//importAnimal.setFarm(farm);
								break;
							}
						}
					}
					
				}
			}
			
			if(id!=null) {
				importAnimal = this.importAnimalRepository.findOne(id);
				
			}
			else if (dto.getId() != null) {
				importAnimal = this.importAnimalRepository.findOne(dto.getId());
			
			}else if(dto.getBatchCode()!=null && farm!=null && farm.getId()!=null && dto.getType()==1){
				List<ImportExportAnimal> list=importAnimalRepository.findByCode(dto.getBatchCode(), dto.getType(),farm.getId());
				if(list!=null && list.size()>0){
					importAnimal=list.get(0);
				}
			}else if(dto.getBatchCode()!=null && farm!=null && farm.getId()!=null && dto.getType()==-1 && dto.getVoucherCode()!=null){
				importAnimal=importAnimalRepository.findBy(dto.getBatchCode(), dto.getType(),farm.getId(), dto.getVoucherCode());
			}
			 if (importAnimal == null) {
				importAnimal = new ImportExportAnimal();
				importAnimal.setCreateDate(currentDate);
				importAnimal.setCreatedBy(currentUserName);
				isNew=true;
				importAnimal.setModifiedBy(currentUserName);
				importAnimal.setModifyDate(currentDate);
			}
			importAnimal.setFarm(farm);
			importAnimal.setType(dto.getType());
			
			ImportExportAnimal importAn=null;
			if(dto.getImportAnimal()!=null && dto.getImportAnimal().getId()!=null){
				 importAn=this.importAnimalRepository.findOne(dto.getImportAnimal().getId());
				
				if(importAn!=null){
					importAnimal.setImportAnimal(importAn);
				}
			}else if(dto.getBatchCode()!=null && farm!=null && farm.getId()!=null){
				List<ImportExportAnimal> importAns=this.importAnimalRepository.findByCode(dto.getBatchCode(), 1, farm.getId());
				if(importAns!=null && importAns.size()>0){
					if(importAns.size()==1){
						importAn=importAns.get(0);
						importAnimal.setImportAnimal(importAn);
					}
				}
			}
			//set số lượng còn lại khi nhập đàn
//			if(isNew && dto.getType()==1 && dto.getQuantity()>0){
//				
//				importAnimal.setRemainQuantity(dto.getQuantity());//set số lượng còn lại=số lượng  khi nhập đàn
//			}else if(isNew==false && dto.getType()==1 && dto.getQuantity()!=importAnimal.getQuantity()){//set số lượng còn lại khi sửa số lượng nhập đàn
//				if(dto.getQuantity()>importAnimal.getQuantity()){
//					double residual=dto.getQuantity() - importAnimal.getQuantity();
//					residual=importAnimal.getRemainQuantity()+residual;
//					importAnimal.setRemainQuantity(residual);//set số lượng còn lại khi sửa số lượng nhập đàn
//				}else if(dto.getQuantity()<importAnimal.getQuantity()){
//					double residual=importAnimal.getQuantity() - dto.getQuantity() ;
//					residual=importAnimal.getRemainQuantity()- residual;
//					importAnimal.setRemainQuantity(residual);//set số lượng còn lại khi sửa số lượng nhập đàn
//				}				
//			}
//			//set số lượng còn lại khi xuất đàn
//			else if(dto.getType()==-1 && importAn!=null && importAn.getId()!=null){
//				remainQuantity=quantityRemain(isNew,dto.getQuantity(), importAn.getId(), importAnimal,dto.getBatchCode());
//				if(remainQuantity<0){
//					dto.setRemainQuantity(-1);
//					//return dto;//không đủ số lượng để xuất (số lượng xuất nhiều hơn nhập)
//				}
//			}
			
			Animal animal = null;
			if (dto.getAnimal() != null && dto.getAnimal().getId()!=null) {
				 animal = this.animalRepository.findOne(dto.getAnimal().getId());							
				importAnimal.setAnimal(animal);
			}else if(dto.getAnimal()!=null && dto.getAnimal().getCode()!=null){
				List<Animal> list=animalRepository.findByCode(dto.getAnimal().getCode());
				if(list!=null && list.size()>0){
					if(list.size()==1){
						animal=list.get(0);
						importAnimal.setAnimal(animal);
					}else{
						for (Animal animal2 : list) {
							if(dto.getAnimal().getName()!=null && animal2.getName().equals(dto.getAnimal().getName())){
								animal=animal2;
								importAnimal.setAnimal(animal);
								break;
							}
						}
					}
				}
			}else if(dto.getAnimal()!=null && dto.getAnimal().getName()!=null){
				List<Animal> list=animalRepository.findByName(dto.getAnimal().getName());
				if(list!=null && list.size()>0){
					if(list.size()==1){
						animal=list.get(0);
						importAnimal.setAnimal(animal);
					}else{
						for (Animal animal2 : list) {
							if(dto.getAnimal().getCode()!=null && animal2.getCode().equals(dto.getAnimal().getCode())){
								animal=animal2;
								importAnimal.setAnimal(animal);
								break;
							}
						}
					}
				}
			}
			ProductTarget pt =null;
			if(dto.getProductTarget()!=null && dto.getProductTarget().getId()!=null) {
				 pt = productTargetRepository.findOne(dto.getProductTarget().getId());
				importAnimal.setProductTarget(pt);				
			}else if(dto.getProductTarget()!=null && dto.getProductTarget().getCode()!=null){
				List<ProductTarget> list=productTargetRepository.findByCode(dto.getProductTarget().getCode());
				if(list!=null && list.size()>0){
					if(list.size()==1){
						pt=list.get(0);
						importAnimal.setProductTarget(pt);	
					}else{
						for (ProductTarget productTarget : list) {
							if(dto.getProductTarget().getName()!=null && productTarget.getName().equals(dto.getProductTarget().getName())){
								pt=productTarget;
								importAnimal.setProductTarget(pt);	
							}
						}
					}
				}
			}else if(dto.getProductTarget()!=null && dto.getProductTarget().getName()!=null){
				List<ProductTarget> list=productTargetRepository.findByName(dto.getProductTarget().getName());
				if(list!=null && list.size()>0){
					if(list.size()==1){
						pt=list.get(0);
						importAnimal.setProductTarget(pt);	
					}else{
						for (ProductTarget productTarget : list) {
							if(dto.getProductTarget().getCode()!=null && productTarget.getCode().equals(dto.getProductTarget().getCode())){
								pt=productTarget;
								importAnimal.setProductTarget(pt);	
							}
						}
					}
				}
			}
						
			//importAnimal.setRemainQuantity(dto.getRemainQuantity());//set số lượng còn lại khi nhập đàn
			//set số lượng còn lại khi xuất đàn
						
			importAnimal.setBatchCode(dto.getBatchCode());
			if (dto.getDateImport() != null) {
				importAnimal.setDateImport(dto.getDateImport());
				importAnimal.setDateIssue(importAnimal.getDateImport());
			}			
			importAnimal.setQuantity(dto.getQuantity());
			importAnimal.setAmount(dto.getAmount());
			importAnimal.setDayOld(dto.getDayOld());
			importAnimal.setLifeCycle(dto.getLifeCycle());
			importAnimal.setDescription(dto.getDescription());
			importAnimal.setSupplierAdress(dto.getSupplierAdress());
			importAnimal.setSupplierName(dto.getSupplierName());
			
			if (dto.getDateExport() != null) {
				importAnimal.setDateExport(dto.getDateExport());
				importAnimal.setDateIssue(importAnimal.getDateExport());
			}			
			importAnimal.setVoucherCode(dto.getVoucherCode());
			importAnimal.setExportType(dto.getExportType());
			if(dto.getIsBornAtFarm() == null || dto.getIsBornAtFarm() == false) {
				importAnimal.setExportReason(dto.getExportReason());;
			}else  importAnimal.setExportReason(null);
			importAnimal.setBuyerName(dto.getBuyerName());
			importAnimal.setBuyerAdress(dto.getBuyerAdress());
			
			Set<InjectionPlant> injectionPlants = new HashSet<InjectionPlant>();
			if (dto.getInjectionPlants() != null && dto.getInjectionPlants().size() > 0) {
				for (InjectionPlantDto injectionPlantDto : dto.getInjectionPlants()) {
					if (injectionPlantDto != null) {
						InjectionPlant ip = null;
						if (injectionPlantDto.getId() != null) {
							ip = this.injectionPlantRepository.findOne(injectionPlantDto.getId());
						}
						if (ip == null) {
							ip = new InjectionPlant();
							ip.setCreateDate(currentDate);
							ip.setCreatedBy(currentUserName);
						}
						ip.setDrug(injectionPlantDto.getDrug());
						if (injectionPlantDto.getInjectionDate() != null) {
							ip.setInjectionDate(injectionPlantDto.getInjectionDate());
						}
						ip.setInjectionRound(injectionPlantDto.getInjectionRound());
						ip.setImportAnimal(importAnimal);
						injectionPlants.add(ip);
					}
				}
				
			}
			
			if (importAnimal.getInjectionPlants() == null) {
				importAnimal.setInjectionPlants(injectionPlants);
			} else {
				importAnimal.getInjectionPlants().clear();
				importAnimal.getInjectionPlants().addAll(injectionPlants);
			}
			
			ImportExportAnimal importAnimalTemp = this.importAnimalRepository.save(importAnimal);
			dto.setId(importAnimalTemp.getId());
			if(isNew==false && importAnimalTemp!=null && importAnimalTemp.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				//khi sửa nhập đàn có khả năng thay đổi cơ sở chăn nuôi và vật nuôi => cần thay đổi xuất đàn (nếu có) theo nhập đàn
				updateExportAnimalWhenUpdateImport(importAnimalTemp);				
			}
			//cập nhật tổng tồn của cơ sở chăn nuôi
			if(importAnimalTemp!=null && importAnimalTemp.getFarm()!=null && importAnimalTemp.getFarm().getId()!=null){
				farmService.countBalanceNumberByFarm(importAnimalTemp.getFarm().getId());
			}
			//cập nhật lượng tồn theo hướng sản phẩm và cập nhật lượng tồn theo hướng sản phẩm + vật nuôi		
			if(importAnimalTemp!=null) {
				if(importAnimalTemp.getFarm()!=null && importAnimalTemp.getFarm().getId()!=null) {
					//xóa tồn các loại
					farmService.deleteFarmAnimalProductTargetExistByFarmId(importAnimalTemp.getFarm().getId());
					farmService.deleteFarmProductTargetExistByFarmId(importAnimalTemp.getFarm().getId());
					//cập nhật lại tồn các loại theo cơ sở chăn nuôi
					updateAllFarmAnimalProductTargetExistByFarm(importAnimalTemp.getFarm().getId());
					updateAllFarmProductTargetExistByFarm(importAnimalTemp.getFarm().getId());
				}
//				updateQuantityFarmProductTarget(importAnimalTemp);
//				updateQuantityFarmAnimalProductTargetExist(importAnimalTemp);
			}
			
			return dto;

		}
		return null;
	}
	/*
	 * Dùng cho import từ Excel
	 */
	@Override
	public List<ImportExportAnimalDto> saveOrUpdateList(
			List<ImportExportAnimalDto> list) {
		ArrayList<ImportExportAnimalDto> ret = new ArrayList<ImportExportAnimalDto>();
		for (int i = 0; i < list.size(); i++) {
			ImportExportAnimalDto dto = list.get(i);						
			saveImport(dto.getId(),dto);
		}
		return ret;
	}
	@Override
	public List<ImportExportAnimalDto> searchDto(ImportExportAnimalSearchDto searchDto) {
		List<ImportExportAnimalDto> ret=new ArrayList<ImportExportAnimalDto>();
		
		String namecode = searchDto.getNameOrCode();
		String sql = " select new com.globits.wl.dto.ImportExportAnimalDto(fa) from ImportExportAnimal fa  where (1=1)";
		
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.batchCode like :namecode or fa.farm.code like :namecode or fa.farm.name like :namecode or fa.animal.name like :namecode )";
		}
		if(searchDto.getFarmId()!=null){
			whereClause += " and (fa.farm.id= :farmId)";
		}
		if(searchDto.getWardsId()!=null) {//đây là query theo xã
			whereClause += " and (fa.farm.administrativeUnit.id= :wardsId)";
		}
		if(searchDto.getDistrictId()!=null) {//đây là query theo huyện
			whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(searchDto.getCityId()!=null ) {//đây là query theo tỉnh 
			whereClause += " and (fa.farm.administrativeUnit.parent.parent.id= :cityId)";
		}
		if(searchDto.getType()==1){//nhập
			if(searchDto.getFromDate()!=null){
				whereClause += " and (fa.dateImport >= :fromDate)";
			}
			if(searchDto.getToDate()!=null){
				whereClause += " and (fa.dateImport <= :toDate)";
			}
		}else if(searchDto.getType()==-1){//xuất
			if(searchDto.getFromDate()!=null){
				whereClause += " and (fa.dateExport >= :fromDate)";
			}
			if(searchDto.getToDate()!=null){
				whereClause += " and (fa.dateExport <= :toDate)";
			}
		}
		if(searchDto.getRemainQuantityDown()!=null) {
			
		}
		
		whereClause += " and (fa.type= :type)";
		
				
		sql +=whereClause;
		if(searchDto.getType()==1) {
			sql +=" order by fa.dateImport desc, fa.batchCode asc ";
		}else if(searchDto.getType()==-1) {
			sql +=" order by fa.dateExport desc, fa.batchCode asc ";
		}

		Query q = manager.createQuery(sql,ImportExportAnimalDto.class);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');		
		}	
		if(searchDto.getFarmId()!=null){
			q.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getWardsId()!=null) {//đây là query theo xã
			q.setParameter("wardsId", searchDto.getWardsId());		
		}
		if(searchDto.getDistrictId()!=null) {//đây là query theo huyện
			q.setParameter("districtId", searchDto.getDistrictId());
		}
		if(searchDto.getCityId()!=null ) {//đây là query theo tỉnh 
			q.setParameter("cityId", searchDto.getCityId());
		}
		if(searchDto.getFromDate()!=null) {
			q.setParameter("fromDate", searchDto.getFromDate());
		}
		if(searchDto.getToDate()!=null) {
			q.setParameter("toDate", searchDto.getToDate());
		}
		
		q.setParameter("type", searchDto.getType());
		
		ret=q.getResultList();
			
		return ret;
	}
	public void updateQuantityFarmProductTarget(ImportExportAnimal importAnimalTemp) {
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if(importAnimalTemp!=null && importAnimalTemp.getFarm()!=null && importAnimalTemp.getProductTarget()!=null){
			FarmProductTargetExist exist=null;
			exist=productTargetExistRepository.findByFarmProductTarget(importAnimalTemp.getFarm().getId(), importAnimalTemp.getProductTarget().getId());
			if(exist==null){
				exist=new FarmProductTargetExist();
				exist.setCreateDate(currentDate);
				exist.setCreatedBy(currentUserName);
				exist.setFarm(importAnimalTemp.getFarm());
				exist.setProductTarget(importAnimalTemp.getProductTarget());
			}
			Double existQuantity=importAnimalRepository.sumRemainQuantityByFarmProducTarget(importAnimalTemp.getFarm().getId(), importAnimalTemp.getProductTarget().getId());
			if(existQuantity!=null){
				exist.setQuantity(existQuantity);
			}else{
				exist.setQuantity(-1);//trường hợp không có lượng tồn
			}
			productTargetExistRepository.save(exist);
			
		}
	}
	/*
	 * Cập nhật số lượng tồn theo hướng sản phẩm
	 * @see com.globits.wl.service.ImportExportAnimalService#updateAllFarmProductTargetExist()
	 */
	@Override
	public void updateAllFarmProductTargetExist() {
		List<ImportExportAnimal> list=importAnimalRepository.findByType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
		if(list!=null && list.size()>0) {
			for (ImportExportAnimal importExportAnimal : list) {
				updateQuantityFarmProductTarget(importExportAnimal);
			}
		}
	}
	/*
	 * Cập nhật tồn theo hướng sản phẩm cho  1 cơ sở chăn nuôi gia cầm
	 */
	public void updateAllFarmProductTargetExistByFarm(Long farmId) {
		List<ImportExportAnimal> list=importAnimalRepository.findByFarmAndType(WLConstant.ImportExportAnimalType.importAnimal.getValue(),farmId);
		if(list!=null && list.size()>0) {
			for (ImportExportAnimal importExportAnimal : list) {
				updateQuantityFarmProductTarget(importExportAnimal);
			}
		}
	}
	
	/** cập nhật tồn theo hướng sản phẩm + vật nuôi cho  1 cơ sở **/
	public void updateQuantityFarmAnimalProductTargetExist(ImportExportAnimal importAnimalTemp) {
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if(importAnimalTemp!=null && importAnimalTemp.getFarm()!=null && importAnimalTemp.getProductTarget()!=null && importAnimalTemp.getAnimal()!=null){
			FarmAnimalProductTargetExist exist=null;
			Animal parent=null;
			if(importAnimalTemp.getAnimal().getParent()!=null) {
				parent=importAnimalTemp.getAnimal().getParent();
			}else {
				parent=importAnimalTemp.getAnimal();
			}
			exist=animalProductTargetExistRepository.findByFarmAnimalProductTarget(importAnimalTemp.getFarm().getId(), importAnimalTemp.getProductTarget().getId(),parent.getId());
			if(exist==null){
				exist=new FarmAnimalProductTargetExist();
				exist.setCreateDate(currentDate);
				exist.setCreatedBy(currentUserName);
				exist.setFarm(importAnimalTemp.getFarm());
				exist.setProductTarget(importAnimalTemp.getProductTarget());
				exist.setAnimal(parent);
			}
			Double existQuantity=importAnimalRepository.sumRemainQuantityByFarmParentAnimalProducTarget(importAnimalTemp.getFarm().getId(), importAnimalTemp.getProductTarget().getId(),parent.getId());
			if(existQuantity!=null){
				exist.setQuantity(existQuantity);
			}else{
				exist.setQuantity(-1);//trường hợp không có lượng tồn
			}
			animalProductTargetExistRepository.save(exist);
			
		}
	}
	/*
	 * Cập nhật số lượng tồn theo hướng sản phẩm + vật nuôi
	 * @see com.globits.wl.service.ImportExportAnimalService#updateAllFarmAnimalProductTargetExist()
	 */
	@Override
	public void updateAllFarmAnimalProductTargetExist() {
		List<ImportExportAnimal> list=importAnimalRepository.findByType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
		if(list!=null && list.size()>0) {
			for (ImportExportAnimal importExportAnimal : list) {
				updateQuantityFarmAnimalProductTargetExist(importExportAnimal);
			}
		}
	}
	
	/*
	 * Cập nhật lại tồn theo hướng sản phẩm + vật nuôi của 1 farm(cơ sở chăn nuôi)
	 */
	public void updateAllFarmAnimalProductTargetExistByFarm(Long farmId) {
		List<ImportExportAnimal> list=importAnimalRepository.findByFarmAndType(WLConstant.ImportExportAnimalType.importAnimal.getValue(),farmId);
		if(list!=null && list.size()>0) {
			for (ImportExportAnimal importExportAnimal : list) {
				updateQuantityFarmAnimalProductTargetExist(importExportAnimal);
			}
		}
	}
	/*
	 * Cập nhật lại thông tin (cơ sở, vật nuôi...) xuất đàn nếu khi sửa nhập đàn
	 */
	public void updateExportAnimalWhenUpdateImport(ImportExportAnimal importExportAnimal) {
		if(importExportAnimal!=null && importExportAnimal.getBatchCode()!=null) {
			List<ImportExportAnimal> listExport=importAnimalRepository.findByTypeAndBatchCode(WLConstant.ImportExportAnimalType.exportAnimal.getValue(), importExportAnimal.getBatchCode());
			if(listExport!=null && listExport.size()>0) {
				for (ImportExportAnimal item : listExport) {
					if(importExportAnimal.getAnimal()!=null && importExportAnimal.getAnimal().getId()!=null && item.getAnimal()!=null && item.getAnimal().getId()!=null && importExportAnimal.getAnimal().getId()!=item.getAnimal().getId()) {
						item.setAnimal(importExportAnimal.getAnimal());
					}
					if(importExportAnimal.getFarm()!=null && importExportAnimal.getFarm().getId()!=null && item.getFarm()!=null && item.getFarm().getId()!=null && importExportAnimal.getFarm().getId()!=item.getFarm().getId()) {
						item.setFarm(importExportAnimal.getFarm());
					}
					if(importExportAnimal.getProductTarget()!=null && importExportAnimal.getProductTarget().getId()!=null && item.getProductTarget()!=null && item.getProductTarget().getId()!=null && importExportAnimal.getProductTarget().getId()!=item.getProductTarget().getId()) {
						item.setProductTarget(importExportAnimal.getProductTarget());
					}
					if(importExportAnimal.getSeedLevel() != null) {
						item.setSeedLevel(importExportAnimal.getSeedLevel());
					}
					importAnimalRepository.save(item);
					
				}
			}			
		}		
	}
	@Override
	public List<FarmSummaryReportDto> farmSummaryReport(List<InventoryReportDto> list){
		if(list!=null && list.size()>0) {
			List<FarmSummaryReportDto> ret = new ArrayList<FarmSummaryReportDto>();
			for (InventoryReportDto inventoryReportDto : list) {		
				if(ret.size()>0) {
					boolean isContain=false;
					for (FarmSummaryReportDto farmSummaryReportDto : ret) {
						if(
								(inventoryReportDto.getRegionName()
								+inventoryReportDto.getProvinceName()
								+inventoryReportDto.getDistrictName()
								+inventoryReportDto.getWardName())
								.equals(
										farmSummaryReportDto.getRegionName()
										+farmSummaryReportDto.getProvinceName()
										+farmSummaryReportDto.getDistrictName()
										+farmSummaryReportDto.getWardName()
								)
							)
						{
							if(inventoryReportDto.getParentlName().toLowerCase().contains("Gà".toLowerCase())) {
								farmSummaryReportDto.setChickenFarmQuantity(farmSummaryReportDto.getChickenFarmQuantity()+inventoryReportDto.getCountFarm());
							}
							else if(inventoryReportDto.getParentlName().toLowerCase().contains("vịt".toLowerCase())) {
								farmSummaryReportDto.setDuckFarmQuantity(farmSummaryReportDto.getDuckFarmQuantity()+inventoryReportDto.getCountFarm());
							}
							else {
								farmSummaryReportDto.setOtherFarmQuantity(farmSummaryReportDto.getOtherFarmQuantity()+inventoryReportDto.getCountFarm());
							}
							farmSummaryReportDto.setQuantity(farmSummaryReportDto.getQuantity()+inventoryReportDto.getQuantity());
							isContain=true;
						}
					}
					if(!isContain) {
						FarmSummaryReportDto dto = new FarmSummaryReportDto();
						dto.setRegionName(inventoryReportDto.getRegionName());
						dto.setProvinceName(inventoryReportDto.getProvinceName());
						dto.setDistrictName(inventoryReportDto.getDistrictName());
						dto.setWardName(inventoryReportDto.getWardName());						
						
						if(inventoryReportDto.getParentlName().toLowerCase().contains("Gà".toLowerCase())) {
							dto.setChickenFarmQuantity(dto.getChickenFarmQuantity()+inventoryReportDto.getCountFarm());
						}
						else if(inventoryReportDto.getParentlName().toLowerCase().contains("vịt".toLowerCase())) {
							dto.setDuckFarmQuantity(dto.getDuckFarmQuantity()+inventoryReportDto.getCountFarm());
						}
						else {
							dto.setOtherFarmQuantity(dto.getOtherFarmQuantity()+inventoryReportDto.getCountFarm());
						}
						ret.add(dto);
					}
				}
				else {
					FarmSummaryReportDto dto = new FarmSummaryReportDto();
					ret.add(dto);
					if(inventoryReportDto.getParentlName().toLowerCase().contains("Gà".toLowerCase())) {
						dto.setChickenFarmQuantity(dto.getChickenFarmQuantity()+inventoryReportDto.getCountFarm());
					}
					else if(inventoryReportDto.getParentlName().toLowerCase().contains("vịt".toLowerCase())) {
						dto.setDuckFarmQuantity(dto.getDuckFarmQuantity()+inventoryReportDto.getCountFarm());
					}
					else {
						dto.setOtherFarmQuantity(dto.getOtherFarmQuantity()+inventoryReportDto.getCountFarm());
					}
					dto.setRegionName(inventoryReportDto.getRegionName());
					dto.setProvinceName(inventoryReportDto.getProvinceName());
					dto.setDistrictName(inventoryReportDto.getDistrictName());
					dto.setWardName(inventoryReportDto.getWardName());
				}
			}
			return ret;
		}
		return null;
	}
	
	public void createUpdateImportExportAnimalExChange(ImportExportAnimal exportAnimal, ProductTargetDto productTargetChangeDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		ImportExportAnimal exportAnimalChange=null;
		boolean isNew=false;
		if(exportAnimal.getIsExChange()!=null&& exportAnimal.getIsExChange() && exportAnimal.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
			//query trong db xem đã có chưa
			List<ImportExportAnimal> list=importAnimalRepository.getImportChangeByExportExChange(exportAnimal.getId(), exportAnimal.getIsExChange());
			if(list!=null && list.size()>0) {
				exportAnimalChange=list.get(0);
			}
			if(exportAnimalChange==null) {
				isNew=true;
				exportAnimalChange=new ImportExportAnimal();
				exportAnimalChange.setCreateDate(currentDate);
				exportAnimalChange.setCreatedBy(currentUserName);
			}
			exportAnimalChange.setModifyDate(currentDate);
			exportAnimalChange.setModifiedBy(currentUserName);
			if(exportAnimal.getImportAnimal()!=null) {
				exportAnimalChange.setAmount(exportAnimal.getImportAnimal().getAmount());
				exportAnimalChange.setAnimal(exportAnimal.getImportAnimal().getAnimal());
				//exportAnimalChange.setBatchCode(exportAnimal.getImportAnimal().getBatchCode());
				exportAnimalChange.setBuyerAdress(exportAnimal.getImportAnimal().getBuyerAdress());
				exportAnimalChange.setBuyerName(exportAnimal.getImportAnimal().getBuyerName());
				exportAnimalChange.setDayOld(exportAnimal.getImportAnimal().getDayOld());
				exportAnimalChange.setDescription(exportAnimal.getImportAnimal().getDescription());
				exportAnimalChange.setFarm(exportAnimal.getImportAnimal().getFarm());
				//exportAnimalChange.setQuantity(exportAnimal.getImportAnimal().getQuantity());
				//exportAnimalChange.setRemainQuantity(exportAnimal.getImportAnimal().getRemainQuantity());
				exportAnimalChange.setType(exportAnimal.getImportAnimal().getType());
				exportAnimalChange.setLifeCycle(exportAnimal.getImportAnimal().getLifeCycle());
			}
			//ngày tạo là ngày xuất phiếu
			exportAnimalChange.setDateImport(exportAnimal.getDateExport());
			exportAnimalChange.setDateIssue(exportAnimal.getDateExport());
			//set trạng thái loại điều chuyển
			exportAnimalChange.setIsExChange(true);
			exportAnimalChange.setExportAnimal(exportAnimal);
			//số lượng phiếu nhập = số lượng của phiếu xuất
			exportAnimalChange.setQuantity(exportAnimal.getQuantity());
			//số lượng còn lại = số lượng phiếu nhập mới chuyển loại khi chưa tạo thêm phiếu xuất nào
			double remainQuantity=0;			
			if(isNew) {
				exportAnimalChange.setRemainQuantity(exportAnimal.getQuantity());
			}else {
				remainQuantity = this.checkRemainQuantity(exportAnimalChange.getId());
				exportAnimalChange.setRemainQuantity(remainQuantity);
			}
			
			
			//set hướng sản phẩm điều chuyển
			if(productTargetChangeDto!=null && productTargetChangeDto.getId()!=null) {
				ProductTarget pt=productTargetRepository.findOne(productTargetChangeDto.getId());
				if(pt!=null) {
					exportAnimalChange.setProductTarget(pt);
				}
			}
			//tạo mã lô
			if(isNew && exportAnimalChange.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				String code=autoGenBathCode(exportAnimalChange.getDateImport());
				exportAnimalChange.setBatchCode(code);
			}
			importAnimalRepository.save(exportAnimalChange);
		}
		
	}

	@Override
	public List<InventoryReportDto> percentOfGrowthQuantityReport(ReportParamDto paramDto) throws ParseException {
		List<InventoryReportDto> ret=new ArrayList<InventoryReportDto>();	
		List<InventoryReportDto> retNot=new ArrayList<InventoryReportDto>();	
		List<InventoryReportDto> list=this.getQuantityReport(paramDto);
		if(list!=null && list.size()>0) {
			ret.addAll(list);
		}
		int fromYear=0;
		int preYear=0;
		double preQuantity=0;
		if(paramDto.getFromYear()>0) {
			fromYear=paramDto.getFromYear();
		}		
		
		for (InventoryReportDto dto : ret) {
			preYear=0;
			preQuantity=0;
			if(dto.getYearReport()==fromYear) {
				dto.setPercent(0);
			}			 
			else if(dto.getYearReport()!=fromYear) {
				preYear=dto.getYearReport()-1;
				for (InventoryReportDto dto1 : ret) {
					if(dto1.getYearReport()==preYear && dto.getParentId().equals(dto1.getParentId())) {
						preQuantity=dto1.getQuantity();
						break;
					}
				}
				if(preQuantity>0) {
					double percent=((dto.getQuantity() - preQuantity)/preQuantity)*100;
					dto.setPercent(percent);
				}else {
					//trường hợp mốc không có dũ liêu
					retNot.add(dto);
				}
				
			}
		}
				
		//query tất cả các loại vật nuôi
		if(paramDto.getGroupByItems()!=null && paramDto.getGroupByItems().size()>0) {
			List<String> animalParenst=new ArrayList<String>();
			for (String item : paramDto.getGroupByItems()) {
				if(item.equals(WLConstant.FunctionAndGroupByItem.animalParent.getValue())) {
					animalParenst.add(item);
				}
			}
			paramDto.getGroupByItems().removeAll(animalParenst);
		}
		paramDto.setIsSumTotal(true);
		List<InventoryReportDto> lst=this.getQuantityReport(paramDto);
		if(lst!=null && lst.size()>0) {
			for (InventoryReportDto dto : lst) {
				dto.setParentlName("Tất cả các giống");
				preYear=0;
				preQuantity=0;
				if(dto.getYearReport()==fromYear) {
					dto.setPercent(0);
				}			 
				else if(dto.getYearReport()!=fromYear) {
					preYear=dto.getYearReport()-1;
					for (InventoryReportDto dto1 : lst) {
						if(dto1.getYearReport()==preYear) {
							preQuantity=dto1.getQuantity();
							break;
						}
					}
					if(preQuantity>0) {
						double percent=((dto.getQuantity() - preQuantity)/preQuantity)*100;
						dto.setPercent(percent);
					}else {
						//trường hợp mốc không có dũ liêu
						retNot.add(dto);
					}
					
				}
			}
			ret.addAll(lst);
		}
		if(retNot!=null && retNot.size()>0) {
			ret.removeAll(retNot);
		}
		return ret;
	}

	@Override
	public List<InventoryReportDto> percentOfGrowthMeatReport(ReportParamDto paramDto) throws ParseException {
		List<InventoryReportDto> ret=new ArrayList<InventoryReportDto>();		
		List<InventoryReportDto> retNot=new ArrayList<InventoryReportDto>();		
		List<InventoryReportDto> list=this.getQuantityReport(paramDto);
		if(list!=null && list.size()>0) {
			ret.addAll(list);
		}
		int fromYear=0;
		int preYear=0;
		double preAmount=0;
		if(paramDto.getFromYear()>0) {
			fromYear=paramDto.getFromYear();
		}		
		
		for (InventoryReportDto dto : ret) {
			preYear=0;
			 preAmount=0;
			if(dto.getYearReport()==fromYear) {
				dto.setPercent(0);
			}			 
			else if(dto.getYearReport()!=fromYear) {
				preYear=dto.getYearReport()-1;
				for (InventoryReportDto dto1 : ret) {
					if(dto1.getYearReport()==preYear && dto.getParentId().equals(dto1.getParentId())) {
						preAmount=dto1.getAmount();
						break;
					}
				}
				if(preAmount>0) {
					double percent=((dto.getAmount() - preAmount)/preAmount)*100;
					dto.setPercent(percent);
				}else {
					//trường hợp mốc không có dũ liêu
					retNot.add(dto);
				}
				
			}
		}
				
		//query tất cả các loại vật nuôi
		if(paramDto.getGroupByItems()!=null && paramDto.getGroupByItems().size()>0) {
			List<String> animalParenst=new ArrayList<String>();
			for (String item : paramDto.getGroupByItems()) {
				if(item.equals(WLConstant.FunctionAndGroupByItem.animalParent.getValue())) {
					animalParenst.add(item);
				}
			}
			paramDto.getGroupByItems().removeAll(animalParenst);
		}
		paramDto.setIsSumTotal(true);
		List<InventoryReportDto> lst=this.getQuantityReport(paramDto);
		if(lst!=null && lst.size()>0) {
			for (InventoryReportDto dto : lst) {
				dto.setParentlName("Tất cả các giống");
				preYear=0;
				 preAmount=0;
				if(dto.getYearReport()==fromYear) {
					dto.setPercent(0);
				}			 
				else if(dto.getYearReport()!=fromYear) {
					preYear=dto.getYearReport()-1;
					for (InventoryReportDto dto1 : lst) {
						if(dto1.getYearReport()==preYear) {
							preAmount=dto1.getAmount();
							break;
						}
					}
					if(preAmount>0) {
						double percent=((dto.getAmount() - preAmount)/preAmount)*100;
						dto.setPercent(percent);
					}else {
						//trường hợp mốc không có dũ liêu
						retNot.add(dto);
					}
					
				}
			}
			ret.addAll(lst);
		}
		if(retNot!=null && retNot.size()>0) {
			ret.removeAll(retNot);
		}
		return ret;
	}
	
	public boolean checkValidDateImport(Date date,Long  importId) {
		date = WLDateTimeUtil.getStartOfDay(date);
		List<ImportExportAnimal> listExport = importAnimalRepository.findExportByImportGranterDate(date, importId);
		if(listExport!=null && listExport.size()>0) {
			return true;
		}
		return false;
	}
	public void checkAdministrativeUnit(List<FmsAdministrativeUnitDto> list,List<InventoryReportDto> listInventoryReport) {
		boolean isContain = false;
		boolean isContainDistrict = false;
		boolean isContainProvince = false;
		boolean isContainRegion = false;
		
		for (InventoryReportDto inventoryReportDto : listInventoryReport) {
			for (FmsAdministrativeUnitDto administrativeUnitDto : list) {
				if (inventoryReportDto.getWardId() != null && inventoryReportDto.getWardId() > 0) {
					if (administrativeUnitDto.getId().equals(inventoryReportDto.getWardId())) {
						isContain = true;
					}
				}
				if (inventoryReportDto.getDistrictId() != null && inventoryReportDto.getDistrictId() > 0) {
					if (administrativeUnitDto.getId().equals(inventoryReportDto.getDistrictId())) {
						isContainDistrict = true;
					}
				}
				if (inventoryReportDto.getProvinceId() != null && inventoryReportDto.getProvinceId() > 0) {
					if (administrativeUnitDto.getId().equals(inventoryReportDto.getProvinceId())) {
						isContainProvince = true;
					}
				}
				if (inventoryReportDto.getRegionId() != null && inventoryReportDto.getRegionId() > 0) {
					if (administrativeUnitDto.getId().equals(inventoryReportDto.getRegionId())) {
						isContainRegion = true;
					}
				}
			}
			if(!isContain) {
				FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
				dto.setId(inventoryReportDto.getWardId());
				dto.setName(inventoryReportDto.getWardName());
				dto.setParentId(inventoryReportDto.getDistrictId());
				dto.setLevel(1);
				list.add(dto);
			}
			if(!isContainDistrict) {
				FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
				dto.setId(inventoryReportDto.getDistrictId());
				dto.setName(inventoryReportDto.getDistrictName());
				dto.setParentId(inventoryReportDto.getProvinceId());
				dto.setLevel(2);
				list.add(dto);
			}
			if(!isContainProvince) {
				FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
				dto.setId(inventoryReportDto.getProvinceId());
				dto.setName(inventoryReportDto.getProvinceName());
				dto.setParentId(inventoryReportDto.getRegionId());
				dto.setLevel(3);
				list.add(dto);
				
			}
			if(!isContainRegion) {
				FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
				dto.setId(inventoryReportDto.getRegionId());
				dto.setName(inventoryReportDto.getRegionName());
				dto.setParentId(0L);
				dto.setLevel(4);
				list.add(dto);
			}
			
			isContain = false;
			isContainDistrict = false;
			isContainProvince = false;
			isContainRegion = false;
		}
		
	}
	public void checkAdministrativeUnits(List<FmsAdministrativeUnitDto> list,List<LiveStockProductReportDto> listLiveStockProductReport) {
		boolean isContain = false;
		boolean isContainDistrict = false;
		boolean isContainProvince = false;
		boolean isContainRegion = false;
		
		for (LiveStockProductReportDto inventoryReportDto : listLiveStockProductReport) {
			for (FmsAdministrativeUnitDto administrativeUnitDto : list) {
				if (inventoryReportDto.getWardId() != null && inventoryReportDto.getWardId() > 0) {
					if (administrativeUnitDto.getId().equals(inventoryReportDto.getWardId())) {
						isContain = true;
					}
				}
				if (inventoryReportDto.getDistrictId() != null && inventoryReportDto.getDistrictId() > 0) {
					if (administrativeUnitDto.getId().equals(inventoryReportDto.getDistrictId())) {
						isContainDistrict = true;
					}
				}
				if (inventoryReportDto.getProvinceId() != null && inventoryReportDto.getProvinceId() > 0) {
					if (administrativeUnitDto.getId().equals(inventoryReportDto.getProvinceId())) {
						isContainProvince = true;
					}
				}
				if (inventoryReportDto.getRegionId() != null && inventoryReportDto.getRegionId() > 0) {
					if (administrativeUnitDto.getId().equals(inventoryReportDto.getRegionId())) {
						isContainRegion = true;
					}
				}
			}
			if(!isContain) {
				FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
				dto.setId(inventoryReportDto.getWardId());
				dto.setName(inventoryReportDto.getWardName());
				dto.setParentId(inventoryReportDto.getDistrictId());
				dto.setLevel(1);
				list.add(dto);
			}
			if(!isContainDistrict) {
				FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
				dto.setId(inventoryReportDto.getDistrictId());
				dto.setName(inventoryReportDto.getDistrictName());
				dto.setParentId(inventoryReportDto.getProvinceId());
				dto.setLevel(2);
				list.add(dto);
			}
			if(!isContainProvince) {
				FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
				dto.setId(inventoryReportDto.getProvinceId());
				dto.setName(inventoryReportDto.getProvinceName());
				dto.setParentId(inventoryReportDto.getRegionId());
				dto.setLevel(3);
				list.add(dto);
				
			}
			if(!isContainRegion) {
				FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
				dto.setId(inventoryReportDto.getRegionId());
				dto.setName(inventoryReportDto.getRegionName());
				dto.setParentId(0L);
				dto.setLevel(4);
				list.add(dto);
			}
			
			isContain = false;
			isContainDistrict = false;
			isContainProvince = false;
			isContainRegion = false;
		}
		
	}
	
	public List<FmsAdministrativeUnitDto> sortFmsAdministrativeUnitDto(List<FmsAdministrativeUnitDto> list,Long parentId){
		List<FmsAdministrativeUnitDto> ret = new ArrayList<FmsAdministrativeUnitDto>();		
		for (int i = 0; i < list.size(); i++) {			
			if(parentId.equals(list.get(i).getParentId())) {
				System.out.println(list.get(i).getName());
				ret.add(list.get(i));
				ret.addAll(sortFmsAdministrativeUnitDto(list,list.get(i).getId()));
			}
		}
		return ret;
	}
	
	@Override
	public TotalReportDto getTotalReport(Date fromDate,Date toDate) {
		TotalReportDto ret = new TotalReportDto();
		List<FmsAdministrativeUnitDto> listAdministrativeUnit = new ArrayList<FmsAdministrativeUnitDto>();
		List<AnimalDto> listParent = animalRepository.getListParent();
		if(listParent!=null && listParent.size()>0) {
			List<TotalReportSubDto> listSub = new ArrayList<TotalReportSubDto>();
			ret.setListSub(listSub);
			for (AnimalDto animalDto : listParent) {				
				if(animalDto.getReportType().equals(1)) {
					/*
					 * Số con tồn
					 */
					
					TotalReportSubDto sub = new TotalReportSubDto();
					sub.setAnimalCode(animalDto.getCode());
					sub.setAnimalName(animalDto.getName());
					sub.setAnimalId(animalDto.getId());
					sub.setReportType(animalDto.getReportType());
					sub.setSubType(1);
					listSub.add(sub);
					List<TotalReportSubDetailDto> listSubDetail = new ArrayList<TotalReportSubDetailDto>();
					sub.setListSubDetail(listSubDetail);
					ReportParamDto paramDto=new ReportParamDto();
					
					
					//0. Số con hiện có
					TotalReportSubDetailDto detail = new TotalReportSubDetailDto();
					detail.setName("Số con hiện có");
					detail.setOrderNumber(0);					
					List<InventoryReportDto> listInventoryReport = new ArrayList<InventoryReportDto>();					
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					
					List<String> groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//1. Con thịt	
					
					detail = new TotalReportSubDetailDto();
					detail.setName(animalDto.getName()+" thịt");
					detail.setOrderNumber(1);
					
					listInventoryReport = new ArrayList<InventoryReportDto>();
//					detail.setListInventoryReport(listInventoryReport);
					
					paramDto=new ReportParamDto();
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());
					
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());				
					groupByItems.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue());
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					for (InventoryReportDto inv : listInventoryReport) {
						if(WLConstant.ProductTargetCode.MEAT.getValue().equals(inv.getProductTargetCode())) {
							detail.getListInventoryReport().add(inv);
						}						
					}
					listSubDetail.add(detail);
					
					//3. Con đẻ trứng	
					detail = new TotalReportSubDetailDto();
					detail.setName(animalDto.getName()+" đẻ trứng");
					detail.setOrderNumber(3);					
					for (InventoryReportDto inv : listInventoryReport) {
						if(WLConstant.ProductTargetCode.EGG.getValue().equals(inv.getProductTargetCode())) {
							detail.getListInventoryReport().add(inv);
						}
					}
					listSubDetail.add(detail);
					
					//2. Tr đó: Con thịt Nuôi công nghiệp	
					detail = new TotalReportSubDetailDto();
					detail.setName("Tr đó: "+animalDto.getName()+" công nghiệp");
					detail.setOrderNumber(2);
					
					listInventoryReport = new ArrayList<InventoryReportDto>();
					
					paramDto=new ReportParamDto();
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());
					
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());				
					groupByItems.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.liveStockMethod.getValue());
					paramDto.setGroupByItems(groupByItems);
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					for (InventoryReportDto inv : listInventoryReport) {
						if(WLConstant.ProductTargetCode.MEAT.getValue().equals(inv.getProductTargetCode())
								&& WLConstant.AnimalLiveStockMethod.noneOrganic.getValue().equals(inv.getLiveStockMethod()) ) {
							detail.getListInventoryReport().add(inv);
						}
					}
					listSubDetail.add(detail);
					
					//4. Tr đó: Con trứng Nuôi công nghiệp
					detail = new TotalReportSubDetailDto();
					detail.setName("Tr đó: "+animalDto.getName()+" công nghiệp");
					detail.setOrderNumber(4);
					
					for (InventoryReportDto inv : listInventoryReport) {
						if(WLConstant.ProductTargetCode.EGG.getValue().equals(inv.getProductTargetCode())
								&& WLConstant.AnimalLiveStockMethod.noneOrganic.getValue().equals(inv.getLiveStockMethod()) ) {
							detail.getListInventoryReport().add(inv);
						}
					}					
					listSubDetail.add(detail);
					Collections.sort(listSubDetail, new Comparator<TotalReportSubDetailDto>() {
						@Override
						public int compare(TotalReportSubDetailDto o1, TotalReportSubDetailDto o2) {
							// TODO Auto-generated method stub
							int c= 0;
							try {
								c = o1.getOrderNumber().compareTo(o2.getOrderNumber());	
							} catch (Exception e) {
								c= 0;
							}												    					       
						    return c;								
						}
					});
					
					/*
					 * Số con xuất
					 */					
					sub = new TotalReportSubDto();
					sub.setAnimalCode(animalDto.getCode());
					sub.setAnimalName(animalDto.getName());
					sub.setAnimalId(animalDto.getId());
					sub.setReportType(animalDto.getReportType());
					sub.setSubType(2);
					listSub.add(sub);
					listSubDetail = new ArrayList<TotalReportSubDetailDto>();
					sub.setListSubDetail(listSubDetail);
					paramDto=new ReportParamDto();
					
					
					//0. Tổng Số con xuất chuồng
					detail = new TotalReportSubDetailDto();
					detail.setName("Số con xuất chuồng");
					detail.setOrderNumber(0);					
					listInventoryReport = new ArrayList<InventoryReportDto>();
					
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					paramDto.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//2. Sản lượng thịt hơi xuất chuồng	
					detail = new TotalReportSubDetailDto();
					detail.setName("Sản lượng thịt hơi xuất chuồng");
					detail.setOrderNumber(2);					
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//1. Tr đó: Con nuôi công nghiệp					
					detail = new TotalReportSubDetailDto();
					detail.setName("Tr đó: "+animalDto.getName()+ " công nghiệp");
					detail.setOrderNumber(1);					
					listInventoryReport = new ArrayList<InventoryReportDto>();
					
					paramDto=new ReportParamDto();
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());
					paramDto.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());
					paramDto.setLiveStockMethod(WLConstant.AnimalLiveStockMethod.noneOrganic.getValue());
					
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());				
					groupByItems.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue());
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);					
					
					//3. Tr đó: Con thịt Nuôi công nghiệp	
					detail = new TotalReportSubDetailDto();
					detail.setName("Tr đó: "+animalDto.getName()+" công nghiệp");
					detail.setOrderNumber(3);					
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//4. Sản lượng trứng trong kỳ
					detail = new TotalReportSubDetailDto();
					detail.setName("Sản lượng trứng trong kỳ");
					detail.setOrderNumber(4);
								
					
					paramDto=new ReportParamDto();
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());
					paramDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
					
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());		
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = exportEggService.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);	
					
					//5. Tr đó: Gà công nghiệp
					detail = new TotalReportSubDetailDto();
					detail.setName(animalDto.getName()+" công nghiệp");
					detail.setOrderNumber(5);
									
					
					paramDto=new ReportParamDto();
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());
					paramDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
					paramDto.setLiveStockMethod(WLConstant.AnimalLiveStockMethod.noneOrganic.getValue());
					
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());		
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = exportEggService.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);			
					listSubDetail.add(detail);
					Collections.sort(listSubDetail, new Comparator<TotalReportSubDetailDto>() {
						@Override
						public int compare(TotalReportSubDetailDto o1, TotalReportSubDetailDto o2) {
							// TODO Auto-generated method stub
							int c= 0;
							try {
								c = o1.getOrderNumber().compareTo(o2.getOrderNumber());	
							} catch (Exception e) {
								c= 0;
							}												    					       
						    return c;								
						}
					});
				}
				else if(animalDto.getReportType().equals(2)) {
					TotalReportSubDto sub = new TotalReportSubDto();
					sub.setAnimalCode(animalDto.getCode());
					sub.setAnimalName(animalDto.getName());
					sub.setAnimalId(animalDto.getId());
					sub.setReportType(animalDto.getReportType());
					sub.setSubType(1);
					listSub.add(sub);
					List<TotalReportSubDetailDto> listSubDetail = new ArrayList<TotalReportSubDetailDto>();
					sub.setListSubDetail(listSubDetail);
					ReportParamDto paramDto=new ReportParamDto();
					
					
					//0. Số con hiện có
					TotalReportSubDetailDto detail = new TotalReportSubDetailDto();
					detail.setName("Số con hiện có");
					detail.setOrderNumber(0);					
					List<InventoryReportDto> listInventoryReport = new ArrayList<InventoryReportDto>();					
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					
					List<String> groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
										
					//1. Con trứng
					paramDto=new ReportParamDto();
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());
					
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());				
					groupByItems.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue());
					paramDto.setGroupByItems(groupByItems);
					
					detail = new TotalReportSubDetailDto();
					detail.setName("Tr đó: "+animalDto.getName()+" đẻ trứng");
					detail.setOrderNumber(1);					
					for (InventoryReportDto inv : listInventoryReport) {
						if(WLConstant.ProductTargetCode.EGG.getValue().equals(inv.getProductTargetCode())) {
							detail.getListInventoryReport().add(inv);
						}
					}
					listSubDetail.add(detail);
					
					
					//2. Số con xuất chuồng
					detail = new TotalReportSubDetailDto();
					detail.setName("Số con xuất chuồng");
					detail.setOrderNumber(2);					
					listInventoryReport = new ArrayList<InventoryReportDto>();				
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					paramDto.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//3. Sản lượng thịt hơi xuất chuồng	
					detail = new TotalReportSubDetailDto();
					detail.setName("Sản lượng thịt hơi xuất chuồng");
					detail.setOrderNumber(3);					
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//4. Sản lượng trứng trong kỳ
					detail = new TotalReportSubDetailDto();
					detail.setName("Sản lượng trứng trong kỳ");
					detail.setOrderNumber(4);
								
					
					paramDto=new ReportParamDto();
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());
					paramDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
					
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());		
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = exportEggService.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					Collections.sort(listSubDetail, new Comparator<TotalReportSubDetailDto>() {
						@Override
						public int compare(TotalReportSubDetailDto o1, TotalReportSubDetailDto o2) {
							// TODO Auto-generated method stub
							int c= 0;
							try {
								c = o1.getOrderNumber().compareTo(o2.getOrderNumber());	
							} catch (Exception e) {
								c= 0;
							}												    					       
						    return c;								
						}
					});
				}
				else if(animalDto.getReportType().equals(3)) {
					TotalReportSubDto sub = new TotalReportSubDto();
					sub.setAnimalCode(animalDto.getCode());
					sub.setAnimalName(animalDto.getName());
					sub.setAnimalId(animalDto.getId());
					sub.setReportType(animalDto.getReportType());
					sub.setSubType(1);
					listSub.add(sub);
					List<TotalReportSubDetailDto> listSubDetail = new ArrayList<TotalReportSubDetailDto>();
					sub.setListSubDetail(listSubDetail);
					ReportParamDto paramDto=new ReportParamDto();
					
					
					//0. Số con hiện có
					TotalReportSubDetailDto detail = new TotalReportSubDetailDto();
					detail.setName("Số con hiện có");
					detail.setOrderNumber(0);					
					List<InventoryReportDto> listInventoryReport = new ArrayList<InventoryReportDto>();					
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					
					List<String> groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
						
					//1. Số con xuất chuồng
					detail = new TotalReportSubDetailDto();
					detail.setName("Số con xuất chuồng");
					detail.setOrderNumber(1);					
					listInventoryReport = new ArrayList<InventoryReportDto>();				
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					paramDto.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//2. Sản lượng thịt hơi xuất chuồng	
					detail = new TotalReportSubDetailDto();
					detail.setName("Sản lượng thịt hơi xuất chuồng");
					detail.setOrderNumber(2);					
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//3. Sản lượng trứng trong kỳ
					detail = new TotalReportSubDetailDto();
					detail.setName("Sản lượng trứng trong kỳ");
					detail.setOrderNumber(3);
								
					
					paramDto=new ReportParamDto();
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());
					paramDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
					
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());		
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = exportEggService.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					Collections.sort(listSubDetail, new Comparator<TotalReportSubDetailDto>() {
						@Override
						public int compare(TotalReportSubDetailDto o1, TotalReportSubDetailDto o2) {
							// TODO Auto-generated method stub
							int c= 0;
							try {
								c = o1.getOrderNumber().compareTo(o2.getOrderNumber());	
							} catch (Exception e) {
								c= 0;
							}												    					       
						    return c;								
						}
					});
				}
				else if(animalDto.getReportType().equals(4)) {
					TotalReportSubDto sub = new TotalReportSubDto();
					sub.setAnimalCode(animalDto.getCode());
					sub.setAnimalName(animalDto.getName());
					sub.setAnimalId(animalDto.getId());
					sub.setReportType(animalDto.getReportType());
					sub.setSubType(1);
					listSub.add(sub);
					List<TotalReportSubDetailDto> listSubDetail = new ArrayList<TotalReportSubDetailDto>();
					sub.setListSubDetail(listSubDetail);
					ReportParamDto paramDto=new ReportParamDto();
					
					
					//0. Số con hiện có
					TotalReportSubDetailDto detail = new TotalReportSubDetailDto();
					detail.setName("Số con hiện có");
					detail.setOrderNumber(0);					
					List<InventoryReportDto> listInventoryReport = new ArrayList<InventoryReportDto>();					
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					
					List<String> groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
						
					//1. Số con xuất chuồng
					detail = new TotalReportSubDetailDto();
					detail.setName("Số con xuất chuồng");
					detail.setOrderNumber(1);					
					listInventoryReport = new ArrayList<InventoryReportDto>();				
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					paramDto.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());
					groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
					
					//2. Sản lượng thịt hơi xuất chuồng	
					detail = new TotalReportSubDetailDto();
					detail.setName("Sản lượng thịt hơi xuất chuồng");
					detail.setOrderNumber(2);					
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);
				}
				else if(animalDto.getReportType().equals(5)) {
					TotalReportSubDto sub = new TotalReportSubDto();
					sub.setAnimalCode(animalDto.getCode());
					sub.setAnimalName(animalDto.getName());
					sub.setAnimalId(animalDto.getId());
					sub.setReportType(animalDto.getReportType());
					sub.setSubType(1);
					listSub.add(sub);
					List<TotalReportSubDetailDto> listSubDetail = new ArrayList<TotalReportSubDetailDto>();
					sub.setListSubDetail(listSubDetail);
					ReportParamDto paramDto=new ReportParamDto();
					
					
					//0. Số con hiện có
					TotalReportSubDetailDto detail = new TotalReportSubDetailDto();
					detail.setName("Số con hiện có");
					detail.setOrderNumber(0);					
					List<InventoryReportDto> listInventoryReport = new ArrayList<InventoryReportDto>();					
					
					paramDto.setFromDate(fromDate);
					paramDto.setToDate(toDate);
					paramDto.setAnimalParentId(animalDto.getId());	
					
					List<String> groupByItems = new ArrayList<String>();
					groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
					groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
					paramDto.setGroupByItems(groupByItems);
					
					listInventoryReport = this.getSumQuantity(paramDto);
					this.checkAdministrativeUnit(ret.getListAdministrativeUnit(), listInventoryReport);
					detail.setListInventoryReport(listInventoryReport);
					listSubDetail.add(detail);		
					
					//1. Sản lượng
					
					detail = new TotalReportSubDetailDto();					
					detail.setOrderNumber(1);	
					
					List<LiveStockProduct> listProduct = livelStockProductRepository.findByAnimalId(sub.getAnimalId());
					if(listProduct!=null && listProduct.size()>0) {
						LiveStockProduct pro = listProduct.get(0);
						
						detail.setName("Sản lượng "+pro.getName());
						detail.setProductName(pro.getName());
						
						if(pro.getUnitAmount()!=null) {
							detail.setUnitName(pro.getUnitAmount().getName());
						}
						else if(pro.getUnitQuantity()!=null) {
							detail.setUnitName(pro.getUnitQuantity().getName());
						}
						
						paramDto.setFromDate(fromDate);
						paramDto.setToDate(toDate);
						paramDto.setAnimalParentId(animalDto.getId());	
						
						groupByItems = new ArrayList<String>();
						groupByItems.add(WLConstant.FunctionAndGroupByItem.ward.getValue());
						groupByItems.add(WLConstant.FunctionAndGroupByItem.district.getValue());
						groupByItems.add(WLConstant.FunctionAndGroupByItem.province.getValue());
						groupByItems.add(WLConstant.FunctionAndGroupByItem.region.getValue());					
						paramDto.setGroupByItems(groupByItems);						
						List<LiveStockProductReportDto>  listLiveStockProductReport = importExportLivelStockProductService.getSumQuantity(paramDto);
						this.checkAdministrativeUnits(ret.getListAdministrativeUnit(), listLiveStockProductReport);
						detail.setListLiveStockProductReport(listLiveStockProductReport);
					}
					listSubDetail.add(detail);
				}
			}
		}
		ret.setListAdministrativeUnit(this.sortFmsAdministrativeUnitDto(ret.getListAdministrativeUnit(), 0L));
		return ret;
	}

	@Override
	public List<ImportExportAnimalDto> getAllBySearch(
			ImportExportAnimalSearchDto searchDto) {
		
		String namecode = searchDto.getNameOrCode();
		String sql = " select new com.globits.wl.dto.ImportExportAnimalDto(fa) from ImportExportAnimal fa  where (1=1)";
		String sqlCount = "select count(fa.id) from ImportExportAnimal fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.batchCode like :namecode or fa.farm.code like :namecode or fa.farm.name like :namecode or fa.animal.name like :namecode )";
		}
		if(searchDto.getFarmId()!=null){
			whereClause += " and (fa.farm.id= :farmId)";
		}
		if(searchDto.getCreate_by()!=null){
			whereClause += " and (fa.created_by= :created_by)";
		}
		if(searchDto.getCreate_date()!=null){
			whereClause += " and (fa.create_date= :create_date)";
		}
		if(searchDto.getWardsId()!=null) {//đây là query theo xã
			whereClause += " and (fa.farm.administrativeUnit.id= :wardsId)";
		}
		if(searchDto.getDistrictId()!=null) {//đây là query theo huyện
			whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(searchDto.getCityId()!=null) {
			whereClause += " and fa.farm.administrativeUnit.parent.parent.id  = :provinceId";
		}
//		if(searchDto.getCityId()!=null && searchDto.getDistrictIds()!=null && searchDto.getDistrictIds().size()>0) {//đây là query theo tỉnh lấy list id của huyện
//			whereClause += " and (fa.farm.administrativeUnit.parent.id in :districtIds)";
//		}
		if(searchDto.getRemainQuantityDown() != null) {
			whereClause += " and fa.remainQuantity  > :remainQuantityDown";
		}
		if(searchDto.getProductTargetCode() != null) {
			whereClause += " and fa.productTarget.code = :productTargetCode";
		}
		if(searchDto.getType()==1){//nhập
			if(searchDto.getFromDate()!=null){
				whereClause += " and (fa.dateImport >= :fromDate)";
			}
			if(searchDto.getToDate()!=null){
				whereClause += " and (fa.dateImport <= :toDate)";
			}
			if(searchDto.getIsImportExportAnimalSeed()!=null) {
				if (searchDto.getIsImportExportAnimalSeed()) {		//Nếu = true thì tìm kiếm theo import export con giống
					whereClause += " AND (fa.seedLevel IS NOT NULL ) ";
				}
				else {
					whereClause += " AND (fa.seedLevel IS NULL ) ";
				}
			}
		}else if(searchDto.getType()==-1){//xuất
			if(searchDto.getFromDate()!=null){
				whereClause += " and (fa.dateExport >= :fromDate)";
			}
			if(searchDto.getToDate()!=null){
				whereClause += " and (fa.dateExport <= :toDate)";
			}
		}
		whereClause += " and (fa.type= :type)";
		
				
		sql +=whereClause;
		if(searchDto.getType()==1) {
			sql +=" order by fa.dateImport desc, fa.batchCode desc ";
		}else if(searchDto.getType()==-1) {
			sql +=" order by fa.dateExport desc, fa.batchCode desc ";
		}
		
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,ImportExportAnimalDto.class);
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
		}	
		if(searchDto.getFarmId()!=null){
			q.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getCreate_by()!=null){
			q.setParameter("created_by", searchDto.getCreate_by());
		}
		if(searchDto.getCreate_date()!=null){
			q.setParameter("create_date", searchDto.getCreate_date());
		}
		if(searchDto.getWardsId()!=null) {//đây là query theo xã
			q.setParameter("wardsId", searchDto.getWardsId());
		}
		if(searchDto.getDistrictId()!=null) {//đây là query theo huyện
			q.setParameter("districtId", searchDto.getDistrictId());
		}
		if(searchDto.getCityId()!=null ) {//đây là query theo tỉnh 
			q.setParameter("provinceId", searchDto.getCityId());
		}
		if(searchDto.getRemainQuantityDown() != null) {
			double remainQuantityDow = Double.valueOf(searchDto.getRemainQuantityDown().intValue()+"");
			q.setParameter("remainQuantityDown", remainQuantityDow);
		}
		if(searchDto.getProductTargetCode() != null) {
			q.setParameter("productTargetCode", searchDto.getProductTargetCode());
		}
		if(searchDto.getFromDate()!=null) {
			q.setParameter("fromDate", searchDto.getFromDate());
		}
		if(searchDto.getToDate()!=null) {
			q.setParameter("toDate", searchDto.getToDate());
		}
		
		q.setParameter("type", searchDto.getType());
		
		return q.getResultList();	
	}
	// Thông tin về loại động vật trừ gấu
	@Override
	public List<AnimalRaisingReportDto> getReportAnimalRaising(AnimalRaisingSearchDto search){
		// Tìm thông tin động vật trừ gấu
		search.setParentCode(WLConstant.BEAR_CODE);
		search.setIsEqualParentCode(false);
		search.setLevels(new ArrayList<Integer>());
		search.getLevels().add(WLConstant.SeedLevel.animalParent.getValue());
		search.getLevels().add(WLConstant.SeedLevel.animalNewBorn.getValue());
		
		List<AnimalRaisingReportDto> listResult = getReportAnimalRaisingAll(search);
		
		return listResult;
	}
	
	// thông tin về tất cả các loài động vật
	public List<AnimalRaisingReportDto> getReportAnimalRaisingAll(AnimalRaisingSearchDto search){
		List<AnimalRaisingReportDto> list = new ArrayList<AnimalRaisingReportDto>();
		List<AnimalRaisingReportDto> listResult = new ArrayList<AnimalRaisingReportDto>();
		String sql = "select new com.globits.wl.dto.report.AnimalRaisingReportDto(iea) FROM ImportExportAnimal iea ";
		
		String joinStr = " ";
		
		String whereClause = " where 1=1 ";
		
		if(search.getLevels() != null) {
			whereClause += " and iea.seedLevel.level in (:levels)";
		}
		if(search.getFarmId() != null) {
			whereClause += " and iea.farm.id = :farmId ";
		}
		if(search.getAnimalParentId() != null) {
			whereClause += " and iea.animal.parent.id = :animalParentId ";
		}
		if(search.getParentCode() != null && search.getIsEqualParentCode() != null) {
			if(search.getIsEqualParentCode()) {
				whereClause += " and iea.animal.parent is not null and iea.animal.parent.code = :parentCode";
			}else {
				whereClause += " and iea.animal.parent is not null and iea.animal.parent.code != :parentCode";
			}
		}
		
		String queryGroup = " ";
		
		sql += joinStr + whereClause + queryGroup;
		
		Query query = manager.createQuery(sql, AnimalRaisingReportDto.class);
		if(search.getLevels() != null) {
			query.setParameter("levels", search.getLevels());
		}
		if(search.getFarmId() != null) {
			query.setParameter("farmId", search.getFarmId());
		}
		if(search.getAnimalParentId() != null) {
			query.setParameter("animalParentId", search.getAnimalParentId());
		}
		if(search.getParentCode() != null && search.getIsEqualParentCode() != null) {
			query.setParameter("parentCode", search.getParentCode());
		}
		list = query.getResultList();// List tên động vật /nguồn gốc mục đích, ten khoa hoc
		Map<String, AnimalRaisingReportDto> map = new HashMap<String, AnimalRaisingReportDto>();
		for(AnimalRaisingReportDto dto: list) {
			if(dto != null && dto.getAnimalCode() != null) {
				if(map.containsKey(dto.getAnimalCode())) {
					if(dto.getQuantity() != null) {
						AnimalRaisingReportDto itemValue = map.get(dto.getAnimalCode());
						itemValue.setTotal(itemValue.getTotal() + dto.getQuantity() * dto.getType());
						
						// Set list ký hiệu nguồn gốc và đối tượng
						if(itemValue.getOriginals() == null)
						itemValue.setOriginals(new HashSet<AnimalRaisingSymbolNameDto>());
						if(itemValue.getProductTargets() == null)
						itemValue.setProductTargets(new HashSet<AnimalRaisingSymbolNameDto>());
						AnimalRaisingSymbolNameDto symbolOriginal = new AnimalRaisingSymbolNameDto();
						symbolOriginal.setSymbol(dto.getOriginCode());
						symbolOriginal.setName(dto.getOriginName());
						itemValue.getOriginals().add(symbolOriginal);
						AnimalRaisingSymbolNameDto symbolProductTarget = new AnimalRaisingSymbolNameDto();
						symbolProductTarget.setSymbol(dto.getProductTargetCode());
						symbolProductTarget.setName(dto.getProductTargetName());
						itemValue.getProductTargets().add(symbolProductTarget);
					}
				}else {
					AnimalRaisingReportDto itemValue = new AnimalRaisingReportDto();
					// Set list ký hiệu nguồn gốc và đối tượng
					if(itemValue.getOriginals() == null)
					itemValue.setOriginals(new HashSet<AnimalRaisingSymbolNameDto>());
					if(itemValue.getProductTargets() == null)
					itemValue.setProductTargets(new HashSet<AnimalRaisingSymbolNameDto>());
					AnimalRaisingSymbolNameDto symbolOriginal = new AnimalRaisingSymbolNameDto();
					symbolOriginal.setSymbol(dto.getOriginCode());
					symbolOriginal.setName(dto.getOriginName());
					itemValue.getOriginals().add(symbolOriginal);
					AnimalRaisingSymbolNameDto symbolProductTarget = new AnimalRaisingSymbolNameDto();
					symbolProductTarget.setSymbol(dto.getProductTargetCode());
					symbolProductTarget.setName(dto.getProductTargetName());
					itemValue.getProductTargets().add(symbolProductTarget);
					
					// Set thông tin cơ bản
					itemValue.setAnimalName(dto.getAnimalName());
					itemValue.setAnimalScienceName(dto.getAnimalScienceName());
					itemValue.setBatchCode(dto.getBatchCode());
					itemValue.setGender(dto.getGender());
					itemValue.setOriginName(dto.getOriginName());
					itemValue.setProductTargetName(dto.getProductTargetName());
					itemValue.setQuantity(dto.getQuantity());
					itemValue.setSeedLevelId(dto.getSeedLevelId());
					itemValue.setType(dto.getType());
					itemValue.setOriginCode(dto.getOriginCode());
					itemValue.setProductTargetCode(dto.getProductTargetCode());
					itemValue.setTotal(dto.getType()*dto.getQuantity());
					// Tính toán theo seedLevel
					
					map.put(dto.getAnimalCode(), itemValue);
				}
			}
		}
		
		search.setLevel(WLConstant.SeedLevel.animalParent.getValue());
		search.setGender(WLConstant.AnimalGender.Male.getValue());
		List<AnimalRaisingReportDto> listParentMale = getListTotalAnimalRaising(search);
		for(AnimalRaisingReportDto element: listParentMale) {
			if(map.containsKey(element.getAnimalCode())) {
				if(map.get(element.getAnimalCode()).getTotalParentMale() == null) {
					map.get(element.getAnimalCode()).setTotalParentMale(0d);
				}
				if(map.get(element.getAnimalCode()).getTotalParent() == null) {
					map.get(element.getAnimalCode()).setTotalParent(0d);
				}
				map.get(element.getAnimalCode()).setTotalParentMale(map.get(element.getAnimalCode()).getTotalParentMale() + element.getTotal());
				map.get(element.getAnimalCode()).setTotalParent(map.get(element.getAnimalCode()).getTotalParent() + element.getTotal());
			}
		}
		
		search.setLevel(WLConstant.SeedLevel.animalParent.getValue());
		search.setGender(WLConstant.AnimalGender.Female.getValue());
		List<AnimalRaisingReportDto> listParentFeMale = getListTotalAnimalRaising(search);
		for(AnimalRaisingReportDto element: listParentFeMale) {
			if(map.containsKey(element.getAnimalCode())) {
				if(map.get(element.getAnimalCode()).getTotalParentFeMale() == null) {
					map.get(element.getAnimalCode()).setTotalParentFeMale(0d);
				}
				if(map.get(element.getAnimalCode()).getTotalParent() == null) {
					map.get(element.getAnimalCode()).setTotalParent(0d);
				}
				map.get(element.getAnimalCode()).setTotalParentFeMale(map.get(element.getAnimalCode()).getTotalParentFeMale() + element.getTotal());
				map.get(element.getAnimalCode()).setTotalParent(map.get(element.getAnimalCode()).getTotalParent() + element.getTotal());
			}
		}
		
		search.setLevel(WLConstant.SeedLevel.animalNewBorn.getValue());
		search.setGender(WLConstant.AnimalGender.Male.getValue());
		search.setYearOld(1);
		search.setIsGranterThan(true);
		List<AnimalRaisingReportDto> listChildMale = getListTotalAnimalRaising(search);
		for(AnimalRaisingReportDto element: listChildMale) {
			if(map.containsKey(element.getAnimalCode())) {
				if(map.get(element.getAnimalCode()).getTotalChildMale() == null) {
					map.get(element.getAnimalCode()).setTotalChildMale(0d);
				}
				if(map.get(element.getAnimalCode()).getTotalChild() == null) {
					map.get(element.getAnimalCode()).setTotalChild(0d);
				}
				map.get(element.getAnimalCode()).setTotalChildMale(map.get(element.getAnimalCode()).getTotalChildMale() + element.getTotal());
				map.get(element.getAnimalCode()).setTotalChild(map.get(element.getAnimalCode()).getTotalChild() + element.getTotal());
			}
		}
		
		search.setLevel(WLConstant.SeedLevel.animalNewBorn.getValue());
		search.setGender(WLConstant.AnimalGender.Female.getValue());
		search.setYearOld(1);
		search.setIsGranterThan(true);
		List<AnimalRaisingReportDto> listChildFeMale = getListTotalAnimalRaising(search);
		for(AnimalRaisingReportDto element: listChildFeMale) {
			if(map.containsKey(element.getAnimalCode())) {
				if(map.get(element.getAnimalCode()).getTotalChildFemale() == null) {
					map.get(element.getAnimalCode()).setTotalChildFemale(0d);
				}
				if(map.get(element.getAnimalCode()).getTotalChild() == null) {
					map.get(element.getAnimalCode()).setTotalChild(0d);
				}
				map.get(element.getAnimalCode()).setTotalChildFemale(map.get(element.getAnimalCode()).getTotalChildFemale() + element.getTotal());
				map.get(element.getAnimalCode()).setTotalChild(map.get(element.getAnimalCode()).getTotalChild() + element.getTotal());
			}
		}
		
		search.setLevel(WLConstant.SeedLevel.animalNewBorn.getValue());
		search.setGender(WLConstant.AnimalGender.UnGender.getValue());
		search.setYearOld(1);
		search.setIsGranterThan(true);
		List<AnimalRaisingReportDto> listChildUnknown = getListTotalAnimalRaising(search);
		for(AnimalRaisingReportDto element: listChildUnknown) {
			if(map.containsKey(element.getAnimalCode())) {
				if(map.get(element.getAnimalCode()).getTotalChildUnknown() == null) {
					map.get(element.getAnimalCode()).setTotalChildUnknown(0d);
				}
				if(map.get(element.getAnimalCode()).getTotalChild() == null) {
					map.get(element.getAnimalCode()).setTotalChild(0d);
				}
				map.get(element.getAnimalCode()).setTotalChildUnknown(map.get(element.getAnimalCode()).getTotalChildUnknown() + element.getTotal());
				map.get(element.getAnimalCode()).setTotalChild(map.get(element.getAnimalCode()).getTotalChild() + element.getTotal());
			}
		}
		search.setLevel(WLConstant.SeedLevel.animalNewBorn.getValue());
		search.setGender(null);
		search.setYearOld(1);
		search.setIsGranterThan(false);
		List<AnimalRaisingReportDto> listChildLessThan1YearOld= getListTotalAnimalRaising(search);
		for(AnimalRaisingReportDto element: listChildLessThan1YearOld) {
			if(map.containsKey(element.getAnimalCode())) {
				if(map.get(element.getAnimalCode()).getTotalChildLessThan1Year() == null) {
					map.get(element.getAnimalCode()).setTotalChildLessThan1Year(0d);
				}
				map.get(element.getAnimalCode()).setTotalChildLessThan1Year(map.get(element.getAnimalCode()).getTotalChildLessThan1Year() + element.getTotal());
			}
		}
		
		for(String key: map.keySet()) {
			listResult.add(map.get(key));
		}
		
		return listResult;
	}
	
	public List<AnimalRaisingReportDto> getListTotalAnimalRaising(AnimalRaisingSearchDto search){
		List<AnimalRaisingReportDto> list = new ArrayList<AnimalRaisingReportDto>();
		String sql = "select new com.globits.wl.dto.report.AnimalRaisingReportDto( iea.animal.code, SUM(iea.quantity * iea.type) ) FROM ImportExportAnimal iea ";
		if(search.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
			sql = "select new com.globits.wl.dto.report.AnimalRaisingReportDto( iea.animal.code, SUM(iea.quantity) ) FROM ImportExportAnimal iea ";
		}
		String whereClause = " where 1=1 ";
		if(search.getFarmId() != null) {
			whereClause +=" and iea.farm.id = :farmId";
		}
		if(search.getAnimalParentId() != null) {
			whereClause +=" and iea.animal.parent.id = :animalParentId";
		}
		if(search.getLevel() != null) {
			whereClause += " and  iea.seedLevel.level = :level ";
		}
		if(search.getGender() != null) {
			whereClause += " and iea.gender = :gender ";
		}
		if(search.getYearOld() != null && search.getIsGranterThan() == true) {
			whereClause += " and iea.dayOld > :dayOld";
		}
		if(search.getYearOld() != null && search.getIsGranterThan() == false) {
			whereClause += " and iea.dayOld < :dayOld";
		}
		if(search.getProductTargetCode() !=  null) {
			whereClause += " and iea.farm.id not in (select fpt.farm.id FROM FarmProductTarget fpt where fpt.productTarget.code = :productTargetCode) ";
		}
		if(search.getIsDateIssueRange() != null && search.getIsDateIssueRange() == true) {
			if(search.getDateIssue() != null) {
				whereClause += " and iea.dateIssue >= :startDate and iea.dateIssue <= :endDate ";
			}
		}else {
			if(search.getDateIssue() != null) {
				whereClause += " and iea.dateIssue <= :startDate";
			}
		}
		if(search.getParentCode() != null && search.getIsEqualParentCode() != null) {
			if(search.getIsEqualParentCode()) {
				whereClause += " and iea.animal.parent is not null and iea.animal.parent.code = :parentCode";
			}else {
				whereClause += " and iea.animal.parent is not null and iea.animal.parent.code != :parentCode";
			}
		}
		if(search.getType() != null) {
			whereClause += " and iea.type = :type";
		}
		
		String queryGroup = " group by iea.animal.code ";
		
		sql += whereClause + queryGroup;
		
		Query query = manager.createQuery(sql, AnimalRaisingReportDto.class);
		
		if(search.getFarmId() != null) {
			query.setParameter("farmId", search.getFarmId());
		}
		if(search.getAnimalParentId() != null) {
			query.setParameter("animalParentId", search.getAnimalParentId());
		}
		if(search.getLevel() != null) {
			query.setParameter("level", search.getLevel());
		}
		if(search.getGender() != null) {
			query.setParameter("gender", search.getGender());
		}
		if(search.getYearOld() != null && search.getIsGranterThan() == true) {
			query.setParameter("dayOld", search.getYearOld() * 365);
		}
		if(search.getYearOld() != null && search.getIsGranterThan() == false) {
			query.setParameter("dayOld", search.getYearOld() * WLConstant.YEAR_OLDS);
		}
		if(search.getProductTargetCode() !=  null) {
			query.setParameter("productTargetCode", search.getProductTargetCode());
		}
		if(search.getParentCode() != null && search.getIsEqualParentCode() != null) {
			query.setParameter("parentCode", search.getParentCode());
		}
		if(search.getType() != null) {
			query.setParameter("type", search.getType());
		}
		if(search.getIsDateIssueRange() != null && search.getIsDateIssueRange() == true) {
			if(search.getDateIssue() != null) {
				query.setParameter("startDate", WLDateTimeUtil.getStartOfDay(search.getDateIssue()));
				query.setParameter("endDate", WLDateTimeUtil.getEndOfDay(search.getDateIssue()));
			}
		}else {
			if(search.getDateIssue() != null) {
				query.setParameter("startDate", WLDateTimeUtil.getStartOfDay(search.getDateIssue()));
			}
		}
		
		list = query.getResultList();
		
		return list;
	}

	@Override
	public List<AnimalRaisingReportDto> getAnimalWithTotalQuantity(AnimalRaisingSearchDto paramDto) {
		List<AnimalRaisingReportDto> listResult = getAnimalRaisingAll(paramDto);
		return listResult;
	}
	
	public List<AnimalRaisingReportDto> getAnimalRaisingAll(AnimalRaisingSearchDto search){
		
		List<AnimalRaisingReportDto> list = new ArrayList<AnimalRaisingReportDto>();
		List<AnimalRaisingReportDto> listResult = new ArrayList<AnimalRaisingReportDto>();
		String sql = "select new com.globits.wl.dto.report.AnimalRaisingReportDto(iea) FROM ImportExportAnimal iea ";
		
		String joinStr = " ";
		
		String whereClause = " where 1=1 ";
		
		if(search.getFarmId() != null) {
			whereClause += " and iea.farm.id = :farmId ";
		}
		if(search.getAnimalParentId() != null) {
			whereClause += " and iea.animal.parent.id = :animalParentId ";
		}
		if(search.getParentCode() != null && search.getIsEqualParentCode() != null) {
			if(search.getIsEqualParentCode()) {
				whereClause += " and iea.animal.parent is not null and iea.animal.parent.code = :parentCode";
			}else {
				whereClause += " and iea.animal.parent is not null and iea.animal.parent.code != :parentCode";
			}
		}
		
		String queryGroup = " ";
		
		sql += joinStr + whereClause + queryGroup;
		
		Query query = manager.createQuery(sql, AnimalRaisingReportDto.class);
		
		if(search.getFarmId() != null) {
			query.setParameter("farmId", search.getFarmId());
		}
		if(search.getAnimalParentId() != null) {
			query.setParameter("animalParentId", search.getAnimalParentId());
		}
		if(search.getParentCode() != null && search.getIsEqualParentCode() != null) {
			query.setParameter("parentCode", WLConstant.BEAR_CODE);
		}
		list = query.getResultList();// List tên động vật /nguồn gốc mục đích, ten khoa hoc
		Map<String, AnimalRaisingReportDto> map = new HashMap<String, AnimalRaisingReportDto>();
		for(AnimalRaisingReportDto dto: list) {
			if(dto != null && dto.getAnimalCode() != null) {
				if(map.containsKey(dto.getAnimalCode())) {
					if(dto.getQuantity() != null) {
						AnimalRaisingReportDto itemValue = map.get(dto.getAnimalCode());
						itemValue.setTotal(itemValue.getTotal() + dto.getQuantity() * dto.getType());
						
						// Set list ký hiệu nguồn gốc và đối tượng
						if(itemValue.getOriginals() == null)
						itemValue.setOriginals(new HashSet<AnimalRaisingSymbolNameDto>());
						if(itemValue.getProductTargets() == null)
						itemValue.setProductTargets(new HashSet<AnimalRaisingSymbolNameDto>());
						AnimalRaisingSymbolNameDto symbolOriginal = new AnimalRaisingSymbolNameDto();
						symbolOriginal.setSymbol(dto.getOriginCode());
						symbolOriginal.setName(dto.getOriginName());
						itemValue.getOriginals().add(symbolOriginal);
						AnimalRaisingSymbolNameDto symbolProductTarget = new AnimalRaisingSymbolNameDto();
						symbolProductTarget.setSymbol(dto.getProductTargetCode());
						symbolProductTarget.setName(dto.getProductTargetName());
						itemValue.getProductTargets().add(symbolProductTarget);
					}
				}else {
					AnimalRaisingReportDto itemValue = new AnimalRaisingReportDto();
					// Set list ký hiệu nguồn gốc và đối tượng
					if(itemValue.getOriginals() == null)
					itemValue.setOriginals(new HashSet<AnimalRaisingSymbolNameDto>());
					if(itemValue.getProductTargets() == null)
					itemValue.setProductTargets(new HashSet<AnimalRaisingSymbolNameDto>());
					AnimalRaisingSymbolNameDto symbolOriginal = new AnimalRaisingSymbolNameDto();
					symbolOriginal.setSymbol(dto.getOriginCode());
					symbolOriginal.setName(dto.getOriginName());
					itemValue.getOriginals().add(symbolOriginal);
					AnimalRaisingSymbolNameDto symbolProductTarget = new AnimalRaisingSymbolNameDto();
					symbolProductTarget.setSymbol(dto.getProductTargetCode());
					symbolProductTarget.setName(dto.getProductTargetName());
					itemValue.getProductTargets().add(symbolProductTarget);
					
					// Set thông tin cơ bản
					itemValue.setAnimalName(dto.getAnimalName());
					itemValue.setAnimalScienceName(dto.getAnimalScienceName());
					itemValue.setBatchCode(dto.getBatchCode());
					itemValue.setGender(dto.getGender());
					itemValue.setOriginName(dto.getOriginName());
					itemValue.setProductTargetName(dto.getProductTargetName());
					itemValue.setQuantity(dto.getQuantity());
					itemValue.setSeedLevelId(dto.getSeedLevelId());
					itemValue.setType(dto.getType());
					itemValue.setOriginCode(dto.getOriginCode());
					itemValue.setProductTargetCode(dto.getProductTargetCode());
					itemValue.setTotal(dto.getType()*dto.getQuantity());
					// Tính toán theo seedLevel
					
					map.put(dto.getAnimalCode(), itemValue);
				}
			}
		}
		
		for(String key: map.keySet()) {
			listResult.add(map.get(key));
		}
		
		return listResult;
	}
	public List<AnimalRaisingReportDto> getAnimalRaisingBySearchDto(AnimalRaisingSearchDto search){
		String sql = "select new com.globits.wl.dto.report.AnimalRaisingReportDto(iea) FROM ImportExportAnimal iea ";
		
		String joinStr = " ";
		
		String whereClause = " where 1=1 ";
		
		if(search.getLevels() != null) {
			whereClause += " and iea.seedLevel.level in (:levels)";
		}
		if(search.getFarmId() != null) {
			whereClause += " and iea.farm.id = :farmId ";
		}
		if(search.getAnimalParentId() != null) {
			whereClause += " and iea.animal.parent.id = :animalParentId ";
		}
		if(search.getGenders() != null) {
			whereClause += " and iea.gender in (:genders) ";
		}
		if(search.getProductTargetCode() !=  null) {
			whereClause += " and iea.farm.id not in (select fpt.farm.id FROM FarmProductTarget fpt where fpt.productTarget.code = :productTargetCode) ";
		}
		if(search.getIsDateIssueRange() != null && search.getIsDateIssueRange() == true) {
			if(search.getDateIssue() != null) {
				whereClause += " and iea.dateIssue >= :startDate and iea.dateIssue <= :endDate ";
			}
		}else {
			if(search.getDateIssue() != null) {
				whereClause += " and iea.dateIssue <= :startDate";
			}
		}
		if(search.getParentCode() != null && search.getIsEqualParentCode() != null) {
			if(search.getIsEqualParentCode()) {
				whereClause += " and iea.animal.parent is not null and iea.animal.parent.code = :parentCode";
			}else {
				whereClause += " and iea.animal.parent is not null and iea.animal.parent.code != :parentCode";
			}
		}
		
		String queryGroup = " ";
		
		sql += joinStr + whereClause + queryGroup;
		
		Query query = manager.createQuery(sql, AnimalRaisingReportDto.class);
		if(search.getLevels() != null) {
			query.setParameter("levels", search.getLevels());
		}
		if(search.getGenders() != null) {
			query.setParameter("genders", search.getGenders());
		}
		if(search.getFarmId() != null) {
			query.setParameter("farmId", search.getFarmId());
		}
		if(search.getAnimalParentId() != null) {
			query.setParameter("animalParentId", search.getAnimalParentId());
		}
		if(search.getParentCode() != null && search.getIsEqualParentCode() != null) {
			query.setParameter("parentCode", search.getParentCode());
		}
		if(search.getProductTargetCode() !=  null) {
			query.setParameter("productTargetCode", search.getProductTargetCode());
		}
		if(search.getIsDateIssueRange() != null && search.getIsDateIssueRange() == true) {
			if(search.getDateIssue() != null) {
				query.setParameter("startDate", WLDateTimeUtil.getStartOfDay(search.getDateIssue()));
				query.setParameter("endDate", WLDateTimeUtil.getStartOfDay(search.getDateIssue()));
			}
		}else {
			if(search.getDateIssue() != null) {
				query.setParameter("startDate", WLDateTimeUtil.getStartOfDay(search.getDateIssue()));
			}
		}
		return query.getResultList();
	}
	@Override
	public List<AnimalRaisingReportDto> getFluctuationHerd(AnimalRaisingSearchDto search) {
		// Tìm thông tin động vật trừ gấu
//		search.setParentCode(FMSConstant.BEAR_CODE);
//		search.setIsEqualParentCode(false);
		List<AnimalRaisingReportDto> listResult = new ArrayList<AnimalRaisingReportDto>();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<Date> dates = WLDateTimeUtil.getDatesByYearMonth(search.getYear(), search.getMonth());
		for(Date date: dates) {	
			search.setDateIssue(date);
//			if(simpleDateFormat.format(date).equals("08/05/2020")) {
//				System.out.println(simpleDateFormat.format(date));
//			}
			search.setIsDateIssueRange(null);
			search.setLevels(new ArrayList<Integer>());
			search.getLevels().add(WLConstant.SeedLevel.animalParent.getValue());
			search.getLevels().add(WLConstant.SeedLevel.animalNewBorn.getValue());
			search.getLevels().add(WLConstant.SeedLevel.gilts.getValue());
			
			search.setGenders(new ArrayList<Integer>());
			search.getGenders().add(WLConstant.AnimalGender.Male.getValue());
			search.getGenders().add(WLConstant.AnimalGender.Female.getValue());
			search.getGenders().add(WLConstant.AnimalGender.UnGender.getValue());
			search.setType(null);
			search.setProductTargetCode(WLConstant.ProductTargetCode.GrowUpRaising.getValue());
			List<AnimalRaisingReportDto> list = new ArrayList<AnimalRaisingReportDto>();
			
			list = getAnimalRaisingBySearchDto(search);// List tên động vật /nguồn gốc mục đích, ten khoa hoc
			Map<String, AnimalRaisingReportDto> map = new HashMap<String, AnimalRaisingReportDto>();
			for(AnimalRaisingReportDto dto: list) {
				if(dto != null && dto.getAnimalCode() != null) {
					if(map.containsKey(dto.getAnimalCode())) {
						if(dto.getQuantity() != null) {
							AnimalRaisingReportDto itemValue = map.get(dto.getAnimalCode());
							itemValue.setTotal(0);
							
							// Set list ký hiệu nguồn gốc và đối tượng
							if(itemValue.getOriginals() == null)
							itemValue.setOriginals(new HashSet<AnimalRaisingSymbolNameDto>());
							if(itemValue.getProductTargets() == null)
							itemValue.setProductTargets(new HashSet<AnimalRaisingSymbolNameDto>());
							AnimalRaisingSymbolNameDto symbolOriginal = new AnimalRaisingSymbolNameDto();
							symbolOriginal.setSymbol(dto.getOriginCode());
							symbolOriginal.setName(dto.getOriginName());
							itemValue.getOriginals().add(symbolOriginal);
							AnimalRaisingSymbolNameDto symbolProductTarget = new AnimalRaisingSymbolNameDto();
							symbolProductTarget.setSymbol(dto.getProductTargetCode());
							symbolProductTarget.setName(dto.getProductTargetName());
							itemValue.getProductTargets().add(symbolProductTarget);
						}
					}else {
						AnimalRaisingReportDto itemValue = new AnimalRaisingReportDto();
						// Set list ký hiệu nguồn gốc và đối tượng
						if(itemValue.getOriginals() == null)
						itemValue.setOriginals(new HashSet<AnimalRaisingSymbolNameDto>());
						if(itemValue.getProductTargets() == null)
						itemValue.setProductTargets(new HashSet<AnimalRaisingSymbolNameDto>());
						AnimalRaisingSymbolNameDto symbolOriginal = new AnimalRaisingSymbolNameDto();
						symbolOriginal.setSymbol(dto.getOriginCode());
						symbolOriginal.setName(dto.getOriginName());
						itemValue.getOriginals().add(symbolOriginal);
						AnimalRaisingSymbolNameDto symbolProductTarget = new AnimalRaisingSymbolNameDto();
						symbolProductTarget.setSymbol(dto.getProductTargetCode());
						symbolProductTarget.setName(dto.getProductTargetName());
						itemValue.getProductTargets().add(symbolProductTarget);
						
						// Set thông tin cơ bản
						itemValue.setAnimalName(dto.getAnimalName());
						itemValue.setAnimalScienceName(dto.getAnimalScienceName());
						itemValue.setBatchCode(dto.getBatchCode());
						itemValue.setGender(dto.getGender());
						itemValue.setOriginName(dto.getOriginName());
						itemValue.setProductTargetName(dto.getProductTargetName());
						itemValue.setQuantity(dto.getQuantity());
						itemValue.setSeedLevelId(dto.getSeedLevelId());
						itemValue.setType(dto.getType());
						itemValue.setOriginCode(dto.getOriginCode());
						itemValue.setProductTargetCode(dto.getProductTargetCode());
						itemValue.setTotal(0);
						// Tính toán theo seedLevel
						
						map.put(dto.getAnimalCode(), itemValue);
					}
				}
			}
			
			search.setLevel(WLConstant.SeedLevel.animalParent.getValue());
			search.setGender(WLConstant.AnimalGender.Male.getValue());
			List<AnimalRaisingReportDto> listParentMale = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listParentMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalParentMale() == null) {
						map.get(element.getAnimalCode()).setTotalParentMale(0d);
					}
					if(map.get(element.getAnimalCode()).getTotalParent() == null) {
						map.get(element.getAnimalCode()).setTotalParent(0d);
					}
					map.get(element.getAnimalCode()).setTotalParentMale(map.get(element.getAnimalCode()).getTotalParentMale() + element.getTotal());
					map.get(element.getAnimalCode()).setTotalParent(map.get(element.getAnimalCode()).getTotalParent() + element.getTotal());
				}
			}
			
			search.setLevel(WLConstant.SeedLevel.animalParent.getValue());
			search.setGender(WLConstant.AnimalGender.Female.getValue());
			List<AnimalRaisingReportDto> listParentFeMale = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listParentFeMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalParentFeMale() == null) {
						map.get(element.getAnimalCode()).setTotalParentFeMale(0d);
					}
					if(map.get(element.getAnimalCode()).getTotalParent() == null) {
						map.get(element.getAnimalCode()).setTotalParent(0d);
					}
					map.get(element.getAnimalCode()).setTotalParentFeMale(map.get(element.getAnimalCode()).getTotalParentFeMale() + element.getTotal());
					map.get(element.getAnimalCode()).setTotalParent(map.get(element.getAnimalCode()).getTotalParent() + element.getTotal());
				}
			}
			
			search.setLevel(WLConstant.SeedLevel.gilts.getValue());
			search.setGender(WLConstant.AnimalGender.Male.getValue());
			List<AnimalRaisingReportDto> listGiltsMale = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listGiltsMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalGiltsMale() == null) {
						map.get(element.getAnimalCode()).setTotalGiltsMale(0d);
					}
					if(map.get(element.getAnimalCode()).getTotalParent() == null) {
						map.get(element.getAnimalCode()).setTotalParent(0d);
					}
					map.get(element.getAnimalCode()).setTotalGiltsMale(map.get(element.getAnimalCode()).getTotalGiltsMale() + element.getTotal());
					map.get(element.getAnimalCode()).setTotalParent(map.get(element.getAnimalCode()).getTotalParent() + element.getTotal());
				}
			}
			search.setLevel(WLConstant.SeedLevel.gilts.getValue());
			search.setGender(WLConstant.AnimalGender.Female.getValue());
			List<AnimalRaisingReportDto> listGiltsFeMale = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listGiltsFeMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalGiltsFemale() == null) {
						map.get(element.getAnimalCode()).setTotalGiltsFemale(0d);
					}
					if(map.get(element.getAnimalCode()).getTotalParent() == null) {
						map.get(element.getAnimalCode()).setTotalParent(0d);
					}
					map.get(element.getAnimalCode()).setTotalGiltsFemale(map.get(element.getAnimalCode()).getTotalGiltsFemale() + element.getTotal());
					map.get(element.getAnimalCode()).setTotalParent(map.get(element.getAnimalCode()).getTotalParent() + element.getTotal());
				}
			}
			
			search.setLevel(WLConstant.SeedLevel.animalNewBorn.getValue());
			search.setGender(WLConstant.AnimalGender.Male.getValue());
			search.setYearOld(1);
			search.setIsGranterThan(true);
			List<AnimalRaisingReportDto> listChildMale = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listChildMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalChildMale() == null) {
						map.get(element.getAnimalCode()).setTotalChildMale(0d);
					}
					if(map.get(element.getAnimalCode()).getTotalChild() == null) {
						map.get(element.getAnimalCode()).setTotalChild(0d);
					}
					map.get(element.getAnimalCode()).setTotalChildMale(map.get(element.getAnimalCode()).getTotalChildMale() + element.getTotal());
					map.get(element.getAnimalCode()).setTotalChild(map.get(element.getAnimalCode()).getTotalChild() + element.getTotal());
				}
			}
			
			search.setLevel(WLConstant.SeedLevel.animalNewBorn.getValue());
			search.setGender(WLConstant.AnimalGender.Female.getValue());
			search.setYearOld(1);
			search.setIsGranterThan(true);
			List<AnimalRaisingReportDto> listChildFeMale = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listChildFeMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalChildFemale() == null) {
						map.get(element.getAnimalCode()).setTotalChildFemale(0d);
					}
					if(map.get(element.getAnimalCode()).getTotalChild() == null) {
						map.get(element.getAnimalCode()).setTotalChild(0d);
					}
					map.get(element.getAnimalCode()).setTotalChildFemale(map.get(element.getAnimalCode()).getTotalChildFemale() + element.getTotal());
					map.get(element.getAnimalCode()).setTotalChild(map.get(element.getAnimalCode()).getTotalChild() + element.getTotal());
				}
			}
			
			search.setLevel(WLConstant.SeedLevel.animalNewBorn.getValue());
			search.setGender(WLConstant.AnimalGender.UnGender.getValue());
			search.setYearOld(1);
			search.setIsGranterThan(true);
			List<AnimalRaisingReportDto> listChildUnknown = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listChildUnknown) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalChildUnknown() == null) {
						map.get(element.getAnimalCode()).setTotalChildUnknown(0d);
					}
					if(map.get(element.getAnimalCode()).getTotalChild() == null) {
						map.get(element.getAnimalCode()).setTotalChild(0d);
					}
					map.get(element.getAnimalCode()).setTotalChildUnknown(map.get(element.getAnimalCode()).getTotalChildUnknown() + element.getTotal());
					map.get(element.getAnimalCode()).setTotalChild(map.get(element.getAnimalCode()).getTotalChild() + element.getTotal());
				}
			}
			search.setLevel(WLConstant.SeedLevel.animalNewBorn.getValue());
			search.setGender(null);
			search.setYearOld(1);
			search.setIsGranterThan(false);
			List<AnimalRaisingReportDto> listChildLessThan1YearOld= getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listChildLessThan1YearOld) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalChildLessThan1Year() == null) {
						map.get(element.getAnimalCode()).setTotalChildLessThan1Year(0d);
					}
					map.get(element.getAnimalCode()).setTotalChildLessThan1Year(map.get(element.getAnimalCode()).getTotalChildLessThan1Year() + element.getTotal());
				}
			}
			// reset điều kiện trước đó
			search.setLevel(null);
			search.setYearOld(null);
			search.setIsGranterThan(null);
			search.setIsDateIssueRange(true);
			
			search.setGender(WLConstant.AnimalGender.Male.getValue());
			search.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());;
			List<AnimalRaisingReportDto> listImportMale = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listImportMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalImportFarmMale() == null) {
						map.get(element.getAnimalCode()).setTotalImportFarmMale(0d);
					}
					map.get(element.getAnimalCode()).setTotalImportFarmMale(map.get(element.getAnimalCode()).getTotalImportFarmMale() + element.getTotal());
				}
			}
			
			search.setGender(WLConstant.AnimalGender.Female.getValue());
			search.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());;
			List<AnimalRaisingReportDto> listImportFeMale = getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listImportFeMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalImportFarmFemale() == null) {
						map.get(element.getAnimalCode()).setTotalImportFarmFemale(0d);
					}
					map.get(element.getAnimalCode()).setTotalImportFarmFemale(map.get(element.getAnimalCode()).getTotalImportFarmFemale() + element.getTotal());
				}
			}
			
			search.setGender(WLConstant.AnimalGender.UnGender.getValue());
			search.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());;
			List<AnimalRaisingReportDto> listImportUnknown= getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listImportUnknown) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalImportFarmUnknown() == null) {
						map.get(element.getAnimalCode()).setTotalImportFarmUnknown(0d);
					}
					map.get(element.getAnimalCode()).setTotalImportFarmUnknown(map.get(element.getAnimalCode()).getTotalImportFarmUnknown() + element.getTotal());
				}
			}
			
			search.setGender(WLConstant.AnimalGender.Male.getValue());
			search.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());;
			List<AnimalRaisingReportDto> listExportMale= getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listExportMale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalExportFarmMale() == null) {
						map.get(element.getAnimalCode()).setTotalExportFarmMale(0d);
					}
					map.get(element.getAnimalCode()).setTotalExportFarmMale(map.get(element.getAnimalCode()).getTotalExportFarmMale() + element.getTotal());
				}
			}
			
			search.setGender(WLConstant.AnimalGender.Female.getValue());
			search.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());;
			List<AnimalRaisingReportDto> listExportFemale= getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listExportFemale) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalExportFarmFemale() == null) {
						map.get(element.getAnimalCode()).setTotalExportFarmFemale(0d);
					}
					map.get(element.getAnimalCode()).setTotalExportFarmFemale(map.get(element.getAnimalCode()).getTotalExportFarmFemale() + element.getTotal());
				}
			}
			
			search.setGender(WLConstant.AnimalGender.UnGender.getValue());
			search.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());;
			List<AnimalRaisingReportDto> listExportUnknow= getListTotalAnimalRaising(search);
			for(AnimalRaisingReportDto element: listExportUnknow) {
				if(map.containsKey(element.getAnimalCode())) {
					if(map.get(element.getAnimalCode()).getTotalExportFarmUnknown() == null) {
						map.get(element.getAnimalCode()).setTotalExportFarmUnknown(0d);
					}
					map.get(element.getAnimalCode()).setTotalExportFarmUnknown(map.get(element.getAnimalCode()).getTotalExportFarmUnknown() + element.getTotal());
				}
			}
			AnimalRaisingReportDto itemResult = new AnimalRaisingReportDto(false);
			for(String key: map.keySet()) {
				AnimalRaisingReportDto dto = map.get(key);
				// male
				if(dto.getTotalParentMale() == null) dto.setTotalParentMale(0d);
				if(dto.getTotalGiltsMale() == null) dto.setTotalGiltsMale(0d);
				if(dto.getTotalChildMale() == null) dto.setTotalChildMale(0d);
				if(dto.getTotalImportFarmMale() == null) dto.setTotalImportFarmMale(0d);
				if(dto.getTotalExportFarmMale() == null) dto.setTotalExportFarmMale(0d);
				dto.setTotalMale(dto.getTotalParentMale() + dto.getTotalGiltsMale() + dto.getTotalChildMale() + dto.getTotalImportFarmMale() - dto.getTotalExportFarmMale() );
				// female
				if(dto.getTotalParentFeMale() == null) dto.setTotalParentFeMale(0d);
				if(dto.getTotalGiltsFemale() == null) dto.setTotalGiltsFemale(0d);
				if(dto.getTotalChildFemale() == null) dto.setTotalChildFemale(0d);
				if(dto.getTotalImportFarmFemale() == null) dto.setTotalImportFarmFemale(0d);
				if(dto.getTotalExportFarmFemale() == null) dto.setTotalExportFarmFemale(0d);
				dto.setTotalFeMale(dto.getTotalParentFeMale() + dto.getTotalGiltsFemale() + dto.getTotalChildFemale() + dto.getTotalImportFarmFemale() - dto.getTotalExportFarmFemale() );
				// unknown
				if(dto.getTotalChildLessThan1Year() == null) dto.setTotalChildLessThan1Year(0d);
				if(dto.getTotalChildUnknown() == null) dto.setTotalChildUnknown(0d);
				if(dto.getTotalImportFarmUnknown() == null) dto.setTotalImportFarmUnknown(0d);
				if(dto.getTotalExportFarmUnknown() == null) dto.setTotalExportFarmUnknown(0d);
				dto.setTotalUnknown(dto.getTotalChildUnknown() + dto.getTotalImportFarmUnknown() + dto.getTotalChildLessThan1Year() - dto.getTotalExportFarmUnknown() );
				 
				dto.setTotal(dto.getTotalUnknown() + dto.getTotalFeMale() + dto.getTotalMale());
				
				itemResult.setTotal(itemResult.getTotal() + dto.getTotal());
				itemResult.setTotalMale(itemResult.getTotalMale() + dto.getTotalMale());
				itemResult.setTotalFeMale(itemResult.getTotalFeMale() + dto.getTotalFeMale());
				itemResult.setTotalUnknown(itemResult.getTotalUnknown() + dto.getTotalUnknown());
				itemResult.setTotalParentMale(itemResult.getTotalParentMale() + dto.getTotalParentMale());
				itemResult.setTotalParentFeMale(itemResult.getTotalParentFeMale() + dto.getTotalParentFeMale());
				itemResult.setTotalGiltsMale(itemResult.getTotalGiltsMale() + dto.getTotalGiltsFemale());
				itemResult.setTotalGiltsFemale(itemResult.getTotalGiltsFemale() + dto.getTotalGiltsFemale());
				itemResult.setTotalChildLessThan1Year(itemResult.getTotalChildLessThan1Year() + dto.getTotalChildLessThan1Year());
				itemResult.setTotalChildMale(itemResult.getTotalChildMale() + dto.getTotalChildFemale());
				itemResult.setTotalChildFemale(itemResult.getTotalChildFemale() + dto.getTotalChildFemale());
				itemResult.setTotalChildUnknown(itemResult.getTotalChildUnknown() + dto.getTotalChildUnknown());
				itemResult.setTotalImportFarmMale(itemResult.getTotalImportFarmMale() + dto.getTotalImportFarmMale());
				itemResult.setTotalImportFarmFemale(itemResult.getTotalImportFarmFemale() + dto.getTotalImportFarmFemale());
				itemResult.setTotalImportFarmUnknown(itemResult.getTotalImportFarmUnknown() + dto.getTotalImportFarmUnknown());
				itemResult.setTotalExportFarmMale(itemResult.getTotalExportFarmMale() + dto.getTotalExportFarmMale());
				itemResult.setTotalExportFarmFemale(itemResult.getTotalExportFarmFemale() + dto.getTotalExportFarmFemale());
				itemResult.setTotalExportFarmUnknown(itemResult.getTotalExportFarmUnknown() + dto.getTotalExportFarmUnknown());
			}
			itemResult.setDate(simpleDateFormat.format(date));
			if(itemResult.getTotal() != 0)
			listResult.add(itemResult);
		}
		
		return listResult;
	}
	
	public ImportExportAnimalDto createImportExportAnimalFromFileInput(Long id, ImportExportAnimalDto dto) {
		if (dto != null) {
			//check list individual animal code duplicate
			if(dto.getIndividualAnimals()!= null && dto.getIndividualAnimals().size() > 0) {
				int len = dto.getIndividualAnimals().size();
				for(int index1 = 0; index1 < len-1; index1++ ) {
					for(int index2 = index1+1; index2 < len; index2++) {
						IndividualAnimalDto individualAnimalDto1 = dto.getIndividualAnimals().get(index1);
						IndividualAnimalDto individualAnimalDto2 = dto.getIndividualAnimals().get(index2);
						if(individualAnimalDto1 != null && individualAnimalDto2 != null && individualAnimalDto1.getCode().equals(individualAnimalDto2.getCode())){
							dto.setDupCodeIndividualAnimals(true);
							dto.getIndividualAnimalDupCode().add(individualAnimalDto1.getCode());
							continue;
						}
					}
				}
				if(dto.isDupCodeIndividualAnimals()) {
					return dto;
				}
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			boolean isNew=false;
			double remainQuantity=0;//số lượng còn lại
			ImportExportAnimal old=null;
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			double firstQuantity = 0d;
			ImportExportAnimal importExportAnimal = null;
			ImportExportAnimal importRecord = null;//Phiếu nhập, trong trường hợp xuất đàn
			//Check các điều kiện, nếu không đạt thì không lưu lại
			if(dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue() 
					&&( dto.getFarm()==null 
//					|| dto.getDateImport()==null 
					|| dto.getAnimal()==null 
					|| dto.getProductTarget()==null 
					|| Double.valueOf(dto.getQuantity())==null  
					|| dto.getQuantity()<=0
//					|| Double.valueOf(dto.getDayOld())==null 
//					|| dto.getDayOld()<=0
//					|| Double.valueOf(dto.getLifeCycle())==null 
//					|| dto.getLifeCycle()<=0
					)) {
				return null;
			}
			else if(dto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(dto.getFarm()==null 
						|| dto.getDateExport()==null
						|| Double.valueOf(dto.getQuantity())==null  
						|| dto.getQuantity()<=0) {
					return null;
				}
				if(dto.getImportAnimal()!=null
						&& dto.getImportAnimal().getId()!=null
						&& dto.getImportAnimal().getId()>0L) {
					importRecord = importAnimalRepository.findOne(dto.getImportAnimal().getId());
				}
				if(importRecord==null) {//Nếu không thấy phiếu nhập (trong trường hợp xuất đàn) thì trả về null
					return null;
				}
				else {
					remainQuantity = this.checkRemainQuantity(importRecord.getId());					
				}
			}

			if(id!=null) {
				importExportAnimal = this.importAnimalRepository.findOne(id);
				old=this.importAnimalRepository.findOne(id);;
			}
			else if (dto.getId() != null) {
				importExportAnimal = this.importAnimalRepository.findOne(dto.getId());
				old=this.importAnimalRepository.findOne(dto.getId());
			}
			if(importExportAnimal!= null && importExportAnimal.getQuantity() > 0) {
				firstQuantity = importExportAnimal.getQuantity();
			}
			
			if(importExportAnimal != null) {
				long diffInMillies = Math.abs(currentDate.toDate().getTime() - importExportAnimal.getCreateDate().toDate().getTime());
			    long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    //Nếu quá khoảng thời gian quy định, chỉ tài khoản admin mới được quyền sửa dữ liệu
				if(diff>=WLConstant.TimeEditData && !SecurityUtils.isUserInRole(modifiedUser, WLConstant.ROLE_ADMIN) ) {
					return null;
				}
			}				
			else if (importExportAnimal == null) {
				importExportAnimal = new ImportExportAnimal();
				isNew=true;
				importExportAnimal.setCreateDate(currentDate);
				importExportAnimal.setCreatedBy(currentUserName);
				
				importExportAnimal.setModifiedBy(currentUserName);
				importExportAnimal.setModifyDate(currentDate);
			}
			importExportAnimal.setGender(dto.getGender());
			importExportAnimal.setType(dto.getType());
//			if(dto.getAnimal()!= null && dto.getAnimal().getParent() != null && dto.getAnimal().getParent().getCode().equals(FMSConstant.BEAR_CODE)  && dto.getChipCode() != null) {
//				List<ImportExportAnimal> listAnimalWithChipCode = importAnimalRepository.getListAnimalWithChipCodeAndType(dto.getChipCode(), FMSConstant.ImportExportAnimalType.importAnimal.getValue());
//				if(listAnimalWithChipCode != null && listAnimalWithChipCode.size() > 0) {
//					dto.setChipCode(FMSConstant.DUPLICATE_CHIP_CODE);
//					return dto;
//				}
//			}
			importExportAnimal.setChipCode(dto.getChipCode());
			importExportAnimal.setIsBornAtFarm(dto.getIsBornAtFarm());
			//Nếu số lượng nhập đàn nhiều hơn số lượng còn lại thì trả về null, đã tổng quát cả trường hợp sửa và thêm mới
			if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()
					&& dto.getQuantity()>remainQuantity+importExportAnimal.getQuantity()) {
				return null;
			}
			
			if (dto.getFarm() != null && dto.getFarm().getId() != null) {
				Farm farm = this.farmRepository.findOne(dto.getFarm().getId());							
				importExportAnimal.setFarm(farm);
			}
			Animal animal = null;
			if (dto.getAnimal() != null && dto.getAnimal().getId()!=null) {
				animal = this.animalRepository.findOne(dto.getAnimal().getId());							
				importExportAnimal.setAnimal(animal);
			}
			
			if(dto.getProductTarget()!=null && dto.getProductTarget().getId()!=null) {
				ProductTarget pt = productTargetRepository.findOne(dto.getProductTarget().getId());
				importExportAnimal.setProductTarget(pt);				
			}
			else {
				importExportAnimal.setProductTarget(null);	
			}
			
			if (dto.getSeedLevel() != null) {
				SeedLevel seedLevel = seedLevelRepository.findOne(dto.getSeedLevel().getId());
				importExportAnimal.setSeedLevel(seedLevel);
			} else {
				importExportAnimal.setSeedLevel(null);
			}
			
			
//			if(dto.getOriginal()!=null && dto.getOriginal().getId()!=null) {
//				Original pt = originalRepository.findOne(dto.getOriginal().getId());
//				importAnimal.setOriginal(pt);
//			}
//			else {
//				importAnimal.setOriginal(null);
//			}
			
			//tự động gen mã lô khi nhập đàn (YYMMDD-STT)
//			if(isNew && dto.getType()==1 && dto.getDateImport()!=null){
//				String code=autoGenBathCode(dto.getDateImport());
//				importAnimal.setBatchCode(code);
//			}else if(dto.getType()!=1){
//				importAnimal.setBatchCode(dto.getBatchCode());
//			}			
			// Khoadv42 sửa ngày 08/04/2020
			if(isNew == false && dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				remainQuantity = this.checkRemainQuantity(importExportAnimal.getId());
				if(dto.getQuantity() < (firstQuantity - remainQuantity)) {
					dto.setBatchCode("-5");// Số lượng nhập nhỏ hơn số lượng đã xuất;
					return dto;
				}
				if(dto.getDateImport()!= null && checkValidDateImport(dto.getDateImport(),importExportAnimal.getId())) {
					dto.setBatchCode("-7");// không thể sửa vì ngày lớn hơn ngày export từ dữ liệu đã dùng
					return dto;
				}
			}
			if(isNew && dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				String code=autoGenBathCode(dto.getDateImport());
				importExportAnimal.setBatchCode(code);
			}else if (dto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				importExportAnimal.setBatchCode(importRecord.getBatchCode());
				//cập nhập trường điều chuyển loại khi xuất đàn loại phiếu xuất là điều chuyển loại
				if(dto.getExportType()>0 && dto.getExportType()==WLConstant.ExportAnimalType.exChangeType.getValue()) {
					importExportAnimal.setIsExChange(true);
				}else {
					importExportAnimal.setIsExChange(false);
				}
			}
			if (dto.getDateImport() != null) {
				importExportAnimal.setDateImport(dto.getDateImport());
				importExportAnimal.setDateIssue(importExportAnimal.getDateImport());
			}			
			if(animal != null && animal.getParent() != null && animal.getParent().getCode().equals(WLConstant.BEAR_CODE)) {
				importExportAnimal.setQuantity(1);
			}else
			importExportAnimal.setQuantity(dto.getQuantity());
			importExportAnimal.setAmount(dto.getAmount());
			importExportAnimal.setDayOld(dto.getDayOld());
			importExportAnimal.setLifeCycle(dto.getLifeCycle());
			importExportAnimal.setDescription(dto.getDescription());
			importExportAnimal.setSupplierAdress(dto.getSupplierAdress());
			importExportAnimal.setSupplierName(dto.getSupplierName());
			
			if (dto.getDateExport() != null) {
				importExportAnimal.setDateExport(dto.getDateExport());
				importExportAnimal.setDateIssue(importExportAnimal.getDateExport());
			}	
			if(dto.getProvincial() != null && dto.getProvincial().getId() != null) {
				FmsAdministrativeUnit province = this.fmsAdministrativeUnitRepository.findOne(dto.getProvincial().getId());
				importExportAnimal.setProvince(province);
			}
			importExportAnimal.setVoucherCode(dto.getVoucherCode());
			importExportAnimal.setExportType(dto.getExportType());
			if(dto.getIsBornAtFarm() == null || dto.getIsBornAtFarm() == false) {
				importExportAnimal.setExportReason(dto.getExportReason());
			}else importExportAnimal.setExportReason(null);
			importExportAnimal.setBuyerName(dto.getBuyerName());
			importExportAnimal.setBuyerAdress(dto.getBuyerAdress());
			
			Set<InjectionPlant> injectionPlants = new HashSet<InjectionPlant>();
			if (dto.getInjectionPlants() != null && dto.getInjectionPlants().size() > 0) {
				for (InjectionPlantDto injectionPlantDto : dto.getInjectionPlants()) {
					if (injectionPlantDto != null) {
						InjectionPlant ip = null;
						if (injectionPlantDto.getId() != null) {
							ip = this.injectionPlantRepository.findOne(injectionPlantDto.getId());
						}
						if (ip == null) {
							ip = new InjectionPlant();
							ip.setCreateDate(currentDate);
							ip.setCreatedBy(currentUserName);
						}
						ip.setDrug(injectionPlantDto.getDrug());
						if (injectionPlantDto.getInjectionDate() != null) {
							ip.setInjectionDate(injectionPlantDto.getInjectionDate());
						}
						ip.setInjectionRound(injectionPlantDto.getInjectionRound());
						ip.setImportAnimal(importExportAnimal);
						//Thêm lần tiêm
						if(injectionPlantDto.getInjectionTime()!=null && injectionPlantDto.getInjectionTime().getId()!=null) {
							InjectionTime ijt = injectionTimeRepository.findOne(injectionPlantDto.getInjectionTime().getId());
							ip.setInjectionTime(ijt);
						}
						injectionPlants.add(ip);
					}
				}				
			}			
			if (importExportAnimal.getInjectionPlants() == null) {
				importExportAnimal.setInjectionPlants(injectionPlants);
			} else {
				importExportAnimal.getInjectionPlants().clear();
				importExportAnimal.getInjectionPlants().addAll(injectionPlants);
			}
			
			// Khoadv42 day: 01/06/2020
			Set<IndividualAnimal> individualAnimals = new HashSet<IndividualAnimal>();
			if(dto.getIndividualAnimals() != null && dto.getIndividualAnimals().size() > 0) {
				for(IndividualAnimalDto individualAnimalDto: dto.getIndividualAnimals()) {
					IndividualAnimal individualAnimal = null;
					if(individualAnimalDto != null && individualAnimalDto.getId() != null) {
						individualAnimal = individualAnimalRepository.findOne(individualAnimalDto.getId());;
					}
					if(individualAnimal == null) {
						individualAnimal = new IndividualAnimal();
						individualAnimal.setCreatedBy(currentUserName);
						individualAnimal.setCreateDate(currentDate);
					}
					individualAnimal.setName(individualAnimalDto.getName());
					List<IndividualAnimal> listIndividual= individualAnimalRepository.findListByCode(individualAnimalDto.getCode());
					if(individualAnimal.getId() == null) {
						// TH Thêm mới check code trùng
						if(listIndividual != null && listIndividual.size() > 0) {
							dto.setDupCodeIndividualAnimals(true);
							dto.getIndividualAnimalDupCode().add(individualAnimalDto.getCode());
							continue;
						}
					}else {
						// TH Update check code trùng
						if(listIndividual != null && listIndividual.size() > 0 && listIndividual.get(0) != null && individualAnimalDto.getId() != listIndividual.get(0).getId()) {
							dto.setDupCodeIndividualAnimals(true);
							dto.getIndividualAnimalDupCode().add(individualAnimalDto.getCode());
							continue;
						}
					}
					individualAnimal.setCode(individualAnimalDto.getCode());
					individualAnimal.setBirthDate(individualAnimalDto.getBirthDate());
					individualAnimal.setAnimal(animal);
					individualAnimal.setStatus(individualAnimalDto.getStatus());
					individualAnimal.setGender(individualAnimalDto.getGender());
					individualAnimal.setDayOld(individualAnimalDto.getDayOld());
					if(dto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
						individualAnimal.setImportAnimal(importExportAnimal);
					}else if(dto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
						individualAnimal.setExportAnimal(importExportAnimal);
					}
					individualAnimals.add(individualAnimal);
				}
			}
			if(dto.isDupCodeIndividualAnimals() == true) {
				return dto;
			}
			if(importExportAnimal.getIndividualAnimals() == null) {
				importExportAnimal.setIndividualAnimals(new HashSet<IndividualAnimal>());
			}
			importExportAnimal.getIndividualAnimals().clear();
			importExportAnimal.getIndividualAnimals().addAll(individualAnimals);
			
			
			importExportAnimal.setImportAnimal(importRecord);
			if(importRecord != null) {
				importExportAnimal.setSeedLevel(importRecord.getSeedLevel());
				importExportAnimal.setGender(importRecord.getGender());
			}
			try {
				importExportAnimal = this.importAnimalRepository.save(importExportAnimal);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
//			importExportAnimal = this.importAnimalRepository.save(importExportAnimal);
			
			if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				remainQuantity = this.checkRemainQuantity(importExportAnimal.getId());
				importExportAnimal.setRemainQuantity(remainQuantity);
				importExportAnimal = this.importAnimalRepository.save(importExportAnimal);
			}
			else if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(importExportAnimal.getImportAnimal()!=null) {
					remainQuantity = this.checkRemainQuantity(importExportAnimal.getImportAnimal().getId());
					importExportAnimal.getImportAnimal().setRemainQuantity(remainQuantity);
					this.importAnimalRepository.save(importExportAnimal.getImportAnimal());
				}
				/*Nếu là phiếu xuất điều chuyển loại  thì tạo thêm 1 phiếu nhập
				 * Phiếu nhập mới sẽ giống phiếu nhập ban đầu của phiếu xuất , ngày nhập là ngày của phiếu xuất, đánh dấu chuyển loại
				 * 
				 */
				if(importExportAnimal.getIsExChange()!=null && importExportAnimal.getIsExChange()) {
					//gọi hàm tạo phiếu nhập ở đây
					createUpdateImportExportAnimalExChange(importExportAnimal,dto.getProductTargetChange());
				}
			}
			
			dto.setId(importExportAnimal.getId());
//			if(isNew==false && importExportAnimal!=null && importExportAnimal.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
//				//khi sửa nhập đàn có khả năng thay đổi cơ sở chăn nuôi và vật nuôi => cần thay đổi xuất đàn (nếu có) theo nhập đàn
//				updateExportAnimalWhenUpdateImport(importExportAnimal);				
//			}
////			//cập nhật số lượng còn lại vào phiếu nhập đàn khi tạo phiếu xuất đàn
////			if(dto.getType()==-1 && dto.getImportAnimal()!=null && dto.getImportAnimal().getId()!=null){
////				ImportExportAnimal importAnimalOld=this.importAnimalRepository.findOne(dto.getImportAnimal().getId());
////				if(importAnimalOld!=null){
////					importAnimalOld.setRemainQuantity(remainQuantity);
////					this.importAnimalRepository.save(importAnimalOld);
////				}
////			}
//			//cập nhật tổng tồn của cơ sở chăn nuôi
//			if(importExportAnimal!=null && importExportAnimal.getFarm()!=null && importExportAnimal.getFarm().getId()!=null){
//				farmService.countBalanceNumberByFarm(importExportAnimal.getFarm().getId());
//			}
//			//cập nhật lượng tồn theo hướng sản phẩm và cập nhật lượng tồn theo hướng sản phẩm + vật nuôi		
//			if(importExportAnimal!=null) {
//				if(importExportAnimal.getFarm()!=null && importExportAnimal.getFarm().getId()!=null) {
//					farmService.deleteFarmAnimalProductTargetExistByFarmId(importExportAnimal.getFarm().getId());
//					farmService.deleteFarmProductTargetExistByFarmId(importExportAnimal.getFarm().getId());
//					updateAllFarmAnimalProductTargetExistByFarm(importExportAnimal.getFarm().getId());
//					updateAllFarmProductTargetExistByFarm(importExportAnimal.getFarm().getId());
//				}				
////				updateQuantityFarmProductTarget(importAnimalTemp);
////				updateQuantityFarmAnimalProductTargetExist(importAnimalTemp);
//			}
			return dto;
		}
		return null;
	}

	@Override
	public void saveFarmAnimalList(List<FarmAnimal2017Dto> listInput) {
		List<ImportExportAnimalDto> listDto = new ArrayList<ImportExportAnimalDto>();
		for(FarmAnimal2017Dto farmAnimal2017Dto: listInput) {
			if(farmAnimal2017Dto!= null) {
				System.out.println(farmAnimal2017Dto.getFarmId());
				FarmDto farmDto = null;
				AnimalDto animalDto = null;
				OriginalDto originalDto = null;
				ProductTargetDto productTargetDto = null;
				AnimalTypeDto animaltypeDto = null;
				List<Farm> farms = farmRepository.findBymapCode(farmAnimal2017Dto.getFarmId());
				if(farms != null && farms.size() > 0) {
					farmDto = new FarmDto();
					farmDto.setId(farms.get(0).getId());
				}
				if(farmAnimal2017Dto.getAnimalId()!= null) {
					List<Animal> animals = animalRepository.findByCode(farmAnimal2017Dto.getAnimalId());
					if(animals != null && animals.size() > 0) {
						animalDto = new AnimalDto();
						animalDto.setId(animals.get(0).getId());
					}
				}else {
					System.out.println("farmId"+farmAnimal2017Dto.getFarmId()+" animalId"+farmAnimal2017Dto.getAnimalId()+" animalTypeId"+farmAnimal2017Dto.getAnimalTypeId());
				}
				if(farmAnimal2017Dto.getAnimalTypeId() != null) {
					List<AnimalType> animalTypes = animalTypeRepository.findByCode(farmAnimal2017Dto.getAnimalTypeId());
					animaltypeDto = new AnimalTypeDto();
					if(animalTypes != null && animalTypes.size() > 0) {
						animaltypeDto.setId(animalTypes.get(0).getId());
					}
					animaltypeDto.setCode(farmAnimal2017Dto.getAnimalTypeId());
					animaltypeDto.setName(farmAnimal2017Dto.getAnimalTypeId());
				}
				if(farmAnimal2017Dto.getSource() != null) {
					List<Original> originals = originalRepository.findByCode(farmAnimal2017Dto.getSource());
					originalDto = new OriginalDto();
					if(originals != null && originals.size() > 0) {
						originalDto.setId(originals.get(0).getId());
					}
					originalDto.setCode(farmAnimal2017Dto.getSource());
					originalDto.setName(farmAnimal2017Dto.getSource());
				}
				if(farmAnimal2017Dto.getPurpose() != null) {
					List<ProductTarget> productTargets = productTargetRepository.findByCode(farmAnimal2017Dto.getPurpose());
					productTargetDto = new ProductTargetDto();
					if(productTargets != null && productTargets.size() > 0) {
						productTargetDto.setId(productTargets.get(0).getId());
					}
					productTargetDto.setName(farmAnimal2017Dto.getPurpose());
					productTargetDto.setCode(farmAnimal2017Dto.getPurpose());
				}
				
				if(farmAnimal2017Dto.getMale() != null && farmAnimal2017Dto.getMale() > 0) {
					ImportExportAnimalDto importExportAnimalDto = new ImportExportAnimalDto();
					importExportAnimalDto.setFarm(farmDto);
					importExportAnimalDto.setAnimal(animalDto);
					importExportAnimalDto.setOriginal(originalDto);
					importExportAnimalDto.setProductTarget(productTargetDto);
					importExportAnimalDto.setAnimalType(animaltypeDto);
					importExportAnimalDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
					importExportAnimalDto.setQuantity((double)farmAnimal2017Dto.getMale());
					importExportAnimalDto.setDateImport(new Date());
					listDto.add(importExportAnimalDto);
				}
				if(farmAnimal2017Dto.getFeMale() != null && farmAnimal2017Dto.getFeMale() > 0) {
					ImportExportAnimalDto importExportAnimalDto = new ImportExportAnimalDto();
					importExportAnimalDto.setFarm(farmDto);
					importExportAnimalDto.setAnimal(animalDto);
					importExportAnimalDto.setOriginal(originalDto);
					importExportAnimalDto.setProductTarget(productTargetDto);
					importExportAnimalDto.setAnimalType(animaltypeDto);
					importExportAnimalDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
					importExportAnimalDto.setQuantity((double)farmAnimal2017Dto.getFeMale());
					importExportAnimalDto.setDateImport(new Date());
					listDto.add(importExportAnimalDto);
				}
				if(farmAnimal2017Dto.getGenderUn() != null && farmAnimal2017Dto.getGenderUn() > 0) {
					ImportExportAnimalDto importExportAnimalDto = new ImportExportAnimalDto();
					importExportAnimalDto.setFarm(farmDto);
					importExportAnimalDto.setAnimal(animalDto);
					importExportAnimalDto.setOriginal(originalDto);
					importExportAnimalDto.setProductTarget(productTargetDto);
					importExportAnimalDto.setAnimalType(animaltypeDto);
					importExportAnimalDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
					importExportAnimalDto.setQuantity((double)farmAnimal2017Dto.getGenderUn());
					importExportAnimalDto.setDateImport(new Date());
					listDto.add(importExportAnimalDto);
				}
			}
		}
		int count =0;
		for(int index = 0;index < listDto.size();index++) {
			ImportExportAnimalDto importExportAnimalDto = listDto.get(index);
			if(importExportAnimalDto != null) {
				if(importExportAnimalDto.getFarm() != null && importExportAnimalDto.getAnimal() != null)count++;
				this.createImportExportAnimalFromFileInput(null, importExportAnimalDto);
			}
		}
		System.out.println("number dto have animal and farm is: "+count);
	}
	public ImportExportAnimalDto createImportBearFromFileInput(Long id, ImportExportAnimalDto dto) {
		if (dto != null) {
			//check list individual animal code duplicate
			if(dto.getIndividualAnimals()!= null && dto.getIndividualAnimals().size() > 0) {
				int len = dto.getIndividualAnimals().size();
				for(int index1 = 0; index1 < len-1; index1++ ) {
					for(int index2 = index1+1; index2 < len; index2++) {
						IndividualAnimalDto individualAnimalDto1 = dto.getIndividualAnimals().get(index1);
						IndividualAnimalDto individualAnimalDto2 = dto.getIndividualAnimals().get(index2);
						if(individualAnimalDto1 != null && individualAnimalDto2 != null && individualAnimalDto1.getCode().equals(individualAnimalDto2.getCode())){
							dto.setDupCodeIndividualAnimals(true);
							dto.getIndividualAnimalDupCode().add(individualAnimalDto1.getCode());
							continue;
						}
					}
				}
				if(dto.isDupCodeIndividualAnimals()) {
					return dto;
				}
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			boolean isNew=false;
			double remainQuantity=0;//số lượng còn lại
			ImportExportAnimal old=null;
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			double firstQuantity = 0d;
			ImportExportAnimal importExportAnimal = null;
			ImportExportAnimal importRecord = null;//Phiếu nhập, trong trường hợp xuất đàn
			//Check các điều kiện, nếu không đạt thì không lưu lại
			if(dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue() 
					&&( dto.getFarm()==null 
//					|| dto.getDateImport()==null 
//					|| dto.getAnimal()==null 
//					|| dto.getProductTarget()==null 
					|| Double.valueOf(dto.getQuantity())==null  
					|| dto.getQuantity()<=0
//					|| Double.valueOf(dto.getDayOld())==null 
//					|| dto.getDayOld()<=0
//					|| Double.valueOf(dto.getLifeCycle())==null 
//					|| dto.getLifeCycle()<=0
					)) {
				return null;
			}
			else if(dto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(dto.getFarm()==null 
						|| dto.getDateExport()==null
						|| Double.valueOf(dto.getQuantity())==null  
						|| dto.getQuantity()<=0) {
					return null;
				}
				if(dto.getImportAnimal()!=null
						&& dto.getImportAnimal().getId()!=null
						&& dto.getImportAnimal().getId()>0L) {
					importRecord = importAnimalRepository.findOne(dto.getImportAnimal().getId());
				}
				if(importRecord==null) {//Nếu không thấy phiếu nhập (trong trường hợp xuất đàn) thì trả về null
					return null;
				}
				else {
					remainQuantity = this.checkRemainQuantity(importRecord.getId());					
				}
			}

			if(id!=null) {
				importExportAnimal = this.importAnimalRepository.findOne(id);
				old=this.importAnimalRepository.findOne(id);;
			}
			else if (dto.getId() != null) {
				importExportAnimal = this.importAnimalRepository.findOne(dto.getId());
				old=this.importAnimalRepository.findOne(dto.getId());
			}
			if(importExportAnimal!= null && importExportAnimal.getQuantity() > 0) {
				firstQuantity = importExportAnimal.getQuantity();
			}
			
			if(importExportAnimal != null) {
				long diffInMillies = Math.abs(currentDate.toDate().getTime() - importExportAnimal.getCreateDate().toDate().getTime());
			    long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    //Nếu quá khoảng thời gian quy định, chỉ tài khoản admin mới được quyền sửa dữ liệu
				if(diff>=WLConstant.TimeEditData && !SecurityUtils.isUserInRole(modifiedUser, WLConstant.ROLE_ADMIN) ) {
					return null;
				}
			}				
			else if (importExportAnimal == null) {
				importExportAnimal = new ImportExportAnimal();
				isNew=true;
				importExportAnimal.setCreateDate(currentDate);
				importExportAnimal.setCreatedBy(currentUserName);
				
				importExportAnimal.setModifiedBy(currentUserName);
				importExportAnimal.setModifyDate(currentDate);
			}
			importExportAnimal.setGender(dto.getGender());
			importExportAnimal.setType(dto.getType());
			importExportAnimal.setIsBornAtFarm(dto.getIsBornAtFarm());
			//Nếu số lượng nhập đàn nhiều hơn số lượng còn lại thì trả về null, đã tổng quát cả trường hợp sửa và thêm mới
			if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()
					&& dto.getQuantity()>remainQuantity+importExportAnimal.getQuantity()) {
				return null;
			}
			
			if (dto.getFarm() != null && dto.getFarm().getId() != null) {
				Farm farm = this.farmRepository.findOne(dto.getFarm().getId());							
				importExportAnimal.setFarm(farm);
			}
			Animal animal = null;
			if (dto.getAnimal() != null && dto.getAnimal().getId()!=null) {
				animal = this.animalRepository.findOne(dto.getAnimal().getId());							
				importExportAnimal.setAnimal(animal);
			}
			
			if(dto.getProductTarget()!=null && dto.getProductTarget().getId()!=null) {
				ProductTarget pt = productTargetRepository.findOne(dto.getProductTarget().getId());
				importExportAnimal.setProductTarget(pt);				
			}
			else {
				importExportAnimal.setProductTarget(null);	
			}
			
			if (dto.getSeedLevel() != null) {
				SeedLevel seedLevel = seedLevelRepository.findOne(dto.getSeedLevel().getId());
				importExportAnimal.setSeedLevel(seedLevel);
			} else {
				importExportAnimal.setSeedLevel(null);
			}
			
			if(isNew == false && dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				remainQuantity = this.checkRemainQuantity(importExportAnimal.getId());
				if(dto.getQuantity() < (firstQuantity - remainQuantity)) {
					dto.setBatchCode("-5");// Số lượng nhập nhỏ hơn số lượng đã xuất;
					return dto;
				}
				if(dto.getDateImport()!= null && checkValidDateImport(dto.getDateImport(),importExportAnimal.getId())) {
					dto.setBatchCode("-7");// không thể sửa vì ngày lớn hơn ngày export từ dữ liệu đã dùng
					return dto;
				}
			}
			if(isNew && dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				String code=autoGenBathCode(dto.getDateImport());
				importExportAnimal.setBatchCode(code);
			}else if (dto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				importExportAnimal.setBatchCode(importRecord.getBatchCode());
				//cập nhập trường điều chuyển loại khi xuất đàn loại phiếu xuất là điều chuyển loại
				if(dto.getExportType()>0 && dto.getExportType()==WLConstant.ExportAnimalType.exChangeType.getValue()) {
					importExportAnimal.setIsExChange(true);
				}else {
					importExportAnimal.setIsExChange(false);
				}
			}
			if (dto.getDateImport() != null) {
				importExportAnimal.setDateImport(dto.getDateImport());
				importExportAnimal.setDateIssue(importExportAnimal.getDateImport());
			}			
			if(animal != null && animal.getParent() != null && animal.getParent().getCode().equals(WLConstant.BEAR_CODE)) {
				importExportAnimal.setQuantity(1);
			}else
			importExportAnimal.setQuantity(dto.getQuantity());
			importExportAnimal.setAmount(dto.getAmount());
			importExportAnimal.setDayOld(dto.getDayOld());
			importExportAnimal.setLifeCycle(dto.getLifeCycle());
			importExportAnimal.setDescription(dto.getDescription());
			importExportAnimal.setSupplierAdress(dto.getSupplierAdress());
			importExportAnimal.setSupplierName(dto.getSupplierName());
			
			if (dto.getDateExport() != null) {
				importExportAnimal.setDateExport(dto.getDateExport());
				importExportAnimal.setDateIssue(importExportAnimal.getDateExport());
			}	
			if(dto.getProvincial() != null && dto.getProvincial().getId() != null) {
				FmsAdministrativeUnit province = this.fmsAdministrativeUnitRepository.findOne(dto.getProvincial().getId());
				importExportAnimal.setProvince(province);
			}
			importExportAnimal.setVoucherCode(dto.getVoucherCode());
			importExportAnimal.setExportType(dto.getExportType());
			if(dto.getIsBornAtFarm() == null || dto.getIsBornAtFarm() == false) {
				importExportAnimal.setExportReason(dto.getExportReason());
			}else importExportAnimal.setExportReason(null);
			
			Set<IndividualAnimal> individualAnimals = new HashSet<IndividualAnimal>();
			if(dto.getIndividualAnimals() != null && dto.getIndividualAnimals().size() > 0) {
				for(IndividualAnimalDto individualAnimalDto: dto.getIndividualAnimals()) {
					IndividualAnimal individualAnimal = null;
					if(individualAnimalDto != null && individualAnimalDto.getId() != null) {
						individualAnimal = individualAnimalRepository.findOne(individualAnimalDto.getId());;
					}
					if(individualAnimal == null) {
						individualAnimal = new IndividualAnimal();
						individualAnimal.setCreatedBy(currentUserName);
						individualAnimal.setCreateDate(currentDate);
					}
					individualAnimal.setName(individualAnimalDto.getName());
					List<IndividualAnimal> listIndividual= individualAnimalRepository.findListByCode(individualAnimalDto.getCode());
					if(individualAnimal.getId() == null) {
						// TH Thêm mới check code trùng
						if(listIndividual != null && listIndividual.size() > 0) {
							dto.setDupCodeIndividualAnimals(true);
							dto.getIndividualAnimalDupCode().add(individualAnimalDto.getCode());
							continue;
						}
					}else {
						// TH Update check code trùng
						if(listIndividual != null && listIndividual.size() > 0 && listIndividual.get(0) != null && individualAnimalDto.getId() != listIndividual.get(0).getId()) {
							dto.setDupCodeIndividualAnimals(true);
							dto.getIndividualAnimalDupCode().add(individualAnimalDto.getCode());
							continue;
						}
					}
					individualAnimal.setCode(individualAnimalDto.getCode());
					if(individualAnimalDto.getBirthDate()!=null) {
						individualAnimal.setBirthDate(individualAnimalDto.getBirthDate());
					}else {
						individualAnimal.setBirthDate(new Date());
					}
					individualAnimal.setAnimal(animal);
					individualAnimal.setStatus(individualAnimalDto.getStatus());
					individualAnimal.setGender(individualAnimalDto.getGender());
					individualAnimal.setDayOld(null);
					if(dto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
						individualAnimal.setImportAnimal(importExportAnimal);
					}else if(dto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
						individualAnimal.setExportAnimal(importExportAnimal);
					}
					individualAnimals.add(individualAnimal);
				}
			}
			if(dto.isDupCodeIndividualAnimals() == true) {
				return dto;
			}
			if(importExportAnimal.getIndividualAnimals() == null) {
				importExportAnimal.setIndividualAnimals(new HashSet<IndividualAnimal>());
			}
			importExportAnimal.getIndividualAnimals().clear();
			importExportAnimal.getIndividualAnimals().addAll(individualAnimals);
			
			importExportAnimal.setRemainQuantity(dto.getQuantity());
			importExportAnimal.setImportAnimal(importRecord);
			if(importRecord != null) {
				importExportAnimal.setSeedLevel(importRecord.getSeedLevel());
				importExportAnimal.setGender(importRecord.getGender());
			}
			importExportAnimal = this.importAnimalRepository.save(importExportAnimal);
			
//			if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
//				remainQuantity = this.checkRemainQuantity(importExportAnimal.getId());
//				importExportAnimal.setRemainQuantity(remainQuantity);
//				importExportAnimal = this.importAnimalRepository.save(importExportAnimal);
//			}
			
			dto.setId(importExportAnimal.getId());
			return dto;
		}
		return null;
	}
	@Override
	public void saveImportAnimalBear(List<FarmAnimal2017Dto> listInput) {
		List<ImportExportAnimalDto> listDto = new ArrayList<ImportExportAnimalDto>();
		for(FarmAnimal2017Dto farmAnimal2017Dto: listInput) {
			if(farmAnimal2017Dto!= null) {
				FarmDto farmDto = null;
				AnimalDto animalDto = null;
				OriginalDto originalDto = null;
				ProductTargetDto productTargetDto = null;
				AnimalTypeDto animaltypeDto = null;
				ImportExportAnimalDto importExportAnimalDto = new ImportExportAnimalDto();
				List<Farm> farms = farmRepository.findBymapCode(farmAnimal2017Dto.getFarmId());
				if(farms != null && farms.size() > 0) {
					farmDto = new FarmDto();
					farmDto.setId(farms.get(0).getId());
					importExportAnimalDto.setFarm(farmDto);
				}
				if(farmAnimal2017Dto.getAnimalTypeId() != null) {
					List<Animal> animals = animalRepository.findByAnimalName(farmAnimal2017Dto.getAnimalTypeId());
					animalDto = new AnimalDto();
					if(animals != null && animals.size() > 0) {
						animalDto.setId(animals.get(0).getId());
					}
					importExportAnimalDto.setAnimal(animalDto);
				}
				
				List<IndividualAnimalDto> individualAnimalDtos = new ArrayList<IndividualAnimalDto>();
				String[] individualCodeChips = null;
				int splitIndex = 0;
				if(farmAnimal2017Dto.getTagId() != null) {
					individualCodeChips = farmAnimal2017Dto.getTagId().split(",");
				}
				if(farmAnimal2017Dto.getMale() != null && farmAnimal2017Dto.getMale() > 0) {
					for(int index = 0;index < farmAnimal2017Dto.getMale().intValue();index++) {
						IndividualAnimalDto individualAnimalDto = new IndividualAnimalDto();
						if(individualCodeChips != null && individualCodeChips.length > splitIndex) {
							individualAnimalDto.setCode(individualCodeChips[splitIndex++]);
						}else {
							break;
						}
						individualAnimalDto.setGender(WLConstant.AnimalGender.Male.getValue());
						individualAnimalDtos.add(individualAnimalDto);
					}
				}
				if(farmAnimal2017Dto.getFeMale() != null && farmAnimal2017Dto.getFeMale() > 0) {
					for(int index = 0;index < farmAnimal2017Dto.getFeMale().intValue();index++) {
						IndividualAnimalDto individualAnimalDto = new IndividualAnimalDto();
						if(individualCodeChips != null && individualCodeChips.length > splitIndex) {
							individualAnimalDto.setCode(individualCodeChips[splitIndex++]);
						}else {
							break;
						}
						individualAnimalDto.setGender(WLConstant.AnimalGender.Female.getValue());
						individualAnimalDtos.add(individualAnimalDto);
					}
				}
				if(farmAnimal2017Dto.getGenderUn() != null && farmAnimal2017Dto.getGenderUn() > 0) {
					for(int index = 0;index < farmAnimal2017Dto.getGenderUn().intValue();index++) {
						IndividualAnimalDto individualAnimalDto = new IndividualAnimalDto();
						if(individualCodeChips != null && individualCodeChips.length > splitIndex) {
							individualAnimalDto.setCode(individualCodeChips[splitIndex++].trim());
						}else {
							break;
						}
						individualAnimalDto.setGender(WLConstant.AnimalGender.UnGender.getValue());
						individualAnimalDtos.add(individualAnimalDto);
					}
				}
				importExportAnimalDto.setIndividualAnimals(individualAnimalDtos);
				importExportAnimalDto.setDateImport(farmAnimal2017Dto.getDateCollect());
				importExportAnimalDto.setDescription(farmAnimal2017Dto.getNote());
				importExportAnimalDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
				importExportAnimalDto.setQuantity(splitIndex);
				listDto.add(importExportAnimalDto);
			}
		}
		int count = 0;
		for(int index = 0;index < listDto.size();index++) {
			ImportExportAnimalDto importExportAnimalDto = listDto.get(index);
			if(importExportAnimalDto != null) {
				if(importExportAnimalDto.getFarm() != null && importExportAnimalDto.getQuantity() > 0)count++;
				this.createImportBearFromFileInput(null, importExportAnimalDto);
			}
		}
		System.out.println("count "+ count);
	}
	@Override
	public List<Report16FormDto> getListReport16FormDto(Integer month,Integer year,Long farmId) {		
		List<ImportExportAnimal> listImportExport = importAnimalRepository.findByFarm(farmId);
		Hashtable<Long, Set<Date>> hashAnimalDate = new Hashtable<Long, Set<Date>>();
		if(listImportExport!=null && listImportExport.size()>0) {
			for (ImportExportAnimal importExportAnimal : listImportExport) {
				if(hashAnimalDate.get(importExportAnimal.getAnimal().getId())!=null) {
					hashAnimalDate.get(importExportAnimal.getAnimal().getId()).add(WLDateTimeUtil.getPrevDay(importExportAnimal.getDateIssue()));
				}
				else {
					Set<Date> listDate = new HashSet<Date>();
					listDate.add(WLDateTimeUtil.getPrevDay(importExportAnimal.getDateIssue()));
					hashAnimalDate.put(importExportAnimal.getAnimal().getId(), listDate);
				}
			}
		}
		List<Report16FormDto> ret = new ArrayList<Report16FormDto>();		
		if(hashAnimalDate.size()>0) {
			for (Long key : hashAnimalDate.keySet()) {
				if(key==null) {
					System.out.print("");
				}
				Set<Date> setDate = hashAnimalDate.get(key);
				for (Date date : setDate) {
					List<Report16FormDto> subRet= this.getReport16FormDto(null,date,farmId,key);
					ret.addAll(subRet);
				}				
			}
		}
		Collections.sort(ret, new Comparator<Report16FormDto>() {
			@Override
			public int compare(Report16FormDto o1, Report16FormDto o2) {
				// TODO Auto-generated method stub
				int c= 0;
				try {
					c = o1.getAnimalName().compareTo(o2.getAnimalName());
					if(c==0) {
						c = o1.getDateReport().compareTo(o2.getDateReport());
					}
				} catch (Exception e) {
					c= 0;
				}												    					       
			    return c;								
			}
		});
		return ret;
	}
	
	@Override
	public List<Report16FormDto> getReport16FormDto(Date fromDate,Date toDate,Long farmId,Long animalId) {
		
		ReportParamDto paramDto = new ReportParamDto();
		paramDto.setFromDate(fromDate);
		paramDto.setToDate(toDate);
		paramDto.setFarmId(farmId);
		paramDto.setAnimalId(animalId);
		paramDto.setGroupByItems(new ArrayList<String>());
		paramDto.getGroupByItems().add(WLConstant.FunctionAndGroupByItem.animalReportDataType.getValue());
		paramDto.getGroupByItems().add(WLConstant.FunctionAndGroupByItem.animal.getValue());
		
		List<InventoryReportDto> listInventory = this.getSumQuantity(paramDto);
		
		List<Report16FormDto> ret = new ArrayList<Report16FormDto>();
		Report16FormDto retDto = new Report16FormDto();
		for (InventoryReportDto inventoryReportDto : listInventory) {			
			if(inventoryReportDto.getAnimalId().equals(animalId)) {
				retDto.setAnimalId(animalId);
				retDto.setAnimalName(inventoryReportDto.getAnimalName());
				retDto.setScienceName(inventoryReportDto.getScienceName());
				retDto.setDateReport(WLDateTimeUtil.getNextDay(toDate));
				if(inventoryReportDto.getAnimalReportDataType()==WLConstant.AnimalReportDataType.animalParent.getValue()) {
					retDto.setTotalParent(retDto.getTotalParent()+ Math.round(inventoryReportDto.getQuantity()));
					retDto.setParentMale(retDto.getParentMale()+Math.round(inventoryReportDto.getMale()));
					retDto.setParentFeMale(retDto.getParentFeMale()+Math.round(inventoryReportDto.getFemale()));
					retDto.setParentUnGen(retDto.getParentUnGen()+Math.round(inventoryReportDto.getUnGender()));
				}
				else if(inventoryReportDto.getAnimalReportDataType()==WLConstant.AnimalReportDataType.gilts.getValue()) {
					retDto.setTotalGilts(retDto.getTotalGilts()+ Math.round(inventoryReportDto.getQuantity()));
					retDto.setGiltsMale(retDto.getGiltsMale()+Math.round(inventoryReportDto.getMale()));
					retDto.setGiltsFeMale(retDto.getGiltsFeMale()+Math.round(inventoryReportDto.getFemale()));
					retDto.setGiltsUnGen(retDto.getGiltsUnGen()+Math.round(inventoryReportDto.getUnGender()));
				}
				else if(inventoryReportDto.getAnimalReportDataType()==WLConstant.AnimalReportDataType.childOver1YearOld.getValue()) {
					retDto.setTotalOver1YO(retDto.getTotalOver1YO()+ Math.round(inventoryReportDto.getQuantity()));
					retDto.setOver1YOMale(retDto.getOver1YOMale()+Math.round(inventoryReportDto.getMale()));
					retDto.setOver1YOFeMale(retDto.getOver1YOFeMale()+Math.round(inventoryReportDto.getFemale()));
					retDto.setOver1YOUnGen(retDto.getOver1YOUnGen()+Math.round(inventoryReportDto.getUnGender()));
				}
				else if(inventoryReportDto.getAnimalReportDataType()==WLConstant.AnimalReportDataType.childUnder1YearOld.getValue()) {
					retDto.setTotalUnder1YO(retDto.getTotalUnder1YO()+ Math.round(inventoryReportDto.getQuantity()));
					retDto.setUnder1YOMale(retDto.getUnder1YOMale()+Math.round(inventoryReportDto.getMale()));
					retDto.setUnder1YOFeMale(retDto.getUnder1YOFeMale()+Math.round(inventoryReportDto.getFemale()));
					retDto.setUnder1YOUnGen(retDto.getUnder1YOUnGen()+Math.round(inventoryReportDto.getUnGender()));
				}
			}			
		}
		ret.add(retDto);
		List<ImportExportAnimal> listImportExport = importAnimalRepository.findByFarmAndAnimal(farmId,animalId);
		if(listImportExport!=null && listImportExport.size()>0) {
			for (ImportExportAnimal importExportAnimal : listImportExport) {
				for (Report16FormDto rep : ret) {
					if(rep.getAnimalId()==null) {
						retDto.setAnimalId(animalId);
						retDto.setAnimalName(importExportAnimal.getAnimal().getName());
						retDto.setScienceName(importExportAnimal.getAnimal().getScienceName());
						retDto.setDateReport(WLDateTimeUtil.getNextDay(toDate));
					}
					if(rep.getAnimalId().equals(importExportAnimal.getAnimal().getId()) 
							&& WLDateTimeUtil.getStartOfDay(rep.getDateReport()).compareTo(importExportAnimal.getDateIssue())<=0
							&& WLDateTimeUtil.getEndOfDay(rep.getDateReport()).compareTo(importExportAnimal.getDateIssue())>=0
							) {
						if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
							rep.setImportMale(rep.getImportMale()+importExportAnimal.getMale());
							rep.setImportFeMale(rep.getImportFeMale()+importExportAnimal.getFemale());
							rep.setImportUnGen(rep.getImportUnGen()+importExportAnimal.getUnGender());
						}
						else if(importExportAnimal.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
							rep.setExportMale(rep.getExportMale()+importExportAnimal.getMale());
							rep.setExportFeMale(rep.getExportFeMale()+importExportAnimal.getFemale());
							rep.setExportUnGen(rep.getExportUnGen()+importExportAnimal.getUnGender());
						}
					}
				}
			}
		}		
		return ret;
	}
	
	@Override
	public List<AnimalReportDataDto> getAllLessThanDate(AnimalReportDataSearchDto searchDto) {
		if(searchDto == null) return null;
		String sql = "select new com.globits.wl.dto.AnimalReportDataDto( SUM(iad.type * iad.male) as male, SUM(iad.type * iad.female)as female, SUM(iad.type * iad.unGender) as unGender, iad.animal as animal, iad.farm ) FROM ImportExportAnimal iad ";
		String whereClause = " where 1=1 ";
		String groupBy = " group by iad.farm.id, iad.animal.id";
		
		Date lastDayOfMonth = WLDateTimeUtil.getLastDayOfMonth(searchDto.getMonth(), searchDto.getYear());
		Boolean useLastDayOfMonth = true;
		
		if(useLastDayOfMonth) {
			whereClause += " and iad.dateIssue <= :lastDayOfMonth ";
		}
		if(searchDto.getFarmId() != null) {
			whereClause += " and iad.farm.id = :farmId";
		}
		if(searchDto.getAnimalId() != null) {
			whereClause += " and iad.animal.id = :animalId";
		}
		Query query = manager.createQuery(sql + whereClause + groupBy, AnimalReportDataDto.class);
		
		if(useLastDayOfMonth) {
			query.setParameter("lastDayOfMonth", lastDayOfMonth);
		}
		if(searchDto.getFarmId() != null) {
			query.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getAnimalId() != null) {
			query.setParameter("animalId", searchDto.getAnimalId());
		}
		
		return query.getResultList();
	}
	/** farmId , toDate = dateExport , AnimalId, animalTypeId */
	@Override
	public InventoryReportDto getInventoryReportRemainQuantity(ReportParamDto paramDto) {
//		ReportParamDto paramDto = new ReportParamDto();
//		paramDto.setToDate(toDate);
//		paramDto.setFarmId(farmId);
//		paramDto.setAnimalId(animalId);
		paramDto.setGroupByItems(new ArrayList<String>());
		paramDto.getGroupByItems().add(WLConstant.FunctionAndGroupByItem.animalReportDataType.getValue());
		paramDto.getGroupByItems().add(WLConstant.FunctionAndGroupByItem.animal.getValue());
		InventoryReportDto result = new InventoryReportDto();
		result.setAmount(0);
		result.setQuantity(0);
		result.setMale(0);
		result.setFemale(0);
		result.setUnGender(0);
		List<InventoryReportDto> listInventory = this.getSumQuantity(paramDto);
		if(listInventory != null && listInventory.size() > 0) {
			for(InventoryReportDto inventoryReportDto:listInventory) {
				if(inventoryReportDto != null) {
					result.setQuantity(result.getQuantity() + inventoryReportDto.getQuantity());
					result.setMale(result.getMale() + (inventoryReportDto.getMale() != null?inventoryReportDto.getMale():0) );
					result.setFemale(result.getFemale() + (inventoryReportDto.getFemale() != null?inventoryReportDto.getFemale():0) );
					result.setUnGender(result.getUnGender() + (inventoryReportDto.getUnGender() != null?inventoryReportDto.getUnGender():0) );
				}
			}
		}
		
		return result;
	}
}
