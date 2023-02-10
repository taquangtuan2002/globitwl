package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ReportFormAnimalGiveBirth;
import com.globits.wl.dto.ReportFormAnimalGiveBirthDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.ReportFormAnimalEggSearchDto;

public interface ReportFormAnimalGiveBirthService extends GenericService<ReportFormAnimalGiveBirth, Long>{

	List<ReportFormAnimalGiveBirthDto> getAll(AnimalReportDataSearchDto searchDto);

	Page<ReportFormAnimalGiveBirthDto> getPage(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize);

	Boolean deleteById(Long id);

	ReportFormAnimalGiveBirthDto getById(Long id);

	ReportFormAnimalGiveBirthDto save(ReportFormAnimalGiveBirthDto dto);

	ReportFormAnimalGiveBirthDto getNumberOfChildrenInTheCommunityOverTime(AnimalReportDataSearchDto searchDto);

	ReportFormAnimalGiveBirthDto getReportFormAnimalGiveBirthDtoById(Long id);

	List<ReportFormAnimalGiveBirthDto> saveList(List<ReportFormAnimalGiveBirthDto> dto);

	Integer getGiveBirthQuantityChildIncrement(ReportFormAnimalEggSearchDto searchDto);
	public Page<ReportFormAnimalGiveBirthDto> searchByPage(ReportFormAnimalEggSearchDto searchDto) ;
	public List<ReportFormAnimalGiveBirthDto> getList(ReportFormAnimalEggSearchDto searchDto) ;
	
}
