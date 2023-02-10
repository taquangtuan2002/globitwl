package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.AnimalCertificate;
import com.globits.wl.dto.AnimalCertificateDto;

@Repository
public interface AnimalCertificateRepository extends JpaRepository<AnimalCertificate, Long> {
	@Query("select new com.globits.wl.dto.AnimalCertificateDto(fa) from AnimalCertificate fa order by fa.id desc")
	Page<AnimalCertificateDto> getPageDto(Pageable pageable);

	@Query("select new com.globits.wl.dto.AnimalCertificateDto(fa) from AnimalCertificate fa")
	List<AnimalCertificateDto> getAllDto();

	@Query("select b from AnimalCertificate b where b.code = ?1")
	List<AnimalCertificate> findByCode(String code);

	@Query("select o from AnimalCertificate o where o.name = ?1")
	List<AnimalCertificate> findByName(String name);

}
