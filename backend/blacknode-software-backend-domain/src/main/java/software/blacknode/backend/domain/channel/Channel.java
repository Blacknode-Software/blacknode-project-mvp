package software.blacknode.backend.domain.channel;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.channel.meta.ChannelMeta;
import software.blacknode.backend.domain.channel.meta.create.ChannelInitialCreationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public class Channel implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private ChannelMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID projectId;
	
	@Getter private final HUID organizationId;
	
	public Channel(HUID organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public void create(Optional<CreationMeta> meta0) {
		if(meta0.isEmpty()) throw new BlacknodeException("CreationMeta is required to create a Project");
		
		this.id = HUID.random();
		
		this.meta = ChannelMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof ChannelInitialCreationMeta _meta) {
			var projectId = _meta.getProjectId();
			
			var name = _meta.getName();
			var description = _meta.getDescription();
			var color = _meta.getColor();			
			
			this.meta = this.meta.withName(name)
					.withDescription(description)
					.withColor(color);
			
			this.projectId = projectId;
		} else {
			throw new BlacknodeException("Unsupported CreationMeta type for Channel creation: " + meta.getClass().getName());
		}
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		// TODO Auto-generated method stub
		
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
