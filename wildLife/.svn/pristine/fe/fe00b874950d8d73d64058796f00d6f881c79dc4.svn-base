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
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.LiveStockProduct;
import com.globits.wl.domain.Unit;
import com.globits.wl.dto.LiveStockProductDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.LiveStockProductRepository;
import com.globits.wl.repository.UnitRepository;
import com.globits.wl.service.LiveStockProductService;

@Service
public class LiveStockproductServiceImpl extends GenericServiceImpl<LiveStockProduct, Long> implements LiveStockProductService {

	@Autowired
	private LiveStockProductRepository liveStockProductRepository;
	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private UnitRepository unitRepository;

	@Override
	public Page<LiveStockProductDto> getAllWidthPagination(int pageIndex, int pageSize) {
		if (pageIndex > 1)
			pageIndex--;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.liveStockProductRepository.getPageAll(pageable);
	}

	@Override
	public LiveStockProductDto getById(Long id) {
		return this.liveStockProductRepository.getById(id);
	}

	@Override
	public LiveStockProductDto save(LiveStockProductDto dto) {
		if (dto != null) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			LiveStockProduct liveStockProduct = null;
			if (dto.getId() != null) {
				liveStockProduct = this.liveStockProductRepository.findOne(dto.getId());
			}
			if (liveStockProduct == null) {
				liveStockProduct = new LiveStockProduct();
				liveStockProduct.setCreateDate(currentDate);
				liveStockProduct.setCreatedBy(currentUserName);
			}
			if (dto.getName() != null) {
				liveStockProduct.setName(dto.getName());
			}
			if (dto.getCode() != null) {
				liveStockProduct.setCode(dto.getCode());
			}
			if(dto.getAnimal()!=null && dto.getAnimal().getId()!=null) {
				Animal an=animalRepository.findOne(dto.getAnimal().getId());
				liveStockProduct.setAnimal(an);
			}
			if(dto.getUnitAmount()!=null && dto.getUnitAmount().getId()!=null) {
				Unit uAmount=unitRepository.findOne(dto.getUnitAmount().getId());
				liveStockProduct.setUnitAmount(uAmount);
			}else {
				liveStockProduct.setUnitAmount(null);
			}
			if(dto.getUnitQuantity()!=null && dto.getUnitQuantity().getId()!=null) {
				Unit uQuantity=unitRepository.findOne(dto.getUnitQuantity().getId());
				liveStockProduct.setUnitQuantity(uQuantity);
			}else {
				liveStockProduct.setUnitQuantity(null);
			}
			
			LiveStockProduct liveStockProduct2 = this.liveStockProductRepository.save(liveStockProduct);
			if (liveStockProduct2 != null) {
				dto.setId(liveStockProduct2.getId());
			}
			
			return dto;
		}

		return null;
	}

	@Override
	public boolean deleteById(Long id) {
		if(id != null) {
			if(this.liveStockProductRepository.exists(id)) {				
				this.liveStockProductRepository.delete(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteByIds(Set<Long> ids) {
		if(ids != null && ids.size() >0) {
			for(Long id: ids) {
				if(id!= null) {
					this.liveStockProductRepository.delete(id);
				}
			}
			
			return true;
		}
		return false;
	}

	@Override
	public List<LiveStockProductDto> getAll() {
		return this.liveStockProductRepository.getAllDtos();
	}

	@Override
	public Page<LiveStockProductDto> searchDto(SearchDto searchDto, int pageIndex,
			int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.LiveStockProductDto(fa) from LiveStockProduct fa  where (1=1)";
		String sqlCount = "select count(fa.id) from LiveStockProduct fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
		if(searchDto.getAnimalId()!=null && searchDto.getAnimalId()>0) {
			whereClause += " and (fa.animal.id =:animalId)";
		}
		if(searchDto.getUnitAmountId()!=null && searchDto.getUnitAmountId()>0) {
			whereClause += " and (fa.unitAmount.id =:unitAmountId)";
		}
		if(searchDto.getUnitQuantityId()!=null && searchDto.getUnitQuantityId()>0) {
			whereClause += " and (fa.unitQuantity.id =:unitQuantityId)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,LiveStockProductDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		if(searchDto.getAnimalId()!=null && searchDto.getAnimalId()>0) {
			q.setParameter("animalId", searchDto.getAnimalId());
			qCount.setParameter("animalId", searchDto.getAnimalId());
		}
		if(searchDto.getUnitAmountId()!=null && searchDto.getUnitAmountId()>0) {
			q.setParameter("unitAmountId", searchDto.getUnitAmountId());
			qCount.setParameter("unitAmountId", searchDto.getUnitAmountId());
		}
		if(searchDto.getUnitQuantityId()!=null && searchDto.getUnitQuantityId()>0) {
			q.setParameter("unitQuantityId", searchDto.getUnitQuantityId());
			qCount.setParameter("unitQuantityId", searchDto.getUnitQuantityId());
		}
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<LiveStockProductDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public LiveStockProductDto checkDuplicateCode(String code) {
		LiveStockProductDto viewCheckDuplicateCodeDto = new LiveStockProductDto();
		LiveStockProduct au = null;
		List<LiveStockProduct> list = liveStockProductRepository.findByCode(code);
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
