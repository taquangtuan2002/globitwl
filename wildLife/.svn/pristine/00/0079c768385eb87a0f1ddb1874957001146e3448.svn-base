package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Certificate;
import com.globits.wl.dto.CertificateDto;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
	@Query("select new com.globits.wl.dto.CertificateDto(fa) from Certificate fa")
	Page<CertificateDto> getPageDto(Pageable pageable);

	@Query("select new com.globits.wl.dto.CertificateDto(fa) from Certificate fa")

	List<CertificateDto> getAllDto();

	@Query("select b from Certificate b where b.code = ?1")
	List<Certificate> findByCode(String code);

	@Query("select o from Certificate o where o.name = ?1")
	List<Certificate> findByName(String name);

}
