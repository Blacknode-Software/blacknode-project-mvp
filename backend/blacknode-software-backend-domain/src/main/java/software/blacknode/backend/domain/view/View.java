package software.blacknode.backend.domain.view;

import java.util.Optional;

import lombok.Getter;
import lombok.NonNull;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.view.meta.ViewMeta;
import software.blacknode.backend.domain.view.meta.create.ViewCreationMeta;
import software.blacknode.backend.domain.view.meta.delete.ViewDeletionMeta;
import software.blacknode.backend.domain.view.meta.modify.ViewModificationMeta;

public class View implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private ViewMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private HUID channelId;
	@Getter private final HUID organizationId;
	
	public View(HUID organizationId) {
		this.organizationId = organizationId;
	}
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof ViewCreationMeta _meta) {
			@NonNull var channelId = _meta.getChannelId();
			
			var name = _meta.getName().orElse("Unnamed View");
			var type = _meta.getType().orElse(View.Type.LIST);
			
			this.channelId = channelId;
			
			this.meta = ViewMeta.builder()
					.name(name)
					.type(type)
					.build();
			
		} else throwUnsupportedCreationMeta(meta);
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof ViewModificationMeta _meta) {
			var updated = this.meta;
			
			updated = _meta.getName().map(updated::withName).orElse(updated);
			updated = _meta.getType().map(updated::withType).orElse(updated);
			
			this.meta = updated;
			
		} else throwUnsupportedModificationMeta(meta);
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof ViewDeletionMeta) {
			// No additional data to process for now
		} else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}
	
	public void ensureBelongsToOrganization(HUID organizationId) {
		if(!belognsToOrganization(organizationId)) {
			throw new BlacknodeException("View does not belong to organization with ID: " + organizationId);
		}
	}
	
	public boolean belognsToOrganization(HUID organizationId) {
		return this.organizationId.equals(organizationId);
	}

	public static enum Type {
		KANBAN,
		LIST,
		GANTT,
	}
}
