package com.security.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequestDTO {

	private Long patienId;
    private LocalDateTime appointment_time;
    private String reason;
    private Long doctorId;
	
}
