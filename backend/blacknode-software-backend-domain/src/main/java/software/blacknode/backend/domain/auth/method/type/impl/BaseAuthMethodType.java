package software.blacknode.backend.domain.auth.method.type.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.auth.method.converter.deserializer.AuthMethodDeserializer;
import software.blacknode.backend.domain.auth.method.impl.PasswordAuthMethod.PasswordAuthMethodDeserializer;
import software.blacknode.backend.domain.auth.method.type.AuthMethodType;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Getter
@AllArgsConstructor
public enum BaseAuthMethodType implements AuthMethodType {
	PASSWORD(
		HUID.fromString("8bf20c11-fe0d-49b8-a6ca-000000000001"),
		"Password Authentication",
		"Authentication method using a password.",
		new PasswordAuthMethodDeserializer()
	)
	;
	
	private final HUID id;
	private final String name;
	private final String description;
	private final AuthMethodDeserializer<?> deserializer;
	
	public static BaseAuthMethodType fromId(HUID id) {
		for(var method : values()) {
			if(method.getId().equals(id)) {
				return method;
			}
		}
		
		throw new BlacknodeException("Unknown AuthMethodType id: " + id);
	}

}
