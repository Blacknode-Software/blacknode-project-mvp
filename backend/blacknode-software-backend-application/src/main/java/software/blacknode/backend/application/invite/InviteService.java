package software.blacknode.backend.application.invite;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.invite.Invite;
import software.blacknode.backend.domain.invite.meta.delete.impl.InviteDefaultDeletionMeta;
import software.blacknode.backend.domain.invite.repository.InviteRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class InviteService {

	private final InviteRepository repository;
	
	public Invite create(HUID organizationId, CreationMeta meta) {
		var invite = new Invite(organizationId);
		
		invite.create(meta);
		
		repository.save(organizationId, invite);
		
		return invite;
	}
	
	public Invite modify(HUID organizationId, HUID inviteId, ModificationMeta meta) {
		return modify(organizationId, inviteId, List.of(meta));
	}
	
	public Invite modify(HUID organizationId, HUID inviteId, List<ModificationMeta> metas) {
		var invite = getOrThrow(organizationId, inviteId);
		
		for(var meta : metas) {
			invite.modify(meta);
		}
		
		repository.save(organizationId, invite);
		
		return invite;
	}
	
	public void delete(HUID organizationId, HUID inviteId) {
		var meta = InviteDefaultDeletionMeta.builder().build();
		
		delete(organizationId, inviteId, meta);
	}
	
	public void delete(HUID organizationId, HUID inviteId, DeletionMeta meta) {
		var invite = getOrThrow(organizationId, inviteId);
		
		invite.delete(meta);
		
		repository.save(organizationId, invite);
	}
	
	public Optional<Invite> get(HUID organizationId, HUID inviteId) {
		return repository.findById(organizationId, inviteId);
	}
	
	public Invite getOrThrow(HUID organizationId, HUID inviteId) {
		return get(organizationId, inviteId)
				.orElseThrow(() -> new BlacknodeException("Invite not found: " + inviteId));
	}
	
	public List<Invite> getByIds(HUID organizationId, List<HUID> inviteIds) {
		return getByIds(organizationId, Set.copyOf(inviteIds));
	}
	
	public List<Invite> getByIds(HUID organizationId, Set<HUID> inviteIds) {
		return repository.findByIds(organizationId, inviteIds);
	}
	
	public List<Invite> getAll(HUID organizationId) {
		return repository.findAll(organizationId);
	}
	
}
