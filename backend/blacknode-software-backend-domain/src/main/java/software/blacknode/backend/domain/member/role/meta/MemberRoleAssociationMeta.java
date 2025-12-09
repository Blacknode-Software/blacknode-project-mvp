package software.blacknode.backend.domain.member.role.meta;

import lombok.Builder;
import lombok.With;

@Builder
@With
public class MemberRoleAssociationMeta {

	private Scope scope;
	
	public enum Scope {
		ORGANIZATION,
		PROJECT,
		CHANNEL
	}
	
}
