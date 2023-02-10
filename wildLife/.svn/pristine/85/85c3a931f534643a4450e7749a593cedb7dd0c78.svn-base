package com.globits.wl.service;

import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import com.globits.wl.dto.LinkDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface LinkService {

	Page<LinkDto> getAllWidthPagination(int pageIndex, int pageSize);

	LinkDto getLinkById(Long id);

	LinkDto saveLink(LinkDto dto);

	boolean deleteLinkById(Long id);

	boolean deleteLinkByIds(Set<Long> ids);

	List<LinkDto> getAll();
	
	Page<LinkDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize);
	LinkDto checkDuplicateCode(String code);
}
