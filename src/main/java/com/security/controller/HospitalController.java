package com.security.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.DoctorResponseDTO;
import com.security.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class HospitalController {

	private final DoctorService doctorService;
	
	@GetMapping("/doctors")
	public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors() throws Exception {
		
		return ResponseEntity.ok(doctorService.getAllDoctors());
	}
	
}
