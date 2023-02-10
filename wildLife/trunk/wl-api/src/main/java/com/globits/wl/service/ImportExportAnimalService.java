package com.globits.wl.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.AnimalRaisingSearchDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.FarmAnimal2017Dto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.AnimalRaisingReportDto;
import com.globits.wl.dto.report.FarmSummaryReportDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.Report16FormDto;
import com.globits.wl.dto.report.TotalReportDto;

public interface ImportExportAnimalService extends GenericService<ImportExportAnimal, Long>{

	public Page<ImportExportAnimalDto> getByPage(int pageIndex, int pageSize,int type);

	public Set<ImportExportAnimalDto> getAll(int type);

	public ImportExportAnimalDto getById(Long id);

	public ObjectDto deleteById(Long id);

	public ImportExportAnimalDto save(Long id, ImportExportAnimalDto dto);
	
	public List<InventoryReportDto> getSumQuantity(ReportParamDto paramDto);
	
	public Page<ImportExportAnimalDto> searchDto(ImportExportAnimalSearchDto searchDto, int pageIndex, int pageSize);

	public List<InventoryReportDto> getQuantityReport(ReportParamDto paramDto) throws ParseException;
	
	public String autoGenBathCode(Date importDate);
	
	public List<ImportExportAnimalDto> saveOrUpdateList(List<ImportExportAnimalDto> list);
	public List<ImportExportAnimalDto> searchDto(ImportExportAnimalSearchDto searchDto);
	public void updateAllFarmProductTargetExist();
	public void updateAllFarmAnimalProductTargetExist();

	public List<FarmSummaryReportDto> farmSummaryReport(List<InventoryReportDto> list);
	
	public List<InventoryReportDto> percentOfGrowthQuantityReport(ReportParamDto paramDto)throws ParseException;
	public List<InventoryReportDto> percentOfGrowthMeatReport(ReportParamDto paramDto)throws ParseException;

	public List<ImportExportAnimalDto> getAllBySearch(ImportExportAnimalSearchDto searchDto);

	public TotalReportDto getTotalReport(Date fromDate, Date toDate);

	public List<AnimalRaisingReportDto> getReportAnimalRaising(AnimalRaisingSearchDto search);

	public List<AnimalRaisingReportDto> getAnimalWithTotalQuantity(AnimalRaisingSearchDto paramDto);

	public List<AnimalRaisingReportDto> getFluctuationHerd(AnimalRaisingSearchDto paramDto);

	public void saveFarmAnimalList(List<FarmAnimal2017Dto> listInput);

	public void saveImportAnimalBear(List<FarmAnimal2017Dto> listInput);

	public List<Report16FormDto> getListReport16FormDto(Integer month, Integer year, Long farmId);

	public List<AnimalReportDataDto> getAllLessThanDate(AnimalReportDataSearchDto searchDto);

	public List<Report16FormDto> getReport16FormDto(Date fromDate, Date toDate, Long farmId, Long animalId);

	public InventoryReportDto getInventoryReportRemainQuantity(ReportParamDto dto);
}
