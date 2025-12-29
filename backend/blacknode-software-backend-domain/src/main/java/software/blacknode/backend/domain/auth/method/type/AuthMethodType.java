package software.blacknode.backend.domain.auth.method.type;

import me.hinsinger.hinz.common.huid.HUID;

public interface AuthMethodType {
	
	HUID getId();
	String getName();
	String getDescription();
	
}
