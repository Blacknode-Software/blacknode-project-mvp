// eslint-disable-next-line @typescript-eslint/no-explicit-any
type Requester = (baseUrl: string, payload: any) => any;
type RequestersBundle = Record<string, Requester>;

type ApiService<T extends RequestersBundle> = {
    [K in keyof T]: (payload: Parameters<T[K]>[1]) => ReturnType<T[K]>;
};
type UseApiServiceFunc<T extends RequestersBundle> = () => ApiService<T>;

export function defineApiService<T extends RequestersBundle>(
    baseUrl: string,
    requesters: T,
): UseApiServiceFunc<T> {
    const obj = {} as ApiService<T>;

    Object.entries(requesters).forEach(([key, func]) => {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        (obj as any)[key] = (payload: any) => func(baseUrl, payload);
    });

    return () => obj;
}
