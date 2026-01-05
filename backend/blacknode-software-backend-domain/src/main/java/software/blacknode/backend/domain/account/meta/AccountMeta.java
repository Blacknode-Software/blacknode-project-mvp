package software.blacknode.backend.domain.account.meta;

import lombok.Builder;
import lombok.Getter;
import lombok.With;

@Getter
@With
@Builder
public class AccountMeta {

	private String firstName;
	private String lastName;
}
