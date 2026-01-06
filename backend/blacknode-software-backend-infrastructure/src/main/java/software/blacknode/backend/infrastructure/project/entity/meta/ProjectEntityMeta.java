package software.blacknode.backend.infrastructure.project.entity.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.annotation.VersionedEntity;
import software.blacknode.backend.infrastructure.entity.version.migration.MigrationEntity;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@VersionedEntity
@ToString
public class ProjectEntityMeta implements VersionableEntity {
	
	@NonNull private String name;
	@NonNull private String description;
	@NonNull private String color;
	
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProjectMetaEntity_V001 implements MigrationEntity<ProjectEntityMeta> {

		@NonNull private String name;
		@NonNull private String description;
		
		@Override
		public ProjectEntityMeta upgrade() {
			var name = this.name;
			var description = this.description;
			var color = "#FFFFFF"; // Default color
			
			return ProjectEntityMeta.builder()
					.name(name)
					.description(description)
					.color(color)
					.build();
		}
		
	}
}
