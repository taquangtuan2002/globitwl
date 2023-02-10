package com.globits.wl.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
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

import com.globits.security.domain.User;
import com.globits.wl.domain.Ownership;
import com.globits.wl.domain.SeedLevel;
import com.globits.wl.dto.OwnershipDto;
import com.globits.wl.dto.SeedLevelDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.OwnershipRepository;
import com.globits.wl.service.OwnershipService;
@Service
public class OwnershipServiceImpl implements OwnershipService{
	@Autowired
	private OwnershipRepository ownershipRepository;
	@Autowired
	EntityManager manager;
	@Override
	public Page<OwnershipDto> getSearchOwnerShip(SearchDto searchDto, int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.OwnershipDto(s) from Ownership s  where (1=1)";
		String sqlCount = "select count(s.id) from Ownership s where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (s.code like :namecode or s.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by s.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,OwnershipDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<OwnershipDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}
	@Override
	public List<OwnershipDto> getAll() {
		return ownershipRepository.getAllOwnershipDto();
	}
	@Override
	public OwnershipDto getOwnershipDtoById(Long id) {
		Ownership ship = null;
		ship = ownershipRepository.findOne(id);
		if(ship != null) {
			return new OwnershipDto(ship);
		}
		return null;
	}
	@Override
	public OwnershipDto save(OwnershipDto ownershipDto, Long id) {
		Ownership ownership = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			ownership = this.ownershipRepository.findOne(id);
		} else if (ownershipDto.getId() != null) {
			ownership = this.ownershipRepository.findOne(ownershipDto.getId());
		}

		if (ownership == null) {// trường hợp thêm mới
			ownership = new Ownership();
			ownership.setCreateDate(currentDate);
			ownership.setCreatedBy(currentUserName);
		}

		if (ownershipDto.getName() != null)
			ownership.setName(ownershipDto.getName());

		if (ownershipDto.getCode() != null)
			ownership.setCode(ownershipDto.getCode());
		
		ownership.setDescription(ownershipDto.getDescription());

		ownership = this.ownershipRepository.save(ownership);
		ownershipDto.setId(ownership.getId());
		return ownershipDto;
	}
	@Override
	public OwnershipDto removeById(Long id) {
		OwnershipDto ownershipDto = null;
		if (this.ownershipRepository.exists(id)) {
			ownershipDto = new OwnershipDto(this.ownershipRepository.findOne(id));
			this.ownershipRepository.delete(id);
		}
		return ownershipDto;
	}
	@Override
	public OwnershipDto checkDuplicateCode(String code) {
		OwnershipDto viewCheckDuplicateCodeDto = new OwnershipDto();
		Ownership au = null;
		List<Ownership> list = ownershipRepository.findByCode(code);
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
