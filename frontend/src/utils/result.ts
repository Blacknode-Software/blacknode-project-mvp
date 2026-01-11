export interface Result<T, E> {
    unwrapOr(fallback: T): T;

    unwrap(): T;

    isOk(): this is Ok<T>;

    isErr(): this is Err<E>;
}

export class Ok<T> implements Result<T, never> {
    readonly ok = true as const;

    constructor(public readonly value: T) {}

    unwrapOr(): T {
        return this.value;
    }

    unwrap(): T {
        return this.value;
    }

    isOk(): this is Ok<T> {
        return true;
    }

    isErr(): this is Err<never> {
        return false;
    }
}

export class Err<E> implements Result<never, E> {
    readonly ok = false as const;

    constructor(public readonly value: E) {}

    unwrapOr<T>(fallback: T): T {
        return fallback;
    }

    unwrap(): never {
        throw new Error('unwrap failed');
    }

    isOk(): this is Ok<never> {
        return false;
    }

    isErr(): this is Err<E> {
        return true;
    }
}

export function ok<T>(v: T): Result<T, never> {
    return new Ok(v);
}

export function err<E>(e: E): Result<never, E> {
    return new Err(e);
}
