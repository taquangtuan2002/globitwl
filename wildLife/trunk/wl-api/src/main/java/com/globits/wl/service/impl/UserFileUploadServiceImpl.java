package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
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

import com.globits.cms.dto.SearchDto;
import com.globits.core.domain.FileDescription;
import com.globits.core.repository.FileDescriptionRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;
import com.globits.wl.domain.FarmFileAttachment;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.LiveStockProduct;
import com.globits.wl.domain.UserFileAttachment;
import com.globits.wl.domain.UserFileUpload;
import com.globits.wl.dto.DrugDto;
import com.globits.wl.dto.FarmFileAttachmentDto;
import com.globits.wl.dto.UserFileAttachmentDto;
import com.globits.wl.dto.UserFileUploadDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.UserFileAttachmentRepository;
import com.globits.wl.repository.UserFileUploadRepository;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.service.UserFileUploadService;
import com.globits.wl.utils.WLConstant;

@Service
public class UserFileUploadServiceImpl extends GenericServiceImpl<UserFileUpload, Long>
		implements UserFileUploadService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserFileUploadRepository userFileUploadRepository;
	@Autowired
	FileDescriptionRepository fileDescriptionRepository;
	@Autowired
	UserFileAttachmentRepository userFileAttachmentRepository;
	@Autowired
	FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	@Autowired
	UserAdministrativeUnitService userAdministrativeUnitService;

	@Override
	public UserFileUploadDto saveOrUpdate(UserFileUploadDto userFileUploadDto, Long id) {
		if (userFileUploadDto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				user = (User) authentication.getPrincipal();
				currentUserName = user.getUsername();
			}
			if (user == null && user.getId() == null) {
				return null;
			}
			UserFileUpload userFileUpload = null;
			if (id != null) {
				userFileUpload = userFileUploadRepository.getOne(id);
			}
			if (userFileUpload == null) {
				userFileUpload = new UserFileUpload();
				userFileUpload.setCreatedBy(currentUserName);
				userFileUpload.setCreateDate(currentDate);
			}
			userFileUpload.setModifiedBy(currentUserName);
			userFileUpload.setModifyDate(currentDate);
			userFileUpload.setUser(user);
			userFileUpload.setOrganizationName(userFileUploadDto.getOrganizationName());
			userFileUpload.setTitle(userFileUploadDto.getTitle());
			userFileUpload.setDescription(userFileUploadDto.getDescription());
			
			FmsAdministrativeUnit administrativeUnit = null;
			if (userFileUploadDto.getWard() != null) {
				administrativeUnit = fmsAdministrativeUnitRepository.findOne(userFileUploadDto.getWard().getId());
				if(administrativeUnit != null) {
					userFileUpload.setWard(administrativeUnit);
				}
			}
			if (userFileUploadDto.getDistrict() != null) {
				administrativeUnit = fmsAdministrativeUnitRepository.findOne(userFileUploadDto.getDistrict().getId());
				if(administrativeUnit != null) {
					userFileUpload.setDistrict(administrativeUnit);
				}
			}
			
			if (userFileUploadDto.getProvince() != null) {
				administrativeUnit = fmsAdministrativeUnitRepository.findOne(userFileUploadDto.getProvince().getId());
				if(administrativeUnit != null) {
					userFileUpload.setProvince(administrativeUnit);
				}
			}
			
			Set<UserFileAttachment> listUFA = new HashSet<UserFileAttachment>();
			if (userFileUploadDto.getAttachments() != null && userFileUploadDto.getAttachments().size() > 0) {
				for (UserFileAttachmentDto ufaDto : userFileUploadDto.getAttachments()) {
					if (ufaDto != null) {
						UserFileAttachment ufa = null;
						if (ufaDto.getId() != null) {
							ufa = this.userFileAttachmentRepository.findOne(ufaDto.getId());
						}
						if (ufa == null) {
							ufa = new UserFileAttachment();
							ufa.setCreateDate(currentDate);
							ufa.setCreatedBy(currentUserName);
						}

						if (ufaDto.getFile() != null) {
							ufa.setUserFileUpload(userFileUpload);
							FileDescription file = null;
							if (ufaDto.getFile().getId() != null) {
								file = this.fileDescriptionRepository.findOne(ufaDto.getFile().getId());
							}
							if (file == null) {
								file = new FileDescription();
								file.setCreateDate(currentDate);
								file.setCreatedBy(currentUserName);
							}

							ufa.setFile(file);
							ufa = this.userFileAttachmentRepository.save(ufa);
							listUFA.add(ufa);
						}

					}
				}
				if (listUFA != null && listUFA.size() > 0) {
					if (userFileUpload.getAttachments() == null) {
						userFileUpload.setAttachments(listUFA);
					} else {
						userFileUpload.getAttachments().clear();
						userFileUpload.getAttachments().addAll(listUFA);
					}
				}

			} else {// Nếu post list trống lên thì clear
				if (userFileUpload.getAttachments() != null) {
					userFileUpload.getAttachments().clear();
				}
			}
			userFileUpload = userFileUploadRepository.save(userFileUpload);
			return new UserFileUploadDto(userFileUpload);
		}
		return null;
	}

	@Override
	public UserFileUploadDto getUserFileUploadById(Long id) {
		if (id != null) {
			UserFileUploadDto result = userFileUploadRepository.getUserFileUploadById(id);
			return result;
		}
		return null;
	}

	@Override
	public Page<UserFileUploadDto> searchByPage(SearchReportPeriodDto searchDto) {
		Integer pageIndex = searchDto.getPageIndex();
		Integer pageSize = searchDto.getPageSize();
		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
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
		String text = searchDto.getText();

		String sql = "select new com.globits.wl.dto.UserFileUploadDto(entity) from UserFileUpload entity  where (1=1)";
		String sqlCount = "select count(entity.id) from UserFileUpload entity  where (1=1)";
		String whereClause = "";
		if (text != null && text.length() > 0) {
			whereClause += " and (entity.organizationName like :text or entity.description like :text or entity.title like :text )";
		}
		if (searchDto.getWardId() != null) {
			whereClause += " and (entity.ward.id =:wardId )";
		}
		if (searchDto.getDistrictId() != null) {
			whereClause += " and (entity.district.id =:districtId )";
		}
		if (searchDto.getProvinceId() != null) {
			whereClause += " and (entity.province.id =:provinceId )";
		}
		if (!isAdmin) {
			whereClause += " and (entity.ward.id in (:listWardId)) ";
		}
		sql += whereClause;
		sql += " order by entity.createDate asc ";
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, UserFileUploadDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (text != null && text.length() > 0) {
			q.setParameter("text", '%' + text + '%');
			qCount.setParameter("text", '%' + text + '%');
		}
		if (searchDto.getWardId() != null) {
			q.setParameter("wardId", searchDto.getWardId());
			qCount.setParameter("wardId", searchDto.getWardId());
		}
		if (searchDto.getDistrictId() != null) {
			q.setParameter("districtId", searchDto.getDistrictId());
			qCount.setParameter("districtId", searchDto.getDistrictId());
		}
		if (searchDto.getProvinceId() != null) {
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
		Page<UserFileUploadDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
		return page;
	}

	@Override
	public boolean deleteById(Long id) {
		if(id != null) {
			if(this.userFileUploadRepository.exists(id)) {
				this.userFileUploadRepository.delete(id);
				return true;
			}
		}
		return false;
	}

}
