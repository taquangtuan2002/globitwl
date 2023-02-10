package com.globits.wl.service.impl;

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
import com.globits.security.domain.User;
import com.globits.wl.domain.Bran;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.ImportAnimalFeed;
import com.globits.wl.domain.ImportDrug;
import com.globits.wl.domain.Unit;
import com.globits.wl.dto.ImportAnimalFeedDto;
import com.globits.wl.dto.ImportDrugDto;
import com.globits.wl.dto.functiondto.ImportAnimalFeedSearchDto;
import com.globits.wl.repository.BranRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.ImportAnimalFeedRepository;
import com.globits.wl.repository.UnitRepository;
import com.globits.wl.service.ExportAnimalFeedService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ExportAnimalFeedServiceImpl extends GenericServiceImpl<ImportAnimalFeed, Long>
		implements ExportAnimalFeedService {

	@Autowired
	private ImportAnimalFeedRepository exportAnimalFeedRepository;

	@Autowired
	private BranRepository branRepository;

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Override
	public ImportAnimalFeedDto getExportAnimalFeedById(Long id) {
		return this.exportAnimalFeedRepository.getAnimalFeedById(id);
	}

	@Override
	public ImportAnimalFeedDto saveExportAnimalFeed(ImportAnimalFeedDto exportAnimalDto, Long id) {
		ImportAnimalFeed exportAnimalFeed = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		
		double firstQuantity = 0;
		Double remainQuantity = null;
		// check required
		if (exportAnimalDto.getDateIssue() == null || exportAnimalDto.getQuantity() < 1 
				|| exportAnimalDto.getDateIssue().after(WLDateTimeUtil.getEndOfDay(new Date()))
				|| exportAnimalDto.getImportAnimalFeed() == null || exportAnimalDto.getImportAnimalFeed().getId() == null) {
			return null;
		}

		remainQuantity = this.checkRemainQuantity(exportAnimalDto.getImportAnimalFeed().getId());

		ImportAnimalFeed importAnimalFeedParent = exportAnimalFeedRepository.findOne(exportAnimalDto.getImportAnimalFeed().getId());
		if (importAnimalFeedParent == null) {
			return null;
		}

		if (id != null) {// trường hợp edit
			exportAnimalFeed = this.exportAnimalFeedRepository.findOne(id);
		} else if (exportAnimalDto.getId() != null) {
			exportAnimalFeed = this.exportAnimalFeedRepository.findOne(exportAnimalDto.getId());
		}

		if (exportAnimalFeed == null) {// trường hợp thêm mới
			exportAnimalFeed = new ImportAnimalFeed();
			exportAnimalFeed.setCreateDate(currentDate);
			exportAnimalFeed.setCreatedBy(currentUserName);
			exportAnimalFeed.setType(WLConstant.AnimalFeedType.exportAnimalFeed.getValue());

		}
		else {
			// trường hợp edit cần check valid
			firstQuantity = exportAnimalFeed.getQuantity();
		}
		
		if(exportAnimalDto.getQuantity() > (remainQuantity +  firstQuantity)) {
			exportAnimalDto.setCode("-3");
			return exportAnimalDto;// so luong xuat khong duoc lon hon so luong con lai
		}
		if(importAnimalFeedParent != null && importAnimalFeedParent.getDateIssue() != null && exportAnimalDto.getDateIssue() != null && exportAnimalDto.getDateIssue().compareTo(WLDateTimeUtil.getStartOfDay(importAnimalFeedParent.getDateIssue())) < 0  ) {
			exportAnimalDto.setCode("-2");
			return exportAnimalDto;//  ngay xuat nho hon ngay nhap
		}

		if (exportAnimalDto.getDateIssue() != null)
			exportAnimalFeed.setDateIssue(exportAnimalDto.getDateIssue());
		if (exportAnimalDto.getQuantity() != 0)
			exportAnimalFeed.setQuantity(exportAnimalDto.getQuantity());
		if (exportAnimalDto.getAmount() != 0)
			exportAnimalFeed.setAmount(exportAnimalDto.getAmount());
		if (exportAnimalDto.getDescription() != null)
			exportAnimalFeed.setDescription(exportAnimalDto.getDescription());

		if (exportAnimalDto.getBran() != null && exportAnimalDto.getBran().getId() != null) {
			Bran bran = branRepository.findOne(exportAnimalDto.getBran().getId());
			if (bran != null)
				exportAnimalFeed.setBran(bran);
		}

		exportAnimalFeed.setCode(importAnimalFeedParent.getCode());
		exportAnimalFeed.setFarm(importAnimalFeedParent.getFarm());
		exportAnimalFeed.setBran(importAnimalFeedParent.getBran());
		exportAnimalFeed.setUnit(importAnimalFeedParent.getUnit());
		
		exportAnimalFeed.setBuyerAdress(exportAnimalDto.getBuyerAdress());
		exportAnimalFeed.setBuyerName(exportAnimalDto.getBuyerName());

		importAnimalFeedParent.setRemainQuantity(remainQuantity + firstQuantity - exportAnimalDto.getQuantity());
		importAnimalFeedParent = this.exportAnimalFeedRepository.save(importAnimalFeedParent);

		exportAnimalFeed.setImportAnimalFeed(importAnimalFeedParent);
		this.exportAnimalFeedRepository.save(exportAnimalFeed);
		exportAnimalDto.setId(exportAnimalFeed.getId());
		return exportAnimalDto;
	}

	private Double checkRemainQuantity(Long id) {
		ImportAnimalFeed importAnimalFeed = exportAnimalFeedRepository.findOne(id);
		if (importAnimalFeed != null) {
			List<ImportAnimalFeed> listExport = exportAnimalFeedRepository.findExportByImport(id);
			if (listExport != null && listExport.size() > 0) {
				Double ret = importAnimalFeed.getQuantity();
				for (ImportAnimalFeed exportDrug : listExport) {
					ret -= exportDrug.getQuantity();
				}
				return ret;
			} else {
				return importAnimalFeed.getQuantity();
			}
		}
		return null;
	}

	@Override
	public ImportAnimalFeedDto removeExportAnimalFeed(Long id) {
		if (id != null) {
			ImportAnimalFeedDto exportAnimalDto = new ImportAnimalFeedDto();
			ImportAnimalFeed importAnimalFeed = null;
			ImportAnimalFeed feed = exportAnimalFeedRepository.findOne(id);
			
			if(feed.getImportAnimalFeed() != null && feed.getImportAnimalFeed().getId() != null) {
				importAnimalFeed= exportAnimalFeedRepository.findOne(feed.getImportAnimalFeed().getId());
			}
			if (feed != null) {
				exportAnimalDto = new ImportAnimalFeedDto(feed);
				if(importAnimalFeed != null) {// xóa xuất
					importAnimalFeed.setRemainQuantity(importAnimalFeed.getRemainQuantity() + feed.getQuantity());
					this.exportAnimalFeedRepository.save(importAnimalFeed);
				}
				this.exportAnimalFeedRepository.delete(id);
				return exportAnimalDto;
			}
		}
		return null;
	}

	@Override
	public Page<ImportAnimalFeedDto> searchDto(ImportAnimalFeedSearchDto searchDto, int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		String sql = " select new com.globits.wl.dto.ImportAnimalFeedDto(fa) from ImportAnimalFeed fa  where (1=1)";
		String sqlCount = "select count(fa.id) from ImportAnimalFeed fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.farm.name like :namecode or fa.farm.code like :namecode )";
		}
		if(searchDto.getFarmId()!=null){
			whereClause += " and (fa.farm.id= :farmId)";
		}
		if(searchDto.getBranId()!=null) {//
			whereClause += " and (fa.bran.id= :branId)";
		}
		if(searchDto.getType()!=null) {//
			whereClause += " and (fa.type= :type)";
		}
		if(searchDto.getFromDate()!=null) {//
			whereClause += " and (fa.dateIssue>= :fromDate)";
		}
		if(searchDto.getToDate()!=null) {//
			whereClause += " and (fa.dateIssue<= :toDate)";
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
				
		sql +=whereClause;
		sql +=" order by fa.dateIssue desc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,ImportAnimalFeedDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}	
		if(searchDto.getFarmId()!=null){
			q.setParameter("farmId", searchDto.getFarmId());
			qCount.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getBranId()!=null) {//
			q.setParameter("branId", searchDto.getBranId());
			qCount.setParameter("branId", searchDto.getBranId());			
		}
		if(searchDto.getType()!=null) {//
			q.setParameter("type", searchDto.getType());
			qCount.setParameter("type", searchDto.getType());
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
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<ImportAnimalFeedDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;	
	}

	@Override
	public Page<ImportAnimalFeedDto> getListExportAnimalFeed(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.exportAnimalFeedRepository.getListAnimalFeedByType(pageable,
				WLConstant.AnimalFeedType.exportAnimalFeed.getValue());
	}

}
