package software.blacknode.backend.domain.project;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
import software.blacknode.backend.domain.project.meta.create.ProjectInitialCreationMeta;
import software.blacknode.backend.domain.project.meta.modify.ProjectColorModificationMeta;
import software.blacknode.backend.domain.project.meta.modify.ProjectDescriptionModificationMeta;
import software.blacknode.backend.domain.project.meta.modify.ProjectNameModificationMeta;

@Builder
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
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
		
		this.meta = ProjectMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof ProjectInitialCreationMeta _meta) {
			var name = _meta.getName();
			var description = _meta.getDescription();
			var color = _meta.getColor();
			
			this.meta = this.meta.withName(name)
					.withDescription(description)
					.withColor(color);
			
		} else {
			throwUnsupportedCreationMeta(meta);
		}
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof ProjectNameModificationMeta _meta) {
			var name = _meta.getName();
			
			this.meta = this.meta.withName(name);
		}
		else if(meta instanceof ProjectDescriptionModificationMeta _meta) {
			var description = _meta.getDescription();
			
			this.meta = this.meta.withDescription(description);
		}
		else if(meta instanceof ProjectColorModificationMeta _meta) {
			var color = _meta.getColor();
			
			this.meta = this.meta.withColor(color);
		}
		else {
			throwUnsupportedModificationMeta(meta);
		}
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
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
