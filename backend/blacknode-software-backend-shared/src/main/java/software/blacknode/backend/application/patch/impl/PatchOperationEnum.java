package software.blacknode.backend.application.patch.impl;

import software.blacknode.backend.application.patch.PatchOperation;
import software.blacknode.backend.domain.exception.BlacknodeException;

public interface PatchOperationEnum extends PatchOperation {
	
	@Override
	public default String getName() {
		if(this instanceof Enum<?>) {
			return ((Enum<?>) this).name();
		}
		
		throw new BlacknodeException("PatchOperationEnum can only be implemented by Enums");
	}

}
