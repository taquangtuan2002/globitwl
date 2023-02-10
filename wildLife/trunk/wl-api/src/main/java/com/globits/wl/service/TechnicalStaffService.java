package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.globits.wl.domain.TechnicalStaff;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.TechnicalStaffDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface TechnicalStaffService {
	Page<TechnicalStaffDto> getPageTechnicalStaff(int pageIndex, int pageSize);
	Page<TechnicalStaffDto> searchDto(SearchReportPeriodDto searchDto, int pageIndex, int pageSize);
	public List<TechnicalStaffDto> getAll();
	public List<TechnicalStaffDto> getStaffFromProvince(Long id);
	public TechnicalStaffDto deleteTechnicalStaff(Long id);
	ResponseEntity<Boolean> deleteTechnicalStaffs(List<Long> ids);
	public TechnicalStaffDto saveStaffFromProvince(TechnicalStaffDto dto, Long id);
//	public TechnicalStaff saveOne(TechnicalStaff t);
	public TechnicalStaff findById(Long id);
	public TechnicalStaffDto getTechnicalStaffById(Long id);
//	public List<TechnicalStaffDto> getTechnicalStaffByName(String name);
	public TechnicalStaffDto checkDuplicateCode(String code);

	
}