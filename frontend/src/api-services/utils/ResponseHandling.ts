import { err, ok, type Result } from '@/utils';

export interface ApiError {
    type: 'json' | 'response';
    code: number;
}

export interface ApiSuccess {
    status: 'success';
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
): Promise<Result<ApiSuccess, ApiError>> {
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
