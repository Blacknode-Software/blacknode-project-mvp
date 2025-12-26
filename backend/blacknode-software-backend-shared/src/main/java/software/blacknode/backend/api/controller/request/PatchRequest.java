package software.blacknode.backend.api.controller.request;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public abstract class PatchRequest extends BaseRequest {
	
	private List<String> operations = new ArrayList<>();
	
}