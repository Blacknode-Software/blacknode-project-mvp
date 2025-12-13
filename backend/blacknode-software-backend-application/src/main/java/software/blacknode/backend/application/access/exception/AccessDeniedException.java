package software.blacknode.backend.application.access.exception;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.AccessControlService.AccessLevel;
import software.blacknode.backend.domain.exception.BlacknodeException;

public class AccessDeniedException extends BlacknodeException {

	public AccessDeniedException(String message) {
		super(message);
	}
	
	public AccessDeniedException(HUID memberId, HUID resourceId, AccessLevel level, String type) {
		super("Access denied for member " + memberId + " on resource [" + type + "] "  + resourceId + " with access level " + level);
	}
}
