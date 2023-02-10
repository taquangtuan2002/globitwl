package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.dto.InjectionPlantDto;

@Repository
public interface InjectionPlantRepository extends JpaRepository<InjectionPlant, Long>{
@Query("select new com.globits.wl.dto.InjectionPlantDto(fa) from InjectionPlant fa")
	Page<InjectionPlantDto> getPageDto(Pageable pageable);
@Query("select new com.globits.wl.dto.InjectionPlantDto(fa) from InjectionPlant fa")

	List<InjectionPlantDto> getAllDto();

}
