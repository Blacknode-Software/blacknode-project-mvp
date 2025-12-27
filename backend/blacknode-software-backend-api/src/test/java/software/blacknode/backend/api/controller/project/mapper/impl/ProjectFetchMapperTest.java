package software.blacknode.backend.api.controller.project.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.project.response.ProjectResponse;
import software.blacknode.backend.api.controller.response.Response;
import software.blacknode.backend.application.project.usecase.ProjectFetchUseCase;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.meta.create.impl.ProjectInitialCreationMeta;

/**
 * Test class for {@link ProjectFetchMapper} to verify that all fields are correctly
 * mapped from {@link ProjectFetchUseCase.Result} to {@link ProjectResponse}.
 */
class ProjectFetchMapperTest {

	private final ProjectFetchMapper mapper = Mappers.getMapper(ProjectFetchMapper.class);

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
	void testToResponse_AllFieldsAreMappedCorrectly() {
		// Arrange
		String projectName = "Fetch Test Project";
		String projectDescription = "Testing project fetch response mapping";
		String projectColor = "#00FF00";
		
		Project project = createProject(projectName, projectDescription, projectColor);
		
		ProjectFetchUseCase.Result result = ProjectFetchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getId(), "ID should not be null");
		assertEquals(project.getId().toUUID(), response.getId(), "ID should be correctly mapped");
		assertEquals(projectName, response.getName(), "Name should be correctly mapped");
		assertEquals(projectDescription, response.getDescription(), "Description should be correctly mapped");
		assertEquals(projectColor, response.getColor(), "Color should be correctly mapped");
	}

	@Test
	void testToResponse_WithNullResult() {
		// Act
		ProjectResponse response = mapper.toResponse(null);

		// Assert
		assertNull(response, "Response should be null when result is null");
	}

	@Test
	void testToResponse_ProjectWithAllFields() {
		// Arrange
		Project project = createProject("Complete Project", "Full description", "#ABCDEF");
		HUID projectId = project.getId();
		
		ProjectFetchUseCase.Result result = ProjectFetchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(projectId.toUUID(), response.getId(), "Project ID should be mapped");
		assertEquals("Complete Project", response.getName(), "Project name should be mapped");
		assertEquals("Full description", response.getDescription(), "Project description should be mapped");
		assertEquals("#ABCDEF", response.getColor(), "Project color should be mapped");
	}

	@Test
	void testToResponse_ProjectWithSpecialCharacters() {
		// Arrange
		String specialName = "Project @#$%";
		String specialDescription = "Description\nwith\nnewlines";
		Project project = createProject(specialName, specialDescription, "#000000");
		
		ProjectFetchUseCase.Result result = ProjectFetchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(specialName, response.getName(), "Special characters in name should be preserved");
		assertEquals(specialDescription, response.getDescription(), "Special characters in description should be preserved");
	}

	@Test
	void testToResponse_ResponseInheritsFromProjectResponseContent() {
		// Arrange
		Project project = createProject("Test", "Test description", "#FFFFFF");
		
		ProjectFetchUseCase.Result result = ProjectFetchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		// Verify that response extends ProjectResponseContent and has its fields
		assertNotNull(response.getId(), "ID from ProjectResponseContent should be accessible");
		assertNotNull(response.getName(), "Name from ProjectResponseContent should be accessible");
		assertNotNull(response.getDescription(), "Description from ProjectResponseContent should be accessible");
		assertNotNull(response.getColor(), "Color from ProjectResponseContent should be accessible");
	}

	@Test
	void testToResponse_ResponseHasStatusAndMessage() {
		// Arrange
		Project project = createProject("Status Test", "Testing status", "#123456");
		
		ProjectFetchUseCase.Result result = ProjectFetchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectResponse response = mapper.toResponse(result);
		
		// Set status and message (these are set by the controller layer)
		response.setStatus(Response.Status.SUCCESS);
		response.setMessage("Project fetched successfully");

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(Response.Status.SUCCESS, response.getStatus(), "Status should be settable");
		assertEquals("Project fetched successfully", response.getMessage(), "Message should be settable");
	}

	@Test
	void testToResponse_IdTypeConversion() {
		// Arrange
		Project project = createProject("ID Test", "Testing ID conversion", "#FEDCBA");
		HUID projectId = project.getId();
		
		ProjectFetchUseCase.Result result = ProjectFetchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getId(), "Response ID should not be null");
		assertEquals(projectId.toUUID(), response.getId(), "HUID should be correctly converted to UUID");
	}
}
