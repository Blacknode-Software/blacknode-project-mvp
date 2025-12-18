package software.blacknode.backend.domain.member.association.meta.create;

import lombok.Builder;
import lombok.Getter;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;

@Getter
@Builder
public class MemberChannelAssociationCreationMeta implements CreationMeta {

	private HUID memberId;
	private HUID roleId;
	private HUID channelId;
	
}
