package com.globits.wl.service.impl;

import java.util.List;

import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.service.ProductTargetService;

@Service
public class ProductTargetServiceImpl extends GenericServiceImpl<ProductTarget, Long> implements ProductTargetService {
	@Autowired
	private ProductTargetRepository productTargetRepository;

	@Override
	public Page<ProductTargetDto> getPageProductTarget(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable page = new PageRequest(pageIndex, pageSize);
		return this.productTargetRepository.getPageProductTarget(page);
	}

	@Override
	public ProductTargetDto createProductTarget(ProductTargetDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			ProductTarget productTarget = null;

			if (dto.getId() != null) {
				productTarget = this.productTargetRepository.findOne(dto.getId());
			}

			if (productTarget == null) {
				productTarget = new ProductTarget();
				productTarget.setCreateDate(currentDate);
				productTarget.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null)
				productTarget.setCode(dto.getCode());

			if (dto.getDescription() != null)
				productTarget.setDescription(dto.getDescription());

			if (dto.getName() != null)
				productTarget.setName(dto.getName());
			
			ProductTarget parent = null;
			if(dto.getParent() != null && dto.getParent().getId() != null) {
				parent = this.productTargetRepository.findOne(dto.getParent().getId());
			}
			productTarget.setParent(parent);

			ProductTarget productTarget2 = this.productTargetRepository.save(productTarget);
			dto.setId(productTarget2.getId());

			return dto;
		}

		return dto;
	}

	@Override
	public ProductTargetDto updateProductTarget(Long id, ProductTargetDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			ProductTarget productTarget = null;
			if (id != null) {
				productTarget = this.productTargetRepository.findOne(id);
			} else if (dto.getId() != null) {
				productTarget = this.productTargetRepository.findOne(dto.getId());
			}

			if (productTarget == null) {
				productTarget = new ProductTarget();
				productTarget.setCreateDate(currentDate);
				productTarget.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null)
				productTarget.setCode(dto.getCode());

			if (dto.getDescription() != null)
				productTarget.setDescription(dto.getDescription());

			if (dto.getName() != null)
				productTarget.setName(dto.getName());
			
			ProductTarget parent = null;
			if(dto.getParent() != null && dto.getParent().getId() != null) {
				parent = this.productTargetRepository.findOne(dto.getParent().getId());
			}
			productTarget.setParent(parent);

			ProductTarget productTarget2 = this.productTargetRepository.save(productTarget);
			dto.setId(productTarget2.getId());

			return dto;
		}

		return dto;
	}

	@Override
	public ProductTargetDto deleteProductTarget(Long id) {
		if (id != null) {
			ProductTargetDto dto = null;
			ProductTarget productTarget = null;
			productTarget = this.productTargetRepository.findOne(id);
			if (productTarget != null) {
				dto = new ProductTargetDto(productTarget);
				this.productTargetRepository.delete(id);
				return dto;
			}
		}
		return null;
	}

	@Override
	public ResponseEntity<Boolean> deleteProductTargets(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				this.productTargetRepository.delete(id);
			}
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ProductTargetDto getProducById(Long id) {
		if(id!=null) {
			ProductTarget product = new ProductTarget();
			product = this.productTargetRepository.findOne(id);
			if(product != null) {
				ProductTargetDto dto = new ProductTargetDto(product);
				return dto;
			}
			return null;
		}
		return null;
	}

	@Override
	public List<ProductTargetDto> getAllProductTarget() {
		return this.productTargetRepository.getAll();
	}

	@Override
	public Page<ProductTargetDto> searchDto(SearchDto searchDto, int pageIndex,
			int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		
		String sql = " select new com.globits.wl.dto.ProductTargetDto(fa) from ProductTarget fa  where (1=1)";
		String sqlCount = "select count(fa.id) from ProductTarget fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.code asc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,ProductTargetDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<ProductTargetDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public ProductTargetDto checkDuplicateCode(String code) {
		ProductTargetDto viewCheckDuplicateCodeDto = new ProductTargetDto();
		ProductTarget au = null;
		List<ProductTarget> list = productTargetRepository.findByCode(code);
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

	@Override
	public List<ProductTargetDto> getAllByParentId(Long parentId) {
		if(parentId != null) {
			return this.productTargetRepository.getByParentId(parentId);
		}
		return null;
	}

}
