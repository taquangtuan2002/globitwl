package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FarmAnimalProductTargetExist;

@Repository
public interface FarmAnimalProductTargetExistRepository extends JpaRepository<FarmAnimalProductTargetExist, Long>{
	
	@Query("select o from FarmAnimalProductTargetExist o where o.farm.id = ?1 and o.productTarget.id = ?2 and o.animal.id=?3")
	FarmAnimalProductTargetExist findByFarmAnimalProductTarget(Long farmId,Long productTargetId, Long animalId);
	
	@Query("select o from FarmAnimalProductTargetExist o where (1=1) ")
	List<FarmAnimalProductTargetExist> getAll();
	
	@Query("select o from FarmAnimalProductTargetExist o where (1=1) and o.farm.id = ?1 ")
	List<FarmAnimalProductTargetExist> getByFarm(Long farmId);

}
