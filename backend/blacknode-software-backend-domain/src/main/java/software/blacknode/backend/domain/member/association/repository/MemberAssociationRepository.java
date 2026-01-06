package software.blacknode.backend.domain.member.association.repository;

import java.util.List;
import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.MemberAssociationMeta;

public interface MemberAssociationRepository {

	public Optional<MemberAssociation> findById(HUID id);
	
	public List<MemberAssociation> findAll();
	
	public List<MemberAssociation> findByMemberIds(List<HUID> memberIds);
	
	public List<MemberAssociation> findByMemberId(HUID memberId);
	
	public List<MemberAssociation> findByMemberIdAndScope(HUID memberId, MemberAssociation.Scope scope);
	
	public Optional<MemberAssociation> findByMemberIdAndScopeIdAndScope(HUID memberId, HUID scopeId, MemberAssociation.Scope scope);
	
	public void save(MemberAssociation association);
}
