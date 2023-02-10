package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Original;
import com.globits.wl.dto.OriginalDto;

@Repository
public interface OriginalRepository extends JpaRepository<Original, Long>{
	
	@Query("select new com.globits.wl.dto.OriginalDto(o) from Original o order by o.id")
	Page<OriginalDto> getPageAll(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.OriginalDto(o) from Original o where o.id = ?1")
	OriginalDto getOrinalById(Long id);
	
	@Query("select new com.globits.wl.dto.OriginalDto(o) from Original o order by o.id")
	List<OriginalDto> getAllDtos();
	
	@Query("select o from Original o where o.code = ?1")
	List<Original> findByCode(String code);
	
	@Query("select o from Original o where o.name = ?1")
	List<Original> findByName(String name);
	
	@Query("from Original o where o.id = ?1")
	Original getOrinalFindById(Long id);

}
