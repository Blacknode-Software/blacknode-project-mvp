package software.blacknode.backend.domain.organization;

import java.util.List;
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
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.organization.meta.OrganizationMeta;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;
import software.blacknode.backend.domain.organization.settings.OrganizationSettings;

public class Organization implements Creatable, Modifiable, Deletable {
	public static final HUID DEFAULT_ORGANIZATION_ID = HUID.fromString("e63c7895-6d65-41cb-9400-000000000001");

	// TODO move name to OrganizationMeta
	// TODO add modification meta for changing name
	
	@Getter private HUID id;
	@Getter private String name;
	
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
		this.name = "Unnamed Organization";
		
		this.settings = new OrganizationSettings();
		this.meta = new OrganizationMeta();
		
		var meta = meta0.get();
		
		if(meta instanceof OrganizationInitialCreationMeta initOrgMeta) {
			this.id = DEFAULT_ORGANIZATION_ID;
			this.name = initOrgMeta.getName();
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
