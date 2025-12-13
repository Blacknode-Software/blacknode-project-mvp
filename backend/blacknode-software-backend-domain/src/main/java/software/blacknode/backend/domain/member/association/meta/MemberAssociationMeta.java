package software.blacknode.backend.domain.member.association.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class MemberAssociationMeta {

	@Builder.Default
	private Scope scope = Scope.UNKNOWN;
	
	public enum Scope {
		ORGANIZATION,
		PROJECT,
		CHANNEL,
		UNKNOWN
	}
	
}
