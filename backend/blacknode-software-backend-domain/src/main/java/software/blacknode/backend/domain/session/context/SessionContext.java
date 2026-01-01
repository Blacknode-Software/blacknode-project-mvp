package software.blacknode.backend.domain.session.context;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;

@Getter
@Builder
public class SessionContext {
	public static SessionContext empty() {
		return SessionContext.builder()
			.build();
	}
	
	private final Optional<HUID> sessionId;
	
	private final Optional<HUID> accountId;
	
	private final Optional<HUID> memberId;
	
	private final Optional<HUID> organizationId;
}
