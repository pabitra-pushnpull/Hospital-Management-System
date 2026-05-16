package com.security.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.AppointmentResponseDTO;
import com.security.model.UserModel;
import com.security.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private final AppointmentService appointmentService;
	
	@GetMapping("/appointments")
	public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointmentsOfDoctor() {
		
		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(user.getId()));
		
		
	}
	
}
