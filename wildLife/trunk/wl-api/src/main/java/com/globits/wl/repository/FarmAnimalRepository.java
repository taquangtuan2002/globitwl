package com.globits.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FarmAnimal;
import com.globits.wl.domain.FarmStore;

@Repository
public interface FarmAnimalRepository extends JpaRepository<FarmAnimal, Long>{

}
