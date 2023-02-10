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
import com.globits.wl.domain.Drug;
import com.globits.wl.dto.DrugDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.DrugRepository;
import com.globits.wl.service.DrugService;

@Service
public class DrugServiceImpl extends GenericServiceImpl<Drug, Long> implements DrugService {
	@Autowired
	private DrugRepository drugRepository; 

	@Override
	public Page<DrugDto> getPageDto(int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
		return this.drugRepository.getPageDto(pageable);
	}

	@Override
	public List<DrugDto> getAllDto() {
		return drugRepository.getAllDto();
	}

	@Override
	public DrugDto getDrugById(Long id) {
		if (id != null) {
			Drug Bran = null;
			Bran = this.drugRepository.findOne(id);
			if (Bran != null) {
				return new DrugDto(Bran);
			}
		}
		return null;
	}

	@Override
	public DrugDto deleteById(Long id) {
		if (id != null) {
			DrugDto drugDto = null;
			Drug drug = new Drug();
			drug = this.drugRepository.findOne(id);
			if (drug != null) {
				drugDto = new DrugDto(drug);
				this.drugRepository.delete(id);
				return drugDto;
			}
		}
		return null;
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {

		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				this.drugRepository.delete(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public DrugDto createDrug(DrugDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Drug Bran = null;
			if (dto.getId() != null) {
				Bran = this.drugRepository.findOne(dto.getId());
			}

			if (Bran == null) {
				Bran = new Drug();
				Bran.setCreateDate(currentDate);
				Bran.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				Bran.setCode(dto.getCode());
			}
			if (dto.getName() != null) {
				Bran.setName(dto.getName());
			}
			if (dto.getDescription()!= null) {
				Bran.setDescription(dto.getDescription());
			}

			Drug Bran2 = this.drugRepository.save(Bran);

			dto.setId(Bran2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public DrugDto updateDrug(Long id, DrugDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Drug Bran = null;
			if (id != null) {
				Bran = this.drugRepository.findOne(id);
			}
			else if (dto.getId() != null) {
				Bran = this.drugRepository.findOne(dto.getId());
			}

			if (Bran == null) {
				Bran = new Drug();
				Bran.setCreateDate(currentDate);
				Bran.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				Bran.setCode(dto.getCode());
			}
			if (dto.getName() != null) {
				Bran.setName(dto.getName());
			}
			if (dto.getDescription() != null) {
				Bran.setDescription(dto.getDescription());
			}

			Drug Bran2 = this.drugRepository.save(Bran);

			dto.setId(Bran2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public Page<DrugDto> searchDrugDto(SearchDto searchDto,
			int pageIndex, int pageSize) {
		
			if(pageIndex > 0) pageIndex = pageIndex-1;
			else pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			
			
			String namecode = searchDto.getNameOrCode();
					
			
			String sql = "select new com.globits.wl.dto.DrugDto(fa) from Drug fa  where (1=1)";
			String sqlCount = "select count(fa.id) from Drug fa  where (1=1)";
			String whereClause ="";
			if(namecode!=null && namecode.length()>0) {
				whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
			}
					
			sql +=whereClause;
			sql +=" order by fa.code asc ";
			sqlCount+=whereClause;

			Query q = manager.createQuery(sql,DrugDto.class);
			Query qCount = manager.createQuery(sqlCount);
			
			
			if(namecode!=null && namecode.length()>0) {
				q.setParameter("namecode", '%'+namecode+'%');
				qCount.setParameter("namecode", '%'+namecode+'%');
			}		
			
			q.setFirstResult((pageIndex)*pageSize);
			q.setMaxResults(pageSize);
			
			Long numberResult =(Long)qCount.getSingleResult();
			Page<DrugDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
			return page;
	}

	@Override
	public DrugDto checkDuplicateCode(String code) {		
		DrugDto viewCheckDuplicateCodeDto = new DrugDto();
		Drug au = null;
		List<Drug> list = drugRepository.findByCode(code);
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
