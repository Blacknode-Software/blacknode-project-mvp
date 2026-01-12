package software.blacknode.backend.domain.member.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.Member;

public interface MemberRepository {

	Optional<Member> findById(HUID organizationId, HUID id);
	
	List<Member> findByIds(HUID organizationId, Set<HUID> ids);
	
	List<Member> findByOrganizationId(HUID organizationId);

	Optional<Member> findByAccountId(HUID organizationId, HUID accountId);
	
	void save(HUID organizationId, Member member);
}
