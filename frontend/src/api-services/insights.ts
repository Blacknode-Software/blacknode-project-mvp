import { API_URL } from '@/config/env';
import { defineApiService, parseResponse, type ApiError } from './utils';
import type { Result } from '@/utils';

export const useInvitesApiService = defineApiService(API_URL, {
    async requestIsnights(
        baseUrl,
        payload: { organizationId: string; authToken: string; channelId: string },
        /* eslint-disable  @typescript-eslint/no-explicit-any */
    ): Promise<Result<any, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/channel/${payload.channelId}/insights`, {
                method: 'GET',
                headers: {
                    'X-Organization-Id': payload.organizationId,
                    Authorization: `Bearer ${payload.authToken}`,
                },
            }),
        );
    },
});
