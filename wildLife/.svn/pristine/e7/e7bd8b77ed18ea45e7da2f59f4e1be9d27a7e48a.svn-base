package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.functiondto.DataDvhdDto;
import com.globits.wl.dto.functiondto.ImportResultDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface ReportPeriodService extends GenericService<ReportPeriod, Long> {

	ReportPeriodDto saveOrUpdate(ReportPeriodDto dto);

	Page<ReportPeriodDto> searchByPage(SearchReportPeriodDto searchDto);

	ReportPeriodDto getById(Long id);

	Boolean checkDuplicateYearMonthDate(SearchReportPeriodDto searchDto);
	
	void updateDataFromReportPeriodToAnimalReportData(ReportPeriodDto dto);

	List<ReportPeriodDto> getAll(SearchReportPeriodDto searchDto);

	void updateListFromFileImport(List<DataDvhdDto> list);

	List<ReportPeriod> getAllEntity(SearchReportPeriodDto searchDto);

	int updateDataFromReportPeriodToAnimalReportDataForAll(SearchReportPeriodDto searchDto);

	Page<ReportPeriodDto> getReport16aBySearchDto(SearchReportPeriodDto searchDto);

	void updateAnimalReportDataBeforeDeleteReportPeriod(ReportPeriod reportPeriod);

	void updateAnimalReportDataBeforeDeleteReportForm16(ReportForm16 reportForm16);

	List<AnimalReportData> findByYearAndMonthFarmAnimal(Integer year, Integer month, Integer day, Long farmId,
			Long animalId, int type);

	ImportResultDto<DataDvhdDto> updateFromFileImport(List<DataDvhdDto> list);

	List<AnimalReportData> updateDataFromReportForm16ToAnimalReportDataByEntityConvertOnly(ReportForm16 reportForm16);

	DataDvhdDto updateFromFileImportByOneRow(DataDvhdDto rawData);

	List<Long> getListAnimalIds(Long farmId, Integer year);
	
	ReportPeriodDto getLastRecordReportPeriodByFarmAndAnimal(SearchReportPeriodDto searchDto);
	
}
