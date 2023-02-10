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
import com.globits.wl.domain.Link;
import com.globits.wl.dto.LinkDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.LiveStockProductRepository;
import com.globits.wl.repository.LinkRepository;
import com.globits.wl.service.LinkService;

@Service
public class LinkServiceImpl extends GenericServiceImpl<Link, Long> implements LinkService {

	@Autowired
	private LinkRepository linkRepository;

	@Override
	public Page<LinkDto> getAllWidthPagination(int pageIndex, int pageSize) {
		if (pageIndex > 1)
			pageIndex--;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.linkRepository.getPageAll(pageable);
	}

	@Override
	public LinkDto getLinkById(Long id) {
		return this.linkRepository.getLinkDtoById(id);
	}

	@Override
	public LinkDto saveLink(LinkDto dto) {
		if (dto != null) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Link original = null;
			if (dto.getId() != null) {
				original = this.linkRepository.findOne(dto.getId());
			}
			if (original == null) {
				original = new Link();
				original.setCreateDate(currentDate);
				original.setCreatedBy(currentUserName);
			}
			if (dto.getName() != null) {
				original.setName(dto.getName());
			}
			if (dto.getCode() != null) {
				original.setCode(dto.getCode());
			}
			if (dto.getHyperLink() != null) {
				original.setHyperLink(dto.getHyperLink());
			}
			if (dto.getDescription() != null) {
				original.setDescription(dto.getDescription());
			}
			Link original2 = this.linkRepository.save(original);
			if (original2 != null) {
				dto.setId(original2.getId());
			}

			return dto;
		}

		return null;
	}

	@Override
	public boolean deleteLinkById(Long id) {
		if (id != null) {
			if (this.linkRepository.exists(id)) {
				this.linkRepository.delete(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteLinkByIds(Set<Long> ids) {
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				if (id != null) {
					this.linkRepository.delete(id);
				}
			}

			return true;
		}
		return false;
	}

	@Override
	public List<LinkDto> getAll() {
		return this.linkRepository.getAllDtos();
	}

	@Override
	public Page<LinkDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize) {
		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		String namecode = searchDto.getNameOrCode();

		String sql = " select new com.globits.wl.dto.LinkDto(fa) from Link fa  where (1=1)";
		String sqlCount = "select count(fa.id) from Link fa where (1=1)";
		String whereClause = "";
		if (namecode != null && namecode.length() > 0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode or fa.description like :namecode)";
		}

		sql += whereClause;
		sql += " order by fa.code asc ";
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, LinkDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
			qCount.setParameter("namecode", '%' + namecode + '%');
		}

		q.setFirstResult((pageIndex) * pageSize);
		q.setMaxResults(pageSize);

		Long numberResult = (Long) qCount.getSingleResult();
		Page<LinkDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
		return page;
	}

	@Override
	public LinkDto checkDuplicateCode(String code) {
		LinkDto viewCheckDuplicateCodeDto = new LinkDto();
		Link au = null;
		List<Link> list = linkRepository.findByCode(code);
		if (list != null && list.size() > 0) {
			au = list.get(0);
		}
		if (list == null) {
			viewCheckDuplicateCodeDto.setDuplicate(false);
		} else if (list != null && list.size() > 0) {
			viewCheckDuplicateCodeDto.setDuplicate(true);
			viewCheckDuplicateCodeDto.setDupCode(au.getCode());
			viewCheckDuplicateCodeDto.setDupName(au.getName());
		}
		return viewCheckDuplicateCodeDto;
	}

}
