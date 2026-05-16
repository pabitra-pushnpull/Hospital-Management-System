package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.AppointmentResponseDTO;
import com.security.dto.CreateAppointmentRequestDTO;
import com.security.dto.PatientResponseDTO;
import com.security.service.AppointmentService;
import com.security.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

	@Autowired
	private final PatientService patientService;
	    
	@Autowired
	private final AppointmentService appointmentService;

	    @PostMapping("/appointments")
	    public ResponseEntity<AppointmentResponseDTO> createNewAppointment(@RequestBody CreateAppointmentRequestDTO createAppointmentRequestDto) {
	        
	    	return ResponseEntity
	        		.status(HttpStatus.CREATED)
	        		.body(appointmentService.createAppointment(createAppointmentRequestDto));
	    }

	    @GetMapping("/profile")
	    private ResponseEntity<PatientResponseDTO> getPatientProfile() {
	        Long patientId = 4L;
	        return ResponseEntity.ok(patientService.getPatientById(patientId));
	    }
	
}
