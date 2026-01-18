import { type Result } from '@/utils';
import { defineApiService } from './utils/DefineApiService';
import { parseResponse, passResult, type ApiError } from './utils';
import { API_URL } from '@/config/env';

interface Organization {
    any: number; // todo
}

interface RequestOrganizationSuccess {
    status: 'success';
    message: string;
    id: string;
    name: string;
}

export const useOrganizationsApiService = defineApiService(API_URL, {
    async requestOrganization(
        baseUrl,
        payload: { organizationId: string; authToken: string },
    ): Promise<Result<RequestOrganizationSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/organization`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async requestAllOrganizations(
        baseUrl,
        payload: { authToken: string },
    ): Promise<Result<Organization[], ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/organizations`, {
                method: 'GET',
                headers: {
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async updateOrganization(
        baseUrl,
        payload: { organizationId: string; updates: Partial<Organization>; authToken: string },
    ) {
        return passResult(
            fetch(`${baseUrl}/organizations/${payload.organizationId}`, {
                method: 'PATCH',
                body: JSON.stringify({
                    operations: Object.keys(payload.updates),
                    ...payload.updates,
                }),
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },
});
