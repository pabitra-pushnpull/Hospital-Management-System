package com.security.dto;

import lombok.Data;

@Data
public class OnBoardDoctorRequestDTO {

	private Long userId;
    private String name;
    private String specialization;
	
}
