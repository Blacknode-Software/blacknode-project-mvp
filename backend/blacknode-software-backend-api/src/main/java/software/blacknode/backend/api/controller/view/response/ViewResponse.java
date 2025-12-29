package software.blacknode.backend.api.controller.view.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import software.blacknode.backend.api.controller.response.impl.ResponseBySetter;
import software.blacknode.backend.api.controller.view.response.content.ViewResponseContent;

@SuperBuilder
public class ViewResponse extends ViewResponseContent implements ResponseBySetter<ViewResponse> {
	
	@Getter @Setter private Status status;
	@Getter @Setter private String message;

}
