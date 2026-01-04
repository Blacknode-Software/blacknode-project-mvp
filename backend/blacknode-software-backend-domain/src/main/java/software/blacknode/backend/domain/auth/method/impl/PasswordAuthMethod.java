package software.blacknode.backend.domain.auth.method.impl;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
	
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder(12);
	
	private static final int PASSWORD_MIN_LENGTH = 8;
	private static final int PASSWORD_MAX_LENGTH = 128;
	
	private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
	private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
	private static final Pattern DIGIT = Pattern.compile("\\d");
	private static final Pattern SPACE = Pattern.compile("\\s");
	private static final Pattern SPECIAL = Pattern.compile("[!@#$%^&*()]");
	
	public static PasswordAuthMethod withPassword(@NonNull String password) {
		validatePassword(password);
		
		var hash = ENCODER.encode(password);
		
		return new PasswordAuthMethod(hash);
	}
	
	private String hash;
	
	private PasswordAuthMethod(@NonNull String hash) {
		this.hash = hash;
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
	
	private boolean checkPassword(@NonNull String password) {
		return ENCODER.matches(password, hash);
	}
	
	private static void validatePassword(@NonNull String password) {
		var messages = new ArrayList<String>();
		
		if(password.isBlank()) {
			messages.add("Password cannot be null or blank.");
		}
		
		if(password.length() < PASSWORD_MIN_LENGTH) {
			messages.add("Password must be at least 8 characters long.");
		}
		
		if(password.length() > PASSWORD_MAX_LENGTH) {
			messages.add("Password cannot exceed 128 characters.");
		}
		
		if (SPACE.matcher(password).find()) {
		    messages.add("Password cannot contain spaces.");
		}

		if (!UPPERCASE.matcher(password).find()) {
		    messages.add("Password must contain at least one uppercase letter.");
		}

		if (!LOWERCASE.matcher(password).find()) {
		    messages.add("Password must contain at least one lowercase letter.");
		}

		if (!DIGIT.matcher(password).find()) {
		    messages.add("Password must contain at least one digit.");
		}

		if (!SPECIAL.matcher(password).find()) {
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
		public PasswordAuthMethod deserialize(@NonNull AuthMethodSerializedModel model) {
			model.ensureAuthMethodType(BaseAuthMethodType.PASSWORD.getId());
			
			var properties = model.getProperties();
			
			@NonNull var hash = properties.asText("hash");
			
			return new PasswordAuthMethod(hash);
		}
		
	}
	
}
