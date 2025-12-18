package software.blacknode.backend.api.controller.converter;

import software.blacknode.backend.api.controller.request.BaseRequest;
import software.blacknode.backend.application.command.ExecutionCommand;

public interface BaseRequestConverter<INPUT extends BaseRequest, OUTPUT extends ExecutionCommand> {
	
	OUTPUT convert(INPUT request);
	
}
