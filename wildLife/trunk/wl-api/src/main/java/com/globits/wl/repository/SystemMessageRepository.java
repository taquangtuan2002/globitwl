package com.globits.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.SystemMessage;

@Repository
public interface SystemMessageRepository extends JpaRepository<SystemMessage, Long>{

}
