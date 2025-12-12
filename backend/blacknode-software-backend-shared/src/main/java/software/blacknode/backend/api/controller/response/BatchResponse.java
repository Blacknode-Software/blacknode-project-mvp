package software.blacknode.backend.api.controller.response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;

public abstract class BatchResponse<R extends BatchResponse<R, T>, T> extends BaseResponse<R> {
	@Getter private Map<UUID, T> results = new HashMap<>();
	
	public void add(UUID id, T result) {
		this.results.put(id, result);
	}
	
	public void add(Map<UUID, T> results) {
		this.results.putAll(results);
	}
	
	public int getCount() {
		return this.results.size();
	}
}
