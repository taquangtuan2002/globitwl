package com.globits.wl.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ExportEgg;
import com.globits.wl.dto.ExportEggDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ExportEggSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;

public interface ExportEggService extends GenericService<ExportEgg, Long>{

	Page<ExportEggDto> getPageDto(int pageIndex, int pageSize);

	List<ExportEggDto> getAllDto();

	ExportEggDto getExportEggById(Long id);

	ExportEggDto createExportEgg(ExportEggDto dto);

	ExportEggDto updateExportEgg(Long id, ExportEggDto dto);

	ExportEggDto deleteById(Long id);

	boolean deleteByIds(List<Long> ids);

	Page<ExportEggDto> searchExportEggDto(ExportEggSearchDto dto, int pageIndex, int pageSize);
	
	public ReportManagerDto getSumQuantity(ExportEggSearchDto paramDto);

	List<ImportExportAnimalDto> getExportEgg(ExportEggSearchDto paramDto) throws ParseException;

	String autoGenBathCode(Date importDate);

	public List<InventoryReportDto> getSumQuantity(ReportParamDto paramDto);

	public List<InventoryReportDto> getQuantityReport(ReportParamDto paramDto) throws ParseException;

	ReportManagerDto getTotalEggReport(ExportEggSearchDto paramDto);

	Double checkRemainQuantity(Long importEggId);

	Boolean checkDateImportUpdate(Date importDate, Long importEggId);
	public List<InventoryReportDto> percentOfGrowthEggReport(ReportParamDto paramDto)throws ParseException;

	List<InventoryReportDto> getExportEggFollowTheHerdReport(ReportParamDto paramDto) throws ParseException;
}
