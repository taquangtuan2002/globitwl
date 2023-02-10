package com.globits.wl.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.globits.wl.dto.report.ReportForm16ForExcelDto;
import com.globits.wl.service.ExportForm16Service;
import com.globits.wl.service.impl.ExportForm16ServiceImpl;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.xmlbeans.ResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.functiondto.SearchReportForm16Dto;
import com.globits.wl.service.ReportForm16Service;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.utils.WLConstant;
import org.springframework.core.io.Resource;

import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/reportForm16")
public class RestReportForm16Controller {
	
	@Autowired
	ReportForm16Service reportForm16Service;
	@Autowired
	ReportPeriodService reportPeriodService;
	@Autowired
	private ExportForm16Service exportForm16Service;

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/getListAnimalFromReportForm16BySearch", method = RequestMethod.POST)
	public List<AnimalDto> getListAnimalFromReportForm16BySearch(@RequestBody SearchReportForm16Dto dto) {
		List<AnimalDto> list = reportForm16Service.getListAnimalFromReportForm16BySearch(dto);
		return list;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public Page<ReportForm16Dto> searchByPage(@RequestBody SearchReportForm16Dto searchDto) {
		Page<ReportForm16Dto> page = reportForm16Service.searchByPage(searchDto);
		return page;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/getbyfarm/{id}", method = RequestMethod.GET)
	public List<ReportForm16Dto> getByFarmId(@PathVariable("id") long id) {
		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16ByFarmId(id);
		return list;
	}

	// tran huu dat them rest luu listform16
	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/savelist", method = RequestMethod.POST)
	public List<ReportForm16Dto> saveList(@RequestBody List<ReportForm16Dto> listDto) {
		List<ReportForm16Dto> list = reportForm16Service.saveList(listDto);
		return list;
	}
	// tran huu dat them rest luu listform16

//	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
//	@RequestMapping(method = RequestMethod.POST)
//	public ReportForm16Dto saveOrUpdate(@RequestBody ReportForm16Dto dto) {
//		return reportForm16Service.saveOrUpdate(dto);
//	}

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ReportForm16Dto getById(@PathVariable("id") Long id) {
		return reportForm16Service.getById(id);
	}

//	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
//	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
//	public ReportForm16Dto deleteReportForm16Dto(@PathVariable("id") Long id){
//		ReportForm16Dto dtos=new ReportForm16Dto();
//		try {
//			reportPeriodService.updateAnimalReportDataBeforeDeleteReportForm16(reportForm16);
//			reportForm16Service.delete(id);
//			return dtos;
//		} catch (Exception e) {
//			return null;
//			// TODO: handle exception
//		}
//		
//	}

	// tran huu dat rest xoa form16 by id start
	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public ReportForm16Dto deleteById(@PathVariable("id") Long id) {
		ReportForm16Dto dto = reportForm16Service.deleteById(id);
		return dto;
	}
	// tran huu dat rest xoa form16 by id end

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/delete/list", method = RequestMethod.POST)
	public List<ReportForm16Dto> deleteList(@RequestBody List<ReportForm16Dto> listDto) {
		List<ReportForm16Dto> listResult = new ArrayList<ReportForm16Dto>();
		for (ReportForm16Dto dto : listDto) {
			dto = reportForm16Service.deleteById(dto.getId());
			listResult.add(dto);
		}

		return listResult;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/mergePeriod/{id}/{dupId}", method = RequestMethod.POST)
	public List<ReportForm16Dto> mergePeriodForForm16(@RequestBody List<ReportForm16Dto> listDto,
			@PathVariable("id") Long id, @PathVariable("dupId") Long dupId) {
		List<ReportForm16Dto> listForm16Dto = reportForm16Service.mergePeriodForForm16(listDto, id, dupId);
		return listForm16Dto;
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@PostMapping(value = "/export-report")
	public ResponseEntity<?> exportReport(@RequestBody SearchReportForm16Dto search, WebRequest request, HttpServletResponse response) throws IOException{
	
	Workbook workbook=exportForm16Service.getReportForm16(search);
	workbook.write(response.getOutputStream());
			 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			 response.setHeader("Content-Disposition", "attachment;filename=BC1_3.xlsx");
			 response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER",
			WLConstant.ROLE_SDAH_VIEW })
	@RequestMapping(value = "/getDataByExcel", method = RequestMethod.POST)
	public List<ReportForm16ForExcelDto> getDataAnimalForExcel(@RequestBody SearchReportForm16Dto dto) {
		List<ReportForm16ForExcelDto> list = exportForm16Service.getDataForExcel(dto);
		return list;
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@PostMapping(value = "/export-data")
	public ResponseEntity<?> exportExcel(@RequestBody SearchReportForm16Dto search,HttpServletResponse response) throws IOException{
		Workbook workbook = exportForm16Service.exportReportForm16(search);
		workbook.write(response.getOutputStream());
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;filename=BC1_3.xlsx");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
