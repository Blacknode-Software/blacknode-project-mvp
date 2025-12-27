package software.blacknode.backend.api.controller.organization.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.api.controller.organization.response.OrganizationResponse;
import software.blacknode.backend.api.controller.response.Response;
import software.blacknode.backend.application.organization.usecase.OrganizationFetchUseCase;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.organization.meta.create.impl.OrganizationInitialCreationMeta;

/**
 * Test class for {@link OrganizationFetchMapper} to verify that all fields are correctly
 * mapped from {@link OrganizationFetchUseCase.Result} to {@link OrganizationResponse}.
 */
class OrganizationFetchMapperTest {

	private final OrganizationFetchMapper mapper = Mappers.getMapper(OrganizationFetchMapper.class);

	/**
	 * Helper method to create an Organization instance for testing.
	 */
	private Organization createOrganization(String name) {
		Organization organization = new Organization();
		
		OrganizationInitialCreationMeta creationMeta = OrganizationInitialCreationMeta.builder()
				.name(name)
				.build();
		
		organization.create(Optional.of(creationMeta));
		return organization;
	}

	@Test
	void testToResponse_AllFieldsAreMappedCorrectly() {
		// Arrange
		String organizationName = "Test Organization";
		Organization organization = createOrganization(organizationName);
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getId(), "ID should not be null");
		assertEquals(organization.getId().toUUID(), response.getId(), "ID should be correctly mapped");
		assertEquals(organizationName, response.getName(), "Name should be correctly mapped");
	}

	@Test
	void testToResponse_WithNullResult() {
		// Act
		OrganizationResponse response = mapper.toResponse(null);

		// Assert
		assertNull(response, "Response should be null when result is null");
	}

	@Test
	void testToResponse_WithDefaultOrganizationName() {
		// Arrange
		Organization organization = createOrganization("Unnamed Organization");
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals("Unnamed Organization", response.getName(), "Default organization name should be mapped");
	}

	@Test
	void testToResponse_WithSpecialCharacters() {
		// Arrange
		String specialName = "Organization @#$%^&*()";
		Organization organization = createOrganization(specialName);
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(specialName, response.getName(), "Special characters in name should be preserved");
	}

	@Test
	void testToResponse_WithUnicodeCharacters() {
		// Arrange
		String unicodeName = "组织名称 🏢 Организация";
		Organization organization = createOrganization(unicodeName);
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(unicodeName, response.getName(), "Unicode characters should be preserved");
	}

	@Test
	void testToResponse_ResponseInheritsFromOrganizationResponseContent() {
		// Arrange
		Organization organization = createOrganization("Test Organization");
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		// Verify that response extends OrganizationResponseContent and has its fields
		assertNotNull(response.getId(), "ID from OrganizationResponseContent should be accessible");
		assertNotNull(response.getName(), "Name from OrganizationResponseContent should be accessible");
	}

	@Test
	void testToResponse_ResponseHasStatusAndMessage() {
		// Arrange
		Organization organization = createOrganization("Status Test Organization");
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);
		
		// Set status and message (these are set by the controller layer)
		response.setStatus(Response.Status.SUCCESS);
		response.setMessage("Organization fetched successfully");

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(Response.Status.SUCCESS, response.getStatus(), "Status should be settable");
		assertEquals("Organization fetched successfully", response.getMessage(), "Message should be settable");
	}

	@Test
	void testToResponse_IdTypeConversion() {
		// Arrange
		Organization organization = createOrganization("ID Test Organization");
		HUID organizationId = organization.getId();
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getId(), "Response ID should not be null");
		assertEquals(organizationId.toUUID(), response.getId(), "HUID should be correctly converted to UUID");
	}

	@Test
	void testToResponse_WithEmptyName() {
		// Arrange
		Organization organization = createOrganization("");
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals("", response.getName(), "Empty name should be mapped");
	}

	@Test
	void testToResponse_WithLongName() {
		// Arrange
		String longName = "A".repeat(500);
		Organization organization = createOrganization(longName);
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertEquals(longName, response.getName(), "Long name should be preserved");
		assertEquals(500, response.getName().length(), "Name length should be preserved");
	}

	@Test
	void testToResponse_WithDefaultOrganizationId() {
		// Arrange
		String organizationName = "Default Organization";
		Organization organization = createOrganization(organizationName);
		
		OrganizationFetchUseCase.Result result = OrganizationFetchUseCase.Result.builder()
				.organization(organization)
				.build();

		// Act
		OrganizationResponse response = mapper.toResponse(result);

		// Assert
		assertNotNull(response, "Response should not be null");
		assertNotNull(response.getId(), "Organization ID should be mapped");
		assertEquals(organizationName, response.getName(), "Organization name should be correctly mapped");
	}
}
