package com.globits.wl.repository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.FarmAnimal;
import com.globits.wl.domain.FarmSize;
import com.globits.wl.dto.AnimalDto;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
	@Query("select new com.globits.wl.dto.AnimalDto(a) from Animal a order by a.orderCode, a.name, a.code")
	Page<AnimalDto> getAll(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.AnimalDto(a) from Animal a where a.id = ?1")
	AnimalDto getAnimalById(Long id);
	
	@Query("select new com.globits.wl.dto.AnimalDto(a) from Animal a order by a.orderCode, a.name, a.code")
	List<AnimalDto> getAllDto();
	
	@Query("select new com.globits.wl.dto.AnimalDto(a) from Animal a where a.parent is null order by a.orderCode, a.name, a.code")
	List<AnimalDto> getListParent();
	
	@Query("select new com.globits.wl.dto.AnimalDto(a) from Animal a where a.parent.id=?1 order by a.orderCode, a.name, a.code")
	List<AnimalDto> getListByParent(Long parentId);
	
	@Query("select b from Animal b where b.code = ?1")
	List<Animal> findByCode(String code);
	
	@Query("select b from Animal b where b.name = ?1")
	List<Animal> findByName(String name);

	@Query("select b from Animal b where lower(b.scienceName) = lower(?1)")
	List<Animal> findByAnimalName(String scienceName);
	
	@Query("select b from Animal b where lower(b.scienceName) = lower(?1)")
	List<Animal> findByAnimalScienceName(String animalTypeId);
	
	@Query("select a from Animal a where lower(a.family) = lower(?1)")
	List<Animal> getListAnimalByFamily(String family);
	
	@Query("select DISTINCT(a.id) from Animal a")
	List<Long> getListIdAnimal();
	
	@Query("select DISTINCT(a.animalClassSci) from Animal a where a.animalClassSci is not null and lower(a.animalClass) = lower(?1)")
	List<String> getListAnimalClassScienceName(String animalClass);
	
	@Query("select a.animalClass from Animal a where lower(a.animalClassSci) = lower(?1)")
	List<String> getListAnimalClass(String classSci);
	
	@Query("select a.ordo from Animal a where lower(a.ordoSci) = lower(?1) ")
	List<String> getListAnimalOrdo(String ordoSci);
	
	@Query("select a.family from Animal a where lower(a.familySci) = lower(?1) ")
	List<String> getListAnimalFamily(String familySci);
}
