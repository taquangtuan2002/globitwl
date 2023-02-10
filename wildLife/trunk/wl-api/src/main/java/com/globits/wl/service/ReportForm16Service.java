package com.globits.wl.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.functiondto.SearchReportForm16Dto;

public interface ReportForm16Service extends GenericService<ReportForm16, Long> {

	Page<ReportForm16Dto> searchByPage(SearchReportForm16Dto searchDto);

	ReportForm16Dto saveOrUpdate(ReportForm16Dto dto);

	ReportForm16Dto getById(Long id);
	List<ReportForm16Dto> saveList(List<ReportForm16Dto> listDto);

	List<ReportForm16Dto> createByImportExportAnimal(ImportExportAnimal ie);
	
	List<ReportPeriod> getListImportDataReportForm16(SearchReportForm16Dto searchDto);
	
	List<ReportForm16> getListReportForm16(SearchReportForm16Dto searchDto);
	
	List<ReportForm16> getListBy(Long farmId, Long animalId, Integer year);
	
	List<ReportForm16Dto> getListReportForm16Dto(SearchReportForm16Dto searchDto);
	
	List<ReportForm16Dto> getListReportForm16DtoNativeQueryForExport(SearchReportForm16Dto searchDto);
	
	List<ReportForm16Dto> getListReportForm16DtoDetail(SearchReportForm16Dto searchDto);
	
	//tran huu dat them hàm lấy form 16 theo farm start
	List<ReportForm16Dto> getListReportForm16ByFarmId(long farmId);
	
	List<ReportForm16Dto> getListReportForm16ByFarmIdAndAnimalId(long farmId, long animalId);
	//tran huu dat them hàm lấy form 16 theo farm end
	
	//tran huu dat them ham xoa form16 theo id start
	ReportForm16Dto deleteById(Long idForm16);
	
	//tran huu dat them hàm xoa form16 theo id end
	public List<AnimalDto> getListAnimalFromReportForm16BySearch(SearchReportForm16Dto dto);
	
	public List<ReportForm16Dto> mergePeriodForForm16(List<ReportForm16Dto> listDto, Long id, Long dupId);
}
