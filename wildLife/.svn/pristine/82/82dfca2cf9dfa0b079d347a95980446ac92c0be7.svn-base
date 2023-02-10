package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.SearchDto;

public interface ProductTargetService {

	Page<ProductTargetDto> getPageProductTarget(int pageIndex, int pageSize);

	ProductTargetDto createProductTarget(ProductTargetDto dto);

	ProductTargetDto updateProductTarget(Long id, ProductTargetDto dto);

	ProductTargetDto deleteProductTarget(Long id);

	ResponseEntity<Boolean> deleteProductTargets(List<Long> ids);

	ProductTargetDto getProducById(Long id);

	List<ProductTargetDto> getAllProductTarget();
	
	Page<ProductTargetDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize);
	ProductTargetDto checkDuplicateCode(String code);

	List<ProductTargetDto> getAllByParentId(Long parentId);

}
