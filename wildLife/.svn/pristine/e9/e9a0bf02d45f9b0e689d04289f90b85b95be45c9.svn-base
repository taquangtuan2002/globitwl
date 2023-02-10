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
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.InjectionTime;
import com.globits.wl.domain.Original;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.InjectionTimeDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.InjectionTimeRepository;
import com.globits.wl.service.InjectionTimeService;

@Service
public class InjectionTimeServiceImpl extends GenericServiceImpl<InjectionTime, Long> implements InjectionTimeService {
	@Autowired
	private InjectionTimeRepository InjectionTimeRepository;

	@Override
	public Page<InjectionTimeDto> getPageDto(int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
		return this.InjectionTimeRepository.getPageDto(pageable);
	}

	@Override
	public List<InjectionTimeDto> getAllDto() {
		return InjectionTimeRepository.getAllDto();
	}

	@Override
	public InjectionTimeDto getInjectionTimeById(Long id) {
		if (id != null) {
			InjectionTime InjectionTime = null;
			InjectionTime = this.InjectionTimeRepository.findOne(id);
			if (InjectionTime != null) {
				return new InjectionTimeDto(InjectionTime);
			}
		}
		return null;
	}

	@Override
	public InjectionTimeDto deleteById(Long id) {
		if (id != null) {
			InjectionTimeDto InjectionTimeDto = null;
			InjectionTime InjectionTime = new InjectionTime();
			InjectionTime = this.InjectionTimeRepository.findOne(id);
			if (InjectionTime != null) {
				InjectionTimeDto = new InjectionTimeDto(InjectionTime);
				this.InjectionTimeRepository.delete(id);
				return InjectionTimeDto;
			}
		}
		return null;
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {

		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				this.InjectionTimeRepository.delete(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public InjectionTimeDto createInjectionTime(InjectionTimeDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			InjectionTime injectionTime = null;
			if (dto.getId() != null) {
				injectionTime = this.InjectionTimeRepository.findOne(dto.getId());
			}

			if (injectionTime == null) {
				injectionTime = new InjectionTime();
				injectionTime.setCreateDate(currentDate);
				injectionTime.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				injectionTime.setCode(dto.getCode());
			}
			if (dto.getName() != null) {
				injectionTime.setName(dto.getName());
			}
			if (dto.getType() != 0) {
				injectionTime.setType(dto.getType());
			}

			InjectionTime injectionTime2 = this.InjectionTimeRepository.save(injectionTime);

			dto.setId(injectionTime2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public InjectionTimeDto updateInjectionTime(Long id, InjectionTimeDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			InjectionTime injectionTime = null;
			if (id != null) {
				injectionTime = this.InjectionTimeRepository.findOne(id);
			}
			else if (dto.getId() != null) {
				injectionTime = this.InjectionTimeRepository.findOne(dto.getId());
			}

			if (injectionTime == null) {
				injectionTime = new InjectionTime();
				injectionTime.setCreateDate(currentDate);
				injectionTime.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				injectionTime.setCode(dto.getCode());
			}
			if (dto.getName() != null) {
				injectionTime.setName(dto.getName());
			}
			if (dto.getType() != 0) {
				injectionTime.setType(dto.getType());
			}

			InjectionTime injectionTime2 = this.InjectionTimeRepository.save(injectionTime);

			dto.setId(injectionTime2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public Page<InjectionTimeDto> searchInjectionTimeDto(SearchDto searchDto,
			int pageIndex, int pageSize) {
		
			if(pageIndex > 0) pageIndex = pageIndex-1;
			else pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			
			
			String namecode = searchDto.getNameOrCode();
					
			
			String sql = "select new com.globits.wl.dto.InjectionTimeDto(fa) from InjectionTime fa  where (1=1)";
			String sqlCount = "select count(fa.id) from InjectionTime fa  where (1=1)";
			String whereClause ="";
			if(namecode!=null && namecode.length()>0) {
				whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
			}
					
			sql +=whereClause;
			sql +=" order by fa.code asc ";
			sqlCount+=whereClause;

			Query q = manager.createQuery(sql,InjectionTimeDto.class);
			Query qCount = manager.createQuery(sqlCount);
			
			
			if(namecode!=null && namecode.length()>0) {
				q.setParameter("namecode", '%'+namecode+'%');
				qCount.setParameter("namecode", '%'+namecode+'%');
			}		
			
			q.setFirstResult((pageIndex)*pageSize);
			q.setMaxResults(pageSize);
			
			Long numberResult =(Long)qCount.getSingleResult();
			Page<InjectionTimeDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
			return page;
	}

	@Override
	public InjectionTimeDto checkDuplicateCode(String code) {		
		InjectionTimeDto viewCheckDuplicateCodeDto = new InjectionTimeDto();
		InjectionTime au = null;
		List<InjectionTime> list = InjectionTimeRepository.findByCode(code);
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

}
