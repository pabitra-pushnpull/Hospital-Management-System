package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.DoctorModel;

public interface DoctorRepository extends JpaRepository<DoctorModel, Long>{

}
