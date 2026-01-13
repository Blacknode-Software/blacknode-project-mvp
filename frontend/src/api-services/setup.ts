import { API_URL } from '@/config/env';
import { defineApiService, parseResponse, type ApiError } from './utils';
import type { Result } from '@/utils';

interface Success {
    message: 'Operation completed successfully.';
    status: 'success';
    organizationId: string;
}

export const useSetupApiService = defineApiService(API_URL, {
    async requestSetup(
        baseUrl,
        payload: {
            organizationName: string;
            adminFirstName: string;
            adminLastName: string;
            adminEmail: string;
            adminPassword: string;
        },
    ): Promise<Result<Success, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/setup`, {
                method: 'POST',
                body: JSON.stringify({
                    organizationName: payload.organizationName,
                    adminFirstName: payload.adminFirstName,
                    adminLastName: payload.adminLastName,
                    adminEmail: payload.adminEmail,
                    adminPassword: payload.adminPassword, // TODO: SHA256
                }),
            }),
        );
    },
});
