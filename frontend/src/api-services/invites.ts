import type { Result } from '@/utils';
import { defineApiService, parseResponse, type ApiError } from './utils';

interface RequestOrganizationInvitesSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    ids: string[];
}

interface CreateInviteSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    inviteId: string;
    token: string;
}

interface ClaimInvite {
    message: 'Operation completed successfully.';
    status: 'success';
}

interface BatchFeatchInvites {
    message: 'Operation completed successfully.';
    status: 'success';
    items: {
        id: string;
        email: string;
        token: string;
        revoked: boolean;
        expired: boolean;
        expiresAt: number;
        claimed: boolean;
        claimedBy: string;
        claimedAt: number;
        status: string;
        message: string;
    }[];
}

export const useInvitesApiService = defineApiService('dummy url', {
    async requestOrganizationInvites(
        baseUrl,
        payload: { organizationId: string; authToken: string },
    ): Promise<Result<RequestOrganizationInvitesSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/organization/invites`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async createInvite(
        baseUrl,
        payload: { organizationId: string; email: string; authToken: string },
    ): Promise<Result<CreateInviteSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/organization/invites`, {
                method: 'POST',
                body: JSON.stringify({
                    email: payload.email,
                }),
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async revokeInvite(
        baseUrl,
        payload: { organizationId: string; id: string; authToken: string },
    ): Promise<Result<CreateInviteSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/invites/${payload.id}/revoke`, {
                method: 'POST',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async claimInvite(
        baseUrl,
        payload: {
            organizationId: string;
            token: string;
            firstName: string;
            lastName: string;
            password: string;
        },
    ): Promise<Result<ClaimInvite, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/invites/claim`, {
                method: 'POST',
                body: JSON.stringify({
                    token: payload.token,
                    firstName: payload.firstName,
                    lastName: payload.lastName,
                    password: payload.password,
                }),
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    'Content-Type': 'application/json',
                },
            }),
        );
    },

    async batchFeatchInvites(
        baseUrl,
        payload: { organizationId: string; ids: string[]; authToken: string },
    ): Promise<Result<BatchFeatchInvites, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/invites/batch-fetch`, {
                method: 'POST',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },
});
