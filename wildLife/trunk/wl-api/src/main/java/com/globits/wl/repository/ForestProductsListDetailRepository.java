package com.globits.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.ForestProductsListDetail;

@Repository
public interface ForestProductsListDetailRepository extends JpaRepository<ForestProductsListDetail, Long> {

}
