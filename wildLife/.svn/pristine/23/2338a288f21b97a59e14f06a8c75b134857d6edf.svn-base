package com.globits.wl.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ReportFormAnimalEgg;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.ReportFormAnimalEggDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.ReportFormAnimalEggSearchDto;
import com.globits.wl.dto.functiondto.SearchReportForm16Dto;

public interface ReportFormAnimalEggService extends GenericService<ReportFormAnimalEgg, Long>{

	ReportFormAnimalEggDto save(ReportFormAnimalEggDto dto);

	List<ReportFormAnimalEggDto> getAll(AnimalReportDataSearchDto searchDto);

	Page<ReportFormAnimalEggDto> getPage(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize);

	ReportFormAnimalEggDto getById(Long id);

	Boolean deleteById(Long id);
	Integer quantityChildIncrement(ReportFormAnimalEggSearchDto dto);

	List<ReportFormAnimalEggDto> saveList(List<ReportFormAnimalEggDto> dto);
	public Page<ReportFormAnimalEggDto> searchByPage(ReportFormAnimalEggSearchDto searchDto) ;
	public List<ReportFormAnimalEggDto> getList(ReportFormAnimalEggSearchDto searchDto) ;
	
}
