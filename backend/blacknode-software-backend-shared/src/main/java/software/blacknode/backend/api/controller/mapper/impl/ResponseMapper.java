package software.blacknode.backend.api.controller.mapper.impl;

import software.blacknode.backend.api.controller.mapper.ControllerMapper;
import software.blacknode.backend.api.controller.response.Response;

public interface ResponseMapper<RESULT, RESPONSE extends Response<RESPONSE>> extends ControllerMapper {
	
	RESPONSE toResponse(RESULT result);

}
