package software.blacknode.backend.application.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository repository;
	
	public Optional<Member> get(HUID organizationId, HUID memberId) {
		return repository.findById(memberId);
	}
	
	public Member getOrThrow(HUID organizationId, HUID memberId) {
		return get(organizationId, memberId)
				.orElseThrow(() -> new RuntimeException("Member with ID " + memberId + " not found."));
	}
	
	public Optional<Member> getByAccountId(HUID accountId, HUID organizationId) {
		return repository.findByAccountId(accountId);
	}
	
	public Member getByAccountIdOrThrow(HUID accountId, HUID organizationId) {
		return getByAccountId(accountId, organizationId)
				.orElseThrow(() -> new RuntimeException("Member with Account ID " + accountId + " not found."));
	}
	
	public Member create(HUID organizationId, CreationMeta meta) {
		var member = new Member(organizationId);
		
		member.create(meta);
		
		repository.save(member);
		
		return member;
	}
}
