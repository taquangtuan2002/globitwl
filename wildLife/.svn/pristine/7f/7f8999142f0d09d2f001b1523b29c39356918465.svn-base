package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.TechnicalStaff;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.TechnicalStaffDto;


@Repository
public interface TechnicalStaffRepository extends JpaRepository<TechnicalStaff, Long>{
	@Query("select new com.globits.wl.dto.TechnicalStaffDto(d) from TechnicalStaff d")
	List<TechnicalStaffDto> getAll();
	
	@Query("select new com.globits.wl.dto.TechnicalStaffDto(d) from TechnicalStaff d order by d.id")
	Page<TechnicalStaffDto> getPageTechnicalStaff( Pageable pageable);
	
	@Query("select new com.globits.wl.dto.TechnicalStaffDto(d) from TechnicalStaff d where d.id != ?1")
	List<TechnicalStaff> findById(Long id);
	
	@Query("select new com.globits.wl.dto.TechnicalStaffDto(d) from TechnicalStaff d where d.province.id= ?1 ")
	List<TechnicalStaffDto> getStaffFromProvince(Long id);
	
	@Query("select new com.globits.wl.dto.TechnicalStaffDto(d) from TechnicalStaff d where d.id != ?1")
	Page<TechnicalStaffDto> getListTechnicalStaff(Long id, Pageable pageable);
	
	@Query("select new com.globits.wl.dto.TechnicalStaffDto(d, true) from TechnicalStaff d where d.id = ?1")
	TechnicalStaffDto getTechnicalStaffById(Long id);
	
//	@Query("select new com.globits.wl.dto.TechnicalStaffDto(d) from TechnicalStaff d where d.name=?1 ")
//	List<TechnicalStaffDto> getTechnicalStaffByName(String name);
	
	@Query("select d from TechnicalStaff d where d.code=?1 ")
	List<TechnicalStaff> findTechnicalStaffByCode(String code);
}
