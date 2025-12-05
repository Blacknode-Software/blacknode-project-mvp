package software.blacknode.backend.domain.account;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.account.meta.AccountMeta;
import software.blacknode.backend.domain.account.settings.AccountSettings;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public class Account implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private String firstName;
	@Getter private String lastName;
	@Getter private String email;
	
	@Getter private AccountSettings settings;
	@Getter private AccountMeta meta;
	
	@Getter private List<HUID> authentications;
	
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
