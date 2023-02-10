package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.core.domain.Building;
import com.globits.wl.domain.FarmSize;
import com.globits.wl.dto.FarmSizeDto;

@Repository
public interface FarmSizeRepository extends JpaRepository<FarmSize, Long>{
	@Query("select new com.globits.wl.dto.FarmSizeDto(cs) from FarmSize cs order by cs.id")
	Page<FarmSizeDto> getAll(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.FarmSizeDto(cs) from FarmSize cs order by cs.id")
	List<FarmSizeDto> getAllDto();
	
	@Query("select b from FarmSize b where b.code = ?1")
	List<FarmSize> findByCode(String code);
	
	@Query("select new com.globits.wl.dto.FarmSizeDto(b) from FarmSize b where b.minQuantity <= ?1 order by b.minQuantity desc")
	List<FarmSizeDto> findByQuantity(int quantity);

}
