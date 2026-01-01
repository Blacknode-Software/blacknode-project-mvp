package software.blacknode.backend.domain.session.context.holder;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.exception.SessionException;

@Component
@RequestScope
@Getter
@Setter(AccessLevel.PACKAGE)
public class SessionContextHolder {

	private SessionContext context = SessionContext.empty();
	
	public void ensureIsAuthenticated() {
		if (!isAuthenticated()) throw new SessionException("Session is not authenticated");
	}
	
	public HUID getAccountIdOrThrow() {
		return context.getAccountId().orElseThrow(() -> 
			new SessionException("No account ID in session context"));
	}
	
	public HUID getOrganizationIdOrThrow() {
		return context.getOrganizationId().orElseThrow(() -> 
			new SessionException("No organization ID in session context"));
	}
	
	public HUID getMemberIdOrThrow() {
		return context.getMemberId().orElseThrow(() -> 
			new SessionException("No member ID in session context"));
	}
	
	public HUID getSessionIdOrThrow() {
		return context.getSessionId().orElseThrow(() -> 
			new SessionException("No session ID in session context"));
	}
	
	public boolean isAuthenticated() {
		return context.getAccountId().isPresent();
	}
	
	public boolean isOrganizationScoped() {
		return context.getOrganizationId().isPresent() && context.getMemberId().isPresent();
	}
	
}
