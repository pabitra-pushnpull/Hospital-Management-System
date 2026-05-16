package com.security.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentResponseDTO {

	private Long patientId;
    private LocalDateTime appointmentTime;
    private String reason;
    private DoctorResponseDTO doctor;
	
}
