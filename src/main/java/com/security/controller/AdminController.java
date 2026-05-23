package com.security.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.DoctorResponseDTO;
import com.security.dto.OnBoardDoctorRequestDTO;
import com.security.dto.PatientResponseDTO;
import com.security.service.DoctorService;
import com.security.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DoctorService doctorService;

	private final PatientService patientService;

    AdminController(DoctorService doctorService) {
        this.doctorService = doctorService;
		this.patientService = null;
    }
	
	@GetMapping("/patients")
	public ResponseEntity<List<PatientResponseDTO>> getAllPatients(
			@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
			@RequestParam(value = "size", defaultValue = "10") Integer pageSize) throws Exception{
	
		return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
		
	}
	
	
	@PostMapping("/onBoardNewDoctor")
	public ResponseEntity<DoctorResponseDTO> onBoardNewDoctor(@RequestBody OnBoardDoctorRequestDTO onBoardDoctorRequestDTO)
		throws Exception {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onBoardDoctorRequestDTO));
		
	}
	
	
	
	
	
	
	
}
