package com.globits.wl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.ExportEgg;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.dto.ExportEggDto;

@Repository
public interface ExportEggRepository extends JpaRepository<ExportEgg, Long>{
	@Query("select new com.globits.wl.dto.ExportEggDto(fa) from ExportEgg fa")
	Page<ExportEggDto> getPageDto(Pageable pageable);

	@Query("select new com.globits.wl.dto.ExportEggDto(fa) from ExportEgg fa")
	List<ExportEggDto> getAllDto();

	@Query("select count(fa) from ExportEgg fa where fa.type=?1 and fa.dateExport>= ?2  and fa.dateExport<= ?3")
	Integer count(int i, Date importDate, Date end);

	@Query("select iad from ExportEgg iad where  iad.type=?1 and iad.batchCode=?2")
	List<ExportEgg> findByTypeAndBatchCode(Integer value, String bathCode);
	@Query("select iad from ExportEgg iad where  iad.importEgg.id = ?1")
	List<ExportEgg> findExportByImport(Long importEggId);

	@Query("select iad from ExportEgg iad where  iad.importEgg.type = ?1 and iad.farm.id = ?2 and iad.batchCode = ?3")
	List<ExportEgg> findBy(Integer value, Long id, String batchCode);
	// tìm tất cả các bản ghi có ngày nhập lớn hơn ngày xuất
	@Query("select iad from ExportEgg iad where  iad.dateExport < ?1 and iad.importEgg.id = ?2")
	List<ExportEgg> findExportGrantByDate(Date dateImport, Long importEggId);
	
//	@Query("select iad from ExportEgg iad where iad.exportEgg.id=?1 and iad.isExChange=?2")
//	public List<ExportEgg> getImportChangeByExportExChange(Long exportEggId, boolean exChange);

}
