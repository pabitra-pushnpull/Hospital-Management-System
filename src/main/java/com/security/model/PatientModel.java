package com.security.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.security.model.type.BloodGroupType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "patients",
	   uniqueConstraints = {@UniqueConstraint( name = "unique_patient_name_date_of_birth", 
											   columnNames = {"name","birth_date"}),},
	   indexes =  {@Index(name = "idx_patient_birth_date", columnList = "birth_date")})
@ToString
@Builder
public class PatientModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true)
	private Long patient_id;
	
	@OneToOne
	@MapsId
	private UserModel user;
	
	@Column(nullable = false, length = 40)
	private String name;
	
	@Column(nullable = false)
	private String gender;
	
	@Column(nullable = false, unique = true)
	@Email
	private String email;
	
	@Column(nullable = false)
	private LocalDate birth_date;
	
	@Enumerated(EnumType.STRING)
	@Column()
	private BloodGroupType blood_group;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime created_at;
	
//	Mappings
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "patient_insurance_id")
	private InsuranceModel insurance;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE,
			orphanRemoval = true, fetch = FetchType.EAGER)
	private List<AppointmentModel> appointment = new ArrayList<>();
	
	
}
