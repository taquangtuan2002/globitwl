package com.globits.wl.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.domain.ExportEgg;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.dto.ExportEggDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ExportEggSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.functiondto.WeekOfYearDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.repository.ExportEggRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.service.ExportEggService;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ExportEggServiceImpl extends GenericServiceImpl<ExportEgg, Long>
		implements ExportEggService {

	@Autowired
	private ExportEggRepository exportEggRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	@Autowired
	private FarmService farmService;
	@Autowired
	private ImportExportAnimalRepository importExportAnimalRepository;
	@Override
	public Page<ExportEggDto> getPageDto(int pageIndex, int pageSize) {

		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
		return this.exportEggRepository.getPageDto(pageable);
	}

	@Override
	public List<ExportEggDto> getAllDto() {
		return exportEggRepository.getAllDto();
	}

	@Override
	public ExportEggDto getExportEggById(Long id) {
		if (id != null) {
			ExportEgg ExportEgg = null;
			ExportEgg = this.exportEggRepository.findOne(id);
			if (ExportEgg != null) {
				return new ExportEggDto(ExportEgg);
			}
		}
		return null;
	}

	@Override
	public ExportEggDto createExportEgg(ExportEggDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			if(dto.getQuantity() == null || dto.getQuantity().doubleValue() == 0d) {
				return null;
			}
			Boolean isNew = false;
			Double remainQuantity = 0D;
			ExportEgg importRecord = null;
			double firstQuantity = 0d;
			 if(dto.getType()==WLConstant.ImportExportEggType.exportEgg.getValue()) {
				if(dto.getFarm()==null 
						|| dto.getDateExport()==null
						|| dto.getQuantity()<=0) {
					return null;
				}
				if(dto.getImportEgg()!=null
						&& dto.getImportEgg().getId()!=null
						&& dto.getImportEgg().getId()>0L) {
					importRecord = exportEggRepository.findOne(dto.getImportEgg().getId());
				}
				if(importRecord==null) {//Nếu không thấy phiếu nhập (trong trường hợp xuất đàn) thì trả về null
					return null;
				}
				else {
					remainQuantity = this.checkRemainQuantity(importRecord.getId());					
				}
			}
			ExportEgg exportEgg = null;
			if (dto.getId() != null) {
				exportEgg = this.exportEggRepository.findOne(dto.getId());
			}

			if (exportEgg == null) {
				exportEgg = new ExportEgg();
				isNew = true;
				exportEgg.setCreateDate(currentDate);
				exportEgg.setCreatedBy(currentUserName);
			}else {
				exportEgg.setModifiedBy(currentUserName);
				exportEgg.setModifyDate(currentDate);
				firstQuantity = exportEgg.getQuantity();
			}
			if(dto.getType() == null || (dto.getType() != null && dto.getType()!= WLConstant.ImportExportEggType.exportEgg.getValue() && dto.getType()!= WLConstant.ImportExportEggType.importEgg.getValue() )) {
				return null;
			}
			exportEgg.setType(dto.getType());
			
			if(dto.getCode()!=null) {
				exportEgg.setCode(dto.getCode());
			}
			if (dto.getDateExport() != null) {
				exportEgg.setDateExport(dto.getDateExport());
			}
			if (dto.getQuantity() != 0) {
				exportEgg.setQuantity(dto.getQuantity());
			}
			if(exportEgg.getType()==WLConstant.ImportExportEggType.exportEgg.getValue()
					&& dto.getQuantity()> remainQuantity+exportEgg.getQuantity()) {
				return null;
			}
			ImportExportAnimal importExportAnimal = null;
			if(dto.getType()==WLConstant.ImportExportEggType.importEgg.getValue() && dto.getImportExportAnimal() != null && dto.getImportExportAnimal().getId() != null) {
				importExportAnimal = importExportAnimalRepository.findById(dto.getImportExportAnimal().getId());
				if(importExportAnimal == null) {
					return null;// không tìm thấy đàn con giống
				}
				if(importExportAnimal.getRemainQuantity() <= 0) {
					return null;
				}
				exportEgg.setImportExportAnimal(importExportAnimal);
			}else {
				if(dto.getType()==WLConstant.ImportExportEggType.importEgg.getValue() ) {
					return null;// Nếu không chọn đàn con giống
				}
			}
			if (dto.getBuyerName() != null) {
				exportEgg.setBuyerName(dto.getBuyerName());
			}
			if (dto.getBuyerAdress() != null) {
				exportEgg.setBuyerAdress(dto.getBuyerAdress());
			}
			if(dto.getEggType() == WLConstant.SeedLevel.commodity.getValue()) {
				exportEgg.setEggType(dto.getEggType());// neu la con thuong pham type = 0
			}else {
				exportEgg.setEggType(dto.getEggType());// Nếu là con giống thì type khác 0 tạm hardcode là 1
			}
			if(isNew && dto.getType()==WLConstant.ImportExportEggType.importEgg.getValue()) {
				String code=autoGenBathCode(dto.getDateExport());
				exportEgg.setBatchCode(code);
				if(dto.getDateExport() != null && importExportAnimal != null && importExportAnimal.getDateImport() != null && WLDateTimeUtil.getEndOfDay(dto.getDateExport()).compareTo(WLDateTimeUtil.getStartOfDay(importExportAnimal.getDateImport())) < 0) {
					dto.setCode("-7");// Ngày nhập trứng nhỏ hơn ngày nhập đàn;
					return dto;
				}
				exportEgg.setRemainQuantity(dto.getQuantity());
			}else if (dto.getType()==WLConstant.ImportExportEggType.exportEgg.getValue()) {
				exportEgg.setBatchCode(importRecord.getBatchCode());
				ExportEgg importEgg = null;
				if(dto.getImportEgg()!= null && dto.getImportEgg().getId() != null) {
					importEgg = this.exportEggRepository.findOne(dto.getImportEgg().getId());
				}
				if(importEgg != null) {
					if(importEgg.getDateExport().compareTo(dto.getDateExport()) > 0) {
						dto.setCode("-4");// ngay xuat khong duoc nho hon ngay nhap
						return dto;
					}
					importEgg.setRemainQuantity(remainQuantity - dto.getQuantity());
					exportEgg.setImportEgg(importEgg);
					this.exportEggRepository.save(importEgg);
				}else {
					return null;
				}
			}
			if(dto.getFarm()!=null && dto.getFarm().getId()!=null) {
				Farm farm=farmRepository.findOne(dto.getFarm().getId());
				if(farm!=null)
					exportEgg.setFarm(farm);
			}
			
			if(WLConstant.ImportExportEggType.importEgg.getValue() == dto.getType()) {
				if(importExportAnimal.getQuantityEgg() == null) {
					importExportAnimal.setQuantityEgg(0d);
				}
				if(isNew) {
					importExportAnimal.setQuantityEgg(importExportAnimal.getQuantityEgg() + exportEgg.getQuantity());					
				}else {
					importExportAnimal.setQuantityEgg(importExportAnimal.getQuantityEgg() + exportEgg.getQuantity() - firstQuantity);
				}
				importExportAnimal = importExportAnimalRepository.save(importExportAnimal);
			}
			
			exportEgg.setReasonForExport(dto.getReasonForExport());
			
			ExportEgg ExportEgg2 = this.exportEggRepository.save(exportEgg);

			dto.setId(ExportEgg2.getId());

			return dto;
		}
		return null;

	}

	@Override
	public ExportEggDto updateExportEgg(Long id, ExportEggDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			if(dto.getQuantity() == null || dto.getQuantity().doubleValue() == 0d) {
				return null;
			}
			Double remainQuantity = 0D;
			ExportEgg importRecord = null;
			Boolean isNew = false;
			Double firstQuantity = 0d;
			 if(dto.getType()==WLConstant.ImportExportEggType.exportEgg.getValue()) {
				if(dto.getFarm()==null 
						|| dto.getDateExport()==null
						|| dto.getQuantity()<=0) {
					return null;
				}
				if(dto.getImportEgg()!=null
						&& dto.getImportEgg().getId()!=null
						&& dto.getImportEgg().getId()>0L) {
					importRecord = exportEggRepository.findOne(dto.getImportEgg().getId());
				}
				if(importRecord==null) {//Nếu không thấy phiếu nhập (trong trường hợp xuất đàn) thì trả về null
					return null;
				}
				else {
					remainQuantity = this.checkRemainQuantity(importRecord.getId());					
				}
			}
			ExportEgg exportEgg = null;
			if (id != null) {
				exportEgg = this.exportEggRepository.findOne(id);
			} else if (dto.getId() != null) {
				exportEgg = this.exportEggRepository.findOne(dto.getId());
			}

			if (exportEgg == null) {
				exportEgg = new ExportEgg();
				isNew = true;
				exportEgg.setCreateDate(currentDate);
				exportEgg.setCreatedBy(currentUserName);
			}else {
				firstQuantity = exportEgg.getQuantity();
			}
			if(exportEgg.getType()==WLConstant.ImportExportEggType.exportEgg.getValue()
					&& dto.getQuantity()> remainQuantity+exportEgg.getQuantity()) {
				return null;
			}
			ImportExportAnimal importExportAnimal = null;
			if(dto.getType()==WLConstant.ImportExportEggType.importEgg.getValue() && dto.getImportExportAnimal() != null && dto.getImportExportAnimal().getId() != null) {
				importExportAnimal = importExportAnimalRepository.findById(dto.getImportExportAnimal().getId());
				if(importExportAnimal == null) {
					return null;// không tìm thấy đàn con giống
				}
				if(importExportAnimal.getRemainQuantity() <= 0) {
					return null;
				}
				exportEgg.setImportExportAnimal(importExportAnimal);
			}else {
				if(dto.getType()==WLConstant.ImportExportEggType.importEgg.getValue()) {
					return null;// Nếu không chọn đàn con giống
				}
			}
			if(dto.getType()==WLConstant.ImportExportEggType.importEgg.getValue()){	
				remainQuantity = this.checkRemainQuantity(exportEgg.getId());
				if(dto.getQuantity() < (exportEgg.getQuantity() - remainQuantity)) {
					dto.setCode("-3");// Số lượng sửa không được nhỏ hơn số lượng đã dùng để xuất
					return dto;
				}
				if(dto.getDateExport()!= null && exportEgg.getId()!= null && !this.checkDateImportUpdate(dto.getDateExport(), exportEgg.getId())) {
					dto.setCode("-2");// Ngày nhập trứng không thể lớn hơn ngày xuất xuất trứng từ dữ liệu xuất trứng đã có
					return dto;
				}
				if(dto.getDateExport() != null && importExportAnimal != null && importExportAnimal.getDateImport() != null && WLDateTimeUtil.getEndOfDay(dto.getDateExport()).compareTo(WLDateTimeUtil.getStartOfDay(importExportAnimal.getDateImport())) < 0) {
					dto.setCode("-7");// Ngày nhập trứng nhỏ hơn ngày nhập đàn;
					return dto;
				}
				exportEgg.setRemainQuantity(dto.getQuantity()-(exportEgg.getQuantity() - remainQuantity));
			}else if (dto.getType()==WLConstant.ImportExportEggType.exportEgg.getValue()) {
				exportEgg.setBatchCode(importRecord.getBatchCode());
				ExportEgg importEgg = null;
				if(dto.getImportEgg()!= null && dto.getImportEgg().getId() != null) {
					importEgg = this.exportEggRepository.findOne(dto.getImportEgg().getId());
				}
				exportEgg.setImportEgg(importEgg);
				if(importEgg != null) {
					if(importEgg.getDateExport().compareTo(dto.getDateExport()) > 0) {
						dto.setCode("-4");// ngay xuat khong duoc nho hon ngay nhap
						return dto;
					}
					importEgg.setRemainQuantity(remainQuantity+exportEgg.getQuantity()-dto.getQuantity());
					exportEgg.setImportEgg(importEgg);
					this.exportEggRepository.save(importEgg);
				}
			}
			if(dto.getCode()!=null) {
				exportEgg.setCode(dto.getCode());
			}
			if (dto.getDateExport() != null) {
				exportEgg.setDateExport(dto.getDateExport());
			}
			if (dto.getQuantity() != 0) {
				exportEgg.setQuantity(dto.getQuantity());
			}
			if (dto.getBuyerName() != null) {
				exportEgg.setBuyerName(dto.getBuyerName());
			}
			if (dto.getBuyerAdress() != null) {
				exportEgg.setBuyerAdress(dto.getBuyerAdress());
			}
			
			if(dto.getFarm()!=null && dto.getFarm().getId()!=null) {
				Farm farm=farmRepository.findOne(dto.getFarm().getId());
				if(farm!=null)
					exportEgg.setFarm(farm);
			}
			if(dto.getEggType() == WLConstant.SeedLevel.commodity.getValue()) {
				exportEgg.setEggType(dto.getEggType());// neu la con thuong pham type = 0
			}else {
				exportEgg.setEggType(dto.getEggType());// Nếu là con giống thì type khác 0 tạm hardcode là 1
			}

			exportEgg.setReasonForExport(dto.getReasonForExport());
			
			ExportEgg ExportEgg2 = this.exportEggRepository.save(exportEgg);
			if(dto.getType().intValue() == WLConstant.ImportExportEggType.importEgg.getValue().intValue()) {
				if(exportEgg.getFarm() != null && exportEgg.getId() != null) {
					this.updateDateInfoExport(exportEgg.getId(), exportEgg.getFarm().getId(), exportEgg.getEggType(), importExportAnimal);
				}
			}
			dto.setId(ExportEgg2.getId());
			
			if(WLConstant.ImportExportEggType.importEgg.getValue() == dto.getType()) {
				if(importExportAnimal.getQuantityEgg() == null) {
					importExportAnimal.setQuantityEgg(0d);
				}
				if(isNew) {
					importExportAnimal.setQuantityEgg(exportEgg.getQuantity());					
				}else {
					importExportAnimal.setQuantityEgg(importExportAnimal.getQuantityEgg() + exportEgg.getQuantity() - firstQuantity);
				}
				importExportAnimal = importExportAnimalRepository.save(importExportAnimal);
			}

			return dto;
		}
		return null;
	}

	@Override
	public ExportEggDto deleteById(Long id) {
		ExportEggDto dto=new ExportEggDto();
		if (id != null) {
			ExportEgg iea=this.exportEggRepository.findOne(id);	
			if(iea!=null){
				if(iea.getType()==WLConstant.ImportExportEggType.exportEgg.getValue()){
					if(iea.getImportEgg()!=null && iea.getImportEgg().getId()!=null){
						ExportEgg im=this.exportEggRepository.findOne(iea.getImportEgg().getId());
						if(im!=null && iea.getQuantity()>0){
							Double remain=im.getRemainQuantity() + iea.getQuantity();
							im.setRemainQuantity(remain);
							this.exportEggRepository.save(im);
						}
					}
				}else if(iea.getType()==WLConstant.ImportExportEggType.importEgg.getValue()) {					
					if(iea!=null && iea.getId()!=null && iea.getFarm()!=null &&  iea.getFarm().getId()!=null && iea.getBatchCode()!=null){
						List<ExportEgg> list=this.exportEggRepository.findBy(WLConstant.ImportExportEggType.exportEgg.getValue(), iea.getFarm().getId(), iea.getBatchCode());
						if(list!=null && list.size()>0) {
							dto.setCode("-2");
							dto.setMsg("Không thể xóa bản ghi nhập đàn vì còn dữ liệu xuất đàn!");
							return dto;
						}
					}
					ImportExportAnimal importExportAnimal = importExportAnimalRepository.findById(iea.getImportExportAnimal().getId());
					importExportAnimal.setQuantityEgg(importExportAnimal.getQuantityEgg() - iea.getQuantity());
					importExportAnimalRepository.save(importExportAnimal);
				}
				
				this.exportEggRepository.delete(id);	
				
				//cập nhật lại số tồn hiện tại của cơ sở chăn nuôi khi xóa
				if(iea.getFarm()!=null && iea.getFarm().getId()!=null){
					farmService.countBalanceNumberByFarm(iea.getFarm().getId());
				}
				
				return dto;
			}
			
		}
		return dto;
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {
		
		if(ids != null && ids.size() > 0) {
			for(Long id :ids) {
				this.exportEggRepository.delete(id);
			}
			return true;
		}
		return false;
	}
	@Override
	public Page<ExportEggDto> searchExportEggDto(ExportEggSearchDto searchDto,
			int pageIndex, int pageSize) {
		
			if(pageIndex > 0) pageIndex = pageIndex-1;
			else pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User currentUser = null;
			if (authentication != null) {
				currentUser = (User) authentication.getPrincipal();
			}
			
			String namecode = searchDto.getNameOrCode();
			boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
			boolean isAdministrativeUnitRole = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH) 
												|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DISTRICT)
												|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_WARD);
			boolean isFarmerRole =  SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_FAMER);
			if(!isAdmin && isAdministrativeUnitRole) {
				List<FmsAdministrativeUnit> list = userAdministrativeUnitService.getAdministrativeUnitByLoginUser();
				if(list!=null && list.size()>0) {
					for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
						if(fmsAdministrativeUnit.getParent()==null) {//Cấp tỉnh
							searchDto.setProvince(fmsAdministrativeUnit.getId());
						}
						else if(fmsAdministrativeUnit.getParent()!=null 
								&& fmsAdministrativeUnit.getParent().getParent()==null) {//Cấp huyện
							searchDto.setDistrict(fmsAdministrativeUnit.getId());
						}
						else if(fmsAdministrativeUnit.getParent()!=null 
								&& fmsAdministrativeUnit.getParent().getParent()!=null 
								&& fmsAdministrativeUnit.getParent().getParent().getParent()==null) {//Cấp xã
							searchDto.setWard(fmsAdministrativeUnit.getId());
						}
						else {
							return null;
						}
					}
				}
				else {
					return null;
				}
			}	
			else if(isFarmerRole){
				searchDto.setNameOrCode(currentUser.getUsername());
			}
			
			String sql = "select new com.globits.wl.dto.ExportEggDto(fa) from ExportEgg fa  where (1=1)";
			String sqlCount = "select count(fa.id) from ExportEgg fa  where (1=1)";
			String whereClause ="";
			if(namecode!=null && namecode.length()>0) {
				whereClause += " and (fa.code like :namecode or fa.farm.name like :namecode or fa.farm.code like :namecode or fa.batchCode like :namecode)";
			}
//			if(searchDto.getBatchCode() != null && searchDto.getBatchCode().trim().length() > 0) {
//				whereClause += " and (fa.batchCode like :batchCode)";
//			}
			if(searchDto.getFarmId()!=null){
				whereClause += " and (fa.farm.id= :farmId)";
			}

			if(searchDto.getFromDate()!=null) {//
				whereClause += " and (fa.dateExport>= :fromDate)";
			}
			if(searchDto.getToDate()!=null) {//
				whereClause += " and (fa.dateExport<= :toDate)";
			}
			if(searchDto.getProvince()!=null && searchDto.getProvince()>0) {
				whereClause += " and (fa.farm.administrativeUnit.parent.parent.id= :provinceId)";
			}
			if(searchDto.getDistrict()!=null && searchDto.getDistrict()>0) {
				whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
			}
			if(searchDto.getWard()!=null && searchDto.getWard()>0) {
				whereClause += " and (fa.farm.administrativeUnit.id= :wardId)";
			}
			if(searchDto.getAnimalId() != null) {
				whereClause += " and (fa.importExportAnimal.animal.parent.id= :animalId)";
			}
			if(searchDto.getType() != null) {
				whereClause += " and (fa.type = :type)";
			}
			
					
			sql +=whereClause;
			sql +=" order by fa.dateExport desc ";
			sqlCount+=whereClause;

			Query q = manager.createQuery(sql,ExportEggDto.class);
			Query qCount = manager.createQuery(sqlCount);
			
			
			if(namecode!=null && namecode.length()>0) {
				q.setParameter("namecode", '%'+namecode+'%');
				qCount.setParameter("namecode", '%'+namecode+'%');
			}		
//			if(searchDto.getBatchCode() != null && searchDto.getBatchCode().trim().length() > 0) {
//				whereClause += " and (fa.batchCode like :batchCode)";
//				q.setParameter("batchCode", '%'+searchDto.getBatchCode()+'%');
//				qCount.setParameter("batchCode", '%'+searchDto.getBatchCode()+'%');
//			}
			if(searchDto.getFarmId()!=null){
				q.setParameter("farmId", searchDto.getFarmId());
				qCount.setParameter("farmId", searchDto.getFarmId());
			}
			if(searchDto.getFromDate()!=null) {//
				q.setParameter("fromDate", searchDto.getFromDate());
				qCount.setParameter("fromDate", searchDto.getFromDate());
			}
			if(searchDto.getToDate()!=null) {//
				searchDto.setToDate(WLDateTimeUtil.getEndOfDay(searchDto.getToDate()));
				q.setParameter("toDate", searchDto.getToDate());
				qCount.setParameter("toDate", searchDto.getToDate());
			}		
			if(searchDto.getProvince()!=null && searchDto.getProvince()>0) {
				q.setParameter("provinceId", searchDto.getProvince());
				qCount.setParameter("provinceId", searchDto.getProvince());
			}
			if(searchDto.getDistrict()!=null && searchDto.getDistrict()>0) {
				q.setParameter("districtId", searchDto.getDistrict());
				qCount.setParameter("districtId", searchDto.getDistrict());
			}
			if(searchDto.getWard()!=null && searchDto.getWard()>0) {
				q.setParameter("wardId", searchDto.getWard());
				qCount.setParameter("wardId", searchDto.getWard());
			}
			if(searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}
			if(searchDto.getType() != null) {
				q.setParameter("type", searchDto.getType());
				qCount.setParameter("type", searchDto.getType());
			}
			q.setFirstResult((pageIndex)*pageSize);
			q.setMaxResults(pageSize);
			
			Long numberResult =(Long)qCount.getSingleResult();
			Page<ExportEggDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
			return page;
	}

	@Override
	public ReportManagerDto getSumQuantity(ExportEggSearchDto paramDto) {
		ReportManagerDto ret=new ReportManagerDto();
		Double quantity=sumQuantity(paramDto);
		int count=count(paramDto);
		ret.setQuantity(quantity);
		ret.setCount(count);
		return ret;
	}

	public double sumQuantity(ExportEggSearchDto paramDto ){
		double ret=0D;
		String sql=" SELECT ";
		//sql+= "SUM(iea.quantity),count(iea.id)  ";//Tính tổng 
		if(paramDto.getType()==null || 
			(paramDto.getType()!=WLConstant.ImportExportEggType.importEgg.getValue()
				&& paramDto.getType()!=WLConstant.ImportExportEggType.exportEgg.getValue()
			))
		{			
			sql+= "SUM(iea.quantity*iea.type) ";//Tính tổng tồn
		}
		else {
			sql+= "SUM(iea.quantity) ";//Tính tổng nhập hoặc xuất
		}
		
		sql+= "FROM ExportEgg iea WHERE 1=1 ";
		String namecode = paramDto.getNameOrCode();
		String whereClause="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and ( iea.farm.name like :namecode or iea.farm.code like :namecode  or iea.batchCode like :namecode)";
		}
		if(paramDto.getFromDate()!=null) {
			whereClause+=" AND iea.dateExport >= :fromDate ";
		}
		if(paramDto.getToDate()!=null) {
			whereClause+=" AND iea.dateExport<= :toDate ";
		}
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {
			whereClause+=" AND iea.farm.id = :farmId ";
		}
		if(paramDto.getProvince()!=null && paramDto.getProvince()>0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.parent.id= :provinceId)";
		}
		if(paramDto.getDistrict()!=null && paramDto.getDistrict()>0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(paramDto.getWard()!=null && paramDto.getWard()>0) {
			whereClause += " and (iea.farm.administrativeUnit.id= :wardId)";
		}
		if(paramDto.getAnimalId()!= null) {
			whereClause += " and (iea.importExportAnimal.animal.parent.id= :animalId)";
		}
		if(paramDto.getType() != null) {
			whereClause += " and (iea.type = :type)";
		}
		if(paramDto.getOwnershipId()!=null && paramDto.getOwnershipId()>0L) {
			whereClause+=" AND iea.farm.ownership.id = :ownershipId ";
		}
		
		sql = sql + whereClause ;
		System.out.println(sql);
		Query q = manager.createQuery(sql);
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');			
		}	
		if(paramDto.getFromDate()!=null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if(paramDto.getToDate()!=null) {
			paramDto.setToDate(WLDateTimeUtil.getEndOfDay(paramDto.getToDate()));
			q.setParameter("toDate", paramDto.getToDate());
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {		
			q.setParameter("farmId", paramDto.getFarmId());
		}	
		if(paramDto.getProvince()!=null && paramDto.getProvince()>0) {
			q.setParameter("provinceId", paramDto.getProvince());
			
		}
		if(paramDto.getDistrict()!=null && paramDto.getDistrict()>0) {
			q.setParameter("districtId", paramDto.getDistrict());
			
		}
		if(paramDto.getWard()!=null && paramDto.getWard()>0) {
			q.setParameter("wardId", paramDto.getWard());
		}
		if(paramDto.getAnimalId()!= null) {
			q.setParameter("animalId", paramDto.getAnimalId());
		}
		if(paramDto.getType() != null) {
			q.setParameter("type", paramDto.getType());
		}
		if(paramDto.getOwnershipId()!=null && paramDto.getOwnershipId()>0L) {
			q.setParameter("ownershipId", paramDto.getOwnershipId());	
		}
		Double results = (Double)q.getSingleResult();
		
		if(results!=null && results.doubleValue()>0) {
			ret= results.doubleValue();		
		}
		
		return ret;
	}
	public int count(ExportEggSearchDto paramDto ){
		int ret=0;
		String sql=" SELECT ";
		//sql+= "SUM(iea.quantity),count(iea.id)  ";//Tính tổng 
		sql+= "count(iea.id) ";//Tính tổng 
		sql+= "FROM ExportEgg iea WHERE 1=1 ";
		
		String namecode = paramDto.getNameOrCode();
		String whereClause="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (iea.farm.name like :namecode or iea.farm.code like :namecode )";
		}
		if(paramDto.getFromDate()!=null) {
			whereClause+=" AND iea.dateExport >= :fromDate ";
		}
		if(paramDto.getToDate()!=null) {
			whereClause+=" AND iea.dateExport<= :toDate ";
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {
			whereClause+=" AND iea.farm.id = :farmId ";
		}		
		if(paramDto.getProvince()!=null && paramDto.getProvince()>0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.parent.id= :provinceId)";
		}
		if(paramDto.getDistrict()!=null && paramDto.getDistrict()>0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(paramDto.getWard()!=null && paramDto.getWard()>0) {
			whereClause += " and (iea.farm.administrativeUnit.id= :wardId)";
		}
		if(paramDto.getAnimalId() != null) {
			whereClause += " and (iea.importExportAnimal.animal.parent.id= :animalId)";
		}
		if(paramDto.getType() != null) {
			whereClause += " and (iea.type= :type)";
		}
		
		sql = sql + whereClause ;
		System.out.println(sql);
		Query q = manager.createQuery(sql);
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');			
		}	
		if(paramDto.getFromDate()!=null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if(paramDto.getToDate()!=null) {
			paramDto.setToDate(WLDateTimeUtil.getEndOfDay(paramDto.getToDate()));
			q.setParameter("toDate", paramDto.getToDate());
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {		
			q.setParameter("farmId", paramDto.getFarmId());
		}	
		if(paramDto.getProvince()!=null && paramDto.getProvince()>0) {
			q.setParameter("provinceId", paramDto.getProvince());
			
		}
		if(paramDto.getDistrict()!=null && paramDto.getDistrict()>0) {
			q.setParameter("districtId", paramDto.getDistrict());
			
		}
		if(paramDto.getWard()!=null && paramDto.getWard()>0) {
			q.setParameter("wardId", paramDto.getWard());
			
		}
		if(paramDto.getAnimalId() != null) {
			q.setParameter("animalId", paramDto.getAnimalId());
		}
		if(paramDto.getType() != null) {
			q.setParameter("type", paramDto.getType());
		}
		Long results = (Long)q.getSingleResult();
		
		if(results!=null && results>0) {
			ret=results.intValue();		
		}
		
		return ret;
	}

	@Override
	public List<ImportExportAnimalDto> getExportEgg(ExportEggSearchDto paramDto) throws ParseException {
		List<ImportExportAnimalDto> ret = new ArrayList<ImportExportAnimalDto>();
		if(paramDto != null) {
			for (int i = paramDto.getFromMonth(); i <= paramDto.getToMonth(); i++) {
				Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
				fromDate = WLDateTimeUtil.numberToDate(1, i, paramDto.getCurrentYear());
			    Date toDate= WLDateTimeUtil.getLastDayOfMonth(i, paramDto.getCurrentYear());
			    paramDto.setFromDate(fromDate);
			    paramDto.setToDate(toDate);	
			    
			    ImportExportAnimalDto importExportAnimalDto = new ImportExportAnimalDto();
			    importExportAnimalDto.setQuantity(this.sumQuantity(paramDto));
			    importExportAnimalDto.setYearReport(paramDto.getCurrentYear());
	    		importExportAnimalDto.setMonthReport(i);
			    ret.add(importExportAnimalDto);
			}
		}
		return ret;
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
			max=this.exportEggRepository.count(1, importDate,end);
			if(max!=null && max>0){
				max=max+1;
			}else{
				max=1;
			}
			code=bathCode;
			bathCode=bathCode +"-"+max;
			
			//phòng trường hợp đã tồn tại bathCode
			List<ExportEgg> list=exportEggRepository.findByTypeAndBatchCode(WLConstant.ImportExportAnimalType.importAnimal.getValue(), bathCode);
			if(list!=null && list.size()>0) {
				max=max+1;
				bathCode=code +"-"+max;
			}
		}
		return bathCode;
	}
	@Override
	public Double checkRemainQuantity(Long importEggId) {
		ExportEgg exportEgg=exportEggRepository.findOne(importEggId);
		if(exportEgg!=null) {
			List<ExportEgg> listExport = exportEggRepository.findExportByImport(importEggId);
			if(listExport!=null && listExport.size()>0) {
				Double ret = exportEgg.getQuantity();
				for (ExportEgg exportEgg2 : listExport) {
					ret-= exportEgg2.getQuantity();
				}
				return ret;
			}
			else {
				return exportEgg.getQuantity();
			}
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
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			sql+= "SUM(iea.quantity),COUNT(iea.farm.id) %s ";//Tính tổng xuất hoặc nhập đàn
		}
		else 
		{
			sql+= "SUM(iea.quantity * iea.type),COUNT(iea.farm.id) %s ";//Tính tồn kho
		}
		sql+= "FROM ExportEgg iea WHERE 1=1 ";
		
		String whereClause="";
		if(paramDto.getFromDate()!=null) {
			whereClause+=" AND iea.dateExport >= :fromDate ";
		}
		if(paramDto.getToDate()!=null) {
			whereClause+=" AND iea.dateExport<= :toDate ";
		}
		if(paramDto.getBatchCode()!=null && paramDto.getBatchCode()!="") {
			whereClause+=" AND iea.batchCode = :batchCode ";
		}
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {
			whereClause+=" AND iea.farm.id = :farmId ";
		}		
		if(paramDto.getEggType()!=null && paramDto.getEggType()>0) {
			whereClause+=" AND iea.eggType = :eggType ";
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
		if(paramDto.getType()!=null && 
				(paramDto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() || 
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			whereClause+=" AND iea.type = :type ";
		}
		if(paramDto.getOwnershipId()!=null && paramDto.getOwnershipId()>0L) {
			whereClause+=" AND iea.farm.ownership.id = :ownershipId ";
		}
		if(paramDto.getIsSeedlevel()!=null && paramDto.getIsSeedlevel()==false) {
			whereClause+=" AND  ( (iea.importExportAnimal.seedLevel IS NULL)) "; 
		}
		if(paramDto.getLevels()!=null && paramDto.getLevels().size()>0) {
			whereClause+=" AND iea.importExportAnimal.seedLevel.level in (:levels) "; 
		}
		if(paramDto.getLiveStockMethod()!=null) {
			whereClause+=" AND iea.importExportAnimal.animal.liveStockMethod = :liveStockMethod ";
		}
		if(paramDto.getAnimalParentId()!=null && paramDto.getAnimalParentId()>0) {
			whereClause+=" AND iea.importExportAnimal.animal.parent.id = :animalParentId ";
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
		String report = " ";
		String importEgg = " ";
		String orderByClause="";
		
		List<String> columns = new ArrayList<String>();
		columns.add(WLConstant.FunctionAndGroupByItem.quantity.getValue());
//		columns.add(FMSConstant.GroupByItem.amount.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.countFarm.getValue());
		
		String groupByClause="";
		String selectClause="";
		if(paramDto.getGroupByItems()!=null && paramDto.getGroupByItems().size()>0) {
			
			for (String grItem : paramDto.getGroupByItems()) {
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
				if(WLConstant.FunctionAndGroupByItem.eggType.getValue().equals(grItem)) {
					groupByClause+=" iea.eggType, ";
					productTarget=" ,iea.eggType ";
					selectClause+=productTarget;
					columns.add(WLConstant.FunctionAndGroupByItem.eggType.getValue());
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
				if(WLConstant.FunctionAndGroupByItem.report.getValue().equals(grItem)) {
					groupByClause+=" iea.importExportAnimal.animal.parent.reportCode,iea.importExportAnimal.animal.parent.reportName, ";	
					report=" ,iea.importExportAnimal.animal.parent.reportCode,iea.importExportAnimal.animal.parent.reportName ";	
					selectClause+=report;
					columns.add(WLConstant.FunctionAndGroupByItem.report.getValue()+"code");
					columns.add(WLConstant.FunctionAndGroupByItem.report.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.importEgg.getValue().equals(grItem)) {
					groupByClause+=" iea.importExportAnimal.id,iea.importExportAnimal.batchCode, ";	
					importEgg=" ,iea.importExportAnimal.id,iea.importExportAnimal.batchCode ";	
					selectClause+=importEgg;
					columns.add(WLConstant.FunctionAndGroupByItem.importEgg.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.importEgg.getValue()+"code");
				}
				if(WLConstant.FunctionAndGroupByItem.animalParent.getValue().equals(grItem)) {
					groupByClause+=" iea.importExportAnimal.animal.parent.id,iea.importExportAnimal.animal.parent.name, ";	
					animalParent=" ,iea.importExportAnimal.animal.parent.id,iea.importExportAnimal.animal.parent.name ";	
					selectClause+=animalParent;
					columns.add(WLConstant.FunctionAndGroupByItem.animalParent.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.animalParent.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.liveStockMethod.getValue().equals(grItem)) {
					groupByClause+=" iea.importExportAnimal.animal.liveStockMethod, ";					
					selectClause+=" ,iea.importExportAnimal.animal.liveStockMethod ";
					columns.add(WLConstant.FunctionAndGroupByItem.liveStockMethod.getValue());
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
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {		
			q.setParameter("farmId", paramDto.getFarmId());
		}		
		if(paramDto.getEggType()!=null && paramDto.getEggType()>0) {
			q.setParameter("eggType", paramDto.getEggType());
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
		if(paramDto.getType()!=null && 
				(paramDto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() || 
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			q.setParameter("type", paramDto.getType());			
		}
		if(paramDto.getOwnershipId()!=null && paramDto.getOwnershipId()>0L) {
			q.setParameter("ownershipId", paramDto.getOwnershipId());	
		}
		if(paramDto.getLevels()!=null && paramDto.getLevels().size()>0) {
			q.setParameter("levels", paramDto.getLevels());	
		}
		if(paramDto.getLiveStockMethod()!=null) {
			q.setParameter("liveStockMethod", paramDto.getLiveStockMethod());
		}
		if(paramDto.getAnimalParentId()!=null && paramDto.getAnimalParentId()>0) {
			q.setParameter("animalParentId", paramDto.getAnimalParentId());
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
	public ReportManagerDto getTotalEggReport(ExportEggSearchDto paramDto) {
		ReportManagerDto reportManagerDto = new ReportManagerDto();
		
		paramDto.setType(null);
		reportManagerDto.setTotalInventory(this.sumQuantity(paramDto));
//		reportManagerDto.setCountInvenroty(this.count(paramDto));
		paramDto.setType(1);
		reportManagerDto.setTotalImport(this.sumQuantity(paramDto));
//		reportManagerDto.setCountImport(this.count(paramDto));
		paramDto.setType(-1);
		reportManagerDto.setTotalExport(this.sumQuantity(paramDto));
//		reportManagerDto.setCountExport(this.count(paramDto));
		return reportManagerDto;
	}
	
	
	public void updateDateInfoExport(Long importEggId,Long farmId, int eggType, ImportExportAnimal importExportAnimal) {
		List<ExportEgg>  exportEggs = exportEggRepository.findExportByImport(importEggId);
		Farm farm = null;
		if(farmId != null) {
			farm = farmService.findById(farmId);
		}
		if(farm == null) {
			return;
		}
		if(exportEggs != null && exportEggs.size() > 0) {
			for(ExportEgg exportEgg: exportEggs) {
				if(exportEgg != null) {
					exportEgg.setFarm(farm);
					exportEgg.setEggType(eggType);
					exportEgg.setImportExportAnimal(importExportAnimal);
				}
			}
		}
		exportEggRepository.save(exportEggs);
	}
	// trả về false nếu ngày nhập vào ko hợp lệ
	@Override
	public Boolean checkDateImportUpdate(Date dateImport,Long importEggId) {
		// Lấy ra những bản ghi xuất có ngày xuất nhỏ hơn dateImport đầu vào
		dateImport = WLDateTimeUtil.getStartOfDay(dateImport);
		List<ExportEgg> exportEggs= this.exportEggRepository.findExportGrantByDate(dateImport,importEggId);
		if(exportEggs != null && exportEggs.size() > 0) {
			for(ExportEgg exportEgg: exportEggs) {
				if(exportEgg != null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public List<InventoryReportDto> percentOfGrowthEggReport(ReportParamDto paramDto) throws ParseException {
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
					if(dto1.getYearReport()==preYear && dto.getEggType().equals(dto1.getEggType())) {
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
				
		//query tất cả các loại  trứng
		if(paramDto.getGroupByItems()!=null && paramDto.getGroupByItems().size()>0) {
			List<String> animalParenst=new ArrayList<String>();
			for (String item : paramDto.getGroupByItems()) {
				if(item.equals(WLConstant.FunctionAndGroupByItem.eggType.getValue())) {
					animalParenst.add(item);
				}
			}
			paramDto.getGroupByItems().removeAll(animalParenst);
		}
		paramDto.setIsSumTotal(true);
		List<InventoryReportDto> lst=this.getQuantityReport(paramDto);
		if(lst!=null && lst.size()>0) {
			for (InventoryReportDto dto : lst) {
				dto.setEggTypeName("Tất cả các giống");
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
	public List<InventoryReportDto> getExportEggFollowTheHerdReport(ReportParamDto paramDto) throws ParseException{
		// TODO Auto-generated method stub
		
		if (paramDto != null) {
			if (paramDto.getFromWeek() != null && paramDto.getToWeek() != null) {
				List<WeekOfYearDto> listWeekByYear = WLDateTimeUtil.getWeeksByYear(paramDto.getCurrentYear());
				if (listWeekByYear != null && listWeekByYear.size() > 0) {
					List<WeekOfYearDto> listWeek = new ArrayList<WeekOfYearDto>();
					for (int i = paramDto.getFromWeek(); i <= paramDto.getToWeek(); i++) {
						listWeek.add(listWeekByYear.get(i-1));
					}
					if (listWeek != null && listWeek.size() > 0) {
						List<InventoryReportDto> ret = new ArrayList<InventoryReportDto>();
						for (WeekOfYearDto week : listWeek) {
							paramDto.setFromDate(WLDateTimeUtil.getStartOfDay(week.getStartDate()));
							paramDto.setToDate(WLDateTimeUtil.getEndOfDay(week.getEndDate()));

							List<InventoryReportDto> listInventoryReportDto = this.getSumQuantity(paramDto);
							if (listInventoryReportDto != null && listInventoryReportDto.size() > 0) {

								for (InventoryReportDto inventoryReportDto : listInventoryReportDto) {
									inventoryReportDto.setWeekIndex(week.getOrderNumber());
									inventoryReportDto.setWeekSortName("Tuần " + week.getOrderNumber());
									inventoryReportDto.setWeekFullName(week.getName());
									ret.add(inventoryReportDto);
								}
							}
						}
						return ret;
					}
				}
			}
		}
		
		return null;
	}
	
}
