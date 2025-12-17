package software.blacknode.backend.infrastructure.project.entity.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import software.blacknode.backend.infrastructure.entity.version.VersionableEntity;
import software.blacknode.backend.infrastructure.entity.version.migration.MigrationEntity;

@Builder
@Getter
public class ProjectMetaEntity implements VersionableEntity {
	
	@NonNull private String name;
	@NonNull private String description;
	@NonNull private String color;
	
//	@Builder
//	@NoArgsConstructor
//	@AllArgsConstructor
//	public static class ProjectMetaEntity_V001 implements MigrationEntity<ProjectMetaEntity> {
//
//		@NonNull private String name;
//		@NonNull private String description;
//		
//		@Override
//		public ProjectMetaEntity upgrade() {
//			var name = this.name;
//			var description = this.description;
//			var color = "#FFFFFF"; // Default color
//			
//			return ProjectMetaEntity.builder()
//					.name(name)
//					.description(description)
//					.color(color)
//					.build();
//		}
//		
//	}
}
