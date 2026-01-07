package software.blacknode.backend.api.controller.member.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class MemberResponseContent {

	private UUID id;
	
}
