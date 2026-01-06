package software.blacknode.backend.api.controller.account.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AccountResponseContent {

	private UUID id;
	private String firstName;
	private String lastName;
	
}
