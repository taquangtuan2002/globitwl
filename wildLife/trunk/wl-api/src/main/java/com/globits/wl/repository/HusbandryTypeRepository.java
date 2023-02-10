package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.globits.wl.domain.HusbandryType;
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.HusbandryTypeDto;

public interface HusbandryTypeRepository extends JpaRepository<HusbandryType, Long>{
	
	@Query("select new com.globits.wl.dto.HusbandryTypeDto(husbandry) from HusbandryType husbandry ")
	Page<HusbandryTypeDto> getListHusbandryTypes(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.HusbandryTypeDto(husbandry) from HusbandryType husbandry ")
	List<HusbandryTypeDto> getAll();

	@Query("select new com.globits.wl.dto.HusbandryTypeDto(husbandry) from HusbandryType husbandry where husbandry.id = ?1")
	HusbandryTypeDto husbandryTypeById(Long id);

}
