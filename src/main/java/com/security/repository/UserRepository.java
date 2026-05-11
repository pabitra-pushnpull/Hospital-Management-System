package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.UserModel;
import com.security.model.type.AuthProviderType;

public interface UserRepository extends JpaRepository<UserModel, Long> {
	
	Optional<UserModel> findByusername(String username);
	
	Optional<UserModel> findByprovider_idAndprovider_type(String provider_id, AuthProviderType providerType);
	
	
	
	
}
