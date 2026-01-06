package software.blacknode.backend.application.organization;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.domain.entity.modifier.impl.create.meta.CreationMeta;
import software.blacknode.backend.domain.entity.modifier.impl.modify.meta.ModificationMeta;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.organization.repository.OrganizationRepository;

@Transactional
@Service
@RequiredArgsConstructor
public class OrganizationService {

	private OrganizationRepository repository;
	
	public Organization getOrThrow(HUID organizationId) {
		return repository.findById(organizationId)
				.orElseThrow(() -> new BlacknodeException("Organization with ID " + organizationId + " not found."));
	}
	
	public Optional<Organization> getOrganization(HUID organizationId) {
		return repository.findById(organizationId);
	}
	
	public Optional<Organization> getDefaultOrganization() {
		return repository.findById(Organization.DEFAULT_ORGANIZATION_ID);
	}
	
	public boolean isDefaultOrganizationPresent() {
		return getDefaultOrganization().isPresent();
	}
	
	public Organization create(CreationMeta meta) {
		var organization = new Organization();
		
		organization.create(Optional.of(meta));
		
		repository.save(organization);
		
		return organization;
	}
	
	public Organization modify(HUID organizationId, ModificationMeta meta) {
		return modify(organizationId, List.of(meta));
	}
	
	public Organization modify(HUID organizationId, List<ModificationMeta> metas) {
		var organization = getOrThrow(organizationId);
		
		for (var meta : metas) {
			organization.modify(meta);
		}
		
		repository.save(organization);
		
		return organization;
	}
	
}
