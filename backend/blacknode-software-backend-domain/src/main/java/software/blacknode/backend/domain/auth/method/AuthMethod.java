package software.blacknode.backend.domain.auth.method;

import software.blacknode.backend.domain.auth.method.meta.AuthMethodMeta;
import software.blacknode.backend.domain.auth.method.type.AuthMethodType;
import software.blacknode.backend.domain.exception.BlacknodeException;

public interface AuthMethod {

	boolean authenticate(AuthMethodMeta meta);
	
	AuthMethodType getType();
	
	default void throwUnsupportedAuthMeta(AuthMethodMeta meta) {
		throw new BlacknodeException("Unsupported AuthMethodMeta type: " + 
			(meta != null ? meta.getClass().getName() : "null")
		);
	}
	
}
