package com.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.security.dto.AppointmentResponseDTO;
import com.security.dto.CreateAppointmentRequestDTO;
import com.security.model.AppointmentModel;
import com.security.model.DoctorModel;
import com.security.model.PatientModel;
import com.security.repository.AppointmentRepository;
import com.security.repository.DoctorRepository;
import com.security.repository.PatientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

	@Autowired
	private final AppointmentRepository appointmentRepository;
	
	@Autowired
	private final PatientRepository patientRepository;
	
	@Autowired
	private final DoctorRepository doctorRepository;
	
	@Autowired
	private final ModelMapper modelMapper;
	
	@Transactional
    @Secured("ROLE_PATIENT")
	public AppointmentResponseDTO createAppointment(CreateAppointmentRequestDTO appointmentRequestDTO) {
		
		Long patientId = appointmentRequestDTO.getPatienId();
		Long doctorId = appointmentRequestDTO.getDoctorId();
		
		PatientModel existingPatient = patientRepository.findById(patientId)
								.orElseThrow(()-> new RuntimeException("Patient not found for"));
		
		DoctorModel existingDoctor = doctorRepository.findById(doctorId)
								.orElseThrow(()-> new RuntimeException("Doctor not found for id :"));
		
		AppointmentModel appointment = AppointmentModel.builder()
										.reason(appointmentRequestDTO.getReason())
										.appointment_time(appointmentRequestDTO.getAppointment_time())
										.build();
		
		appointment.setPatient(existingPatient);
		appointment.setDoctor(existingDoctor);
		
		existingPatient.getAppointment().add(appointment);
		
		appointment = appointmentRepository.save(appointment);
		
		return modelMapper.map(appointment, AppointmentResponseDTO.class);
		
	}
	
	public AppointmentModel reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
		
        AppointmentModel appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        DoctorModel existingDoctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(existingDoctor); // this will automatically call the update, because it is dirty

        existingDoctor.getAppointment().add(appointment); // just for bidirectional consistency

        return appointment;
    }
	
	public List<AppointmentResponseDTO> getAllAppointmentsOfDoctor(Long doctorId) {
        DoctorModel existingDoctor = doctorRepository.findById(doctorId).orElseThrow();

        return existingDoctor.getAppointment()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .collect(Collectors.toList());
    }
	
	
	
	
	
	
}
