package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FarmHusbandryMethod;

@Repository
public interface FarmHusbandryMethodRepository extends JpaRepository<FarmHusbandryMethod, Long>{

	@Query("select f FROM FarmHusbandryMethod f where f.farm.id = ?1 AND f.husbandryMethod.id = ?2")
	List<FarmHusbandryMethod> findByFarmIdAndHusbandryMethodId(Long farmId, Long husbandryMethodId);
	
}
