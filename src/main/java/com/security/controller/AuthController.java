package com.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.LoginRequestDTO;
import com.security.dto.LoginResponseDTO;
import com.security.dto.SignUpRequestDTO;
import com.security.dto.SignUpResponseDTO;
import com.security.security.AuthService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
	
	@PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signup(@RequestBody SignUpRequestDTO signupRequestDto) {
        return ResponseEntity.ok(authService.signUp(signupRequestDto));
    }
	
}
