package software.blacknode.backend.domain.account.meta.create;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;

@Getter
@Builder
public class AccountInitialAdminCreationMeta implements CreationMeta {
	private String email;
	private String firstName;
	private String lastName;
}
