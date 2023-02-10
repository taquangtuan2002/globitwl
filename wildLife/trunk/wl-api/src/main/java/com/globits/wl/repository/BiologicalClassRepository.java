package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.BiologicalClass;
import com.globits.wl.dto.BiologicalClassDto;

@Repository
public interface BiologicalClassRepository extends JpaRepository<BiologicalClass, Long>{
	
	@Query("select new com.globits.wl.dto.BiologicalClassDto(b) from BiologicalClass b where b.name = ?1 and b.type = ?2")
	List<BiologicalClassDto> getBiologicalClassByName(String name, Integer type);
	
	@Query("from BiologicalClass b where b.name = ?1 and b.type = ?2")
	List<BiologicalClass> getByNameAndType(String name, Integer type);
	
	@Query("select new com.globits.wl.dto.BiologicalClassDto(b) from BiologicalClass b where b.sci = ?1 and b.type = ?2")
	List<BiologicalClassDto> getBiologicalClassBySci(String sci, Integer type);
	
	@Query("select count(b.id) from BiologicalClass b where b.parent.id = ?1")
	Long checkReferenceBiologicalClass(Long id);
	
	@Query("from BiologicalClass b where b.id = ?1")
	BiologicalClass findId(Long id);

	@Query("select count(a.id) from Animal a where a.animalClass = ?1")
	Long checkReferenceAnimal(String name);
	
	@Modifying
	@Query("update Animal a set a.animalClass = ?1, a.animalClassSci = ?2 where a.animalClass = ?3")
	void updateAnimalClassForAnimal(String animalClassUpdate, String Sci, String animalClassOrigin);
	
	@Modifying
	@Query("update Animal a set a.ordo = ?1, a.ordoSci = ?2 where a.ordo = ?3")
	void updateAnimalClassForOrdo(String nameUpdate, String Sci, String nameOrigin);
	
	@Modifying
	@Query("update Animal a set a.family = ?1, a.familySci = ?2 where a.family = ?3")
	void updateAnimalClassForFamily(String nameUpdate, String Sci, String nameOrigin);
	
	@Modifying
	@Query("update Animal a set a.animalClass = ?1, a.animalClassSci = ?2 where a.ordo = ?3 and a.animalClass = ? 4")
	void updateAnimalClassWhenChangeParentOrdo(String nameUpdate, String Sci, String nameOrigin, String animalClass);
	
	@Modifying
	@Query("update Animal a set a.ordo = ?1, a.ordoSci = ?2, a.animalClass = ?3, a.animalClassSci = ?4 where a.family = ?5 and a.ordo = ? 6")
	void updateAnimalClassWhenChangeParentFamily(String nameUpdate, String Sci, String animalClass, String animalClassSci, String nameOrigin, String ordo);
}
