package software.blacknode.backend.api.controller.project.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import software.blacknode.backend.api.controller.request.PatchRequest;

@Getter
@NoArgsConstructor
@ToString
public class ProjectPatchRequest extends PatchRequest {
	
	private String name;
	private String description;
	private String color;
	
}