package software.blacknode.backend.application.organization;

import java.util.Optional;

import org.springframework.stereotype.Service;

import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.organization.Organization;
import software.blacknode.backend.domain.organization.repository.OrganizationRepository;

@Service
public class OrganizationService {

	private OrganizationRepository repository;
	
	public OrganizationService(OrganizationRepository repository) {
		this.repository = repository;
	}
	
	public Organization create(CreationMeta meta) {
		var organization = new Organization();
		
		organization.create(Optional.of(meta));
		
		repository.save(organization);
		
		return organization;
	}
	
}
