package software.blacknode.backend.domain.channel;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.channel.meta.ChannelMeta;
import software.blacknode.backend.domain.channel.meta.create.ChannelCreationMeta;
import software.blacknode.backend.domain.channel.meta.delete.ChannelDeletionMeta;
import software.blacknode.backend.domain.channel.meta.modify.ChannelModificationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;

public class Channel implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private ChannelMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private HUID projectId;
	
	@Getter private final HUID organizationId;
	
	public Channel(HUID organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		this.meta = ChannelMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof ChannelCreationMeta _meta) {
			var projectId = _meta.getProjectId();

			var name = _meta.getName().orElse("Unnamed Channel");
			var description = _meta.getDescription().orElse("");
			var color = _meta.getColor().orElse("#FFFFFF");
			
			this.meta = this.meta
					.withName(name)
					.withDescription(description)
					.withColor(color);
			
			this.projectId = projectId;
		} else throwUnsupportedCreationMeta(meta);
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof ChannelModificationMeta _meta) {
			var updated = this.meta;
			
			updated = _meta.getName().map(updated::withName).orElse(updated);
			updated = _meta.getDescription().map(updated::withDescription).orElse(updated);
			updated = _meta.getColor().map(updated::withColor).orElse(updated);
			
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
		
		if(meta instanceof ChannelDeletionMeta _meta) {
			// No specific deletion logic for now
		} else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}
	
	public boolean belongsToProject(HUID projectId) {
		return this.projectId.equals(projectId);
	}
	
	public void ensureBelongsToProject(HUID projectId) {
		if(!this.projectId.equals(projectId)) {
			throw new BlacknodeException("Channel does not belong to the specified project.");
		}
	}
	
	public boolean belongsToOrganization(HUID organizationId) {
		return this.organizationId.equals(organizationId);
	}
	
	public void ensureBelongsToOrganization(HUID organizationId) {
		if(!this.organizationId.equals(organizationId)) {
			throw new BlacknodeException("Channel does not belong to the specified organization.");
		}
	}
}
