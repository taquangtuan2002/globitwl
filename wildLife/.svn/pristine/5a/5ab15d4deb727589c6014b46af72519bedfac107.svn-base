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
import com.globits.wl.domain.FmsRegion;
import com.globits.wl.domain.SeedLevel;
import com.globits.wl.domain.WaterSource;
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.SeedLevelDto;
import com.globits.wl.dto.WaterSourceDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.WaterSourceSearchDto;
import com.globits.wl.repository.SeedLevelRepository;
import com.globits.wl.repository.WaterSourceRepository;
import com.globits.wl.service.SeedLevelService;
import com.globits.wl.service.WaterSourceService;

@Service
public class SeedLevelServiceImpl extends GenericServiceImpl<SeedLevel, Long> implements SeedLevelService {

	@Autowired
	private SeedLevelRepository seedLevelRepository;

	@Override
	public Page<SeedLevelDto> getListSeedLevels(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return seedLevelRepository.getListWaterSources(pageable);
	}

	@Override
	public List<SeedLevelDto> getAll() {
		// TODO Auto-generated method stub
		return this.seedLevelRepository.getAll();
	}

	@Override
	public SeedLevelDto seedLevelById(Long id) {
		return this.seedLevelRepository.seedLevelById(id);
	}

	@Override
	public SeedLevelDto saveSeedLevel(SeedLevelDto seedLevelDto, Long id) {
		SeedLevel seedLevel = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			seedLevel = this.seedLevelRepository.findOne(id);
		} else if (seedLevelDto.getId() != null) {
			seedLevel = this.seedLevelRepository.findOne(seedLevelDto.getId());
		}

		if (seedLevel == null) {// trường hợp thêm mới
			seedLevel = new SeedLevel();
			seedLevel.setCreateDate(currentDate);
			seedLevel.setCreatedBy(currentUserName);
		}

		if (seedLevelDto.getName() != null)
			seedLevel.setName(seedLevelDto.getName());

		if (seedLevelDto.getCode() != null)
			seedLevel.setCode(seedLevelDto.getCode());
		
		if (seedLevelDto.getLevel() != 0)
			seedLevel.setLevel(seedLevelDto.getLevel());

		seedLevel = this.seedLevelRepository.save(seedLevel);
		seedLevelDto.setId(seedLevel.getId());
		return seedLevelDto;
	}

	@Override
	public SeedLevelDto removeSeedLevel(Long id) {
		SeedLevelDto SeedLevelDto = null;
		if (seedLevelRepository != null && this.seedLevelRepository.exists(id)) {
			SeedLevelDto = new SeedLevelDto(this.seedLevelRepository.findOne(id));
			this.seedLevelRepository.delete(id);
		}
		return SeedLevelDto;
	}

	@Override
	public Page<SeedLevelDto> searchSeedLevelBySearchDto(SearchDto searchDto, int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.SeedLevelDto(s) from SeedLevel s  where (1=1)";
		String sqlCount = "select count(s.id) from SeedLevel s where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (s.code like :namecode or s.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by s.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,SeedLevelDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<SeedLevelDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public SeedLevelDto checkDuplicateCode(String code) {
		SeedLevelDto viewCheckDuplicateCodeDto = new SeedLevelDto();
		SeedLevel au = null;
		List<SeedLevel> list = seedLevelRepository.findByCode(code);
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
