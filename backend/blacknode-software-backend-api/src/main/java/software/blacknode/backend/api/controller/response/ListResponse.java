package software.blacknode.backend.api.controller.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

public abstract class ListResponse<T extends ListResponse<T>> extends BaseResponse<T> {
	@Getter private List<UUID> identifiers = new ArrayList<>();
	
	public void add(UUID identifier) {
		this.identifiers.add(identifier);
	}
	
	public void add(List<UUID> identifiers) {
		this.identifiers.addAll(identifiers);
	}
	
	public int getCount() {
		return identifiers != null ? identifiers.size() : 0;
	}
}
