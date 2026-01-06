package software.blacknode.backend.domain.member.meta.create.impl;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.meta.create.MemberCreationMeta;

@Getter
@Builder
public class MemberInitialAdminCreationMeta implements MemberCreationMeta {

	private final HUID accountId;
	
}
