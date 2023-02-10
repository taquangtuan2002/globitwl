package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Unit;
import com.globits.wl.dto.UnitDto;


@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>{
	
	@Query("select new com.globits.wl.dto.UnitDto(o) from Unit o order by o.id")
	Page<UnitDto> getPageAll(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.UnitDto(o) from Unit o where o.id = ?1")
	UnitDto getUnitDtoById(Long id);
	
	@Query("select new com.globits.wl.dto.UnitDto(o) from Unit o order by o.id")
	List<UnitDto> getAllDtos();
	
	@Query("select o from Unit o where o.code = ?1")
	List<Unit> findByCode(String code);
	
	@Query("select o from Unit o where o.name = ?1")
	List<Unit> findByName(String name);

}
