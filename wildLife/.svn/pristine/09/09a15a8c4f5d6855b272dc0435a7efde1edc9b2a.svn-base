package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.globits.wl.domain.ProductTarget;
import com.globits.wl.domain.WaterSource;
import com.globits.wl.dto.WaterSourceDto;

public interface WaterSourceRepository extends JpaRepository<WaterSource, Long> {
	
	@Query("select new com.globits.wl.dto.WaterSourceDto(water) from WaterSource water ")
	Page<WaterSourceDto> getListWaterSources(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.WaterSourceDto(water) from WaterSource water ")
	List<WaterSourceDto> getAll();
	
	@Query("select new com.globits.wl.dto.WaterSourceDto(water) from WaterSource water where water.id = ?1")
	WaterSourceDto waterSourceById(Long id);
	
	@Query("select o from WaterSource o where o.code = ?1")
	List<WaterSource> findByCode(String code);

}
