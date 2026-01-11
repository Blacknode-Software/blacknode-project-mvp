import { API_URL } from '@/config/env';
import { defineApiService, parseResponse, type ApiError } from './utils';
import type { Result } from '@/utils';

interface Success {
    message: 'Operation completed successfully.';
    status: 'success';
    accessToken: string;
    accessTokenExpiresAt: number;
}

export const useAuthenticationApiService = defineApiService(API_URL, {
    async requestAuthentication(
        baseUrl,
        payload: { email: string; password: string },
    ): Promise<Result<Success, ApiError>> {
        return parseResponse(
            fetch(`${baseUrl}/auth/password`, {
                method: 'POST',
                body: JSON.stringify({
                    email: payload.email,
                    password: payload.password, // TODO: SHA256
                }),
                headers: {
                    'Content-Type': 'application/json',
                },
            }),
        );
    },
});
