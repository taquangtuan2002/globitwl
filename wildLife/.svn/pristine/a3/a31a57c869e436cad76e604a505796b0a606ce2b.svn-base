package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.AdministrativeUnitEditableDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;

import java.util.List;

public interface AdministrativeUnitEditableService {

	Page<AdministrativeUnitEditableDto> findPage(AnimalReportDataSearchDto dto);
	AdministrativeUnitEditableDto saveOrUpdate(AdministrativeUnitEditableDto dto);
	AdministrativeUnitEditableDto getAdministrativeUnitEditableById(Long id);
	List<AdministrativeUnitEditableDto> getAdministrativeUnitEditableByAdminUnit(Long id);
	boolean deleteById(Long id);
}
