import { type Result } from '@/utils';
import { defineApiService } from './utils/DefineApiService';
import { parseResponse, passResult, type ApiError } from './utils';

interface Member {
    any: number; // todo
}

export const useChannelsApiService = defineApiService('dummy url', {
    async createNewMember(baseUrl, payload: { organizationId: string }) {
        return passResult(
            fetch(`${baseUrl}/organization/${payload.organizationId}/members`, {
                method: 'POST',
                body: JSON.stringify({}),
            }),
        );
    },

    async requestMember(
        baseUrl,
        payload: { organizationId: string; memberId: string },
    ): Promise<Result<Member, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/members/${payload.memberId}`, {
                method: 'GET',
            }),
        );
    },

    async deleteMember(baseUrl, payload: { organizationId: string; memberId: string }) {
        return passResult(
            fetch(`${baseUrl}/members/${payload.memberId}`, {
                method: 'DELETE',
            }),
        );
    },
});
