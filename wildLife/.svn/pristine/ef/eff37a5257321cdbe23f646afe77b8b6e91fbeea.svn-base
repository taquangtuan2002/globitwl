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
import com.globits.wl.domain.ImportExportLiveStockProduct;
import com.globits.wl.dto.ImportExportLiveStockProductDto;


@Repository
public interface ImportExportLiveStockProductRepository extends JpaRepository<ImportExportLiveStockProduct, Long>{
	
	@Query("select new com.globits.wl.dto.ImportExportLiveStockProductDto(iad) from ImportExportLiveStockProduct iad where iad.type=?1")
	Page<ImportExportLiveStockProductDto> getByPage(Pageable pageable,int type);

	@Query("select new com.globits.wl.dto.ImportExportLiveStockProductDto(iad) from ImportExportLiveStockProduct iad where iad.type=?1")
	Set<ImportExportLiveStockProductDto> getAll(int type);

	@Query("select new com.globits.wl.dto.ImportExportLiveStockProductDto(iad) from ImportExportLiveStockProduct iad where iad.id=?1")
	ImportExportLiveStockProductDto getById(Long id);
		
	@Query("select ielsp from ImportExportLiveStockProduct ielsp where ielsp.importLiveStockPorduct.id=?1 ")
	public List<ImportExportLiveStockProduct> findExportByImport(Long importLiveStockPorductId);
	
	@Query("select count(ielsp) from ImportExportLiveStockProduct ielsp where ielsp.type=?1 and ielsp.dateIssue>= ?2  and ielsp.dateIssue<= ?3")
	Integer count(int type, Date startDate, Date endDate);
	
	@Query("select ielsp from ImportExportLiveStockProduct ielsp where  ielsp.type=?1 and ielsp.batchCode=?2")
	List<ImportExportLiveStockProduct> findByTypeAndBatchCode(Integer type,String batchCode);
	
	@Query("select iad from ImportExportLiveStockProduct iad where  iad.type=?1 and iad.farm.id=?2 and iad.batchCode=?3")
	List<ImportExportLiveStockProduct> findBy(Integer type,Long farmId,String batchCode);
	
	@Query("select iad from ImportExportLiveStockProduct iad where  iad.type=-1 and iad.importLiveStockPorduct.id=?1 ")
	List<ImportExportLiveStockProduct> findByImportIdAndType(Long importId);
}
