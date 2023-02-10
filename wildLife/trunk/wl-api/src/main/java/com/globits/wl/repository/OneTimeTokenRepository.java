package com.globits.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.OneTimeToken;

@Repository
public interface OneTimeTokenRepository extends JpaRepository<OneTimeToken, Long>{

	@Query("select ott from OneTimeToken ott where ott.username = ?1")
	OneTimeToken findByUsername(String username);

	@Query("select ott from OneTimeToken ott where ott.token = ?1 AND ott.token IS NOT NULL ")
	OneTimeToken findByToken(String username);
	
}
