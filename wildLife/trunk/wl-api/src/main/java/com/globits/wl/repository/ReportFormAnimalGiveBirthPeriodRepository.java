package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.ReportFormAnimalGiveBirthPeriod;

@Repository
public interface ReportFormAnimalGiveBirthPeriodRepository extends JpaRepository<ReportFormAnimalGiveBirthPeriod, Long>{
	@Query("select rf FROM ReportFormAnimalGiveBirthPeriod rf Where rf.year = ?1 and rf.month = ?2 and rf.date = ?3 and rf.farm.id = ?4")
	List<ReportFormAnimalGiveBirthPeriod> findByYearMonthDateFarmIdAnimalId(Integer year, Integer month, Integer date,
			Long farmId);
	
}
