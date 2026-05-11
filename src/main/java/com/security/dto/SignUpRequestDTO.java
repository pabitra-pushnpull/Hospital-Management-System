package com.security.dto;

import java.util.HashSet;
import java.util.Set;

import com.security.model.type.RoleType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

	private String username;
    private String name;
    private String password;
    private Set<RoleType> roles = new HashSet<>();
	
}
