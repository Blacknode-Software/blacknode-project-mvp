package software.blacknode.backend.api.controller.project.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.project.response.content.ProjectResponseContent;
import software.blacknode.backend.domain.project.Project;
import software.blacknode.backend.domain.project.meta.ProjectMeta;
import software.blacknode.backend.domain.project.meta.create.ProjectInitialCreationMeta;

/**
 * Test class for {@link ProjectMapper} to verify that all fields are correctly
 * mapped from {@link Project} to {@link ProjectResponseContent}.
 */
class ProjectMapperTest {

	private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

	/**
	 * Helper method to create a Project instance with specific meta values for testing.
	 * Uses the proper creation lifecycle.
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

	/**
	 * Helper method to set the ID on a Project using reflection for testing purposes.
	 */
	private void setProjectId(Project project, HUID id) {
		try {
			Field idField = Project.class.getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(project, id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to set project ID", e);
		}
	}

	@Test
	void testToResponseContent_AllFieldsAreMappedCorrectly() {
		// Arrange
		String projectName = "Test Project";
		String projectDescription = "This is a test project description";
		String projectColor = "#FF5733";

		Project project = createProject(projectName, projectDescription, projectColor);
		HUID projectId = project.getId();

		// Act
		ProjectResponseContent responseContent = mapper.toResponseContent(project);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertNotNull(responseContent.getId(), "ID should not be null");
		assertEquals(projectId.toUUID(), responseContent.getId(), "ID should be correctly mapped");
		assertEquals(projectName, responseContent.getName(), "Name should be correctly mapped");
		assertEquals(projectDescription, responseContent.getDescription(), "Description should be correctly mapped");
		assertEquals(projectColor, responseContent.getColor(), "Color should be correctly mapped");
	}

	@Test
	void testToResponseContent_WithDefaultValues() {
		// Arrange - Using default values from ProjectMeta
		Project project = createProject("Unknown Project", "Unknown description", "#FAFAFA");
		HUID projectId = project.getId();

		// Act
		ProjectResponseContent responseContent = mapper.toResponseContent(project);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(projectId.toUUID(), responseContent.getId(), "ID should be correctly mapped");
		assertEquals("Unknown Project", responseContent.getName(), "Default name should be mapped");
		assertEquals("Unknown description", responseContent.getDescription(), "Default description should be mapped");
		assertEquals("#FAFAFA", responseContent.getColor(), "Default color should be mapped");
	}

	@Test
	void testToResponseContent_WithNullProject() {
		// Act
		ProjectResponseContent responseContent = mapper.toResponseContent(null);

		// Assert
		assertNull(responseContent, "Response content should be null when project is null");
	}

	@Test
	void testToResponseContent_WithDifferentColorFormats() {
		// Arrange
		String hexColor = "#123ABC";
		Project project = createProject("Color Test Project", "Testing color mapping", hexColor);

		// Act
		ProjectResponseContent responseContent = mapper.toResponseContent(project);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(hexColor, responseContent.getColor(), "Color format should be preserved");
	}

	@Test
	void testToResponseContent_WithEmptyStrings() {
		// Arrange
		Project project = createProject("", "", "");
		HUID projectId = project.getId();

		// Act
		ProjectResponseContent responseContent = mapper.toResponseContent(project);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(projectId.toUUID(), responseContent.getId(), "ID should be correctly mapped");
		assertEquals("", responseContent.getName(), "Empty name should be mapped");
		assertEquals("", responseContent.getDescription(), "Empty description should be mapped");
		assertEquals("", responseContent.getColor(), "Empty color should be mapped");
	}

	@Test
	void testToResponseContent_WithSpecialCharactersInStrings() {
		// Arrange
		String specialName = "Test Project @#$%^&*()";
		String specialDescription = "Description with\nnewlines and\ttabs";
		String specialColor = "#ABCDEF";
		
		Project project = createProject(specialName, specialDescription, specialColor);

		// Act
		ProjectResponseContent responseContent = mapper.toResponseContent(project);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(specialName, responseContent.getName(), "Name with special characters should be preserved");
		assertEquals(specialDescription, responseContent.getDescription(), "Description with special characters should be preserved");
		assertEquals(specialColor, responseContent.getColor(), "Color should be correctly mapped");
	}

	@Test
	void testToResponseContent_IdTypeConversion() {
		// Arrange - Testing that HUID is correctly converted to UUID
		UUID uuid = UUID.randomUUID();
		HUID projectId = HUID.fromUUID(uuid);
		
		Project project = createProject("ID Test Project", "Testing ID conversion", "#000000");
		setProjectId(project, projectId);

		// Act
		ProjectResponseContent responseContent = mapper.toResponseContent(project);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertNotNull(responseContent.getId(), "ID should not be null");
		assertEquals(uuid, responseContent.getId(), "UUID should be correctly extracted from HUID");
	}

	@Test
	void testToResponseContent_NestedMetaFieldsAreMapped() {
		// Arrange - Explicitly testing that nested meta fields are accessible
		String name = "Nested Test";
		String description = "Testing nested meta mapping";
		String color = "#FFFFFF";
		
		Project project = createProject(name, description, color);
		ProjectMeta meta = project.getMeta();

		// Act
		ProjectResponseContent responseContent = mapper.toResponseContent(project);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		// Verify all nested fields from meta are correctly mapped
		assertEquals(meta.getName(), responseContent.getName(), "Nested meta.name should be mapped");
		assertEquals(meta.getDescription(), responseContent.getDescription(), "Nested meta.description should be mapped");
		assertEquals(meta.getColor(), responseContent.getColor(), "Nested meta.color should be mapped");
	}
}
