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
import software.blacknode.backend.domain.role.meta.create.RoleInitialChannelScopeCreationMeta;
import software.blacknode.backend.domain.role.meta.create.RoleInitialOrganizationScopeCreationMeta;
import software.blacknode.backend.domain.role.meta.create.RoleInitialProjectScopeCreationMeta;

public class Role implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private RoleMeta meta;
	
	@Getter private Scope scope;
	
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
		
		if(meta instanceof RoleInitialOrganizationScopeCreationMeta initOrgMeta) {
			var name = initOrgMeta.getName();
			var description = initOrgMeta.getDescription();
			var color = initOrgMeta.getColor();
			var byDefaultAssigned = initOrgMeta.isByDefaultAssigned();
			
			this.scope = Scope.ORGANIZATION;
			
			this.meta.withName(name)
					.withDescription(description)
				 	.withColor(color)
				 	.withByDefaultAssigned(byDefaultAssigned)
				 	.withSystemDefault(true);
			
			this.organizationId = initOrgMeta.getOrganizationId();
		} 
		else if (meta instanceof RoleInitialProjectScopeCreationMeta initProjMeta) {
			var name = initProjMeta.getName();
			var description = initProjMeta.getDescription();
			var color = initProjMeta.getColor();
			var byDefaultAssigned = initProjMeta.isByDefaultAssigned();
			
			this.scope = Scope.PROJECT;
			
			this.meta.withName(name)
					.withDescription(description)
				 	.withColor(color)
				 	.withByDefaultAssigned(byDefaultAssigned)
				 	.withSystemDefault(true);
			
			this.organizationId = initProjMeta.getOrganizationId();
		}
		else if (meta instanceof RoleInitialChannelScopeCreationMeta initChnlMeta) {
			var name = initChnlMeta.getName();
			var description = initChnlMeta.getDescription();
			var color = initChnlMeta.getColor();
			var byDefaultAssigned = initChnlMeta.isByDefaultAssigned();
			
			this.scope = Scope.CHANNEL;
			
			this.meta.withName(name)
					.withDescription(description)
				 	.withColor(color)
				 	.withByDefaultAssigned(byDefaultAssigned)
				 	.withSystemDefault(true);
			
			this.organizationId = initChnlMeta.getOrganizationId();
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
	
	public boolean belongsToOrganization(HUID organizationId) {
        if (organizationId == null) return false;
        return organizationId.equals(this.organizationId);
    }

    public void ensureBelongsToOrganization(HUID organizationId) {
        if (!belongsToOrganization(organizationId)) {
            BlacknodeException.throwWith("Role with ID " + id + " does not belong to Organization with ID " + organizationId + ".");
        }
    }
    
    public boolean hasScope(Scope scope) {
		return this.scope == scope;
	}
    
    public void ensureHasScope(Scope scope) {
		if (!hasScope(scope)) {
			BlacknodeException.throwWith("Role with ID " + id + " does not have scope " + scope + ".");
		}
    }
    
    public static enum Scope {
		GLOBAL,
		ORGANIZATION,
		PROJECT,
		CHANNEL,
		UNKNOWN
	}
}
