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
import com.globits.wl.domain.Bran;
import com.globits.wl.dto.BranDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.BranRepository;
import com.globits.wl.service.BranService;

@Service
public class BranServiceImpl extends GenericServiceImpl<Bran, Long> implements BranService {
	@Autowired
	private BranRepository BranRepository; 

	@Override
	public Page<BranDto> getPageDto(int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
		return this.BranRepository.getPageDto(pageable);
	}

	@Override
	public List<BranDto> getAllDto() {
		return BranRepository.getAllDto();
	}

	@Override
	public BranDto getBranById(Long id) {
		if (id != null) {
			Bran Bran = null;
			Bran = this.BranRepository.findOne(id);
			if (Bran != null) {
				return new BranDto(Bran);
			}
		}
		return null;
	}

	@Override
	public BranDto deleteById(Long id) {
		if (id != null) {
			BranDto BranDto = null;
			Bran Bran = new Bran();
			Bran = this.BranRepository.findOne(id);
			if (Bran != null) {
				BranDto = new BranDto(Bran);
				this.BranRepository.delete(id);
				return BranDto;
			}
		}
		return null;
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {

		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				this.BranRepository.delete(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public BranDto createBran(BranDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Bran Bran = null;
			if (dto.getId() != null) {
				Bran = this.BranRepository.findOne(dto.getId());
			}

			if (Bran == null) {
				Bran = new Bran();
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

			Bran Bran2 = this.BranRepository.save(Bran);

			dto.setId(Bran2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public BranDto updateBran(Long id, BranDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Bran Bran = null;
			if (id != null) {
				Bran = this.BranRepository.findOne(id);
			}
			else if (dto.getId() != null) {
				Bran = this.BranRepository.findOne(dto.getId());
			}

			if (Bran == null) {
				Bran = new Bran();
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

			Bran Bran2 = this.BranRepository.save(Bran);

			dto.setId(Bran2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public Page<BranDto> searchBranDto(SearchDto searchDto,
			int pageIndex, int pageSize) {
		
			if(pageIndex > 0) pageIndex = pageIndex-1;
			else pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);
			
			
			String namecode = searchDto.getNameOrCode();
					
			
			String sql = "select new com.globits.wl.dto.BranDto(fa) from Bran fa  where (1=1)";
			String sqlCount = "select count(fa.id) from Bran fa  where (1=1)";
			String whereClause ="";
			if(namecode!=null && namecode.length()>0) {
				whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
			}
					
			sql +=whereClause;
			sql +=" order by fa.code asc ";
			sqlCount+=whereClause;

			Query q = manager.createQuery(sql,BranDto.class);
			Query qCount = manager.createQuery(sqlCount);
			
			
			if(namecode!=null && namecode.length()>0) {
				q.setParameter("namecode", '%'+namecode+'%');
				qCount.setParameter("namecode", '%'+namecode+'%');
			}		
			
			q.setFirstResult((pageIndex)*pageSize);
			q.setMaxResults(pageSize);
			
			Long numberResult =(Long)qCount.getSingleResult();
			Page<BranDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
			return page;
	}

	@Override
	public BranDto checkDuplicateCode(String code) {		
		BranDto viewCheckDuplicateCodeDto = new BranDto();
		Bran au = null;
		List<Bran> list = BranRepository.findByCode(code);
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
