package software.blacknode.backend.domain.auth.method.converter.serializer;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.converter.model.AuthMethodSerializedModel;

@Component
@RequiredArgsConstructor
public class AuthMethodSerializer<M extends AuthMethod> {

	private final ObjectMapper objectMapper;
	
	public AuthMethodSerializedModel serialize(M method) {
		var id = method.getType().getId();
		var properties = objectMapper.valueToTree(method);
		
		return new AuthMethodSerializedModel(id, properties);
	}
	
}
