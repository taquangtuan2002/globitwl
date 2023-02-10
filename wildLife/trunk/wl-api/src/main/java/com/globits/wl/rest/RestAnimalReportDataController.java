package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.AnimalReportDataFormDto;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.AnimalSearchDto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.AnimalService;
import com.globits.wl.utils.WLConstant;


@RestController
@RequestMapping("/api/animal_report")
public class RestAnimalReportDataController {
	@Autowired
	private AnimalReportDataService animalReportDataService;
	@Autowired
	AnimalReportDataRepository animalReportDataRepository;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/{pageIndex}/{pageSize}",method=RequestMethod.GET)
	public Page<AnimalReportDataDto> getByFarmId(@PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize){
		if(pageIndex<1) pageIndex=1;
		else pageIndex=pageIndex-1;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.animalReportDataRepository.getByPage(pageable);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="getPageBySearch/{pageIndex}/{pageSize}",method=RequestMethod.POST)
	public Page<AnimalReportDataDto> getPageBySearch(@RequestBody AnimalReportDataSearchDto searchDto, @PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize){
		
		return this.animalReportDataService.getPageBySearch(searchDto, pageIndex, pageSize);
	}
	
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getByFarmId/{farmId}",method=RequestMethod.GET)
	public List<AnimalReportDataDto> getByFarmId(@PathVariable("farmId")Long farmId){
		return this.animalReportDataRepository.getListByFarmId(farmId);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getPageByFarmId/{farmId}/{pageIndex}/{pageSize}",method=RequestMethod.GET)
	public Page<AnimalReportDataDto> getPageByFarmId(@PathVariable("farmId")Long farmId, @PathVariable("pageIndex")int pageIndex, @PathVariable("pageSize")int pageSize){
		if(pageIndex > 0) {
			pageIndex--;
		}else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.animalReportDataRepository.getPageByFarmId(farmId, pageable);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public AnimalReportDataDto getById(@PathVariable("id")Long id){
		return this.animalReportDataService.getById(id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(method=RequestMethod.POST)
	public AnimalReportDataDto create(@RequestBody AnimalReportDataDto dto){
		return animalReportDataService.saveAnimalReport((Long)null, dto);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public AnimalReportDataDto update(@RequestBody AnimalReportDataDto dto, @PathVariable("id")Long id){
		return animalReportDataService.saveAnimalReport(id, dto);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Boolean update( @PathVariable("id")Long id){
		return animalReportDataService.deleteById(id);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/saveListAnimalReport",method=RequestMethod.POST)
	public Integer saveListAnimalReport(@RequestBody List<AnimalReportDataFormDto> dtos) {
		return animalReportDataService.saveListAnimalReport(dtos);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/saveListBearReport",method=RequestMethod.POST)
	public Integer saveListBearReport(@RequestBody List<AnimalReportDataFormDto> dtos) {
		return animalReportDataService.saveListBearReport(dtos);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/saveNormalAnimal",method=RequestMethod.POST)
	public Integer saveNormalAnimal(@RequestBody List<AnimalReportDataFormDto> dtos) {
		return animalReportDataService.saveNormalAnimal(dtos);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value="/reportAnimalDataAccordingBySearch",method=RequestMethod.POST)
	public List<ReportAccordingToTheRedBookDto> reportAnimalAccordingBySearch(@RequestBody AnimalReportDataSearchDto searchDto) {
		return animalReportDataService.reportAnimalAccordingBySearch(searchDto);
	}
	
	/**Đồng bộ dữ liệu theo tháng/ Năm từ importExportAnimal => ReportAnimalData */
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value="/convertFromImportExportAnimal2ReportAnimalData",method=RequestMethod.POST)
	public List<AnimalReportDataDto> convertFromImportExportAnimal2ReportAnimalData(@RequestBody AnimalReportDataSearchDto searchDto) {
		return animalReportDataService.convertFromImportExportAnimal2ReportAnimalData(searchDto);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value="/getViewExcelExportAnimalReportData/{pageIndex}/{pageSize}",method=RequestMethod.POST)
	public Page<AnimalReportDataDto> getViewExcelExportAnimalReportData(@RequestBody AnimalReportDataSearchDto searchDto, @PathVariable("pageIndex")int pageIndex, @PathVariable("pageSize")int pageSize) {
		return animalReportDataService.getViewExcelExportAnimalReportData(searchDto, pageIndex, pageSize);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value="/getAllAnimalReported/{farmId}",method=RequestMethod.POST)
	public List<AnimalDto> getAllAnimalReported(@PathVariable("farmId")Long farmId) {
		return animalReportDataService.getAllAnimalReported(farmId);
	}
	/** API return animalReportData by farm id with last update by each animals */
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value="/getAllAnimalLastReported/{farmId}",method=RequestMethod.POST)
	public List<FarmAnimalTotalDto> getAllAnimalLastReported(@PathVariable("farmId")Long farmId) {
		return animalReportDataService.getAllAnimalLastReported(farmId);
	}
	//cập nhật danh sách trại chưa lên bản đồ
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/updateAllFarmtoMap/{pageIndex}/{pageSize}/{year}",method=RequestMethod.POST)
	public void updateAllFarmtoMap(@RequestBody FarmSearchDto farmSearchDto, @PathVariable("pageIndex")int pageIndex,@PathVariable("pageSize")int pageSize, @PathVariable("year")int year) {
		 animalReportDataService.updateAnimalReportDataToRecordMonthDayIsNull(farmSearchDto, pageIndex, pageSize, year);
	}
	//cập nhật 1 trại chưa lên bản đồ
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/updateOneFarmtoMap/{farmCode}/{year}",method=RequestMethod.POST)
	public List<AnimalReportData> updateOneFarmtoMap(@PathVariable("farmCode")String farmCode, @PathVariable("year")int year) {
		return animalReportDataService.updateOneFarmtoMap(true, farmCode, year);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/updateFarmAnimalYearForAllFarm/{pageIndex}/{pageSize}/{year}",method=RequestMethod.POST)
	List<AnimalReportDataDto> updateFarmAnimalYearForAllFarm(@PathVariable int pageIndex,@PathVariable int pageSize,@PathVariable int year,@RequestBody FarmSearchDto dto){
		return animalReportDataService.updateFarmAnimalYearForAllFarm(pageIndex, pageSize, year, dto);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/updateAministrativeUnit",method=RequestMethod.GET)
	void updateAministrativeUnit(){
		 animalReportDataService.updateAdministrative();
	}

	//Xóa dữ liệu bản đồ theo đơn vị hành chính
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/deleteDataMapByAdministrativeUnit",method=RequestMethod.POST)
	public boolean deleteDataMapByAdministrativeUnit(@RequestBody FarmSearchDto farmSearchDto) {
		 return animalReportDataService.deleteDataMapByAdministrativeUnit(farmSearchDto);
	}
}
