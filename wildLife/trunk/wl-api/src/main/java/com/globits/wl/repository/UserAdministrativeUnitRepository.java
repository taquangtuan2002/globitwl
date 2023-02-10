package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.dto.FmsAdministrativeUnitDto;

@Repository
public interface UserAdministrativeUnitRepository extends JpaRepository<UserAdministrativeUnit, Long>{

	@Query("select entity from UserAdministrativeUnit entity where entity.adminUnit.id = ?1 ")
	List<UserAdministrativeUnit> findByAdminUnit(Long id);

	@Query("select entity from UserAdministrativeUnit entity where entity.user.id = ?1")
	UserAdministrativeUnit findByUserId(Long id);
	
}
