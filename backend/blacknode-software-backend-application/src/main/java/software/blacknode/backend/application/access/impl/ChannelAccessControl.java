package software.blacknode.backend.application.access.impl;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.exception.AccessDeniedException;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.channel.ChannelService;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.member.association.MemberAssociationService;
import software.blacknode.backend.domain.channel.Channel;
import software.blacknode.backend.domain.member.Member;

@Transactional
@Service
@RequiredArgsConstructor
public class ChannelAccessControl {

	private final MemberAssociationService memberAssociationService;
	private final ProjectAccessControl projectAccessControl;
	private final ChannelService channelService;
	private final MemberService memberService;
	
	public void ensureMemberHasChannelAccess(HUID memberId, HUID channelId, HUID organizationId, AccessLevel level) {
		var member = memberService.getOrThrow(organizationId, memberId);
		var channel = channelService.getOrThrow(organizationId, channelId);
		
		ensureMemberHasChannelAccess(member, channel, level);
	}
	
	public void ensureMemberHasChannelAccess(Member member, HUID channelId, AccessLevel level) {
		var channel = channelService.getOrThrow(member.getOrganizationId(), channelId);
		
		ensureMemberHasChannelAccess(member, channel, level);
	}
	
	public void ensureMemberHasChannelAccess(HUID memberId, Channel channel, AccessLevel level) {
		var member = memberService.getOrThrow(channel.getOrganizationId(), memberId);
		
		ensureMemberHasChannelAccess(member, channel, level);
	}
	
	public void ensureMemberHasChannelAccess(Member member, Channel channel, AccessLevel level) {
		var hasAccess = hasAccessToChannel(member, channel, level);
		
		var memberId = member.getId();
		var channelId = channel.getId();
		
		if(!hasAccess) {
			throw new AccessDeniedException("Member with ID " + memberId + " does not have " + level + " access to channel with ID " + channelId + ".");		
		}
	}
	
	public AccessLevel getRoleAccessInChannel(HUID memberId, HUID channelId, HUID organizationId) {
		var channel = channelService.getOrThrow(organizationId, channelId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return getRoleAccessInChannel(member, channel);
	}
	
	public AccessLevel getRoleAccessInChannel(Member member, HUID channelId) {
		var channel = channelService.getOrThrow(member.getOrganizationId(), channelId);
		
		return getRoleAccessInChannel(member, channel);
	}
	
	public AccessLevel getRoleAccessInChannel(HUID memberId, Channel channel) {
		var member = memberService.getOrThrow(channel.getOrganizationId(), memberId);
		
		return getRoleAccessInChannel(member, channel);
	}
	
	public AccessLevel getRoleAccessInChannel(Member member, Channel channel) {
		var organizationId = member.getOrganizationId();
		
		channel.ensureBelongsToOrganization(organizationId);
		
		var access = projectAccessControl.getRoleAccessInProject(member, channel.getProjectId());
		
		if(access.atLeast(AccessLevel.MANAGE)) {
			return access;
		}
		
		var association = memberAssociationService.getMemberChannelAssociationOrThrow(organizationId, member.getId(), channel.getId());
		
		var roleId = association.getRoleId();
		
		return projectAccessControl.getRoleAccess(organizationId, roleId);
	}
	
	public boolean hasAccessToChannel(HUID memberId, HUID channelId, HUID organizationId, AccessLevel level) {
		var channel = channelService.getOrThrow(organizationId, channelId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return hasAccessToChannel(member, channel, level);
	}
	
	public boolean hasAccessToChannel(Member member, HUID channelId, AccessLevel level) {
		var channel = channelService.getOrThrow(member.getOrganizationId(), channelId);
		
		return hasAccessToChannel(member, channel, level);
	}
	
	public boolean hasAccessToChannel(HUID memberId, Channel channel, AccessLevel level) {
		var member = memberService.getOrThrow(channel.getOrganizationId(), memberId);
		
		return hasAccessToChannel(member, channel, level);
	}

	public boolean hasAccessToChannel(Member member, Channel channel, AccessLevel level) {
		var access = getRoleAccessInChannel(member, channel);
		
		return access.atLeast(level);
	}
}