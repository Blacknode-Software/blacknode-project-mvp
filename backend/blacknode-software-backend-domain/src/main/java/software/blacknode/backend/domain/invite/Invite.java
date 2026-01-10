package software.blacknode.backend.domain.invite;

import java.net.IDN;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.invite.meta.InviteMeta;
import software.blacknode.backend.domain.invite.meta.create.InviteCreationMeta;
import software.blacknode.backend.domain.invite.meta.delete.InviteDeletionMeta;
import software.blacknode.backend.domain.invite.meta.modify.InviteModificationMeta;
import software.blacknode.backend.domain.validate.exception.BlacknodeValidationException;

@Builder
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
@ToString
public class Invite implements DomainEntity, Creatable, Modifiable, Deletable {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
	
	private static final SecureRandom RNG = new SecureRandom();
	private static final int BYTES = 32;
	
	@Getter private HUID id;
	
	@Getter private String token;
	
	@Getter private InviteMeta meta;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private final HUID organizationId;
	
	public Invite(HUID organizationId) {
		this.organizationId = organizationId;
	}
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof InviteCreationMeta _meta) {
			@NonNull var email = _meta.getEmail();
			
			var expiresAt = _meta.getExpiresAt().orElse(Timestamp.now().addDays(7));
			var token = generateToken();
			
			var revoked = false;
			
			var claimedBy = Optional.<HUID>empty();
			var claimedAt = Optional.<Timestamp>empty();
			
			this.token = token;
			
			this.meta = InviteMeta.builder()
								  .email(email)
								  .expiresAt(expiresAt)
								  .revoked(revoked)
								  .claimedBy(claimedBy)
								  .claimedAt(claimedAt)
								  .build();
			
		} else throwUnsupportedCreationMeta(meta);
		
		this.creationTimestamp = Timestamp.now();
	}

	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof InviteModificationMeta _meta) {
			var updated = this.meta;
			
			updated = _meta.getEmail().map(updated::withEmail).orElse(updated);
			updated = _meta.getExpiresAt().map(updated::withExpiresAt).orElse(updated);
			updated = _meta.isRevoked().map(updated::withRevoked).orElse(updated);
			
			if(_meta.isClaimedAtSet()) {
				updated = updated.withClaimedAt(_meta.getClaimedAt());
			}
			
			if(_meta.isClaimedBySet()) {
				updated = updated.withClaimedBy(_meta.getClaimedBy());
			}
			
			this.meta = updated;
			
		} else throwUnsupportedModificationMeta(meta);
	
		this.modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof InviteDeletionMeta) {
			// No specific deletion meta for Invite
		} else throwUnsupportedDeletionMeta(meta);
		
		
		this.deletionTimestamp = Timestamp.now();
	}

	private String generateToken() {
		byte[] buffer = new byte[BYTES];
		
        RNG.nextBytes(buffer);
        
        return Base64.getUrlEncoder()
                     .withoutPadding()
                     .encodeToString(buffer);
	}
	
	public boolean isRevoked() {
		return meta.isRevoked();
	}
	
	public boolean isClaimed() {
		return meta.getClaimedBy().isPresent();
	}
	
	public boolean isExpired() {
		return meta.getExpiresAt().isPast();
	}
	
	public void ensureBelongsToOrganization(HUID organizationId) {
		if(!this.organizationId.equals(organizationId)) {
			throw new BlacknodeException("Invite does not belong to organization: " + organizationId);
		}
	}

	public boolean belongsToOrganization(HUID organizationId) {
		return this.organizationId.equals(organizationId);
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
