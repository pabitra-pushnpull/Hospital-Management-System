package com.security.dto;

import java.time.LocalDate;

import com.security.model.type.BloodGroupType;

import lombok.Data;

@Data
public class PatientResponseDTO {

	private Long patientId;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
	
}
