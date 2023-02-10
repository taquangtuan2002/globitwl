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
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.AnimalTypeRepository;
import com.globits.wl.service.AnimalTypeService;

@Service
public class AnimalTypeServiceIpml extends GenericServiceImpl<AnimalType, Long> implements AnimalTypeService {
	@Autowired
	private AnimalTypeRepository animalTypeRepository;

	@Override
	public Page<AnimalTypeDto> getAll(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.animalTypeRepository.getAll(pageable);
	}

	@Override
	public AnimalTypeDto getAnimalTypeById(Long id) {
		if (id != null) {
			AnimalType animalType = null;
			animalType = this.animalTypeRepository.findOne(id);
			if (animalType != null) {
				return new AnimalTypeDto(animalType);
			}
		}
		return null;
	}

	@Override
	public AnimalTypeDto createAnimalType(AnimalTypeDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			AnimalType animalType = null;
			if (dto.getId() != null) {
				animalType = this.animalTypeRepository.findOne(dto.getId());
			}

			if (animalType == null) {
				animalType = new AnimalType();
				animalType.setCreateDate(currentDate);
				animalType.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				animalType.setCode(dto.getCode());
			}
			if (dto.getDescription() != null) {
				animalType.setDescription(dto.getDescription());
			}
			if (dto.getName() != null) {
				animalType.setName(dto.getName());
			}
			
			AnimalType animalType2 = this.animalTypeRepository.save(animalType);

			dto.setId(animalType2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public AnimalTypeDto updateAnimalType(Long id, AnimalTypeDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			AnimalType animalType = null;
			if (id != null) {
				animalType = this.animalTypeRepository.findOne(id);
			} else if (dto.getId() != null) {
				animalType = this.animalTypeRepository.findOne(dto.getId());
			}

			if (animalType == null) {
				animalType = new AnimalType();
				animalType.setCreateDate(currentDate);
				animalType.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				animalType.setCode(dto.getCode());
			}
			if (dto.getDescription() != null) {
				animalType.setDescription(dto.getDescription());
			}
			if (dto.getName() != null) {
				animalType.setName(dto.getName());
			}
			AnimalType animalType2 = this.animalTypeRepository.save(animalType);

			dto.setId(animalType2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public AnimalTypeDto deleteById(Long id) {
		if(id!= null) {
			AnimalTypeDto animalTypeDto = null;
			AnimalType animalType = new AnimalType();
			animalType = this.animalTypeRepository.findOne(id);
			if(animalType != null) {
				animalTypeDto = new AnimalTypeDto(animalType);
				this.animalTypeRepository.delete(id);
				return animalTypeDto;
			}
		}
		return null;
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {
		if(ids != null && ids.size() > 0) {
			for(Long id :ids) {
				this.animalTypeRepository.delete(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<AnimalTypeDto> getAllDto() {
		return this.animalTypeRepository.getAllDto();
	}

	@Override
	public Page<AnimalTypeDto> searchAnimalTypeDto(SearchDto searchDto,
			int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.AnimalTypeDto(fa) from AnimalType fa  where (1=1)";
		String sqlCount = "select count(fa.id) from AnimalType fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,AnimalTypeDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<AnimalTypeDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public AnimalTypeDto checkDuplicateCode(String code) {
		AnimalTypeDto viewCheckDuplicateCodeDto = new AnimalTypeDto();
		AnimalType animalType = null;
		List<AnimalType> list = animalTypeRepository.findByCode(code);
		if(list != null && list.size() > 0) {
			animalType = list.get(0);
		}
		if(list == null) {
			viewCheckDuplicateCodeDto.setDuplicate(false);
		}else if(list != null && list.size() > 0) {
			viewCheckDuplicateCodeDto.setDuplicate(true);
			viewCheckDuplicateCodeDto.setDupCode(animalType.getCode());
			viewCheckDuplicateCodeDto.setDupName(animalType.getName());
		}
		return viewCheckDuplicateCodeDto;
	}

}
