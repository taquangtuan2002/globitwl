package com.globits.wl.service;



import java.util.List;

import com.globits.wl.dto.functiondto.OrganizationSearchDto;
import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.AdministrativeOrganization;
import com.globits.wl.dto.AdministrativeOrganizationDto;
import com.globits.wl.dto.FmsOrganizationAdministrativeDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface AdministrativeOrganizationService extends GenericService<AdministrativeOrganization, Long>{
	List<AdministrativeOrganizationDto> getAll();
	
	AdministrativeOrganizationDto getById(Long id);
	
	AdministrativeOrganizationDto saveOrUpdate(AdministrativeOrganizationDto dto, Long id);
	
	AdministrativeOrganizationDto deleteAdministrativeOrganization(Long id);
	
	Page<AdministrativeOrganizationDto> getListTree(SearchReportPeriodDto searchDto);
	
	// Nút gốc
	List<FmsOrganizationAdministrativeDto> getParent(SearchReportPeriodDto searchDto);
	
	// List nút lá
	List<AdministrativeOrganizationDto> getChildrenByParentId(Long parentId);

	List<AdministrativeOrganizationDto> getAdministrativeOrganizationByUser();

	Page<AdministrativeOrganizationDto> getListTree2(SearchReportPeriodDto searchDto);
	
	List<AdministrativeOrganizationDto> getOrganization(OrganizationSearchDto search);
	
}
