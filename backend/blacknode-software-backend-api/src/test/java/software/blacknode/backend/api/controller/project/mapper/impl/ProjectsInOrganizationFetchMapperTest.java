package software.blacknode.backend.api.controller.project.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.project.response.ProjectsListResponse;
import software.blacknode.backend.api.controller.response.Response.Status;
import software.blacknode.backend.application.project.usecase.ProjectsInOrganizationFetchUseCase;

/**
 * Test class for {@link ProjectsInOrganizationFetchMapper} to verify that all fields are correctly
 * mapped from {@link ProjectsInOrganizationFetchUseCase.Result} to {@link ProjectsListResponse}.
 */
class ProjectsInOrganizationFetchMapperTest {

	private final ProjectsInOrganizationFetchMapper mapper = Mappers.getMapper(ProjectsInOrganizationFetchMapper.class);

	@Test
	void testToResponse_SingleProjectId() {
		// Arrange
		HUID projectId = HUID.random();
		List<HUID> projectIds = Collections.singletonList(projectId);
		
		ProjectsInOrganizationFetchUseCase.Result result = ProjectsInOrganizationFetchUseCase.Result.builder()
				.projectsIds(projectIds)
				.build();

		// Act
		ProjectsListResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getIds(), "IDs list should not be null");
		assertEquals(1, response.getIds().size(), "Should have one project ID");
		assertTrue(response.getIds().contains(projectId.toUUID()), "Should contain the project UUID");
	}

	@Test
	void testToResponse_MultipleProjectIds() {
		// Arrange
		HUID projectId1 = HUID.random();
		HUID projectId2 = HUID.random();
		HUID projectId3 = HUID.random();
		List<HUID> projectIds = Arrays.asList(projectId1, projectId2, projectId3);
		
		ProjectsInOrganizationFetchUseCase.Result result = ProjectsInOrganizationFetchUseCase.Result.builder()
				.projectsIds(projectIds)
				.build();

		// Act
		ProjectsListResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getIds(), "IDs list should not be null");
		assertEquals(3, response.getIds().size(), "Should have three project IDs");
		assertTrue(response.getIds().contains(projectId1.toUUID()), "Should contain first project UUID");
		assertTrue(response.getIds().contains(projectId2.toUUID()), "Should contain second project UUID");
		assertTrue(response.getIds().contains(projectId3.toUUID()), "Should contain third project UUID");
	}

	@Test
	void testToResponse_EmptyProjectsList() {
		// Arrange
		List<HUID> projectIds = Collections.emptyList();
		
		ProjectsInOrganizationFetchUseCase.Result result = ProjectsInOrganizationFetchUseCase.Result.builder()
				.projectsIds(projectIds)
				.build();

		// Act
		ProjectsListResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getIds(), "IDs list should not be null");
		assertTrue(response.getIds().isEmpty(), "IDs list should be empty");
	}

	@Test
	void testToResponse_WithNullResult() {
		// Act
		ProjectsListResponse response = mapper.toResponse(null);

		// Assert
		assertNull(response, "Response should be null when result is null");
	}

	@Test
	void testToResponse_HUIDToUUIDConversion() {
		// Arrange - Test that HUIDs are properly converted to UUIDs
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		HUID huid1 = HUID.fromUUID(uuid1);
		HUID huid2 = HUID.fromUUID(uuid2);
		List<HUID> projectIds = Arrays.asList(huid1, huid2);
		
		ProjectsInOrganizationFetchUseCase.Result result = ProjectsInOrganizationFetchUseCase.Result.builder()
				.projectsIds(projectIds)
				.build();

		// Act
		ProjectsListResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getIds(), "IDs list should not be null");
		assertEquals(2, response.getIds().size(), "Should have two project IDs");
		assertTrue(response.getIds().contains(uuid1), "Should contain first UUID");
		assertTrue(response.getIds().contains(uuid2), "Should contain second UUID");
	}

	@Test
	void testToResponse_ResponseExtendsListResponse() {
		// Arrange
		List<HUID> projectIds = Arrays.asList(HUID.random(), HUID.random());
		
		ProjectsInOrganizationFetchUseCase.Result result = ProjectsInOrganizationFetchUseCase.Result.builder()
				.projectsIds(projectIds)
				.build();

		// Act
		ProjectsListResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getIds(), "IDs should be mapped");
		assertNotNull(response.getStatus(), "Status from BaseResponse should be accessible");
		assertNotNull(response.getMessage(), "Message from BaseResponse should be accessible");
	}

	@Test
	void testToResponse_LargeNumberOfProjects() {
		// Arrange - Test with many projects
		List<HUID> projectIds = Arrays.asList(
			HUID.random(), HUID.random(), HUID.random(), HUID.random(), HUID.random(),
			HUID.random(), HUID.random(), HUID.random(), HUID.random(), HUID.random()
		);
		
		ProjectsInOrganizationFetchUseCase.Result result = ProjectsInOrganizationFetchUseCase.Result.builder()
				.projectsIds(projectIds)
				.build();

		// Act
		ProjectsListResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getIds(), "IDs list should not be null");
		assertEquals(10, response.getIds().size(), "Should have ten project IDs");
		
		// Verify all IDs are converted
		for (int i = 0; i < projectIds.size(); i++) {
			assertTrue(response.getIds().contains(projectIds.get(i).toUUID()), 
					"Should contain project UUID at index " + i);
		}
	}

	@Test
	void testToResponse_OrderIsPreserved() {
		// Arrange - Test that order of IDs is preserved
		HUID projectId1 = HUID.random();
		HUID projectId2 = HUID.random();
		HUID projectId3 = HUID.random();
		List<HUID> projectIds = Arrays.asList(projectId1, projectId2, projectId3);
		
		ProjectsInOrganizationFetchUseCase.Result result = ProjectsInOrganizationFetchUseCase.Result.builder()
				.projectsIds(projectIds)
				.build();

		// Act
		ProjectsListResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getIds(), "IDs list should not be null");
		assertEquals(3, response.getIds().size(), "Should have three project IDs");
		assertEquals(projectId1.toUUID(), response.getIds().get(0), "First ID should match");
		assertEquals(projectId2.toUUID(), response.getIds().get(1), "Second ID should match");
		assertEquals(projectId3.toUUID(), response.getIds().get(2), "Third ID should match");
	}
}
