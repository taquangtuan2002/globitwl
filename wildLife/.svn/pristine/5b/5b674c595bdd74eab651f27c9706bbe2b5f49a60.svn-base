package com.globits.wl.service.impl;

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

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.Original;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.service.OriginalService;

@Service
public class OriginalServiceImpl extends GenericServiceImpl<Original, Long> implements OriginalService {

	@Autowired
	private OriginalRepository originalRepository;

	@Override
	public Page<OriginalDto> getAllWidthPagination(int pageIndex, int pageSize) {
		if (pageIndex > 1)
			pageIndex--;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.originalRepository.getPageAll(pageable);
	}

	@Override
	public OriginalDto getOriginalById(Long id) {
		return this.originalRepository.getOrinalById(id);
	}

	@Override
	public OriginalDto saveOriginal(OriginalDto dto) {
		if (dto != null) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Original original = null;
			if (dto.getId() != null) {
				original = this.originalRepository.findOne(dto.getId());
			}
			if (original == null) {
				original = new Original();
				original.setCreateDate(currentDate);
				original.setCreatedBy(currentUserName);
			}
			if (dto.getName() != null) {
				original.setName(dto.getName());
			}
			if (dto.getCode() != null) {
				original.setCode(dto.getCode());
			}
			if (dto.getDescription() != null) {
				original.setDescription(dto.getDescription());
			}
			Original original2 = this.originalRepository.save(original);
			if (original2 != null) {
				dto.setId(original2.getId());
			}
			
			return dto;
		}

		return null;
	}

	@Override
	public boolean deleteOriginById(Long id) {
		if(id != null) {
			if(this.originalRepository.exists(id)) {
				this.originalRepository.delete(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteOriginByIds(Set<Long> ids) {
		if(ids != null && ids.size() >0) {
			for(Long id: ids) {
				if(id!= null) {
					this.originalRepository.delete(id);
				}
			}
			
			return true;
		}
		return false;
	}

	@Override
	public List<OriginalDto> getAll() {
		return this.originalRepository.getAllDtos();
	}

	@Override
	public Page<OriginalDto> searchDto(SearchDto searchDto, int pageIndex,
			int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.OriginalDto(fa) from Original fa  where (1=1)";
		String sqlCount = "select count(fa.id) from Original fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,OriginalDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<OriginalDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public OriginalDto checkDuplicateCode(String code) {
		OriginalDto viewCheckDuplicateCodeDto = new OriginalDto();
		Original au = null;
		List<Original> list = originalRepository.findByCode(code);
		if(list != null && list.size() > 0) {
			au = list.get(0);
		}
		if(list == null) {
			viewCheckDuplicateCodeDto.setDuplicate(false);
		}else if(list != null && list.size() > 0) {
			viewCheckDuplicateCodeDto.setDuplicate(true);
			viewCheckDuplicateCodeDto.setDupCode(au.getCode());
			viewCheckDuplicateCodeDto.setDupName(au.getName());
		}
		return viewCheckDuplicateCodeDto;
	}

	@Override
	public Original getOrinalFindById(Long id) {
		return this.originalRepository.getOrinalFindById(id);
		
	}

}
