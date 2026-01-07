package software.blacknode.backend.infrastructure.member.association.entity.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;

@Builder
@Getter
@AllArgsConstructor
@VersionedEntity
@ToString
public class MemberAssociationEntityMeta implements VersionableEntity {

}
