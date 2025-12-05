package software.blacknode.backend.api.controller.converter;

import software.blacknode.backend.api.controller.request.BaseRequest;
import software.blacknode.backend.application.command.ExecutionCommand;

public interface BaseRequestConverter<R extends BaseRequest, C extends ExecutionCommand> {
	
	C convert(R request);
	
}
