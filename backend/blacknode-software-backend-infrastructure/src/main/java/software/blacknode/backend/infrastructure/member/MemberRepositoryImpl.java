package software.blacknode.backend.infrastructure.member;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.member.repository.MemberRepository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

	@Override
	public Optional<Member> findById(HUID organizationId, HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Member> findAllById(HUID organizationId, List<HUID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> findAllInOrganization(HUID organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Member> findByAccountId(HUID organizationId, HUID accountId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(HUID organizationId, Member member) {
		// TODO Auto-generated method stub
		
	}


}
