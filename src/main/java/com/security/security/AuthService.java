package com.security.security;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import com.security.model.type.RoleType;
import com.security.repository.PatientRepository;
import com.security.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final AuthUtil authUtil;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PatientRepository patientRepository;

	public LoginResponseDTO login(LoginRequestDTO loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(	new UsernamePasswordAuthenticationToken(
																		loginRequest.getUsername(), 
																		loginRequest.getPassword()));
		
		UserModel user = (UserModel) authentication.getPrincipal();
		
		String token = authUtil.generateAccessToken(user);
		
		return new LoginResponseDTO(token, user.getId());
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
	
	
	@Transactional
	public ResponseEntity<LoginResponseDTO> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
		
		AuthProviderType authProviderType = authUtil.getProviderTypeFromRegistrationId(registrationId);
		String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);
		
		UserModel user = userRepository.findByprovider_idAndprovider_type(providerId, authProviderType)
										.orElseThrow();
		String email = oAuth2User.getAttribute("email");
		String name = oAuth2User.getAttribute("name");
		
		UserModel emailUser = userRepository.findByusername(email).orElse(null);
		
		if (user == null && emailUser == null) {
			
			String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
			user = signUpInternal(
					new SignUpRequestDTO(username, null, name, Set.of(RoleType.PATIENT))
						, authProviderType, providerId);
		}else if (user != null) {
			
			if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
				user.setUsername(email);
				userRepository.save(user);
			}
			
		}else {
			
			throw new BadCredentialsException("This email is already registered with provider : " + emailUser.getProvider_type());
			
		}
		
		LoginResponseDTO  loginResponse = new LoginResponseDTO(authUtil.generateAccessToken(user), user.getId());
		return ResponseEntity.ok(loginResponse);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
