package software.blacknode.backend.domain.account.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.domain.account.meta.modify.AccountModificationMeta;

@Getter
@Builder
@ToString
public class AccountFirstNameModificationMeta implements AccountModificationMeta {

	private final String firstName;
	
	@Override
	public Optional<String> getFirstName() {
		return Optional.ofNullable(firstName);
	}
	
}
