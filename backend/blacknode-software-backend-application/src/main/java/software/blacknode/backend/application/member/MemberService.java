package software.blacknode.backend.application.member;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.member.repository.MemberRepository;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository repository;
	
	public Member getMemberOrThrow(HUID memberId) {
		return repository.findById(memberId)
				.orElseThrow(() -> new RuntimeException("Member with ID " + memberId + " not found."));
	}
	
	public Member create(CreationMeta meta) {
		var member = new Member();
		
		member.create(meta);
		
		repository.save(member);
		
		return member;
	}
}
