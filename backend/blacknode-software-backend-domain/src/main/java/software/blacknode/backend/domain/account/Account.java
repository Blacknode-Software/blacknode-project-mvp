package software.blacknode.backend.domain.account;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.account.meta.AccountMeta;
import software.blacknode.backend.domain.account.meta.create.AccountCreationMeta;
import software.blacknode.backend.domain.account.meta.delete.AccountDeletionMeta;
import software.blacknode.backend.domain.account.meta.modify.AccountModificationMeta;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;

@Builder
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
@ToString
public class Account implements DomainEntity, Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private String email;
	
	//@Getter private AccountSettings settings;
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
		//this.settings = new AccountSettings();
		
		var meta = meta0.get();
		
		if(meta instanceof AccountCreationMeta _meta) {
			@NonNull var email = _meta.getEmail();
			
			var firstName = _meta.getFirstName().orElse("Unknown");
			var lastName = _meta.getLastName().orElse("User");
			
			this.email = email;
			
			this.meta = AccountMeta.builder()
					.firstName(firstName)
					.lastName(lastName)
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
		
		if(meta instanceof AccountModificationMeta _meta) {
			var updated = this.meta;
			
			updated = _meta.getFirstName().map(updated::withFirstName).orElse(updated);
			updated = _meta.getLastName().map(updated::withLastName).orElse(updated);
			
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
		
		if(meta instanceof AccountDeletionMeta _meta) {
			// No specific deletion logic for now
		} else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}
}
