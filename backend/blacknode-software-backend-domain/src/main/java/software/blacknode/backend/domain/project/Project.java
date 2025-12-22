package software.blacknode.backend.domain.project;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.project.meta.ProjectMeta;
import software.blacknode.backend.domain.project.meta.create.ProjectCreationMeta;
import software.blacknode.backend.domain.project.meta.delete.ProjectDeletionMeta;
import software.blacknode.backend.domain.project.meta.modify.ProjectModificationMeta;

@Builder()
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
@ToString
public class Project implements DomainEntity, Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private ProjectMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private final HUID organizationId;
	
	public Project(HUID organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof ProjectCreationMeta _meta) {
			var name = _meta.getName().orElse("Unnamed Project");
			var description = _meta.getDescription().orElse("No description provided.");
			var color = _meta.getColor().orElse("#FAFAFA");
			
			this.meta = ProjectMeta.builder()
					.name(name)
					.description(description)
					.color(color)
					.build();
			
		} 
		else throwUnsupportedCreationMeta(meta);
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof ProjectModificationMeta _meta) {
			var updated = this.meta;
			
			updated = _meta.getName().map(updated::withName).orElse(updated);
		    updated = _meta.getDescription().map(updated::withDescription).orElse(updated);
		    updated = _meta.getColor().map(updated::withColor).orElse(updated);
		    
			this.meta = updated;
		}
		else throwUnsupportedModificationMeta(meta);
		
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof ProjectDeletionMeta _meta) {
			// No specific deletion logic for now
		}
		else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}
	
	public boolean belongsToOrganization(HUID organizationId) {
		return this.organizationId.equals(organizationId);
	}
	
	public void ensureBelongsToOrganization(HUID organizationId) {
		if(!belongsToOrganization(organizationId)) {
			throw new BlacknodeException("Project does not belong to organization with ID: " + organizationId);
		}
	}
}
