package com.globits.wl.service.impl;

import java.util.ArrayList;
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
import com.globits.wl.domain.FarmSize;
import com.globits.wl.dto.FarmSizeDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.FarmSizeRepository;
import com.globits.wl.service.FarmSizeService;

@Service
public class FarmSizeServiceIpml extends GenericServiceImpl<FarmSize, Long> implements FarmSizeService {
	@Autowired
	private FarmSizeRepository farmSizeRepository;

	@Override
	public Page<FarmSizeDto> getAll(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.farmSizeRepository.getAll(pageable);
	}

	@Override
	public FarmSizeDto getFarmSizeById(Long id) {
		if (id != null) {
			FarmSize animalType = null;
			animalType = this.farmSizeRepository.findOne(id);
			if (animalType != null) {
				return new FarmSizeDto(animalType);
			}
		}
		return null;
	}

	@Override
	public FarmSizeDto createFarmSize(FarmSizeDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			FarmSize animalType = null;
			if (dto.getId() != null) {
				animalType = this.farmSizeRepository.findOne(dto.getId());
			}

			if (animalType == null) {
				animalType = new FarmSize();
				animalType.setCreateDate(currentDate);
				animalType.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				animalType.setCode(dto.getCode());
			}
			if (dto.getMinQuantity() >0) {
				animalType.setMinQuantity(dto.getMinQuantity());
			}
			if (dto.getName() != null) {
				animalType.setName(dto.getName());
			}
			
			FarmSize animalType2 = this.farmSizeRepository.save(animalType);

			dto.setId(animalType2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public FarmSizeDto updateFarmSize(Long id, FarmSizeDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			FarmSize animalType = null;
			if (id != null) {
				animalType = this.farmSizeRepository.findOne(id);
			} else if (dto.getId() != null) {
				animalType = this.farmSizeRepository.findOne(dto.getId());
			}

			if (animalType == null) {
				animalType = new FarmSize();
				animalType.setCreateDate(currentDate);
				animalType.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				animalType.setCode(dto.getCode());
			}
			if (dto.getMinQuantity() >0) {
				animalType.setMinQuantity(dto.getMinQuantity());
			}
			if (dto.getName() != null) {
				animalType.setName(dto.getName());
			}
			FarmSize animalType2 = this.farmSizeRepository.save(animalType);

			dto.setId(animalType2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public FarmSizeDto deleteById(Long id) {
		if(id!= null) {
			FarmSizeDto animalTypeDto = null;
			FarmSize animalType = new FarmSize();
			animalType = this.farmSizeRepository.findOne(id);
			if(animalType != null) {
				animalTypeDto = new FarmSizeDto(animalType);
				this.farmSizeRepository.delete(id);
				return animalTypeDto;
			}
		}
		return null;
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {
		if(ids != null && ids.size() > 0) {
			for(Long id :ids) {
				this.farmSizeRepository.delete(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<FarmSizeDto> getAllDto() {
		return this.farmSizeRepository.getAllDto();
	}

	@Override
	public Page<FarmSizeDto> searchDto(SearchDto searchDto,
			int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.FarmSizeDto(fa) from FarmSize fa  where (1=1)";
		String sqlCount = "select count(fa.id) from FarmSize fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,FarmSizeDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<FarmSizeDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public FarmSizeDto checkDuplicateCode(String code) {
		FarmSizeDto viewCheckDuplicateCodeDto = new FarmSizeDto();
		FarmSize building = null;
		List<FarmSize> list = farmSizeRepository.findByCode(code);
		if(list != null && list.size() > 0) {
			building = list.get(0);
		}
		if(list == null) {
			viewCheckDuplicateCodeDto.setDuplicate(false);
		}else if(list != null && list.size() > 0) {
			viewCheckDuplicateCodeDto.setDuplicate(true);
			viewCheckDuplicateCodeDto.setDupCode(building.getCode());
			viewCheckDuplicateCodeDto.setDupName(building.getName());
		}
		return viewCheckDuplicateCodeDto;
	}
	@Override
	public FarmSizeDto getByQuantity(int quantity) {
		List<FarmSizeDto> list=new ArrayList<FarmSizeDto>();
		FarmSizeDto dto=null;
		list=farmSizeRepository.findByQuantity(quantity);
		if(list!=null && list.size()>0){
			dto=list.get(0);
		}
		return dto;
	}

}
