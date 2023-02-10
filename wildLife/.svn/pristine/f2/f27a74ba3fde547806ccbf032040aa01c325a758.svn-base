package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FarmReportPeriod;
import com.globits.wl.dto.FarmReportPeriodDto;

@Repository
public interface FarmReportPeriodRepository extends JpaRepository<FarmReportPeriod, Long>{
	
	@Query("select new com.globits.wl.dto.FarmReportPeriodDto(fr) FROM FarmReportPeriod fr where fr.farm.id =?1 and fr.type = ?2 order by id desc")
	List<FarmReportPeriodDto> getByFarmReportPeriod(Long farmId, int type);
	
}
