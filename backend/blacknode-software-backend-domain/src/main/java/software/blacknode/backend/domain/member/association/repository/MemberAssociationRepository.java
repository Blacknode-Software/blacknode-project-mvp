package software.blacknode.backend.domain.member.association.repository;

import java.util.List;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;

public interface MemberAssociationRepository {

	public List<MemberAssociation> getByMemberId(HUID memberId);
	
	public MemberAssociation getByMemberIdAndScopeId(HUID memberId, HUID scopeId);
	
	public void save(MemberAssociation association);
}
