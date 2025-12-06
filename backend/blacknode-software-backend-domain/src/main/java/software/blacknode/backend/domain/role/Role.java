package software.blacknode.backend.domain.role;

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
import software.blacknode.backend.domain.role.meta.RoleMeta;
import software.blacknode.backend.domain.role.meta.create.RoleDefaultChannelScopeCreationMeta;
import software.blacknode.backend.domain.role.meta.create.RoleDefaultOrganizationScopeCreationMeta;
import software.blacknode.backend.domain.role.meta.create.RoleDefaultProjectScopeCreationMeta;

public class Role implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private RoleMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID organizationId;
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		if(meta0.isEmpty()) BlacknodeException.throwWith("CreationMeta is required to create a Role");
		
		this.id = HUID.random();
		this.meta = RoleMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof RoleDefaultOrganizationScopeCreationMeta orgMeta) {
			this.meta.withName(orgMeta.getName())
					.withDescription(orgMeta.getDescription())
				 	.withColor(orgMeta.getColor())
				 	.withScope(RoleMeta.Scope.ORGANIZATION)
				 	.withByDefault(true);
			
			this.organizationId = orgMeta.getOrganizationId();
		} 
		else if (meta instanceof RoleDefaultProjectScopeCreationMeta projMeta) {
			this.meta.withName(projMeta.getName())
					.withDescription(projMeta.getDescription())
				 	.withColor(projMeta.getColor())
				 	.withScope(RoleMeta.Scope.PROJECT)
				 	.withByDefault(true);
			
			this.organizationId = projMeta.getOrganizationId();
		}
		else if (meta instanceof RoleDefaultChannelScopeCreationMeta chanMeta) {
			this.meta.withName(chanMeta.getName())
					.withDescription(chanMeta.getDescription())
				 	.withColor(chanMeta.getColor())
				 	.withScope(RoleMeta.Scope.CHANNEL)
				 	.withByDefault(true);
			
			this.organizationId = chanMeta.getOrganizationId();
		}
		else {
			BlacknodeException.throwWith("Unsupported CreationMeta type for Role creation");
		}
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		// TODO Auto-generated method stub
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		// TODO Auto-generated method stub
		
		deletationTimestamp = Timestamp.now();
	}
}
