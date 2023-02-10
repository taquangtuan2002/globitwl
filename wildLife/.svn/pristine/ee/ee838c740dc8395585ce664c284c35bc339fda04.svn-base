package com.globits.wl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.ReportFormAnimalEgg;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.ReportFormAnimalEggDto;

@Repository
public interface ReportFormAnimalEggRepository extends JpaRepository<ReportFormAnimalEgg, Long>{
	@Query("select new com.globits.wl.dto.ReportFormAnimalEggDto(p) from ReportFormAnimalEgg p where  p.farm.id = ?1 and  p.animal.id = ?2 and p.dateReport<=?3")
	List<ReportFormAnimalEggDto> getChildIncrement(Long farmId,Long animalId, Date dateReport);
	@Query("select p from ReportFormAnimalEgg p where  p.farm.id = ?1 order by p.dateReport asc ")
	List<ReportFormAnimalEgg> getListByFarm(Long farmId);
	@Query("select p from ReportFormAnimalEgg p where  p.farm.id = ?1 and  p.animal.id = ?2 order by p.dateReport asc ")
	List<ReportFormAnimalEgg> getListByFarmAnimal(Long farmId,Long animalId);
	@Query("select distinct (p.animal.id) from ReportFormAnimalEgg p where  p.farm.id = ?1  ")
	List<Long> getListAnimalIdByFarm(Long farmId);
}
