package software.blacknode.backend.domain.auth.meta.create;

import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

public interface AuthCreationMeta extends CreationMeta{

	public AuthMethod getAuthMethod();
	
}
