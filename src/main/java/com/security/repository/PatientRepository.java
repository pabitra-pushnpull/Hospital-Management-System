package com.security.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.model.PatientModel;
import com.security.model.type.BloodGroupType;

import jakarta.transaction.Transactional;

public interface PatientRepository extends JpaRepository<PatientModel, Long> {
	
	PatientModel findByname(String name);
	
	List<PatientModel> findBybirth_dateOremail(LocalDate birthDate, String email);
	
	List<PatientModel> findBybirth_dateBetween(LocalDate startDate, LocalDate endDate);
	
	List<PatientModel> findBynameContainingOrderByIdDesc(String query);
	
	@Query("SELECT p FROM patients p WHERE p.blood_group = ?1")
	List<PatientModel> findByBloodGroup(@Param("blood_group")BloodGroupType bloodGroup);
	
	@Query("SELECT p FROM patients p WHERE p.birth_date > :birth_date")
	List<PatientModel> findByBornAfterDate(@Param("birth_date") LocalDate birthDate);
	
//	@Query("") -- [incomplete ???]
//	List<BloodGroupCountResponseDTO> countEachBloodGroupType();
	
	@Query(value = "select * from patient", nativeQuery = true)
    Page<PatientModel> findAllPatients(Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDATE patients p SET p.name = :name WHERE p.id = :id")
	int updateNameWithId(@Param("name") String name, @Param("id")Long id);

	@Query("SELECT p from patients p LEFT JOIN FETCH p.appointments")
	List<PatientModel> findAllPatientsWithAppointments();
	
	
	
	
	
	
	
	
	
	
}
