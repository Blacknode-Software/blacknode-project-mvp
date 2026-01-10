package software.blacknode.backend.domain.invite.meta;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.With;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;

@Getter
@With
@Builder
public class InviteMeta {

	private String email;
	
	private Timestamp expiresAt;
	
	private boolean revoked;
	
	private Optional<Timestamp> claimedAt;
	
	private Optional<HUID> claimedBy;
	
}
