package software.blacknode.backend.domain.member.association.meta.create;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.member.association.MemberAssociation;

public interface MemberAssociationCreationMeta extends CreationMeta {

	MemberAssociation.Scope getScope();
	
	HUID getEntityId();
	
	HUID getMemberId();
	
	HUID getRoleId();
	
}
