package com.globits.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FmsAdministrativeUnitSystemMessage;

@Repository
public interface FmsAdministrativeUnitSystemMessageRepository extends JpaRepository<FmsAdministrativeUnitSystemMessage, Long>{

}
