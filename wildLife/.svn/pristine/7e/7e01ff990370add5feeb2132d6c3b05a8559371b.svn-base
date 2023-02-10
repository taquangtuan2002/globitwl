package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;
import com.globits.wl.domain.UserAttachments;
import com.globits.wl.dto.UserAttachmentsDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;

public interface UserAttachmentsService extends GenericService<UserAttachments, Long> {

	List<UserAttachmentsDto> saveList(List<UserAttachmentsDto> listUserAttachments);

	List<UserAttachmentsDto> getByUserId(Long userId);

	Page<UserAttachmentsDto> searchByPage(SearchDto searchDto);

	Page<UserAttachmentsDto> searchByPageUniqueUser(SearchReportPeriodDto searchDto);
}
