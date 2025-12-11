package software.blacknode.backend.domain.member.association.repository;

import java.util.List;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;

public interface MemberAssociationRepository {

	public List<MemberAssociation> findAll();
	
	public List<MemberAssociation> findByMemberIds(List<HUID> memberIds);
	
	public List<MemberAssociation> findByMemberId(HUID memberId);
	
	public MemberAssociation findByMemberIdAndScopeId(HUID memberId, HUID scopeId);
	
	public void save(MemberAssociation association);
}
