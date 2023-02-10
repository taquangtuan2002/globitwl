package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.globits.wl.domain.Link;
import com.globits.wl.dto.LinkDto;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long>{
	
	@Query("select new com.globits.wl.dto.LinkDto(o) from Link o order by o.id")
	Page<LinkDto> getPageAll(Pageable pageable);
	
	@Query("select new com.globits.wl.dto.LinkDto(o) from Link o where o.id = ?1")
	LinkDto getLinkDtoById(Long id);
	
	@Query("select new com.globits.wl.dto.LinkDto(o) from Link o order by o.id")
	List<LinkDto> getAllDtos();
	
	@Query("select o from Link o where o.code = ?1")
	List<Link> findByCode(String code);
	
	@Query("select o from Link o where o.name = ?1")
	List<Link> findByName(String name);

}
