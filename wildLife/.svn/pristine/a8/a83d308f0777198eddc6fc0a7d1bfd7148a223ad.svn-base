package com.globits.wl.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.SearchDto;
import com.globits.core.domain.FileDescription;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.core.service.FileDescriptionService;
import com.globits.wl.dto.FarmFileAttachmentDto;
import com.globits.wl.dto.UnitDto;
import com.globits.wl.dto.UserFileAttachmentDto;
import com.globits.wl.dto.UserFileUploadDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.service.UserFileUploadService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/user_file_upload")
public class RestUserFileUploadController {
	
	@Autowired
	private UserFileUploadService userFileUploadService;
	@Autowired
	private FileDescriptionService fileDescriptionService;
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD"})
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public Page<UserFileUploadDto> searchByPage(@RequestBody SearchReportPeriodDto searchDto) {
		Page<UserFileUploadDto> page = userFileUploadService.searchByPage(searchDto);
		return page;
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD"})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public UserFileUploadDto save(@RequestBody UserFileUploadDto userFileUpload) {
		return userFileUploadService.saveOrUpdate(userFileUpload, null);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD"})
	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public UserFileUploadDto update(@RequestBody UserFileUploadDto userFileUpload, @PathVariable Long id ) {
		return userFileUploadService.saveOrUpdate(userFileUpload, id);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD"})
	@RequestMapping(value = "/getUserFileUploadById/{id}", method = RequestMethod.GET)
	public UserFileUploadDto getUserFileUploadById(@PathVariable Long id) {
		return userFileUploadService.getUserFileUploadById(id);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD"})
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public boolean deleteById(@PathVariable Long id) {
		try {
			this.userFileUploadService.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD"})
	@RequestMapping(value = "/uploadattachment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserFileAttachmentDto> uploadAttachment(@RequestParam("uploadfile") MultipartFile uploadfile) {
		UserFileAttachmentDto result = null;
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
			file = fileDescriptionService.save(file);
			FileDescriptionDto fileDto = new FileDescriptionDto(file);
			result = new UserFileAttachmentDto();
			result.setFile(fileDto);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserFileAttachmentDto>(result,HttpStatus.OK);
	}
	@RequestMapping(value = "/getFileAttchmentUploadById/{id}",method = RequestMethod.GET)
	public void downloadFileResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") Long fileId) throws IOException {
		FileDescription fileDesc= fileDescriptionService.findById(fileId);
		if(fileDesc!=null) {
			String filePath = fileDesc.getFilePath();
			File file = new File(filePath);
			if (file.exists()) {
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
			}
		}
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD"})
	@RequestMapping(value = "/deleteFileAttchmentUploadById/{id}", method = RequestMethod.DELETE)
	public boolean deleteFileAttchmentUploadById(@PathVariable Long id) {
		if(id != null) {
			fileDescriptionService.delete(id);
			return true;
		}
		return false;
	}
	
}
