package software.blacknode.backend.infrastructure.entity.version.interceptor;

import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.entity.version.json.VersionedEntityJson;
import software.blacknode.backend.infrastructure.entity.version.migrator.VersionedEntityMigrator;

public class VersionedEntityPostLoadListener implements PostLoadEventListener {

	private final ObjectMapper mapper;
	private final VersionedEntityMigrator migrator;

	public VersionedEntityPostLoadListener(ObjectMapper mapper) {
		this.mapper = mapper;
		this.migrator = new VersionedEntityMigrator(mapper);
	}

	@Override
	public void onPostLoad(PostLoadEvent event) {
		Object entity = event.getEntity();
		Class<?> entityType = entity.getClass();

		ReflectionUtils.doWithFields(entityType, field -> {
			try {
				Class<?> fieldType = field.getType();

				if (!fieldType.isAnnotationPresent(VersionedEntity.class))
					return;

				field.setAccessible(true);
				Object rawValue = field.get(entity);

				if (!(rawValue instanceof String json))
					return;

				VersionedEntityJson vj = mapper.readValue(json, VersionedEntityJson.class);

				Object migrated = migrator.migrate(vj, fieldType);

				field.set(entity, migrated);

			} catch (Exception e) {
				throw new IllegalStateException("Failed to migrate versioned field: "
						+ field.getDeclaringClass().getName() + "." + field.getName(), e);
			}
		});
	}

}
