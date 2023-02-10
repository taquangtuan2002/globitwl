package com.globits.wl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.ReportFormAnimalGiveBirth;

@Repository
public interface ReportFormAnimalGiveBirthRepository extends JpaRepository<ReportFormAnimalGiveBirth, Long>{

	@Query("select entity from ReportFormAnimalGiveBirth entity WHERE entity.farm.id =?1 AND entity.animal.id =?2 AND entity.dateReport > ?3 AND entity.id != ?4 ORDER BY entity.dateReport ")
	List<ReportFormAnimalGiveBirth> getAllDataNeedUpdateBy(Long farmId, Long animalId, Date dateReport, Long id);

	@Query("select SUM(entity.remainQuantity) from ReportFormAnimalGiveBirth entity WHERE entity.farm.id =?1 AND entity.animal.id =?2 AND entity.dateReport <= ?3 ")
	Long sumRemainQuantityBefore(Long farmId, Long animalId, Date dateReport);

	@Query("select entity from ReportFormAnimalGiveBirth entity WHERE entity.farm.id =?1 AND entity.animal.id =?2 AND entity.dateReport < ?3 AND entity.id != ?4 ORDER BY entity.dateReport ")
	List<ReportFormAnimalGiveBirth> getAllDataBeforeBy(Long id, Long id2, Date dateReport, Long id3);
	
	@Query("select entity from ReportFormAnimalGiveBirth entity WHERE entity.farm.id =?1 ")
	List<ReportFormAnimalGiveBirth> getListByFarmId(Long farmId);
	
}
