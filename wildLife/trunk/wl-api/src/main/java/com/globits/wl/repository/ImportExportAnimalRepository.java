package com.globits.wl.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.dto.ImportExportAnimalDto;

@Repository
public interface ImportExportAnimalRepository extends JpaRepository<ImportExportAnimal, Long>{

	@Query("select new com.globits.wl.dto.ImportExportAnimalDto(iad) from ImportExportAnimal iad where iad.type=?1")
	Page<ImportExportAnimalDto> getByPage(Pageable pageable,int type);

	@Query("select new com.globits.wl.dto.ImportExportAnimalDto(iad) from ImportExportAnimal iad where iad.type=?1")
	Set<ImportExportAnimalDto> getAll(int type);
	
	@Query("select new com.globits.wl.dto.ImportExportAnimalDto(iad) from ImportExportAnimal iad where iad.id=?1")
	ImportExportAnimalDto getById(Long id);
	
	@Query("select count(fa) from ImportExportAnimal fa where fa.type=?1 and fa.dateImport=?2")
	Integer count(int type, Date dateImport);
	
	@Query("select iad from ImportExportAnimal iad where iad.id=?1")
	ImportExportAnimal findById(Long id);
	
	@Query("select iad from ImportExportAnimal iad where iad.batchCode=?1 and iad.type=?2 and iad.farm.id=?3")
	List<ImportExportAnimal> findByCode(String batchCode, int type, Long farmId);
	@Query("select iad from ImportExportAnimal iad where iad.batchCode=?1 and iad.type=?2 and iad.farm.id=?3 and iad.voucherCode=?4")
	ImportExportAnimal findBy(String batchCode, int type, Long farmId, String voucherCode);
	
	@Query("select sum(iad.quantity*iad.type) from ImportExportAnimal iad where  iad.farm.id=?1")
	Double sumRemainQuantity( Long farmId);
	
	@Query("select sum(iad.remainQuantity) from ImportExportAnimal iad where  iad.farm.id=?1  and iad.productTarget.id=?2 and iad.type=1")
	Double sumRemainQuantityByFarmProducTarget( Long farmId,Long productTargetId);
	
	@Query("select sum(iad.remainQuantity) from ImportExportAnimal iad where  iad.farm.id=?1  and iad.productTarget.id=?2 and iad.animal.id=?3 and iad.type=1")
	Double sumRemainQuantityByFarmAnimalProducTarget( Long farmId,Long productTargetId,Long animalId);
	
	@Query("select count(fa) from ImportExportAnimal fa where fa.type=?1 and fa.dateImport>= ?2  and fa.dateImport<= ?3")
	Integer count(int type, Date startDate, Date endDate);
	
	@Query("select iad from ImportExportAnimal iad where  iad.farm.id=?1")
	List<ImportExportAnimal> findByFarm(Long farmId);
	
	@Query("select iad from ImportExportAnimal iad where iad.farm.id=?1 AND iad.animal.id=?2 ")
	List<ImportExportAnimal> findByFarmAndAnimal(Long farmId,Long animalId);
	
	@Query("select iad from ImportExportAnimal iad where  iad.type=?1")
	List<ImportExportAnimal> findByType(Integer type);
	
	@Query("select iad from ImportExportAnimal iad where  iad.type=?1 and iad.farm.id=?2 and iad.batchCode=?3")
	List<ImportExportAnimal> findBy(Integer type,Long farmId,String batchCode);
	
	@Query("select sum(iad.remainQuantity) from ImportExportAnimal iad where  iad.farm.id=?1  and iad.productTarget.id=?2 and iad.animal.parent.id=?3 and iad.type=1")
	Double sumRemainQuantityByFarmParentAnimalProducTarget( Long farmId,Long productTargetId,Long animalId);
	
	@Query("select iad from ImportExportAnimal iad where  iad.type=?1 and iad.farm.id=?2")
	List<ImportExportAnimal> findByFarmAndType(Integer type,Long farmId);
	
	@Query("select iad from ImportExportAnimal iad where  iad.type=?1 and iad.batchCode=?2")
	List<ImportExportAnimal> findByTypeAndBatchCode(Integer type,String batchCode);

	@Query("select iad from ImportExportAnimal iad where iad.importAnimal.id=?1 ")
	public List<ImportExportAnimal> findExportByImport(Long importAnimalId);
	
	@Query("select iad from ImportExportAnimal iad where iad.exportAnimal.id=?1 and iad.isExChange=?2")
	public List<ImportExportAnimal> getImportChangeByExportExChange(Long exportAnimalId, boolean exChange);

	@Query("select iad from ImportExportAnimal iad where iad.dateExport < ?1 and iad.importAnimal.id=?2 ")
	List<ImportExportAnimal> findExportByImportGranterDate(Date date, Long importId);

	@Query("select iad from ImportExportAnimal iad where iad.chipCode = ?1 and iad.importAnimal.type =?2 ")
	List<ImportExportAnimal> getListAnimalWithChipCodeAndType(String chipCode, Integer value);
	
}
