package com.globits.wl.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ImportExportLiveStockProduct;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.ImportExportLiveStockProductDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.LiveStockProductReportDto;


public interface ImportExportLivelStockProductService extends GenericService<ImportExportLiveStockProduct, Long>{
	public Page<ImportExportLiveStockProductDto> getByPage(int pageIndex, int pageSize,int type);

	public ImportExportLiveStockProductDto getById(Long id);

	public ImportExportLiveStockProductDto save(Long id, ImportExportLiveStockProductDto dto);	
	
	public Page<ImportExportLiveStockProductDto> searchDto(ImportExportAnimalSearchDto searchDto, int pageIndex, int pageSize);
	public String autoGenBathCode(Date importDate);
	public ObjectDto deleteById(Long id);
	public List<LiveStockProductReportDto> getSumQuantity(ReportParamDto paramDto);
	public List<LiveStockProductReportDto> getQuantityReport(ReportParamDto paramDto) throws ParseException;
}
