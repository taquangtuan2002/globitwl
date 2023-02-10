package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.InjectionTime;
import com.globits.wl.dto.InjectionTimeDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface InjectionTimeService extends GenericService<InjectionTime, Long> {

	Page<InjectionTimeDto> getPageDto(int pageIndex, int pageSize);

	List<InjectionTimeDto> getAllDto();

	InjectionTimeDto getInjectionTimeById(Long id);

	InjectionTimeDto deleteById(Long id);

	boolean deleteByIds(List<Long> ids);

	InjectionTimeDto createInjectionTime(InjectionTimeDto dto);

	InjectionTimeDto updateInjectionTime(Long id, InjectionTimeDto dto);

	Page<InjectionTimeDto> searchInjectionTimeDto(SearchDto dto, int pageIndex, int pageSize);

	InjectionTimeDto checkDuplicateCode(String code);

}
