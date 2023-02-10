package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Drug;
import com.globits.wl.dto.DrugDto;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
	@Query("select new com.globits.wl.dto.DrugDto(d) from Drug d")
	Page<DrugDto> getPageDto(Pageable pageable);

	@Query("select new com.globits.wl.dto.DrugDto(d) from Drug d")

	List<DrugDto> getAllDto();

	@Query("select b from Drug b where b.code = ?1")
	List<Drug> findByCode(String code);

	@Query("select o from Drug o where o.name = ?1")
	List<Drug> findByName(String name);

}
