package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.dto.ProductTargetDto;

@Repository
public interface ProductTargetRepository extends JpaRepository<ProductTarget, Long>{
	@Query("select new com.globits.wl.dto.ProductTargetDto(p) from ProductTarget p order by p.id")
	Page<ProductTargetDto> getPageProductTarget(Pageable page);
	
	@Query("select new com.globits.wl.dto.ProductTargetDto(p) from ProductTarget p order by p.id")
	List<ProductTargetDto> getAll();
	
	@Query("select o from ProductTarget o where o.code = ?1")
	List<ProductTarget> findByCode(String code);
	
	@Query("select o from ProductTarget o where o.name = ?1")
	List<ProductTarget> findByName(String name);

	@Query("select new com.globits.wl.dto.ProductTargetDto(p) from ProductTarget p where p.parent is not null and p.parent.id = ?1 order by p.id")
	List<ProductTargetDto> getByParentId(Long parentId);

}
