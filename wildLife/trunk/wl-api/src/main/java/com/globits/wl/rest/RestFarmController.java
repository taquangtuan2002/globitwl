package com.globits.wl.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.globits.core.domain.FileDescription;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.core.service.FileDescriptionService;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FarmFileAttachmentDto;
import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.functiondto.DensityRegionDto;
import com.globits.wl.dto.functiondto.FarmAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.FarmCoordinateDto;
import com.globits.wl.dto.functiondto.FarmDuplicateDoubtsDto;
import com.globits.wl.dto.functiondto.FarmMapDeleteDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.FarmReportDto;
import com.globits.wl.service.FarmService;
import com.globits.wl.utils.FarmMapServiceUtil;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/farm")
public class RestFarmController {
	@Autowired 
	private FileDescriptionService fileService;
	@Autowired
	private FarmService farmService;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/page/{pageIndex}/{pageSize}",method=RequestMethod.GET)
	public Page<FarmDto> getPageDto(@PathVariable("pageIndex") int pageIndex,@PathVariable("pageSize")int pageSize){
		return this.farmService.getPageDto(pageIndex,pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getall",method=RequestMethod.GET)
	public List<FarmDto> getPageDto(){
		return this.farmService.getAll();
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getFarmByUserLogin",method=RequestMethod.GET)
	public List<FarmDto> getFarmByUserLogin(){
		return this.farmService.getFarmByUserLogin();
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/get/{id}",method=RequestMethod.GET)
	public FarmDto getPageDto(@PathVariable("id")Long id){
		return this.farmService.getById(id);
	}
	@Transactional
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER"})
	@RequestMapping(value="/update/{id}",method=RequestMethod.PUT)
	public FarmDto updateFarm(@PathVariable("id") Long id,@RequestBody FarmDto dto){
		return this.farmService.updateFarm(id,dto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER"})
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public FarmDto createFarm(@RequestBody FarmDto dto){
		return this.farmService.createFarm(dto);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH"})
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public FarmDto deleteById(@PathVariable("id")Long id){
		FarmDto dto=new FarmDto();
		try {
			dto=this.farmService.deleteById(id);
			
			return dto;
		} catch (Exception e) {
			dto = new FarmDto();
			dto.setCode("-2");
			return dto;
		}
	}
	
	@RequestMapping(value = "/uploadattachment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FarmFileAttachmentDto> uploadAttachment(@RequestParam("uploadfile") MultipartFile uploadfile) {
		FarmFileAttachmentDto result = null;
		try {
			String filePath = uploadfile.getOriginalFilename();
			filePath =WLConstant.FolderPath+filePath;
			FileOutputStream stream = new FileOutputStream(filePath);
			try {
			    stream.write(uploadfile.getBytes());
			} finally {
			    stream.close();
			}
			
			FileDescription file = new FileDescription();
			file.setContentSize(uploadfile.getSize());
			file.setContentType(uploadfile.getContentType());
			file.setName(uploadfile.getOriginalFilename());
			file.setFilePath(filePath);
			file = fileService.save(file);
			FileDescriptionDto fileDto = new FileDescriptionDto(file);
			result = new FarmFileAttachmentDto();
			result.setFile(fileDto);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FarmFileAttachmentDto>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/file/{fileId}",method = RequestMethod.GET)
	public void downloadFileResource(HttpServletRequest request, HttpServletResponse response,
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
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD"})
	@RequestMapping(value="/removeListByParent/{id}",method=RequestMethod.POST)
	public List<AnimalDto> removeListByParent(@PathVariable("id") Long id,@RequestBody List<AnimalDto> list){
		return this.farmService.removeListByParent(id, list);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<FarmDto>> getListSimpleByNameCode(@RequestBody FarmSearchDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<FarmDto> results = this.farmService.searchFarmDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<FarmDto>>(results, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto", method = RequestMethod.POST)	
	public ResponseEntity<List<FarmDto>> getListSimpleByNameCode(@RequestBody FarmSearchDto dto) {
		
		List<FarmDto> results = this.farmService.searchFarmDto(dto);
		return new ResponseEntity<List<FarmDto>>(results, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER"})
	@RequestMapping(value="/autoGenericCode/{codeDistrict}/{id}/{codeCity}",method=RequestMethod.GET)
	public ResponseEntity<FarmDto> autoGenericCode(@PathVariable("codeDistrict")String codeDistrict,@PathVariable("id")Long id,@PathVariable("codeCity")String codeCity){
		FarmDto dto= new FarmDto();
		String code = this.farmService.autoGenericCode(codeDistrict, id, codeCity);
		dto.setCode(code);
		return new ResponseEntity<FarmDto>(dto, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/countFarmByAuId/{id}/{level}",method=RequestMethod.GET)
	public ResponseEntity<List<FarmAdministrativeUnitDto>> countFarmByAu(@PathVariable("id")Long id,@PathVariable("level")int level){
		List<FarmAdministrativeUnitDto> dto=this.farmService.countFarmListAdministrativeUnitById(id, level);
		
		return new ResponseEntity<List<FarmAdministrativeUnitDto>>(dto, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/countFarmByListAu/{level}",method=RequestMethod.POST)
	public ResponseEntity<List<FarmAdministrativeUnitDto>> countFarmByListAu(@PathVariable("level")int level,@RequestBody List<Long> ids){
		List<FarmAdministrativeUnitDto> dtos=this.farmService.countFarmByListAdministrativeUnit(ids,level);
		
		return new ResponseEntity<List<FarmAdministrativeUnitDto>>(dtos, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/countFarmAuByMapCode/{mapCode}/{level}",method=RequestMethod.GET)
	public ResponseEntity<List<FarmAdministrativeUnitDto>> countFarmAuByMapCode(@PathVariable("mapCode")String mapCode,@PathVariable("level")int level){
		List<FarmAdministrativeUnitDto> dtos=this.farmService.countFarmListAdministrativeUnitByMapCode(mapCode,level);
		
		return new ResponseEntity<List<FarmAdministrativeUnitDto>>(dtos, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP"})
	@RequestMapping(value="/countBalanceNumberAllFarm",method=RequestMethod.GET)
	public void countBalanceNumberAllFarm(){
		 this.farmService.countBalanceNumberAllFarm();
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/countDensityMapCodeAdministrativeUnit/{mapCode}",method=RequestMethod.GET)
	public ResponseEntity<DensityRegionDto> countDensityRegion(@PathVariable("mapCode")String mapCode){
		DensityRegionDto dto=this.farmService.countDensityMapCodeAu(mapCode);
		
		return new ResponseEntity<DensityRegionDto>(dto, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/countDensityListRegion",method=RequestMethod.GET)
	public ResponseEntity<List<DensityRegionDto>> countDensityListRegion(){
		List<DensityRegionDto> dtos=this.farmService.countDensityListRegion();
		
		return new ResponseEntity<List<DensityRegionDto>>(dtos, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/deleteAnimalProductTargetExist",method=RequestMethod.DELETE)
	public ObjectDto deleteFarmAnimalProductTargetExist(){
		ObjectDto dto=new ObjectDto();
		try {
			this.farmService.deleteFarmAnimalProductTargetExist();
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/deleteProductTargetExist",method=RequestMethod.DELETE)
	public ObjectDto deleteFarmProductTargetExist(){
		ObjectDto dto=new ObjectDto();
		try {
			this.farmService.deleteFarmProductTargetExist();
			return dto;
		} catch (Exception e) {			
			dto.setCode("-1");
			return dto;
		}
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/farmReport",method=RequestMethod.POST)
	public List<FarmReportDto> farmReport(@RequestBody ReportParamDto paramDto){
		return this.farmService.getSumQuantity(paramDto);
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/setCodeForAllFarm",method=RequestMethod.POST)
	public void setCodeForAllFarm(){
		this.farmService.setCodeForAllFarm();
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/mergeFarm/{farmId}/{duplicateFarmId}",method=RequestMethod.POST)
	public FarmDto mergeFarm(@PathVariable Long farmId,@PathVariable Long duplicateFarmId) {
		return this.farmService.mergeFarm( farmId, duplicateFarmId);
	}
	
	//tran huu dat them rest mergefarm start 
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/mergeFarmNew/{farmId}/{duplicateFarmId}",method=RequestMethod.POST)
	public FarmDto mergeFarmNew(@PathVariable Long farmId,@PathVariable Long duplicateFarmId, @RequestBody FarmDto farmMergeDto) {
		return this.farmService.mergeFarmNew( farmId, duplicateFarmId, farmMergeDto);
	}
	//tran huu dat them rest mergefarm end
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/farmDupDoubt/{yearA}/{yearB}",method=RequestMethod.POST)
	public List<FarmDuplicateDoubtsDto> farmDupDoubt(@PathVariable("yearA") String yearA,@PathVariable("yearB") String yearB) {
		return this.farmService.farmDupDoubt( yearA, yearB);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value="/mergeAllFarm/{yearA}/{yearB}",method=RequestMethod.POST)
	public int mergeAllFarm(@PathVariable("yearA") String yearA,@PathVariable("yearB") String yearB) {
		int ret=0;
		List<FarmDuplicateDoubtsDto> lst = farmService.farmDupDoubtCommune(yearA,yearB);
		if(lst!=null && lst.size()>0) {
			for (FarmDuplicateDoubtsDto farmDuplicateDoubtsDto : lst) {
				farmService.mergeFarm(farmDuplicateDoubtsDto.getFarmB().getId(), farmDuplicateDoubtsDto.getFarmA().getId());
				ret+=1;
			}
		}
		return ret;
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/getSummary/{id}",method=RequestMethod.GET)
	public FarmDto getByIdSummary(@PathVariable("id")Long id){
		return this.farmService.getByIdSummary(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/updateAllFarmInfoToMap/{pageIndex}/{pageSize}",method=RequestMethod.POST)
	public void updateAllFarmInfoToMap(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize")int pageSize){
		this.farmService.updateAllFarmInfoToMap(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/updateAllFarmInfoToMap/{pageIndex}/{pageSize}/{year}/{provinceId}",method=RequestMethod.POST)
	public void updateAllFarmInfoToMap(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize")int pageSize,@PathVariable int year,@PathVariable Long provinceId){
		this.farmService.updateAllFarmInfoToMap(pageIndex, pageSize,year,provinceId);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/updateAllFarmCreatedFarmToMap/{pageIndex}/{pageSize}",method=RequestMethod.POST)
	public void updateAllFarmCreatedFarmToMap(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize")int pageSize){
		this.farmService.updateAllFarmCreatedFarmToMap(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value="/updateAllFarmCreatedFarmToMap/{pageIndex}/{pageSize}/{year}/{provinceId}",method=RequestMethod.POST)
	public void updateAllFarmCreatedFarmToMap(@PathVariable int pageIndex,@PathVariable int pageSize,@PathVariable int year,@PathVariable Long provinceId) {
		this.farmService.updateAllFarmCreatedFarmToMap(pageIndex, pageSize,year,provinceId);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value="/updateCoordinates",method=RequestMethod.POST)
	public FarmDto updateCoordinates(@RequestBody FarmCoordinateDto dto) throws ClientProtocolException, JSONException, IOException{
		return this.farmService.updateCoordinates(dto.getFarmCode(), dto.getLat(), dto.getLng());
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT"})
	@RequestMapping(value="/reGenerateFarmCode/{farmId}",method=RequestMethod.POST)
	public FarmDto reGenerateFarmCode(@PathVariable Long farmId){
		return this.farmService.reGenerateFarmCode(farmId);
	}
}
