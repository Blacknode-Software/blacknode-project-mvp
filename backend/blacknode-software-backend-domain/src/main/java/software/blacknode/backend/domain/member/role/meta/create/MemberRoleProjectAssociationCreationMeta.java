package software.blacknode.backend.domain.member.role.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Getter
@Builder
public class MemberRoleProjectAssociationCreationMeta implements CreationMeta {

	private HUID memberId;
	private HUID roleId;
	private HUID projectId;
	
}
