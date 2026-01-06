package software.blacknode.backend.domain.member.repository;

import java.util.List;
import java.util.Optional;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.Member;

public interface MemberRepository {

	Optional<Member> findById(HUID organizationId, HUID id);
	
	List<Member> findAllById(HUID organizationId, List<HUID> ids);
	
	List<Member> findAllInOrganization(HUID organizationId);

	Optional<Member> findByAccountId(HUID organizationId, HUID accountId);
	
	void save(HUID organizationId, Member member);
}
