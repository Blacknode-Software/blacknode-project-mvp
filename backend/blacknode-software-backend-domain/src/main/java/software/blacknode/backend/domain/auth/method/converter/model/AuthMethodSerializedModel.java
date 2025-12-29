package software.blacknode.backend.domain.auth.method.converter.model;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.exception.BlacknodeException;

@Getter
@AllArgsConstructor
public class AuthMethodSerializedModel {

	private HUID id;
	
	private JsonNode properties;
	
	public void ensureAuthMethodType(HUID id) {
		if(!this.id.equals(id)) {
			throw new BlacknodeException("Auth method type mismatch: expected " + id + " but found " + this.id);
		}
	}
	
	public boolean isAuthMethodType(HUID id) {
		return this.id.equals(id);
	}
	
}
