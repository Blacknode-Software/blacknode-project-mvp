package software.blacknode.backend.domain.auth.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.NonNull;
import software.blacknode.backend.domain.auth.meta.modify.AuthModificationMeta;
import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.impl.PasswordAuthMethod;

@Builder
public class PasswordChangeModificationMeta implements AuthModificationMeta {

	@NonNull
	private String password;
	
	public Optional<AuthMethod> getAuthMethod() {
		return Optional.of(new PasswordAuthMethod(password));
	}
}
