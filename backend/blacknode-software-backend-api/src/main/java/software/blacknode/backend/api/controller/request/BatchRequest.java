package software.blacknode.backend.api.controller.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class BatchRequest extends BaseRequest {
	private List<UUID> identifiers = new ArrayList<>();
}
