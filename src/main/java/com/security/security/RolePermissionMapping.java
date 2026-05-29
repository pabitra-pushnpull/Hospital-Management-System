package com.security.security;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.security.model.type.PermissionType;
import com.security.model.type.RoleType;

public class RolePermissionMapping {

	
	private static final Map<RoleType, Set<PermissionType>> map = Map.of(
			
			RoleType.PATIENT, Set.of(
					PermissionType.PATIENT_READ, 
					PermissionType.APPOINTMENT_READ, 
					PermissionType.APPOINTMENT_WRITE),
			
			RoleType.DOCTOR, Set.of(
					PermissionType.APPOINTMENT_DELETE, 
					PermissionType.APPOINTMENT_WRITE, 
					PermissionType.APPOINTMENT_READ, 
					PermissionType.PATIENT_READ),
			
			RoleType.ADMIN, Set.of(
					PermissionType.PATIENT_READ, 
					PermissionType.PATIENT_WRITE, 
					PermissionType.APPOINTMENT_READ, 
					PermissionType.APPOINTMENT_WRITE,
					PermissionType.APPOINTMENT_DELETE, 
					PermissionType.USER_MANAGE, 
					PermissionType.REPORT_VIEW)
	);
	
	
	private static Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType role) {
		
		return map.get(role).stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
				.collect(Collectors.toSet());
		
	}
	
}
