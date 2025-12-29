package software.blacknode.backend.domain.auth.method.converter.deserializer;

import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.converter.model.AuthMethodSerializedModel;

public interface AuthMethodDeserializer<M extends AuthMethod> {
	
	public M deserialize(AuthMethodSerializedModel model);

}
