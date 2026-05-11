package com.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.security.dto.PatientResponseDTO;
import com.security.model.PatientModel;
import com.security.repository.PatientRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	private final ModelMapper mapper;
	
	@Transactional
	public PatientResponseDTO getPatientById(Integer patient_id) {
		
		PatientModel existedPatient = patientRepository.findById(patient_id).orElseThrow(()-> new EntityNotFoundException("Id not found !"));     
		
		return mapper.map(existedPatient, PatientResponseDTO.class); 
		// .map method helps to converts the Entity to its DTO
		// It's advantage is that they don't expose our main Entity Directly.
		
	}
	
	public List<PatientResponseDTO> getAllPatients(Integer pageNumber, Integer pageSize) {
		
		return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
				.stream().map(patient -> mapper.map(patient, PatientResponseDTO.class))
				.collect(Collectors.toList());
		
//        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
//                .stream()
//                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
//                .collect(Collectors.toList());
    }
}
