export interface UiUser {
    uuid: string;
}

export interface ApiUser {
    uuid: string;
}

export function userUi(apiUser: ApiUser): UiUser {
    return {
        uuid: apiUser.uuid,
    };
}

export function userApi(uiUser: UiUser): ApiUser {
    return {
        uuid: uiUser.uuid,
    };
}
