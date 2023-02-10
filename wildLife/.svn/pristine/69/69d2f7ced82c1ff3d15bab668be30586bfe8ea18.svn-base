package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Ownership;
import com.globits.wl.dto.OwnershipDto;

@Repository
public interface OwnershipRepository extends JpaRepository<Ownership, Long>{
	@Query("select new com.globits.wl.dto.OwnershipDto(s) from Ownership s")
	List<OwnershipDto> getAllOwnershipDto();
	@Query("select s from Ownership s where s.code = ?1")
	List<Ownership> findByCode(String code);

}
