package software.blacknode.backend.api.controller.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public abstract class BatchFetchRequest extends BaseRequest {
	private List<UUID> ids = new ArrayList<>();
}
