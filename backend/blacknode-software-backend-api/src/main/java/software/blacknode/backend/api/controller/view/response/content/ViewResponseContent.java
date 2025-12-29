package software.blacknode.backend.api.controller.view.response.content;

import java.util.UUID;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ViewResponseContent {

	private UUID id;
	
	private String name;
	
	private String type;
	
}
