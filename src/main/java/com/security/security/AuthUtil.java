package com.security.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.security.model.UserModel;
import com.security.model.type.AuthProviderType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthUtil {

	@Value("${jwt.secretKey}")
	private String jwtSecretKey;
	
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	
	public String generateAccessToken(UserModel userModel) {
		
		
		return Jwts.builder()
				.subject(userModel.getUsername())
				.claim("userId", userModel.getId().toString())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000*60*10))
				.signWith(getSecretKey())
				.compact();
	}
	
	
	
	public String getUsernameFromToken(String token) {
		
		Claims claims = Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		
		return claims.getSubject();
	}

	
	public AuthProviderType getProviderTypeFromRegistrationId(String registrationId) {
		
		return switch (registrationId.toLowerCase()) {
		case "google" -> AuthProviderType.Google;
		case "github" -> AuthProviderType.GitHub;
		case "facebook" -> AuthProviderType.Facebook;
		
		default -> throw new IllegalArgumentException("Unsupported OAuth2 provider : " + registrationId); };
		
	}
	
	
	
	public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId) {
		
		String providerId = switch (registrationId.toLowerCase()) {
		case "google" -> oAuth2User.getAttribute("sub");
		case "github" -> oAuth2User.getAttribute("id").toString();
		
		default -> {log.error("Unsupported OAuth2 provider : {}", registrationId);
					throw new IllegalArgumentException("Unsupported OAuth2 provider : " + registrationId);
					}
		};
		
		if (providerId == null || providerId.isBlank()) {
			log.error("Unable to determine providerId for provider : {}", registrationId);
			throw new IllegalArgumentException("Unable to determine providerId for OAuth2 Login");
		}
		
		return providerId;
		
	}
	
	public String determineUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId) {
		
		String email = oAuth2User.getAttribute("email");
		
		if (email != null && !email.isBlank()) {
			return email;
		}
		
		return switch (registrationId.toLowerCase()) {
		case "google" -> oAuth2User.getAttribute("sub");
		case "github" -> oAuth2User.getAttribute("login");
		
		default -> providerId;
		};
	}
	
	
	
	
	
	
	
}
