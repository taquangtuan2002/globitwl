package com.globits.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.ForestProductsList;

@Repository
public interface ForestProductsListRepository extends JpaRepository<ForestProductsList, Long>{

}
