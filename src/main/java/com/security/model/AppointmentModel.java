package com.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "appointment")
public class AppointmentModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointment_time;

    @Column(nullable = false, length = 500)
    private String reason;
    
//    Mappings
    
    @ManyToOne()
    @ToString.Exclude()
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientModel patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude()
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorModel doctor;
    
    /*
	DocSer
	AppSer
	InsSer
	PatSer
	
	HosCon
	PatCon
	AdminCon
	AuthCon
	DocCon
	*/
    
    
    
    
}
