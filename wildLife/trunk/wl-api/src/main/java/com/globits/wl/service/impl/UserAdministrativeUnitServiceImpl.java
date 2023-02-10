package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.globits.security.dto.UserDto;
import com.globits.wl.dto.UserAdministrativeUnitDto;
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
import com.globits.security.repository.UserRepository;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.domain.UserAttachments;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.FmsRegionRepository;
import com.globits.wl.repository.UserAdministrativeUnitRepository;
import com.globits.wl.repository.UserAttachmentsRepository;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.WLConstant;

@Service
public class UserAdministrativeUnitServiceImpl extends GenericServiceImpl<UserAdministrativeUnit, Long>
		implements UserAdministrativeUnitService {

	@Autowired
	private FmsAdministrativeUnitRepository administrativeUnitRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserAdministrativeUnitRepository userAdministrativeUnitRepository;
	
	@Autowired
	UserAdministrativeUnitService userAdministrativeUnitService;
	@Autowired
	UserAttachmentsRepository userAttachmentsRepository;

	@Override
	public List<UserAdministrativeUnit> saveAdministrativeUnitByUserName(
			String userName,
			List<FmsAdministrativeUnitDto> listFmsAdministrativeUnit) {
		User user = userRepository.findByUsername(userName);
		if(user!=null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			List<UserAdministrativeUnit> lst = this.findAdministrativeUnitByUserName(userName);
			if(lst!=null && lst.size()>0) {
				userAdministrativeUnitRepository.delete(lst);
			}
			for (FmsAdministrativeUnitDto trainingBaseDto : listFmsAdministrativeUnit) {
				FmsAdministrativeUnit tb = administrativeUnitRepository.findOne(trainingBaseDto.getId());
				if(tb!=null) {
					UserAdministrativeUnit ub = new UserAdministrativeUnit();
					ub.setCreatedBy(currentUserName);
					ub.setCreateDate(currentDate);
					ub.setModifiedBy(currentUserName);
					ub.setModifyDate(currentDate);						
					ub.setUser(user);
					ub.setAdminUnit(tb);
					
					this.save(ub);					
				}
			}
		}
		return null;
	}

	@Override
	public List<UserAdministrativeUnit> saveAdministrativeUnitByUserId(Long userId,	List<FmsAdministrativeUnitDto> listAdministrativeUnit) {
		User user = userRepository.findOne(userId);
	
		
		if(user!=null) {
			List<UserAttachments> userAttachments = this.userAttachmentsRepository.findUserAttachmentsByUserId(user.getId());
			if(userAttachments != null) {
				for(UserAttachments usrAtt: userAttachments) {
					usrAtt.setDisplayNameAccountUser(user.getPerson().getDisplayName());
//					usrAtt.setEmail(user.getPerson().getEmail());
					usrAtt.setEmail(user.getEmail());
					this.userAttachmentsRepository.save(usrAtt);
				}
			}
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			List<UserAdministrativeUnit> lst = this.findAdministrativeUnitByUserId(userId);
			if(lst!=null && lst.size()>0) {
				userAdministrativeUnitRepository.delete(lst);
			}	
			if(listAdministrativeUnit!=null && listAdministrativeUnit.size()>0)
			for (FmsAdministrativeUnitDto dto : listAdministrativeUnit) {
				FmsAdministrativeUnit tb = null;
				if(dto.getId()!=null){
					 tb = administrativeUnitRepository.findOne(dto.getId());
				}else if(dto.getCode()!=null){
					List<FmsAdministrativeUnit> list=administrativeUnitRepository.findListByCode(dto.getCode());
					if(list!=null && list.size()>0){
						tb=list.get(0);						
					}				
				}
				
				if(tb!=null) {
					UserAdministrativeUnit ub = new UserAdministrativeUnit();
					ub.setCreatedBy(currentUserName);
					ub.setCreateDate(currentDate);
					ub.setModifiedBy(currentUserName);
					ub.setModifyDate(currentDate);						
					ub.setUser(user);
					ub.setAdminUnit(tb);
					this.save(ub);					
				}
			}
		}
		return null;
	}

	@Override
	public List<UserAdministrativeUnit> findAdministrativeUnitByUserNameAndAdministrativeUnit(
			String userName, Long adminUnitId) {
		String sql=" SELECT u FROM UserAdministrativeUnit u WHERE u.user.username=:userName AND u.adminUnit.id=:adminUnitId ";
		Query q = manager.createQuery(sql,UserAdministrativeUnit.class);
		q.setParameter("userName", userName);
		q.setParameter("adminUnitId", adminUnitId);
		return q.getResultList();
	}

	@Override
	public List<UserAdministrativeUnit> findAdministrativeUnitByUserIdAndAdministrativeUnit(
			Long userId, Long adminUnitId) {
		String sql=" SELECT u FROM UserAdministrativeUnit u WHERE u.user.id=:userId AND u.adminUnit.id=:adminUnitId ";
		Query q = manager.createQuery(sql,UserAdministrativeUnit.class);
		q.setParameter("userId", userId);
		q.setParameter("adminUnitId", adminUnitId);
		return q.getResultList();
	}

	@Override
	public List<UserAdministrativeUnit> findAdministrativeUnitByUserName(
			String userName) {
		String sql=" SELECT u FROM UserAdministrativeUnit u WHERE u.user.username=:userName  ";
		Query q = manager.createQuery(sql,UserAdministrativeUnit.class);
		q.setParameter("userName", userName);
		return q.getResultList();	
	}

	@Override
	public List<UserAdministrativeUnit> findAdministrativeUnitByUserId(Long userId) {
		String sql=" SELECT u FROM UserAdministrativeUnit u WHERE u.user.id=:userId  ";
		Query q = manager.createQuery(sql,UserAdministrativeUnit.class);
		q.setParameter("userId", userId);
		return q.getResultList();	
	}

	@Override
	public List<UserAdministrativeUnit> getUserAdministrativeUnitByLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		 
		LocalDateTime currentDate = LocalDateTime.now();
		if (authentication != null) {
			User modifiedUser = (User) authentication.getPrincipal();
			if(modifiedUser!=null) {
				String currentUserName = modifiedUser.getUsername();
				String sql=" SELECT u FROM UserAdministrativeUnit u WHERE u.user.id=:userId  ";
				Query q = manager.createQuery(sql,UserAdministrativeUnit.class);
				q.setParameter("userId", modifiedUser.getId());
				return q.getResultList();
			}
		}
		return null;
	}

	@Override
	public List<FmsAdministrativeUnitDto> findAdministrativeUnitDtoByUserId(
			Long userId) {
		String sql=" SELECT new com.globits.wl.dto.FmsAdministrativeUnitDto(u.adminUnit) FROM UserAdministrativeUnit u WHERE u.user.id=:userId  ";
		Query q = manager.createQuery(sql,FmsAdministrativeUnitDto.class);
		q.setParameter("userId", userId);		
		return q.getResultList();		
	}

	@Override
	public List<FmsAdministrativeUnitDto> findAdministrativeUnitDtoByUserName(
			String userName) {
		String sql=" SELECT new com.globits.wl.dto.FmsAdministrativeUnitDto(u.adminUnit) FROM UserAdministrativeUnit u WHERE u.user.username=:username  ";
		Query q = manager.createQuery(sql,FmsAdministrativeUnitDto.class);
		q.setParameter("username", userName);		
		return q.getResultList();
	}

	@Override
	public List<Long> getAdministrativeUnitIdByLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		 
		LocalDateTime currentDate = LocalDateTime.now();
		if (authentication != null) {
			User modifiedUser = (User) authentication.getPrincipal();
			if(modifiedUser!=null) {
				String currentUserName = modifiedUser.getUsername();
				String sql=" SELECT u.adminUnit.id FROM UserAdministrativeUnit u WHERE u.user.id=:userId  ";
				Query q = manager.createQuery(sql);
				q.setParameter("userId", modifiedUser.getId());
				return q.getResultList();
			}
		}
		return null;
	}

	@Override
	public List<FmsAdministrativeUnit> getAdministrativeUnitByLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		 
		LocalDateTime currentDate = LocalDateTime.now();
		if (authentication != null) {
			User modifiedUser = (User) authentication.getPrincipal();
			if(modifiedUser!=null) {
				String currentUserName = modifiedUser.getUsername();
				String sql=" SELECT u.adminUnit FROM UserAdministrativeUnit u WHERE u.user.id=:userId  ";
				Query q = manager.createQuery(sql,FmsAdministrativeUnit.class);
				q.setParameter("userId", modifiedUser.getId());
				return q.getResultList();
			}
		}
		return null;
	}

	@Override
	public List<FmsAdministrativeUnitDto> getAdministrativeUnitDtoByLoginUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();		 
		LocalDateTime currentDate = LocalDateTime.now();
		if (authentication != null) {
			User modifiedUser = (User) authentication.getPrincipal();
			if(modifiedUser!=null) {
				String currentUserName = modifiedUser.getUsername();
				String sql=" SELECT new com.globits.wl.dto.FmsAdministrativeUnitDto(u.adminUnit) FROM UserAdministrativeUnit u WHERE u.user.id=:userId  ";
				Query q = manager.createQuery(sql,FmsAdministrativeUnitDto.class);
				q.setParameter("userId", modifiedUser.getId());
				return q.getResultList();
			}
		}
		return null;
	}

	@Override
	public void deleteUserAdministrativeUnit(Long userId) {
		List<UserAdministrativeUnit> lst = this.findAdministrativeUnitByUserId(userId);
		if(lst!=null && lst.size()>0) {
			userAdministrativeUnitRepository.delete(lst);
		}	
	}
	@Override
	public List<String> getListUserNameByAdministrativeUnitIds(List<Long> administrativeUnitIds){
		String sql=" SELECT u.user.username FROM UserAdministrativeUnit u WHERE u.adminUnit.id in (:administrativeUnitIds) ";
		Query q = manager.createQuery(sql,String.class);
		q.setParameter("administrativeUnitIds", administrativeUnitIds);
		return q.getResultList();
	}
	@Override
	public List<Long> getListWardIdByLoginUser(){
		List<Long> listId = this.getAdministrativeUnitIdByLoginUser();
		if(listId!=null && listId.size()>0) {
			String sql = " SELECT f.id FROM FmsAdministrativeUnit f WHERE f.parent.id is not null AND f.parent.parent.id is not null"
					+ " AND (f.parent.id in (:listId) OR f.parent.parent.id in (:listId))";
			Query q = manager.createQuery(sql,Long.class);
			q.setParameter("listId", listId);
			List<Long> ret= q.getResultList();
			ret.addAll(listId);
			return ret;
		}
		return null;
	}
	
	@Override
	public Boolean checkUserAdministrativeUnitPermission(Long administrativeUnitId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return false;
			}
			else if(!listWardId.contains(administrativeUnitId)) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}

	@Override
	public List<String> getListAdministrativeUnitCodeByUserLogin() {
		List<String> result = new ArrayList<String>();
		List<FmsAdministrativeUnitDto> listAU = getAdministrativeUnitDtoByLoginUser();
		if (listAU != null && listAU.size() > 0) {
			for (FmsAdministrativeUnitDto fmsAdministrativeUnitDto : listAU) {
				result.add(fmsAdministrativeUnitDto.getCode());
				result.addAll(administrativeUnitRepository.getAllCodeById(fmsAdministrativeUnitDto.getId()));
			}
			return result;
		}
		return null;
	}

	@Override
	public List<UserAdministrativeUnit> getListUserByAdministrativeUnitId(FmsAdministrativeUnit adminUnit) {
		List<UserAdministrativeUnit> list= userAdministrativeUnitRepository.findByAdminUnit(adminUnit.getId());
		if(list!=null && list.size()>0) {
			return list;
		}
		return null;
	}

	@Override
	public UserAdministrativeUnitDto getOneByUserId(Long userId) {
		UserAdministrativeUnit userAdministrativeUnit = userAdministrativeUnitRepository.findByUserId(userId);
		if (userAdministrativeUnit != null) {
			return new UserAdministrativeUnitDto(userAdministrativeUnit);
		} else {
			return null;
		}
	}
	
}
