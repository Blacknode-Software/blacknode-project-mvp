package software.blacknode.backend.api.controller.project.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.project.response.ProjectCreateResponse;
import software.blacknode.backend.application.project.usecase.ProjectCreateUseCase;

/**
 * Test class for {@link ProjectCreateMapper} to verify that all fields are correctly
 * mapped from {@link ProjectCreateUseCase.Result} to {@link ProjectCreateResponse}.
 */
class ProjectCreateMapperTest {

	private final ProjectCreateMapper mapper = Mappers.getMapper(ProjectCreateMapper.class);

	@Test
	void testToResponse_ProjectIdIsMappedCorrectly() {
		// Arrange
		HUID projectId = HUID.random();
		
		ProjectCreateUseCase.Result result = ProjectCreateUseCase.Result.builder()
				.projectId(projectId)
				.build();

		// Act
		ProjectCreateResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getProjectId(), "Project ID should not be null");
		assertEquals(projectId.toUUID(), response.getProjectId(), "Project ID should be correctly mapped");
	}

	@Test
	void testToResponse_WithNullResult() {
		// Act
		ProjectCreateResponse response = mapper.toResponse(null);

		// Assert
		assertNull(response, "Response should be null when result is null");
	}

	@Test
	void testToResponse_IdTypeConversion() {
		// Arrange - Testing HUID to UUID conversion
		UUID uuid = UUID.randomUUID();
		HUID projectId = HUID.fromUUID(uuid);
		
		ProjectCreateUseCase.Result result = ProjectCreateUseCase.Result.builder()
				.projectId(projectId)
				.build();

		// Act
		ProjectCreateResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(uuid, response.getProjectId(), "UUID should be correctly extracted from HUID");
	}

	@Test
	void testToResponse_ResponseExtendsBaseResponse() {
		// Arrange
		HUID projectId = HUID.random();
		
		ProjectCreateUseCase.Result result = ProjectCreateUseCase.Result.builder()
				.projectId(projectId)
				.build();

		// Act
		ProjectCreateResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getProjectId(), "ProjectId should be mapped");
		assertNotNull(response.getStatus(), "Status from BaseResponse should be accessible");
		assertNotNull(response.getMessage(), "Message from BaseResponse should be accessible");
	}

	@Test
	void testToResponse_MultipleProjectIds() {
		// Arrange - Test with different project IDs
		HUID projectId1 = HUID.random();
		HUID projectId2 = HUID.random();
		HUID projectId3 = HUID.random();
		
		// Act & Assert - First project
		ProjectCreateUseCase.Result result1 = ProjectCreateUseCase.Result.builder()
				.projectId(projectId1)
				.build();
		ProjectCreateResponse response1 = mapper.toResponse(result1);
		assertNotNull(response1);
		assertEquals(projectId1.toUUID(), response1.getProjectId());
		
		// Act & Assert - Second project
		ProjectCreateUseCase.Result result2 = ProjectCreateUseCase.Result.builder()
				.projectId(projectId2)
				.build();
		ProjectCreateResponse response2 = mapper.toResponse(result2);
		assertNotNull(response2);
		assertEquals(projectId2.toUUID(), response2.getProjectId());
		
		// Act & Assert - Third project
		ProjectCreateUseCase.Result result3 = ProjectCreateUseCase.Result.builder()
				.projectId(projectId3)
				.build();
		ProjectCreateResponse response3 = mapper.toResponse(result3);
		assertNotNull(response3);
		assertEquals(projectId3.toUUID(), response3.getProjectId());
	}

	@Test
	void testToResponse_ProjectIdIsOnlyField() {
		// Arrange
		HUID projectId = HUID.random();
		
		ProjectCreateUseCase.Result result = ProjectCreateUseCase.Result.builder()
				.projectId(projectId)
				.build();

		// Act
		ProjectCreateResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getProjectId(), "Project ID should be the only mapped field");
		assertEquals(projectId.toUUID(), response.getProjectId(), "Project ID should match");
	}
}
