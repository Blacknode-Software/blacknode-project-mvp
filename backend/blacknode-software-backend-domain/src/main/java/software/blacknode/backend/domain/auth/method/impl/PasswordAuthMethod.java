package software.blacknode.backend.domain.auth.method.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.auth.exception.AuthenticationException;
import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.meta.AuthMethodMeta;
import software.blacknode.backend.domain.auth.method.type.AuthMethodType;

public class PasswordAuthMethod implements AuthMethod {

	private String passwordHash;
	private String salt;
	
	public PasswordAuthMethod(String password) {
		this.salt = generateSalt();
		this.passwordHash = hashPassword(password, salt);
	}

	@Override
	public boolean authenticate(AuthMethodMeta meta) {
		if(meta instanceof PasswordAuthMeta _meta) {
			String password = _meta.getPassword();

			if(!checkPassword(password)) throw new AuthenticationException("Provided invalid password.");
			
			return true;
		} else throwUnsupportedAuthMeta(meta);
		
		return false;
	}
	
	private boolean checkPassword(String password) {
		return this.passwordHash.equals(hashPassword(password, this.salt));
	}
	
	private String hashPassword(String password, String salt) {
		// Implement a proper hashing mechanism here.
		// This is just a placeholder for demonstration.
		return password + salt;
	}
	
	private String generateSalt() {
		// Implement a proper salt generation mechanism here.
		// This is just a placeholder for demonstration.
		return "random_salt";
	}

	@Override
	public AuthMethodType getType() {
		return AuthMethodType.of(
			HUID.fromString("8bf20c11-fe0d-49b8-a6ca-000000000001"),
			"Password Authentication",
			"Authentication method using a password."
		);
	}
	
	@Getter
	@Builder
	public static class PasswordAuthMeta implements AuthMethodMeta {
		
		@NonNull
		@NotBlank
		@Size(min = 4, max = 128)
		private String password;
		
	}
	
}
