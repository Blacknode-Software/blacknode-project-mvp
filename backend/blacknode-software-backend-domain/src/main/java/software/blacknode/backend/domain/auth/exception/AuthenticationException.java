package software.blacknode.backend.domain.auth.exception;

import software.blacknode.backend.domain.exception.BlacknodeException;

public class AuthenticationException extends BlacknodeException {

	public AuthenticationException(String message) {
		super(message);
	}

}
