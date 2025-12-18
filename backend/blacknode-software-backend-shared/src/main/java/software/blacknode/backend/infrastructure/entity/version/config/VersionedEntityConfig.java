package software.blacknode.backend.infrastructure.entity.version.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import software.blacknode.backend.infrastructure.entity.version.hibernate.VersionedEntityJavaType;
import software.blacknode.backend.infrastructure.entity.version.interceptor.VersionedEntityPostLoadListener;

@Configuration
public class VersionedEntityConfig {

	private final ObjectMapper mapper;

	public VersionedEntityConfig(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@PostConstruct
	public void init() {
		VersionedEntityJavaType.init(mapper);
	}

	@Bean
	public HibernatePropertiesCustomizer versionedEntityListener(
	        ObjectMapper mapper
	) {
	    return props -> props.put(
	            "hibernate.event.post-load",
	            new VersionedEntityPostLoadListener(mapper)
	    );
	}

}
