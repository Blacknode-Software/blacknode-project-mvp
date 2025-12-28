package software.blacknode.backend.application.access.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.hinsinger.hinz.common.huid.HUID;
import software.blacknode.backend.application.access.exception.AccessDeniedException;
import software.blacknode.backend.application.access.level.AccessLevel;
import software.blacknode.backend.application.member.MemberService;
import software.blacknode.backend.application.view.ViewService;
import software.blacknode.backend.domain.member.Member;
import software.blacknode.backend.domain.view.View;

@Service
@RequiredArgsConstructor
public class ViewAccessControl {

	private final ChannelAccessControl channelAccessControl;
	private final MemberService memberService;
	private final ViewService viewService;
	
	public void ensureMemberHasViewAccess(HUID memberId, HUID viewId, HUID organizationId, AccessLevel level) {
		var member = memberService.getOrThrow(organizationId, memberId);
		var view = viewService.getOrThrow(organizationId, viewId);
		
		ensureMemberHasViewAccess(member, view, level);
	}
	
	public void ensureMemberHasViewAccess(Member member, HUID viewId, AccessLevel level) {
		var view = viewService.getOrThrow(member.getOrganizationId(), viewId);
		
		ensureMemberHasViewAccess(member, view, level);
	}
	
	public void ensureMemberHasViewAccess(HUID memberId, View view, AccessLevel level) {
		var member = memberService.getOrThrow(view.getOrganizationId(), memberId);
		
		ensureMemberHasViewAccess(member, view, level);
	}
	
	public void ensureMemberHasViewAccess(Member member, View view, AccessLevel level) {
		var hasAccess = hasAccessToView(member, view, level);
		
		var memberId = member.getId();
		var viewId = view.getId();
		
		if(!hasAccess) {
			throw new AccessDeniedException("Member with ID " + memberId + " does not have " + level + " access to view with ID " + viewId + ".");		
		}
	}
	
	public AccessLevel getRoleAccessInView(HUID memberId, HUID viewId, HUID organizationId) {
		var view = viewService.getOrThrow(organizationId, viewId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return getRoleAccessInView(member, view);
	}
	
	public AccessLevel getRoleAccessInView(Member member, HUID viewId) {
		var view = viewService.getOrThrow(member.getOrganizationId(), viewId);
		
		return getRoleAccessInView(member, view);
	}
	
	public AccessLevel getRoleAccessInView(HUID memberId, View view) {
		var member = memberService.getOrThrow(view.getOrganizationId(), memberId);
		
		return getRoleAccessInView(member, view);
	}
	
	public AccessLevel getRoleAccessInView(Member member, View view) {
		var organizationId = member.getOrganizationId();
		
		view.ensureBelongsToOrganization(organizationId);
		
		var channelId = view.getChannelId();
		
		var access = channelAccessControl.getRoleAccessInChannel(member, channelId);
		
		return access;
	}
	
	public boolean hasAccessToView(HUID memberId, HUID viewId, HUID organizationId, AccessLevel level) {
		var view = viewService.getOrThrow(organizationId, viewId);
		var member = memberService.getOrThrow(organizationId, memberId);
		
		return hasAccessToView(member, view, level);
	}
	
	public boolean hasAccessToView(Member member, HUID viewId, AccessLevel level) {
		var view = viewService.getOrThrow(member.getOrganizationId(), viewId);
		
		return hasAccessToView(member, view, level);
	}
	
	public boolean hasAccessToView(HUID memberId, View view, AccessLevel level) {
		var member = memberService.getOrThrow(view.getOrganizationId(), memberId);
		
		return hasAccessToView(member, view, level);
	}
	
	public boolean hasAccessToView(Member member, View view, AccessLevel level) {
		var access = getRoleAccessInView(member, view);
		
		return access.atLeast(level);
	}
}