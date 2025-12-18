package software.blacknode.backend.domain.context;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.hinsinger.hinz.common.huid.HUID;

@Getter
@Setter
@Builder
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionContext {
	private HUID sessionId;
	private HUID accountId;
	private HUID memberId;
	private HUID organizationId;
}
