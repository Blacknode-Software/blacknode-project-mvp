package software.blacknode.backend.domain.role;

import java.util.Optional;

import lombok.Getter;
import lombok.NonNull;
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
import software.blacknode.backend.domain.role.meta.RoleMeta;
import software.blacknode.backend.domain.role.meta.create.RoleCreationMeta;
import software.blacknode.backend.domain.role.meta.delete.RoleDeletionMeta;
import software.blacknode.backend.domain.role.meta.modify.RoleModificationMeta;

public class Role implements DomainEntity, Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private RoleMeta meta;
	
	@Getter private Scope scope;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private final HUID organizationId;
	
	public Role(HUID organizationId) {
		this.organizationId = organizationId;
	}
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof RoleCreationMeta _meta) {
			@NonNull var scope = _meta.getScope();
			
			var name = _meta.getName().orElse("Unknown");
			var description = _meta.getDescription().orElse("Unknown role description");
			var color = _meta.getColor().orElse("#FF0000");
			var systemDefault = _meta.isSystemDefault().orElse(false);
			var byDefaultAssigned = _meta.isByDefaultAssigned().orElse(false);
			var superPrivileged = _meta.isSuperPrivileged().orElse(false);
			
			this.scope = scope;
			this.meta = RoleMeta.builder()
					.name(name)
					.description(description)
				 	.color(color)
				 	.systemDefault(systemDefault)
				 	.byDefaultAssigned(byDefaultAssigned)
				 	.superPrivileged(superPrivileged)
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
		
		if(meta instanceof RoleModificationMeta _meta) {
			var updated = this.meta;
			
			updated = _meta.getName().map(updated::withName).orElse(updated);
			updated = _meta.getDescription().map(updated::withDescription).orElse(updated);
			updated = _meta.getColor().map(updated::withColor).orElse(updated);
			updated = _meta.isSystemDefault().map(updated::withSystemDefault).orElse(updated);
			updated = _meta.isByDefaultAssigned().map(updated::withByDefaultAssigned).orElse(updated);
			updated = _meta.isSuperPrivileged().map(updated::withSuperPrivileged).orElse(updated);

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
		
		if(meta instanceof RoleDeletionMeta _meta) {
			// No specific deletion logic for now
		} else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}
	
	public boolean belongsToOrganization(HUID organizationId) {
        if (organizationId == null) return false;
        return organizationId.equals(this.organizationId);
    }

    public void ensureBelongsToOrganization(HUID organizationId) {
        if (!belongsToOrganization(organizationId)) {
            throw new BlacknodeException("Role with ID " + id + " does not belong to Organization with ID " + organizationId + ".");
        }
    }
    
    public boolean hasScope(Scope scope) {
		return this.scope == scope;
	}
    
    public void ensureHasScope(Scope scope) {
		if (!hasScope(scope)) {
			throw new BlacknodeException("Role with ID " + id + " does not have scope " + scope + ".");
		}
    }
    
    public static enum Scope {
		GLOBAL,
		ORGANIZATION,
		PROJECT,
		CHANNEL,
		UNKNOWN,
		
		;
		
		public boolean isGlobal() {
			return this == GLOBAL;
		}
		
		public boolean isOrganization() {
			return this == ORGANIZATION;
		}
		
		public boolean isProject() {
			return this == PROJECT;
		}
		
		public boolean isChannel() {
			return this == CHANNEL;
		}
	}
}
