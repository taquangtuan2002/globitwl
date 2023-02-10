package com.globits.wl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.globits.wl.domain.ExportEgg;
import com.globits.wl.domain.ImportDrug;
import com.globits.wl.dto.ImportDrugDto;

public interface ImportDrugRepository extends JpaRepository<ImportDrug, Long>{
	
	@Query("select new com.globits.wl.dto.ImportDrugDto(exp) from ImportDrug exp ")
	Page<ImportDrugDto> getListImportDrug(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.ImportDrugDto(exp) from ImportDrug exp where exp.id = ?1")
	ImportDrugDto getImportDrugById(Long id);
	
	@Query("select count(fa) from ImportDrug fa where fa.type=?1 and fa.dateImport>= ?2  and fa.dateImport<= ?3")
	Integer count(int i, Date dateImport, Date end);

	@Query("select id from ImportDrug id where  id.type=?1 and id.code=?2")
	List<ImportDrug> findByTypeAndBatchCode(Integer value, String bathCode);

	@Query("select id from ImportDrug id where  id.importDrug.id = ?1")
	List<ImportDrug> findExportByImport(Long importDrugId);

	@Query("select id from ImportDrug id where id.dateImport < ?1 and id.importDrug.id = ?2")
	List<ImportDrug> getListExportByGTImportDateAndImportDrug(Date dateImport, Long importDrugId);

}
