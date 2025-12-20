package software.blacknode.backend.domain.organization;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.organization.meta.OrganizationMeta;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;
import software.blacknode.backend.domain.organization.meta.modify.OrganizationNameModificationMeta;
import software.blacknode.backend.domain.organization.settings.OrganizationSettings;

public class Organization implements Creatable, Modifiable, Deletable {
	public static final HUID DEFAULT_ORGANIZATION_ID = HUID.fromString("e63c7895-6d65-41cb-9400-000000000001");
	
	@Getter private HUID id;
	
	@Getter private OrganizationSettings settings;
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
		
		this.settings = new OrganizationSettings();
		this.meta = OrganizationMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof OrganizationInitialCreationMeta initOrgMeta) {
			var id = DEFAULT_ORGANIZATION_ID;
			
			var name = initOrgMeta.getName();
			
			this.id = id;
			this.meta = this.meta.withName(name);
		}
		else {
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
		
		if(meta instanceof OrganizationNameModificationMeta nameMeta) {
			var name = nameMeta.getName();
			
			this.meta = this.meta.withName(name);
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

	
}
