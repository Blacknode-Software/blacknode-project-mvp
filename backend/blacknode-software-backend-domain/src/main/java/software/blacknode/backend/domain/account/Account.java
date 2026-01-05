package software.blacknode.backend.domain.account;

import java.net.IDN;
import java.util.Optional;
import java.util.regex.Pattern;

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
import software.blacknode.backend.domain.validate.exception.BlacknodeValidationException;

@Builder
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
@ToString
public class Account implements DomainEntity, Creatable, Modifiable, Deletable {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
	
	@Getter private HUID id;
	@Getter private String email;
	
	//@Getter private AccountSettings settings;
	@Getter private AccountMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	
	
	public Account() {
		// Default constructor
	}
	
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
			
			validateEmail(email);
			
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
	
	private void validateEmail(String email) {
		 if (email == null) {
	        throw new BlacknodeValidationException("Invalid email address: null");
	    }

	    String trimmed = email.trim();
	    if (trimmed.isEmpty()) {
	        throw new BlacknodeValidationException("Invalid email address: empty");
	    }

	    // Overall length limit per RFCs (practical)
	    if (trimmed.length() > 254) {
	        throw new BlacknodeValidationException("Invalid email address (too long): " + email);
	    }

	    int atIndex = trimmed.lastIndexOf('@');
	    if (atIndex <= 0 || atIndex == trimmed.length() - 1) {
	        throw new BlacknodeValidationException("Invalid email address: " + email);
	    }

	    String local = trimmed.substring(0, atIndex);
	    String domain = trimmed.substring(atIndex + 1);

	    // Convert internationalized domain names to ASCII form (punycode)
	    try {
	        domain = IDN.toASCII(domain);
	    } catch (Exception ex) {
	        throw new BlacknodeValidationException("Invalid email domain: " + email);
	    }

	    String normalized = local + "@" + domain;
	    if (normalized.length() > 254) {
	        throw new BlacknodeValidationException("Invalid email address (too long after normalization): " + email);
	    }

	    if (!EMAIL_PATTERN.matcher(normalized).matches()) {
	        throw new BlacknodeValidationException("Invalid email address: " + email);
	    }
	}
}
