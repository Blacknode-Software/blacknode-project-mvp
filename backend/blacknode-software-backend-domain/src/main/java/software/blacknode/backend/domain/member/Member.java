package software.blacknode.backend.domain.member;

import java.util.Optional;

import lombok.Getter;
import me.hinsinger.projects.hinz.common.huid.HUID;
import me.hinsinger.projects.hinz.common.time.timestamp.Timestamp;
import software.blacknode.backend.domain.exception.BlacknodeException;
import software.blacknode.backend.domain.member.meta.MemberMeta;
import software.blacknode.backend.domain.member.meta.create.MemberAdminCreationMeta;
import software.blacknode.backend.domain.member.settings.MemberSettings;
import software.blacknode.backend.domain.modifier.create.Creatable;
import software.blacknode.backend.domain.modifier.create.meta.CreationMeta;
import software.blacknode.backend.domain.modifier.delete.Deletable;
import software.blacknode.backend.domain.modifier.delete.meta.DeletionMeta;
import software.blacknode.backend.domain.modifier.modify.Modifiable;
import software.blacknode.backend.domain.modifier.modify.meta.ModificationMeta;

public class Member implements Creatable, Modifiable, Deletable {

	@Getter private HUID id;
	
	@Getter private MemberSettings settings;
	@Getter private MemberMeta meta;

	@Getter private Timestamp creationTimestamp;
	@Getter private Timestamp modificationTimestamp;
	@Getter private Timestamp deletationTimestamp;
	
	@Getter private HUID accountId;
	@Getter private HUID organizationId;
	
	@Override
	public void create(Optional<CreationMeta> meta0) {
		if(meta0.isEmpty()) BlacknodeException.throwWith("CreationMeta is required to create an Member");
		
		var meta = meta0.get();
		
		this.id = HUID.random();
		this.settings = new MemberSettings();
		this.meta = new MemberMeta();
		
		if(meta instanceof MemberAdminCreationMeta adminMeta) {
			this.accountId = adminMeta.getAccountId();
			this.organizationId = adminMeta.getOrganizationId();
		} else {
			BlacknodeException.throwWith("Unsupported CreationMeta type for Member creation");
		}
		
		creationTimestamp = Timestamp.now();
	}
	
	@Override
	public void modify(Optional<ModificationMeta> meta0) {
		// TODO Auto-generated method stub
		
		modificationTimestamp = Timestamp.now();
	}
	
	@Override
	public void delete(Optional<DeletionMeta> meta0) {
		// TODO Auto-generated method stub
		
		deletationTimestamp = Timestamp.now();
	}
	
	
}
