package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmSize;
import com.globits.wl.dto.DoubtsOverlapDto;
import com.globits.wl.dto.FarmDto;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long>{
	
	@Query("select new com.globits.wl.dto.FarmDto(fa) from Farm fa where fa.code is NOT NULL and fa.name is not null and (fa.isDuplicate is null or fa.isDuplicate <> true) order by fa.createDate")
	Page<FarmDto> getPageDto(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.FarmDto(fa) from Farm fa "
			+ "LEFT JOIN fa.attachments atc "
			+ "LEFT JOIN fa.stores fs")
	List<FarmDto> getAll();

	@Query("select new com.globits.wl.dto.FarmDto(fa,true) from Farm fa "
			+ "LEFT JOIN fa.attachments atc "
			+ "LEFT JOIN fa.stores fs")
	List<FarmDto> getAllSimpleDto();
	
	@Query("select fa from Farm fa "
			+ "LEFT JOIN fetch fa.attachments atc "
			+ "LEFT JOIN fetch fa.stores fs where fa.id = ?1")
	Farm getById(Long id);
	
	@Query("select count(*) from Farm fa where fa.administrativeUnit.parent.id=?1 and (fa.isDuplicate is null or fa.isDuplicate = false)")
	long count(long auId);
	
	@Query("select max(fa.code) from Farm fa where fa.administrativeUnit.parent.id=?1 and (fa.isDuplicate is null or fa.isDuplicate = false)")
	String maxCode(long auId);
	
	@Query("select b from Farm b where b.code = ?1 and (b.isDuplicate is null or b.isDuplicate = false) ")
	List<Farm> findByCode(String code);
	
	@Query("select b from Farm b where b.createdBy = ?1 and (b.isDuplicate is null or b.isDuplicate = false) ")
	List<Farm> findByCreatedBy(String username);
	//Tính cả trường hợp trại trùng nhau
	@Query("select COUNT(b.id) from Farm b where b.code = ?1 ")
	Long countByCode(String code);
	
	@Query("select b from Farm b where b.mapCode = ?1 and (b.isDuplicate is null or b.isDuplicate = false)")
	List<Farm> findBymapCode(String mapCode);
	
	@Query("select b from Farm b where b.mapCode = ?1 and b.administrativeUnit.code=?2 AND (b.isDuplicate is null or b.isDuplicate = false)")
	List<Farm> findBymapCodeAndAdministrativeUnitCode(String mapCode,String administrativeUnitCode);
	
	@Query("select b from Farm b where b.code is null or b.code=''")
	List<Farm> findNullCodeFarm();
	
	@Query("select new com.globits.wl.dto.FarmDto(fa,true) from Farm fa ")
	List<FarmDto> getAllSimple();

	@Query("select new com.globits.wl.dto.FarmDto(fa) from Farm fa WHERE (fa.isDuplicate is null or fa.isDuplicate <> true) and fa.yearRegistration = ?1 "
			+ " order by fa.administrativeUnit.parent.parent.id,fa.administrativeUnit.parent.id,fa.administrativeUnit.id,fa.name ")
	List<FarmDto> getAllByYearRegister(String year);
	
	@Query(" SELECT f FROM Farm f WHERE 1=1 "
			+" AND f.id in ( SELECT DISTINCT(a.farm.id) FROM AnimalReportData a WHERE a.year=?1 ) "
			+" AND f.administrativeUnit.parent.parent.id=?2 ")
	Page<Farm> getListByYearWithReport(int year,Long provinceId,Pageable pageable);
	
	
	@Query("select new com.globits.wl.dto.DoubtsOverlapDto(fa.name,fa.administrativeUnit.name,fa.administrativeUnit.parent.name,fa.administrativeUnit.parent.parent.name) from Farm fa "			
			+ "WHERE fa.name IS NOT NULL GROUP BY fa.name, fa.administrativeUnit.name, fa.administrativeUnit.parent.name,  fa.administrativeUnit.parent.parent.name")
	List<DoubtsOverlapDto> getAllDoubtsOverlap();

}
