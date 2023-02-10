package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.Certificate;
import com.globits.wl.domain.InjectionTime;
import com.globits.wl.dto.CertificateDto;
import com.globits.wl.dto.InjectionTimeDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface CertificateService extends GenericService<Certificate, Long> {

	Page<CertificateDto> getPageDto(int pageIndex, int pageSize);

	List<CertificateDto> getAllDto();

	CertificateDto getCertificateById(Long id);

	CertificateDto deleteById(Long id);

	boolean deleteByIds(List<Long> ids);

	CertificateDto createCertificate(CertificateDto dto);

	CertificateDto updateCertificate(Long id, CertificateDto dto);

	Page<CertificateDto> searchCertificateDto(SearchDto dto, int pageIndex, int pageSize);

	CertificateDto checkDuplicateCode(String code);



	
}
