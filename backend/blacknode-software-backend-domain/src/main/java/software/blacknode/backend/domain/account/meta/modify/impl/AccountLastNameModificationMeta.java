package software.blacknode.backend.domain.account.meta.modify.impl;

import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import software.blacknode.backend.domain.account.meta.modify.AccountModificationMeta;

@Getter
@Builder
@ToString
public class AccountLastNameModificationMeta implements AccountModificationMeta {

	private final String lastName;
	
	@Override
	public Optional<String> getLastName() {
		return Optional.ofNullable(lastName);
	}
	
}
