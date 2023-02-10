package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;

import com.globits.cms.dto.SearchDto;
import com.globits.wl.dto.UserAttachmentsDto;
import com.globits.wl.dto.functiondto.OrganizationSearchDto;
import org.apache.commons.lang.StringUtils;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.ArrayType;
import org.hibernate.type.StandardBasicTypeTemplate;
import org.hibernate.type.StandardBasicTypes;
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
import com.globits.wl.domain.AdministrativeOrganization;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.FmsOrganizationAdministrative;
import com.globits.wl.dto.AdministrativeOrganizationDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.FmsOrganizationAdministrativeDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.repository.AdministrativeOrganizationRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.service.AdministrativeOrganizationService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.WLConstant;
import org.springframework.util.CollectionUtils;

@Service
public class AdministrativeOrganizationServiceImpl extends GenericServiceImpl<AdministrativeOrganization, Long> implements AdministrativeOrganizationService {

	@Autowired
	private AdministrativeOrganizationRepository administrativeOrganizationRepos;
	
	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepos;

	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	
	@Override
	public List<AdministrativeOrganizationDto> getAll() {
//		List<AdministrativeOrganizationDto> dtos = this.administrativeOrganizationRepos.getAll();
//		return dtos;
		return null;
	}

	@Override
	public AdministrativeOrganizationDto getById(Long id) {
		AdministrativeOrganization entity = null;
		if(id != null) {
			entity = this.administrativeOrganizationRepos.findOne(id);
			if(entity != null) {
				return new AdministrativeOrganizationDto(entity);
			}
		}
		return null;
	}

	@Override
	public AdministrativeOrganizationDto saveOrUpdate(AdministrativeOrganizationDto dto, Long id) {
		AdministrativeOrganization entity = null;
		Boolean isNew = true;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		// Edit
		if(id != null) {
			isNew = false;
			entity = this.administrativeOrganizationRepos.findOne(id);
		} else if(dto.getId() != null) {
			isNew = false;
			entity = this.administrativeOrganizationRepos.findOne(dto.getId());
		}
		// Thêm mới
		if(entity == null) {
			entity = new AdministrativeOrganization();
			entity.setCreateDate(currentDate);
			entity.setCreatedBy(currentUserName);
		}
		if(dto.getOrganizationLevel() != null) {
			entity.setOrganizationLevel(dto.getOrganizationLevel());
		}
		if(dto.getName() != null) {
			entity.setName(dto.getName());
		}
		if(dto.getAbbreviations() != null) {
			entity.setAbbreviations(dto.getAbbreviations());
		}
		if(dto.getAddress() != null) {
			entity.setAddress(dto.getAddress());
		}
		if(dto.getGovernmentLevel() != null) {
			entity.setGovernmentLevel(dto.getGovernmentLevel());
		}
		if(dto.getOrganizationalForm() != null) {
			entity.setOrganizationalForm(dto.getOrganizationalForm());
		}
		if(dto.getEmail() != null) {
			entity.setEmail(dto.getEmail());
		}
		if(dto.getPhoneNumber() != null) {
			entity.setPhoneNumber(dto.getPhoneNumber());
		}
		if(dto.getFax() != null) {
			entity.setFax(dto.getFax());
		}
		if(dto.getDescription() != null) {
			entity.setDescription(dto.getDescription());
		}
		if(dto.getWebsite() != null) {
			entity.setWebsite(dto.getWebsite());
		}
		if(dto.getNumbericalOrder() != null) {
			entity.setNumbericalOrder(dto.getNumbericalOrder());
		}
		if(dto.getDisplayName() != null) {
			entity.setDisplayName(dto.getDisplayName());
		}
		if(dto.getPositionName() != null) {
			entity.setPositionName(dto.getPositionName());
		}
		if(dto.getPhoneNumberAgencyRepresentative() != null) {
			entity.setPhoneNumberAgencyRepresentative(dto.getPhoneNumberAgencyRepresentative());
		}
		if(dto.getEmailAgencyRepresentative() != null) {
			entity.setEmailAgencyRepresentative(dto.getEmailAgencyRepresentative());
		}
		// Parent
		if(dto.getParentDto() != null && dto.getParentDto().getId() != null) {
			AdministrativeOrganization parent = null;
			parent = this.administrativeOrganizationRepos.findOne(dto.getParentDto().getId());
			if(parent != null) {
				entity.setParent(parent);
			}
		} else {
			entity.setParent(null);
		}
		// Map bảng trung gian
		if(dto.getFmsOrganiration() != null && dto.getFmsOrganiration().size() > 0) {
			HashSet<FmsOrganizationAdministrative> fmsOrganizations = new HashSet<>();
			for(FmsAdministrativeUnitDto item : dto.getFmsOrganiration()) {
				FmsOrganizationAdministrative fmsOrganization = new FmsOrganizationAdministrative();
				FmsAdministrativeUnit fmsAdministrative = null;
				if(item != null) {
					if(item.getId() != null) {
						fmsAdministrative = this.fmsAdministrativeUnitRepos.findOne(item.getId());
					}
				}
				fmsOrganization.setAdministrativeOrganization(entity);
				if(fmsAdministrative != null) {
					fmsOrganization.setFmsAdministrativeUnit(fmsAdministrative);
				}
				fmsOrganizations.add(fmsOrganization);
			}
			if(entity.getFmsOrganization() != null) {
				// Trường hợp có dl
				entity.getFmsOrganization().clear();
				entity.getFmsOrganization().addAll(fmsOrganizations);
			} else {
				// Chưa có thì thêm vào
				entity.setFmsOrganization(fmsOrganizations);
			}
		} else if (dto.getFmsOrganiration() != null){
			if(entity.getFmsOrganization() != null) {
				entity.getFmsOrganization().clear();
			}
		}
		
		this.administrativeOrganizationRepos.save(entity);
		dto.setId(entity.getId());
		return dto;
	}

	@Override
	public AdministrativeOrganizationDto deleteAdministrativeOrganization(Long id) {
		AdministrativeOrganizationDto aoDto = null;
		if(administrativeOrganizationRepos != null && this.administrativeOrganizationRepos.exists(id)) {
			aoDto = new AdministrativeOrganizationDto(this.administrativeOrganizationRepos.findOne(id));
			this.administrativeOrganizationRepos.delete(id);
		}
		return aoDto;
	}
	
	@Override
	public List<FmsOrganizationAdministrativeDto> getParent(SearchReportPeriodDto searchDto) {
		// Lấy tỉnh theo user login
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if (!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if (listWardId == null || listWardId.size() == 0) {
				return null;
			}
		}
		if(searchDto != null) {
			String sql = "Select new com.globits.wl.dto.FmsOrganizationAdministrativeDto(entity) "
					   + " From FmsOrganizationAdministrative entity where (1=1) ";
//					   + " and entity.administrativeOrganization.parent is null ";
			String whereClause = "";
			if(searchDto.getWardId() != null) {
				whereClause += " AND (entity.fmsAdministrativeUnit.id =:wardId )";
			} else if(searchDto.getDistrictId() != null) {
				whereClause += " AND (entity.fmsAdministrativeUnit.parent.id =:districtId )";
			} else if(searchDto.getProvinceId() != null) {
				whereClause += " AND (entity.fmsAdministrativeUnit.parent.parent.id =:provinceId )";
			}
			if (!isAdmin) {
				whereClause += " AND (entity.fmsAdministrativeUnit.id in (:listWardId)) ";
			}
			sql += whereClause;
			
			Query q = manager.createQuery(sql, FmsOrganizationAdministrativeDto.class);
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
			}
			if(!isAdmin) {
				q.setParameter("listWardId", listWardId);
			} 
			List<FmsOrganizationAdministrativeDto> result = q.getResultList();
			if(result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<AdministrativeOrganizationDto> getChildrenByParentId(Long parentId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> administrativeId = null;
		if (!isAdmin) {
			administrativeId = this.getAllAdminOrgIdParentByLoginUser();
			if (CollectionUtils.isEmpty(administrativeId)) {
				return null;
			}
		}
//		String sql = "Select new com.globits.wl.dto.AdministrativeOrganizationDto(foa.administrativeOrganization) From FmsOrganizationAdministrative foa Where (1=1) AND "
////				+ " foa.administrativeOrganization.parent is null"
//				+ " foa.administrativeOrganization.parent.id = :parentId" ;
		String sql =" select distinct new com.globits.wl.dto.AdministrativeOrganizationDto(entity)" +
				" from AdministrativeOrganization entity  where 1=1 and entity.parent.id = :parentId ";
		String whereClause = "";
		
		if (!isAdmin) { 
			whereClause += " AND (entity.id in (:administrativeId)) ";
		}
		sql += whereClause;
		
		Query q = manager.createQuery(sql, AdministrativeOrganizationDto.class);
		
		q.setParameter("parentId", parentId);
		if (!isAdmin) {
			q.setParameter("administrativeId", administrativeId);
		}

		List<AdministrativeOrganizationDto> dtos = q.getResultList();
		return dtos;
	}

	public List<AdministrativeOrganizationDto> getAdministrativeOrganizationByUser() {
		List<Long> id = new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		if (currentUser.getId() != null) {
			List<FmsAdministrativeUnitDto> fmsAdministrativeUnitDtos = userAdministrativeUnitService.findAdministrativeUnitDtoByUserId(currentUser.getId());
			for (FmsAdministrativeUnitDto administrativeUnitDto : fmsAdministrativeUnitDtos) {
				if (administrativeUnitDto != null) {
					id.add(administrativeUnitDto.getId());
				}
			}
		}
		// admin lay ra trung uong 
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		if(isAdmin) {
			String sql = "select new com.globits.wl.dto.AdministrativeOrganizationDto(ao,true) from AdministrativeOrganization ao "
					+ "Where (1=1) AND "
					+ " ao.parent is null";
			Query q = manager.createQuery(sql, AdministrativeOrganizationDto.class);
			List<AdministrativeOrganizationDto> entitys = new ArrayList<AdministrativeOrganizationDto>();
			if(q.getResultList() != null && q.getResultList().size() > 0) {
				entitys = q.getResultList(); 
			}
			return entitys;
		}else {
			if (id != null && id.size() > 0) {
				String sql = "select new com.globits.wl.dto.AdministrativeOrganizationDto(entity) from AdministrativeOrganization entity  where entity.id " +
						"in(select fms.administrativeOrganization.id from FmsOrganizationAdministrative as fms where fms.fmsAdministrativeUnit.id in( ";
				String whereClause = " ";
					whereClause += StringUtils.join(id, ',');
					whereClause += "))";
				sql += whereClause;
				sql += " order by entity.createDate asc ";

				Query q = manager.createQuery(sql, AdministrativeOrganizationDto.class);

				List<AdministrativeOrganizationDto> administrativeOrganizationDtos = q.getResultList();
				return administrativeOrganizationDtos;
				} else {
					return null;
				}

			}
		}
		

	@Override
	public Page<AdministrativeOrganizationDto> getListTree2(SearchReportPeriodDto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if (!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if (listWardId == null || listWardId.size() == 0) {
				return null;
			}
		}
		if(searchDto != null && searchDto.getPageIndex() != null && searchDto.getPageSize() != null) {
			int pageIndex = searchDto.getPageIndex();
			int pageSize = searchDto.getPageSize();
			if(pageIndex > 0) {
				pageIndex = pageIndex - 1;
			} else {
				pageIndex = 0;
			}
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			String sql = "Select (foa.administrativeOrganization) From FmsOrganizationAdministrative foa Where (1=1) AND "
					+ " foa.administrativeOrganization.parent is null";
			String sqlCount = "Select COUNT(*) From FmsOrganizationAdministrative foa Where (1=1) AND "
					+ " foa.administrativeOrganization.parent is null";
			String whereClause = "";
			if(searchDto.getText() != null) {
				whereClause += " AND foa.administrativeOrganization.name Like :text";
			}
			if (searchDto.getWardId() != null) {
				whereClause += " and (foa.fmsAdministrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (foa.fmsAdministrativeUnit.parent.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (foa.fmsAdministrativeUnit.parent.parent.id =:provinceId )";
			}
			if (!isAdmin) {
				whereClause += " and (foa.fmsAdministrativeUnit.id in (:listWardId)) ";
			}
			sql += whereClause;
			sqlCount += whereClause;
			
			Query q = manager.createQuery(sql, AdministrativeOrganization.class);
			Query qCount = manager.createQuery(sqlCount);
			if(searchDto.getText() != null) {
				q.setParameter("text", "%" + searchDto.getText() + "%");
				qCount.setParameter("text", "%" + searchDto.getText() + "%");
			}
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
				qCount.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
				qCount.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
				qCount.setParameter("provinceId", searchDto.getProvinceId());
			}
			if (!isAdmin) {
				q.setParameter("listWardId", listWardId);
				qCount.setParameter("listWardId", listWardId);
			}
			q.setFirstResult((pageIndex) * pageSize);
			q.setMaxResults(pageSize);
			Long numberResult = (Long) qCount.getSingleResult();
			List<AdministrativeOrganization> entitys = new ArrayList<AdministrativeOrganization>();
			if(q.getResultList() != null && q.getResultList().size() > 0) {
				entitys = q.getResultList();
			}
			List<AdministrativeOrganizationDto> dtos = new ArrayList<AdministrativeOrganizationDto>();
			for(AdministrativeOrganization entity : entitys) {
				dtos.add(new AdministrativeOrganizationDto(entity));
			}
			Page<AdministrativeOrganizationDto> resultPage = new PageImpl<AdministrativeOrganizationDto>(dtos, pageable, numberResult);
			return resultPage;
		}
		return null;
	}
	@Override
	public Page<AdministrativeOrganizationDto> getListTree(SearchReportPeriodDto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		boolean isProvince = SecurityUtils.isUserInRole(currentUser,WLConstant.ROLE_SDAH);
		List<Long> administrativeId = null;
		if (!isAdmin) {
			if(isProvince){
				administrativeId = getAdminOrgIdParentByLoginUser();
				if (CollectionUtils.isEmpty(administrativeId)) {
					return null;
				}
			}else{
				return null;
			}
		}
		if(searchDto != null && searchDto.getPageIndex() != null && searchDto.getPageSize() != null) {
			int pageIndex = searchDto.getPageIndex();
			int pageSize = searchDto.getPageSize();
			if(pageIndex > 0) {
				pageIndex = pageIndex - 1;
			} else {
				pageIndex = 0;
			}
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			String sql = "select new com.globits.wl.dto.AdministrativeOrganizationDto(ao,true) from AdministrativeOrganization ao "
					+ "Where (1=1) ";
			String sqlCount = "select count(*) from AdministrativeOrganization ao Where (1=1) ";
			String whereClause = "";
			if(searchDto.getText() != null) {
				whereClause += " AND ao.name Like :text";
			}
			if (!isAdmin) {
				whereClause += " and ao.id in (:administrativeId) ";
			}else{
				whereClause += " and ao.parent is null ";
			}
			sql += whereClause;
			sqlCount += whereClause;
			
			Query q = manager.createQuery(sql, AdministrativeOrganizationDto.class);
			Query qCount = manager.createQuery(sqlCount);
			if(searchDto.getText() != null) {
				q.setParameter("text", "%" + searchDto.getText() + "%");
				qCount.setParameter("text", "%" + searchDto.getText() + "%");
			}
			if (!isAdmin) {
				q.setParameter("administrativeId", administrativeId);
				qCount.setParameter("administrativeId", administrativeId);
			}
			q.setFirstResult((pageIndex) * pageSize);
			q.setMaxResults(pageSize);
			Long numberResult = (Long) qCount.getSingleResult();
			List<AdministrativeOrganizationDto> entitys = new ArrayList<AdministrativeOrganizationDto>();
			if(q.getResultList() != null && q.getResultList().size() > 0) {
				entitys = q.getResultList();
			}
//			List<AdministrativeOrganizationDto> dtos = new ArrayList<AdministrativeOrganizationDto>();
//			for(AdministrativeOrganizationDto entity : entitys) {
//				dtos.add(entity);
//			}
			Page<AdministrativeOrganizationDto> resultPage = new PageImpl<>(entitys, pageable, numberResult);
			return resultPage;
		}
		return null;
	}

	@Override
	public List<AdministrativeOrganizationDto> getOrganization(OrganizationSearchDto search) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		boolean isProvince = SecurityUtils.isUserInRole(currentUser,WLConstant.ROLE_SDAH);
		List<Long> administrativeId = null;
		if (!isAdmin) {
			if(isProvince){
				administrativeId = getAllAdminOrgIdSelectByLoginUser();
				if (CollectionUtils.isEmpty(administrativeId)) {
					return null;
				}
			}else{
				return null;
			}
		}
		TypedParameterValue currentOrgId = new TypedParameterValue(StandardBasicTypes.LONG,search.getCurrentOrgId());
		if(!isAdmin){
			return administrativeOrganizationRepos.getOrganization(currentOrgId,administrativeId);
		}
		return administrativeOrganizationRepos.getOrganization(currentOrgId);
	}

	private List<Long> getAllAdminOrgIdParentByLoginUser(){
		List<Long> administrativeId = userAdministrativeUnitService.getAdministrativeUnitIdByLoginUser();
		List<Long> rs = new ArrayList<>();
		if(!CollectionUtils.isEmpty(administrativeId)){
			String sql =" select foa.administrativeOrganization.id from FmsOrganizationAdministrative foa where  " ;

			String whereP = "  foa.fmsAdministrativeUnit.id in :administrativeId " ;
			String whereD = "  foa.fmsAdministrativeUnit.parent.id in :administrativeId " ;
			String whereW = "  foa.fmsAdministrativeUnit.parent.parent.id in :administrativeId " ;
			Query q = manager.createQuery(sql+whereP);
			q.setParameter("administrativeId", administrativeId);
			List<Long> ids = q.getResultList();
			q = manager.createQuery(sql+whereD);
			q.setParameter("administrativeId", administrativeId);
			ids.addAll(q.getResultList());
			q = manager.createQuery(sql+whereW);
			q.setParameter("administrativeId", administrativeId);
			ids.addAll(q.getResultList());
			String sqlParent = " select distinct entity.parent.id from AdministrativeOrganization entity where " +
					" entity.parent is not null and entity.id in (:ids) ";
			while (!CollectionUtils.isEmpty(ids)){
				rs.addAll(ids);
				q = manager.createQuery(sqlParent);
				q.setParameter("ids", ids);
				ids = q.getResultList();
			}
		}
		return rs;
	}

	private List<Long> getAdminOrgIdParentByLoginUser() {
		List<Long> administrativeId = userAdministrativeUnitService.getAdministrativeUnitIdByLoginUser();
		List<Long> rs = new ArrayList<>();
		if (!CollectionUtils.isEmpty(administrativeId)) {
			String sql = " select foa.administrativeOrganization.id from FmsOrganizationAdministrative foa where  ";
			String whereP = "  foa.fmsAdministrativeUnit.id in :administrativeId ";
			Query q = manager.createQuery(sql + whereP);
			q.setParameter("administrativeId", administrativeId);
			List<Long> ids = q.getResultList();
			if(!CollectionUtils.isEmpty(ids)) {
				String sqlParent = " select distinct entity.parent.id from AdministrativeOrganization entity where " +
						" entity.parent is not null and entity.id in (:ids) ";
				q = manager.createQuery(sqlParent);
				q.setParameter("ids", ids);
				ids = q.getResultList();
				rs.addAll(ids);
			}
		}
		return rs;
	}

	private List<Long> getAllAdminOrgIdSelectByLoginUser(){
		List<Long> administrativeId = userAdministrativeUnitService.getAdministrativeUnitIdByLoginUser();
		List<Long> rs = new ArrayList<>();
		if(!CollectionUtils.isEmpty(administrativeId)){
			String sql =" select foa.administrativeOrganization.id from FmsOrganizationAdministrative foa where  " ;

			String whereP = "  foa.fmsAdministrativeUnit.id in :administrativeId " ;
			String whereD = "  foa.fmsAdministrativeUnit.parent.id in :administrativeId " ;
			String whereW = "  foa.fmsAdministrativeUnit.parent.parent.id in :administrativeId " ;
			Query q = manager.createQuery(sql+whereP);
			q.setParameter("administrativeId", administrativeId);
			List<Long> ids = q.getResultList();
			q = manager.createQuery(sql+whereD);
			q.setParameter("administrativeId", administrativeId);
			ids.addAll(q.getResultList());
			q = manager.createQuery(sql+whereW);
			q.setParameter("administrativeId", administrativeId);
			ids.addAll(q.getResultList());
			if(!CollectionUtils.isEmpty(ids)) {
				String sqlParent = " select distinct entity.parent.id from AdministrativeOrganization entity where " +
						" entity.parent is not null and entity.id in (:ids) ";
				q = manager.createQuery(sqlParent);
				q.setParameter("ids", ids);
				ids.addAll(q.getResultList());
				rs.addAll(ids);
			}
		}
		return rs;
	}
}
