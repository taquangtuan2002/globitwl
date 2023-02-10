package com.globits.wl.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.core.domain.FileDescription;
import com.globits.core.service.FileDescriptionService;
import com.globits.wl.domain.Farm;
import com.globits.wl.dto.Report18Dto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.DataDvhdDto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.ForgotPasswordDto;
import com.globits.wl.dto.functiondto.ImportResultDto;
import com.globits.wl.dto.functiondto.ImpotList16Dto;
import com.globits.wl.dto.functiondto.WeekOfYearDto;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.OneTimeTokenService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.service.ReportService;
import com.globits.wl.utils.FarmMapServiceUtil;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@RestController
@RequestMapping("/public/publicAPI")
public class RestPublishApiController {
	
	private static Hashtable<UUID, ImpotList16Dto> hashToImport = new  Hashtable<UUID, ImpotList16Dto>();
	
	@Autowired
	private FileDescriptionService fileService;
	@Autowired
	private OneTimeTokenService oneTimeTokenService;
	@Autowired
	private ReportPeriodService reportPeriodService;
	@Autowired
	private AnimalReportDataService animalReportDataService;
	
	@Autowired
	private ReportService reportService;
	private List<DataDvhdDto> syncData(List<DataDvhdDto> list) {
		List<DataDvhdDto> errorList = new ArrayList<DataDvhdDto>();
		for (DataDvhdDto rawData : list) {
			try {	
				rawData = reportPeriodService.updateFromFileImportByOneRow(rawData);
				if (rawData != null && (rawData.getErrContent() == null || rawData.getErrContent().length() == 0)
						&& rawData.getFarm() != null) {
//					List<Long> animalIds = reportPeriodService.getListAnimalIds(rawData.getFarmId(), rawData.getYear());
//					// Cập nhật tới báo cáo theo năm
//					animalReportDataService.updateAnimalReportDataByFarmAnimalYear(rawData.getFarmId(), animalIds,rawData.getYear());
						
				} else {
					errorList.add(rawData);
				}
			}catch (Exception ex) {
				errorList.add(rawData);
				ex.printStackTrace();
			}	
		}
		return errorList;
	}
	@RequestMapping(value = "/updateListFromFileImportByListDto", method = RequestMethod.POST)
	public ImportResultDto<DataDvhdDto> updateListFromFileImportByListDto(
			@RequestBody ImpotList16Dto dto) throws IOException {
	try {
		List<DataDvhdDto> list=dto.getList();
		if (list == null || list.size() <= 0) {
			return null;
		}
		UUID key = UUID.randomUUID();
		hashToImport.put(key, dto);
		ImportResultDto<DataDvhdDto> ret = new ImportResultDto<DataDvhdDto>();
		List<DataDvhdDto> errorList = new ArrayList<DataDvhdDto>();
		Set<Farm> farmSets = new HashSet<Farm>();
		
//		errorList = syncData(list);
//		//Kiểm tra lại erroList và có thể thực hiện lại Import nếu số lượng errorList >0
//		
//		
//		
//		if(errorList!=null && errorList.size()>0) {
//			System.out.println("Error:"+errorList.size());//Có thể sẽ thực hiện đồng bộ lại
////			for(int i=0;i<errorList.size();i++) {
////				DataDvhdDto dvDto = errorList.get(i);
////				System.out.println(dvDto.getFarmCode()+":"+dvDto.getFarmName()+":"+dvDto.getAnimalCode()+":"+dvDto.getWardCode());
////			}
//			
//			errorList = syncData(errorList);
//			ret.setTotalErr(errorList.size());
//			ret.getListErr().addAll(errorList);
//		}
		
		hashToImport.remove(key);
		for (DataDvhdDto rawData : list) {
			try {	
				rawData = reportPeriodService.updateFromFileImportByOneRow(rawData);
				ret.setTotalRow(list.size());
				if (rawData != null && (rawData.getErrContent() == null || rawData.getErrContent().length() == 0)
						&& rawData.getFarm() != null) {
//						//đồng bộ bản đồ
					AnimalReportDataSearchDto search = new AnimalReportDataSearchDto();
					search.setFarmId(rawData.getFarm().getId());
					search.setYear(new LocalDateTime(rawData.getDateUpdate()).getYear());
					List<Report18Dto> animalLastYear= reportService.reportActivityOfEndangeredPreciousRareNormarlAndCitesNativeQuery(search);
					if(animalLastYear!=null && animalLastYear.size()>0) {
						for(Report18Dto a : animalLastYear) {
							FarmAnimalTotalDto fa = new FarmAnimalTotalDto();
							fa.setFarmCode(a.getFarmCode());
							fa.setAnimalCode(a.getAnimalCode());
							fa.setTotal(a.getTotal());
							fa.setYear(a.getYear());
							try {
								FarmMapServiceUtil.pushFarmAnimalMap(fa);
							} catch (Exception e) {
								continue;
							}
							
							
						}
					}
					
//					List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(row.getId(), null);
//					if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
//						FarmAnimalTotalDto farmAnimalTotalDto = listAnimalReportTotal.get(0);
//						if (farmAnimalTotalDto != null) {
//							try {
//								
//							} catch (Exception e) {
//								e.printStackTrace();
//								continue;
//							}
//						}
//					}
				} else {
					ret.setTotalErr(ret.getTotalErr() + 1);
					ret.getListErr().add(rawData);
				}
			}catch (Exception ex) {
				errorList.add(rawData);
				ex.printStackTrace();
			}	
		}
		
		return ret;
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		WLConstant.isImporting = false;
		return null;
	}
	}
	@RequestMapping(value = "/downloadArticleImg/{fileId}",method = RequestMethod.GET)
	public void downloadProductFileResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileId") Long fileId) throws IOException {

		FileDescription fileDesc= fileService.findById(fileId);
		if(fileDesc!=null) {
			String filePath = fileDesc.getFilePath();
			File file = new File(filePath);
			//File file = new File(EXTERNAL_FILE_PATH + fileName);
			if (file.exists()) {

				//get the mimetype
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					//unknown mimetype so set the mimetype to application/octet-stream
					mimeType = "application/octet-stream";
				}

				response.setContentType(mimeType);

				/**
				 * In a regular HTTP response, the Content-Disposition response header is a
				 * header indicating if the content is expected to be displayed inline in the
				 * browser, that is, as a Web page or as part of a Web page, or as an
				 * attachment, that is downloaded and saved locally.
				 * 
				 */

				/**
				 * Here we have mentioned it to show inline
				 */
				response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

				 //Here we have mentioned it to show as attachment
				 //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
		}
	}
	
	@RequestMapping(value="forgotPasswordSendEmail", method=RequestMethod.POST)
	public ForgotPasswordDto forgotPasswordSendEmail(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return oneTimeTokenService.sendEmailForgotPassword(forgotPasswordDto);
	}
	@RequestMapping(value="checkToken", method=RequestMethod.POST)
	public ForgotPasswordDto checkToken(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return oneTimeTokenService.checkToken(forgotPasswordDto);
	}
	@RequestMapping(value="changePassword", method=RequestMethod.POST)
	public ForgotPasswordDto changePassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
		return oneTimeTokenService.changePassword(forgotPasswordDto);
	}

	@RequestMapping(value = "getWeeksByYear/{year}", method = RequestMethod.GET)
	public List<WeekOfYearDto> getWeeksByYear(@PathVariable("year") Integer year) {
		return WLDateTimeUtil.getWeeksByYear(year);

	}
}
