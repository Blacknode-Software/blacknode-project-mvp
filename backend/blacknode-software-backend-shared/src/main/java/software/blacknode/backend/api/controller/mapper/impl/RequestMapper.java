package software.blacknode.backend.api.controller.mapper.impl;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.request.BaseRequest;
import software.blacknode.backend.application.command.ExecutionCommand;

public interface RequestMapper<REQUEST extends BaseRequest, COMMAND extends ExecutionCommand> extends ControllerMapper {

	COMMAND toCommand(REQUEST request);
	
}
