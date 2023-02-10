package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.globits.wl.dto.OwnershipDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface OwnershipService{

	Page<OwnershipDto> getSearchOwnerShip(SearchDto searchDto, int pageIndex, int pageSize);

	List<OwnershipDto> getAll();

	OwnershipDto getOwnershipDtoById(Long id);

	OwnershipDto save(OwnershipDto ownershipDto, Long object);

	OwnershipDto removeById(Long id);

	OwnershipDto checkDuplicateCode(String code);

}
