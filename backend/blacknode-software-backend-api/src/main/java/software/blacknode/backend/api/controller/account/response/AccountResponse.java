package software.blacknode.backend.api.controller.account.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.account.response.content.AccountResponseContent;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;

@Getter
@SuperBuilder
public class AccountResponse extends AccountResponseContent implements ResponseBySetter<AccountResponse> {

	@Getter @Setter private Status status;
	@Getter @Setter private String message;
	
}


