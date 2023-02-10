package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.dto.AnimalReportDataDto;

@Repository
public interface AnimalReportDataRepository extends JpaRepository<AnimalReportData, Long> {
	@Query("select new com.globits.wl.dto.AnimalReportDataDto(a) from AnimalReportData a where a.farm is not null AND a.animal is not null ")
	Page<AnimalReportDataDto> getByPage(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.AnimalReportDataDto(a) from AnimalReportData a where a.farm.id=?1 ")
	List<AnimalReportDataDto> getListByFarmId(Long farmId);
	
	@Query("select a from AnimalReportData a where a.farm.id=?1 ")
	List<AnimalReportData> getListEntityByFarmId(Long farmId);

	@Query("select a from AnimalReportData a where a.farmReportPeriod.id=?1 ")
	List<AnimalReportData> getAllByFarmReportPeriodId(Long id);

	@Query("select new com.globits.wl.dto.AnimalReportDataDto(a) from AnimalReportData a where a.year = ?1 and a.month = ?2 and a.farm.id = ?3 and a.animal.id = ?4")
	List<AnimalReportDataDto> findByYearAndMonth(Integer year, Integer month, Long farmId, Long animalId);
	
	@Query("select a from AnimalReportData a where a.year = ?1 and a.month = ?2 and a.day=?3 and a.farm.id = ?4 and a.animal.id = ?5 and a.type = ?6")
	List<AnimalReportData> findByYearAndMonthFarmAnimal(Integer year, Integer month, Integer day, Long farmId, Long animalId,int type);

	@Query("select new com.globits.wl.dto.AnimalReportDataDto(a) from AnimalReportData a where a.farm.id=?1 order by a.year desc , a.quarter desc, a.month desc, a.animal.id")
	Page<AnimalReportDataDto> getPageByFarmId(Long farmId, Pageable pageable);
	
	@Query("SELECT ard FROM AnimalReportData ard where ard.farm.id = ?1 AND ard.animal.id in (?2) AND ard.year = ?3 AND ard.month is null AND ard.day is null")
	List<AnimalReportData> getAnimalReportDataByFarmAnimalYear(Long farmId, List<Long> animalIds, Integer year);

	@Query("SELECT ard FROM AnimalReportData ard where ard.farm.id = ?1 AND ard.animal.id = ?2 AND ard.type = ?3 AND ard.year = ?4 AND ard.month is not null ORDER BY ard.year desc, ard.month desc, ard.day desc, ard.total")
	List<AnimalReportData> findListAnimalReportDataBy(Long farmId, Long animalId, Integer type, Integer year);

	@Query("SELECT ard FROM AnimalReportData ard where ard.year = ?1 AND ard.farm.id = ?2 AND ard.animal.id = ?3 AND ard.type = ?4 AND ard.month is null AND ard.day is null")
	List<AnimalReportData> findByYearAndMonthFarmAnimalAndMonthDayNull(Integer year, Long farmId, Long animalId, Integer type);

//	@Query(" SELECT (a.farm.id) FROM AnimalReportData a WHERE a.year=?1 ")
//	List<Long> getListFarmIdByYear(int year);
	
	@Query("select DISTINCT(a.animal.id) from AnimalReportData a where a.farm.id=?1 ")
	List<Long> getListAnimalIdByFarmId(Long farmId);
	
	@Query("select DISTINCT(a.animal.id) from AnimalReportData a where a.farm.id=?1 and a.year =?2 ")
	List<Long> getListByFarmIdAndYear(Long farmId, Integer year);
	
}
