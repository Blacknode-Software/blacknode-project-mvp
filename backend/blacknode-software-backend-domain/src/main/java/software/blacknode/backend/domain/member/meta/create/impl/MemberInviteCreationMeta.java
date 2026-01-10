package software.blacknode.backend.domain.member.meta.create.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.member.meta.create.MemberCreationMeta;

@Getter
@Builder
@ToString
public class MemberInviteCreationMeta implements MemberCreationMeta{

	private final HUID accountId;

}
