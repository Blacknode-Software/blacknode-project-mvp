package software.blacknode.backend.domain.member.repository;

import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.Member;

public interface MemberRepository {

	Optional<Member> findById(HUID id);

	Optional<Member> findByAccountId(HUID accountId);
	
	void save(Member member);
}
