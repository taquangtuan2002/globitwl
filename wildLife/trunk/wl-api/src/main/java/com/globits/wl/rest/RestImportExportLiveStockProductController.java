package com.globits.wl.rest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.dto.ImportExportLiveStockProductDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.LiveStockProductReportDto;
import com.globits.wl.service.ImportExportLivelStockProductService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/import_export/liveStockProduct")
public class RestImportExportLiveStockProductController {
	@Autowired
	private ImportExportLivelStockProductService importExportLivelStockProductService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/page/{type}/{pageIndex}/{pageSize}",method=RequestMethod.GET)
	public Page<ImportExportLiveStockProductDto> getByPage(@PathVariable("type")int type,@PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize) {
		return this.importExportLivelStockProductService.getByPage(pageIndex,pageSize,type);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/get/{id}",method=RequestMethod.GET)
	public ImportExportLiveStockProductDto getById(@PathVariable("id") Long id) {
		return this.importExportLivelStockProductService.getById(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<ImportExportLiveStockProductDto>> getListSimpleByNameCode(@RequestBody ImportExportAnimalSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<ImportExportLiveStockProductDto> results = this.importExportLivelStockProductService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<ImportExportLiveStockProductDto>>(results, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public ImportExportLiveStockProductDto createImportExportLiveStockProduct(@RequestBody ImportExportLiveStockProductDto dto) {
		return this.importExportLivelStockProductService.save(null,dto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value="/update/{id}",method=RequestMethod.PUT)
	public ImportExportLiveStockProductDto updateImportExportLiveStockProduct(@PathVariable("id")Long id,@RequestBody ImportExportLiveStockProductDto dto) {
		return this.importExportLivelStockProductService.save(id,dto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public ObjectDto deleteImportExport(@PathVariable("id") Long id) {
		ObjectDto dto=new ObjectDto();
		try {
			dto=this.importExportLivelStockProductService.deleteById(id); 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/report",method=RequestMethod.POST)
	public List<LiveStockProductReportDto> getInventory(@RequestBody ReportParamDto paramDto) {
		try {
			return this.importExportLivelStockProductService.getQuantityReport(paramDto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
