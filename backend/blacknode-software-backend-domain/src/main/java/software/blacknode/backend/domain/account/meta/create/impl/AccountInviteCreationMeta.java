package software.blacknode.backend.domain.account.meta.create.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import software.blacknode.backend.domain.account.meta.create.AccountCreationMeta;

@Getter
@Builder
public class AccountInviteCreationMeta implements AccountCreationMeta {

	private final String email;

	private final String firstName;
	private final String lastName;
	
	public Optional<String> getFirstName() {
		return Optional.ofNullable(firstName);
	}
	
	public Optional<String> getLastName() {
		return Optional.ofNullable(lastName);
	}
	
}
