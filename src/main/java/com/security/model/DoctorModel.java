package com.security.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctor")
@Builder
public class DoctorModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long doctor_id;
	
	@OneToOne
	@MapsId
	private UserModel user;

    @Column(nullable = false, length = 100)
    private String doctor_name;

    @Column(length = 100)
    private String specialization;

    @Email
    @Column(nullable = false, unique = true)
    private String email;
    
//    Mappings
    @ManyToMany(mappedBy = "doctors")
    private Set<DepartmentModel> department = new HashSet<>();
    
    @OneToMany(mappedBy = "doctor")
    private List<AppointmentModel> appointment = new ArrayList<>();
	
}
