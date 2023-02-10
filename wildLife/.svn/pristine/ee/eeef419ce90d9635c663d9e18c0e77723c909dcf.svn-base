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
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.FmsRegionRepository;
import com.globits.wl.service.FmsRegionService;

@Service
public class FmsRegionServicImpl extends GenericServiceImpl<FmsRegion, Long> implements FmsRegionService {

	@Autowired
	private FmsRegionRepository fmsRegionRepository;

	@Override
	public Page<FmsRegionDto> getListFmsRegion(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return fmsRegionRepository.getListFmsRegion(pageable);
	}

	@Override
	public FmsRegionDto fmsRegionById(Long id) {

		return this.fmsRegionRepository.fmsRegionById(id);
	}

	@Override
	public FmsRegionDto saveFmsRegion(FmsRegionDto fmsRegionDto,Long id) {
		FmsRegion fmsRegion = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if(id!=null) {//trường hợp edit
			fmsRegion = this.fmsRegionRepository.findOne(id);
		}
		else if (fmsRegionDto.getId() != null) {
			fmsRegion = this.fmsRegionRepository.findOne(fmsRegionDto.getId());
		}

		if (fmsRegion == null) {//trường hợp thêm mới
			fmsRegion = new FmsRegion();
			fmsRegion.setCreateDate(currentDate);
			fmsRegion.setCreatedBy(currentUserName);
		}

		if (fmsRegionDto.getName() != null)
			fmsRegion.setName(fmsRegionDto.getName());

		if (fmsRegionDto.getCode() != null)
			fmsRegion.setCode(fmsRegionDto.getCode());

		if (fmsRegionDto.getDescription() != null)
			fmsRegion.setDescription(fmsRegionDto.getDescription());

		if (fmsRegionDto.getAcreage() >= 0)
			fmsRegion.setAcreage(fmsRegionDto.getAcreage());

		fmsRegion = this.fmsRegionRepository.save(fmsRegion);
		fmsRegionDto.setId(fmsRegion.getId());
		return fmsRegionDto;
	}

	@Override
	public FmsRegionDto removeFmsRegion(Long id) {
		FmsRegionDto fmsRegionDto = null;
		if(fmsRegionRepository != null && this.fmsRegionRepository.exists(id)) {
			fmsRegionDto = new FmsRegionDto(this.fmsRegionRepository.findOne(id));		
			this.fmsRegionRepository.delete(id);
			
		}
		return fmsRegionDto;
	}

	@Override
	public boolean removeLists(List<Long> ids) {
		// TODO Auto-generated method stub
		if(ids != null && ids.size() >0) {
			for(Long id : ids) {
				this.fmsRegionRepository.delete(id);
			}
		}
		return false;
	}

	@Override
	public List<FmsRegionDto> getAll() {
		// TODO Auto-generated method stub
		return this.fmsRegionRepository.getAll();
	}

	@Override
	public Page<FmsRegionDto> searchDto(SearchDto searchDto, int pageIndex,
			int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.FmsRegionDto(fa) from FmsRegion fa  where (1=1)";
		String sqlCount = "select count(fa.id) from FmsRegion fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,FmsRegionDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<FmsRegionDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public FmsRegionDto checkDuplicateCode(String code) {
		FmsRegionDto viewCheckDuplicateCodeDto = new FmsRegionDto();
		FmsRegion au = null;
		List<FmsRegion> list = fmsRegionRepository.findByCode(code);
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
