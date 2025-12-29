package software.blacknode.backend.domain.auth.method.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

public class PasswordAuthMethod implements AuthMethod {
	
	private String passwordHash;
	private String salt;
	
	private PasswordAuthMethod(String passwordHash, String salt) {
		this.passwordHash = passwordHash;
		this.salt = salt;
	}
	
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
		return BaseAuthMethodType.PASSWORD;
	}
	
	@Getter
	@Builder
	public static class PasswordAuthMeta implements AuthMethodMeta {
		
		@NonNull
		@NotBlank
		@Size(min = 4, max = 128)
		private String password;
		
	}
	
	public static class PasswordAuthMethodDeserializer implements AuthMethodDeserializer<PasswordAuthMethod> {	
		
		@Override
		public PasswordAuthMethod deserialize(AuthMethodSerializedModel model) {
			model.ensureAuthMethodType(BaseAuthMethodType.PASSWORD.getId());
			
			var properties = model.getProperties();
			
			String passwordHash = properties.asText("passwordHash");
			String salt = properties.asText("salt");
			
			return new PasswordAuthMethod(passwordHash, salt);
		}
		
	}
	
}
