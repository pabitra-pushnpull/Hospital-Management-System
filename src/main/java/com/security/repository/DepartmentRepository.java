package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.DepartmentModel;

public interface DepartmentRepository extends JpaRepository<DepartmentModel, Long>{

}
