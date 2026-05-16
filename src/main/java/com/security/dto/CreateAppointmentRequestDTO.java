package com.security.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateAppointmentRequestDTO {

	private Long patienId;
    private LocalDateTime appointmentTime;
    private String reason;
    private Long doctorId;
	
}
