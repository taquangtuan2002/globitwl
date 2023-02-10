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
import com.globits.wl.domain.Certificate;
import com.globits.wl.dto.CertificateDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.CertificateRepository;
import com.globits.wl.service.CertificateService;

@Service
public class CertificateServiceImpl extends GenericServiceImpl<Certificate, Long> implements CertificateService {
	@Autowired
	private CertificateRepository CertificateRepository;

	@Override
	public Page<CertificateDto> getPageDto(int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
		return this.CertificateRepository.getPageDto(pageable);
	}

	@Override
	public List<CertificateDto> getAllDto() {
		return CertificateRepository.getAllDto();
	}

	@Override
	public CertificateDto getCertificateById(Long id) {
		if (id != null) {
			Certificate Certificate = null;
			Certificate = this.CertificateRepository.findOne(id);
			if (Certificate != null) {
				return new CertificateDto(Certificate);
			}
		}
		return null;
	}

	@Override
	public CertificateDto deleteById(Long id) {
		if (id != null) {
			CertificateDto CertificateDto = null;
			Certificate Certificate = new Certificate();
			Certificate = this.CertificateRepository.findOne(id);
			if (Certificate != null) {
				CertificateDto = new CertificateDto(Certificate);
				this.CertificateRepository.delete(id);
				return CertificateDto;
			}
		}
		return null;
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				this.CertificateRepository.delete(id);
			}
			return true;
		}
		return false;
	}

	@Override
	public CertificateDto createCertificate(CertificateDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Certificate certificate = null;
			if (dto.getId() != null) {
				certificate = this.CertificateRepository.findOne(dto.getId());
			}

			if (certificate == null) {
				certificate = new Certificate();
				certificate.setCreateDate(currentDate);
				certificate.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				certificate.setCode(dto.getCode());
			}
			if (dto.getName() != null) {
				certificate.setName(dto.getName());
			}

			if (dto.getDescription() != null) {
				certificate.setDescription(dto.getDescription());
			}

			Certificate certificate2 = this.CertificateRepository.save(certificate);

			dto.setId(certificate2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public CertificateDto updateCertificate(Long id, CertificateDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			Certificate certificate = null;
			if (id != null) {
				certificate = this.CertificateRepository.findOne(id);
			} else if (dto.getId() != null) {
				certificate = this.CertificateRepository.findOne(dto.getId());
			}

			if (certificate == null) {
				certificate = new Certificate();
				certificate.setCreateDate(currentDate);
				certificate.setCreatedBy(currentUserName);
			}
			if (dto.getCode() != null) {
				certificate.setCode(dto.getCode());
			}
			if (dto.getName() != null) {
				certificate.setName(dto.getName());
			}
			if (dto.getDescription() != null) {
				certificate.setDescription(dto.getDescription());
			}

			Certificate certificate2 = this.CertificateRepository.save(certificate);

			dto.setId(certificate2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public Page<CertificateDto> searchCertificateDto(SearchDto searchDto, int pageIndex, int pageSize) {

		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		String namecode = searchDto.getNameOrCode();

		String sql = "select new com.globits.wl.dto.CertificateDto(fa) from Certificate fa  where (1=1)";
		String sqlCount = "select count(fa.id) from Certificate fa  where (1=1)";
		String whereClause = "";
		if (namecode != null && namecode.length() > 0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}

		sql += whereClause;
		sql += " order by fa.code asc ";
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CertificateDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
			qCount.setParameter("namecode", '%' + namecode + '%');
		}

		q.setFirstResult((pageIndex) * pageSize);
		q.setMaxResults(pageSize);

		Long numberResult = (Long) qCount.getSingleResult();
		Page<CertificateDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
		return page;
	}

	@Override
	public CertificateDto checkDuplicateCode(String code) {
		CertificateDto viewCheckDuplicateCodeDto = new CertificateDto();
		Certificate au = null;
		List<Certificate> list = CertificateRepository.findByCode(code);
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

//	@Override
//	public InjectionTimeDto checkDuplicateCode(String code) {		
//		InjectionTimeDto viewCheckDuplicateCodeDto = new InjectionTimeDto();
//		InjectionTime au = null;
//		List<InjectionTime> list = CertificateRepository.findByCode(code);
//		if(list != null && list.size() > 0) {
//			au = list.get(0);
//		}
//		if(list == null) {
//			viewCheckDuplicateCodeDto.setDuplicate(false);
//		}else if(list != null && list.size() > 0) {
//			viewCheckDuplicateCodeDto.setDuplicate(true);
//			viewCheckDuplicateCodeDto.setDupCode(au.getCode());
//			viewCheckDuplicateCodeDto.setDupName(au.getName());
//		}
//		return viewCheckDuplicateCodeDto;
//	}
//

}
