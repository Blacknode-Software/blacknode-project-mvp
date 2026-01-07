package software.blacknode.backend.domain.member.association.meta.create.impl;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.create.MemberAssociationCreationMeta;

@Getter
@Builder
public class MemberProjectAssociationCreationMeta implements MemberAssociationCreationMeta {

	private HUID memberId;
	private HUID roleId;
	private HUID projectId;
	
	public MemberAssociation.Scope getScope() {
		return MemberAssociation.Scope.PROJECT;
	}
	
	public HUID getEntityId() {
		return projectId;
	}
	
}
