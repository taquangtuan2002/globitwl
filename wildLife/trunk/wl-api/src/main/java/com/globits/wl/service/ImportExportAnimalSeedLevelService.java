package com.globits.wl.service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.ReportSeedLevelDto;
import com.globits.wl.dto.report.ReportSeedLevelProducTargetDto;

public interface ImportExportAnimalSeedLevelService extends GenericService<ImportExportAnimal, Long>{

	
	public List<InventoryReportDto> getSumQuantity(ReportParamDto paramDto);

	public List<InventoryReportDto> getQuantityReport(ReportParamDto paramDto) throws ParseException;
	
	public List<ReportSeedLevelDto> getListReportSeedLevel(ReportParamDto paramDto) throws ParseException;
	public List<ReportSeedLevelProducTargetDto> getListReportSeedLevelProductTarget(ReportParamDto paramDto) throws ParseException,UnsupportedEncodingException;
	
}
