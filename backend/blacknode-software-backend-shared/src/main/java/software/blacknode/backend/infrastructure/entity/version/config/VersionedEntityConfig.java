package software.blacknode.backend.infrastructure.entity.version.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.blacknode.backend.infrastructure.entity.version.interceptor.VersionedEntityPostLoadListener;

@Configuration
public class VersionedEntityConfig {

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
