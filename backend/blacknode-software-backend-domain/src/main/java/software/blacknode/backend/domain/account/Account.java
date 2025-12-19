package software.blacknode.backend.domain.account;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.account.meta.AccountMeta;
import software.blacknode.backend.domain.account.meta.create.AccountInitialAdminCreationMeta;
import software.blacknode.backend.domain.account.settings.AccountSettings;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

public class Account implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private String email;
	
	@Getter private AccountSettings settings;
	@Getter private AccountMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		this.email = "unknown@unknown.com";
		
		this.settings = new AccountSettings();
		this.meta = AccountMeta.builder().build();
		
		var meta = meta0.get();
		
		if(meta instanceof AccountInitialAdminCreationMeta _meta) {
			var email = _meta.getEmail();
			var firstName = _meta.getFirstName();
			var lastName = _meta.getLastName();
			
			this.email = email;
			
			this.meta.withFirstName(firstName)
				.withLastName(lastName);
		} else {
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
