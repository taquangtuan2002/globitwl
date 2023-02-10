package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.ReportPeriod;

@Repository
public interface ReportPeriodRepository extends JpaRepository<ReportPeriod, Long> {
	@Query("select rp FROM ReportPeriod rp where rp.year = ?1 and rp.month = ?2 and rp.date = ?3 and rp.farm.id = ?4 and rp.type=?5")
	List<ReportPeriod> findReportPeriodByYearMonthDate(Integer year, Integer month, Integer date, Long farmId, Integer type);

//	@Query("select entity from ReportPeriod entity where entity.type = ?1 AND entity.farm.id = ?2 AND entity.year = ?3 AND entity.month = ?4 AND entity.date = ?5 ORDER BY entity.date DESC ")
//	List<ReportPeriod> getAllByYearMonthDate(Integer type, Long farmId, Integer year, Integer month, Integer date);

	@Query("select rp FROM ReportPeriod rp where rp.date = ?1 and rp.month = ?2 and rp.year = ?3 and rp.type = ?4 and rp.farm.id = ?5")
	List<ReportPeriod> findByDayMonthYearType(int day, int month, int year, Integer value, Long farmId);
	
	@Query("select rp FROM ReportPeriod rp where rp.farm.id = ?1")
	List<ReportPeriod> findByFarmId(Long farmId);

	@Query("select DISTINCT(rp.year) FROM ReportPeriod rp where rp.farm.id = ?1")
	List<Integer> getListYearByFarmId(Long id);

}
