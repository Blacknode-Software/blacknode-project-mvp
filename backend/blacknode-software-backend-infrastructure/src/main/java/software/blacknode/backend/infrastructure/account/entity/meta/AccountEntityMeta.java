package software.blacknode.backend.infrastructure.account.entity.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@VersionedEntity
@ToString
public class AccountEntityMeta implements VersionableEntity {

	@NonNull private String firstName;
	@NonNull private String lastName;

}
