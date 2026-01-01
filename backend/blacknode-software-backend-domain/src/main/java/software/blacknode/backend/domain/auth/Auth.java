package software.blacknode.backend.domain.auth;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.auth.meta.AuthMeta;
import software.blacknode.backend.domain.auth.meta.create.AuthCreationMeta;
import software.blacknode.backend.domain.auth.meta.delete.AuthDeletionMeta;
import software.blacknode.backend.domain.auth.meta.modify.AuthModificationMeta;
import software.blacknode.backend.domain.auth.method.AuthMethod;
import software.blacknode.backend.domain.auth.method.meta.AuthMethodMeta;
import software.blacknode.backend.domain.auth.method.type.AuthMethodType;
import software.blacknode.backend.domain.entity.DomainEntity;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;	

@Builder
@AllArgsConstructor(onConstructor = @__({ @Deprecated }))
@ToString
public class Auth implements DomainEntity, Creatable, Deletable, Modifiable {

	@Getter private HUID id;
	
	@Getter private AuthMeta meta;
	
	@Getter private AuthMethod method;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private final HUID accountId;

	public Auth(@NonNull HUID accountId) {
		this.accountId = accountId;
	}
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		
		var meta = meta0.get();
		
		if(meta instanceof AuthCreationMeta _meta) {
			@NonNull var method = _meta.getAuthMethod();
			
			this.method = method;
			
			this.meta = AuthMeta.builder()
					.build();
		}
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureModificationMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof AuthModificationMeta _meta) {
			
			_meta.getAuthMethod().ifPresent(this::updateAuthMethod);
			
		}
		
		modificationTimestamp = Timestamp.now();
	}
	
	private void updateAuthMethod(AuthMethod method) {
		var currentMethodId = this.method.getType().getId();
		var newMethodId = method.getType().getId();
		
		if(currentMethodId.equals(newMethodId)) {
			this.method = method;
		} 
		else throw new BlacknodeException("Cannot change auth method type during modification! "
				+ "Current: " + this.method.getType().getName() 
				+ ", New: " + method.getType().getName());
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		ensureCreated(meta0);
		ensureNotDeleted(meta0);
		ensureDeletionMetaProvided(meta0);
		
		var meta = meta0.get();
		
		if(meta instanceof AuthDeletionMeta _meta) {
			// No specific deletion logic for now
		} else throwUnsupportedDeletionMeta(meta);
		
		deletionTimestamp = Timestamp.now();
	}
	
	public boolean authenticate(AuthMethodMeta meta) {
		return method.authenticate(meta);
	}
	
	public AuthMethodType getAuthMethodType() {
		return method.getType();
	}
	
	public boolean canAuthenticate(AuthMethodMeta meta) {
		try { method.authenticate(meta); } 
		catch (Exception e) { return false; }
		
		return true;
	}
	
	public void ensureBelongsToAccount(HUID accountId) {
		if (!belongsToAccount(accountId)) {
			throw new BlacknodeException("Auth with ID " + id + " does not belong to Account with ID " + accountId + ".");
		}
	}
	
	public boolean belongsToAccount(HUID accountId) {
		if (accountId == null) return false;
		return accountId.equals(this.accountId);
	}

	
}
