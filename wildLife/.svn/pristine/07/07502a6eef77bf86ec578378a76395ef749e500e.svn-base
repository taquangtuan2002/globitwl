package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.FmsRegion;
import com.globits.wl.dto.FmsRegionDto;

public interface FmsRegionRepository extends JpaRepository<FmsRegion, Long> {
	
	@Query("select new com.globits.wl.dto.FmsRegionDto(fmsRe) from FmsRegion fmsRe ")
	Page<FmsRegionDto> getListFmsRegion( Pageable pageable);

	@Query("select new com.globits.wl.dto.FmsRegionDto(fmsRe) from FmsRegion fmsRe where fmsRe.id = ?1")
	FmsRegionDto fmsRegionById(Long id);

	@Query("select new com.globits.wl.dto.FmsRegionDto(fmsRe) from FmsRegion fmsRe ")
	List<FmsRegionDto> getAll();
	
	@Query("select b from FmsRegion b where b.code = ?1")
	List<FmsRegion> findByCode(String code);

}
