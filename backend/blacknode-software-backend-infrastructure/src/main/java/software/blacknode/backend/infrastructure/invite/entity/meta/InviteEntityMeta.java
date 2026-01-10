package software.blacknode.backend.infrastructure.invite.entity.meta;

import java.time.Instant;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@VersionedEntity
@ToString
public class InviteEntityMeta implements VersionableEntity {

	@NotNull private String email;
	
	@NotNull private Timestamp expiresAt;
	
	private boolean revoked;

	private boolean claimed;
	
	@NonNull private Instant claimedAt;
	
	@Nullable private HUID claimedByMemberId;
	
}
