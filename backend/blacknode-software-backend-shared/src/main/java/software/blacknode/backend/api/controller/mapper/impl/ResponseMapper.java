package software.blacknode.backend.api.controller.mapper.impl;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;

public interface ResponseMapper<RESULT, RESPONSE> extends ControllerMapper {
	
	RESPONSE toResponse(RESULT result);

}
