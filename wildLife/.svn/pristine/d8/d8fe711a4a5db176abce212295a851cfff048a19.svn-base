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

import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.functiondto.AnimalRaisingSearchDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.AnimalRaisingReportDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.Report16FormDto;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/import_export/animal")
public class RestImportExportAnimalController {
	@Autowired
	private ImportExportAnimalService importAnimalService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/page/{type}/{pageIndex}/{pageSize}",method=RequestMethod.GET)
	public Page<ImportExportAnimalDto> getByPage(@PathVariable("type")int type,@PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize) {
		return this.importAnimalService.getByPage(pageIndex,pageSize,type);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getall/{type}",method=RequestMethod.GET)
	public Set<ImportExportAnimalDto> getAll(@PathVariable("type")int type){
		return this.importAnimalService.getAll(type);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/get/{id}",method=RequestMethod.GET)
	public ImportExportAnimalDto getById(@PathVariable("id") Long id) {
		return this.importAnimalService.getById(id);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public ObjectDto deleteImportAnimal(@PathVariable("id") Long id) {
		ObjectDto dto=new ObjectDto();
		try {
			dto=this.importAnimalService.deleteById(id); 
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value="/update/{id}",method=RequestMethod.PUT)
	public ImportExportAnimalDto updateImportAnimal(@PathVariable("id")Long id,@RequestBody ImportExportAnimalDto dto) {
		return this.importAnimalService.save(id,dto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER"})
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public ImportExportAnimalDto createImportAnimal(@RequestBody ImportExportAnimalDto dto) {
		return this.importAnimalService.save(null,dto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/inventory",method=RequestMethod.POST)
	public List<InventoryReportDto> getInventory(ReportParamDto paramDto) {
		try {
			return this.importAnimalService.getQuantityReport(paramDto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<ImportExportAnimalDto>> getListSimpleByNameCode(@RequestBody ImportExportAnimalSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<ImportExportAnimalDto> results = this.importAnimalService.searchDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<ImportExportAnimalDto>>(results, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/autoGenericBatchCode/{importDate}",method=RequestMethod.GET)
	public ImportExportAnimalDto getBatchCode(@PathVariable("importDate") Date importDate) {
		ImportExportAnimalDto dto=new ImportExportAnimalDto();
		String code=this.importAnimalService.autoGenBathCode(importDate);
		dto.setBatchCode(code);
		return dto;
	}
	@RequestMapping(value="/updateAllFarmProductTargetExist",method=RequestMethod.GET)
	public void updateAllFarmProductTargetExist() {		
		this.importAnimalService.updateAllFarmProductTargetExist();		
	}
	@RequestMapping(value="/updateAllFarmAnimalProductTargetExist",method=RequestMethod.GET)
	public void updateAllFarmAnimalProductTargetExist() {		
		this.importAnimalService.updateAllFarmAnimalProductTargetExist();		
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/percentOfGrowthQuantity",method=RequestMethod.POST)
	public List<InventoryReportDto> percentOfGrowthQuantity(@RequestBody ReportParamDto paramDto) {
		try {
			return this.importAnimalService.percentOfGrowthQuantityReport(paramDto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/percentOfGrowthMeat",method=RequestMethod.POST)
	public List<InventoryReportDto> percentOfGrowthMeat(@RequestBody ReportParamDto paramDto) {
		try {
			return this.importAnimalService.percentOfGrowthMeatReport(paramDto);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	// Thông tin về quần thể các loài động vật hoang dã gây nuôi (trừ gấu).
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getReportAnimalRaising",method=RequestMethod.POST)
	public List<AnimalRaisingReportDto> getReportAnimalRaising(@RequestBody AnimalRaisingSearchDto paramDto) {
		return this.importAnimalService.getReportAnimalRaising(paramDto);
	}
	//Thông tin về các loài động vật nuôi, động vật cảnh tại cơ sở.
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getAnimalWithTotalQuantity",method=RequestMethod.POST)
	public List<AnimalRaisingReportDto> getAnimalWithTotalQuantity(@RequestBody AnimalRaisingSearchDto paramDto) {
		return this.importAnimalService.getAnimalWithTotalQuantity(paramDto);
	}
//	SỔ THEO DÕI HOẠT ĐỘNG NUÔI ĐỘNG VẬT RỪNG NGUY CẤP, QUÝ, HIẾM, ĐỘNG VẬT HOANG  
//	DÃ NGUY CẤP THUỘC PHỤ LỤC CITES, ĐỘNG VẬT RỪNG THÔNG THƯỜNG(Không áp dụng cho cơ sở nuôi sinh trưởng)
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getFluctuationHerd",method=RequestMethod.POST)
	public List<AnimalRaisingReportDto> getFluctuationHerd(@RequestBody AnimalRaisingSearchDto paramDto) {
		return this.importAnimalService.getFluctuationHerd(paramDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getListReport16FormDto/{month}/{year}/{farmId}",method=RequestMethod.GET)
	public List<Report16FormDto> getListReport16FormDto(@PathVariable Integer month,@PathVariable Integer year,@PathVariable Long farmId) {
		return this.importAnimalService.getListReport16FormDto(month, year, farmId);
	}
	
	// Lấy ra toàn bộ số lượng còn đực cái không xác định còn lại theo loài và kiểu loài , farmId
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getInventoryReportRemainQuantity",method=RequestMethod.POST)
	public InventoryReportDto getInventoryReportRemainQuantity(@RequestBody ReportParamDto dto) {
		return this.importAnimalService.getInventoryReportRemainQuantity(dto);
	}
}
