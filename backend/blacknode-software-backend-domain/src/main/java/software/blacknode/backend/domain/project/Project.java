package software.blacknode.backend.domain.project;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.project.meta.ProjectMeta;
import software.blacknode.backend.domain.project.meta.create.ProjectInitialCreationMeta;
import software.blacknode.backend.domain.project.meta.modify.ProjectColorModificationMeta;
import software.blacknode.backend.domain.project.meta.modify.ProjectDescriptionModificationMeta;
import software.blacknode.backend.domain.project.meta.modify.ProjectNameModificationMeta;

public class Project implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private ProjectMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
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
		
		deletationTimestamp = Timestamp.now();
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
