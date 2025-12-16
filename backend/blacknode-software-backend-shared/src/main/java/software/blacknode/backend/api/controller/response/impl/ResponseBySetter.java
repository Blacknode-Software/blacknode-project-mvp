package software.blacknode.backend.api.controller.response.impl;

import software.blacknode.backend.api.controller.response.Response;

public interface ResponseBySetter<R extends ResponseBySetter<R>> extends Response<R> {
	
	public void setMessage(String message);
	public void setStatus(Status status);

	@Override
	public default void success(String message) {
		setStatus(Status.SUCCESS);
		setMessage(message);
	}
	
	public default void failure(String message) {
		setStatus(Status.FAILURE);
		setMessage(message);
	}
}
