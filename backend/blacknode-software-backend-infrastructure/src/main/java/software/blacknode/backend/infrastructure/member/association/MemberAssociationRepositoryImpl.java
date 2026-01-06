package software.blacknode.backend.infrastructure.member.association;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.MemberAssociation.Scope;
import software.blacknode.backend.domain.member.association.repository.MemberAssociationRepository;

@Repository
public class MemberAssociationRepositoryImpl implements MemberAssociationRepository {

	@Override
	public Optional<MemberAssociation> findById(HUID organizationId, HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<MemberAssociation> findAll(HUID organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberAssociation> findByMemberIds(HUID organizationId, List<HUID> memberIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberAssociation> findByMemberId(HUID organizationId, HUID memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberAssociation> findByMemberIdAndScope(HUID organizationId, HUID memberId, Scope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MemberAssociation> findByMemberIdAndScopeIdAndScope(HUID organizationId, HUID memberId,
			HUID scopeId, Scope scope) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(HUID organizationId, MemberAssociation association) {
		// TODO Auto-generated method stub
		
	}


}
