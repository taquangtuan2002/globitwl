package com.globits.wl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.globits.wl.domain.ImportAnimalFeed;
import com.globits.wl.dto.ImportAnimalFeedDto;

public interface ImportAnimalFeedRepository extends JpaRepository<ImportAnimalFeed, Long>{
	
	@Query("select new com.globits.wl.dto.ImportAnimalFeedDto(exp) from ImportAnimalFeed exp where exp.type = ?2 ")
	Page<ImportAnimalFeedDto> getListAnimalFeedByType(Pageable pageable, int type);
	
	@Query("select new com.globits.wl.dto.ImportAnimalFeedDto(exp) from ImportAnimalFeed exp where exp.id = ?1")
	ImportAnimalFeedDto getAnimalFeedById(Long id);

	@Query("select count(exp) from ImportAnimalFeed exp where exp.type = ?1 and exp.dateIssue >= ?2  and exp.dateIssue <= ?3 ")
	Integer count(int type, Date startDate, Date end);

	@Query("select exp from ImportAnimalFeed exp where  exp.type = ?1 and exp.code = ?2 ")
	List<ImportAnimalFeedDto> findByTypeAndBatchCode(Integer type, String code);

	@Query("select id from ImportAnimalFeed id where  id.importAnimalFeed.id = ?1")
	List<ImportAnimalFeed> findExportByImport(Long id);

	@Query("select id from ImportAnimalFeed id where id.dateIssue < ?1 and id.importAnimalFeed.id = ?2")
	List<ImportAnimalFeed> getListExportByGTImportDateAndImportAnimalFeed(Date dateImport, Long importDrugId);
}
