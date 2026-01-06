package software.blacknode.backend.infrastructure.member.entity.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@NoArgsConstructor
@VersionedEntity
@ToString
public class MemberEntityMeta implements VersionableEntity {
	
}
