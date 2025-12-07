package software.blacknode.backend.domain.member.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Getter
@Builder
public class MemberAdminCreationMeta implements CreationMeta {

	private HUID accountId;
	private HUID organizationId;
	
}
