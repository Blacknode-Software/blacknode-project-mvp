package software.blacknode.backend.application.view;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.view.View;
import software.blacknode.backend.domain.view.meta.delete.impl.ViewDefaultDeletionMeta;
import software.blacknode.backend.domain.view.repository.ViewRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class ViewService {

	private final ViewRepository repository;
	
	public View create(HUID organizationId, CreationMeta meta) {
		var view = new View(organizationId);
		
		view.create(meta);
		
		repository.save(organizationId, view);
		
		return view;
	}
	
	public View modify(HUID organizationId, HUID viewId, ModificationMeta meta) {
		return modify(organizationId, viewId, List.of(meta));
	}
	
	public View modify(HUID organizationId, HUID viewId, List<ModificationMeta> metas) {
		var view = getOrThrow(organizationId, viewId);
		
		for(var meta : metas) {
			view.modify(meta);
		}
		
		repository.save(organizationId, view);
	
		return view;
	}
	
	public void delete(HUID organizationId, HUID viewId) {
		var meta = ViewDefaultDeletionMeta.builder().build();
		
		delete(organizationId, viewId, meta);
	}
	
	public void delete(HUID organizationId, HUID viewId, DeletionMeta meta) {
		var view = getOrThrow(organizationId, viewId);
		
		view.delete(meta);
		
		repository.save(organizationId, view);
	}
	
	public Optional<View> get(HUID organizationId, HUID viewId) {
		return repository.findById(organizationId, viewId);
	}
	
	public View getOrThrow(HUID organizationId, HUID viewId) {
		return get(organizationId, viewId).orElseThrow(() -> 
			new BlacknodeException("View with id " + viewId + " not found")
		);
	}
	
	public List<View> getByIds(HUID organizationId, List<HUID> viewIds) {
		return repository.findByIds(organizationId, viewIds);
	}
	
	public List<View> getAllInChannel(HUID organizationId, HUID channelId) {
		return repository.findByChannelId(organizationId, channelId);
	}
	
}
