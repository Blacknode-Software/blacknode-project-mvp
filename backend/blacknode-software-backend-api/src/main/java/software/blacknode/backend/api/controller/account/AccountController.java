package software.blacknode.backend.api.controller.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import software.blacknode.backend.api.controller.BaseController;
import software.blacknode.backend.api.controller.account.mapper.impl.AccountFetchMapper;
import software.blacknode.backend.api.controller.account.mapper.impl.AccountPatchMapper;
import software.blacknode.backend.api.controller.account.request.AccountPatchRequest;
import software.blacknode.backend.api.controller.account.response.AccountPatchResponse;
import software.blacknode.backend.api.controller.account.response.AccountResponse;
import software.blacknode.backend.api.controller.annotation.BearerAuth;
import software.blacknode.backend.api.controller.annotation.DisplayPatchOperations;
import software.blacknode.backend.application.account.command.AccountFetchCommand;
import software.blacknode.backend.application.account.usecase.AccountFetchUseCase;
import software.blacknode.backend.application.account.usecase.AccountPatchUseCase;
import software.blacknode.backend.application.account.usecase.AccountPatchUseCase.AccountPatchOperation;

@RestController
@RequiredArgsConstructor
@Tag(name = "Account", description = "Account management APIs")
public class AccountController extends BaseController {

	private final AccountFetchMapper accountFetchMapper;
	private final AccountFetchUseCase accountFetchUseCase;
	
	private final AccountPatchMapper accountPatchMapper;
	private final AccountPatchUseCase accountPatchUseCase;
	
	@Operation(summary = "Get account details")
	@ApiResponse(responseCode = "200", description = "Account details")
	@GetMapping("/account")
	public ResponseEntity<AccountResponse> getAccount() {
		var command = AccountFetchCommand.builder().build();
		
		var result = accountFetchUseCase.execute(command);
		
		var response = accountFetchMapper.toResponse(result);
		
		return response.toOkResponse("Account fetched successfully.");
	}
	
	@Operation(summary = "Update account details", description = "Update account details.")
	@ApiResponse(responseCode = "200", description = "Updated account details")
	@DisplayPatchOperations(AccountPatchOperation.class)
	@PatchMapping("/account")
	public ResponseEntity<AccountPatchResponse> patchAccount(@RequestBody AccountPatchRequest request) {
		var command = accountPatchMapper.toCommand(request);
		
		var result = accountPatchUseCase.execute(command);
		
		var response = accountPatchMapper.toResponse(result);
		
		return response.toOkResponse("Account updated successfully.");
	}
	
	
}
