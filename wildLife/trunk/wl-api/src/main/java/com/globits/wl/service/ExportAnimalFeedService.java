package com.globits.wl.service;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.ImportAnimalFeedDto;
import com.globits.wl.dto.functiondto.ImportAnimalFeedSearchDto;

public interface ExportAnimalFeedService {

	Page<ImportAnimalFeedDto> getListExportAnimalFeed(int pageIndex, int pageSize);

	ImportAnimalFeedDto getExportAnimalFeedById(Long id);

	ImportAnimalFeedDto saveExportAnimalFeed(ImportAnimalFeedDto exportAnimalDto, Long id);

	ImportAnimalFeedDto removeExportAnimalFeed(Long id);

	Page<ImportAnimalFeedDto> searchDto(ImportAnimalFeedSearchDto dto, int pageIndex, int pageSize);

}
