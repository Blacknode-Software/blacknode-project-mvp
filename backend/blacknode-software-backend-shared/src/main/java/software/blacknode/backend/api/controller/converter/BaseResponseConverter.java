package software.blacknode.backend.api.controller.converter;

import software.blacknode.backend.api.controller.response.Response;

public interface BaseResponseConverter<INPUT, OUTPUT extends Response<OUTPUT>>{
	
	OUTPUT convert(INPUT source);

}
