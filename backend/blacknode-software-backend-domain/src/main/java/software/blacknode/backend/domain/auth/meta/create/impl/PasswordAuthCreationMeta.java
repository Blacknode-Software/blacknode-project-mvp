package software.blacknode.backend.domain.auth.meta.create.impl;

import lombok.Builder;
import software.blacknode.backend.domain.auth.meta.create.AuthCreationMeta;
import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.impl.PasswordAuthMethod;

@Builder
public class PasswordAuthCreationMeta implements AuthCreationMeta {

	private String password;
	
	public AuthMethod getAuthMethod() {
		return PasswordAuthMethod.withPassword(password);
	}

}
