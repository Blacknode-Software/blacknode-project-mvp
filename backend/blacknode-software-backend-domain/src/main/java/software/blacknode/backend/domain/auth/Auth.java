package software.blacknode.backend.domain.auth;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.auth.meta.AuthMeta;
import software.blacknode.backend.domain.auth.meta.create.AuthByPasswordCreationMeta;
import software.blacknode.backend.domain.auth.properties.AuthProperties;
import software.blacknode.backend.domain.auth.properties.impl.AuthByPasswordProperties;
import software.blacknode.backend.domain.auth.type.AuthType;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public class Auth implements Creatable, Deletable, Modifiable {

	@Getter private HUID id;
	
	@Getter private AuthMeta meta;
	@Getter private AuthType type;
	
	private AuthProperties properties;
	
	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID accountId;

	@Override
	public void create(Optional<CreationMeta> meta0) {
		ensureNotCreated(meta0);
		if(meta0.isEmpty()) throw new BlacknodeException("CreationMeta is required to create an Account");
		
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
			throw new BlacknodeException("Unsupported CreationMeta type for Auth creation");
		}
		
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		// TODO Auto-generated method stub
		
	}
}
