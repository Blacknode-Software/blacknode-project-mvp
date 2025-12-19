package software.blacknode.backend.domain.auth;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import me.hinsinger.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.auth.meta.AuthMeta;
import software.blacknode.backend.domain.auth.meta.create.AuthByPasswordCreationMeta;
import software.blacknode.backend.domain.auth.properties.AuthProperties;
import software.blacknode.backend.domain.auth.properties.impl.AuthByPasswordProperties;
import software.blacknode.backend.domain.auth.type.AuthType;
import software.blacknode.backend.domain.entity.modifier.impl.create.Creatable;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.delete.Deletable;
import software.blacknode.backend.domain.entity.modifier.impl.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.Modifiable;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;	

public class Auth implements Creatable, Deletable, Modifiable {

	@Getter private HUID id;
	
	@Getter private AuthMeta meta;
	@Getter private AuthType type;
	
	private AuthProperties properties;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletionTimestamp;
	
	@Getter private HUID accountId;

	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		ensureNotDeleted(meta0);
		ensureCreationMetaProvided(meta0);
		
		this.id = HUID.random();
		this.meta = new AuthMeta();
		
		var meta = meta0.get();
		
		if(meta instanceof AuthByPasswordCreationMeta _meta) {
			var password = _meta.getPassword();
			var accountId = _meta.getAccountId();
			
			var properties = new AuthByPasswordProperties();
			
			properties.changePassword(password);
			
			this.type = AuthType.PASSWORD_AUTHENTICATION;
			this.properties = properties;
			
			this.accountId = accountId;
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
