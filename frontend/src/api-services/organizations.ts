import { type Result } from '@/utils';
import { defineApiService } from './utils/DefineApiService';
import { parseResponse, passResult, type ApiError } from './utils';

interface Organization {
    any: number; // todo
}

export const useOrganizationsApiService = defineApiService('dummy url', {
    async requestAllOrganizations(baseUrl): Promise<Result<Organization[], ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/organizations`, {
                method: 'GET',
            }),
        );
    },

    async updateOrganization(
        baseUrl,
        payload: { organizationId: string; updates: Partial<Organization> },
    ) {
        return passResult(
            fetch(`${baseUrl}/organizations/${payload.organizationId}`, {
                method: 'PATCH',
                body: JSON.stringify({
                    operations: Object.keys(payload.updates),
                    ...payload.updates,
                }),
            }),
        );
    },
});
