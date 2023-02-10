package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.IndividualAnimal;

@Repository
public interface IndividualAnimalRepository extends JpaRepository<IndividualAnimal, Long> {
	@Query("select ia FROM IndividualAnimal ia where ia.code = ?1")
	List<IndividualAnimal> findListByCode(String code);

}
