package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.dto.FmsAdministrativeUnitDto;

@Repository
public interface FmsAdministrativeUnitRepository extends JpaRepository<FmsAdministrativeUnit, Long>{

	@Query("select new com.globits.wl.dto.FmsAdministrativeUnitDto(administrative) from FmsAdministrativeUnit administrative where administrative.id != ?1")
	Page<FmsAdministrativeUnitDto> getListAdministrative(Long Id,Pageable pageable);
	
	@Query("select new com.globits.wl.dto.FmsAdministrativeUnitDto(administrative,true) from FmsAdministrativeUnit administrative where administrative.id = ?1")
	FmsAdministrativeUnitDto getAdministrativeUnitById(Long id);
	
	@Query("select new com.globits.wl.dto.FmsAdministrativeUnitDto(administrative,1) from FmsAdministrativeUnit administrative  where administrative.id = ?1")
	FmsAdministrativeUnitDto getById(Long id);
	
	@Query("select new com.globits.wl.dto.FmsAdministrativeUnitDto(administrative,true) from FmsAdministrativeUnit administrative ")
	List<FmsAdministrativeUnitDto> getAll();

	@Query("select new com.globits.wl.dto.FmsAdministrativeUnitDto(administrative,true) from FmsAdministrativeUnit administrative where administrative.parent.id is NULL or administrative.parent.id = 0 ")
	List<FmsAdministrativeUnitDto> getAllCity();
	
	@Query("select new com.globits.wl.dto.FmsAdministrativeUnitDto(administrative,true) from FmsAdministrativeUnit administrative where (administrative.parent.id is NULL or administrative.parent.id = 0) and  administrative.region.id=?1")
	List<FmsAdministrativeUnitDto> getAllCityByRegion(Long regionId);

	@Query("select new com.globits.wl.dto.FmsAdministrativeUnitDto(a,true) from FmsAdministrativeUnit a "
			+ " where a.parent.id = ?1")
	List<FmsAdministrativeUnitDto> getAllByParentId(Long parentId);

	@Query("select u from FmsAdministrativeUnit u where u.code = ?1")
	List<FmsAdministrativeUnit> findListByCode(String code);
	
	// pagination tree data //
	@Query("select COUNT(*) from FmsAdministrativeUnit cs where cs.parent is null ")
	Long countDadAdministrativeUnit();
	
	@Query("select cs from FmsAdministrativeUnit cs left join fetch cs.subAdministrativeUnits where cs.parent is null order by cs.code asc")
	List<FmsAdministrativeUnit> findTreeAdministrativeUnit(Pageable pageable);
	
	@Query("select u from FmsAdministrativeUnit u  where u.id = ?1")
	FmsAdministrativeUnit findById(Long id);
	
	@Query("select cs from FmsAdministrativeUnit cs  where cs.parent is null order by cs.code asc")
	List<FmsAdministrativeUnit> findTreeAdministrativeUnitNotSub(Pageable pageable);
	
	@Query("select au from FmsAdministrativeUnit au where  au.parent.id=?1")
	List<FmsAdministrativeUnit> getListdministrativeUnitbyParent(Long parentId);
	
	@Query("select u from FmsAdministrativeUnit u where u.mapCode = ?1 and u.parent is null")
	List<FmsAdministrativeUnit> findListByMapCode(String mapCode);
	
	@Query("select a.id from FmsAdministrativeUnit a "
			+ " where a.parent.id = ?1")
	List<Long> getAllIdByParentId(Long parentId);
	
	@Query("select a from FmsAdministrativeUnit a "
			+ " where a.name=?1 "
			+ " AND a.parent.name = ?2 "
			+ " AND a.parent.parent.name =?3 ")
	public List<FmsAdministrativeUnit> getAdministrativeUnitByName(String wardName,String disName,String proName);

	
	@Query("select DISTINCT a.code from FmsAdministrativeUnit a "
			+ " where (1=1) AND( a.id=?1 "
			+ " OR a.parent.id = ?1 "
			+ " OR a.parent.parent.id =?1 ) ")
	List<String> getAllCodeById(Long id);
	
	
}
