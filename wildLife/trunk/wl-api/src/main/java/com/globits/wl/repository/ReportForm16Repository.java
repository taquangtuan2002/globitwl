package com.globits.wl.repository;

import com.globits.wl.domain.Farm;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.dto.ReportForm16Dto;
import org.hibernate.jpa.TypedParameterValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportForm16Repository extends JpaRepository<ReportForm16, Long> {
	@Query("select rp.reportPeriod FROM ReportForm16 rp where rp.id = ?1")
	ReportPeriod findByReportForm16(Long reportFormId);
	
	@Query("select DISTINCT(rf16.animal.id) from ReportForm16 rf16 where rf16.farm.id=?1 ")
	List<Long> getListAnimalIdByFarmId(Long farmId);
	
	@Query("select rf16 from ReportForm16 rf16 where rf16.reportPeriod.id=?1 AND rf16.id not in (?2) ")
	List<ReportForm16> getListRemoveFromPeriodList(Long periodId,List<Long> reportForm16Ids);
	
	@Query("select rf16 from ReportForm16 rf16 where rf16.reportPeriod.id=?1")
	List<ReportForm16> getListRemoveFromPeriodList(Long periodId);

	@Query("select DISTINCT(rf16.animal.id) from ReportForm16 rf16 where rf16.farm.id=?1 AND rf16.reportPeriod.year = ?2 ")
	List<Long> getListAnimalIdByFarmIdAndYear(Long id, Integer year);

	List<ReportForm16> findAllByFarm(Farm farmEntity);
	
	@Query("select rp16 from ReportForm16 rp16 where rp16.farm.id =?1 and rp16.animal.id = ?2 order by rp16.dateReport desc")
	List<ReportForm16> findAllByFarmAndSortDate(Long farmId, Long animalId);

	@Query("select new com.globits.wl.dto.ReportForm16Dto(f16) from ReportForm16 f16 " +
			" where  (:farmId is null or f16.farm.id =:farmId) " +
			" and (:fromDate is null or f16.dateReport >= :fromDate) " +
			" and (:toDate is null or f16.dateReport <= :toDate) " +
			" and (:textSearch is null or f16.farm.name like concat('%',:textSearch,'%') or f16.farm.code like concat('%',:textSearch,'%') ) " +
			" and (:year is null or f16.reportPeriod.year = :year) " +
			" and (:month is null or f16.reportPeriod.month = :month) " +
			" and (:day is null or f16.reportPeriod.date = :day) " +
			" and (:animalClass is null or f16.animal.animalClass like concat('%',:animalClass,'%')) " +
			" and (:animalOrdo is null or f16.animal.ordo like concat('%',:animalOrdo,'%')) " +
			" and (:animalFamily is null or f16.animal.family like concat('%',:animalFamily,'%')) " +
			" and (:animalId is null or f16.animal.id = :animalId) " +
			" and (:provinceId is null or f16.province.id =:provinceId) " +
			" and (:districtId is null or f16.district.id =:districtId) " +
			" and (:wardId is null or f16.administrativeUnit.id = :wardId)")
	List<ReportForm16Dto> findAllForReportExcel(@Param("farmId") TypedParameterValue farmId,
												@Param("fromDate") TypedParameterValue fromDate,
												@Param("toDate") TypedParameterValue toDate,
												@Param("textSearch") TypedParameterValue textSearch,
												@Param("year") TypedParameterValue year,
												@Param("month") TypedParameterValue month,
												@Param("day") TypedParameterValue day,
												@Param("animalClass") TypedParameterValue animalClass,
												@Param("animalOrdo") TypedParameterValue animalOrdo,
												@Param("animalFamily") TypedParameterValue animalFamily,
												@Param("animalId") TypedParameterValue animalId,
												@Param("provinceId") TypedParameterValue provinceId,
												@Param("districtId") TypedParameterValue districtId,
												@Param("wardId") TypedParameterValue wardId);
}
