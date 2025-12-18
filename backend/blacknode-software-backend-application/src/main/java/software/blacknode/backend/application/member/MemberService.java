package software.blacknode.backend.application.member;

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
	
	public Member getOrThrow(HUID organizationId, HUID memberId) {
		return repository.findById(memberId)
				.orElseThrow(() -> new RuntimeException("Member with ID " + memberId + " not found."));
	}
	
	public Member getByAccountId(HUID accountId, HUID organizationId) {
		return repository.findByAccountId(accountId)
				.orElseThrow(() -> new RuntimeException("Member with Account ID " + accountId + " not found."));
	}
	
	public Member create(HUID organizationId, CreationMeta meta) {
		var member = new Member(organizationId);
		
		member.create(meta);
		
		repository.save(member);
		
		return member;
	}
}
