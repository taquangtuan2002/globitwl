package com.globits.wl.rest;

import java.io.FileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.globits.cms.Const;
import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsArticleService;
import com.globits.core.domain.FileDescription;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.core.service.FileDescriptionService;
import com.globits.wl.service.FmsCmsArticleService;

@RestController
@RequestMapping("/api/cms/article")
public class RestFmsCmsArticleController {
	@Autowired
	private FmsCmsArticleService cmsArticleService;
	
	@Autowired
	private FileDescriptionService fileService;

	@RequestMapping(value="/page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CmsArticleDto> getPageCmsArticleDto(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return cmsArticleService.getPageCmsArticleDto(pageIndex, pageSize);
	}
	
	@RequestMapping(value="/{articleId}", method = RequestMethod.GET)
	public CmsArticleDto getCmsArticleDtoById(@PathVariable("articleId")Long id) {
		return cmsArticleService.getCmsArticleDtoById(id);
	}
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH"})
	public CmsArticleDto createCmsArticleDto(@RequestBody CmsArticleDto dto) {
		return cmsArticleService.saveCmsArticle(dto, null);
	}
	
	@RequestMapping(value="/update/{articleId}", method = RequestMethod.PUT)
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH"})
	public CmsArticleDto updateCmsArticleDto(@RequestBody CmsArticleDto dto, @PathVariable("articleId")Long id) {
		return cmsArticleService.saveCmsArticle(dto, id);
	}
	
	@RequestMapping(value="/delete/{articleId}", method = RequestMethod.DELETE)
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH"})
	public Boolean deleteCmsArticleById(@RequestBody CmsArticleDto dto, @PathVariable("articleId")Long id) {
		return cmsArticleService.deleteCmsArticleById(id);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Page<CmsArticleDto> searchArticle(@RequestBody SearchDto search) {
		return cmsArticleService.searchArticleServiceBySearchDto(search);
	}
	
	
	@RequestMapping(value = "/uploadattachment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FileDescriptionDto> uploadAttachment(@RequestParam("uploadfile") MultipartFile uploadfile) {
		FileDescriptionDto result = null;
		
		try {
			String filePath = uploadfile.getOriginalFilename();
			filePath =Const.PathFileImage+filePath;
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
			result = fileDto;	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FileDescriptionDto>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileDescriptionDto> getFileById(@PathVariable("id") Long fileId) {
		FileDescriptionDto result = null;
		
		try {
			FileDescription file = null;
			file = fileService.findById(fileId);
			if(file == null) {
				return new ResponseEntity<FileDescriptionDto>(new FileDescriptionDto(new FileDescription()) ,HttpStatus.BAD_REQUEST);
			}
			file = fileService.save(file);
			FileDescriptionDto fileDto = new FileDescriptionDto(file);
			result = fileDto;	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FileDescriptionDto>(result,HttpStatus.OK);
	}	
}
