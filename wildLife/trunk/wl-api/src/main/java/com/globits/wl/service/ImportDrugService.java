package com.globits.wl.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.ImportDrugDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ImportDrugSearchDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;

public interface ImportDrugService {

	Page<ImportDrugDto> getListImportDrug(int pageIndex, int pageSize);

	ImportDrugDto getImportDrugById(Long id);

	ImportDrugDto saveImportDrug(ImportDrugDto exportAnimalDto, Long id);

	ImportDrugDto removeImportDrug(Long id);

	Page<ImportDrugDto> searchDto(ImportDrugSearchDto dto, int pageIndex, int pageSize);
	
	public ReportManagerDto getSumQuantity(ImportDrugSearchDto paramDto);

	boolean validDelete(Long importId);

	ReportManagerDto reportInventory(ImportDrugSearchDto paramDto);
}
