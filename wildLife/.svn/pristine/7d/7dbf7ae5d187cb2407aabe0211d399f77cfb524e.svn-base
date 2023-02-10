package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.ExportAnimalDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;

public interface ExportAnimalService {

	Page<ExportAnimalDto> getListExportAnimal(int pageIndex, int pageSize);

	List<ExportAnimalDto> getAll();

	ExportAnimalDto getExportAnimalById(Long id);

	ExportAnimalDto saveExportAnimal(ExportAnimalDto exportAnimalDto, Long id);

	ExportAnimalDto removeExportAnimal(Long id);

	Page<ImportExportAnimalDto> searchDto(ImportExportAnimalSearchDto dto, int pageIndex, int pageSize);

}
