package com.globits.wl.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.ExportAnimalDto;
import com.globits.wl.dto.ImportAnimalFeedDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ImportAnimalFeedSearchDto;
import com.globits.wl.dto.functiondto.ImportDrugSearchDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;

public interface ImportAnimalFeedService {

	Page<ImportAnimalFeedDto> getListImportAnimalFeed(int pageIndex, int pageSize);

	ImportAnimalFeedDto getImportAnimalFeedById(Long id);

	ImportAnimalFeedDto saveImportAnimalFeed(ImportAnimalFeedDto exportAnimalDto, Long id);

	ImportAnimalFeedDto removeImportAnimalFeed(Long id);

	Page<ImportAnimalFeedDto> searchDto(ImportAnimalFeedSearchDto dto, int pageIndex, int pageSize);
	public ReportManagerDto getSumQuantity(ImportAnimalFeedSearchDto paramDto);

	ReportManagerDto reportInventory(ImportAnimalFeedSearchDto paramDto);

	boolean validDateIssue(Long id);
}
