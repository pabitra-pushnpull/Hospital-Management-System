package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.AppointmentModel;

public interface AppointmentRepository extends JpaRepository<AppointmentModel, Long>{

}
