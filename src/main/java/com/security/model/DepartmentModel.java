package com.security.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")
public class DepartmentModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;
    
//    Mappings
    @ManyToMany()
    @JoinTable(name = "my_dept_doctors",
    				joinColumns = @JoinColumn(name ="dept_id"),
    				inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private Set<DoctorModel> doctors = new HashSet<>();

	
}
