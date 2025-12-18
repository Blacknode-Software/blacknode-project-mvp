package software.blacknode.backend.domain.task.status;

import me.hinsinger.hinz.common.huid.HUID;

public interface TaskStatus {
	
	public static final TaskStatus STATUS_TODO = of(
			HUID.fromString("039465d28-a4b7-44f6-8dfb-000000000001"),
			"To Do",
			"Task to be done",
			"#FAFAFF"
	);
	
	public static final TaskStatus STATUS_IN_PROGRESS = of(
			HUID.fromString("39465d28-a4b7-44f6-8dfb-000000000002"),
			"In Progress",
			"Task is in progress",
			"#E6AE6E"
	);
	
	public static final TaskStatus STATUS_ON_HOLD = of(
			HUID.fromString("39465d28-a4b7-44f6-8dfb-000000000003"),
			"In Review",
			"Task is on hold for review",
			"#A748CE"
	);
	
	public static final TaskStatus STATUS_DONE = of(
			HUID.fromString("39465d28-a4b7-44f6-8dfb-000000000004"),
			"DONE",
			"Task is completed",
			"#33C4a8"
	);
	
	public static TaskStatus of(HUID id, String name, String description, String colorCode) {
		return new TaskStatus() {
			@Override
			public HUID getId() {
				return id;
			}
			
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public String getDescription() {
				return description;
			}
			
			@Override
			public String getColorCode() {
				return colorCode;
			}
			
			@Override
	        public boolean equals(Object obj) {
	            if (this == obj)
	                return true;
	            if (obj == null || !(obj instanceof TaskStatus))
	                return false;
	            
	            TaskStatus other = (TaskStatus) obj;
	            return this.getId().equals(other.getId());
	        }
			
	        @Override
	        public int hashCode() {
	            return getId().hashCode();
	        }
		};
	}
	
	HUID getId();
	String getName();
	String getDescription();
	String getColorCode();
	
}
