package com.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.dto.DoctorResponseDTO;
import com.security.dto.OnBoardDoctorRequestDTO;
import com.security.model.DoctorModel;
import com.security.model.UserModel;
import com.security.model.type.RoleType;
import com.security.repository.DoctorRepository;
import com.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final ModelMapper modelMapper;
	
	@Autowired
	private final DoctorRepository doctorRepository;
	
	public List<DoctorResponseDTO> getAllDoctors() throws Exception {
		
		return doctorRepository.findAll()
				.stream()
				.map(doctor -> modelMapper.map(doctor, DoctorResponseDTO.class))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public DoctorResponseDTO onBoardNewDoctor(OnBoardDoctorRequestDTO requestDTO) throws Exception {
		
		Long userId = requestDTO.getUserId();
		
		UserModel userFound = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User Already Exists !"));
		
		if (doctorRepository.existsById(userId)) {
			
			throw new IllegalArgumentException("Doctor Already Exists !");
		}
		
		  DoctorModel doctor = DoctorModel.builder().
			 doctor_name(requestDTO.getName())
			 .specialization(requestDTO.getSpecialization())
			 .user(userFound)
			 .build();
						
		  userFound.getRole_type().add(RoleType.DOCTOR);
		  return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDTO.class);
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
