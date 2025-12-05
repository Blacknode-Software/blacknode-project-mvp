package software.blacknode.backend.domain.organization;

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
import software.blacknode.backend.domain.organization.meta.OrganizationMeta;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;
import software.blacknode.backend.domain.organization.settings.OrganizationSettings;

public class Organization implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private String name;
	
	@Getter private List<HUID> members;
	@Getter private List<HUID> projects;
	@Getter private List<HUID> roles;
	
	@Getter private OrganizationSettings settings;
	@Getter private OrganizationMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Override
	public void create(Optional<CreationMeta> creationMeta0) {
		if(creationMeta0.isEmpty()) BlacknodeException.throwWith("Creation meta must be provided when creating an organization.");
		
		CreationMeta meta1 = creationMeta0.get();
		
		id = HUID.random();
		name = "Unnamed Organization";
		
		members = List.of();
		projects = List.of();
		roles = List.of();
		
		settings = new OrganizationSettings();
		meta = new OrganizationMeta();
		
		if(meta1 instanceof OrganizationInitialCreationMeta) {
			OrganizationInitialCreationMeta _meta = (OrganizationInitialCreationMeta) meta1;
			
			name = _meta.getName();
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
