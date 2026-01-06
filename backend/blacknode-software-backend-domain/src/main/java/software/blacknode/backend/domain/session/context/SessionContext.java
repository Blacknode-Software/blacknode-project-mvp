package software.blacknode.backend.domain.session.context;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import me.hinsinger.hinz.common.huid.HUID;

@Getter
@With
@Builder
public class SessionContext {
	public static SessionContext empty() {
		return SessionContext.builder().build();
	}
	
	@Builder.Default
	private final Optional<HUID> sessionId = Optional.empty();
	
	@Builder.Default
	private final Optional<HUID> accountId = Optional.empty();
	
	@Builder.Default
	private final Optional<HUID> memberId = Optional.empty();
	
	@Builder.Default
	private final Optional<HUID> organizationId = Optional.empty();
}
