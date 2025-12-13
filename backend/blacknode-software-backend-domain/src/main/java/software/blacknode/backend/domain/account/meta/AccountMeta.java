package software.blacknode.backend.domain.account.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class AccountMeta {

	@Builder.Default
	private String firstName = "Unknown";
	
	@Builder.Default
	private String lastName = "Unknown";
}
