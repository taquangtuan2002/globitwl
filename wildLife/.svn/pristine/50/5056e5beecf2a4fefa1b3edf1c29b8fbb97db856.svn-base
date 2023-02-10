package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.globits.wl.domain.AdministrativeOrganization;
import com.globits.wl.repository.AdministrativeOrganizationRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.cms.dto.SearchDto;
import com.globits.core.domain.FileDescription;
import com.globits.core.domain.Person;
import com.globits.core.repository.FileDescriptionRepository;
import com.globits.core.repository.PersonRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.CommonUtils;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;
import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.domain.UserAttachments;
import com.globits.wl.dto.UserAttachmentsDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.repository.UserAttachmentsRepository;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.service.UserAttachmentsService;
import com.globits.wl.utils.WLConstant;

@Service
public class UserAttachmentsServiceImpl extends GenericServiceImpl<UserAttachments, Long>
		implements UserAttachmentsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserAttachmentsRepository userAttachmentsRepository;
	@Autowired
	FileDescriptionRepository fileDescriptionRepository;
	@Autowired
	private FmsAdministrativeUnitService fmsAdministrativeUnitService;
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	@Autowired
	private AdministrativeOrganizationRepository administrativeOrganizationRepository;
	
	@Autowired
	PersonRepository personRepository;

	@Override
	public List<UserAttachmentsDto> saveList(List<UserAttachmentsDto> listUserAttachments) {
		List<UserAttachmentsDto> result = new ArrayList<UserAttachmentsDto>();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (modifiedUser == null && modifiedUser.getId() == null) {
			return null;
		}
		List<UserAttachments> lst = userAttachmentsRepository.findUserAttachmentsByUserId(modifiedUser.getId());
		if (lst != null && lst.size() > 0) {
			userAttachmentsRepository.delete(lst);
		}
		if (listUserAttachments != null && listUserAttachments.size() > 0) {
			for (UserAttachmentsDto dto : listUserAttachments) {
				UserAttachments ua = null;
				if(dto.getId() != null) {
					ua = userAttachmentsRepository.findOne(dto.getId());
				}
				if (ua == null) {
					ua = new UserAttachments();
					ua.setCreatedBy(currentUserName);
					ua.setCreateDate(currentDate);
				}
				if (dto.getFile() != null && dto.getFile().getId() != null) {
					FileDescription file = fileDescriptionRepository.getOne(dto.getFile().getId());
					if (file != null) {
						ua.setFile(file);
					}
				}
				if (dto.getAdministrativeOrganization() != null && dto.getAdministrativeOrganization().getId() != null){
					AdministrativeOrganization administrativeOrganization = administrativeOrganizationRepository.findOne(dto.getAdministrativeOrganization().getId());
					if(administrativeOrganization != null){
						ua.setAdministrativeOrganization(administrativeOrganization);
					}
				}
				
				ua.setModifiedBy(currentUserName);
				ua.setModifyDate(currentDate);

				ua.setUser(modifiedUser);
				ua.setFileName(dto.getFileName());
				ua.setOrganizationName(dto.getOrganizationName());
				ua.setPositionName(dto.getPositionName());
				ua.setPhoneNumber(dto.getPhoneNumber());
				ua.setDisplayName(dto.getDisplayName());
				ua.setEmail(dto.getEmail());
				ua.setOrganizationAddress(dto.getOrganizationAddress());
				
				ua.setNumberPhoneOffice(dto.getNumberPhoneOffice());
				ua.setEmailOffice(dto.getEmailOffice());
				
				ua.setPhoneNumberAgencyRepresentative(dto.getPhoneNumberAgencyRepresentative());
				ua.setEmailAgencyRepresentative(dto.getEmailAgencyRepresentative());
				
				ua.setDisplayNameAccountUser(dto.getDisplayNameAccountUser());
				ua.setDepartment(dto.getDepartment());
				ua.setPositionNameAccountUser(dto.getPositionNameAccountUser());
				ua.setAccountRoleLevel(dto.getAccountRoleLevel());
				
				User user = ua.getUser();
				if (user != null && CommonUtils.isPositive(user.getId(), true)) {
					user = this.userRepository.findOne(user.getId());
				}
				if(user !=null ) {
					user.setEmail(dto.getEmail());
					this.userRepository.save(user);
				}
				
				Person person = ua.getUser().getPerson();
				if (person != null && CommonUtils.isPositive(person.getId(), true)) {
					person = this.personRepository.findOne(person.getId());
				}
				if (person != null) {
					person.setDisplayName(dto.getDisplayNameAccountUser());
					person.setEmail(dto.getEmail());
					this.personRepository.save(person);
				}
				
				ua = this.save(ua);

				result.add(new UserAttachmentsDto(ua));
			}
			return result;
		}
		return null;
	}

	@Override
	public List<UserAttachmentsDto> getByUserId(Long userId) {
		if (userId != null) {
			List<UserAttachmentsDto> results = userAttachmentsRepository.getByUserId(userId);
			return results;
		}
		return null;
	}

	@Override
	public Page<UserAttachmentsDto> searchByPage(SearchDto searchDto) {
		
		Integer pageIndex = searchDto.getPageIndex();
		Integer pageSize = searchDto.getPageSize();
		
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		
		String text = searchDto.getTitle();
				
		
		String sql = "select new com.globits.wl.dto.UserAttachmentsDto(entity) from UserAttachments entity  where (1=1)";
		String sqlCount = "select count(entity.id) from UserAttachments entity  where (1=1)";
		String whereClause ="";
		if(text!=null && text.length()>0) {
			whereClause += " and (entity.user.username like :text or entity.user.person.displayName like :text)";
		}
				
		sql +=whereClause;
		sql +=" order by entity.createDate asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,UserAttachmentsDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(text!=null && text.length()>0) {
			q.setParameter("text", '%'+text+'%');
			qCount.setParameter("text", '%'+text+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<UserAttachmentsDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public Page<UserAttachmentsDto> searchByPageUniqueUser(SearchReportPeriodDto dto) {
		Integer pageIndex = dto.getPageIndex();
		Integer pageSize = dto.getPageSize();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		
		if(authentication == null) {
			return null;
		}
		User currentUser = (User) authentication.getPrincipal();
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN);
		List<Long> listAuByRole = new ArrayList<Long>();
		if(!isAdmin) {
			List<UserAdministrativeUnit> listUserAdministrativeUnit = userAdministrativeUnitService.findAdministrativeUnitByUserId(currentUser.getId());
			if(listUserAdministrativeUnit == null) {
				return null;
			}
			for (UserAdministrativeUnit userAdministrativeUnit : listUserAdministrativeUnit) {
				if(userAdministrativeUnit.getAdminUnit()!=null) {
					List<Long> ret = fmsAdministrativeUnitService.getAllAdministrativeUnitIdByParentId(userAdministrativeUnit.getAdminUnit().getId());
					if(ret!=null && ret.size()>0) {
						listAuByRole.addAll(ret);
					}
				}
			}
		}
		
		List<Long> listAuByDto = null;
		if(dto.getAdministrativeUnitId()!=null && dto.getAdministrativeUnitId()>0){
			listAuByDto=fmsAdministrativeUnitService.getAllAdministrativeUnitIdByParentId(dto.getAdministrativeUnitId());			
		}
		
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String sql = "select new com.globits.wl.dto.UserAttachmentsDto(entity, u) from UserAttachments entity right join User u on entity.user.id = u.id ";
		String sqlCount = "select count(u.id) from UserAttachments entity right join User u on entity.user.id = u.id ";
		String whereClause =" where (1=1) ";
		String joinTable = "";
		String selectIn = "";
		String whereIn = "";
		if(dto.getText()!=null && dto.getText().length()>0) {
			whereClause += " and (u.username like :text or u.person.displayName like :text)";
		}
		if(dto.getAdministrativeUnitId() != null || !isAdmin) {
			selectIn += " and u.id in (select distinct uin.user.id from UserAdministrativeUnit uin where (1=1) ";
		}
		
		if(!isAdmin) {
			whereIn += " and uin.adminUnit.id in (:listAuByRole)";
		}

		if(dto.getAdministrativeUnitId() != null) {
			whereIn += " and uin.adminUnit.id in (:listAuByDto) ";
		}	
			
		if(dto.getRoleId() != null) {
			joinTable += " inner join u.roles r ON r.id=:roleId ";
		}
		
		if(!isAdmin || dto.getAdministrativeUnitId()  !=null)
		{
			selectIn += whereIn + ")";
		}
		else {
			selectIn += whereIn;
		}
		

		sql += joinTable + whereClause + selectIn;
		sql +=" order by u.createDate asc ";
		sqlCount+= joinTable + whereClause + selectIn;

		Query q = manager.createQuery(sql,UserAttachmentsDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		if(dto.getRoleId() != null) {
			q.setParameter("roleId", dto.getRoleId());
			qCount.setParameter("roleId", dto.getRoleId());
		}
		if(dto.getText() !=null && dto.getText().length()>0) {
			q.setParameter("text", '%'+dto.getText()+'%');
			qCount.setParameter("text", '%'+dto.getText()+'%');
		}		
		if(!isAdmin) {
			q.setParameter("listAuByRole", listAuByRole);
			qCount.setParameter("listAuByRole", listAuByRole);
		}
		if(dto.getAdministrativeUnitId() != null) {
			q.setParameter("listAuByDto", listAuByDto);
			qCount.setParameter("listAuByDto", listAuByDto);
		}
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		List<UserAttachmentsDto> list = q.getResultList();
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<UserAttachmentsDto> page = new PageImpl<>(list, pageable,numberResult);		
		return page;
	
	}

}
