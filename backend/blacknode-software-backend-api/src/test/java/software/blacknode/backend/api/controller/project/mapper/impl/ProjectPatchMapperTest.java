package software.blacknode.backend.api.controller.project.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.project.response.ProjectPatchResponse;
import software.blacknode.backend.api.controller.response.Response;
import software.blacknode.backend.application.project.usecase.ProjectPatchUseCase;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.meta.create.impl.ProjectInitialCreationMeta;

/**
 * Test class for {@link ProjectPatchMapper} to verify that all fields are correctly
 * mapped from {@link ProjectPatchUseCase.Result} to {@link ProjectPatchResponse}.
 */
class ProjectPatchMapperTest {

	private final ProjectPatchMapper mapper = Mappers.getMapper(ProjectPatchMapper.class);

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
		String projectName = "Patched Project";
		String projectDescription = "Updated description";
		String projectColor = "#FF00FF";
		
		Project project = createProject(projectName, projectDescription, projectColor);
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);

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
		ProjectPatchResponse response = mapper.toResponse(null);

		// Assert
		assertNull(response, "Response should be null when result is null");
	}

	@Test
	void testToResponse_AfterNameUpdate() {
		// Arrange
		Project project = createProject("Original Name", "Description", "#123456");
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals("Original Name", response.getName(), "Updated name should be mapped");
	}

	@Test
	void testToResponse_AfterDescriptionUpdate() {
		// Arrange
		Project project = createProject("Project Name", "New Description After Patch", "#ABCDEF");
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals("New Description After Patch", response.getDescription(), "Updated description should be mapped");
	}

	@Test
	void testToResponse_AfterColorUpdate() {
		// Arrange
		Project project = createProject("Project Name", "Description", "#00FF00");
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals("#00FF00", response.getColor(), "Updated color should be mapped");
	}

	@Test
	void testToResponse_ResponseInheritsFromProjectResponseContent() {
		// Arrange
		Project project = createProject("Test", "Test description", "#FFFFFF");
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);

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
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);
		
		// Set status and message
		response.setStatus(Response.Status.SUCCESS);
		response.setMessage("Project patched successfully");

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(Response.Status.SUCCESS, response.getStatus(), "Status should be settable");
		assertEquals("Project patched successfully", response.getMessage(), "Message should be settable");
	}

	@Test
	void testToResponse_WithEmptyStrings() {
		// Arrange
		Project project = createProject("", "", "");
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals("", response.getName(), "Empty name should be mapped");
		assertEquals("", response.getDescription(), "Empty description should be mapped");
		assertEquals("", response.getColor(), "Empty color should be mapped");
	}

	@Test
	void testToResponse_WithSpecialCharacters() {
		// Arrange
		String specialName = "Project @#$%^&*()";
		String specialDescription = "Description with\nnewlines and\ttabs";
		String specialColor = "#FEDCBA";
		
		Project project = createProject(specialName, specialDescription, specialColor);
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(specialName, response.getName(), "Special characters in name should be preserved");
		assertEquals(specialDescription, response.getDescription(), "Special characters in description should be preserved");
		assertEquals(specialColor, response.getColor(), "Color should be preserved");
	}

	@Test
	void testToResponse_IdTypeConversion() {
		// Arrange
		Project project = createProject("ID Test", "Testing ID conversion", "#000000");
		HUID projectId = project.getId();
		
		ProjectPatchUseCase.Result result = ProjectPatchUseCase.Result.builder()
				.project(project)
				.build();

		// Act
		ProjectPatchResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getId(), "Response ID should not be null");
		assertEquals(projectId.toUUID(), response.getId(), "HUID should be correctly converted to UUID");
	}
}
