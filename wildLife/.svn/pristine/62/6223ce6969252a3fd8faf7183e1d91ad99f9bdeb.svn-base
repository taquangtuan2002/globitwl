package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.HusbandryMethod;
import com.globits.wl.domain.LiveStockProduct;
import com.globits.wl.dto.LiveStockProductDto;

@Repository
public interface LiveStockProductRepository extends JpaRepository<LiveStockProduct,Long>{
	
	@Query("select new com.globits.wl.dto.LiveStockProductDto(o) from LiveStockProduct o order by o.id")
	Page<LiveStockProductDto> getPageAll(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.LiveStockProductDto(o) from LiveStockProduct o where o.id = ?1")
	LiveStockProductDto getById(Long id);
	
	@Query("select new com.globits.wl.dto.LiveStockProductDto(o) from LiveStockProduct o order by o.id")
	List<LiveStockProductDto> getAllDtos();
	
	@Query("select o from LiveStockProduct o where o.code = ?1")
	List<LiveStockProduct> findByCode(String code);
	
	@Query("select o from LiveStockProduct o where o.name = ?1")
	List<LiveStockProduct> findByName(String name);
	@Query("select o from LiveStockProduct o where o.unitQuantity.id = ?1 or o.unitAmount.id = ?1")
	List<LiveStockProduct> findByUnit(Long unitId);
	
	@Query("select o from LiveStockProduct o where o.animal.id = ?1")
	List<LiveStockProduct> findByAnimalId(Long animalId);
	

}
