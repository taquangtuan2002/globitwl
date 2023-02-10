package com.globits.wl.service;

import java.util.List;
import org.springframework.data.domain.Page;
import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;
import com.globits.wl.domain.UserFileUpload;
import com.globits.wl.dto.UserFileUploadDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface UserFileUploadService extends GenericService<UserFileUpload, Long> {
	UserFileUploadDto saveOrUpdate(UserFileUploadDto userFileUpload, Long id);
	UserFileUploadDto getUserFileUploadById(Long id);
	Page<UserFileUploadDto> searchByPage(SearchReportPeriodDto searchDto);
	boolean deleteById(Long id);
}
