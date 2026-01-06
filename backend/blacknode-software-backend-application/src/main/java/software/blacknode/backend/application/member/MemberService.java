package software.blacknode.backend.application.member;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.member.meta.delete.impl.MemberDefaultDeletionMeta;
import software.blacknode.backend.domain.member.repository.MemberRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository repository;
	
	public Optional<Member> get(HUID organizationId, HUID memberId) {
		return repository.findById(organizationId, memberId);
	}
	
	public Member getOrThrow(HUID organizationId, HUID memberId) {
		return get(organizationId, memberId)
				.orElseThrow(() -> new RuntimeException("Member with ID " + memberId + " not found."));
	}
	
	public Optional<Member> getByAccountId(HUID accountId, HUID organizationId) {
		return repository.findByAccountId(organizationId, accountId);
	}
	
	public Member getByAccountIdOrThrow(HUID accountId, HUID organizationId) {
		return getByAccountId(accountId, organizationId)
				.orElseThrow(() -> new RuntimeException("Member with Account ID " + accountId + " not found."));
	}
	
	public Member create(HUID organizationId, CreationMeta meta) {
		var member = new Member(organizationId);
		
		member.create(meta);
		
		repository.save(organizationId, member);
		
		return member;
	}
	
	public Member modify(HUID organizationId, HUID memberId, ModificationMeta meta) {
		return modify(organizationId, memberId, List.of(meta));
	}
	
	public Member modify(HUID organizationId, HUID memberId, List<ModificationMeta> metas) {
		var member = getOrThrow(organizationId, memberId);
		
		for(var meta : metas) {
			member.modify(meta);
		}
		
		repository.save(organizationId, member);
		
		return member;
	}
	
	public void delete(HUID organizationId, HUID memberId) {
		var meta = MemberDefaultDeletionMeta.builder().build();
		
		delete(organizationId, memberId, meta);
	}
	
	public void delete(HUID organizationId, HUID memberId, DeletionMeta meta) {
		var member = getOrThrow(organizationId, memberId);
		
		member.delete(meta);
		
		repository.save(organizationId, member);
	}

	
}
