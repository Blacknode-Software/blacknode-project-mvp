package software.blacknode.backend.domain.session.context.holder;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.Setter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.session.context.SessionContext;
import software.blacknode.backend.domain.session.exception.SessionException;

@Component
@RequestScope
@Getter
public class SessionContextHolder {

	private SessionContext context = SessionContext.empty();
	
	public void initialize(SessionContext context) {
		this.context = context;
	}
	
	public HUID getAccountIdOrThrow() {
		ensureAuthenticated();
		
		return context.getAccountId().orElseThrow(() -> 
			new SessionException("No account ID in session context"));
	}
	
	public HUID getOrganizationIdOrThrow() {
		ensureOrganizationScoped();
		
		return context.getOrganizationId().orElseThrow(() -> 
			new SessionException("No organization ID in session context"));
	}
	
	public HUID getMemberIdOrThrow() {
		ensureOrganizationScoped();
		
		return context.getMemberId().orElseThrow(() -> 
			new SessionException("No member ID in session context"));
	}
	
	public HUID getSessionIdOrThrow() {
		return context.getSessionId().orElseThrow(() -> 
			new SessionException("No session ID in session context"));
	}
	
	public void ensureAuthenticated() {
		if (!isAuthenticated()) {
			throw new SessionException("Session is not authenticated");
		}
	}
	
	public void ensureOrganizationScoped() {
		ensureAuthenticated();
		
		if (!isOrganizationScoped()) {
			throw new SessionException("Session is not organization scoped");
		}
	}
	
	public boolean isAuthenticated() {
		return context.getAccountId().isPresent();
	}
	
	public boolean isOrganizationScoped() {
		return context.getOrganizationId().isPresent() && context.getMemberId().isPresent();
	}
	
}
