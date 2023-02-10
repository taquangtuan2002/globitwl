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
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.AdministrativeUnitEditable;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.dto.AdministrativeUnitEditableDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.repository.AdministrativeUnitEditableRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.service.AdministrativeUnitEditableService;

import java.util.List;

@Service
public class AdministrativeUnitEditableServiceImple extends GenericServiceImpl<AdministrativeUnitEditable, Long> 
	implements AdministrativeUnitEditableService {
	
	@Autowired
	private AdministrativeUnitEditableRepository administrativeUnitEditableRepository;
	
	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	

	@SuppressWarnings("unchecked")
	@Override
	public Page<AdministrativeUnitEditableDto> findPage(AnimalReportDataSearchDto dto) {
		if(dto!=null) {
			String sql = "select new com.globits.wl.dto.AdministrativeUnitEditableDto(s) FROM AdministrativeUnitEditable s ";
			String whereClause = " where 1=1 ";
			String orderBy = " order by s.editable desc ";
			String sqlCount = "select count(*) FROM AdministrativeUnitEditable s ";
			if(dto.getProvinceId()!=null) {
				whereClause += " AND s.adminUnit.id =:provinceId ";
			}
			if(dto.getEditable()!=null) {
				whereClause += " AND s.editable =:editable ";
			}
			if(dto.getText()!=null) {
				whereClause += " AND (s.adminUnit.name LIKE :text OR s.editable LIKE :text OR s.quater LIKE :text OR s.year LIKE :text)";
			}
			Query query = manager.createQuery(sql + whereClause + orderBy, AdministrativeUnitEditableDto.class);
			Query queryCount = manager.createQuery(sqlCount + whereClause, Long.class);
			if(dto.getProvinceId()!=null) {
				query.setParameter("provinceId", dto.getProvinceId());
				queryCount.setParameter("provinceId", dto.getProvinceId());
			}
			if(dto.getEditable()!=null) {
				query.setParameter("editable", dto.getEditable());
				queryCount.setParameter("editable", dto.getEditable());
			}
			if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
				query.setParameter("text", '%' + dto.getText().trim() + '%');
				queryCount.setParameter("text", '%' + dto.getText().trim() + '%');
			}
			if(dto.getPageIndex()==null) {
				dto.setPageIndex(0);
			}else {
				if(dto.getPageIndex()>0) {
					dto.setPageIndex(dto.getPageIndex()-1);
				}else {
					dto.setPageIndex(0);
				}
			}
			if(dto.getPageSize()==null || dto.getPageSize()<=0) {
				dto.setPageSize(25);
			}
			
			Long total = 0L;
			Object obj = queryCount.getSingleResult();
			if (obj != null) {
				total = (Long) obj;
			}
			
			query.setFirstResult(dto.getPageIndex() * dto.getPageSize());
			query.setMaxResults(dto.getPageSize());
			Pageable pageable = new PageRequest(dto.getPageIndex(), dto.getPageSize());
			Page<AdministrativeUnitEditableDto> page = new PageImpl<AdministrativeUnitEditableDto>(query.getResultList(), pageable,total);
			return page;
		}
		return null;
	}

	@Override
	public AdministrativeUnitEditableDto saveOrUpdate(AdministrativeUnitEditableDto dto) {
		if(dto!=null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			AdministrativeUnitEditable entity =null;
			if(dto.getId()!=null) {
				entity = administrativeUnitEditableRepository.findOne(dto.getId());
			}
			if(entity==null) {
				entity = new AdministrativeUnitEditable();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			}else {
				if (!entity.getCreatedBy().equals(currentUserName)) {
					return null;
				}
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);
			}
			if(dto.getListSelectedAdminUnit()!=null&&dto.getListSelectedAdminUnit().size()!=0) {
				for(int i=0; i<dto.getListSelectedAdminUnit().size();i++) {
					entity = new AdministrativeUnitEditable();
					entity.setCreateDate(currentDate);
					entity.setCreatedBy(currentUserName);
					FmsAdministrativeUnit au = null;
					au = fmsAdministrativeUnitRepository.findOne(dto.getListSelectedAdminUnit().get(i).getId());
					entity.setAdminUnit(au);
					entity.setRoles(dto.getRoles());
					entity.setEditable(dto.getEditable());
					entity.setYear(dto.getYear());
					entity.setQuater(dto.getQuater());
					
					entity = administrativeUnitEditableRepository.save(entity);
				}
				return new AdministrativeUnitEditableDto(entity);
			}else {
				FmsAdministrativeUnit au = null;
				if(dto.getAdminUnit()!=null && dto.getAdminUnit().getId()!=null) {
					au = fmsAdministrativeUnitRepository.findOne(dto.getAdminUnit().getId());
					entity.setAdminUnit(au);
				}
				entity.setRoles(dto.getRoles());
				entity.setEditable(dto.getEditable());
				entity.setYear(dto.getYear());
				entity.setQuater(dto.getQuater());
				
				entity = administrativeUnitEditableRepository.save(entity);
				if(entity!=null) {
					return new AdministrativeUnitEditableDto(entity);
				}else {
					return null;
				}
			}
		}
		return null;
	}

	@Override
	public AdministrativeUnitEditableDto getAdministrativeUnitEditableById(Long id) {
		// TODO Auto-generated method stub
		if (id != null) {
			AdministrativeUnitEditableDto result = administrativeUnitEditableRepository.getAdministrativeUnitEditableById(id);
			return result;
		}
		return null;
	}

	@Override
	public List<AdministrativeUnitEditableDto> getAdministrativeUnitEditableByAdminUnit(Long id) {
		// TODO Auto-generated method stub
		if (id != null) {
			List<AdministrativeUnitEditableDto> result = administrativeUnitEditableRepository.getAdministrativeUnitEditableByAdminUnit(id);
			return result;
		}
		return null;
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		if(id != null) {
			if(this.administrativeUnitEditableRepository.exists(id)) {
				this.administrativeUnitEditableRepository.delete(id);
				return true;
			}
		}
		return false;
	}

}
