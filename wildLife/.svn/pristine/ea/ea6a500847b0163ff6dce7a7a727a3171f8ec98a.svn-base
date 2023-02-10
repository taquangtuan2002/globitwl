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
import com.globits.wl.domain.HusbandryMethod;
import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.HusbandryMethodRepository;
import com.globits.wl.service.HusbandryMethodService;

@Service
public class HusbandryMethodServiceImpl extends GenericServiceImpl<HusbandryMethod, Long> implements HusbandryMethodService{

	@Autowired
	private HusbandryMethodRepository husbandryMethodRepository;

	@Override
	public Page<HusbandryMethodDto> getListHusbandryMethods(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.husbandryMethodRepository.getListHusbandryMethods(pageable);
	}

	@Override
	public List<HusbandryMethodDto> getAll() {
		return this.husbandryMethodRepository.getAll();
	}

	@Override
	public HusbandryMethodDto husbandryMethodById(Long id) {
		return this.husbandryMethodRepository.husbandryMethodById(id);
	}

	@Override
	public HusbandryMethodDto saveHusbandryMethod(HusbandryMethodDto husbandryMethodDto, Long id) {
		HusbandryMethod husbandryMethod = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			husbandryMethod = this.husbandryMethodRepository.findOne(id);
		} else if (husbandryMethodDto.getId() != null) {
			husbandryMethod = this.husbandryMethodRepository.findOne(husbandryMethodDto.getId());
		}

		if (husbandryMethod == null) {// trường hợp thêm mới
			husbandryMethod = new HusbandryMethod();
			husbandryMethod.setCreateDate(currentDate);
			husbandryMethod.setCreatedBy(currentUserName);
		}

		if (husbandryMethodDto.getName() != null)
			husbandryMethod.setName(husbandryMethodDto.getName());

		if (husbandryMethodDto.getCode() != null)
			husbandryMethod.setCode(husbandryMethodDto.getCode());

		husbandryMethod = this.husbandryMethodRepository.save(husbandryMethod);
		husbandryMethodDto.setId(husbandryMethod.getId());
		return husbandryMethodDto;
	}

	@Override
	public HusbandryMethodDto removeHusbandryMethod(Long id) {
		HusbandryMethodDto husbandryMethodDto = null;
		if (husbandryMethodRepository != null && this.husbandryMethodRepository.exists(id)) {
			husbandryMethodDto = new HusbandryMethodDto(this.husbandryMethodRepository.findOne(id));
			this.husbandryMethodRepository.delete(id);

		}
		return husbandryMethodDto;
	}

	@Override
	public Page<HusbandryMethodDto> searchDto(SearchDto searchDto,
			int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.HusbandryMethodDto(fa) from HusbandryMethod fa  where (1=1)";
		String sqlCount = "select count(fa.id) from HusbandryMethod fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,HusbandryMethodDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<HusbandryMethodDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public HusbandryMethodDto checkDuplicateCode(String code) {
		HusbandryMethodDto viewCheckDuplicateCodeDto = new HusbandryMethodDto();
		HusbandryMethod au = null;
		List<HusbandryMethod> list = husbandryMethodRepository.findByCode(code);
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
