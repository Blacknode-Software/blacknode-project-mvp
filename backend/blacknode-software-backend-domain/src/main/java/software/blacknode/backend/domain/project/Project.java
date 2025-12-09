package software.blacknode.backend.domain.project;

import java.util.List;
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

public class Project implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private String name;
	@Getter private List<HUID> channels;
	@Getter private List<HUID> members;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID organizationId;

	@Override
	public void delete(Optional<DeletionMeta> meta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modify(Optional<ModificationMeta> meta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void create(Optional<CreationMeta> meta) {
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
