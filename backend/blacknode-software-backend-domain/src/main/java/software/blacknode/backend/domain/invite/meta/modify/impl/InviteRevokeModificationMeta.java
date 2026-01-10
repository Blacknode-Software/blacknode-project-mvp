package software.blacknode.backend.domain.invite.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.invite.meta.modify.InviteModificationMeta;

@Getter
@Builder
public class InviteRevokeModificationMeta implements InviteModificationMeta {

	private final boolean revoked;
	
	@Override
	public Optional<Boolean> isRevoked() {
		return Optional.of(revoked);
	}
	
}
