package software.blacknode.backend.infrastructure.account.entity.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@VersionedEntity
@ToString
public class AccountEntityMeta implements VersionableEntity {

	@NonNull private String firstName;
	@NonNull private String lastName;

}
