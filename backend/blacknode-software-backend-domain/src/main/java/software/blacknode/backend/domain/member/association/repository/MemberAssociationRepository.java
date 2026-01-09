package software.blacknode.backend.domain.member.association.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;

public interface MemberAssociationRepository {

	public Optional<MemberAssociation> findById(HUID organizationId, HUID id);
	
	public List<MemberAssociation> findAll(HUID organizationId);
	
	public List<MemberAssociation> findBydRoleId(HUID organizationId, HUID roleId);
	
	public List<MemberAssociation> findByMemberIds(HUID organizationId, Set<HUID> memberIds);
	
	public List<MemberAssociation> findByMemberId(HUID organizationId, HUID memberId);
	
	public List<MemberAssociation> findByMemberIdAndScope(HUID organizationId, HUID memberId, MemberAssociation.Scope scope);
	
	public Optional<MemberAssociation> findByMemberIdAndScopeIdAndScope(HUID organizationId, HUID memberId, HUID scopeId, MemberAssociation.Scope scope);
	
	public void save(HUID organizationId, MemberAssociation association);
}
