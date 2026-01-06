package software.blacknode.backend.domain.member.meta.create;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

public interface MemberCreationMeta extends CreationMeta {

	HUID getAccountId();
	
}
