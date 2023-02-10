package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.globits.wl.domain.ExportAnimal;
import com.globits.wl.dto.ExportAnimalDto;

public interface ExportAnimalRepository extends JpaRepository<ExportAnimal, Long>{
	
	@Query("select new com.globits.wl.dto.ExportAnimalDto(exp) from ExportAnimal exp ")
	Page<ExportAnimalDto> getListExportAnimail(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.ExportAnimalDto(exp) from ExportAnimal exp where exp.id = ?1")
	ExportAnimalDto getExportAnimalById(Long id);
	
	@Query("select new com.globits.wl.dto.ExportAnimalDto(exp) from ExportAnimal exp ")
	List<ExportAnimalDto> getAll();

}
