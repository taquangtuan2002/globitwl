package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.InjectionTime;
import com.globits.wl.dto.InjectionTimeDto;

@Repository
public interface InjectionTimeRepository extends JpaRepository<InjectionTime, Long> {
	@Query("select new com.globits.wl.dto.InjectionTimeDto(fa) from InjectionTime fa")
	Page<InjectionTimeDto> getPageDto(Pageable pageable);

	@Query("select new com.globits.wl.dto.InjectionTimeDto(fa) from InjectionTime fa")

	List<InjectionTimeDto> getAllDto();

	@Query("select b from InjectionTime b where b.code = ?1")
	List<InjectionTime> findByCode(String code);

	@Query("select o from InjectionTime o where o.name = ?1")
	List<InjectionTime> findByName(String name);

}
