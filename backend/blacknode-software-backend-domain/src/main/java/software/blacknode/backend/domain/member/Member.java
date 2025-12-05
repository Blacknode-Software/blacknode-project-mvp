package software.blacknode.backend.domain.member;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.member.meta.MemberMeta;
import software.blacknode.backend.domain.member.settings.MemberSettings;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public class Member implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	@Getter private HUID account;
	@Getter private HUID role;
	
	@Getter private MemberSettings settings;
	@Getter private MemberMeta meta;

	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID organizationId;
	
	@Override
	public void create(Optional<CreationMeta> meta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta) {
		// TODO Auto-generated method stub
		
	}
	
	
}
