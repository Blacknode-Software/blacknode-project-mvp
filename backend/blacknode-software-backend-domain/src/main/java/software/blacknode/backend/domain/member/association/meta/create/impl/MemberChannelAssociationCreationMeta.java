package software.blacknode.backend.domain.member.association.meta.create.impl;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.create.MemberAssociationCreationMeta;

@Getter
@Builder
public class MemberChannelAssociationCreationMeta implements MemberAssociationCreationMeta {

	private HUID memberId;
	private HUID roleId;
	private HUID channelId;
	
	public MemberAssociation.Scope getScope() {
		return MemberAssociation.Scope.CHANNEL;
	}
	
	public HUID getEntityId() {
		return channelId;
	}
	
}
