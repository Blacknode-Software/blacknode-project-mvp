import { type Result } from '@/utils';
import { defineApiService } from './utils/DefineApiService';
import { parseResponse, passResult, type ApiError } from './utils';

interface Channel {
    any: number; // todo
}

export const useChannelsApiService = defineApiService('dummy url', {
    async requestAllChannelsForProject(
        baseUrl,
        payload: { organizationId: string; projectId: string },
    ): Promise<Result<Channel[], ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects/${payload.projectId}/channels`, {
                method: 'GET',
            }),
        );
    },

    async createNewChannel(baseUrl, payload: { organizationId: string; projectId: string }) {
        return passResult(
            fetch(`${baseUrl}/projects/${payload.organizationId}/channels`, {
                method: 'POST',
                body: JSON.stringify({}),
            }),
        );
    },

    async requestChannel(
        baseUrl,
        payload: { organizationId: string; channelId: string },
    ): Promise<Result<Channel, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/channels/${payload.channelId}`, {
                method: 'GET',
            }),
        );
    },

    async deleteChannel(baseUrl, payload: { organizationId: string; channelId: string }) {
        return passResult(
            fetch(`${baseUrl}/channels/${payload.channelId}`, {
                method: 'DELETE',
            }),
        );
    },

    async updateChannel(
        baseUrl,
        payload: { organizationId: string; channelId: string; updates: Partial<Channel> },
    ) {
        return passResult(
            fetch(`${baseUrl}/channels/${payload.channelId}`, {
                method: 'PATCH',
                body: JSON.stringify({
                    operations: Object.keys(payload.updates),
                    ...payload.updates,
                }),
            }),
        );
    },
});
