package software.blacknode.backend.api.controller.organization.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.organization.response.content.OrganizationResponseContent;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.organization.meta.create.OrganizationInitialCreationMeta;

/**
 * Test class for {@link OrganizationMapper} to verify that all fields are correctly
 * mapped from {@link Organization} to {@link OrganizationResponseContent}.
 */
class OrganizationMapperTest {

	private final OrganizationMapper mapper = Mappers.getMapper(OrganizationMapper.class);

	/**
	 * Helper method to create an Organization instance with specific meta values for testing.
	 * Uses the proper creation lifecycle.
	 */
	private Organization createOrganization(String name) {
		Organization organization = new Organization();
		
		OrganizationInitialCreationMeta creationMeta = OrganizationInitialCreationMeta.builder()
				.name(name)
				.build();
		
		organization.create(Optional.of(creationMeta));
		return organization;
	}

	/**
	 * Helper method to set the ID on an Organization using reflection for testing purposes.
	 */
	private void setOrganizationId(Organization organization, HUID id) {
		try {
			Field idField = Organization.class.getDeclaredField("id");
			idField.setAccessible(true);
			idField.set(organization, id);
		} catch (Exception e) {
			throw new RuntimeException("Failed to set organization ID", e);
		}
	}

	@Test
	void testToResponseContent_AllFieldsAreMappedCorrectly() {
		// Arrange
		String organizationName = "Test Organization";
		Organization organization = createOrganization(organizationName);
		HUID organizationId = organization.getId();

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertNotNull(responseContent.getId(), "ID should not be null");
		assertEquals(organizationId.toUUID(), responseContent.getId(), "ID should be correctly mapped");
		assertEquals(organizationName, responseContent.getName(), "Name should be correctly mapped");
	}

	@Test
	void testToResponseContent_WithDefaultValues() {
		// Arrange - Using default values from OrganizationMeta
		Organization organization = createOrganization("Unnamed Organization");
		HUID organizationId = organization.getId();

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(organizationId.toUUID(), responseContent.getId(), "ID should be correctly mapped");
		assertEquals("Unnamed Organization", responseContent.getName(), "Default name should be mapped");
	}

	@Test
	void testToResponseContent_WithNullOrganization() {
		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(null);

		// Assert
		assertNull(responseContent, "Response content should be null when organization is null");
	}

	@Test
	void testToResponseContent_WithEmptyString() {
		// Arrange
		Organization organization = createOrganization("");
		HUID organizationId = organization.getId();

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(organizationId.toUUID(), responseContent.getId(), "ID should be correctly mapped");
		assertEquals("", responseContent.getName(), "Empty name should be mapped");
	}

	@Test
	void testToResponseContent_WithSpecialCharactersInName() {
		// Arrange
		String specialName = "Test Organization @#$%^&*()";
		Organization organization = createOrganization(specialName);

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(specialName, responseContent.getName(), "Name with special characters should be preserved");
	}

	@Test
	void testToResponseContent_WithLongName() {
		// Arrange
		String longName = "A".repeat(500); // Very long organization name
		Organization organization = createOrganization(longName);

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(longName, responseContent.getName(), "Long name should be preserved");
		assertEquals(500, responseContent.getName().length(), "Name length should be preserved");
	}

	@Test
	void testToResponseContent_IdTypeConversion() {
		// Arrange - Testing that HUID is correctly converted to UUID
		UUID uuid = UUID.randomUUID();
		HUID organizationId = HUID.fromUUID(uuid);
		
		Organization organization = createOrganization("ID Test Organization");
		setOrganizationId(organization, organizationId);

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertNotNull(responseContent.getId(), "ID should not be null");
		assertEquals(uuid, responseContent.getId(), "UUID should be correctly extracted from HUID");
	}

	@Test
	void testToResponseContent_NestedMetaFieldsAreMapped() {
		// Arrange - Explicitly testing that nested meta fields are accessible
		String name = "Nested Test Organization";
		Organization organization = createOrganization(name);

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		// Verify nested field from meta is correctly mapped
		assertEquals(name, responseContent.getName(), "Nested meta.name should be mapped");
	}

	@Test
	void testToResponseContent_WithUnicodeCharacters() {
		// Arrange
		String unicodeName = "组织名称 🏢 Организация";
		Organization organization = createOrganization(unicodeName);

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(unicodeName, responseContent.getName(), "Unicode characters should be preserved");
	}

	@Test
	void testToResponseContent_WithNewlinesAndTabs() {
		// Arrange
		String nameWithWhitespace = "Organization\nWith\tWhitespace";
		Organization organization = createOrganization(nameWithWhitespace);

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(nameWithWhitespace, responseContent.getName(), "Name with whitespace should be preserved");
	}

	@Test
	void testToResponseContent_WithDefaultOrganizationId() {
		// Arrange - Testing with the default organization ID constant
		HUID organizationId = Organization.DEFAULT_ORGANIZATION_ID;
		String organizationName = "Default Organization";
		
		Organization organization = createOrganization(organizationName);
		setOrganizationId(organization, organizationId);

		// Act
		OrganizationResponseContent responseContent = mapper.toResponseContent(organization);

		// Assert
		assertNotNull(responseContent, "Response content should not be null");
		assertEquals(Organization.DEFAULT_ORGANIZATION_ID.toUUID(), responseContent.getId(), 
				"Default organization ID should be correctly mapped");
		assertEquals(organizationName, responseContent.getName(), "Name should be correctly mapped");
	}
}
