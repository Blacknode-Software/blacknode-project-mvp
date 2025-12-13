package software.blacknode.backend.api.controller.converter;

import software.blacknode.backend.api.controller.response.BaseResponse;

public interface BaseResponseConverter<T, R extends BaseResponse<R>>{
	
	R convert(T source);

}
