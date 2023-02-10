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
import com.globits.wl.domain.WaterSource;
import com.globits.wl.dto.WaterSourceDto;
import com.globits.wl.dto.functiondto.WaterSourceSearchDto;
import com.globits.wl.repository.WaterSourceRepository;
import com.globits.wl.service.WaterSourceService;

@Service
public class WaterSourceServiceImpl extends GenericServiceImpl<WaterSource, Long> implements WaterSourceService {

	@Autowired
	private WaterSourceRepository waterSourceRepository;

	@Override
	public Page<WaterSourceDto> getListWaterSources(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return waterSourceRepository.getListWaterSources(pageable);
	}

	@Override
	public List<WaterSourceDto> getAll() {
		// TODO Auto-generated method stub
		return this.waterSourceRepository.getAll();
	}

	@Override
	public WaterSourceDto waterSourceById(Long id) {
		return this.waterSourceRepository.waterSourceById(id);
	}

	@Override
	public WaterSourceDto saveWaterSource(WaterSourceDto waterSourceDto, Long id) {
		WaterSource waterSource = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			waterSource = this.waterSourceRepository.findOne(id);
		} else if (waterSourceDto.getId() != null) {
			waterSource = this.waterSourceRepository.findOne(waterSourceDto.getId());
		}

		if (waterSource == null) {// trường hợp thêm mới
			waterSource = new WaterSource();
			waterSource.setCreateDate(currentDate);
			waterSource.setCreatedBy(currentUserName);
		}

		if (waterSourceDto.getName() != null)
			waterSource.setName(waterSourceDto.getName());

		if (waterSourceDto.getCode() != null)
			waterSource.setCode(waterSourceDto.getCode());

		waterSource = this.waterSourceRepository.save(waterSource);
		waterSourceDto.setId(waterSource.getId());
		return waterSourceDto;
	}

	@Override
	public WaterSourceDto removeWaterSource(Long id) {
		WaterSourceDto WaterSourceDto = null;
		if (waterSourceRepository != null && this.waterSourceRepository.exists(id)) {
			WaterSourceDto = new WaterSourceDto(this.waterSourceRepository.findOne(id));
			this.waterSourceRepository.delete(id);

		}
		return WaterSourceDto;
	}

	@Override
	public Page<WaterSourceDto> searchWaterSourceDto(
			WaterSourceSearchDto searchDto, int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.WaterSourceDto(fa) from WaterSource fa  where (1=1)";
		String sqlCount = "select count(fa.id) from WaterSource fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,WaterSourceDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<WaterSourceDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public WaterSourceDto checkDuplicateCode(String code) {
		WaterSourceDto viewCheckDuplicateCodeDto = new WaterSourceDto();
		WaterSource au = null;
		List<WaterSource> list = waterSourceRepository.findByCode(code);
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
