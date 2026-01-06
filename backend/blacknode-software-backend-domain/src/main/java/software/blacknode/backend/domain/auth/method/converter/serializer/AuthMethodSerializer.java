package software.blacknode.backend.domain.auth.method.converter.serializer;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.converter.model.AuthMethodSerializedModel;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Component
@RequiredArgsConstructor
public class AuthMethodSerializer {

	private final ObjectMapper objectMapper;
	
	public AuthMethodSerializedModel serialize(AuthMethod method) {
		var mapper = objectMapper.copy().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
		
		var id = method.getType().getId();
		var properties = mapper.valueToTree(method);
		
		return new AuthMethodSerializedModel(id, properties);
	}
	
	public String serializeToString(AuthMethod method) {
		var serializedModel = serialize(method);
		
		try { return objectMapper.writeValueAsString(serializedModel); } 
		catch (Exception e) { throw new BlacknodeException("Failed to serialize AuthMethod to string", e); }
	}
	
}
