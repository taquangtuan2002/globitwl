package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.globits.wl.domain.FmsRegion;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.domain.SeedLevel;
import com.globits.wl.domain.WaterSource;
import com.globits.wl.dto.SeedLevelDto;
import com.globits.wl.dto.WaterSourceDto;

public interface SeedLevelRepository extends JpaRepository<SeedLevel, Long> {
	
	@Query("select new com.globits.wl.dto.SeedLevelDto(s) from SeedLevel s ")
	Page<SeedLevelDto> getListWaterSources(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.SeedLevelDto(s) from SeedLevel s ")
	List<SeedLevelDto> getAll();
	
	@Query("select new com.globits.wl.dto.SeedLevelDto(s) from SeedLevel s where s.id = ?1")
	SeedLevelDto seedLevelById(Long id);
	
	@Query("select new com.globits.wl.dto.SeedLevelDto(s) from SeedLevel s where s.code like %?1% or s.name like %?1% or s.level like %?1%")
	Page<SeedLevelDto> findByText(String text, Pageable pageable);
	
	@Query("select b from SeedLevel b where b.code = ?1")
	List<SeedLevel> findByCode(String code);
	
	@Query("select new com.globits.wl.dto.SeedLevelDto(s) from SeedLevel s where s.level = ?1")
	List<SeedLevelDto> seedLevelByLevel(Integer level);

}
