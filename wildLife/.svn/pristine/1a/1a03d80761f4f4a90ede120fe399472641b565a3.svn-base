package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.globits.wl.domain.FmsRegion;
import com.globits.wl.domain.HusbandryMethod;
import com.globits.wl.dto.HusbandryMethodDto;

public interface HusbandryMethodRepository extends JpaRepository<HusbandryMethod, Long>{

	@Query("select new com.globits.wl.dto.HusbandryMethodDto(husbandryMe) from HusbandryMethod husbandryMe ")
	Page<HusbandryMethodDto> getListHusbandryMethods(Pageable pageable);

	@Query("select new com.globits.wl.dto.HusbandryMethodDto(husbandryMe) from HusbandryMethod husbandryMe ")
	List<HusbandryMethodDto> getAll();
	
	@Query("select new com.globits.wl.dto.HusbandryMethodDto(husbandryMe) from HusbandryMethod husbandryMe where husbandryMe.id = ?1")
	HusbandryMethodDto husbandryMethodById(Long id);

	@Query("select b from HusbandryMethod b where b.code = ?1")
	List<HusbandryMethod> findByCode(String code);
	
	@Query("select new com.globits.wl.dto.HusbandryMethodDto(b) from HusbandryMethod b where b.code = ?1")
	List<HusbandryMethodDto> findByCode2(String code);
}
