package software.blacknode.backend.domain.account;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.account.meta.AccountMeta;
import software.blacknode.backend.domain.account.meta.create.AccountInitialAdminCreationMeta;
import software.blacknode.backend.domain.account.settings.AccountSettings;
import software.blacknode.backend.domain.exception.BlacknodeException;
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
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		if(meta0.isEmpty()) BlacknodeException.throwWith("CreationMeta is required to create an Account");
		
		this.id = HUID.random();
		this.firstName = "Unknown";
		this.lastName = "Unknown";
		this.email = "unknown@unknown.com";
		
		this.settings = new AccountSettings();
		this.meta = new AccountMeta();
		
		var meta = meta0.get();
		
		if(meta instanceof AccountInitialAdminCreationMeta initialAdminMeta) {
			this.firstName = initialAdminMeta.getFirstName();
			this.lastName = initialAdminMeta.getLastName();
			this.email = initialAdminMeta.getEmail();
		} else {
			BlacknodeException.throwWith("Unsupported CreationMeta type for Account creation");
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
