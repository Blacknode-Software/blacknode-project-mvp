package software.blacknode.backend.domain.auth.method.impl;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import software.blacknode.backend.domain.auth.exception.AuthenticationException;
import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.converter.deserializer.AuthMethodDeserializer;
import software.blacknode.backend.domain.auth.method.converter.model.AuthMethodSerializedModel;
import software.blacknode.backend.domain.auth.method.meta.AuthMethodMeta;
import software.blacknode.backend.domain.auth.method.type.AuthMethodType;
import software.blacknode.backend.domain.auth.method.type.impl.BaseAuthMethodType;
import software.blacknode.backend.domain.validate.exception.BlacknodeValidationException;

public class PasswordAuthMethod implements AuthMethod {
	
	private String passwordHash;
	private String salt;
	
	private PasswordAuthMethod(String passwordHash, String salt) {
		this.passwordHash = passwordHash;
		this.salt = salt;
	}
	
	public PasswordAuthMethod(String password) {
		password = password.trim();
		
		validatePassword(password);
		
		this.salt = generateSalt();
		this.passwordHash = hashPassword(password, salt);
	}

	@Override
	public boolean authenticate(AuthMethodMeta meta) {
		if(meta instanceof PasswordAuthMethodMeta _meta) {
			String password = _meta.getPassword();

			if(!checkPassword(password)) throw new AuthenticationException("Provided invalid password.");
			
			return true;
		} else throwUnsupportedAuthMeta(meta);
		
		return false;
	}
	
	private boolean checkPassword(String password) {
		return this.passwordHash.equals(hashPassword(password.trim(), this.salt));
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
	
	private void validatePassword(String password) {
		var messages = new ArrayList<String>();
		
		if(password == null || password.isBlank()) {
			messages.add("Password cannot be null or blank.");
		}
		
		if(password.length() < 8) {
			messages.add("Password must be at least 8 characters long.");
		}
		
		if(password.length() > 128) {
			messages.add("Password cannot exceed 128 characters.");
		}
		
		if(password.contains(" ")) {
			messages.add("Password cannot contain spaces.");
		}
		
		if(!password.matches(".*[A-Z].*")) {
			messages.add("Password must contain at least one uppercase letter.");
		}
		
		if(!password.matches(".*[a-z].*")) {
			messages.add("Password must contain at least one lowercase letter.");
		}
		
		if(!password.matches(".*\\d.*")) {
			messages.add("Password must contain at least one digit.");
		}
		
		if(!password.matches(".*[!@#$%^&*()].*")) {
			messages.add("Password must contain at least one special character (!@#$%^&*()).");
		}
		
		if(!messages.isEmpty()) {
			throw new BlacknodeValidationException(String.join("\n", messages));
		}
	}


	@Override
	public AuthMethodType getType() {
		return BaseAuthMethodType.PASSWORD;
	}
	
	@Getter
	@Builder
	public static class PasswordAuthMethodMeta implements AuthMethodMeta {
		
		@NonNull
		private String password;
		
	}
	
	public static class PasswordAuthMethodDeserializer implements AuthMethodDeserializer<PasswordAuthMethod> {	
		
		@Override
		public PasswordAuthMethod deserialize(AuthMethodSerializedModel model) {
			model.ensureAuthMethodType(BaseAuthMethodType.PASSWORD.getId());
			
			var properties = model.getProperties();
			
			@NonNull var passwordHash = properties.asText("passwordHash");
			@NonNull var salt = properties.asText("salt");
			
			return new PasswordAuthMethod(passwordHash, salt);
		}
		
	}
	
}
