package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.AnimalReportDataFormDto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto;
import com.globits.wl.dto.functiondto.SearchDto;


public interface AnimalReportDataService  extends GenericService<AnimalReportData, Long>{

	public AnimalReportDataDto saveAnimalReport(Long id, AnimalReportDataDto dto);

	public Boolean deleteById(Long id);

	public AnimalReportDataDto getById(Long id);

	public List<AnimalReportData> getList(Long provinceId, Long districtId, Long communeId, Integer year, Integer quarter,	Integer month, Integer day, Long farmId, Long animalId);

	public List<AnimalReportData> getListAnimalReportData(AnimalReportDataSearchDto searchDto);

	public Integer saveAnimalReport(AnimalReportDataFormDto dto);

	public Integer saveListAnimalReport(List<AnimalReportDataFormDto> dtos);

	public List<ReportAccordingToTheRedBookDto> reportAnimalAccordingBySearch(AnimalReportDataSearchDto searchDto);

	public Integer saveListBearReport(List<AnimalReportDataFormDto> dtos);

	public Integer saveNormalAnimal(List<AnimalReportDataFormDto> dtos);

	public List<AnimalReportDataDto> convertFromImportExportAnimal2ReportAnimalData(
			AnimalReportDataSearchDto searchDto);
	
	public AnimalReportDataDto saveUpdateAnimalReportBy16A(AnimalReportDataDto dto);
	public List<AnimalReportDataDto> saveUpdateListAnimalReportBy16A(List<AnimalReportDataDto> dtos);

	public Page<AnimalReportDataDto> getPageBySearch(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize);

	public Page<AnimalReportDataDto> getViewExcelExportAnimalReportData(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize);

	public List<AnimalDto> getAllAnimalReported(Long farmId);

	public List<FarmAnimalTotalDto> getAllAnimalLastReported(Long farmId);

	List<FarmAnimalTotalDto> getAllAnimalLastReportedByFarmIdAndAnimalId(Long farmId, Long animalId);

	List<AnimalReportData> updateAnimalReportDataByFarmAnimalYear(Long farmId, List<Long> animalIds, Integer year);

	/** Bản ghi các animal cuối cùng của 1 trại theo bản ghi có month is null and day is null*/
	List<FarmAnimalTotalDto> getAllAnimalLastReportedByRecordMonthDayIsNull(Long farmId, Integer ỷear);
	
	List<AnimalReportData> updateOneFarmtoMap(Boolean isConvert, String farmCode, int year);

	List<AnimalReportDataDto> updateFarmAnimalYearForAllFarm(int pageIndex, int pageSize, int year, FarmSearchDto dto);

	List<AnimalReportDataDto> updateAllFarmToMap(int pageIndex, int pageSize, int year, FarmSearchDto farmSearchDto);
	
	void updateAnimalReportDataToRecordMonthDayIsNull(FarmSearchDto farmSearchDto,int pageIndex,int pageSize, int year);
	void updateAdministrative();

	public boolean deleteDataMapByAdministrativeUnit(FarmSearchDto farmSearchDto);

	List<AnimalReportData> updateAnimalReportDataByFarmAnimalYearConvertOnly(Long farmId, List<Long> animalIds, Integer year);
}
