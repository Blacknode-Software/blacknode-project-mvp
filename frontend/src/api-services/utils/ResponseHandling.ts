import { err, ok, type Result } from '@/utils';

export interface ApiError {
    type: 'json' | 'response';
    code: number;
}

export interface ApiStatus {
    status: 'success' | 'failure';
    message: string;
}

export async function parseResponse<T>(
    fetchPromise: ReturnType<typeof fetch>,
): Promise<Result<T, ApiError>> {
    const res = await fetchPromise;

    if (res.ok) {
        return res
            .json()
            .then((obj) => ok(obj))
            .catch(() => err({ type: 'json', code: res.status }));
    } else {
        return err({ type: 'response', code: res.status });
    }
}

export async function passResult(
    fetchPromise: ReturnType<typeof fetch>,
): Promise<Result<ApiStatus, ApiError>> {
    const res = await fetchPromise;

    if (res.ok) {
        return res
            .json()
            .then((obj) => ok(obj))
            .catch(() => err({ type: 'json', code: res.status }));
    } else {
        return err({ type: 'response', code: res.status });
    }
}
