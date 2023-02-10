package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Original;
import com.globits.wl.dto.AnimalTypeDto;

@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

	@Query("select new com.globits.wl.dto.AnimalTypeDto(cs) from AnimalType cs order by cs.id")
	Page<AnimalTypeDto> getAll(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.AnimalTypeDto(cs) from AnimalType cs order by cs.id")
	List<AnimalTypeDto> getAllDto();
	
	@Query("select b from AnimalType b where b.code = ?1")
	List<AnimalType> findByCode(String code);
	
	@Query("select o from AnimalType o where o.name = ?1")
	List<AnimalType> findByName(String name);

	@Query("select o from AnimalType o where lower(o.name) = lower(?1)")
	List<AnimalType> findByAnimalName(String animalTypeId);
}
