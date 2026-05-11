package com.security.dto;

import java.time.LocalDate;

import com.security.model.type.BloodGroupType;

import lombok.Data;

@Data
public class PatientResponseDTO {

	private Long patient_id;
    private String name;
    private String gender;
    private LocalDate birth_date;
    private BloodGroupType blood_group;
	
}
