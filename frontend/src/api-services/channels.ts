import { type Result } from '@/utils';
import { defineApiService } from './utils/DefineApiService';
import { parseResponse, passResult, type ApiError } from './utils';
import { API_URL } from '@/config/env';

interface Channel {
    any: number; // todo
}

interface RequestAllChannelsForProjectSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    ids: string[];
}

interface RequestChannelsBatchSuccess {
    message: 'Operation completed successfully.';
    status: 'success';
    items: {
        id: string;
        name: string;
        description: string;
        color: string;
    }[];
}

export const useChannelsApiService = defineApiService(API_URL, {
    async requestAllChannelsForProject(
        baseUrl,
        payload: { organizationId: string; projectId: string; authToken: string },
    ): Promise<Result<RequestAllChannelsForProjectSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/projects/${payload.projectId}/channels`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async requestChannelsBatch(
        baseUrl,
        payload: { organizationId: string; ids: string[]; authToken: string },
    ): Promise<Result<RequestChannelsBatchSuccess, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/channels/batch-fetch`, {
                method: 'POST',
                body: JSON.stringify({
                    ids: payload.ids,
                }),
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async createNewChannel(
        baseUrl,
        payload: { organizationId: string; projectId: string; authToken: string },
    ) {
        return passResult(
            fetch(`${baseUrl}/projects/${payload.organizationId}/channels`, {
                method: 'POST',
                body: JSON.stringify({}),
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async requestChannel(
        baseUrl,
        payload: { organizationId: string; channelId: string; authToken: string },
    ): Promise<Result<Channel, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/channels/${payload.channelId}`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async deleteChannel(
        baseUrl,
        payload: { organizationId: string; channelId: string; authToken: string },
    ) {
        return passResult(
            fetch(`${baseUrl}/channels/${payload.channelId}`, {
                method: 'DELETE',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },

    async updateChannel(
        baseUrl,
        payload: {
            organizationId: string;
            channelId: string;
            updates: Partial<Channel>;
            authToken: string;
        },
    ) {
        return passResult(
            fetch(`${baseUrl}/channels/${payload.channelId}`, {
                method: 'PATCH',
                body: JSON.stringify({
                    operations: Object.keys(payload.updates),
                    ...payload.updates,
                }),
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },
});
