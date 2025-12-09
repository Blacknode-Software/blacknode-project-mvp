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

public class Project implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private ProjectMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID organizationId;

	@Override
	public void create(Optional<CreationMeta> meta0) {
		if(meta0.isEmpty()) BlacknodeException.throwWith("CreationMeta is required to create a Project");
		
		this.id = HUID.random();
		
		this.meta = ProjectMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof ProjectInitialCreationMeta initCreMeta) {
			var organizationId = initCreMeta.getOrganizationId();
			
			var name = initCreMeta.getName();
			var description = initCreMeta.getDescription();
			var color = initCreMeta.getColor();
			
			this.meta = this.meta.withName(name)
					.withDescription(description)
					.withColor(color);
			
			this.organizationId = organizationId;
		} else {
			BlacknodeException.throwWith("Unsupported CreationMeta type for Project creation: " + meta.getClass().getName());
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
	
	public boolean belongsToOrganization(HUID organizationId) {
		return this.organizationId.equals(organizationId);
	}
	
	public void ensureBelongsToOrganization(HUID organizationId) {
		if(!belongsToOrganization(organizationId)) {
			throw new BlacknodeException("Project does not belong to organization with ID: " + organizationId);
		}
	}
}
