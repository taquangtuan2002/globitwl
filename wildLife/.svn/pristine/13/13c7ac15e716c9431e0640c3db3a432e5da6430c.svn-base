package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.AnimalCertificate;
import com.globits.wl.dto.AnimalCertificateDto;
import com.globits.wl.dto.ForestProductsListDetailDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface AnimalCertificateService extends GenericService<AnimalCertificate, Long> {

	Page<AnimalCertificateDto> getPageDto(int pageIndex, int pageSize);

	List<AnimalCertificateDto> getAllDto();

	AnimalCertificateDto getAnimalCertificateById(Long id);

	AnimalCertificateDto deleteById(Long id);

	boolean deleteByIds(List<Long> ids);

	AnimalCertificateDto createOrUpdateAnimalCertificate(Long id,AnimalCertificateDto dto);

	Page<AnimalCertificateDto> searchAnimalCertificateDto(SearchDto dto, int pageIndex, int pageSize);
	
	Page<AnimalCertificateDto> searchByPage(SearchReportPeriodDto searchDto);

	AnimalCertificateDto checkDuplicateCode(String code);

	List<ForestProductsListDetailDto> findAllByAC(AnimalReportDataSearchDto searchDto);



	
}
