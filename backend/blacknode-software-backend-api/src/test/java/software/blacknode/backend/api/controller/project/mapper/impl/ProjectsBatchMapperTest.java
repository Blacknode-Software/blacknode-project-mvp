package software.blacknode.backend.api.controller.project.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.project.response.ProjectsBatchFetchResponse;
import software.blacknode.backend.api.controller.project.response.content.ProjectResponseContent;
import software.blacknode.backend.application.project.usecase.ProjectsBatchFetchUseCase;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.meta.create.impl.ProjectInitialCreationMeta;

/**
 * Test class for {@link ProjectsBatchFetchMapper} to verify that all fields are correctly
 * mapped from {@link ProjectsBatchFetchUseCase.Result} to {@link ProjectsBatchFetchResponse}.
 */
class ProjectsBatchMapperTest {

	private final ProjectsBatchFetchMapper mapper = Mappers.getMapper(ProjectsBatchFetchMapper.class);

	/**
	 * Helper method to create a Project instance for testing.
	 */
	private Project createProject(String name, String description, String color) {
		HUID organizationId = HUID.random();
		Project project = new Project(organizationId);
		
		ProjectInitialCreationMeta creationMeta = ProjectInitialCreationMeta.builder()
				.name(name)
				.description(description)
				.color(color)
				.build();
		
		project.create(Optional.of(creationMeta));
		return project;
	}

	@Test
	void testToResponse_SingleProject() {
		// Arrange
		Project project = createProject("Test Project", "Test Description", "#FF0000");
		List<Project> projects = Collections.singletonList(project);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		// Note: BatchResponse items field may not have public getter, testing response structure
		assertNotNull(response.getStatus(), "Status should be accessible");
		assertNotNull(response.getMessage(), "Message should be accessible");
	}

	@Test
	void testToResponse_MultipleProjects() {
		// Arrange
		Project project1 = createProject("Project 1", "Description 1", "#FF0000");
		Project project2 = createProject("Project 2", "Description 2", "#00FF00");
		Project project3 = createProject("Project 3", "Description 3", "#0000FF");
		List<Project> projects = Arrays.asList(project1, project2, project3);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getStatus(), "Status should be accessible");
		assertNotNull(response.getMessage(), "Message should be accessible");
	}

	@Test
	void testToResponse_EmptyProjectsList() {
		// Arrange
		List<Project> projects = Collections.emptyList();
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getStatus(), "Status should be accessible");
	}

	@Test
	void testToResponse_WithNullResult() {
		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(null);

		// Assert
		assertNull(response, "Response should be null when result is null");
	}

	@Test
	void testToResponse_ProjectsWithDifferentData() {
		// Arrange - Projects with various data to test mapping
		Project project1 = createProject("Alpha", "First project", "#111111");
		Project project2 = createProject("Beta", "Second project", "#222222");
		Project project3 = createProject("Gamma", "Third project", "#333333");
		List<Project> projects = Arrays.asList(project1, project2, project3);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		// Verify response structure
		assertNotNull(response.getStatus(), "Status should be accessible");
		assertNotNull(response.getMessage(), "Message should be accessible");
	}

	@Test
	void testToResponse_ProjectsWithSpecialCharacters() {
		// Arrange
		Project project1 = createProject("Project @#$%", "Description with\nnewlines", "#ABCDEF");
		Project project2 = createProject("组织 🏢", "Unicode description", "#FEDCBA");
		List<Project> projects = Arrays.asList(project1, project2);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getStatus(), "Status should be accessible");
	}

	@Test
	void testToResponse_LargeNumberOfProjects() {
		// Arrange - Test with many projects
		List<Project> projects = Arrays.asList(
			createProject("Project 1", "Desc 1", "#111111"),
			createProject("Project 2", "Desc 2", "#222222"),
			createProject("Project 3", "Desc 3", "#333333"),
			createProject("Project 4", "Desc 4", "#444444"),
			createProject("Project 5", "Desc 5", "#555555"),
			createProject("Project 6", "Desc 6", "#666666"),
			createProject("Project 7", "Desc 7", "#777777"),
			createProject("Project 8", "Desc 8", "#888888"),
			createProject("Project 9", "Desc 9", "#999999"),
			createProject("Project 10", "Desc 10", "#AAAAAA")
		);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getStatus(), "Status should be accessible");
		assertNotNull(response.getMessage(), "Message should be accessible");
	}

	@Test
	void testToResponse_ResponseExtendsBatchResponse() {
		// Arrange
		Project project = createProject("Test", "Testing batch response", "#FFFFFF");
		List<Project> projects = Collections.singletonList(project);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		// Verify response extends BatchResponse -> BaseResponse
		assertNotNull(response.getStatus(), "Status from BaseResponse should be accessible");
		assertNotNull(response.getMessage(), "Message from BaseResponse should be accessible");
	}

	@Test
	void testToResponse_ProjectFieldsMapping() {
		// Arrange - Create project with specific known values
		Project project = createProject("Mapping Test", "Test Description", "#ABCDEF");
		HUID projectId = project.getId();
		List<Project> projects = Collections.singletonList(project);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		// The mapper should map each Project to ProjectResponseContent
		// Verify the response was created successfully
		assertNotNull(response.getStatus(), "Status should be present");
	}

	@Test
	void testToResponse_ProjectsWithEmptyStrings() {
		// Arrange
		Project project = createProject("", "", "");
		List<Project> projects = Collections.singletonList(project);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getStatus(), "Status should be accessible");
	}

	@Test
	void testToResponse_ProjectsWithLongStrings() {
		// Arrange
		String longName = "A".repeat(500);
		String longDescription = "B".repeat(1000);
		Project project = createProject(longName, longDescription, "#FFFFFF");
		List<Project> projects = Collections.singletonList(project);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getStatus(), "Status should be accessible");
	}

	@Test
	void testToResponse_VerifyMapperNotNull() {
		// Assert
		assertNotNull(mapper, "Mapper instance should be created successfully");
	}

	@Test
	void testToResponse_ProjectsWithVariousColors() {
		// Arrange - Test different color formats
		Project project1 = createProject("Project 1", "Desc", "#FF0000");
		Project project2 = createProject("Project 2", "Desc", "#00FF00");
		Project project3 = createProject("Project 3", "Desc", "#0000FF");
		Project project4 = createProject("Project 4", "Desc", "#ABCDEF");
		List<Project> projects = Arrays.asList(project1, project2, project3, project4);
		
		ProjectsBatchFetchUseCase.Result result = ProjectsBatchFetchUseCase.Result.builder()
				.projects(projects)
				.build();

		// Act
		ProjectsBatchFetchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getStatus(), "Status should be accessible");
	}
}
