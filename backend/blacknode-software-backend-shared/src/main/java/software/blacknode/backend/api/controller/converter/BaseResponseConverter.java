package software.blacknode.backend.api.controller.converter;

import software.blacknode.backend.api.controller.response.BaseResponse;

public interface BaseResponseConverter<INPUT, OUTPUT extends BaseResponse<OUTPUT>>{
	
	OUTPUT convert(INPUT source);

}
