package software.blacknode.backend.domain.auth.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Getter
@Builder
public class AuthByPasswordCreationMeta implements CreationMeta {

	private HUID accountId;
	private String password;
	
}
