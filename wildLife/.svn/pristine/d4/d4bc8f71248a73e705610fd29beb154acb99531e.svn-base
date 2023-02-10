package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.AdministrativeUnitEditable;
import com.globits.wl.dto.AdministrativeUnitEditableDto;


@Repository
public interface AdministrativeUnitEditableRepository extends JpaRepository<AdministrativeUnitEditable, Long>{

	@Query("select new com.globits.wl.dto.AdministrativeUnitEditableDto(entity) from AdministrativeUnitEditable entity where entity.id = ?1 ")
	AdministrativeUnitEditableDto getAdministrativeUnitEditableById(Long id);

	@Query("select new com.globits.wl.dto.AdministrativeUnitEditableDto(entity) from AdministrativeUnitEditable entity where entity.adminUnit.id = ?1 ")
	List<AdministrativeUnitEditableDto> getAdministrativeUnitEditableByAdminUnit(Long id);
}
