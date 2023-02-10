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
import com.globits.wl.domain.LiveStockProduct;
import com.globits.wl.domain.Unit;
import com.globits.wl.dto.UnitDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.LiveStockProductRepository;
import com.globits.wl.repository.UnitRepository;
import com.globits.wl.service.UnitService;

@Service
public class UnitServiceImpl extends GenericServiceImpl<Unit, Long> implements UnitService {

	@Autowired
	private UnitRepository unitRepository;
	@Autowired
	private LiveStockProductRepository liveStockProductRepository;

	@Override
	public Page<UnitDto> getAllWidthPagination(int pageIndex, int pageSize) {
		if (pageIndex > 1)
			pageIndex--;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.unitRepository.getPageAll(pageable);
	}

	@Override
	public UnitDto getUnitById(Long id) {
		return this.unitRepository.getUnitDtoById(id);
	}

	@Override
	public UnitDto saveUnit(UnitDto dto) {
		if (dto != null) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Unit original = null;
			if (dto.getId() != null) {
				original = this.unitRepository.findOne(dto.getId());
			}
			if (original == null) {
				original = new Unit();
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
			Unit original2 = this.unitRepository.save(original);
			if (original2 != null) {
				dto.setId(original2.getId());
			}
			
			return dto;
		}

		return null;
	}

	@Override
	public boolean deleteUnitById(Long id) {
		if(id != null) {
			if(this.unitRepository.exists(id)) {
				List<LiveStockProduct> list=liveStockProductRepository.findByUnit(id);
				if(list!=null && list.size()>0) {
					return false;
				}else {
					this.unitRepository.delete(id);
					return true;
				}	
			}
		}
		return false;
	}

	@Override
	public boolean deleteUnitByIds(Set<Long> ids) {
		if(ids != null && ids.size() >0) {
			for(Long id: ids) {
				if(id!= null) {
					List<LiveStockProduct> list=liveStockProductRepository.findByUnit(id);
					if(list!=null && list.size()>0) {
						
					}else {
						this.unitRepository.delete(id);
						
					}						
				}
			}
			
			return true;
		}
		return false;
	}

	@Override
	public List<UnitDto> getAll() {
		return this.unitRepository.getAllDtos();
	}

	@Override
	public Page<UnitDto> searchDto(SearchDto searchDto, int pageIndex,
			int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.UnitDto(fa) from Unit fa  where (1=1)";
		String sqlCount = "select count(fa.id) from Unit fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,UnitDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<UnitDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public UnitDto checkDuplicateCode(String code) {
		UnitDto viewCheckDuplicateCodeDto = new UnitDto();
		Unit au = null;
		List<Unit> list = unitRepository.findByCode(code);
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
