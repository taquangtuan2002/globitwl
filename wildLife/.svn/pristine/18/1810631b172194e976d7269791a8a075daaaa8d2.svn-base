package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FarmAnimalProductTargetExist;
import com.globits.wl.domain.FarmProductTargetExist;

@Repository
public interface FarmProductTargetExistRepository extends JpaRepository<FarmProductTargetExist, Long>{
	
	@Query("select o from FarmProductTargetExist o where o.farm.id = ?1 and o.productTarget.id = ?2")
	FarmProductTargetExist findByFarmProductTarget(Long farmId,Long productTargetId);
	@Query("select o from FarmProductTargetExist o where (1=1) ")
	List<FarmProductTargetExist> getAll();
	@Query("select o from FarmProductTargetExist o where (1=1) and o.farm.id = ?1")
	List<FarmProductTargetExist> getByFarm(Long farmId);

}
