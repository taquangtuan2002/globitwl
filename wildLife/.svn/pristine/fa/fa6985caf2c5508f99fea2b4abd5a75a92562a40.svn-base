package com.globits.wl.service.impl;

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
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.ExportAnimal;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.dto.ExportAnimalDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.ExportAnimalRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.service.ExportAnimalService;

@Service
public class ExportAnimalServiceImpl extends GenericServiceImpl<ExportAnimal, Long>  implements ExportAnimalService{
	@Autowired
	private ExportAnimalRepository exportAnimalRepository;
	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private FarmRepository farmRepository; 
	@Autowired
	private ProductTargetRepository productTargetRepository;	
	@Autowired
	private OriginalRepository originalRepository;
	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	
	@Override
	public Page<ExportAnimalDto> getListExportAnimal(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.exportAnimalRepository.getListExportAnimail(pageable);
	}

	@Override
	public List<ExportAnimalDto> getAll() {
		return this.exportAnimalRepository.getAll();
	}

	@Override
	public ExportAnimalDto getExportAnimalById(Long id) {
		return this.exportAnimalRepository.getExportAnimalById(id);
	}

	
	
	@Override
	public ExportAnimalDto saveExportAnimal(ExportAnimalDto exportAnimalDto, Long id) {
		ExportAnimal exportAnimal = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			exportAnimal = this.exportAnimalRepository.findOne(id);
		} else if (exportAnimalDto.getId() != null) {
			exportAnimal = this.exportAnimalRepository.findOne(exportAnimalDto.getId());
		}

		if (exportAnimal == null) {// trường hợp thêm mới
			exportAnimal = new ExportAnimal();
			exportAnimal.setCreateDate(currentDate);
			exportAnimal.setCreatedBy(currentUserName);
		}
		if(exportAnimalDto.getVoucherCode() != null)
			exportAnimal.setVoucherCode(exportAnimalDto.getVoucherCode());
		if(exportAnimalDto.getBatchCode() != null )
			exportAnimal.setBatchCode(exportAnimalDto.getBatchCode());
		if(exportAnimalDto.getDateImport() != null)
			exportAnimal.setDateImport(exportAnimalDto.getDateImport());
		if(exportAnimalDto.getQuantity() != 0)
			exportAnimal.setQuantity(exportAnimalDto.getQuantity());
		if(exportAnimalDto.getAmount() != 0)
			exportAnimal.setAmount(exportAnimalDto.getAmount());
		if(exportAnimalDto.getDescription() !=  null)
			exportAnimal.setDescription(exportAnimalDto.getDescription());
		if(exportAnimalDto.getDateExport() != null)
			exportAnimal.setDateExport(exportAnimalDto.getDateExport());
		if(exportAnimalDto.getType() != 0)
			exportAnimal.setType(exportAnimalDto.getType());
		if(exportAnimalDto.getExportReason() != null)
			exportAnimal.setExportReason(exportAnimalDto.getExportReason());
		if(exportAnimalDto.getBuyerName() != null)
			exportAnimal.setBuyerName(exportAnimalDto.getBuyerName());
		if(exportAnimalDto.getBuyerAdress() != null)
			exportAnimal.setBuyerAdress(exportAnimalDto.getBuyerAdress());
		
		if (exportAnimalDto.getAnimal() != null && exportAnimalDto.getAnimal().getId() != null) {
			Animal animal = this.animalRepository.findOne(exportAnimalDto.getAnimal().getId());			
			exportAnimal.setAnimal(animal);
		}
		if (exportAnimalDto.getFarm() != null) {
			Farm farm = this.farmRepository.findOne(exportAnimalDto.getFarm().getId());			
			exportAnimal.setFarm(farm);
		}
		if(exportAnimalDto.getProvince() != null && exportAnimalDto.getProvince().getId() != null) {
			FmsAdministrativeUnit province = this.fmsAdministrativeUnitRepository.findOne(exportAnimalDto.getProvince().getId());
			exportAnimal.setProvince(province);
		}
		if(exportAnimalDto.getProductTarget()!=null && exportAnimalDto.getProductTarget().getId()!=null) {
			ProductTarget pt= productTargetRepository.findOne(exportAnimalDto.getProductTarget().getId());
			exportAnimal.setProductTarget(pt);
		}
		else {
			exportAnimal.setProductTarget(null);
		}
		if(exportAnimalDto.getOriginal()!=null && exportAnimalDto.getOriginal().getId()!=null) {
			Original ori= originalRepository.findOne(exportAnimalDto.getOriginal().getId());
			exportAnimal.setOriginal(ori);
		}
		else {
			exportAnimal.setOriginal(null);
		}
		this.exportAnimalRepository.save(exportAnimal);
		exportAnimalDto.setId(exportAnimal.getId());
		return exportAnimalDto;
	}

	@Override
	public ExportAnimalDto removeExportAnimal(Long id) {
		ExportAnimalDto exportAnimalDto = null;
		if (exportAnimalRepository != null && this.exportAnimalRepository.exists(id)) {
			exportAnimalDto = new ExportAnimalDto(this.exportAnimalRepository.findOne(id));
			this.exportAnimalRepository.delete(id);

		}
		return exportAnimalDto;
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
			whereClause += " and (fa.batchCode like :namecode or fa.farm.name like :namecode or fa.animal.name like :namecode )";
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
		if(searchDto.getCityId()!=null && searchDto.getDistrictIds()!=null && searchDto.getDistrictIds().size()>0) {//đây là query theo tỉnh lấy list id của huyện
			whereClause += " and (fa.farm.administrativeUnit.parent.id in :districtIds)";
		}
		if(searchDto.getCreate_by()!=null) {
			whereClause += " and (fa.created_by like :created_by)";
		}
		if(searchDto.getCreate_date()!=null) {
			whereClause += " and (fa.create_date like :create_date)";
		}
		
		
		whereClause += " and (fa.type= :type)";
		
				
		sql +=whereClause;
		sql +=" order by fa.batchCode asc ";
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
		if(searchDto.getCityId()!=null && searchDto.getDistrictIds()!=null && searchDto.getDistrictIds().size()>0) {//đây là query theo tỉnh lấy list id của huyện
			q.setParameter("districtIds", searchDto.getDistrictIds());
			qCount.setParameter("districtIds", searchDto.getDistrictIds());
		}
		
		q.setParameter("type", searchDto.getType());
		qCount.setParameter("type", searchDto.getType());
		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<ImportExportAnimalDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;	}

}
