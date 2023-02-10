package com.globits.wl.rest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

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

import com.globits.wl.dto.ExportEggDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ExportEggSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.service.ExportEggService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/export_egg")
public class RestExportEggCotroller {
	
	@Autowired
	private ExportEggService exportEggService;
	
	@RequestMapping(value="page/{pageIndex}/{pageSize}",method=RequestMethod.GET)
	public Page<ExportEggDto> getPageDto(@PathVariable("pageIndex") int pageIndex,@PathVariable("pageSize")int pageSize){
		return this.exportEggService.getPageDto(pageIndex,pageSize);
	}
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	private List<ExportEggDto> getAll() {
		return this.exportEggService.getAllDto();
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	private ExportEggDto getExportEggById(@PathVariable("id") Long id) {
		return this.exportEggService.getExportEggById(id);
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private ExportEggDto createExportEgg(@RequestBody ExportEggDto dto) {
		return this.exportEggService.createExportEgg(dto);
	}
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	private ExportEggDto updateExportEgg(@PathVariable("id") Long id,@RequestBody ExportEggDto dto) {
		return this.exportEggService.updateExportEgg(id,dto);
	}
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	private ExportEggDto deleteById(@PathVariable("id") Long id) {
		return this.exportEggService.deleteById(id);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	private boolean deleteByIds(@RequestBody List<Long> ids) {
		return this.exportEggService.deleteByIds(ids);
	}
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<ExportEggDto>> getListSimpleByNameCode(@RequestBody ExportEggSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<ExportEggDto> results = this.exportEggService.searchExportEggDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<ExportEggDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/inventoryEgg",method=RequestMethod.POST)
	public ReportManagerDto getInventory(@RequestBody ExportEggSearchDto paramDto) {
		return this.exportEggService.getSumQuantity(paramDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getExportEgg",method=RequestMethod.POST)
	public List<ImportExportAnimalDto> getExportEgg(@RequestBody ExportEggSearchDto paramDto) throws ParseException {
		return this.exportEggService.getExportEgg(paramDto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/eggQuantityReport",method=RequestMethod.POST)
	public List<InventoryReportDto> getQuantityReport(@RequestBody ReportParamDto paramDto) throws ParseException{
		return this.exportEggService.getQuantityReport(paramDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getTotalEggReport",method=RequestMethod.POST)
	public ReportManagerDto getTotalEggReport(@RequestBody ExportEggSearchDto paramDto) {
		return this.exportEggService.getTotalEggReport(paramDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER"})
	@RequestMapping(value="/checkRemainQuantity/{id}",method=RequestMethod.GET)
	public Double checkRemainQuantity(@PathVariable("id") Long id) {
		return this.exportEggService.checkRemainQuantity(id);
	}
	
	// api kiểm tra về true nếu ngày nhập trứng nhỏ hơn ngày xuất trứng của tất cả các bản ghi xuất trứng có cùng 1 mã lô với bản ghi đang update
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER"})
	@RequestMapping(value="/checkImportEggValidDate/{importEggId}",method=RequestMethod.POST)
	public Boolean checkDateImportUpdate(@RequestBody Date dateImport ,@PathVariable("importEggId") Long importEggId) {
		return this.exportEggService.checkDateImportUpdate(dateImport,importEggId);
	}
	//báo cáo phần trăm tăng trưởng sản lượng trứng
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/percentOfGrowthEgg",method=RequestMethod.POST)
	public List<InventoryReportDto> percentOfGrpercentOfGrowthEggowthQuantity(@RequestBody ReportParamDto paramDto) {
		try {
			return this.exportEggService.percentOfGrowthEggReport(paramDto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	//báo cáo phần trăm tăng trưởng sản lượng trứng
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/export_egg_follow_the_herd",method=RequestMethod.POST)
	public List<InventoryReportDto> getExportEggFollowTheHerdReport(@RequestBody ReportParamDto paramDto) {
		try {
			return this.exportEggService.getExportEggFollowTheHerdReport(paramDto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
