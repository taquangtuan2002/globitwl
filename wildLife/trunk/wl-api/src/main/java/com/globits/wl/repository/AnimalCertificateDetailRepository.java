package com.globits.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.AnimalCertificateDetail;

@Repository
public interface AnimalCertificateDetailRepository extends JpaRepository<AnimalCertificateDetail, Long> {

}