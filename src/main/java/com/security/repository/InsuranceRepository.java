package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.InsuranceModel;

public interface InsuranceRepository extends JpaRepository<InsuranceModel, Long>{

}
