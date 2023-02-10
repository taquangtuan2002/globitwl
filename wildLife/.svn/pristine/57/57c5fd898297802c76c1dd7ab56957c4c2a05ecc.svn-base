package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Bran;
import com.globits.wl.dto.BranDto;

@Repository
public interface BranRepository extends JpaRepository<Bran, Long> {
	@Query("select new com.globits.wl.dto.BranDto(fa) from Bran fa")
	Page<BranDto> getPageDto(Pageable pageable);

	@Query("select new com.globits.wl.dto.BranDto(fa) from Bran fa")

	List<BranDto> getAllDto();

	@Query("select b from Bran b where b.code = ?1")
	List<Bran> findByCode(String code);

	@Query("select o from Bran o where o.name = ?1")
	List<Bran> findByName(String name);

}
