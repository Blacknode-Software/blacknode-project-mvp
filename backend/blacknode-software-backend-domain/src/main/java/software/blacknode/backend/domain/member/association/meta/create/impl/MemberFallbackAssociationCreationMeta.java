package software.blacknode.backend.domain.member.association.meta.create.impl;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation.Scope;
import software.blacknode.backend.domain.member.association.meta.create.MemberAssociationCreationMeta;

@Getter
@Builder
public class MemberFallbackAssociationCreationMeta implements MemberAssociationCreationMeta {
	
	private final HUID memberId;
	private final HUID roleId;
	private final HUID entityId;
	private final Scope scope;
	
}
