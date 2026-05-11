package com.security.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.model.type.AuthProviderType;
import com.security.model.type.RoleType;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users",
		indexes = {@Index(name = "idx_provider_id_provider_type", columnList = "provider_id, provider_type")})
@Builder
public class UserModel implements UserDetails{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id ;

    @Column(unique = true)
    private String username;

    @Column()
    private String password;

    @Column()
    private String provider_id;

    @Enumerated(EnumType.STRING)
    private AuthProviderType provider_type;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<RoleType> role_type = new HashSet<>();
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role_type.stream().map(role -> new SimpleGrantedAuthority("Role_" + role.toString()))
				.collect(Collectors.toSet());
		
		
//		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//		
//		role_type.forEach(role -> {
//			Set<SimpleGrantedAuthority> permission = RolePermissionMapping.getAuthoritiesForRole(role);
//			authorities.addAll(permission);
//			authorities.add(new SimpleGrantedAuthority("Role" + role.toString()));
//		});
//		return authorities;
		
	}
	
	
	
    //DTO
    //Auth2 Security
    
}
