package software.blacknode.backend.domain.auth;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.auth.meta.AuthMeta;
import software.blacknode.backend.domain.auth.properties.AuthProperties;
import software.blacknode.backend.domain.auth.type.AuthType;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public class Auth implements Creatable, Deletable, Modifiable {

	@Getter private HUID id;
	
	@Getter private AuthProperties properties;
	@Getter private AuthType type;
	@Getter private AuthMeta meta;
	
	@Getter private List<HUID> associatedMembers;
	
	@Getter private HUID accountId;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Override
	public void create(Optional<CreationMeta> meta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta) {
		// TODO Auto-generated method stub
		
	}
}
