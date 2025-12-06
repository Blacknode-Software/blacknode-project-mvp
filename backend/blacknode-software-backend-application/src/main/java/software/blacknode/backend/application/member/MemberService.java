package software.blacknode.backend.application.member;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.member.repository.MemberRepository;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository repository;
	
	public Member create(CreationMeta meta) {
		var member = new Member();
		
		member.create(meta);
		
		repository.save(member);
		
		return member;
	}
}
