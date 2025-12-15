package software.blacknode.backend.infrastructure.member.association;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.association.MemberAssociation;
import software.blacknode.backend.domain.member.association.meta.MemberAssociationMeta.Scope;
import software.blacknode.backend.domain.member.association.repository.MemberAssociationRepository;

@Repository
public class MemberAssociationRepositoryImpl implements MemberAssociationRepository {

	@Override
	public Optional<MemberAssociation> findById(HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<MemberAssociation> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberAssociation> findByMemberIds(List<HUID> memberIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberAssociation> findByMemberId(HUID memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberAssociation> findByMemberIdAndScope(HUID memberId, Scope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MemberAssociation> findByMemberIdAndScopeIdAndScope(HUID memberId, HUID scopeId, Scope scope) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(MemberAssociation association) {
		// TODO Auto-generated method stub
		
	}

}
