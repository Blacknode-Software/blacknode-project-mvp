package software.blacknode.backend.infrastructure.member;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.member.repository.MemberRepository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

	@Override
	public Optional<Member> findById(HUID id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Member> findByAccountId(HUID accountId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void save(Member member) {
		// TODO Auto-generated method stub
		
	}

}
