package com.security.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.security.dto.LoginRequestDTO;
import com.security.dto.LoginResponseDTO;
import com.security.dto.SignUpRequestDTO;
import com.security.dto.SignUpResponseDTO;
import com.security.model.PatientModel;
import com.security.model.UserModel;
import com.security.model.type.AuthProviderType;
import com.security.repository.PatientRepository;
import com.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
//	private final AuthUti
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PatientRepository patientRepository;

	public LoginResponseDTO login(LoginRequestDTO loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(	new UsernamePasswordAuthenticationToken(
																		loginRequest.getUsername(), 
																		loginRequest.getPassword()));
		
		User user = (User) authentication.getPrincipal();
//		
//		
		return new LoginResponseDTO(null, null);
	}
	
	
	public UserModel signUpInternal(SignUpRequestDTO signUpRequest, AuthProviderType authProviderType, String providerId) {
		
		UserModel user = userRepository.findByusername(signUpRequest.getUsername()).orElse(null);
		
		if (user != null) throw new IllegalArgumentException("User already exists.");
		
		user = UserModel.builder()
				.username(signUpRequest.getUsername())
				.provider_id(providerId)
				.provider_type(authProviderType)
				.role_type(signUpRequest.getRoles())
				.build();
		
		if (authProviderType == AuthProviderType.Email) {
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		}
			
		user = userRepository.save(user);
		
		PatientModel patient = PatientModel.builder()
											.name(signUpRequest.getName())
											.email(signUpRequest.getUsername())
											.user(user)
											.build();
		
		patientRepository.save(patient);
		
		return user;
		
	}
	
	public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequest) {
		
		UserModel user = signUpInternal(signUpRequest, AuthProviderType.Email, null);
		return new SignUpResponseDTO(user.getId(), user.getUsername());
		
	}
	
	
	public ResponseEntity<LoginResponseDTO> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
		
//		AuthProviderType authProviderType = authu
//		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
