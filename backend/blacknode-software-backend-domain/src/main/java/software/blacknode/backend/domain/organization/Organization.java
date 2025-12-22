package software.blacknode.backend.domain.organization;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.organization.meta.OrganizationMeta;
import software.blacknode.backend.domain.organization.meta.create.OrganizationCreationMeta;
import software.blacknode.backend.domain.organization.meta.create.impl.OrganizationInitialCreationMeta;
import software.blacknode.backend.domain.organization.meta.delete.OrganizationDeletionMeta;
import software.blacknode.backend.domain.organization.meta.modify.OrganizationModificationMeta;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Organization implements DomainEntity, Creatable, Modifiable, Deletable {
	public static final HUID DEFAULT_ORGANIZATION_ID = HUID.fromString("e63c7895-6d65-41cb-9400-000000000001");
	
	@Getter private HUID id;
	
	//@Getter private OrganizationSettings settings;
	@Getter private OrganizationMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);

		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof OrganizationInitialCreationMeta _meta) {
			this.id = DEFAULT_ORGANIZATION_ID;
		}
		
		if(meta instanceof OrganizationCreationMeta _meta) {
			var name = _meta.getName().orElse("Unnamed Organization");
			
			this.meta = OrganizationMeta.builder()
					.name(name)
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
		
		if(meta instanceof OrganizationModificationMeta _meta) {
			var updated = this.meta;
			
			updated = _meta.getName().map(updated::withName).orElse(updated);
			
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
		
		if(meta instanceof OrganizationDeletionMeta _meta) {
			// no specific deletion logic for now
		} 
		else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}

	
}
